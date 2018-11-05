package com.zt.blog.controller;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zt.blog.common.constant.Constants;
import com.zt.blog.common.entity.Result;
import com.zt.blog.common.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/9/28
 */
@RestController
@RequestMapping("/image")
@Api(description = "image 相关api")
public class ImageController {

    //文件分隔符 windows下是'\\'  linux下是'/'
    private static final String separator=File.separator;

    private static final Set<String> imageTypes=Sets.newHashSet("jpg","jpeg","jpe","png","gif","tif","bmp");

    @Value("${image.rootPath}")
    private String rootPath;

    /**
     * 文件上传
     * @return
     */
    @ApiOperation(value = "上传图片")
    @RequestMapping(value = "/upload",method =RequestMethod.POST)
    public Result upload(MultipartFile file) throws IOException {
        Result<Map<String,Object>> result=new Result<>(true,Constants.Status.SUCCESS);
        String originalFilename = file.getOriginalFilename();
        String fileName=originalFilename.substring(0,originalFilename.lastIndexOf("."));
        String ext = FilenameUtils.getExtension(originalFilename).toLowerCase(Locale.ENGLISH);
        if (StringUtils.isEmpty(ext) || !imageTypes.contains(ext)){
            return new Result(false,Constants.Status.BUSINESS_ERROR.getCode(),"图片格式错误");
        }
        String today = DateUtil.getToday();
        String localPath=rootPath+separator+today.substring(0,6)+separator+today.substring(6);
        //判断文件夹是否存在,不存在就创建(判断是否是多级目录)
        File localDirectory=new File(localPath);
        if (!localDirectory.exists()){
            localDirectory.mkdirs();
        }
        //判断文件是否存在，不存在就创建，存在就重新命名
        String uuid=UUID.randomUUID().toString().replaceAll("-","");
        File localFile = new File(localPath,uuid+"."+ext);
        while (localFile.exists()){
            uuid=UUID.randomUUID().toString().replaceAll("-","");
            localFile = new File(localPath,uuid+"."+ext);
        }
        file.transferTo(localFile);
        String filePath=String.valueOf(localPath+separator+uuid+"."+ext).replace(rootPath+separator,"");
        Map<String,Object> map=Maps.newHashMap();
        map.put("fileName",fileName);
        map.put("filePath",filePath);
        result.setData(map);
        return result;
    }



}
