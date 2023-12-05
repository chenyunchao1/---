<template>
  <div>
    <el-form :inline="true" class="demo-form-inline" align="center">
      <el-form-item >
        <el-input v-model="competitionName" placeholder="赛事名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getCompetition">查询赛事</el-button>
        <el-button @click="newAddCompetition">新建赛事</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="competitionList" style="width: 100%">
      <el-table-column label="序号" type="index" align="center" width="width">
        <template slot-scope="scope">
          <span>{{scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column label="赛事名称" width="width">
        <template slot-scope="scope">
          <el-link :href="scope.row.competitionUrl" target="_blank">{{scope.row.competitionName}}</el-link>
        </template>
      </el-table-column>
      <el-table-column prop="competitionCreatTime" label="赛事发布时间" width="width"></el-table-column>
      <el-table-column prop="competitionTitle" label="比赛主题" width="width"></el-table-column>
      <el-table-column prop="competitionJoinEndTime" label="报名截止时间" width="width"></el-table-column>
      <el-table-column prop="competitionStartTime" label="比赛时间" width="width"></el-table-column>
      <el-table-column label="操作" width="width">
        <template slot-scope="scope">
          <el-popover
            placement="right"
            trigger="click">
            <el-table :data="productList" width="width">
              <el-table-column label="序号" type="index" align="center" width="width">
                <template slot-scope="scope">
                  <span>{{scope.$index + 1}}</span>
                </template>
              </el-table-column>
              <el-table-column width="width" property="productionName" label="作品名称">
                <template slot-scope="scope">
                  <el-link type="primary" @click="goAblum(scope.row)">{{scope.row.productionName}}</el-link>
                </template>
              </el-table-column>
              <el-table-column width="width" property="joinTime" label="参赛时间"></el-table-column>
              <el-table-column width="width" property="joinWinning" label="获奖情况"></el-table-column>
              <el-table-column width="width" label="编辑">
                <template slot-scope="scope">
                  <el-button @click="deleteProductCompetition(scope.row)">删除该获奖记录</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-button slot="reference" @click="getProduct(scope.row)">编辑获奖情况</el-button>
          </el-popover>
          <el-button @click="addProductCompetitionButton(scope.row)">添加赛事获奖作品信息</el-button>
          <el-button @click="deleteCompetition(scope.row)">删除赛事</el-button>
          <el-button @click="editCompetition(scope.row)">编辑赛事</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="添加赛事获奖信息"
      :visible.sync="dialogVisible"
      width="30%">
      <span>
      <el-form ref="form" :model="addProduct" label-width="80px">
        <el-form-item label="获奖作品">
          <el-select v-model="addProduct.productId" placeholder="请选择获奖作品">
            <el-option v-for="item in productList" :key="item.id" :label="item.productionName" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="参赛时间">
          <el-col :span="11">
            <el-date-picker type="date" value-format="yyyy-MM-dd" format="yyyy 年 MM 月 dd 日" placeholder="选择日期" v-model="addProduct.joinTime" style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="获奖信息">
          <el-input v-model="addProduct.joinWinning"></el-input>
        </el-form-item>
      </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="insertButton()">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog
      title="添加赛事信息"
      :visible.sync="addCompetitionVisible"
      width="30%">
      <span>
      <el-form ref="form" :model="addCompetition" label-width="80px">
        <el-form-item label="赛事名称">
          <el-input v-model="addCompetition.competitionName"></el-input>
        </el-form-item>
        <el-form-item label="赛事主题">
          <el-input v-model="addCompetition.competitionTitle"></el-input>
        </el-form-item>
        <el-form-item label="报名截至时间">
          <el-col :span="11">
            <el-date-picker type="date" value-format="yyyy-MM-dd" format="yyyy 年 MM 月 dd 日" placeholder="选择日期" v-model="addCompetition.competitionJoinEndTime" style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>
          <el-form-item label="比赛时间">
          <el-col :span="11">
            <el-date-picker type="date" value-format="yyyy-MM-dd" format="yyyy 年 MM 月 dd 日" placeholder="选择日期" v-model="addCompetition.competitionStartTime" style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="赛事链接">
          <el-input v-model="addCompetition.competitionUrl"></el-input>
        </el-form-item>
      </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="addCompetitionVisible = false">取 消</el-button>
    <el-button type="primary" @click="insertCompetition">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog
      title="添加赛事信息"
      :visible.sync="editCompetitionVisible"
      width="30%">
      <span>
      <el-form ref="form" :model="addCompetition" label-width="80px">
        <el-form-item label="赛事名称">
          <el-input v-model="addCompetition.competitionName"></el-input>
        </el-form-item>
        <el-form-item label="赛事主题">
          <el-input v-model="addCompetition.competitionTitle"></el-input>
        </el-form-item>
        <el-form-item label="报名截至时间">
          <el-col :span="11">
            <el-date-picker type="date" value-format="yyyy-MM-dd" format="yyyy 年 MM 月 dd 日" placeholder="选择日期" v-model="addCompetition.competitionJoinEndTime" style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>
          <el-form-item label="比赛时间">
          <el-col :span="11">
            <el-date-picker type="date" value-format="yyyy-MM-dd" format="yyyy 年 MM 月 dd 日" placeholder="选择日期" v-model="addCompetition.competitionStartTime" style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="赛事链接">
          <el-input v-model="addCompetition.competitionUrl"></el-input>
        </el-form-item>
      </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="addCompetitionVisible = false">取 消</el-button>
    <el-button type="primary" @click="updateCompetition">保 存</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import mixin from "../mixins";
import {mapGetters} from "vuex";
import {HttpManager} from "../api";

export default {
  name: "CompetitionManage",
  mixins: [mixin],
  props: {
    contentList: Array,
  },
  data(){
    return {
      competitionList:[], //赛事列表
      competitionId:"",
      competitionName:"",
      productList:[],   //获奖作品列表
      dialogVisible:false,    //获奖作品编辑
      addCompetitionVisible:false,    //添加赛事
      editCompetitionVisible:false,
      product:"",
      path:"product-album",
      addCompetition:{    //添加赛事信息
        id:"",
        competitionName:"",
        competitionTitle:"",
        competitionJoinEndTime:"",
        competitionStartTime:"",
        competitionUrl:"",
      },
      addProduct:{    //添加获奖作品
        id:"",
        productId:"",
        competitionId:"",
        joinTime:"",
        joinWinning:"",
      },
    }
  },
  computed: {
    ...mapGetters([
      'userId', // 用户ID
      'loginIn', // 用户是否登录
      'userName',// 用户名称
      'permissions', //用户类别
    ])
  },
  created () {
    this.getCompetition()
  },
  methods: {
    //获得赛事列表
    getCompetition(){
      let params = new URLSearchParams()
      params.append('competitionName',this.competitionName)
      HttpManager.getCompetition(params)
        .then(res => {
          this.competitionList = res
        })
        .catch(err => {
          console.log(err)
        })
    },
    //修改赛事信息编辑
    editCompetition(row){
      this.addCompetition.competitionTitle=row.competitionTitle
      this.addCompetition.competitionName=row.competitionName
      this.addCompetition.competitionUrl=row.competitionUrl
      this.addCompetition.competitionStartTime=row.competitionStartTime
      this.addCompetition.competitionJoinEndTime=row.competitionJoinEndTime
      this.addCompetition.id= row.id
      this.editCompetitionVisible = true
    },
    //修改赛事信息方法
    updateCompetition(){
      this.editCompetitionVisible = false
      let params = new URLSearchParams()
      params.append('competitionName',this.addCompetition.competitionName)
      params.append('competitionUrl',this.addCompetition.competitionUrl)
      params.append('competitionTitle',this.addCompetition.competitionTitle)
      params.append('competitionStartTime',this.addCompetition.competitionStartTime)
      params.append('competitionJoinEndTime',this.addCompetition.competitionJoinEndTime)
      params.append('id',this.addCompetition.id)
      HttpManager.updateCompetition(params)
        .then(res => {
          if (res === "success") {
            this.showError = false
            this.showSuccess = true
            this.getCompetition()
            this.$notify.success({
              title: '修改成功',
              showClose: true
            })
            setTimeout(function () {
              this.$router.go(-1)
            }, 2000)
          } else {
            this.showSuccess = false
            this.showError = true
            this.$notify.error({
              title: '修改失败',
              showClose: true
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    //删除赛事
    deleteCompetition(row){
      HttpManager.addCompetition(row.id)
        .then(res => {
          if (res === "success") {
            this.showError = false
            this.showSuccess = true
            this.getCompetition()
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

    //添加赛事方法
    insertCompetition(){
      this.addCompetitionVisible = false
      let params = new URLSearchParams()
      params.append('competitionName',this.addCompetition.competitionName)
      params.append('competitionUrl',this.addCompetition.competitionUrl)
      params.append('competitionTitle',this.addCompetition.competitionTitle)
      params.append('competitionStartTime',this.addCompetition.competitionStartTime)
      params.append('competitionJoinEndTime',this.addCompetition.competitionJoinEndTime)
      HttpManager.addCompetition(params)
        .then(res => {
          if (res === "success") {
            this.showError = false
            this.showSuccess = true
            this.getCompetition()
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
    newAddCompetition(){
      this.addCompetitionVisible = true
      this.addCompetition.competitionTitle=""
      this.addCompetition.competitionName=""
      this.addCompetition.competitionUrl=""
      this.addCompetition.competitionStartTime=""
      this.addCompetition.competitionJoinEndTime=""
    },
    //按钮
    getProduct(row){
      this.competitionId = row.id
      this.getProductList(this.competitionId)
    },
    //点击添加获奖作品按钮
    addProductCompetitionButton(row){
      this.addProduct.competitionId = row.id
      this.addProduct.productId=""
      this.addProduct.jionWinning=""
      this.addProduct.jionTime=""
      this.getAllProduction()
      this.dialogVisible = true
    },
    //点击保存按钮
    insertButton(){
      this.dialogVisible=false
      this.addProductCompetition()
    },
    //获得全部作品
    getAllProduction(){
      let params = new URLSearchParams()
      HttpManager.getProductList(params)
        .then(res => {
          this.productList = res
        })
        .catch(err => {
          console.log(err)
        })
    },
    //获得赛事中获奖作品列表
    getProductList(id){
      HttpManager.getProductByCompetitionId(id)
        .then(res => {
          this.productList = res
        })
        .catch(err => {
          console.log(err)
        })
    },
    //根据作品名进入作品页
    goAblum (item) {
      HttpManager.getProductionById(item.productionId)
        .then(res => {
          this.product = res
          this.$store.commit('setTempList', this.product)
          this.$router.push({path: `/${this.path}/${item.productionId}`})
        })
        .catch(err => {
          console.log(err)
        })
    },
    deleteProductCompetition(row){
      let params = new URLSearchParams()
      params.append('id',row.id)
      HttpManager.deleteProductCompetition(params)
        .then(res => {
          if (res === "success") {
            this.showError = false
            this.showSuccess = true
            this.getProductList(this.competitionId)
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
    addProductCompetition(){
      let params = new URLSearchParams()
      params.append('productionId',this.addProduct.productId)
      params.append('competitionId',this.addProduct.competitionId)
      params.append('joinTime',this.addProduct.joinTime)
      params.append('joinWinning',this.addProduct.joinWinning)
      HttpManager.addProductCompetition(params)
        .then(res => {
          if (res === "success") {
            this.showError = false
            this.showSuccess = true
            this.getProductList(this.competitionId)
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
  },
}
</script>

<style scoped>
@import '../assets/css/info.scss';

</style>
