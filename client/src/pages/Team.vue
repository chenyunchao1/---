<template>
  <div class="singer">
    <ul class="singer-header">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="分组名称">
          <el-input align="center" v-model="teams.teamName" placeholder="分组名称"></el-input>
        </el-form-item>
        <el-form-item label="学生学号">
          <el-input v-model="teams.stuNio" placeholder="学生学号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getAllSinger()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="insertTeamButton = true">新建分组</el-button>
        </el-form-item>
      </el-form>
    </ul>
    <el-table :data="data" style="width: 100%">
      <el-table-column label="序号" type="index" align="center" width="width">
        <template slot-scope="scope">
          <span>{{scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="队伍ID" width="width"></el-table-column>
      <el-table-column prop="teamName" label="队伍名称" width="width"></el-table-column>
      <el-table-column  label="操作" width="width">
        <template slot-scope="scope">
          <stu-team-list :teamId="scope.row.id" :type="1"></stu-team-list>
          <el-popover
            trigger="click">
            <content-list :contentList="productList" path="song-list-album"></content-list>
            <el-button slot="reference" @click="findProductByTeamId(scope.row.id)">查看作品</el-button>
          </el-popover>
          <el-button v-if="permissions == 'student'" @click="insertStuTeam(scope.row)">加入分组</el-button>
          <el-button v-else @click="clickOnAdd(scope.row.id)">添加成员</el-button>
        </template>
      </el-table-column>
    </el-table>
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
    <el-dialog
      title="添加组内成员"
      :visible.sync="centerDialogVisible"
      width="30%"
      center>
            <span>
              <el-button type="text">成员列表</el-button>
              <el-table :data="stuList">
                <el-table-column label="序号" type="index" align="center" width="width">
                  <template slot-scope="scope">
                    <span>{{scope.$index + 1}}</span>
                  </template>
                </el-table-column>
                <el-table-column width="150" property="stuNio" label="学号"></el-table-column>
                <el-table-column width="100" property="name" label="姓名"></el-table-column>
                <el-table-column label="操作" width="150">
                  <template slot-scope="scope">
                    <el-button  @click="insertStudentOfTea(scope.row)">添加该成员</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </span>
      <span slot="footer" class="dialog-footer">
            <el-button @click="centerDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
            </span>
    </el-dialog>
    <el-dialog
      title="新建组名"
      :visible.sync="insertTeamButton"
      width="30%"
      center>
            <span>
               <el-form label-width="80px">
                  <el-form-item label="名称">
                    <el-input v-model="teamName"></el-input>
                  </el-form-item>
                </el-form>
            </span>
      <span slot="footer" class="dialog-footer">
            <el-button @click="insertTeamButton = false">取 消</el-button>
            <el-button type="primary" @click="insertTeam">保存</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
import ContentList from '../components/ContentList'

import { HttpManager } from '../api/index'
import StuTeamList from "../components/StuTeamList";
import { mapGetters } from 'vuex'


export default {
  name: 'singer',
  components: {
    ContentList,
    StuTeamList,
  },
  data () {
    return {
      teams:{
        teamName:"",
        stuNio:"",
      },
      activeName: '全部歌手',
      teamName:'',
      theTeamId:'',
      centerDialogVisible:false,  //是否显示添加成员编辑框
      insertTeamButton:false,
      stuList:[],   //学生列表
      pageSize: 10, // 页数
      currentPage: 1, // 当前页
      albumDatas: [],  //分组
      productList:[]  //某个队伍的作品列表

    }
  },
  computed: {
    // 计算当前表格中的数据
    data () {
      return this.albumDatas.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    },
    ...mapGetters([
      'loginIn', // 登录标识
      'userId', // 用户ID
      'permissions', // 用户权限
      'userName'
    ])
  },
  created () {
    this.getAllSinger()
  },
  methods: {
    // 获取当前页
    handleCurrentChange (val) {
      this.currentPage = val
    },
    //新建分组按钮
    insertTeam(){
      let params = new URLSearchParams()
      params.append('teamName',this.teamName)
      HttpManager.insertTeam(params)
        .then(res => {
          this.theTeamId = res.id
          this.findStuList()
          this.getAllSinger ()
          this.insertTeamButton = false
          this.centerDialogVisible = true
          this.teamName = ""

        })
        .catch(err => {
          console.log(err)
        })

    },
    //添加新成员按钮
    clickOnAdd(teamId){
      this.centerDialogVisible = true
      this.theTeamId = teamId
      this.findStuList()
    },
    // 获取所有分组
    getAllSinger () {
      let params = new URLSearchParams()
      params.append('teamName',this.teams.teamName)
      params.append('stuNio',this.teams.stuNio)
      HttpManager.getFindTeam(params)
        .then(res => {
          this.currentPage = 1
          this.albumDatas = res
        })
        .catch(err => {
          console.log(err)
        })
    },
    findProductByTeamId(teamId){
      let params = new URLSearchParams()
      params.append('teamId',teamId)
      params.append('teamType',1)
      HttpManager.getProductList(params)
        .then(res => {
          this.currentPage = 1
          this.productList = res
        })
        .catch(err => {
          console.log(err)
        })
    },
    insertStuTeam(row){
      let params = new URLSearchParams()
      params.append('teamId',row.id)
      params.append('tramName',row.teamName)
      params.append('studentId',this.userId)
      params.append('studentName',this.userName)
      HttpManager.insertStuTeam(params)
        .then(res => {
          if (res.code === 1) {
            this.showError = false
            this.showSuccess = true
            this.$notify.success({
              title: res.msg,
              showClose: true
            })
            setTimeout(function () {
              this.$router.go(-1)
            }, 2000)
          } else {
            this.showSuccess = false
            this.showError = true
            this.$notify.error({
              title: res.msg,
              showClose: true
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    insertStudentOfTea(row){
      let params = new URLSearchParams()
      params.append('teamId',this.theTeamId)
      params.append('studentId',row.id)
      params.append('studentName',row.name)
      HttpManager.insertStuTeam(params)
        .then(res => {
          if (res.code === 1) {
            this.showError = false
            this.showSuccess = true
            this.$notify.success({
              title: res.msg,
              showClose: true
            })
            setTimeout(function () {
              this.$router.go(-1)
            }, 2000)
          } else {
            this.showSuccess = false
            this.showError = true
            this.$notify.error({
              title: res.msg,
              showClose: true
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    findStuList() {
      HttpManager.getStudentList()
        .then(res => {
          this.stuList = res;
        })
        .catch(err => {
          console.log(err)
        })
    },

  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/singer.scss';
</style>
