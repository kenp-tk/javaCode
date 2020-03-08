package com.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.e3mall.conmon.utils.FastDFSClient;
import com.e3mall.conmon.utils.JsonUtils;

/**
 *  图片上传的处理
 * 
 * */

@Controller
public class PictureController {
	
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	
	@RequestMapping(value="/pic/upload", produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {
		try {
			//将图片上传到图片服务器
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
			//获取文件扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String substring = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			//得到文件的地址和文件名字
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), substring);
			//补全url路径
			url = IMAGE_SERVER_URL + url;
			//返回到封装的Map中
			Map<Object, Object> result = new HashMap<Object, Object>();
			result.put("error", 0);
			result.put("url", url);
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Map<Object, Object> result = new HashMap<Object, Object>(); 
			result.put("error", 1);
			result.put("message", "图片上传失败");
			return JsonUtils.objectToJson(result);
		}
		
	}
	
}
