<template>
  <div id="app">
    <div class="operation">
      <el-row>
        <el-button type="primary" @click="add" plain>添加</el-button>
        <el-button type="info" plain>导入</el-button>
        <el-button type="success" plain>导出</el-button>
        <el-input v-model="search" placeholder="按照名字模糊查询..." clearable style="width: 250px; margin: auto 10px"/>
        <el-button type="info" @click="searchByName" plain>搜索</el-button>
      </el-row>
    </div>

    <el-table :data="tableData" style="width: 100%">
        <el-table-column sortable prop="id" label="ID" />
        <el-table-column prop="name" label="名字"  />
        <el-table-column prop="createAt" label="创建时间"  />
        <el-table-column label="头像">
          <template #default="scope">
            <el-image
              style="width: 100px; height: 100px"
              :src="scope.row.url"
              :preview-src-list="[scope.row.url]">
            </el-image>
          </template>
        </el-table-column>
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
        <el-form-item label="名字">
          <el-input v-model="form.name" style="width: 90%" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker type="date" format="YYYY-MM-DD" clearable v-model="form.createAt"></el-date-picker>
        </el-form-item>
        <el-form-item label="上传头像">
          <el-upload
            ref="upload"
            :action="filesUploadUrl"
            :on-success="filesUploadSuccess">
            <el-button size="small" type="primary">Click to upload</el-button>
          </el-upload>
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
      filesUploadUrl: 'http://localhost:8888/common/upload/',
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
    filesUploadSuccess (resp) {
      console.log(resp)
      if (resp.code === 200) this.form.url = resp.data
    },
    load () {
      request.get('/avatar', {
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
      // 是将回调函数延迟在下一次dom更新数据后调用
      this.$nextTick(() => {
        if (this.$refs['upload']) {
          this.$refs['upload'].clearFiles()  // 清除历史文件列表
        }
      })
    },
    searchByName () {
      this.load()
    },
    add () { // 打开添加弹框
      this.addDialogVisable = true
      this.form = { }
      if (this.$refs['upload']) {
        this.$refs['upload'].clearFiles() // 清除历史文件列表
      }
    },
    doAdd () {
      // 修改
      if (this.form.id) {
        request.put('/avatar', {
          id: this.form.id,
          name: this.form.name,
          url: this.form.url,
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
        request.post('/avatar', {
          name: this.form.name,
          createAt: this.form.createAt,
          url: this.form.url
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
      request.delete('/avatar/' + id).then(res => {
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
