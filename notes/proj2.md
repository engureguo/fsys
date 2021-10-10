

## 文件上传下载



**后端文件上传下载接口**



**前端图片上传和展示**

表格中的图片展示

```vue
<el-table-column label="头像">
    <template #default="scope">
		<el-image
          style="width: 100px; height: 100px"
          :src="scope.row.url"
          :preview-src-list="[scope.row.url]">
        </el-image>
    </template>
</el-table-column>
```

表单中上传头像

```vue
<el-form-item label="上传头像">
  <el-upload
    ref="upload"
    :action="filesUploadUrl"
    :on-success="filesUploadSuccess">
    <el-button size="small" type="primary">Click to upload</el-button>
  </el-upload>
</el-form-item>
```



注意：

**1. 在每次添加时清除上传组件中上次上传的文件**

```js
if (this.$refs['upload']) {	// 组件的 ref 属性找到组件
    this.$refs['upload'].clearFiles() // 清除历史文件列表
}
```

**2. 编辑时的图片重传**

```js
this.$nextTick(() => {
  if (this.$refs['upload']) {
    this.$refs['upload'].clearFiles()  // 清除历史文件列表
  }
})
```

关于 `vue.nextTick()` https://blog.csdn.net/zhouzuoluo/article/details/84752280

**将回调函数延迟在下一次dom更新数据后调用**，简单的理解是：**当数据更新了，在dom中渲染后，自动执行该函数**



- https://www.cnblogs.com/lixianguo/p/12518970.html

- https://gitee.com/engureguo/springboot-vue-demo/blob/master/springboot/src/main/java/com/example/demo/controller/FileController.java



## 富文本编辑框





