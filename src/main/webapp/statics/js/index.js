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
					if(data.error == 0){		
						var folid = ".fol"+uid;
						var template2 = '<button class="  gzccc btnwgz   fol'+uid+'"  type="button" uid='+uid+' ><span  class="icon iconfont icon-iconfonttaguanzhuwo s1" ></span><span  class="s2"> 关注ta</span></button>';
						$(template2).replaceAll(folid);
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
/*关注用户事件 - start*/
$(document).on("click",".btnwgz",function() {
	var uid = $(this).attr("uid");
	$.post(path+"/user/att/add/"+uid,function(data){
	if(data.error == 0){		
		var template1 = '<button class="  gzccc btnygz  fol'+uid+'"  type="button" uid='+uid+' ><span class="icon iconfont icon-woguanzhuta s1" ></span><span class="s2" >   已关注</span></button>';
		var folid = ".fol"+uid;
		$(template1).replaceAll(folid);
		
       	}else{
       		$("#message").html(data.message).show(20).delay(1500).hide(20);
       	}							
    });
	
});
/*关注用户事件 - end*/
/*访问用户首页- start*/
$(document).on("click",".getUserMain",function() {
	var user =$(this).attr("luid")
	
	window.open(path+"/user/"+user+"/main.html");  
});

/*访问用户首页- end*/
});

/* 初始化加载事件 --end */