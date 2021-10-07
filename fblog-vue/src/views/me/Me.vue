<template>
  <div id="app">
      <el-card class="box-card" style="width: 40%">
        <template #header>
          <div class="card-header">
            <span>个人信息</span>
          </div>
        </template>
        <el-form
          label-position="right"
          label-width="70px"
          :model="user">
          <el-form-item label="账户">
            <el-input v-model="user.userName" clearable disabled></el-input>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="user.nickName" clearable></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="user.gender" disabled>
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
              <el-radio label="保密">保密</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="user.age" type="number"></el-input>
          </el-form-item>
          <el-form-item label="住址">
            <el-input v-model="user.address" type="textarea" clearable></el-input>
          </el-form-item>
          <el-button type="primary" style="width: 100%" @click="updateProfile('form')">修改</el-button>
        </el-form>
      </el-card>
  </div>
</template>

<script>
import request from '@/util/request'
export default {
  data () {
    return {
      user: {}
    }
  },
  created () {
    const user = sessionStorage.getItem('user')
    this.user = JSON.parse(user)
  },
  methods: {
    updateProfile (formName) {
      console.log(formName, this.user)
      request.put('/user', this.user)
        .then(res => {
          if (res.code === 200) {
            this.$message({
              type: 'success',
              message: '修改成功！'
            })
            sessionStorage.setItem('user', JSON.stringify(this.user))
          } else {
            this.$message({
              type: 'success',
              message: res.msg
            })
          }
        })
        .catch(res => {
          this.$message({
            type: 'error',
            message: '出错了~'
          })
        })
    }
  }
}
</script>

<style lang="less" scoped>
  #app {
    padding: 20px;
  }
</style>
