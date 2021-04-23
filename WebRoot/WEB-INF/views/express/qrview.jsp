<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String BASE = request.getScheme() + "://" + request.getParameter("serverip") + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	String PATH = request.getContextPath();
%>
<style>
#qrcode{
	margin: auto;
}
</style>
<div class="content">
	<link rel="stylesheet"href="<%=PATH%>/assets/layui/css/layui.css">
	<script src="<%=PATH%>/assets/js/jquery-3.2.1.js"></script>
	<div style="width: 200px;height: 200px;text-align: center;margin: auto;margin-top: 25px;" id="qrcode-div">
		<div id="qrcode"></div>
		<img id="image" src="" />
	</div>
</div>

<script src="<%=PATH%>/assets/js/jquery.qrcode.min.js"></script>
<script src="<%=PATH%>/assets/layui/layui.js"></script>
<script src="<%=PATH%>/assets/js/md5.js"></script>
<script>
	(function($) {
		layui.use(['form'], function() {
			var form = layui.form;

			jQuery('#qrcode').qrcode({
				render: "canvas", //也可以替换为table
				width: 200,
				height: 200,
				text: '${expressId}'//通过id搜索
			});

			var img = document.getElementById("image"); /// get image element
			var canvas  = document.getElementsByTagName("canvas")[0];  /// get canvas element
			img.src = canvas.toDataURL();

			$("#qrcode").hide();

		});

	})(window.jQuery);
</script>