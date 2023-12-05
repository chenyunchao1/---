<template>
  <div class="home">
    <!--轮播图-->
    <el-carousel class="swiper-container" :interval="4000" type="card" height="280px">
      <el-carousel-item v-for="(item, index) in swiperList" :key="index">
        <img :src="item.picImg"/>
      </el-carousel-item>
    </el-carousel>
    <!--热门作品-->
    <div class="section">
      <div class="section-title">作品</div>
      <content-list :contentList="productList" path="product-album"></content-list>
    </div>
    <!--赛事-->
    <div class="section">
      <div class="section-title">赛事</div>
      <Competition/>
    </div>
  </div>
</template>

<script>
import ContentList from '../components/ContentList'
import { swiperList } from '../assets/data/swiper'
import { HttpManager } from '../api/index'
import Competition from "../components/Competition";

export default {
  name: 'home',
  components: {
    ContentList,
    Competition
  },
  data () {
    return {
      swiperList: swiperList, // 轮播图列表
      productList: [], // 作品列表
      singerList: [] // 歌手列表
    }
  },
  created () {
    // 获取歌单列表
    this.getProductList()
    // 获取歌手列表
    this.getSingerList()
  },
  methods: {
    getProductList () {
      let params = new URLSearchParams()
      HttpManager.getProductList(params)
        .then(res => {
          this.productList = res.sort().slice(0, 10)
        })
        .catch(err => {
          console.log(err)
        })
    },
    getSingerList () {
      let params = new URLSearchParams()
      HttpManager.getCompetition(params)
        .then(res => {
          this.singerList = res.sort().slice(0, 10)
        })
        .catch(err => {
          console.log(err)
        })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
</style>
