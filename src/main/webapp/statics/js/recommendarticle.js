/*初始化加载事件 -- start*/
$(document).ready(function() {
	var user2 = $("#user").val();
	var arr = new Array();
	$.ajax({							
		url : path + "/user/atc/retopush.json",						
		data : {
			"userLogin":user2
		},
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.error === 0){
				
				 arr = data.articleInfo;
				var r = arr.length;
			for(var i=0;i<r;i++){
	   					var info = arr[i];
	   					var content ='<li><a href="javascrpit:;" class="getabout" aid="'+info.articleId+'"  luid="'+info.userModel.userLogin+'" title="【'+info.articleLabeName+'】'+info.articleTitle+'">\
	   				 						【'+info.articleLabeName+'】'+info.articleTitle+'</a></li>';
	   				 			$(".recommend").append(content);
				 }
			}
		},
		error : function(data) {
			console.log("none recommend !");
		}
	});
	/*访问文章事件- start*/
	$(document).on("click",".getabout",function() {
		var aid=$(this).attr("aid");
		var user1 =$(this).attr("luid")
		$.post(path+"/user/atc/"+user1+"/get/"+aid,function(data){
			if(data.error == 0){		
				window.location.href=path+"/user/atc/"+user1+"/about/"+aid;
	       		}else {
	       			art.dialog({
	       				title:'提示',
	       				content:data.message,
	       				time:(1.5) 				
	       			});       		
	       		}
	});
	});
		/*访问文章事件- end*/
	
});
/*初始化加载事件 -- end*/