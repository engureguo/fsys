<template>
  <div id="app">
    <div class="operation">
      <el-row>
        <el-button type="primary" @click="addUser" plain>添加</el-button>
        <el-button type="info" plain>导入</el-button>
        <el-button type="success" plain>导出</el-button>
        <el-input v-model="search" placeholder="按照用户名模糊查询..." clearable style="width: 250px; margin: auto 10px"/>
        <el-button type="info" @click="searchByUserName" plain>搜索</el-button>
      </el-row>
    </div>
    <el-table :data="tableData" style="width: 100%">
        <el-table-column sortable prop="id" label="ID" />
        <el-table-column prop="userName" label="用户名"  />
        <el-table-column prop="nickName" label="昵称"  />
        <el-table-column prop="gender" label="性别"  />
        <el-table-column prop="age" label="年龄"  />
        <el-table-column prop="address" label="住址" />
        <el-table-column label="操作">
            <template #default="scope">
                <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
                <el-popconfirm title="确定删除吗？" @confirm="handleDel(scope.row.id)" icon="el-icon-info" icon-color="red">
                  <template #reference>
                      <el-button size="mini" type="danger">删除</el-button>
                  </template>
                </el-popconfirm>
            </template>
        </el-table-column>
    </el-table>

    <div class="pagination-block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :currentPage="currentPage"
        :page-sizes="pageSizes"
        :page-size="currentPageSize"
        :page-count="pageCount"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalItemCount"
      >
      </el-pagination>
    </div>

    <el-dialog
      v-model="addDialogVisable"
      title="确定"
      width="30%">
      <el-form ref="form" :model="form" label-width="120px">
        <el-input v-model="form.id" placeholder="" type="hidden"></el-input>
        <el-form-item label="用户名">
          <el-input v-model="form.userName" style="width: 90%" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickName" style="width: 90%" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.gender">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
            <el-radio label="保密">保密</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄">
          <!-- TO DO: fix bug，年龄可能为字符串 -->
          <el-input type="number" v-model="form.age" style="width: 90%" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="住址">
          <el-input type="textarea" v-model="form.address" style="width: 90%" placeholder=""></el-input>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addDialogVisable = false">取消</el-button>
          <el-button type="primary" @click="addUserReal" >添加</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
import request from '@/util/request'
export default {
  data () {
    return {
      form: {},
      addDialogVisable: false,
      pageSizes: [5, 10, 20],
      currentPage: 1,
      currentPageSize: 10,
      totalItemCount: 0,
      pageCount: 1,
      search: '',
      tableData: []
    }
  },
  created: function () {
    this.load()
  },
  methods: {
    load () {
      request.get('/user', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.tableData = res.data.records
          this.totalItemCount = res.data.total
          this.pageSize = res.data.size
          this.currentPage = res.data.current
          this.pageCount = res.data.pages
        } else {
          this.$message({
            type: 'error',
            message: '出错了'
          })
        }
      }).catch(res => {
        // 后端未开启
        this.$message({
          type: 'error',
          message: '网络错误'
        })
      })
    },
    handleEdit (row) {
      // 深拷贝，拷贝自表格中的数据
      this.form = JSON.parse(JSON.stringify(row))
      this.addDialogVisable = true
    },
    searchByUserName () {
      this.load()
    },
    addUser () { // 打开添加弹框
      this.addDialogVisable = true
      this.form = { }
    },
    addUserReal () { // 添加
      if (this.form.id) {
        request.put('/user', {
          id: this.form.id,
          userName: this.form.userName,
          nickName: this.form.nickName,
          age: this.form.age,
          gender: this.form.gender,
          address: this.form.address
        })
          .then(res => {
            this.$message({
              type: 'success',
              message: res.msg
            })
            this.load()
          })
          .catch(res => {
            this.$message({
              type: 'error',
              message: '出错了'
            })
          })
      } else {
        request.post('/user', {
          id: this.form.id,
          userName: this.form.userName,
          nickName: this.form.nickName,
          age: this.form.age,
          gender: this.form.gender,
          address: this.form.address
        })
          .then(res => {
            this.$message({
              type: 'success',
              message: res.msg
            })
            this.load()
          })
          .catch(res => {
            this.$message({
              type: 'error',
              message: '出错了'
            })
          })
      }
      this.addDialogVisable = false
    },
    handleDel (id) {
      request.delete('/user/' + id).then(res => {
        this.$message({
          type: 'success',
          message: res.msg
        })
        this.load()
      }).catch(res => {
        this.$message({
          type: 'error',
          message: '出错了~~~'
        })
      })
    },
    handleSizeChange (pageSize) { // 改变分页大小
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange (pageNum) { // 改变页码
      this.pageNum = pageNum
      this.load()
    }
  }
}
</script>

<style lang="less" scoped>
#app {
  padding: 20px;
  .operation {
    margin-bottom: 10px;
  }
}
.pagination-block {
  margin-top: 15px;
}
</style>
