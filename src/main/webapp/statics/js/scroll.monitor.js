
/*初始化加载事件 --start*/
jQuery(document).ready(function() {
	var path =$("#path").val();
	var user = $('#user').val();

	// 自定义事件
	var page =1;
	ajax_function();
	function ajax_function() {
		
		var arr = new Array();
		
		
		//获取.bloglist ,的最后一个子标签后面追加内容
		var parent=$(".message");
		$.ajax({
			   url: path+"/user/atc/"+user+"/articleinfo.json",
			   type: 'POST',
			   data:{'page':page},
			   dataType : "json",
			   success: function(data) {
				   		if(data.error == 0){		
				   				$(".message").hide();
				   				 arr = data.info.list;
				   				 var r = arr.length;
				   				for(var i=0;i<r;i++){
				   					var info = arr[i];
				   					$(parent).before('<div class="blogs"><div class="userinfo"><div  ><a href="javascript:;" class="getUserMain" luid="'+info.userModel.userLogin+'" >' +
										'<img alt=""  src="'+getHeadPhoto(info.userModel.userPhotoimg)+'" >\
											   </a></div><div  class="info" ><div class="nm" ><a href="javascript:;"  class="getUserMain" luid="'+info.userModel.userLogin+'" >'+info.userModel.userName+'</a></div>' +
										'<div class="dl" ><span>'+getMyDate(info.articleCreatedate)+'</span></div></div>\
												<div class="gzdv" >'+getExFollow(info.foId,info.articleUserid)+'</div></div>\
												<div class="acontent"><ul><h3><a href="javascript:;" class="getabout" aid="'+info.articleId+'"' +
										'  luid="'+info.userModel.userLogin+'">'+info.articleTitle+'</a></h3>\
												<div class="itxt"><div class="bText">'+info.articleIntro+'</div><figure><a href="javascript:;" class="getabout" aid="'+info.articleId+'"  ' +
										'luid="'+info.userModel.userLogin+'"><img src="'+getArticlePhoto(info.articleImage)+'" /></a>' +
										'</figure></div><p class="autor"><span class="lm f_l">'+info.articleLabeName+'</a></span> <span class="hyxx_bottom">作者：'+info.articleAuthor+'</span> \
												<span class="viewnum f_r">浏览（'+info.hiCount+'）</span></p></ul></div></div>');

				   				}
				   				page++;
				   				
				   			}else{
				   				
				   				$(".message").show(300);
				   				
				   			}				
			     },
				error : function() {
					
					$("#message").html("获取更多失败").show(20).delay(1500).hide(20);
				}
			 });  
	}	
	var timeoutInt;   // 要保证最后要运行一次

	window.onscroll = function () {
	    setTimeout(function () {
	        if (timeoutInt != undefined) {
	            window.clearTimeout(timeoutInt);
	        }
	        timeoutInt = window.setTimeout(function () {
	            //监听事件内容
	        	
	            if(getScrollHeight() <= (getDocumentTop() + getWindowHeight()+2)){
	                //当滚动条到底时,这里是触发内容
	                //异步请求数据,局部刷新dom
	            	
	                ajax_function();
	            }
	        }, 105);
	    }, 100);
	}
 
	//文档高度
	function getDocumentTop() {
	    var scrollTop =  0, bodyScrollTop = 0, documentScrollTop = 0;
	    if (document.body) {
	        bodyScrollTop = document.body.scrollTop;
	    }
	    if (document.documentElement) {
	        documentScrollTop = document.documentElement.scrollTop;
	    }
	    scrollTop = (bodyScrollTop - documentScrollTop > 0) ? bodyScrollTop : documentScrollTop;
	   /* console.log("文档高度"+scrollTop);*/
	    return scrollTop;
	}
 
	//可视窗口高度
	function getWindowHeight() {
	    var windowHeight = 0;
	    if (document.compatMode == "CSS1Compat") {
	        windowHeight = document.documentElement.clientHeight;
	    } else {
	        windowHeight = document.body.clientHeight;
	    }
	   /* console.log("可视窗口高度"+windowHeight);*/
	    return windowHeight;
	}
 
	//滚动条滚动高度
	function getScrollHeight() {
	    var scrollHeight = 0, bodyScrollHeight = 0, documentScrollHeight = 0;
	    if (document.body) {
	        bodyScrollHeight = document.body.scrollHeight;
	    }
	    if (document.documentElement) {
	        documentScrollHeight = document.documentElement.scrollHeight;
	    }
	    scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
	    /*console.log("滚动条滚动高度"+scrollHeight);*/
	    return scrollHeight;
	}
	
	
//将时间戳转化为0000年00月00日 00:00:00
function getMyDate(str){  
	   var oDate = new Date(str),  
	   oYear = oDate.getFullYear(),  
	   oMonth = oDate.getMonth()+1,  
	   oDay = oDate.getDate(),  
	   oHour = oDate.getHours(),  
	   oMin = oDate.getMinutes(),  
	   oSen = oDate.getSeconds(),  
	   oTime = oYear +'年'+ getzf(oMonth) +'月'+ getzf(oDay) +'日  '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间  
	   return oTime;  
	}; 
	//补0操作
	function getzf(num){  
		 if(parseInt(num) < 10){  
		     num = '0'+num;  
		 }  
		 return num;  
	}

//用户头像
function getHeadPhoto(str){
	var head = str;
	var photo;
	if(head == null || head ==''){
		photo = path+'/statics/images/head.png';
	}else{
		photo = path+str;
	}
	return photo;
}
//文章描述图片
function getArticlePhoto(str){
	var articleimg = str;
	
	var photo;
	if(articleimg == null || articleimg ==''){
		photo =path+'/statics/images/kongbai.png';
	}else{
		photo = path+str;
	}
	return photo;
}

function getExFollow(str,userid){
	//是否关注
	
	var poid = str;
	var uid=userid;
	var sid = $("#sid").val();
	var fwid = $('#uid').val();
	var template1 = '<button class="gzccc btnygz  fol'+uid+'"  type="button" uid='+uid+' ><span class="icon iconfont icon-woguanzhuta s1" ></span><span class="s2" >   已关注</span></button>';
	var template2 = '<button class="gzccc btnwgz fol'+uid+'"  type="button" uid='+uid+' ><span  class="icon iconfont icon-iconfonttaguanzhuwo s1" ></span><span  class="s2"> 关注ta</span></button>';
	if(fwid != sid){
		return '';
	}
	if(uid == sid){
		return '';
	}else{
		if(poid){
			return template1;
		}else{
			
			return template2;
		}
	}
	
}

});

/* 初始化加载事件 --end */
