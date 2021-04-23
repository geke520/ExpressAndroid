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
			      <input type="text" name="search_input_1"  lay-verify="required" placeholder="请输入姓名检索" autocomplete="off" class="layui-input">
			    </div>
		</div>
    </div>
    <div class="layui-col-md3">
	     <button class="layui-btn btn-user-search" style="margin-top: 20px;margin-right: 5px;margin-bottom: 10px;margin-right: 20px;">
		   <i class="layui-icon">&#xe615;</i> 搜索
	     </button>
    </div>
  </div>
 
<div id="user-table-div">
	<table id="user-table" lay-filter="user-filter" lay-skin="line"></table>
</div>
<script type="text/html" id="common-table-bar">
  <a class="layui-btn layui-btn-sm" lay-event="edit">配置权限</a>
  <a class="layui-btn layui-btn-danger layui-btn-sm" lay-event="del">删除</a>
</script>
<script >
(function($) {
//收件人数据列表配置项
var user_option = {
		  elem: '#user-table' //指定原始表格元素选择器（推荐id选择器）
			  ,id:'user-table-id'
			  ,url: 'user/queryPageList.do'
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
			            {field: 'user_name', title: '快递员姓名', width: 180,sort:true}
			            ,{field: 'user_phone', title: '手机号码', sort:true}
			            ,{fixed: 'right',  title: '操作',width:180, align:'center', toolbar: '#common-table-bar'} //这里的toolbar值是模板元素的选择器
			          ]] //设置表头
			};

//主方法
layui.use(['element','table','layer','form'], function(){
	
		var table = layui.table
		,layer = layui.layer
		,form = layui.form;



		/* 收件人列表 */
		table.render(user_option);
		
		//收件人工具栏
		table.on('tool(user-filter)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
				  var data = obj.data; //获得当前行数据
				  var layEvent = obj.event; //获得 lay-event 对应的值
				  var tr = obj.tr; //获得当前行 tr 的DOM对象
				 
				  if(layEvent === 'del'){ //删除
				    layer.confirm('确定删除?', function(index){
				      $.ajax({
							url : 'user/delete.do',
							data : {
								id:data.user_id
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
				  }else if(layEvent === 'edit'){
					  layer.open({
						  type: 2,
						  title: '配置可见联系人',
						  area: ['600px', '340px'],
						  offset: '10px',
						  scrollbar: true,
						  maxmin:true,
						  content: 'user/editPeople?user_id='+data.user_id,     //新的页面地址
						  end: function(index, layero){
							  return false;
						  }
					  });
				  }
			});
		
		
		//搜索数据
		$('.btn-user-search').click(function(){
			table.reload('user-table-id', {
				  where: {isweb:1,user_name_like:$('input[name="search_input_1"]').val()} //设定异步数据接口的额外参数
				});
		});

	});

})(window.jQuery);
</script>
</body>

