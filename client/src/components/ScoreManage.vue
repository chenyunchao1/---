<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="选择作品类型">
        <el-select v-model="productCategoryId" placeholder="作品类型" filterable>
          <el-option
            v-for="item in productTypeList"
            :key="item.id"
            :label="item.categoryName"
            :value="item.id">
          </el-option>
        </el-select>
        <el-button @click="sort">查询</el-button>
      </el-form-item>

    </el-form>
    <el-table border resizable :data="tableData" style="width: 100%">
      <el-table-column sortable :label="item.label" :key="index" v-for="(item,index) in headArr" :prop="item.prop+''"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import mixin from "../mixins";
import {HttpManager} from "../api";

export default {
  name: "ScoreManage",
  mixins: [mixin],
  data(){
    return{
      productTypeList:[],   //作品类型列表
      productCategoryId:"",     //作品类型
      headArr:[], //  表头
      tableData: [],  //内容
      albumDatas:"",
      productList:"",
    }
  },
  mounted () {
    this.getProductList()
    this.getTypeList()
  },
  methods:{
    sort(){
      this.getCategoryOfStyle()
      this.getClassList()
    },

    //获得全部作品
    getProductList(){
      HttpManager.getProductList()
        .then(res => {
          this.productList = res
        })
        .catch(err => {
          console.log(err)
        })
    },

    //获得全部评分类别
    getCategoryOfStyle(){
      HttpManager.getCategoryOfStyle(this.productCategoryId)
        .then(res => {
          this.headArr=[]
          this.headArr.push({label:"作品Id",prop:"id"})
          this.headArr.push({label:"作品名称",prop:"name"})
          res.forEach(e => this.headArr.push({label:e.scoreName+"平均分",prop:e.id}))
        })
        .catch(err => {
          console.log(err)
        })
    },

    //获取作品类别列表
    getTypeList(){
      HttpManager.getCategoryTypeList()
        .then(res => {
          this.productTypeList = res
          this.productCategoryId = res[0].id
        })
        .catch(err => {
          console.log(err)
        })
    },
    //获取统计列表
    getClassList(){
      let params = new URLSearchParams()
      HttpManager.getProductScoreSort(this.productCategoryId)
        .then(res => {
          this.tableData=[]
          this.albumDatas = res
          // console.log(res);
          for(let key in res) {
            let score = {}
            for (let i = 0; i < this.productList.length; i++) {
              if (key == this.productList[i].id) {
                score.id = key
                score.name = this.productList[i].productionName


                // console.log("作品id");
                // console.log(key);
                for (let n in res[key]) {
                  // console.log("评分类别id");
                  // console.log(n);
                  // console.log("得分");
                  score[n] = res[key][n]
                  // console.log(score[n]);
                }
                this.tableData.push(score)
              }
            }
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
@import '../assets/css/my-music.scss';

</style>
