<template>
  <div class="song-list">
    <div class="song-content">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="作品名称">
          <el-input v-model="product.productionName" placeholder="作品名称"></el-input>
        </el-form-item>
        <el-form-item label="作品类别">
          <el-select v-model="product.categoryId" placeholder="作品类别" filterable>
            <el-option
              v-for="item in typeList"
              :key="item.id"
              :label="item.categoryName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="指导教师">
          <el-select ref="select" v-model="product.productionTeacherId" placeholder="指导教师" filterable>
            <el-option
              v-for="item in teacherList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分组名称">
          <el-select ref="select"  v-model="product.teamId" filterable placeholder="分组名称" >
            <el-option
              v-for="item in teamList"
              :key="item.id"
              :label="item.teamName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getProductList">查询</el-button>
        </el-form-item>
      </el-form>
      <content-list :contentList="data" path="product-album"></content-list>
      <div class="pagination">
        <el-pagination
          @current-change="handleCurrentChange"
          background
          layout="total, prev, pager, next"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="albumDatas.length">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import ContentList from '../components/ContentList'
import { mapGetters } from 'vuex'
import { HttpManager } from '../api/index'

export default {
  name: 'product-list',
  components: {
    ContentList
  },
  data () {
    return {
      activeName: '全部歌单',
      product:{
        productionName:"",
        categoryId:"",
        productionTeacherId:"",
        teamId:"",
      },
      typeList:[],  //作品类别
      teacherList:[], //教师列表
      teamList:[],
      pageSize: 15, // 页数
      currentPage: 1, // 当前页
      albumDatas: [] // 歌单
    }
  },
  computed: {
    ...mapGetters([
      'songsList'
    ]),
    // 计算当前表格中的数据
    data () {
      return this.albumDatas.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  mounted () {
    this.getTypeList()
    this.getProductList()
    this.getTeacherList()
    this.getTeamList()
  },
  methods: {
    // 获取当前页
    handleCurrentChange (val) {
      this.currentPage = val
    },
    //获取队伍列表
    getTeamList(){
      HttpManager.getTeamList()
        .then(res => {
          this.teamList = res
        })
        .catch(err => {
          console.log(err)
        })
    },

    //获取教师列表
    getTeacherList(){
      HttpManager.getTeacherList()
        .then(res => {
          this.teacherList = res
        })
        .catch(err => {
          console.log(err)
        })
    },

    //获取作品类别列表
    getTypeList(){
      HttpManager.getCategoryTypeList()
        .then(res => {
          this.typeList = res
        })
        .catch(err => {
          console.log(err)
        })
    },
    //获取作品列表
    getProductList(){
      let params = new URLSearchParams()
      params.append('productionName',this.product.productionName);
      params.append('categoryId',this.product.categoryId);
      params.append('productionTeacherId',this.product.productionTeacherId)
      params.append('teamId',this.product.teamId)
      // params.append('product',this.product)
      HttpManager.getProductList(params)
        .then(res => {
          this.currentPage = 1
          this.albumDatas = res
        })
        .catch(err => {
          console.log(err)
        })
    },

  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/song-list.scss';
</style>
