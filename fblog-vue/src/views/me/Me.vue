<template>
  <div id="app">
      个人中心 {{user}}
  </div>
</template>

<script>
export default {
  data () {
    return {
      user: null
    }
  },
  created () {
    const user = sessionStorage.getItem('user')
    this.user = JSON.parse(user)
  }
}
</script>

<style lang="less" scoped>

</style>
