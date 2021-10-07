<template>
    <div id="app">
    <h5>welcome</h5>
    <el-menu
        router
        unique-opened
        default-active="index"
        class="el-menu-vertical-demo">
        <el-menu-item index="index">
            <i class="el-icon-message"></i>
            <span>首页</span>
        </el-menu-item>
        <el-sub-menu index="people">
            <template #title>
                <i class="el-icon-user"></i>
                <span>人员管理</span>
            </template>
            <el-menu-item index="user">用户管理</el-menu-item>
            <el-menu-item index="store">库存管理</el-menu-item>
            <!-- <el-menu-item-group title="收支管理">
                <el-menu-item index="1-3">item three</el-menu-item>
            </el-menu-item-group>
            <el-sub-menu index="1-4">
                <template #title>item four</template>
                <el-menu-item index="1-4-1">item one</el-menu-item>
            </el-sub-menu> -->
        </el-sub-menu>
        <el-menu-item index="financial">
            <i class="el-icon-goods"></i>
            <span>财务管理</span>
        </el-menu-item>
        <el-menu-item index="repo" disabled>
            <i class="el-icon-document"></i>
            <span>关系管理</span>
        </el-menu-item>
        <el-menu-item index="relation">
            <i class="el-icon-setting"></i>
            <span>认证关系管理</span>
        </el-menu-item>
    </el-menu>
    </div>
</template>

<style lang="less" scoped>
  #app {
    h5 {
      font-size: 20px;
      font-weight: 300;
      margin: 20px auto 10px;
      // margin-top: 20px;
      // margin-bottom: 10px;
      text-align: center;
    }
  }
</style>

<script>
export default {
  data () {
    return {
    }
  }
}
</script>
