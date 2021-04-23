package com.soft.web.people.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import club.codeapes.web.core.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import club.codeapes.web.core.controller.BaseController;
import com.soft.web.people.service.PeopleService;



/**
 * <p>Title: 收件人控制层</p>
 * <p>Description: 主要控制增删改查操作</p>
 */
@Controller
@RequestMapping("/people")
public class PeopleController extends BaseController{

    @Autowired
    private PeopleService peopleService;

    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> add(HttpServletRequest request)throws Exception {
        Map<String,Object> params = getParams(request);
        peopleService.add(params);
        params.put("success", true);
        params.put("msg", "操作成功");
        return params;
    }

    @RequestMapping(value = "/add")
    public String addPage(HttpServletRequest request,ModelMap params)throws Exception {
        resetModel(params,request);
        return "views/people/add";
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> delete(HttpServletRequest request, long id,String name)throws Exception {
        Map<String,Object> params = getParams(request);
        peopleService.deleteById(id);
        params.put("success", true);
        params.put("msg", "操作成功");
        return params;
    }

    @RequestMapping(value = "/detail")
    public String detailPage(HttpServletRequest request,ModelMap params,long id)throws Exception {
        params.put("detail", peopleService.getMapById(id));
        return "views/people/detail";
    }

    @RequestMapping(value = "/edit.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> edit(HttpServletRequest request)throws Exception {
        Map<String,Object> params = getParams(request);
        peopleService.update(params);
        params.put("success", true);
        params.put("msg", "操作成功");
        return params;
    }

    @RequestMapping(value = "/edit")
    public String editPage(HttpServletRequest request,ModelMap params,long id)throws Exception {
        params.put("detail", peopleService.getMapById(id));
        return "views/people/edit";
    }

    @RequestMapping(value = "/index")
    public String indexPage(HttpServletRequest request,ModelMap params)throws Exception {
        resetModel(params,request);
        return "views/people/index";
    }

    @RequestMapping(value = "/queryList.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> queryList(HttpServletRequest request)throws Exception {
        Map<String,Object> result = getParams(request);
        result.put("data", peopleService.queryList(result));
        result.put("success", true);
        return result;
    }

    @RequestMapping(value = "/queryPageList.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> queryPageList(HttpServletRequest request)throws Exception {
        Map<String,Object> result = getParams(request);
        result.put("start", Integer.valueOf(String.valueOf(result.get("start")))-1);
        Integer limit = Integer.valueOf(String.valueOf(result.get("limit")));
        result.put("start", Integer.valueOf(String.valueOf(result.get("start")))*limit);
        result.put("data", peopleService.queryPageList(result));
        result.put("code", 0);
        result.put("count", peopleService.count(result));
        result.put("success", true);
        return result;
    }

}