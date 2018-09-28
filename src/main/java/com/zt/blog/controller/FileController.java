package com.zt.blog.controller;

import com.zt.blog.common.constant.StatusCode;
import com.zt.blog.common.entity.Result;
import com.zt.blog.common.util.DateUtil;
import com.zt.blog.common.util.FileUtil;
import com.zt.blog.model.Attach;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

/**
 * @Author: ZhouTian
 * @Description:  文件上传 根目录 /upload/{userId}/201809/17/
 * @Date: 2018/9/4
 */
@RestController
@RequestMapping("/file")
@Api(description = "文件相关api")
public class FileController {

    private static final Logger log=LoggerFactory.getLogger(FileController.class);

    //文件分隔符 windows下是'\\'  linux下是'/'
    private static final String separator=File.separator;

    @Value("${file.rootPath}")
    private String rootPath;


    /**
     * 文件上传
     * @return
     */
    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "/upload",method =RequestMethod.POST)
    public Result upload(MultipartFile file) throws IOException {
        Result<String> result=new Result<>(true,StatusCode.Status.SUCCESS);
        String originalFilename = file.getOriginalFilename();
        String fileName=originalFilename.substring(0,originalFilename.lastIndexOf("."));
        long fileSize = file.getSize();
        String today = DateUtil.getToday();
        //文件扩展名
        String ext = FilenameUtils.getExtension(originalFilename).toLowerCase(Locale.ENGLISH);
        String localPath=rootPath+separator+today.substring(0,6)+separator+today.substring(6);
        //判断文件夹是否存在,不存在就创建(判断是否是多级目录)
        File localDirectory=new File(localPath);
        if (!localDirectory.exists()){
            localDirectory.mkdirs();
        }
        //判断文件是否存在，不存在就创建，存在就重新命名
        String uuid=UUID.randomUUID().toString().replaceAll("-","");
        File localFile = new File(localPath,uuid);
        while (localFile.exists()){
            uuid=UUID.randomUUID().toString().replaceAll("-","");
            localFile = new File(localPath,uuid);
        }
        file.transferTo(localFile);
        Attach attach=new Attach();
        attach.setId(uuid);
        attach.setFileName(fileName);
        attach.setFilePath(localPath+separator+uuid);
        attach.setFileSize(new BigDecimal(fileSize));
        attach.setFileType(ext);
        attach.insert();
        result.setData(uuid);
        return result;
    }


    /**
     * 文件下载
     * @return
     */
    @ApiOperation(value = "下载文件")
    @ApiImplicitParam(name = "id",required = true,paramType = "path")
    @RequestMapping(value = "/{id}",method=RequestMethod.GET)
    public void download(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response){
        Attach attach = new Attach();
        attach=attach.selectById(id);
        if (null != attach){
            try (InputStream inputStream = new FileInputStream(new File(attach.getFilePath()))) {
                OutputStream outputStream = response.getOutputStream();
                response.setContentType("application/x-download");
                response.addHeader("Content-Disposition", "attachment;filename=" + attach.getFileName().trim()+"."+attach.getFileType());
                FileUtil.copy(inputStream,outputStream);
            } catch (Exception e) {
                log.error("file download error",e);
            }
        }
    }


}
