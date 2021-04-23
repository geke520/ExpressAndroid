<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String BASE = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	String PATH = request.getContextPath();
	String SPATH = PATH + "/static";
	String LPATH = SPATH + "/lib";
	String TPATH = SPATH + "/theme";
	String TDPATH = TPATH + "/default";
%>
<body >
<div class="layui-row">
      <div class="layui-col-md3">
			  <div class="layui-form-item" style="margin-top: 20px;margin-bottom: 10px;">
			    <div class="layui-input-block" style="margin-left: 0px;">
			      <input type="text" name="search_input_1"  lay-verify="required" placeholder="请输入关键字" autocomplete="off" class="layui-input">
			    </div>
		</div>
    </div>
    <div class="layui-col-md3">
	     <button class="layui-btn btn-express-search" style="margin-top: 20px;margin-right: 5px;margin-bottom: 10px;margin-right: 20px;">
		   <i class="layui-icon">&#xe615;</i> 搜索
	     </button>
	     <button class="layui-btn btn-data-add" style="margin-top: 20px;margin-right: 20px;margin-bottom: 10px;">
		   <i class="layui-icon">&#xe608;</i> 发快递
	     </button>
    </div>
  </div>
 
<div id="express-table-div">
	<table id="express-table" lay-filter="express-filter" lay-skin="line"></table>
</div>
<script type="text/html" id="common-table-bar">
<%--	lay-event监听事件--%>
  <a class="layui-btn layui-btn-sm" lay-event="see">二维码</a>
  <a class="layui-btn layui-btn-sm" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-sm" lay-event="del">删除</a>
</script>
<script >
function showimgdialog(img) {
	var imgdata = {
			  "title": "", //相册标题
			  "id": 123, //相册id
			  "start": 0, //初始显示的图片序号，默认0
			  "data": [   //相册包含的图片，数组格式
			    {
			      "alt": "照片",
			      "pid": 0, //图片id
			      "src": img, //原图地址
			      "thumb": img //缩略图地址
			    }
			  ]
			};
	layer.photos({
	    photos: imgdata
	    ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
	  });
}
(function($) {

//快递数据列表配置项
var express_option = {
		  elem: '#express-table' //指定原始表格元素选择器（推荐id选择器）
			  ,id:'express-table-id'
			  ,url: 'express/queryPageList.do'
			  ,even: true //开启隔行背景
			  ,method: 'post'
			  ,page: true
			  ,limits: [15,30,45,60,100]
			  ,limit: 15 //默认采用60
			  ,loading: true
			  ,request: {
				  pageName: 'start' //页码的参数标题，默认：page
				  ,limitName: 'limit' //每页数据量的参数名，默认：limit
				} 
			  ,where:{
					isweb:1
				}
			  ,cols: [[ //标题栏
			            {field: 'people_name', title: '姓名', width: 160,sort:true}
			            ,{field: 'people_phone', title: '电话', width: 160,sort:true}
			            ,{field: 'people_address', title: '收货地址', sort:true}
			            ,{field: 'express_price', title: '快递费用', width: 120,sort:true}
			            ,{field: 'express_status', title: '快递状态', width: 80,sort:true,templet: function(d){
			            	if(d.express_status==0){
			            		return '待揽件';
							}else if(d.express_status==1){
								return '运输中';
							}else if(d.express_status==2){
								return '派送中';
							}else if(d.express_status==3){
								return '已完成';
							}
			            }}
			            ,{field: 'express_start_datetime', title: '快递时间', width: 160,sort:true}
			            ,{fixed: 'right',  title: '操作',width:240, align:'center', toolbar: '#common-table-bar'} //这里的toolbar值是模板元素的选择器
			          ]] //设置表头
			};

//主方法
layui.use(['element','table','layer','form'], function(){
	
		var table = layui.table
		,layer = layui.layer
		,form = layui.form;
		
		/* 快递列表 */
		table.render(express_option);
		
		//快递工具栏
		table.on('tool(express-filter)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
				  var data = obj.data; //获得当前行数据
				  var layEvent = obj.event; //获得 lay-event 对应的值
				  var tr = obj.tr; //获得当前行 tr 的DOM对象
				 
				  if(layEvent === 'del'){ //删除
				    layer.confirm('确定删除?', function(index){
				      $.ajax({
							url : 'express/delete.do',
							data : {
								id:data.express_id
							},
							type : 'post',
							success : function(result) {
								if(result.success){
									 layer.msg(result.msg||'删除成功');
									 obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
								}else{
									 layer.msg(result.msg||'删除失败');
								}
							}
						});
				      layer.close(index);
				    });
				  } else if(layEvent === 'edit'){ //编辑
					  layer.open({
						  type: 2, 
						  title: '快递编辑',
						  area: ['600px', '580px'],
						  offset: '10px',
						  scrollbar: true,
						  maxmin:true,
						  content: 'express/edit?id='+data.express_id,     //新的页面地址
						  end: function(index, layero){ 
							  table.reload('express-table-id', {
								  where: {isweb:1,key_like:$('input[name="search_input_1"]').val()} //设定异步数据接口的额外参数
								});
								return false; 
							}
					});
				  } else if(layEvent === 'see'){ //查看二维码
					  layer.open({
						  type: 2,
						  title: '二维码',
						  area: ['300px', '300px'],
						  scrollbar: true,
						  maxmin:true,
						  //通过express_id访问快递信息
						  content: 'express/qrview?expressId='+data.express_id
					  });
				  }
			});
		
		
		//搜索数据
		$('.btn-express-search').click(function(){
			table.reload('express-table-id', {
				  where: {isweb:1,key_like:$('input[name="search_input_1"]').val()} //设定异步数据接口的额外参数
				});
		});
		
		//发布按钮事件
		$('.btn-data-add').click(function(){
			layer.open({
				  type: 2, 
				  title: '发快递',
				  area: ['600px', '580px'],
				  offset: '10px',
				  scrollbar: true,
				  maxmin:true,
				  content: 'express/add',     //新的页面发布
				  end: function(index, layero){ 
						  table.reload('express-table-id', {
							  where: {isweb:1,key_like:$('input[name="search_input_1"]').val()} //设定异步数据接口的额外参数
							});
						  return false; 
						}    
				});
		});
		
	});

})(window.jQuery);
</script>
</body>

