jQuery(document).ready(function() {
var path = $("#path").val();
var user = $("#user").val();
	$(function() {

			 /* 添加分类事件-start */
			$(".addType").click(function() {
				var li = $(this);
				var txt = li.text();
				var input = $("<input type='text' maxlength='10' />");
				li.html(input);
				input.click(function() {
					return false;
				});
				// 获取焦点 
				input.trigger("focus");
				// 文本框失去焦点后提交内容，重新变为文本 
				input.blur(function() {
					// 获取输入框内容 
					var newtxt = $(this).val();
					// 判断是否有值 
					if (newtxt.trim() != "" && newtxt.trim() != null ) {
					 $.post(path+"/user/lab/add",{labelName:newtxt},function(data){
						     if(data.error == 0){									
								$("<li id='lab"+data.labelModel.labelId+"'>" +
									    "<a class='delType' href='#' tid='"+data.labelModel.labelId+"'>删除</a>" +
									    "<a class='upType'  href='#' tid='"+data.labelModel.labelId+"'>编辑</a> " +
									    "<a href='article.html' class='tname"+data.labelModel.labelId+"'>"+data.labelModel.labelName+"</a>" +
								    "</li>").insertBefore("#addType");		
									
						       		}else{
						       			
						       			$("#message").html(data.message).show(20).delay(1500).hide(20);
						       		}							
						    });
					}
						li.html(txt);
				});
			});
			/* 添加分类事件-end */
			/*删除标签事件-start*/
			$(document).on("click",".delType",function(){
				var tid = $(this).attr("tid");
				
				art.dialog({
					title:'提示',
					content:'确定要删除该标签吗？',
					ok:function(){
							$.post(path+"/user/lab/del/"+tid,function(data){
								if(data.error == 0){		
								
									var lab ="#lab"+tid;
									$(lab).fadeOut(500);
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
			
			/*删除标签事件-end*/	
			/*修改文章事件 -start*/
			$(".upArticle").click(function() {
				var aid = $(this).attr("aid");
				
				window.location.href=path+"/user/atc/edit/"+aid;
			});
			/*修改文章事件 -end*/
			/*删除文章事件 -start*/
			$(".delArticle").click(function() {
				var aid = $(this).attr("aid");				
				art.dialog({
					title:'提示',
					content:'确定要删除该文章吗？',
					ok:function(){
							$.post(path+"/user/atc/del/"+aid,function(data){
								if(data.error == 0){		
									/*$(this).parentNode.parentNode.removeChild(this.parentNode);*/
									var atc ="#atc"+aid;
									$(atc).fadeOut(500);
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
			});
			/*删除文章事件 -end*/
	
	
		  });
	/* 修改标签事件-start */
	$(document).on("click",".upType",function(){
		
		var tid = $(this).attr("tid");
		var li = $(".tname" + tid);
		var txt = li.text();
		var input = $("<input class='name"+tid+"' type='text' value='"+txt+"' maxlength='10' />");
		li.html(input);
		input.click(function() {
			return false;
		});
		
		//获取焦点 
		input.trigger("focus");
		//文本框失去焦点后提交内容，重新变为文本 
		input.blur(function() {
			var newtxt = $(".name" + tid).val();
			if (newtxt.trim()  != "" && newtxt.trim() != null
					&& newtxt.trim() != txt) {
				$.ajax({
					   url: path+'/user/lab/change/'+tid,
					   type: 'POST',
					   data:{'labelName':newtxt},
					   dataType : "json",
					   success: function(data) {
						   if(data.error == 0){
							   li.html(data.labelName);
						   }else{
							   $("#message").html(data.message).show(20).delay(1500).hide(20);
								li.html(txt);
						   }
					     },
						error : function() {
							
							$("#message").html("更改失败").show(20).delay(1500).hide(20);
							li.html(txt);
						}
					});										
			} else {
				li.html(txt);
			}
		});
		/* 修改分类事件-end */

	
});