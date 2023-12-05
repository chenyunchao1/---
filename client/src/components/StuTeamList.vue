<template>
  <el-popover
    trigger="click" v-if="type == 1">
    <el-table :data="stuList">
      <el-table-column label="序号" type="index" align="center" width="width">
        <template slot-scope="scope">
          <span>{{scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column width="150" property="stuNio" label="学号"></el-table-column>
      <el-table-column width="100" property="name" label="姓名"></el-table-column>
      <el-table-column v-if="permissions == 'teacher'" label="操作" width="150">
        <template slot-scope="scope">
          <el-button  @click="deleteStuTeam(scope.row)">删除该成员</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button slot="reference" @click="findTeam">查看分组</el-button>
  </el-popover>

    <el-button trigger="click" v-else>所属教师：{{productionTeacherName}}</el-button>


</template>

<script>
import {HttpManager} from "../api";
import {mapGetters} from "vuex";

export default {
  name: "StuTeamList",
  props: {
    teamId: Number,
    type: Number,
  },
  data(){
    return{
      stuList:[],
      productionTeacherName:"",
    }
  },
  computed: {
    ...mapGetters([
      'loginIn', // 登录标识
      'userId', // 用户ID
      'permissions', // 用户权限
      'userName'
    ])
  },
  created () {
    this.getTeacherName(this.teamId)
  },
  methods: {
    deleteStuTeam(row){
      let params = new URLSearchParams()
      params.append('id',row.id)
      params.append('teamId',this.teamId)
      HttpManager.deleteStudentTeam(params, "student")
        .then(res => {
          if (res =="success") {
            this.showError = false
            this.showSuccess = true
            this.findTeam(this.teamId,this.permissions)
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
    findTeam() {
      HttpManager.getTeamById(this.teamId, "student")
        .then(res => {
          this.stuList = res;
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 获得指导教师的姓名
    getTeacherName(teacherId){
      if(this.type == 1){
        return
      }
      HttpManager.getTeacherName(teacherId)
        .then(res => {
          this.productionTeacherName = res.name
        })
        .catch(err => {
          console.log(err)
        })
    },
  }
}
</script>

<style scoped>

</style>
