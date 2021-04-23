package com.soft.web.people.dao;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: PeopleDAO层</p>
 * <p>Description: 增删改查操作接口</p>
 */
public interface PeopleDao {

    /**
     * 通过主键集合批量删除
     * @param people_ids long[]
     * @return
     */
    public int batchDelete(long[] people_ids);

    /**
     * 统计
     * @param params Map<String,Object>
     * @return
     */
    public long count(Map<String,Object> params);

    /**
     * 通过主键删除
     * @param people_id int(11)
     * @return
     */
    public int deleteById(long people_id);

    /**
     * 通过主键编号获得map对象
     * @param people_id long
     * @return
     */
    public Map<String,Object> getMapById(long people_id);

    /**
     * 插入
     * @param params Map<String,Object>
     *  people_name [ varchar(255) ] 
     *  people_phone [ varchar(255) ] 
     *  people_address [ varchar(255) ] 
     * @return int
     */
    public int insert(Map<String,Object> params);

    /**
     * 查询列表
     * @param params Map<String,Object>
     * @return
     */
    public List<Map<String,Object>> queryList(Map<String,Object> params);

    /**
     * 获得分页列表
     * @param params Map<String,Object>
     *  *start long/int
     *  *limit long/int
     * @return
     */
    public List<Map<String,Object>> queryPageList(Map<String,Object> params);

    /**
     * 更新
     * @param params Map<String,Object>
     *  people_name [ varchar(255) ] 
     *  people_phone [ varchar(255) ] 
     *  people_address [ varchar(255) ] 
     * @return int
     */
    public int update(Map<String,Object> params);

}