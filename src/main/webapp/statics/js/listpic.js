/*初始化加载事件 --start*/
jQuery(document).ready(function() {
		var path = $("#path").val();
		var user = $("#user").val();
  		/*鼠标移到相册图片上的放大效果-start*/
		$(".hmanage").hide();
  		$(".lispic").find("li").mouseover(function(){
  			$(this).find(".hmanage").show();
  		});
  		$(".lispic").find("li").mouseout(function(){
  			$(this).find(".hmanage").hide();
  		});
  		/*鼠标移到相册图片上的放大效果-end*/
			
        	/* 上传图片弹出框-start*/
            $("#upload").click(function () {
                art.dialog.open(path+"/user/pic/uploadpic.html",{               	
                    title: "上传图片 ",
                    lock: true,
                    fixed: true,
					drag:false,
                    width: "690px",
                    height: "432px",
                    close: function () {						
							window.location.reload();
					}
                });
            });
            /* 上传图片弹出框-end*/
            
            /*  创建相册弹出框 -start*/
			$("#addphoto_box").click(function () {
	        	
				art.dialog.open(path+"/user/pic/add.html", {
					id:"addpic",
					title: "创建相册",
					lock: true,
					fixed:true,
					drag:false,
					resize:false,
					width: "400px",
					height: "300px",
					close: function () {
						var returnValue = art.dialog.data('returnValue');
						if(returnValue == 1){
							window.location.reload();
							}
					}
				});
			});
			/*  创建相册弹出框 -end*/
			
		/*   修改相册信息弹出框 -start*/
		$(".editphoto_box").click(function () {
			var pid=$(this).attr("pid");
			art.dialog.open(path+"/user/pic/edit/"+pid, {
				id:"editpic",
				title: "修改相册信息",
				lock: true,
				fixed: true,
				drag:false,
				resize:false,
				width: "400px",
				height: "300px",
				close: function () {
					var returnValue = art.dialog.data('returnValue');
							if(returnValue == 1){
								window.location.reload();
								}
					}
			});
		});
		/*   修改相册信息弹出框 -end*/
		
		/* 删除相册- start*/
		$(".delphoto_a").click(function(){
			var pid=$(this).attr("pid");
			// 通用删除提示信息
			art.dialog({
				title:'提示',
				drag:true,
				content:'此操作会将该相册下所有图片删除！是否确定删除？',
				lock:true,
				fixed:true,
				ok:function(){
					$.post(path+"/user/pic/del/"+pid,function(data){
						if(data.error == 0){							
							var pa ="#pa"+pid;
							$(pa).fadeOut(500);
				       		}else{
				       			$("#message").html(data.message).show(20).delay(1500).hide(20);
				       		}							
				    });
				},
				cancel:function(){
					
				}
			});
		});
		/* 删除相册- end*/
		/*访问相册事件- start*/
		$(".getAlbums").on("click",function() {
			var pid=$(this).attr("pid");
			$.post(path+"/user/pic/"+user+"/get/"+pid,function(data){
				if(data.error == 0){							
					window.location.href=path+"/user/pic/"+user+"/photodetail/"+pid;
		       		}else{
		       			art.dialog({
		       				title:'提示',
		       				content: data.message,
		       				time:(1.5)		
		       			});       	
		       		}				
		});
	});
		/*访问相册事件- end*/
});
/* 初始化加载事件 --end */
