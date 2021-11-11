<template>
  <div id="app">
    <div class="operation">
      <el-row>
        <el-button type="primary" @click="add" plain>添加</el-button>
        <el-button type="info" plain>导入</el-button>
        <el-button type="success" plain>导出</el-button>
        <el-input v-model="search" placeholder="按照词条名模糊查询..." clearable style="width: 250px; margin: auto 10px"/>
        <el-button type="info" @click="searchByName" plain>搜索</el-button>
      </el-row>
    </div>
    <el-table :data="tableData" style="width: 100%">
        <el-table-column sortable prop="id" label="ID" />
        <el-table-column prop="name" label="词条"  />
        <el-table-column prop="des" label="解释"  />
        <el-table-column prop="createAt" label="创建时间"  />
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
      title="添加"
      width="30%">
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="词条名">
          <el-input v-model="form.name" style="width: 90%" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="解释">
          <el-input v-model="form.des" type="textarea" style="width: 90%" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker type="date" format="YYYY-MM-DD" clearable v-model="form.createAt"></el-date-picker>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addDialogVisable = false">取消</el-button>
          <el-button type="primary" @click="doAdd" >添加</el-button>
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
      request.get('/word', {
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
    searchByName () {
      this.load()
    },
    add () { // 打开添加弹框
      this.addDialogVisable = true
      this.form = { }
    },
    doAdd () {
      // 修改
      if (this.form.id) {
        request.put('/word', {
          id: this.form.id,
          name: this.form.name,
          des: this.form.des,
          createAt: this.form.createAt
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
        // 添加
        request.post('/word', {
          name: this.form.name,
          des: this.form.des,
          createAt: this.form.createAt
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
      request.delete('/word/' + id).then(res => {
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
