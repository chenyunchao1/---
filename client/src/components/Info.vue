<template>
  <div class="info">
    <p class="title">编辑个人资料</p>
    <hr/>
    <div class="personal">
      <el-form :model="registerForm" class="demo-ruleForm" label-width="80px">
        <el-form-item prop="stuNio" label="学号">
          <el-col :span="6">
            <el-input v-model="registerForm.stuNio" placeholder="学号" :disabled="true"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item prop="username" label="用户名">
          <el-col :span="6">
          <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-col :span="6">
          <el-input type="password" placeholder="密码" v-model="registerForm.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-col :span="6">
            <div class="btn">
              <div @click="saveMsg()">保存</div>
              <div @click="goback">取消</div>
            </div>
          </el-col>
        </el-form-item>
      </el-form>

    </div>
    <p class="title">分组管理</p>
    <hr/>
    <div class="personal">
      <el-table :data="stuTeam" style="width: 100%">
        <el-table-column label="序号" type="index" align="center" width="width">
          <template slot-scope="scope">
            <span>{{scope.$index + 1}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="teamId" label="队伍id" width="width">
        </el-table-column>
        <el-table-column prop="teamName" label="队伍名称" width="width">
        </el-table-column>
        <el-table-column
          label="操作"
          width="width">
          <template slot-scope="scope">
            <stu-team-list :teamId="scope.row.id" :type="1"></stu-team-list>
            <el-button type="primary" size="mini" @click="deleteStudentTeam(scope.row)">退出分组</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <p class="title">我的作品</p>
    <hr/>
    <!--热门歌单-->
    <div class="section">
      <content-list :contentList="productionList" path="product-album"></content-list>
    </div>
</div>
</template>

<script>
import mixin from '../mixins'
import { mapGetters } from 'vuex'
import { cities } from '../assets/data/form'
import { HttpManager } from '../api/index'
import ContentList from "./ContentList";
import StuTeamList from "./StuTeamList";

export default {
  name: 'info',
  components: {
    ContentList,
    StuTeamList,
  },
  mixins: [mixin],
  data: function () {
    return {
      ss:false,
      registerForm: { // 注册
        username: '',
        password: '',
        stuNio:'',
      },
      stuTeam:[],   //学生所属的队伍列表
      stuList:[],   //单个队伍的学生列表
      productionList:[],//作品列表
    }
  },
  computed: {
    ...mapGetters([
      'userId',
      'permissions'
    ])
  },

  mounted () {
    this.getMsg(this.userId,this.permissions)
    this.getTeam(this.userId,this.permissions)
  },
  methods: {
    getMsg (id,permissions) {
      HttpManager.getUserOfId(id,permissions)
        .then(res => {
          this.registerForm.stuNio = res.stuNio
          this.registerForm.username = res.name
          this.registerForm.password = res.password
        })
        .catch(err => {
          console.log(err)
        })
    },
    getTeam (id,permissions) {
      HttpManager.getTeamOfId(id,permissions)
        .then(res => {
          this.stuTeam = res.stuTeam;
          this.productionList =res.productionList;
        })
        .catch(err => {
          console.log(err)
        })
    },
    goback () {
      this.$router.go(-1)
    },
    saveMsg () {
      let params = new URLSearchParams()
      params.append('id', this.userId)
      params.append('name', this.registerForm.username)
      params.append('password', this.registerForm.password)
      HttpManager.updateUserMsg(params,this.permissions)
        .then(res => {
          if (res ==="success") {
            this.showError = false
            this.showSuccess = true
            this.$store.commit('setUserName', this.registerForm.username)
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
    deleteStudentTeam(row){
      let params = new URLSearchParams()
      params.append('id', this.userId)
      params.append('teamId', row.id)
      HttpManager.deleteStudentTeam(params,this.permissions)
        .then(res => {
          if (res =="success") {
            this.showError = false
            this.showSuccess = true
            this.getTeam(this.userId,this.permissions)
            this.$notify.success({
              title: '退出成功',
              showClose: true
            })
            setTimeout(function () {
              this.$router.go(-1)
            }, 2000)
          } else {
            this.showSuccess = false
            this.showError = true
            this.$notify.error({
              title: '退出失败',
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

<style lang="scss" scoped>
@import '../assets/css/info.scss';
</style>
