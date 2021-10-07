<template>
  <div id="app">
      <div id="box">
          <el-card class="box-card">
            <template #header>
              <div class="card-header">
                <span>欢迎您的注册~ </span>
                <el-button class="button" @click="this.$router.push('/login')" type="text">已有账号？点我登录</el-button>
              </div>
            </template>
            <el-form
              ref="form"
              :rules="rules"
              label-position="right"
              label-width="80px"
              :model="form">
              <el-form-item label="用户名" prop="userName">
               <el-input v-model="form.userName" clearable></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input v-model="form.password" show-password></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="confirm">
                <el-input v-model="form.confirm" show-password></el-input>
              </el-form-item>
              <el-button type="primary" style="width: 100%" @click="registerAcc('form')">注册</el-button>
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
        userName: '',
        password: '',
        confirm: ''
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
        ],
        confirm: [
          {
            required: true,
            message: '请确认密码',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    // 自定义校验方法
    isValid () {
      if (this.form.password !== this.form.confirm) {
        this.$message({
          type: 'error',
          message: '两次密码不一致'
        })
        return false
      }
      if (this.form.userName.length === 0 || this.form.password.length === 0) return false
      return true
    },
    registerAcc (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid && this.isValid()) {
          request.post('/user/register', this.form)
            .then(res => {
              if (res.code === 200) {
                this.$message({
                  type: 'success',
                  message: '注册成功~，请登录'
                })
                // 注册成功，去登陆页
                this.$router.push('/login')
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
