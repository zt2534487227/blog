package com.zt.blog.controller;

import com.zt.blog.common.entity.Result;
import com.zt.blog.common.util.DateUtil;
import com.zt.blog.common.util.FileUtil;
import com.zt.blog.model.Attach;
import com.zt.blog.service.AttachService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Locale;
import java.util.UUID;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/9/4
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static String rootPath="d:\\";

    @Autowired
    private AttachService attachService;


    /**
     * 文件上传
     * @return
     */
    @RequestMapping(value = "/upload",method =RequestMethod.POST)
    public Result upload(MultipartFile file) throws IOException {

        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        String today = DateUtil.getToday();

        //文件扩展名
        String ext = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase(Locale.ENGLISH);

        String localPath=rootPath+today.substring(0,6)+"\\"+today.substring(6)+"\\";
        File localFile = new File(localPath, UUID.randomUUID().toString());
        while (localFile.exists()){
            localFile = new File(localPath, UUID.randomUUID().toString());
        }
        file.transferTo(localFile);
        Attach attach=new Attach();


        return null;
    }


    /**
     * 文件下载
     * @return
     */
    @RequestMapping(value = "/{id}",method=RequestMethod.GET)
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
        Attach attach = attachService.selectById(id);
        if (null != attach){
            try (InputStream inputStream = new FileInputStream(new File(attach.getFilePath(),id+"."+attach.getFileType()))) {
                OutputStream outputStream = response.getOutputStream();
                response.setContentType("application/x-download");
                response.addHeader("Content-Disposition", "attachment;filename=" + attach.getFileName() +"."+attach.getFileType());
                FileUtil.copy(inputStream,outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        String dateTime = DateUtil.getToday();
        String localPath=rootPath+"\\"+dateTime.substring(0,6)+"\\"+dateTime.substring(6)+"\\";
        File localFile = new File("D:\\logs\\blog","blog.log");
        boolean exists = localFile.exists();
        System.out.println(exists);
        System.out.println(localPath);
        System.out.println(UUID.randomUUID());
    }



}
