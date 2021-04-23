<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%
	String BASE = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	String PATH = request.getContextPath();
%>
<style>
	.layui-input-block{
		margin-right: 40px;
	}
</style>
<div class="content" style="margin-top: 40px">
	<link rel="stylesheet"href="<%=PATH%>/assets/layui/css/layui.css">
	<script src="<%=PATH%>/assets/js/jquery-3.2.1.js"></script>
	<form class="layui-form" action="" id="express-add-form">
		<input type="hidden" name="express_id" class="layui-input" value="${express_id}">

		<div class="layui-form-item">
			<label class="layui-form-label">收件人</label>
			<div class="layui-input-block">
				<select name="people_id">
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">运费价格</label>
			<div class="layui-input-block">
				<input type="number" name="express_price" required lay-verify="required"
					   placeholder="请输入" autocomplete="off" class="layui-input" >
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">快递时间</label>
			<div class="layui-input-block">
				<input type="text" name="express_start_datetime" id="express-start-datetime" required lay-verify="required"
					   placeholder="" autocomplete="off" class="layui-input" >
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">快递状态</label>
			<div class="layui-input-block">
				<select name="express_status">
					<option value="0">待揽件</option>
					<option value="1">运输中</option>
					<option value="2">派送中</option>
					<option value="3">已完成</option>
				</select>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit
						lay-filter="express-add-form-commit">立即提交</button>
			</div>
		</div>
	</form>
</div>

<script src="<%=PATH%>/assets/layui/layui.js"></script>
<script>
	(function($) {
		layui.use(['form','laydate'], function() {
			var form = layui.form,
					laydate = layui.laydate;

			loadPeopleList();

			function loadPeopleList(){
				$.ajax({
					url : '<%=PATH%>/people/queryList.do',
					data : {},
					type : 'post',
					success : function(result) {
						if(result.success){

							var html = '<option value="">选择收件人</option>';
							for (var i = 0; i < result.data.length; i++) {
								html += '<option value="'+result.data[i].people_id+'">'+result.data[i].people_name+'【'+result.data[i].people_phone+'】</option>';
							}

							$('select[name="people_id"]').html(html);
							form.render('select');

						}else{
							layer.msg(result.msg||'产品获取失败');
						}
					}
				});
			}


			//时间选择器
			laydate.render({
				elem: '#express-start-datetime'
				,type: 'datetime',
				trigger: 'click'
			});

			//监听提交
			form.on('submit(express-add-form-commit)', function(data) {
				$.ajax({
					url : 'add.do',
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