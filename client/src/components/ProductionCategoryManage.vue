<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="评分类别名称">
        <el-input v-model="Score.scoreName" placeholder="评分类别名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getScoreCategory">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="newScoreCate">新增评分类型</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="albumDatas" style="width: 100%">
      <el-table-column label="序号" type="index" align="center" width="width">
        <template slot-scope="scope">
          <span>{{scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="ID" width="width"></el-table-column>
      <el-table-column prop="scoreName" label="评分类别名称" width="width"></el-table-column>
      <el-table-column label="操作" width="width">
        <template slot-scope="scope">
          <el-button  @click="editScoreCategory(scope.row)">编辑</el-button>
          <el-button @click="deleteScoreCategory(scope.row)">删除</el-button>
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
      title="编辑评分类型信息"
      :visible.sync="centerDialogVisible"
      width="30%"
      center>
            <span>
              <el-form :label-position="labelPosition" label-width="80px" :model="editScore">
                <el-form-item label="评分类型名称">
                  <el-input v-model="editScore.scoreName"></el-input>
                </el-form-item>
              </el-form>
            </span>
      <span slot="footer" class="dialog-footer">
            <el-button @click="centerDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="updateStu">保 存</el-button>
            </span>
    </el-dialog>
    <el-dialog
      title="新增评分类型信息"
      :visible.sync="newDialogVisible"
      width="30%"
      center>
            <span>
              <el-form :label-position="labelPosition" label-width="80px" :model="newScore">
                <el-form-item label="类型名称">
                  <el-input v-model="newScore.scoreName"></el-input>
                </el-form-item>
              </el-form>
            </span>
      <span slot="footer" class="dialog-footer">
            <el-button @click="centerDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="newScoreCategory">保 存</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
import mixin from "../mixins";
import {HttpManager} from "../api";

export default {
  name: "ProductionCategoryManage",
  mixins: [mixin],
  data () {
    return {
      activeName: '全部歌单',
      Score:{
        scoreName:"",
      },
      labelPosition:"right",
      centerDialogVisible:false,
      newDialogVisible:false,
      editScore:{
        id:"",
        scoreName:"",
      },
      newScore:{
        scoreName:"",
      },
      pageSize: 15, // 页数
      currentPage: 1, // 当前页
      albumDatas: [] // 评分类别列表
    }
  },
  computed: {
    // 计算当前表格中的数据
    data () {
      return this.albumDatas.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  mounted () {
    this.getScoreCategory()
  },
  methods: {
    // 获取当前页
    handleCurrentChange(val) {
      this.currentPage = val
    },

    //新增评分类型按钮
    newScoreCate(){
      this.newDialogVisible = true
      this.newScore.scoreName = ""
    },

    //获取全部评分类别
    getScoreCategory(){
      HttpManager.getCategoryStyleAll(this.Score)
        .then(res => {
          this.albumDatas = res
          this.currentPage = 1
        })
        .catch(err => {
          console.log(err)
        })
    },
    editScoreCategory(row){
      this.editScore.id = row.id
      this.editScore.scoreName = row.scoreName
      this.centerDialogVisible = true
    },
    deleteScoreCategory(row){
      HttpManager.deleCategoryType(row.id)
        .then(res => {
          if (res === "success") {
            this.showError = false
            this.showSuccess = true
            this.getScoreCategory()
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
    updateStu(){
      this.centerDialogVisible = false
      let params = new URLSearchParams()
      params.append('id',this.editScore.id)
      params.append('scoreName',this.editScore.scoreName)
      HttpManager.updateCategoryType(params)
        .then(res => {
          if (res === "success") {
            this.showError = false
            this.showSuccess = true
            this.getScoreCategory()
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
    newScoreCategory(){
      this.newDialogVisible = false
      let params = new URLSearchParams()
      params.append('scoreName',this.newScore.scoreName)
      HttpManager.insertCategoryType(params)
        .then(res => {
          if (res === "success") {
            this.showError = false
            this.showSuccess = true
            this.getScoreCategory()
            this.$notify.success({
              title: '新增成功',
              showClose: true
            })
            setTimeout(function () {
              this.$router.go(-1)
            }, 2000)
          } else {
            this.showSuccess = false
            this.showError = true
            this.$notify.error({
              title: '新增失败',
              showClose: true
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
  },
}
</script>

<style scoped>
@import '../assets/css/info.scss';
</style>
