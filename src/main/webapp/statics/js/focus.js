/*初始化加载事件 --start*/
jQuery(document).ready(function() {
	/*取消关注事件 - start*/
	$(document).on("click",".btnygz",function() {
		var uid = $(this).attr("uid");
		
		art.dialog({
			title:'提示',
			content:'确定要取消关注吗？',
			ok:function(){
					$.post(path+"/user/att/del/"+uid,function(data){
						if(data.error === 0){		
							var removeId = "#fo"+uid;
							$(removeId).fadeOut(500);
				       		}else{
				       			$("#message").html(data.message).show(20).delay(1500).hide(20);
				       		}							
				    });
			},
			cancel:function(){
			
			}
		});
		return false;
	});
	/*取消关注事件 - end*/
	/*访问用户首页- start*/
	$(document).on("click",".getUserMain",function() {
		var user =$(this).attr("luid")
		window.open(path+"/user/"+user+"/main.html");
	});

	/*访问用户首页- end*/
});

/* 初始化加载事件 --end */