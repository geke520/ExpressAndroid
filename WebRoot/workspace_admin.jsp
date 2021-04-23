<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String BASE = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	String PATH = request.getContextPath();
	String admin_name = request.getSession().getAttribute("admin_name")+"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>快递信息后台</title>
<link rel="stylesheet" href="assets/layui/css/layui.css">
<!-- <link rel="stylesheet" href="assets/css/workspace.css"> -->
<!-- <link rel="shortcut icon" href="assets/images/logo.ico" /> -->
<script src="assets/js/jquery-3.2.1.js"></script>
<script src="assets/js/md5.js"></script>
</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<div class="layui-logo">快递信息后台</div>
			<ul class="layui-nav layui-layout-right" lay-filter="nav-menu-top" >
				<li class="layui-nav-item"><a href="javascript:;">个人中心</a></li>
				<li class="layui-nav-item"><a href="javascript:;">注销</a></li>
			</ul>
		</div>

		<div class="layui-side layui-bg-black">
			<div class="layui-side-scroll">
				<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
				<ul class="layui-nav layui-nav-tree" lay-filter="nav-menu-left" id="nav-menu-left">
					<li class="layui-nav-item"><a href="javascript:;">收件人信息</a></li>
					<li class="layui-nav-item"><a href="javascript:;">快递信息</a></li>
					<li class="layui-nav-item"><a href="javascript:;">快递员</a></li>
				</ul>
			</div>
		</div>

		<div class="layui-body">
			<!-- 内容主体区域 -->
			<div style="padding: 15px;">
				<section id="container"></section>
			</div>
			
		</div>

		<div class="layui-footer">
			<!-- 底部固定区域 -->
			© 后台管理系统
		</div>
	</div>
<script src="assets/layui/layui.js"></script>
<script >
(function($) {
	var admin_name = '<%=admin_name%>';
	layui.use(['element','table','layer','form'], function(){
		var table = layui.table
		,element = layui.element
		,layer = layui.layer
		,form = layui.form;
		
		if(admin_name==undefined||admin_name=='null'||admin_name.length==0){
			window.location.href = '<%=PATH%>';
			return;
		}
		
		//监听右上角导航点击
		  element.on('nav(nav-menu-top)', function(elem){
			 var menu_text = elem.text().replace(/(^\s*)|(\s*$)/g, "");
		    switch (menu_text) {
			case '修改密码':
				layer.open({
					  type: 2, 
					  title: '修改密码',
					  area: ['500px', '280px'],
					  offset: '50px',
					  content: 'admin/edit',     //新的页面地址
					  end: function(index, layero){ 
						  table.reload('admin-table-id', {
								where: {isweb:1,admin_name_like:$('input[name="search_input_1"]').val()} //设定异步数据接口的额外参数
							});
							return false; 
						}
				});
				break;
			case '注销':
				layer.confirm('确定退出?', function(index){
					$.ajax({
						url : 'admin/logout.do',
						data : {},
						type : 'post',
						success : function(result) {
							layer.close(index);
							window.location.href= '<%=PATH%>';
						}
					});
					
				    });
				break;
			}
		    
		  });
		//监听左边导航点击
		  element.on('nav(nav-menu-left)', function(elem){
			  var menu_text = elem.text().replace(/(^\s*)|(\s*$)/g, "");
			    switch (menu_text) {
					case '收件人信息':
						initFirstFragment('people/index');
						break;
					case '快递信息':
						initFirstFragment('express/index');
						break;
					case '快递员':
						initFirstFragment('user/index');
						break;

				
				}
		  });
		
		// initFirstFragment('people/index');
		$('#nav-menu-left li:first')[0].getElementsByTagName('a')[0].click();
			
		function initFirstFragment(href) {
			var container = '#container';
			$.ajax({
				type : "GET",
				url : href,
				dataType : 'html',
				cache : true, // (warning: setting it to false will cause a timestamp and will call the request twice)
				beforeSend : function() {
				},
				success : function(data) {
					$(container).css({
						opacity : '0.0'
					}).html(data).delay(50).animate({
						opacity : '1.0'
					}, 300);
					/* $.parser.parse(container); */
				},
				error : function(xhr, status, thrownError, error) {
				},
				async : true
			});
		}
	});
})(window.jQuery);
</script>
</body>
</html>

