package com.soft.web.express.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.soft.web.express.dao.ExpressDao;

/**
 * <p>Title: Express业务层</p>
 * <p>Description: 增删改查业务</p>
 */
@Service
public class ExpressService {

    @Autowired
    private ExpressDao expressDao;

    /**
     * 添加
     * 添加方法备注
     * @param params
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public int add(Map<String,Object> params) throws Exception{
        return expressDao.insert(params);
    }

    /**
     * 批量删除
     * 通过一组主键进行批量删除
     * @param long[] express_ids
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public int batchDelete(long[] express_ids) throws Exception{
        return expressDao.batchDelete(express_ids);
    }

    /**
     * 统计
     * 查询分页统计方法
     * @param params
     * @return
     */
    public long count(Map<String,Object> params) {
        return expressDao.count(params);
    }

    /**
     * 通过主键删除对象
     * 通过单一主键进行删除
     * @param long
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public int deleteById(long express_id)throws Exception{
        return expressDao.deleteById(express_id);
    }

    /**
     * 获取对象
     * 通过主键获取map
     * @param string
     * @return
     */
    public Map<String,Object> getMapById(String express_id){
        return expressDao.getMapById(express_id);
    }

    /**
     * 插入
     * 添加方法备注
     * @param params
     * @return
     */
    public int insert(Map<String,Object> params) {
        return expressDao.insert(params);
    }

    /**
     * 查询列表
     * 查询列表方法
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryList(Map<String,Object> params){
        return expressDao.queryList(params);
    }

    /**
     * 查询分页列表
     * 查询分页列表方法
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryPageList(Map<String,Object> params){
        return expressDao.queryPageList(params);
    }

    /**
     * 更新
     * 编辑方法备注
     * @param params
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public int update(Map<String,Object> params)throws Exception{
        return expressDao.update(params);
    }

    public Map<String,Object> qrcode(Map<String, Object> params) {
        return expressDao.qrcode(params);
    }
}