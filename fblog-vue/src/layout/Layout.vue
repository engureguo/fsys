<template>
  <div id="app">
    <div class="header">
      <HdrView />
    </div>
    <div class="main">
      <div class="lside">
        <LSideBar />
      </div>
      <div class="container">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import HdrView from '../components/HdrView'
import LSideBar from '../components/LSideBar'
export default {
  name: 'Home',
  components: {
    HdrView, LSideBar
  }
}
</script>

<style lang="less" scoped>
#app {
  .main {
    // 侧边栏固定宽度
    .lside {
      width: 200px;
      height: calc(100vh - 50px);
      display: inline-block;
      vertical-align: top;
    }
    // 主体区域使用【剩余宽度】
    .container {
      width: calc(100% - 200px);
      height: calc(100vh - 50px);
      display: inline-block;
      vertical-align: top;
      // 主体区域溢出部分使用滚动条
      overflow: scroll;
    }
  }
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>
