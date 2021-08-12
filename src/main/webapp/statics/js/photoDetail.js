/*初始化加载事件 --start*/
jQuery(document).ready(function() {
	var path = $("#path").val();
	var pid = $("#pid").val();

			/* 上传图片弹出框-start*/
            $("#upload").click(function () {
            	
                art.dialog.open(path+"/user/pic/uploadpics/"+pid,{               	
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
            
            /*触摸图片出现，消失删除图片事件 - start*/
            $(".imggroup").on("mouseover",function() {
            	 var del = $(this).find("#error");
            	 $(del).show();
            	 var uppicimg = $(this).find(".uppicimg");            	            	 
            	 $(uppicimg).show();
            });
            $(".imggroup").on("mouseout",function() {
           	 var del = $(this).find("#error");
           	 $(del).hide();
           	var uppicimg = $(this).find(".uppicimg");            	            	 
           	$(uppicimg).hide();
           });
            /*触摸图片出现，消失删除图片事件 - end*/
            /*批量操作的单击事件 -start*/
            $(document).on("click","#batch",function() {
            	$(".checkBoxs").css("display","block");	
            	$(".delPhoto").hide();        	
            	$(".uppicimg").hide();
            	$('.upDesc').hide();
            	$(this).html("取消批量操作");
            	$(this).attr("id","cancelbatch");
            	$(".cz1").show();
            });
            $(document).on("click","#cancelbatch",function() {
            	$(".checkBoxs").css("display","none");
            	$('.delPhoto').show();
            	$('.uppicimg').show();
            	$('.upDesc').show();
            	$(".cz1").hide();
            	$(this).html("批量操作");
            	$(this).attr("id","batch");
            	
            });
            /*批量操作的单击事件 -end*/
            /*删除当前图片事件 -start*/
            $(".delPhoto").click(function() {
				var mid = $(this).attr("mid");				
				art.dialog({
					title:'提示',
					content:'确定要删除该图片吗？',
					ok:function(){
							$.post(path+"/user/pic/delPhoto/"+mid,function(data){
								if(data.error == 0){		
									var img ="#img"+mid;
									$(img).remove();
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
			
            /*删除当前图片事件 -end*/
            /*设置为封面事件 -start*/
            $(".uppicimg").on("click",function() {
            	var mid =$(this).attr("mid");
            	
            	$.post(path+"/user/pic/upcover/"+pid,{mid:mid},function(data){
					if(data.error == 0){							
						$("#message").html("设置成功").show(20).delay(1500).hide(20);
			       		}else{
			       			
			       			$("#message").html(data.message).show(20).delay(1500).hide(20);
			       		}				
            		});
            });
            /*设置为封面事件 -end*/
            /* 点击编辑描述事件-start */
        	$(document).on("click",".upDesc",function(){    		
        		var mid = $(this).attr("mid");
        		var b = $("#describe" + mid);
        		var txt = b.text();
        		var textarea = $("<textarea  class='describe"+mid+"'  value='"+txt+"' maxlength='30' style='padding: 3px 5px;font-size:10px; outline:none;width:98%;' ></textarea>");
        		b.html(textarea);
        		textarea.click(function() {
        			return false;
        		});      		
        		//获取焦点 
        		textarea.trigger("focus");
        		//文本框失去焦点后提交内容，重新变为文本 
        		textarea.blur(function() {
        			var newtxt = $(".describe" + mid).val();	
        				$.ajax({
        					   url: path+'/user/pic/describe/'+mid,
        					   type: 'POST',
        					   data:{'pdDescription':newtxt},
        					   dataType : "json",
        					   success: function(data) {
        						   if(data.error == 0){
        							   b.html(newtxt);
        						   }else{
        							   $("#message").html(data.message).show(20).delay(1500).hide(20);
        							   b.html(txt);
        						   }
        					     },
        						error : function() {
        							
        							$("#message").html("更改失败").show(20).delay(1500).hide(20);
        							b.html(txt);
        						}
        					});							      			
        		});
        	});
        	/* 点击编辑描述事件-end */
            /*显示图片轮播事件- start*/
            $(document).on("click",".getPo",function(){
            	 var _this = $(this);//将当前的pimg元素作为_this传入函数  
                 imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);  
            });
            /*显示图片轮播事件- end*/
            /*点击全选,反选事件 -start*/
            $(document).on('click','#checkedAll',function(){
			    
			        $('.checkbox').prop('checked',true);
			        $(this).html("取消选择");
			        $(this).attr("id","cancelAll")
			       
			   
			});
            $(document).on('click','#cancelAll',function(){
            	$('.checkbox').prop('checked',false);
            	 $(this).html("选择全部");
            	 $(this).attr("id","checkedAll")
            	
            });
            /*点击全选,反选事件 -end*/
            /*点击删除（多选）-start*/
            $("#delsimg").on("click",function() {
            	var id_array=new Array();  
            	$('input[name="item"]:checked').each(function(){  
            	    id_array.push($(this).val());//向数组中添加元素  
            	});  
            	var idstr=id_array.join(',');//将数组元素连接起来以构建一个字符串  
            	
            	if(id_array.length != 0){
            		$.ajax({
 					   url: path+"/user/pic/delphotos.json",
 					   type: 'POST',
 					   data:{'mids':id_array},
 					   traditional:true,
 					   success: function(data) {
 						   		if(data.error == 0){							
 						   				var arr = id_array;
 						   				for(var i=0;i<arr.length;i++){
 						   				var img ="#img"+arr[i]; 
 						   				$(img).remove();
 						   				
 						   				}
 						   			$("#message").html("批量删除成功").show(20).delay(1500).hide(20);
 						   			}else{
 						   				
 						   			$("#message").html(data.message).show(20).delay(1500).hide(20);
 						   			}				
 					     },
 						error : function() {
 							
 								$("#message").html("批量删除失败").show(20).delay(1500).hide(20);
 						}
 					});							  
            	}
            });
            /*点击删除（多选）-end*/
            /* 点击图片查看大图 -start*/
            function imgShow(outerdiv, innerdiv, bigimg, _this){  
                var src = _this.attr("src");//获取当前点击的pimg元素中的src属性  
                $(bigimg).attr("src", src);//设置#bigimg元素的src属性  
              
                    /*获取当前点击图片的真实大小，并显示弹出层及大图*/  
                $("<img/>").attr("src", src).load(function(){  
                    var windowW = $(window).width();//获取当前窗口宽度  
                    var windowH = $(window).height();//获取当前窗口高度  
                    var realWidth = this.width;//获取图片真实宽度  
                    var realHeight = this.height;//获取图片真实高度  
                    var imgWidth, imgHeight;  
                    var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放  
                      
                    if(realHeight>windowH*scale) {//判断图片高度  
                        imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放  
                        imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度  
                        if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度  
                            imgWidth = windowW*scale;//再对宽度进行缩放  
                        }  
                    } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度  
                        imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放  
                                    imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度  
                    } else {//如果图片真实高度和宽度都符合要求，高宽不变  
                        imgWidth = realWidth;  
                        imgHeight = realHeight;  
                    }  
                            $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放  
                      
                    var w = (windowW-imgWidth)/2;//计算图片与窗口左边距  
                    var h = (windowH-imgHeight)/2;//计算图片与窗口上边距  
                    $(innerdiv).css({"top":h, "left":w, });//设置#innerdiv的top和left属性
					$(bigimg).css({"border": "0px solid"});
                    $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg  
                });  
                  
                $(outerdiv).click(function(){//再次点击淡出消失弹出层  
                    $(this).fadeOut("fast");  
                });
                
            }
            /* 点击图片查看大图 -end*/
});
/* 初始化加载事件 --end */
