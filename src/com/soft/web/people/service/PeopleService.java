package com.soft.web.people.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.soft.web.people.dao.PeopleDao;

/**
 * <p>Title: People业务层</p>
 * <p>Description: 增删改查业务</p>
 */
@Service
public class PeopleService {

    @Autowired
    private PeopleDao peopleDao;

    /**
     * 添加
     * 添加方法备注
     * @param params
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public int add(Map<String,Object> params) throws Exception{
        return peopleDao.insert(params);
    }

    /**
     * 批量删除
     * 通过一组主键进行批量删除
     * @param long[] people_ids
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public int batchDelete(long[] people_ids) throws Exception{
        return peopleDao.batchDelete(people_ids);
    }

    /**
     * 统计
     * 查询分页统计方法
     * @param params
     * @return
     */
    public long count(Map<String,Object> params) {
        return peopleDao.count(params);
    }

    /**
     * 通过主键删除对象
     * 通过单一主键进行删除
     * @param long
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public int deleteById(long people_id)throws Exception{
        return peopleDao.deleteById(people_id);
    }

    /**
     * 获取对象
     * 通过主键获取map
     * @param long
     * @return
     */
    public Map<String,Object> getMapById(long people_id){
        return peopleDao.getMapById(people_id);
    }

    /**
     * 插入
     * 添加方法备注
     * @param params
     * @return
     */
    public int insert(Map<String,Object> params) {
        return peopleDao.insert(params);
    }

    /**
     * 查询列表
     * 查询列表方法
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryList(Map<String,Object> params){
        return peopleDao.queryList(params);
    }

    /**
     * 查询分页列表
     * 查询分页列表方法
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryPageList(Map<String,Object> params){
        return peopleDao.queryPageList(params);
    }

    /**
     * 更新
     * 编辑方法备注
     * @param params
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public int update(Map<String,Object> params)throws Exception{
        return peopleDao.update(params);
    }

}