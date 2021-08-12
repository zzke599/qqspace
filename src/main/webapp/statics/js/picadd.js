jQuery(document).ready(function() {
	var path = $("#path").val();
	/*点击保存按钮事件 - start*/	
	$("#savebtn").on("click",function() {
		var paName = $("#paName").val();
		//数据效验
		if(paName.trim() == null && paName.trim()==''){
			
			$("#message").html("相册名称不能为空").show(20).delay(1500).hide(20);
			return false;
			
		}else{
			var photoAlbumsModel = $('#photoAlbumsInfo').serialize();		
			
			$.ajax({
				url : path+ "/user/pic/addpic.json",
				type : "POST",
				data : photoAlbumsModel,
				dataType : "json",
				success : function(data) {
					if (data.error == 0) {
						
						artDialog.data("returnValue", 1);
						top.art.dialog({
							id : "addpic"
						}).close();
					} else {
						var message = data.message;
						$("#message").html(message).show(20).delay(1500).hide(20);
						
					}
				},
				error : function() {
					/* 提示信息*/
					$("#message").html("新增相册失败").show(20).delay(1500).hide(20);
					
				}
			});
			
		}
			
	});
	
	/*点击保存按钮事件 - end*/
	
	
});