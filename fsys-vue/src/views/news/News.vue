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
      <!-- change no.1 -->
        <el-table-column sortable prop="id" label="ID" />
        <el-table-column prop="title" label="词条"  />
        <el-table-column prop="createAt" label="创建时间"  />
        <el-table-column label="操作">
            <template #default="scope">
              <el-button size="mini" @click="show_details(scope.row)">详情</el-button>
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
      width="70%">
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="新闻标题">
          <el-input v-model="form.title" placeholder="请输入新闻标题"></el-input>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker type="date" format="YYYY-MM-DD" clearable v-model="form.createAt"></el-date-picker>
        </el-form-item>
        <el-form-item label="新闻内容">
          <!-- <el-input v-model="form.content" style="width: 90%" type="textarea" placeholder=""></el-input> -->
          <div id="div1" ></div>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addDialogVisable = false">取消</el-button>
          <el-button type="primary" @click="doAdd" >添加</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog
      v-model="showNewsDetailsDialog"
      title="新闻详情"
      width="70%"
      >
      <el-card>
          <div style="min-height: 100px" v-html="form.content"></div>
      </el-card>
    </el-dialog>

  </div>
</template>

<script>
import request from '@/util/request'
import E from 'wangeditor'
let editor = null;

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
      tableData: [],
      showNewsDetailsDialog: false, // 查看新闻详情
    }
  },
  created: function () {
    this.load()
  },
  methods: {
    load () {
      request.get('/news', {
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
    show_details (row) {
      // console.log(row)
      this.form = JSON.parse(JSON.stringify(row))
      this.showNewsDetailsDialog = true
    },
    handleEdit (row) {
      // 深拷贝，拷贝自表格中的数据
      this.form = JSON.parse(JSON.stringify(row))
      this.addDialogVisable = true
      // 富文本编辑器
      this.$nextTick(() => {
        if (!editor) {
          editor =  new E('#div1')
          editor.config.height = 150
          editor.create()
        }
        editor.txt.html(this.form.content)
      })
    },
    searchByName () {
      this.load()
    },
    add () { // 打开添加弹框
      this.form = { }
      this.addDialogVisable = true
      // 富文本编辑器
      this.$nextTick(() => {
        if (!editor) {
          editor =  new E('#div1')
          editor.config.height = 150
          editor.create()
        }
        editor.txt.html('')
      })
    },
    doAdd () {
      if (editor) {
        this.form.content = editor.txt.html()
      }
      // 修改
      if (this.form.id) {
        request.put('/news', this.form)
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
        request.post('/news', this.form)
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
      request.delete('/news/' + id).then(res => {
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
