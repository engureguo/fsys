<template>
      <div class="hdr">
      <div class="logo">Admin</div>
      <div class="nav"></div>
      <div class="person">
        <el-dropdown>
            <span class="el-dropdown-link">
            {{user['userName']}}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <template #dropdown>
            <el-dropdown-menu>
                <el-dropdown-item @click="this.$router.push('/me')">个人中心</el-dropdown-item>
                <el-dropdown-item @click="exitSys">退出系统</el-dropdown-item>
            </el-dropdown-menu>
            </template>
        </el-dropdown>
      </div>
    </div>
</template>

<script>
export default {
  data () {
    return {
      user: {}
    }
  },
  methods: {
    exitSys () {
      this.$message({
        type: 'success',
        message: '退出成功'
      })
      sessionStorage.clear()
      this.$router.push('/login')
    }
  },
  created () {
    const userInfoStr = sessionStorage.getItem('user')
    if (userInfoStr == null) {
      this.$router.push('/login')
    } else {
      this.user = JSON.parse(userInfoStr)
    }
  }
}
</script>

<style lang="less" scoped>

.hdr {
  height: 50px;
  padding: 0 10px;
  background: #f4f4f4;
  display: flex;
  align-content: space-around;
  .logo {
    line-height: 50px;
    margin: 0 10px;
    font-size: 20px;
    font-weight: 400;
    cursor: pointer;
    color: #54a2f5;
  }
  .nav {
    flex: 1;
  }
  .person {
    font-size: 20;
    line-height: 50px;
    margin: 0 10px;
  }
}

.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>
