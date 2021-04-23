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
	<form class="layui-form" action="" id="select-people-edit-form">
		<input type="hidden" name="user_id" class="layui-input" value="${user_id}">
		<div class="layui-form-item">
			<label class="layui-form-label">选择</label>
			<div class="layui-input-block">
				<div class="xm-select-demo" id="xm-select-demo"></div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit
					lay-filter="select-people-edit-form-commit">立即提交</button>
			</div>
		</div>
	</form>
</div>

<script src="<%=PATH%>/assets/layui/layui.js"></script>
<script>
	(function($) {
		layui.config({
			base: '<%=PATH%>/assets/layui_exts/xmSelect/'
		}).extend({
			xmSelect: 'xm-select'
		}).use(['form','xmSelect'], function() {
			var form = layui.form,xmSelect = layui.xmSelect;

			var options = JSON.parse('${option}');
			var select = JSON.parse('${select}');

			var select_option = [];
			$.each(options,function(i,o){
				select_option.push({
					name:o.people_name+'【'+o.people_phone+'】',
					value:o.people_id
				})
			});

			var xmSelect = xmSelect.render({
				el: '#xm-select-demo',
				data: select_option
			})

			xmSelect.setValue(select);
			
			//监听提交
			form.on('submit(select-people-edit-form-commit)', function(data) {
				var valuess = [];
				var listSelectItem = xmSelect.getValue();
				$.each(listSelectItem,function(i,o){
					valuess.push(o.value)
				});
				$.ajax({
					url : 'setPeopleList.do',
					data : {
						user_id:$('input[name=user_id]').val(),
						peopleList:JSON.stringify(valuess)
					},
					type : 'post',
					success : function(result) {
						if(result.success){
							 layer.msg(result.msg||'保存成功');
							 setTimeout(function(){
								 var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
								 parent.layer.close(index); //再执行关闭
							 },1500);
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