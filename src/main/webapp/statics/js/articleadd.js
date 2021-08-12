jQuery(document).ready(function() {

var path =$("#path").val();

var editor;
KindEditor.ready(function (K) {
    // 富文本编辑器
    editor = K.create('textarea[name="articleContent"]', {
        minWidth: 600,
        minHeight: 210,
        resizeType: 1,
        filePostName: 'fileImage',
        uploadJson: path+'/user/atc/upload_img.json',
        fileManagerJson: path+'/user/atc/uploadspace_img.json',
        allowFileManager: true,
        afterBlur : function() { // 利用该方法处理当富文本编辑框失焦之后，立即同步数据
			KindEditor.sync("#articleContent");
		},
        themeType : 'simple',
        items: [
                	 'fontname', 'fontsize', '|',  'undo', 'redo', '|',   'template', 'cut', 'copy', 'paste',
                	'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright','justifyfull',
                	'|',  'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                	'superscript','|' ,'formatblock', 'quickformat', 'selectall',  'forecolor', 'hilitecolor',
                	'bold','italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
                	'multiimage','media',  '|' ,  'table', 'hr', 'emoticons',  'link',  '|' ,'fullscreen'
                			]
    });
  

$(function () {
    $('input:radio[name="status"]').click(function () {
        var rad = $('input:radio[name="status"]:checked').val();
        if (rad != 1) {
            // .css("display", "none")
            $("#ts").hide();
        } else if (rad == 1) {
            $("#ts").show();
        }
    });
		// 提示信息
    	var msg = '';
    		if(msg.length>0){
    				
    				$("#message").html(msg).show(20).delay(1500).hide(20);
    			};
		}); 
	});
/*描述图的上传事件-start*/
var dragImgUpload = new DragImgUpload("#drop_area",{
    callback:function (files) {
        //回调函数，可以传递给后台等等
        var file = files[0];

        var fileData = new FormData();
        var imgurl = $("#articleImage").val();
        fileData.append('image', file);
        fileData.append('imgurl', imgurl);
        $.ajax({
            type: "POST",
            url: path+"/user/atc/upload.json",         
            data: fileData,
            processData: false,
            contentType : false,
            mimeType:"multipart/form-data",
            dataType : "json",
            success: function (data) {
            	if(data.error == 0){		
            		$("#articleImage").val(data.url);          		
	       		}else{
	       			
	       			$("#message").html(data.message).show(20).delay(1500).hide(20);
	       		}					
            },
            error : function() {
            	$("#message").html("上传失败").show(20).delay(1500).hide(20);
            	
			}
          });
        					
	
    }
});

/*描述图的上传事件-end*/
/* 修改个人信息的保存按钮的点击事件--start */
$("#saveArticleInfo")
		.on(
				"click",
				function() {
					if (!check()) {
						return false;
					} else {
						var articleModel = $('#articleInfo')
								.serialize();			
						$.ajax({
									url : path
											+ "/user/atc/front.json",
									type : "POST",
									data : articleModel,
									dataType : "json",
									success : function(data) {
										if (data.error == 0) {
											window.location.href = path
													+ "/user/atc/"+data.userLogin+"/article.html";
										} else {
											var message = data.message;
											
											$("#message").html(message).show(20).delay(1500).hide(20);
										}
									},
									error : function() {
										
										
										$("#message").html("提交失败").show(20).delay(1500).hide(20);
									}

								});
					}
				});

/* 效验信息脚本 --start */
function check() {
	//标题
	var articleTitle = $("#articleTitle").val();
	
	//简介
	var articleIntro = $("#articleIntro").val();
	//正文
	var articleContent = $("#articleContent").val();
	//作者
	var articleAuthor = $("#articleAuthor").val();
	if(articleTitle.trim()==null && articleTitle.trim()==""){
		
		$("#message").html("标题不能为空").show(20).delay(1500).hide(20);
		return false;
	}
	
	if(articleIntro.trim()==null && articleIntro.trim()==""){
		$("#message").html("简介不能为空").show(20).delay(1500).hide(20);
		
		return false;
	}
	if(articleContent.trim()==null && articleContent.trim()==""){
		
		$("#message").html("正文不能为空").show(20).delay(1500).hide(20);
		return false;
	}
	if(articleAuthor.trim()==null && articleAuthor.trim()==""){
		
		$("#message").html("作者不能为空").show(20).delay(1500).hide(20);
		return false;
	}
	
	return true;
}

/* 效验信息脚本 --end */
/* 修改个人信息的保存按钮的点击事件--end */

});