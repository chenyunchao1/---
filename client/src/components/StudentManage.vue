<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="学生学号">
        <el-input v-model="stu.nio" placeholder="学生学号"></el-input>
      </el-form-item>
      <el-form-item label="学生姓名">
        <el-input v-model="stu.name" placeholder="学生姓名"></el-input>
      </el-form-item>
      <el-form-item label="学生班级">
        <el-select v-model="stu.classId" placeholder="学生班级" filterable>
          <el-option
            v-for="item in classList"
            :key="item.id"
            :label="item.className"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getStuList">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-upload
          class="upload-demo"
          :action="importStu()"
          :on-success="handlePreview"
          multiple>
          <el-button type="primary">excel导入学生</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
    <el-table :data="data" style="width: 100%">
      <el-table-column label="序号" type="index" align="center" width="width">
        <template slot-scope="scope">
          <span>{{scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="stuNio" label="学号" width="width"></el-table-column>
      <el-table-column prop="name" label="姓名" width="width"></el-table-column>
      <el-table-column prop="className" label="班级" width="width"></el-table-column>
      <el-table-column label="操作" width="width">
        <template slot-scope="scope">
          <el-button  @click="editStu(scope.row)">编辑</el-button>
          <el-button @click="deleteStu(scope.row)">删除</el-button>
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
      title="编辑学生信息"
      :visible.sync="centerDialogVisible"
      width="30%"
      center>
            <span>
              <el-form :label-position="labelPosition" label-width="80px" :model="editStus">
                <el-form-item label="学号">
                  <el-input v-model="editStus.stuNio"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                  <el-input v-model="editStus.name"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                  <el-input v-model="editStus.password"></el-input>
                </el-form-item>
                 <el-form-item label="班级">
                    <el-select ref="select" v-model="editStus.classId" placeholder="学生班级" filterable>
                    <el-option v-for="item in classList" :key="item.id" :label="item.className" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
              </el-form>
            </span>
            <span slot="footer" class="dialog-footer">
            <el-button @click="centerDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="updateStu">保 存</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
import {mapGetters} from "vuex";
import {HttpManager} from "../api";
import mixin from "../mixins";

export default {
  name: "StudentManage",
  mixins: [mixin],
  data () {
    return {
      activeName: '全部歌单',
      stu:{
        nio:"",
        name:"",
        classId:"",
      },
      labelPosition:"right",
      centerDialogVisible:false,
      editStus:{
        id:"",
        name:"",
        password:"",
        classId:"",
        stuNio:"",
      },
      classList:[],  //班级列表
      pageSize: 10, // 页数
      currentPage: 1, // 当前页
      albumDatas: [] // 学生列表
    }
  },
  computed: {
    // 计算当前表格中的数据
    data () {
      return this.albumDatas.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  mounted () {
    this.getClassList()
    this.getStuList()
  },
  methods: {
    //导入excel模板
    importStu () {
      return `${this.$store.state.configure.HOST}/student/insertStudentForExcel`
    },
    //上传成功之后提示
    handlePreview(res,file){
      if (res.code === 1) {
        this.imageUrl = URL.createObjectURL(file.raw)
        this.$message({
          message: '导入成功',
          type: 'success'
        })
      } else {
        this.notify(res.msg, 'error')
      }
    },
    // 获取当前页
    handleCurrentChange (val) {
      this.currentPage = val
    },
    //编辑学生
    editStu(row){
      this.centerDialogVisible = true
      this.editStus.id = row.id
      this.editStus.password = row.password
      this.editStus.stuNio =row.stuNio
      this.editStus.name = row.name
      this.editStus.classId = row.classId
    },
    deleteStu(row){
      let params = new URLSearchParams()
      params.append('id',row.id)
      HttpManager.deleteStun(params)
        .then(res => {
          if (res === "success") {
            this.showError = false
            this.showSuccess = true
            this.getStuList()
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
    updateStu(){
      this.centerDialogVisible = false
      let params = new URLSearchParams()
      params.append('id',this.editStus.id)
      params.append('stuNio',this.editStus.stuNio)
      params.append('name',this.editStus.name)
      params.append('password',this.editStus.password)
      params.append('classId',this.editStus.classId)
      HttpManager.updateUserMsg(params,'student')
        .then(res => {
          if (res === "success") {
            this.showError = false
            this.showSuccess = true
            this.getStuList()
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
    //获取学生
    getStuList(){
      let params = new URLSearchParams()
      params.append('stuNio',this.stu.nio)
      params.append('name',this.stu.name)
      params.append('classId',this.stu.classId)
      HttpManager.getStudentList(params)
        .then(res => {
          res.forEach(e => {
            for (let i = 0; i < this.classList.length; i++) {
              if(e.classId == this.classList[i].id){
                e.className = this.classList[i].className
              }
            }
          })
          this.albumDatas = res
          this.currentPage = 1
        })
        .catch(err => {
          console.log(err)
        })
    },

    //获取教师列表
    getClassList(){
      let params = new URLSearchParams()
      HttpManager.getClassList(params)
        .then(res => {
          this.classList = res
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
