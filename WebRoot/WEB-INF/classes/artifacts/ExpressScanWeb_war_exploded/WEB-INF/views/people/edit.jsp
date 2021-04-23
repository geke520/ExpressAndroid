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
<style>
.layui-input-block{
	margin-right: 40px;
}
</style>
<div class="content" style="margin-top: 40px">
	<link rel="stylesheet"href="<%=PATH%>/assets/layui/css/layui.css">
	<script src="<%=PATH%>/assets/js/jquery-3.2.1.js"></script>
	<form class="layui-form" action="" id="people-edit-form">
		<input type="hidden" name="people_id" class="layui-input" value="${detail.people_id}">
		<div class="layui-form-item">
			<label class="layui-form-label">姓名</label>
			<div class="layui-input-block">
				<input type="text" name="people_name" required lay-verify="required" value="${detail.people_name}"
					placeholder="请输入" autocomplete="off" class="layui-input" >
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">手机号</label>
			<div class="layui-input-block">
				<input type="text" name="people_phone" required lay-verify="required" value="${detail.people_phone}"
					placeholder="请输入" autocomplete="off" class="layui-input" >
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">收货地址</label>
			<div class="layui-input-block">
				<input type="text" name="people_address" required lay-verify="required" value="${detail.people_address}"
					placeholder="请输入" autocomplete="off" class="layui-input" >
			</div>
		</div>

		
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit
					lay-filter="people-edit-form-commit">立即提交</button>
			</div>
		</div>
	</form>
</div>

<script src="<%=PATH%>/assets/layui/layui.js"></script>
<script>
	(function($) {
		layui.use(['form'], function() {
			var form = layui.form;
			

			
			//监听提交
			form.on('submit(people-edit-form-commit)', function(data) {
				$.ajax({
					url : 'edit.do',
					data : data.field,
					type : 'post',
					success : function(result) {
						if(result.success){
							 layer.msg(result.msg||'保存成功');
							 var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
							 parent.layer.close(index); //再执行关闭   
						}else{
							 layer.msg(result.msg||'保存失败');
						}
					}
				});
				return false;
			});
			

		});

	})(window.jQuery);
</script>