<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="类别名称">
        <el-input v-model="productCategory.categoryName" placeholder="类别名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getProductCategoryList">查询</el-button>
        <el-button type="primary" @click="newDialogVisible = true">新建作品类别</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="albumDatas" style="width: 100%">
      <el-table-column label="序号" type="index" align="center" width="width">
        <template slot-scope="scope">
          <span>{{scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="categoryName" label="类别名称" width="width"></el-table-column>
      <el-table-column label="操作" width="width">
        <template slot-scope="scope">
          <el-button  @click="editCategorys(scope.row)">编辑</el-button>
          <el-button @click="deleteCategory(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div align="center">
      <el-pagination
        @current-change="handleCurrentChange"
        background
        layout="total, prev, pager, next"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="albumDatas.length">
      </el-pagination>
    </div>
    <el-dialog
      title="编辑作品类别信息"
      :visible.sync="centerDialogVisible"
      width="30%"
      center>
            <span>
                  作品类别名称：<el-input v-model="editCategory.categoryName"></el-input>

                <el-select class="lyric-fade-enter" v-model="selectValue" placeholder="评分类型">
                <el-option
                  v-for="item in scoreCategoryList"
                  :key="item.id"
                  :label="item.scoreName"
                  :value="item.id">
                </el-option>
              </el-select>
              <el-button type="primary" @click="addScoreCategory">添加作品评分类别</el-button>
              <el-table :data="editCategory.scoreCategory" style="width: 100%">
                <el-table-column label="序号" type="index" align="center" width="width">
                  <template slot-scope="scope">
                    <span>{{scope.$index + 1}}</span>
                   </template>
                </el-table-column>
                <el-table-column prop="scoreName" label="类别名称" width="width"></el-table-column>
                <el-table-column label="操作" width="width">
                  <template slot-scope="scope">
                    <el-button @click="deleteScoreCategory(scope.row)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </span>
      <span slot="footer" class="dialog-footer">
            <el-button @click="centerDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="updateCategory">保 存</el-button>
            </span>
    </el-dialog>

    <el-dialog
      title="新建作品类别信息"
      :visible.sync="newDialogVisible"
      width="30%"
      center>
            <span>
              <el-form>
                类别名称<el-input  placeholder="请输入类别名" v-model="newProductCategory.categoryName"></el-input>
              </el-form>
            </span>
      <span slot="footer" class="dialog-footer">
            <el-button @click="newDiaQuxiao">取 消</el-button>
            <el-button type="primary" @click="newInsertCategory">保 存</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
import {HttpManager} from "../api";

export default {
  name: "ProductCategoryManage",
  data () {
    return {
      activeName: '全部歌单',
      productCategory:{
        id:"",
        categoryName:"",
      },
      newProductCategory:{
        categoryName:"",
      },
      selectValue:'',
      labelPosition:"right",
      centerDialogVisible:false,
      newDialogVisible:false,
      editCategory:{
        id:"",
        categoryName:"",
        scoreCategory:"",
      },
      scoreCategoryList:[],  //评分类别列表
      pageSize: 15, // 页数
      currentPage: 1, // 当前页
      albumDatas: [] // 作品类别
    }
  },
  computed: {
    // 计算当前表格中的数据
    data () {
      return this.albumDatas.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  mounted () {
    this.getProductCategoryList()
    this.getScoreCategoryAll()
  },
  methods: {
    // 获取当前页
    handleCurrentChange (val) {
      this.currentPage = val
    },
    //编辑学生
    editCategorys(row){
      this.selectValue =""
      this.centerDialogVisible = true
      this.editCategory.id = row.id
      this.editCategory.categoryName = row.categoryName
      this.getScoreCategory(this.editCategory.id)
    },
  //删除作品类别
    deleteCategory(row){
      let params = new URLSearchParams()
      params.append('id',row.id)
      HttpManager.deleteProCate(params)
        .then(res => {
          if (res === "success") {
            this.showError = false
            this.showSuccess = true
            this.getProductCategoryList()
            this.$notify.success({
              title: '删除成功',
              showClose: true
            })
            setTimeout(function () {
              this.$router.go(-1)
            }, 2000)
          } else {
            this.showSuccess = false
            this.showError = true
            this.$notify.error({
              title: '删除失败',
              showClose: true
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    //添加作品类型评分类型
    addScoreCategory(){
      for (let i = 0; i < this.editCategory.scoreCategory.length; i++) {
        if(this.editCategory.scoreCategory[i].id === this.selectValue){
          this.showSuccess = false
          this.showError = true
          this.$notify.error({
            title: '添加失败，重复添加该评分类型',
            showClose: true
          })
          return
        }
      }
      let params = new URLSearchParams()
      params.append('scoreCategoryId',this.selectValue)
      params.append('productionCategoryId',this.editCategory.id)
      HttpManager.addProductScoreCategory(params)
        .then(res => {
          if (res === "success") {
            this.showError = false
            this.showSuccess = true
            this.getScoreCategory(this.editCategory.id)
            this.$notify.success({
              title: '添加成功',
              showClose: true
            })
            setTimeout(function () {
              this.$router.go(-1)
            }, 2000)
          } else {
            this.showSuccess = false
            this.showError = true
            this.$notify.error({
              title: '添加失败',
              showClose: true
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    //删除作品类别绑定的评分类别
    deleteScoreCategory(row){
      let params = new URLSearchParams()
      params.append('scoreCategoryId',row.id)
      params.append('productionCategoryId',this.editCategory.id)
      HttpManager.deleteProScoreCate(params)
        .then(res => {
          if (res === "success") {
            this.showError = false
            this.showSuccess = true
            this.getScoreCategory(this.editCategory.id)
            this.$notify.success({
              title: '删除成功',
              showClose: true
            })
            setTimeout(function () {
              this.$router.go(-1)
            }, 2000)
          } else {
            this.showSuccess = false
            this.showError = true
            this.$notify.error({
              title: '删除失败',
              showClose: true
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    //保存学生信息
    updateCategory(){
      this.centerDialogVisible = false
      let params = new URLSearchParams()
      params.append('id',this.editCategory.id)
      params.append('categoryName',this.editCategory.categoryName)
      HttpManager.updateCategoryName(params)
        .then(res => {
          if (res === "success") {
            this.showError = false
            this.showSuccess = true
            this.$notify.success({
              title: '编辑成功',
              showClose: true
            })
            setTimeout(function () {
              this.$router.go(-1)
            }, 2000)
          } else {
            this.showSuccess = false
            this.showError = true
            this.$notify.error({
              title: '编辑失败',
              showClose: true
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    //获取作品分类
    getProductCategoryList(){
      let params = new URLSearchParams()
      params.append('id',this.productCategory.id)
      params.append('categoryName',this.productCategory.categoryName)
      HttpManager.getCategoryTypeList(params)
        .then(res => {
          this.albumDatas = res
          this.currentPage = 1
        })
        .catch(err => {
          console.log(err)
        })
    },
    //获取评分全部类型
    getScoreCategoryAll(){
      HttpManager.getCategoryStyleAll()
        .then(res => {
          this.scoreCategoryList = res
        })
        .catch(err => {
          console.log(err)
        })
    },

    //获取作品类别评分类型
    getScoreCategory(editCategoryId){
      HttpManager.getCategoryOfStyle(editCategoryId)
        .then(res => {
          if(res === ""){
            this.editCategory.scoreCategory=[]
            return
          }
          this.editCategory.scoreCategory = res
        })
        .catch(err => {
          console.log(err)
        })
    },
    //新建作品类别取消
    newDiaQuxiao(){
      this.newDialogVisible = false
      this.newProductCategory.categoryName = ""
    },
    //新建作品类别保存
    newInsertCategory(){
      for (let i = 0; i < this.albumDatas.length; i++) {
        if(this.albumDatas[i].categoryName === this.newProductCategory.categoryName){
          this.showSuccess = false
          this.showError = true
          this.$notify.error({
            title: '新增失败，存在同名类别',
            showClose: true
          })
          return
        }
      }
      HttpManager.newCategoryType(this.newProductCategory)
        .then(res => {
          if (res === "success") {
            this.newDialogVisible = false
            this.showError = false
            this.showSuccess = true
            this.getProductCategoryList()
            this.$notify.success({
              title: '添加成功',
              showClose: true
            })
            setTimeout(function () {
              this.$router.go(-1)
            }, 2000)
          } else {
            this.showSuccess = false
            this.showError = true
            this.$notify.error({
              title: '添加失败',
              showClose: true
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
  }
}
</script>

<style scoped>
@import '../assets/css/info.scss';
</style>
