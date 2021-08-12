 var path = $("#path").val();
 var user = $("#user").val();
$(function () {
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
			$(document).on('focus',"textarea",function(){
			   var count = $("textarea").val();
			   	if(count=="随便说两句……"){
			   		$(this).css("color","#000");
			        $(this).val("");
			   	}
		  });
		$(document).on('blur',"textarea",function(){
		   var count = $("textarea").val();
		    if(count ==""){
		    	$(this).css("color","#999");
		     	$(this).val("随便说两句……");
		    }
		  });
        });
/* 回复框的隐藏和显示 -end*/
/*初始化加载事件 --start*/
jQuery(document).ready(function() {
	/*留言发送事件 -start*/
	$(document).on("click",".messagebtn",function() {
		//获取当前的节点的指定兄弟节点（#rMessage）
		var message = $(this).siblings("#rMessage").val();
		//判断是否为空
		if(message.trim() ==null || message.trim() == '' || message.trim() == '随便说两句……'){
			console.log("Null Message!");
		}else{
			//发送请求
			$.post(path+"/user/lea/"+user+"/addmessage.json",{content:message},function(data){
				if(data.error == 0){		
					 window.location.reload();
		       		}else{
		       			$("#message").html(data.message).show(20).delay(1500).hide(20);
		       		}							
		    });
		}	
	});
	/*留言发送事件 -end*/
	/*删除留言事件 - start*/
	$(document).on("click",".delmessage",function() {
		var mid = $(this).attr("mid");
		art.dialog({
			title:'提示',
			content:'该操作会删除留言和留言回复，你确定删除吗？',
			ok:function(){
					$.post(path+"/user/lea/del/"+mid,function(data){
						if(data.error == 0){		
							var removeId = "#me"+mid;
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
	/*删除留言事件 - end*/
	/*留言回复发送事件 - start*/
	$(document).on("click",".answeringbtn",function() {
		//获取当前的节点的指定兄弟节点（#rMessage）
		var message = $(this).siblings("#rMessage");
		var messageValue=message.val();
		//判断是否为空
		if(messageValue.trim() ==null || messageValue.trim() == '' || messageValue.trim() == '请输入回复'){
			console.log("Null Answering!");
		}else{
			//发送请求
			var mid = $(message).attr("mid");
			var uid = $(message).attr("uid");
			
			$.post(path+"/user/lea/addanswering.json",{anContent:messageValue,anUserid:uid,anMessageid:mid},function(data){
				if(data.error == 0){		
					
					var content ='<div class="reply1" id="an'+data.answeringModel.anId+'">\
											<div class="f_nick"><span >'+data.answeringModel.anLeaveName+'</span> <b>回复</b>\
											<span >'+data.answeringModel.anUserName+'</span> \
											<span >['+getMyDate(data.answeringModel.anDate)+']</span></div>\
											<div class="reply" style="padding: 7px 20px 0 20px"><h3>'+data.answeringModel.anContent+'</h3>\
											<p class="autor f_r"><span class="sc f_r delanswering" anid="'+data.answeringModel.anId+'">删除该回复</span> \
											<span class="hf f_r">回复</span></p><div class="addR">\
											<textarea id="rMessage"  uid="'+data.answeringModel.anLeaveid+'" mid="'+data.answeringModel.anMessageid+'">请输入回复</textarea>\
											<input class="input_submit answeringbtn" type="button" value="提交" style="float: right" /></div></div></div>';
					var id = "#me"+mid;
					$(id+":last").append(content);
		       		}else{
		       		
		       		}							
		    });
		}	
		
	});
	/*留言回复发送事件 - end*/
	
	
	
	/*删除留言回复事件- start*/
	$(document).on("click",".delanswering",function() {
		
		var anid = $(this).attr("anid");
		art.dialog({
			title:'提示',
			content:'你确定删除该回复吗？',
			ok:function(){
					$.post(path+"/user/lea/delanswered/"+anid,function(data){
						if(data.error == 0){		
							var removeId = "#an"+anid;
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
	/*删除留言回复事件- end*/
	
	/*修改留言寄语 -start*/
	$(document).on("dblclick",".sendword",function() {
		var text = $(".word").text();
		var dd =$(".word");
		var input = $("<input type='text' value='"+text+"'  maxlength='34' " +
								"style='width:520px;height:25px; font-size:15px;border:0px;outline: none;' />");
		dd.html(input);		
		input.click(function() {
			return false;
		});
		// 获取焦点 
		input.trigger("focus");
		// 文本框失去焦点后提交内容，重新变为文本 
		input.blur(function() {
			// 获取输入框内容 
			var newtxt = $(this).val();
			$.post(path+"/user/upword.json",{text:newtxt},function(data){
				if(data.error == 0){						
					dd.html(newtxt);
		       		}else{
		       			$("#message").html(data.message).show(20).delay(1500).hide(20);
		       			dd.html(text);
		       		}							
		    });
			
			
		});
	});
	
	/*修改留言寄语 -end*/
	
	/*访问用户首页- start*/
	$(document).on("click",".getUserMain",function() {
		var user1 =$(this).attr("luid")
		window.open(path+"/user/"+user1+"/main.html");
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
		   oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +'-  '+ getzf(oHour) +':'+ getzf(oMin);//最后拼接时间  
		   return oTime;  
		}; 
		//补0操作
		function getzf(num){  
			 if(parseInt(num) < 10){  
			     num = '0'+num;  
			 }  
			 return num;  
		}
	
});
/* 初始化加载事件 --end */