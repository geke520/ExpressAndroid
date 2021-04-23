package com.soft.web.file.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.soft.web.user.service.UserDataService;

import club.codeapes.web.core.controller.BaseController;



@Controller
@RequestMapping("/file")
public class FileController extends BaseController{
    
	   @Autowired
	    private UserDataService userDataService;
	    
    
    @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> add(HttpServletRequest request,@RequestParam(value = "img", required = false) MultipartFile img)throws Exception {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	Map<String, Object> result = getParams(request);

		String log = null;// msg描述

		try {
			String iconPath = "";

			if (img != null) {
				String fileName = img.getOriginalFilename();
				String decPath = request.getSession().getServletContext().getRealPath("/assets/file");
				File newFolderCreate = new File(decPath);
				newFolderCreate.mkdirs();
				// 保存
				try {
					try {
						byte[] bytes;
						bytes = img.getBytes();
						FileOutputStream fos = new FileOutputStream(decPath + "/" + fileName);
						fos.write(bytes);
						fos.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					iconPath = fileName;
				} catch (Exception e) {
					e.printStackTrace();
					log = "文件处理有误";
					map.put("msg", log);
					map.put("success", false);
					return map;
				}
				result.put("user_head", "assets/file/"+iconPath);
				userDataService.updateHead(result);
				
				map.put("msg", "assets/file/"+iconPath);
				map.put("success", true);
				return map;
			}else{
				log = "文件为空";
			}
		} catch (Exception e) {
			log = "上传文件异常！";
		}
		map.put("msg", log);
		map.put("success", false);
		return map;
    }

}