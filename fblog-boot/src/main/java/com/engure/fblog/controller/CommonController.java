package com.engure.fblog.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.engure.fblog.vo.RespConstants;
import com.engure.fblog.vo.RespVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/common")
public class CommonController {

    @GetMapping
    public RespVO test() {
        return RespVO.success("没得问题~");
    }

    /**
     * 上传文件接口
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public RespVO uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        if (file == null) return RespVO.error(RespConstants.UPLOAD_FAIL);

        //String originalFilename = file.getOriginalFilename(); 上传的文件名

        // hutool：通过读取文件的首部几个二进制位来判断常用的文件类型
        String type = FileTypeUtil.getType(file.getInputStream());

        String fileName;

        if (type == null) {
            // 文件无类型
            fileName = IdUtil.fastSimpleUUID();
        } else {
            // 同时上传文件名可能冲突，鸵鸟策略~
            fileName = IdUtil.fastSimpleUUID() + "." + type;
        }

        // 拼接路径
        String pathname = System.getProperty("user.dir") + "/files/" + fileName;

        File dest = new File(pathname);

        file.transferTo(dest);

        String url = "/common/file/" + fileName;

        return RespVO.success("OK", url);
    }

    /**
     * 下载文件接口。
     *
     * @param flag     上传文件时服务端给的文件名
     * @param response
     */
    @GetMapping("/file/{flag}")
    public void getFile(@PathVariable String flag,
                        HttpServletResponse response) {

        OutputStream os;
        String basePath = System.getProperty("user.dir") + "/files/";
        List<String> fileNames = FileUtil.listFileNames(basePath);
        String fileName = fileNames.stream().filter(name -> name.equals(flag)).findAny().orElse("");

        try {

            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }

        } catch (Exception e) {
            System.out.println("文件下载失败。。。");
        }

    }


}
