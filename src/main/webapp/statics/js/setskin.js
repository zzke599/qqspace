jQuery(document).ready(function() {
	var path = $("#path").val();
	$(function() {

		// 修改模板样式的弹出
		$("#skin").click(function() {
			art.dialog.open(path + "/user/back/setskin.html", {
				title : "选择你喜欢的皮肤",
				width : 725,
				height : 570,
				lock : true,
				fixed : true,
				top : 1
			});
		});

	});
});