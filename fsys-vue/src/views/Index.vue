<template>
  <div id="app">
    <div class="block">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>简易版管理系统</span>
            <el-button class="button" @click="projDetail" type="text">项目详情</el-button>
          </div>
        </template>
        <div class="text item">使用 vue-cli、element-plus、springboot、mybatis-plus 实现的简单的管理系统</div>
        <div class="text item">来源：B站视频</div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    projDetail () {
      window.location.href = 'https://github.com/engureguo/fsys'
    }
  }
}
</script>

<style lang="less" scoped>
#app {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
  line-height: 20px;
}

.item {
  margin-bottom: 12px;
}

.box-card {
  width: 480px;
}

</style>
