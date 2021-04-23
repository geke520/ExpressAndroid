package com.soft.web.express.controller;


import club.codeapes.web.core.controller.BaseController;
import com.soft.web.express.service.ExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;



/**
 * <p>Title: 快递控制层</p>
 * <p>Description: 主要控制增删改查操作</p>
 */
@Controller
@RequestMapping("/express")
public class ExpressController extends BaseController{

    @Autowired
    private ExpressService expressService;

    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> add(HttpServletRequest request)throws Exception {
        Map<String,Object> params = getParams(request);
        params.put("express_id", UUID.randomUUID().toString().replace("-",""));
        expressService.add(params);
        params.put("success", true);
        params.put("msg", "操作成功");
        return params;
    }

    @RequestMapping(value = "/add")
    public String addPage(HttpServletRequest request,ModelMap params)throws Exception {
        resetModel(params,request);
        return "views/express/add";
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> delete(HttpServletRequest request, long id,String name)throws Exception {
        Map<String,Object> params = getParams(request);
        expressService.deleteById(id);
        params.put("success", true);
        params.put("msg", "操作成功");
        return params;
    }

    @RequestMapping(value = "/qrcode.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> qrcode(HttpServletRequest request)throws Exception {
        Map<String,Object> params = getParams(request);
        params.put("data", expressService.qrcode(params));
        params.put("success", true);
        params.put("msg", "操作成功");
        return params;
    }

    @RequestMapping(value = "/detail")
    public String detailPage(HttpServletRequest request,ModelMap params,String id)throws Exception {
        params.put("detail", expressService.getMapById(id));
        return "views/express/detail";
    }

    @RequestMapping(value = "/edit.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> edit(HttpServletRequest request)throws Exception {
        Map<String,Object> params = getParams(request);
        expressService.update(params);
        params.put("success", true);
        params.put("msg", "操作成功");
        return params;
    }

    @RequestMapping(value = "/edit")
    public String editPage(HttpServletRequest request,ModelMap params,String id)throws Exception {
        params.put("detail", expressService.getMapById(id));
        return "views/express/edit";
    }

    @RequestMapping(value = "/index")
    public String indexPage(HttpServletRequest request,ModelMap params)throws Exception {
        resetModel(params,request);
        return "views/express/index";
    }

    @RequestMapping(value = "/qrview")
    public String qrviewPage(HttpServletRequest request,ModelMap params)throws Exception {
        resetModel(params,request);
        return "views/express/qrview";
    }



    @RequestMapping(value = "/queryList.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> queryList(HttpServletRequest request)throws Exception {
        Map<String,Object> result = getParams(request);
        result.put("data", expressService.queryList(result));
        result.put("success", true);
        return result;
    }

    @RequestMapping(value = "/queryPageList.do", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> queryPageList(HttpServletRequest request)throws Exception {
        Map<String,Object> result = getParams(request);
        result.put("start", Integer.valueOf(String.valueOf(result.get("start")))-1);
        Integer limit = Integer.valueOf(String.valueOf(result.get("limit")));
        result.put("start", Integer.valueOf(String.valueOf(result.get("start")))*limit);
        result.put("data", expressService.queryPageList(result));
        result.put("code", 0);
        result.put("count", expressService.count(result));
        result.put("success", true);
        return result;
    }

}