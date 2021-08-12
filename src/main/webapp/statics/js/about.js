/*页面加载完成时执行 - start*/
	
	var path = $("#path").val();
	var aid = $("#aid").val();
jQuery(document).ready(function() {
           /* 回复框的隐藏和显示 -start*/
            $(document).on('click',".hf",function () {
                var text = $(this).text();
                var hf = $(this).parents(".reply").children(".addR");
                if (text == "回复") {
                    $(this).text("取消回复");
                    hf.show();
                } else {
                    $(this).text("回复");
                    hf.hide();
                }
            });
			$("textarea").on('focus',function(){
			   var count = $("textarea").val();
			   	if(count=="随便说两句……"){
			   		$(this).css("color","#000");
			        $(this).val("");
			   	}
		  });
		$("textarea").on('blur',function(){
		   var count = $("textarea").val();
		    if(count ==""){
		    	$(this).css("color","#999");
		     	$(this).val("随便说两句……");
		    }
		  });
		
       
/* 回复框的隐藏和显示 -end*/



	LoadCommentData();
	/*访问量 -start*/
    $.ajax({
        type: "POST",
        url: path+"/user/atc/autohits.json",         
        data: {"aid":aid},     
        dataType : "json",
        success: function (data) {
        	if(data.error == 0){		
        		console.log('++1');
       		}else{
       			console.log('error');
       		}					
        },
        error : function() {
			
        	console.log('error');
		}
      });
    /*访问量- end*/
	
	/*评论加载事件 - start*/
var page = $("#page").val();
var pageCount =1;
	function LoadCommentData(){
		
		var arr = new Array();
		var backarr = new Array();
		$.ajax({
			   url: path+'/user/com/load.json',
			   type: 'POST',
			   data:{'coArticleid':aid,
				   		 'page':page
				   		},
			   dataType : "json",
			   async: false,
			   success: function(data) {
				   if(data.error == 0){
					   $(".related").remove();
					   arr = data.info.list;
					   pageCount = data.info.totalPageCount;
					   page = data.info.currentPageNo;
					   var r = arr.length;
					 //循环评论信息
					   for(var i=0;i<r;i++){
		   					var info = arr[i];
		   						var content = '<div class="related"><div class="leave"><div class="hyxx"><div class="portrait1"><a href="javascipt:;" class="getUserMain"  luid="'+info.userModel.userLogin+'"><img src="'+getHeadPhoto(info.userModel.userPhotoimg)+'" /></a></div><div class="info"><div class="f_nick"><a href="javascipt:;" class="getUserMain"  luid="'+info.userModel.userLogin+'">'+info.userModel.userName+'</a></div><div class="f_data">'+getMyDate(info.coCreatedate)+'</div></div>\
		   												'+exdainzan(info.exLike,info.likeCount,info.coId)+'</div><div class="reply" style="padding: 10px 20px;"><h3>'+info.coContent+'</h3><p class="autor f_r"><span class="hf f_r">回复</span><span class="sc f_r"></span></p><div class="addR" ><textarea id="rMessage" cid="'+info.coId+'" uid="'+info.coUserid+'" maxlength="300"></textarea>\
		   												<input class="input_submit backbtn" type="button" value="回复" style="float:right" /></div></div></div>';
		   						backarr = info.commentBackModels;
		   						//循环回复信息
		   						var rr = backarr.length;
					   			for(var j=0;j<rr;j++){
					   						var backinfo = backarr[j];
					   						content+='<div class="reply1"><div class="f_nick"><a href="javascipt:;" class="getUserMain"  luid="'+info.userModel.userLogin+'">'+backinfo.userModel1.userName+' </a><b>回复</b> <a href="javascipt:;" class="getUserMain"  luid="'+info.userModel.userLogin+'">'+backinfo.userModel2.userName+' </a>['+getMyDate(backinfo.cbDate)+'] </div><div class="reply"  style="padding:7px 20px 0 20px"><h3>'+backinfo.cbContent+'</h3>\
					   											<p class="autor f_r"><span class="hf f_r">回复</span></p><div class="addR"><textarea id="rMessage" cid="'+backinfo.cbCommentid+'" uid="'+backinfo.cbCommentuserid+'" maxlength="300"></textarea><input class="input_submit backbtn" type="button" value="回复" style="float:right" /></div></div></div>';
					   					}
					   				content+=' </div>';
					   				//把每个评论的信息和回复信息追加到.leavelist节点后
					   				$(".leavelist").append(content);
					   				//当前页
					   				$("#page").val(data.info.currentPageNo);
					   		}
						/*创建分页 -start*/
			   			 $(".tcdPageCode").createPage({
			   				 
			   			        pageCount:  pageCount,
			   			        current: page,
			   			        backFn: function(p) {
			   			        	search(p);
			   			        }
			   			    });
			   			
			   			 /*创建分页 -end*/
					   }else{	
					   console.log(data.message);
				   }
			     },
				error : function() {
					console.log("加载失败！");
					
				}
			});			
		
	}
	
	//查询方法
    function search(p) {
    	 
    	$.ajax({
			   url: path+'/user/com/load.json',
			   type: 'POST',
			   data:{'coArticleid':aid,
				   		 'page':p
				   		},
			   dataType : "json",
			   success: function(data) {
				   if(data.error == 0){
					   $(".related").remove();
					   arr = data.info.list;
					   pageCount = data.info.totalPageCount;
					   page = data.info.currentPageNo;
					   var r = arr.length;
					 //循环评论信息
					   for(var i=0;i<r;i++){
		   					var info = arr[i];
		   						var content = ' <div class="related"><div class="leave"><div class="hyxx"><div class="portrait1"><a href="javascipt:;" class="getUserMain"  luid="'+info.userModel.userLogin+'"><img src="'+getHeadPhoto(info.userModel.userPhotoimg)+'" /></a></div><div class="info"><div class="f_nick"><a href="javascipt:;" class="getUserMain"  luid="'+info.userModel.userLogin+'">'+info.userModel.userName+'</a></div><div class="f_data">'+getMyDate(info.coCreatedate)+'</div></div>\
		   												'+exdainzan(info.exLike,info.likeCount,info.coId)+'</div><div class="reply" style="padding: 10px 20px;"><h3>'+info.coContent+'</h3><p class="autor f_r"><span class="hf f_r">回复</span><span class="sc f_r"></span></p><div class="addR" ><textarea id="rMessage" cid="'+info.coId+'" uid="'+info.coUserid+'">请输入回复</textarea>\
		   												<input class="input_submit backbtn" type="button" value="回复" style="float:right" /></div></div></div>';
		   						backarr = info.commentBackModels;
		   						//循环回复信息
		   						var rr = backarr.length;
					   			for(var j=0;j<rr;j++){
					   						var backinfo = backarr[j];
					   						content+='<div class="reply1"><div class="f_nick"><a href="javascipt:;" class="getUserMain"  luid="'+info.userModel.userLogin+'">'+backinfo.userModel1.userName+' </a><b>回复</b> <a href="javascipt:;" class="getUserMain"  luid="'+info.userModel.userLogin+'">'+backinfo.userModel2.userName+' </a>['+getMyDate(backinfo.cbDate)+'] </div><div class="reply"  style="padding:7px 20px 0 20px"><h3>'+backinfo.cbContent+'</h3>\
					   											<p class="autor f_r"><span class="hf f_r">回复</span></p><div class="addR"><textarea id="rMessage" cid="'+backinfo.cbCommentid+'" uid="'+backinfo.cbCommentuserid+'">请输入回复</textarea><input class="input_submit backbtn" type="button" value="回复" style="float:right" /></div></div></div>';
					   					}
					   					content+=' </div>';
					   				//追加内容到.leavelist节点
					   				$(".leavelist").append(content);
					   		}
					   }else{
					   console.log(data.message);
				   }
			     },
				error : function() {
					console.log("加载失败！");

				}
			});

    }
    /*评论加载事件 - end*/
    
    /*评论发送事件 -start*/
	$(document).on("click",".commentbtn",function() {
		//获取当前的节点的指定兄弟节点（#rMessage）
		var message = $(this).siblings("#rMessage").val();
		var messageNode =$(this).siblings("#rMessage");
		//判断是否为空
		if(message.trim() ==null || message.trim() == '' || message.trim() == '随便说两句……'){
			console.log("Null Message!");
		}else{
			//发送请求
			$.post(path+"/user/com/"+aid+"/addcomment.json",{content:message},function(data){
				if(data.error == 0){		
					LoadCommentData();
					messageNode.val("");
		       		}else{
		       			
		       			$("#message").html(data.message).show(20).delay(1500).hide(20);
		       			messageNode.val("");
		       		}							
		    });
		}	
	});
	/*评论发送事件 -end*/
	
	
	/*留言回复发送事件 - start*/
	$(document).on("click",".backbtn",function() {
		//获取当前的节点的指定兄弟节点（#rMessage）
		var message = $(this).siblings("#rMessage");
		var messageValue=message.val();
		
		//判断是否为空
		if(messageValue.trim() ==null || messageValue.trim() == '' || messageValue.trim() == '请输入回复'){
			console.log("Null Answering!");
		}else{
			//发送请求
			var cid = $(message).attr("cid");
			var uid = $(message).attr("uid");
			
			$.post(path+"/user/com/addcommentback.json",{cbContent:messageValue,cbUserid:uid,cbCommentid:cid},function(data){
				if(data.error == 0){					
					search(page);
					
		       		}else{
		       			$("#message").html(data.message).show(20).delay(1500).hide(20);
		       			
		       		}							
		    });
		}	
		
	});
	/*留言回复发送事件 - end*/
	/*点赞和取消点赞事件 -start*/
	$(document).on("click",".dianzan",function() {
		var cid= $(this).attr("cid");
		$.post(path+"/user/com/onlike.json",{clCommentid:cid},function(data){
			if(data.error == 0){	
				search(page);
	       		}else{
	       			$("#message").html(data.message).show(20).delay(1500).hide(20);
	       		}			
		});
	});
	/*点赞和取消点赞事件 -end*/

	
	/*访问用户首页- start*/
	$(document).on("click",".getUserMain",function() {
		var user =$(this).attr("luid")
		window.open(path+"/user/"+user+"/main.html");
	});

	/*访问用户首页- end*/
	
	
	
		//将时间戳转化为0000年00月00日 00:00:00
		function getMyDate(str){  
			   var oDate = new Date(str),  
			   oYear = oDate.getFullYear(),  
			   oMonth = oDate.getMonth()+1,  
			   oDay = oDate.getDate(),  
			   oHour = oDate.getHours(),  
			   oMin = oDate.getMinutes(),  
			   oSen = oDate.getSeconds(),  
			   oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +'  '+ getzf(oHour) +':'+ getzf(oMin) ;//最后拼接时间  
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
		//判断是否点赞过和点赞量
		function exdainzan(str,str2,str3){
			var ex = str;
			var count =str2;
			var cid = str3;
			var content1 = '<span style="float: right; margin-right: 48px; margin-top: 10px;line-height: -20px;display: flex;flex-direction:column; text-align: center;cursor: pointer;"><svg cid="'+cid+'"  class="icon dianzan" aria-hidden="true" style="height: 2.5em;"><use  xlink:href="#icon-zan"></use></svg>('+count+')</span>';
			var content2 = '<span style="float: right; margin-right: 48px; margin-top: 10px;line-height: -20px;display: flex;flex-direction:column; text-align: center;cursor: pointer;"><svg  cid="'+cid+'"   class="icon dianzan" aria-hidden="true" style="height: 2.5em;"><use  xlink:href="#icon-fabu-"></use></svg>('+count+')</span>';
			if(!ex){
				return content1;
			}else{
				return content2;
			}
			
		}
		
});
/*页面加载完成时执行 - end*/