<template>
  <div id="app">
      <div id="box">
          <el-card class="box-card">
            <template #header>
              <div class="card-header">
                <span>欢迎您的登录~ </span>
                <el-button class="button" @click="this.$router.push('/register')" type="text">没有账号？点我注册</el-button>
              </div>
            </template>
            <el-form
              ref="form"
              :rules="rules"
              label-position="right"
              label-width="70px"
              :model="form">
              <el-form-item label="账户" prop="userName">
               <el-input v-model="form.userName" clearable></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input v-model="form.password" show-password></el-input>
              </el-form-item>
                <el-button type="primary" style="width: 100%" @click="login('form')">登录</el-button>
          </el-form>
          </el-card>
      </div>
  </div>
</template>

<style lang="less" scoped>
#app {
  height: 100vh;
  background: darkslateblue;
  #box {
    margin: 0 auto;
    padding-top: 150px;
    width: 30%;
  }
}
</style>

<script>
import request from '@/util/request'
export default {
  data () {
    return {
      form: {
        userName: 'engure',
        password: '123'
      },
      rules: {
        userName: [
          {
            required: true,
            message: '请输入账号',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    login (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post('/user/login', this.form)
            .then(res => {
              if (res.code === 200) {
                this.$message({
                  type: 'success',
                  message: '登陆成功~'
                })
                // 将用户信息放入 sessionStorage
                sessionStorage.setItem('user', JSON.stringify(res.data))
                this.$router.push('/')
              } else {
                this.$message({
                  type: 'warning',
                  message: res.msg
                })
              }
            })
            .catch(res => {
              this.$message({
                type: 'error',
                message: '请求错误'
              })
            })
        }
      })
    }
  }
}
</script>
