package com.soft.web.user.dao;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: UserDataDAO层</p>
 * <p>Description: 增删改查操作接口</p>
 * <p>Copyright:Copyright (c) NDASEC</p>
 * <p>Company:六壬网安</p>
 */
public interface UserDataDao {

    /**
     * 通过主键集合批量删除
     * @param user_ids long[]
     * @return
     */
    public int batchDelete(long[] user_ids);

    /**
     * 统计
     * @param params Map<String,Object>
     * @return
     */
    public long count(Map<String,Object> params);

    /**
     * 通过主键删除
     * @param user_id int(11)
     * @return
     */
    public int deleteById(long user_id);

    /**
     * 通过主键编号获得map对象
     * @param user_id long
     * @return
     */
    public Map<String,Object> getMapById(long user_id);

    /**
     * 插入
     * @param params Map<String,Object>
     *  user_phone [ varchar(11) ] 用户手机号
     *  user_name [ varchar(20) ] 用户昵称
     *  user_head [ varchar(2000) ] 
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
     *  user_phone [ varchar(11) ] 用户手机号
     *  user_name [ varchar(20) ] 用户昵称
     *  user_head [ varchar(2000) ] 
     * @return int
     */
    public int update(Map<String,Object> params);
    /**
     * 更新头像
     */
    public int updateHead(Map<String,Object> params);

    List<Integer> queryPeopleList(Map<String, Object> rmap);

    int clearPeople(Map<String, Object> rmap);

    int insertPeople(Map<String, Object> itemmap);
}