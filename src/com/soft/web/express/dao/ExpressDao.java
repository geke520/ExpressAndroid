package com.soft.web.express.dao;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: ExpressDAO层</p>
 * <p>Description: 增删改查操作接口</p>
 */
public interface ExpressDao {

    /**
     * 通过主键集合批量删除
     * @param express_ids long[]
     * @return
     */
    public int batchDelete(long[] express_ids);

    /**
     * 统计
     * @param params Map<String,Object>
     * @return
     */
    public long count(Map<String,Object> params);

    /**
     * 通过主键删除
     * @param express_id int(11)
     * @return
     */
    public int deleteById(long express_id);

    /**
     * 通过主键编号获得map对象
     * @param express_id long
     * @return
     */
    public Map<String,Object> getMapById(String express_id);

    /**
     * 插入
     * @param params Map<String,Object>
     *  people_id [ int(11) ] 
     *  express_start_datetime [ varchar(20) ] 
     *  express_status [ int(5) ] 0待揽件，1运输中，2派送中，3已完成
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
     *  people_id [ int(11) ] 
     *  express_start_datetime [ varchar(20) ] 
     *  express_status [ int(5) ] 0待揽件，1运输中，2派送中，3已完成
     * @return int
     */
    public int update(Map<String,Object> params);

    Map<String, Object> qrcode(Map<String, Object> params);
}