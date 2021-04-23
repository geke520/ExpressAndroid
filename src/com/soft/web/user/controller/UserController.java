package com.soft.web.user.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.soft.web.people.service.PeopleService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.soft.web.user.service.UserDataService;
import com.soft.web.user.service.UserPwdService;

import club.codeapes.common.date.DateUtil;
import club.codeapes.web.core.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import net.sf.ehcache.search.impl.BaseResult;



/**
 * <p>Title: 用户信息控制层</p>
 * <p>Description: 主要控制增删改查操作</p>
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserDataService userDataService;
    
    @Autowired
    private UserPwdService userPwdService;

	@Autowired
	private PeopleService peopleService;



	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(HttpServletRequest request) throws Exception {
		Map<String, Object> params = getParams(request);
		System.out.println("快递APP有用户注册:"+JSON.toJSONString(params)+" 时间："+DateUtil.getNow());
		Object name = params.get("user_name");
		params.put("user_name", null);
		if (userDataService.count(params) > 0) {
			params.put("success", false);
			params.put("msg", "手机号已被注册");
			return params;
		}
		params.put("user_name", name);
		userDataService.add(params);
		userPwdService.add(params);
		System.out.println(DateUtil.getNow()+" 有用户注册，信息:  "+JSON.toJSONString(params));
		params.put("success", true);
		params.put("msg", "注册成功");
		return params;
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> login(HttpServletRequest request) throws Exception {
		Map<String, Object> result = getParams(request);
		System.out.println("快递APP有用户登录:"+JSON.toJSONString(result)+" 时间："+DateUtil.getNow());
		if (userDataService.queryList(result).size()>0) {
			result.putAll(userDataService.queryList(result).get(0));
			if (userPwdService.count(result) > 0) {
				result.put("success", true);
				result.put("data",userDataService.queryList(result).get(0).get("user_id"));
			}else{
				result.put("success", false);
				result.put("msg", "用户不存在或密码错误");
			}
			
		} else {
			result.put("success", false);
			result.put("msg", "手机号或密码错误");
		}
		return result;
	}
	
	@RequestMapping(value = "/get.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> get(HttpServletRequest request) throws Exception {
		Map<String, Object> result = getParams(request);
		result.put("success", true);
		result.put("data", userDataService.getMapById(Long.valueOf(String.valueOf(result.get("user_id")))));
		return result;
	}
	
	@RequestMapping(value = "/changepwd.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> changepwd(HttpServletRequest request) throws Exception {
		Map<String, Object> params = getParams(request);
		int num = userPwdService.update(params);
		if (num>0) {
			params.put("success", true);
			params.put("msg", "密码修改成功");
		}else{
			params.put("success", false);
			params.put("msg", "原密码错误");
		}
		
		return params;
	}


    @RequestMapping(value = "/edit.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> edit(HttpServletRequest request)throws Exception {
        Map<String,Object> params = getParams(request);
		userDataService.update(params);
        params.put("success", true);
        params.put("msg", "操作成功");
        return params;
    }

    @RequestMapping(value = "/setPeopleList.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> setPeopleList(HttpServletRequest request,long user_id,String peopleList)throws Exception {
        Map<String,Object> params = getParams(request);

		Map<String,Object> rmap = new HashMap<String,Object>();
		rmap.put("user_id",user_id);
		userDataService.clearPeople(rmap);
		List<Integer> idss = JSON.parseArray(peopleList,Integer.class);
		for (Integer peopleId: idss){
			Map<String,Object> itemmap = new HashMap<String,Object>();
			itemmap.put("user_id",user_id);
			itemmap.put("people_id",peopleId);
			userDataService.insertPeople(itemmap);
		}
        params.put("success", true);
        params.put("msg", "操作成功");
        return params;
    }




	@RequestMapping(value = "/index")
	public String indexPage(HttpServletRequest request,ModelMap params)throws Exception {
		resetModel(params,request);
		return "views/user/index";
	}

	@RequestMapping(value = "/editPeople")
	public String editPeoplePage(HttpServletRequest request,ModelMap params,long user_id)throws Exception {
		Map<String,Object> rmap = new HashMap<String,Object>();
		rmap.put("user_id",user_id);
		params.put("user_id",user_id);
		params.put("select", JSON.toJSONString(userDataService.queryPeopleList(rmap)));
		params.put("option", JSON.toJSONString(peopleService.queryList(new HashMap<>())));
		resetModel(params,request);
		return "views/user/editPeople";
	}
    

    @RequestMapping(value = "/queryList.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> queryList(HttpServletRequest request)throws Exception {
        Map<String,Object> result = getParams(request);
        result.put("data", userDataService.queryList(result));
        result.put("success", true);
        return result;
    }

    @RequestMapping(value = "/queryPageList.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> queryPageList(HttpServletRequest request)throws Exception {
        Map<String,Object> result = getParams(request);
		result.put("start", Integer.valueOf(String.valueOf(result.get("start")))-1);
		Integer limit = Integer.valueOf(String.valueOf(result.get("limit")));
		result.put("start", Integer.valueOf(String.valueOf(result.get("start")))*limit);
		result.put("data", userDataService.queryPageList(result));
		result.put("code", 0);
		result.put("count", userDataService.count(result));
        result.put("success", true);
        return result;
    }
    
    /**
	 * 上传图片
	 */
	@ApiOperation(value = "上传图片", notes = "上传图片", httpMethod = "POST", response = BaseResult.class)
	@RequestMapping(value = "/uploadImg.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadReportIcon(HttpServletRequest request,
			@RequestParam(value = "report_icon", required = false) MultipartFile report_icon) {
		Map<String, Object> map = new HashMap<String, Object>();

		String log = null;// msg描述

		try {
			String iconPath = "";

			if (report_icon != null) {
				String fileName = report_icon.getOriginalFilename();
				if (!(fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith("jpeg"))) {
					log = "图片格式有误！";
					map.put("msg", log);
					map.put("success", false);
					return map;
				}
				String decPath = request.getSession().getServletContext().getRealPath("/assets/head");
				File newFolderCreate = new File(decPath);
				newFolderCreate.mkdirs();
				// 保存
				try {
					fileName = UUID.randomUUID().toString() + ".jpg";
					try {
						byte[] bytes;
						bytes = report_icon.getBytes();
						FileOutputStream fos = new FileOutputStream(decPath + "/" + fileName);
						fos.write(bytes);
						fos.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
						iconPath = "assets/report/styleicon/dec/" + fileName;
				} catch (Exception e) {
					e.printStackTrace();
					log = "文件处理有误";
					map.put("msg", log);
					map.put("success", false);
					return map;
				}
				log = "上传图片成功！";
				map.put("data", iconPath);
				map.put("success", true);
				return map;
			}else{
				log = "文件为空";
			}
		} catch (Exception e) {
			log = "上传图片异常！";
		}
		map.put("msg", log);
		map.put("success", false);
		return map;
	}

}