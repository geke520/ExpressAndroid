<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>快递信息码管理平台</title>
 
 <link rel="shortcut icon" href="assets/images/logo.ico" />
 <link rel="stylesheet" href="assets/layui/css/layui.css">
 <script src="assets/js/jquery-3.2.1.js"></script>
 <style type="text/css">
 
 .login-container {
    background: url(assets/images/login_bg.jpg) no-repeat center;
    background-size: cover;
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
}

 </style>
</head>
<body class="layui-anim layui-anim-scaleSpring login-container">

<div align="center">
		<div id="main-body" align="left" style="margin-top: 200px">
			<div align="center" style="width: 100%; margin-top: 30px;">
			
				<div style="width: 300px;" class="layui-anim layui-anim-up">
					<h2 style="margin-bottom: 30px;color: white;font-size:  24px;">快递信息码管理平台</h2>
					<form class="layui-form" action="" id="login-form">
						<div class="layui-form-item" >
							<div class="layui-input-block" style="margin-left: 0px;">
								<input type="text"  name="admin_name" required
									lay-verify="required" placeholder="请输入管理员账号" autocomplete="off" value="admin"
									class="layui-input">
							</div>
							<!-- <div class="layui-form-mid layui-word-aux"></div> -->
						</div>
						<div class="layui-form-item">
							<div class="layui-input-block" style="margin-left: 0px;">
								<input type="password" name="admin_password" required
									lay-verify="required" placeholder="请输入账号密码" autocomplete="off" value="123123"
									class="layui-input">
							</div>
							<!-- <div class="layui-form-mid layui-word-aux"></div> -->
						</div>
						<div class="layui-form-item" style="margin-top: 30px;">
							<div class="layui-input-block" style="margin-left: 0px;">
								<button class="layui-btn layui-btn-normal  " lay-submit
									lay-filter="login-form-commit" style="width: 300px;">登录</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
<div style="text-align: center; clear: both;"></div>
	<script src="assets/layui/layui.js"></script>
	<script>
		(function($) {
			layui.use('form', function() {
				var form = layui.form;
				//监听提交
				form.on('submit(login-form-commit)', function(data) {
					$.ajax({
						url : 'admin/login.do',
						data : data.field,
						type : 'post',
						success : function(result) {
							if(result.success){
								 layer.msg(result.msg||'登录成功');
								 window.location.replace('workspace_admin.jsp');
							}else{
								 layer.msg(result.msg||'登录失败');
							}
						}
					}); 
					return false;
				});
			});
		})(window.jQuery);
	</script>
</body>
</html>

