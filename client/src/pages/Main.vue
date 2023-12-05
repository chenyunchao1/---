<template>
  <div>
    <the-header/>
    <router-view class="music-content"/>
    <scroll-top/>
    <the-footer/>
  </div>
</template>

<script>
import ScrollTop from '../components/ScrollTop'
import TheHeader from '../components/TheHeader'
import TheFooter from '../components/TheFooter'


export default {
  components: {
    ScrollTop,
    TheHeader,
    TheFooter,
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/main.scss';
</style>
