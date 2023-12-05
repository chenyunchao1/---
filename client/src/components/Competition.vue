<template>
  <div>
  <el-form :inline="true" class="demo-form-inline" align="center">
    <el-form-item >
      <el-input v-model="competitionName" placeholder="赛事名称"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="getCompetition">查询赛事</el-button>
    </el-form-item>
  </el-form>
  <el-table
    :data="competitionList" style="width: 100%">
    <el-table-column label="序号" type="index" align="center" width="width">
      <template slot-scope="scope">
        <span>{{scope.$index + 1}}</span>
      </template>
    </el-table-column>
    <el-table-column prop="competitionName" label="赛事名称" width="width">
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
          width="400"
          trigger="click">
          <el-table :data="productList">
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
          </el-table>
          <el-button slot="reference" @click="getProductList(scope.row)">查看作品站内获奖情况</el-button>
        </el-popover>
      </template>
    </el-table-column>
  </el-table>
  </div>
</template>

<script>
import mixin from "../mixins";
import {mapGetters} from "vuex";
import {HttpManager} from "../api";

export default {
  name: "Competition",
  mixins: [mixin],
  props: {
    contentList: Array,
  },
  data(){
    return {
      competitionList:[], //赛事列表
      competitionName:"",
      productList:[],   //获奖作品列表
      product:"",
      path:"product-album",
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
    getProductList(row){
      let params = new URLSearchParams()
      params.append('competitionId',row.id)
      HttpManager.getProductByCompetitionId(params)
        .then(res => {
          this.productList = res
        })
        .catch(err => {
          console.log(err)
        })
    },
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
    }
  },
}
</script>

<style scoped>

</style>
