<template>
  <div class="my-music">
    <div class="album-slide">
      <div class="album-img">
        <el-menu :default-active="activeIndex" router class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <el-menu-item index="/studentManage">学生管理</el-menu-item>
          <el-menu-item index="/productCategoryManage">作品类别管理</el-menu-item>
          <el-menu-item index="/productionCategoryManage">评分类别管理</el-menu-item>
          <el-menu-item index="/competitionManage">赛事管理</el-menu-item>
          <el-menu-item index="/scoreManage">评分汇总</el-menu-item>
        </el-menu>
      </div></div></div>
</template>

<script>
export default {
  name: "Daohang",
  data() {
    return {
      activeIndex: '1',
    };
  },
  methods: {
    handleSelect(key, keyPath) {
    }
  }
}
</script>

<style scoped>
@import '../assets/css/info.scss';

</style>
