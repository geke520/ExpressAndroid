package com.soft.web.admin.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.soft.web.admin.service.AdminService;

import club.codeapes.web.core.controller.BaseController;



/**
 * <p>Title: 管理员控制层</p>
 * <p>Description: 主要控制增删改查操作</p>
 * 
 *
 */
@Controller
@SessionAttributes("admin_name")
@RequestMapping("/admin")
public class AdminController extends BaseController{

    @Autowired
    private AdminService adminService;
    
    
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> login(HttpServletRequest request,ModelMap model) throws Exception {
		Map<String, Object> result = getParams(request);
		if (adminService.count(result)>0) {
			model.addAttribute("admin_name",String.valueOf(result.get("admin_name"))); //②向ModelMap中添加一个属性  
			result.put("success", true);
			result.put("msg", "登录成功");	
		} else {
			result.put("success", false);
			result.put("msg", "管理员不存在或密码错误");
		}
		return result;
	}
    @RequestMapping(value = "/logout.do", method = RequestMethod.POST)
   	public @ResponseBody Map<String, Object> logout(HttpServletRequest request,ModelMap model) throws Exception {
   		Map<String, Object> result = getParams(request);
   		model.addAttribute("admin_name","");
			result.put("success", true);
			result.put("msg", "注销成功");	
   		return result;
   	}

    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> add(HttpServletRequest request)throws Exception {
        Map<String,Object> params = getParams(request);
        String pwd = String.valueOf(params.get("admin_password"));
        params.put("admin_issystem", null);
        params.put("admin_password", null);
        if(adminService.count(params)>0){
            params.put("success", false);
            params.put("msg", "名称已存在");
        }else{
        	 params.put("admin_issystem", 0);
        	 params.put("admin_password", pwd);
        	adminService.add(params);
            params.put("success", true);
            params.put("msg", "操作成功");
        }
        return params;
    }

    @RequestMapping(value = "/add")
    public String addPage(HttpServletRequest request,ModelMap params)throws Exception {
        resetModel(params,request);
        return "views/admin/add";
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> delete(HttpServletRequest request, String id)throws Exception {
        Map<String,Object> params = getParams(request);
        adminService.deleteById(id);
        params.put("success", true);
        params.put("msg", "操作成功");
        return params;
    }

    @RequestMapping(value = "/detail")
    public String detailPage(HttpServletRequest request,ModelMap params,String id)throws Exception {
        params.put("detail", adminService.getMapById(id));
        return "views/admin/detail";
    }

    @RequestMapping(value = "/edit.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> edit(HttpServletRequest request,@ModelAttribute("admin_name") String admin_name2)throws Exception {
        Map<String,Object> params = getParams(request);
        if (params.get("admin_name")==null) {
        	params.put("admin_name", admin_name2);
		}
        adminService.update(params);
        params.put("success", true);
        params.put("msg", "操作成功");
        return params;
    }
    
    @RequestMapping(value = "/changePwd.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> changePwd(HttpServletRequest request)throws Exception {
        Map<String,Object> params = getParams(request);
        adminService.update(params);
        params.put("success", true);
        params.put("msg", "操作成功");
        return params;
    }

    @RequestMapping(value = "/edit")
    public String editPage(HttpServletRequest request,ModelMap params,String id,@ModelAttribute("admin_name") String admin_name2)throws Exception {
    	if (id==null) {
			id = admin_name2;
		}
        params.put("detail", adminService.getMapById(id));
        return "views/admin/edit";
    }

    @RequestMapping(value = "/index")
    public String indexPage(HttpServletRequest request,ModelMap params)throws Exception {
        resetModel(params,request);
        return "views/admin/index";
    }

    @RequestMapping(value = "/queryList.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> queryList(HttpServletRequest request)throws Exception {
        Map<String,Object> result = getParams(request);
        List<Map<String, Object>> datalist = adminService.queryList(result);
        result.put("data", datalist);
        result.put("success", true);
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", adminService.count(result));
        return result;
    }

    @RequestMapping(value = "/queryPageList.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> queryPageList(HttpServletRequest request)throws Exception {
        Map<String,Object> result = getParams(request);
        if (result.get("isweb")!=null&&result.get("isweb").toString().trim().equals("1")) {
        	result.put("start", Integer.valueOf(String.valueOf(result.get("start")))-1);
        	Integer limit = Integer.valueOf(String.valueOf(result.get("limit")));
        	result.put("start", Integer.valueOf(String.valueOf(result.get("start")))*limit);
		}
        List<Map<String, Object>> datalist = adminService.queryPageList(result);
        result.put("data", datalist);
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", adminService.count(result));
        result.put("recordsTotal", adminService.count(result));
        result.put("recordsFiltered",Integer.parseInt(result.get("recordsTotal").toString()));
        result.put("success", true);
        return result;
    }

}