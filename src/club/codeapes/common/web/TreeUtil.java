package club.codeapes.common.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import club.codeapes.common.lang.ArrayAndMapUtil;

/**
 *	@Title:			树工具类
 *	@File:			TreeUtil.java 
 *	@Package 		club.codeapes.common.util.web
 *	@Description: 	
 *	@date 			2014年11月4日 下午5:44:38 
 *	@version 		V1.0
 */
public class TreeUtil {
	/**
	 * 使用之前需先定义个rootNode, WEB URL用
	 * @param idKey			编号关键字
	 * @param pidKey		父编号关键字
	 * @param childrenKey	子节点关键字
	 * @param rootNode		根节点
	 * @param allList		所有数据列表
	 * @throws Exception 
	 * 
	 *     public List<Map<String,Object>> queryTestList(Map<String,Object> params) throws Exception{
    	List<Map<String,Object>> list = dictionaryDataDao.queryTestList(params);
    	Map<String,Object> rootNode = list.get(0);
    	TreeUtil.listToTree(0, "url", "children", rootNode, list);
		return list;
	}
	 */
	public static void listToTree(int isRetainFullUrl, String columParam, String childrenKey,Map<String,Object> rootNode,List<Map<String,Object>> allList) throws Exception{
		allList.remove(rootNode);// 删除掉本身。
		List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();// 除去已被用过栏目的集合
		tempList.addAll(allList);
		List<Map<String,Object>> children = new ArrayList<Map<String,Object>>();// 本级子栏目
		
		
		for (Map<String,Object> node : allList) {
			String tempIdKey[] = rootNode.get(columParam).toString().split("(?<!/)/(?!/)");
			String tempIdKeyVal = tempIdKey[tempIdKey.length-1];
			
			String tempPIdKey[] = node.get(columParam).toString().split("(?<!/)/(?!/)");
			String tempPIdKeyVal = tempPIdKey[tempPIdKey.length-2];
			
			if(tempIdKeyVal.equals(tempPIdKeyVal)){
				if(isRetainFullUrl == 0){
					Map<String,Object> copyNode = ArrayAndMapUtil.copy(node);
					copyNode.put("short_url", node.get("url").toString().replace(rootNode.get("url").toString(), ""));
					children.add(copyNode);
					tempList.remove(node);
				}
			}
		}
		
		rootNode.put("leaf",true);
		if (children.size() == 0) {
			return;
		} else {
			rootNode.put(childrenKey, children);
			rootNode.put("leaf", false);
			rootNode.put("expanded", true);
			for (Map<String,Object> temp : children) {
				listToTree(0, columParam, childrenKey,temp, tempList);
			}
		}
	}
	/**
	 * 
	 * @Title: listToTree 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Type:Method
	 * @param idKey
	 * @param pidKey
	 * @param childrenKey
	 * @param rootNode
	 * @param allList
	 * @param depth		深度
	 */
	public static void listToTree(String idKey,String pidKey,String childrenKey,Map<String,Object> rootNode,List<Map<String,Object>> allList,int depth){
		if(depth==1){
			return ;
		}
		allList.remove(rootNode);// 删除掉本身。
		List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();// 除去已被用过栏目的集合
		tempList.addAll(allList);
		List<Map<String,Object>> children = new ArrayList<Map<String,Object>>();// 本级子栏目
		for (Map<String,Object> node : allList) {
			if (rootNode.get(idKey)!=null && node.get(pidKey) !=null && rootNode.get(idKey).toString().equals(node.get(pidKey).toString())) {
				children.add(node);
				tempList.remove(node);
			}
		}
		rootNode.put("leaf",true);
		if (children.size() == 0) {
			return;
		} else {
			rootNode.put(childrenKey, children);
			rootNode.put("leaf",false);
			rootNode.put("expanded",true);
			for (Map<String,Object> temp : children) {
				listToTree(idKey,pidKey,childrenKey,temp, tempList,depth-1);
			}
		}
	}
	/**
	 * 使用之前需先定义个rootNode
	 * List<Map<String,Object>> tree = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> rootNode = new HashMap<String,Object>();
		rootNode.put("resource_id", 0l);
		rootNode.put("resource_pid",-1l);
		rootNode.put("resource_name", "ROOT");
		rootNode.put("expand",true);
		rootNode.put("leaf",true);
		tree.add(rootNode);

		tree.addAll(resources);
		
		TreeUtil.listToTree("resource_id", "resource_pid", "children", rootNode, tree);
		tree.clear();
		tree.add(rootNode);
	 * 列表转换为tree
	 * @param idKey			编号关键字
	 * @param pidKey		父编号关键字
	 * @param childrenKey	子节点关键字
	 * @param rootNode		根节点
	 * @param allList		所有数据列表
	 */
	public static void listToTree(String idKey,String pidKey,String childrenKey,String openKey, Map<String,Object> rootNode,List<Map<String,Object>> allList){
		allList.remove(rootNode);// 删除掉本身。
		List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();// 除去已被用过栏目的集合
		tempList.addAll(allList);
		List<Map<String,Object>> children = new ArrayList<Map<String,Object>>();// 本级子栏目
		
		for (Map<String,Object> node : allList) {
			if (rootNode.get(idKey)!=null && node.get(pidKey) !=null && rootNode.get(idKey).toString().equals(node.get(pidKey).toString())) {
				children.add(node);
				tempList.remove(node);
			}
		}
		rootNode.put("leaf",true);
		if (children.size() == 0) {
			return;
		} else {
			rootNode.put(childrenKey, children);
			rootNode.put("leaf",false);
			rootNode.put(openKey,true);
			for (Map<String,Object> temp : children) {
				listToTree(idKey,pidKey,childrenKey,openKey,temp, tempList);
			}
		}
	}
	
	public static void listToTree(String idKey,String pidKey,String childrenKey,String openKey,String folderKey, Map<String,Object> rootNode,List<Map<String,Object>> allList){
		allList.remove(rootNode);// 删除掉本身。
		List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();// 除去已被用过栏目的集合
		tempList.addAll(allList);
		List<Map<String,Object>> children = new ArrayList<Map<String,Object>>();// 本级子栏目
		
		for (Map<String,Object> node : allList) {
			if (rootNode.get(idKey)!=null && node.get(pidKey) !=null && rootNode.get(idKey).toString().equals(node.get(pidKey).toString())) {
				children.add(node);
				tempList.remove(node);
			}
		}
		rootNode.put(folderKey,false);
		if (children.size() == 0) {
			return;
		} else {
			rootNode.put(childrenKey, children);
			rootNode.put(folderKey,true);
			rootNode.put(openKey,true);
			for (Map<String,Object> temp : children) {
				listToTree(idKey,pidKey,childrenKey,openKey,folderKey,temp, tempList);
			}
		}
	}
	
	/**
	 * 
	 * @Title: treeToList 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Type:Method
	 * @param list
	 * @param nodes
	 * @param childKey
	 */
	@SuppressWarnings("unchecked")
	public static void treeToList(List<Map<String,Object>> list,List<Map<String,Object>> nodes,String childKey){
		if(list == null){
			list = new ArrayList<Map<String,Object>>();
		}
		for(Map<String,Object> node : nodes){
			if(node.get(childKey)!=null){
				treeToList(list,(List<Map<String, Object>>) node.get(childKey),childKey);
			}
			node.remove(childKey);
			list.add(node);
		}
	}
}
