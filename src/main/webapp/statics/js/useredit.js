/*初始化加载事件 --start*/
jQuery(document).ready(function() {
					var path = $("#path").val();
					var userlogin = $("#user").val();
					var editor;
					KindEditor.ready(function(K) {

						/* 富文本编辑器的初始化事件--start */
						editor = K.create('textarea[name="userIntro"]', {
							minWidth : 521,
							minHeight : 190,
							resizeType : 0,
							filePostName : 'fileImage',
							uploadJson : path + '/user/upload_img.json',
							fileManagerJson : path
									+ '/user/uploadspace_img.json',
							allowFileManager : true,
							afterBlur : function() { // 利用该方法处理当富文本编辑框失焦之后，立即同步数据
								KindEditor.sync("#userIntro");
							},
							items : [ 'fontname', 'fontsize', '|', 'cut',
									'copy', 'paste', 'plainpaste', 'wordpaste',
									'|', 'forecolor', 'hilitecolor', 'bold',
									'italic', 'underline', 'removeformat', '|',
									'justifyleft', 'justifycenter',
									'justifyright', 'insertorderedlist',
									'insertunorderedlist', '|', 'image',
									'multiimage', 'emoticons', 'link',
									'fullscreen' ]
						});
						/* 富文本编辑器的初始化事件--end */
						/* 头像 上传按钮点击事件--start */
						var uploadbutton = K.uploadbutton({
							button : K('#btnUploadFile')[0],
							fieldName : 'fileImage',// 上传文件要提交的属性名称
							url : path + '/user/douploadphoto.json',// 提交的URL地址（控制器地址）
							afterUpload : function(data) {
								if (data.error == 0) {
									var url = data.url;
									K('#imgShow').attr("src", path+url);
									$('#hpic').val(url);
								} else {
									
									$("#message").html(data.message).show(20).delay(2000).hide(20);
								}
							},
							afterError : function(str) {
								$("#message").html("上传头像失败！").show(20).delay(2000).hide(20);
							}
						});
						uploadbutton.fileBox.change(function(e) {
							
							// 执行提交事件
							uploadbutton.submit();
						});
					});
					/* 头像 上传按钮点击事件--end */

					/* 密码框的弹出的点击事件-- start */
					$(function() {
						// 修改密码框的弹出
						$(".updatePwd").click(function() {
							art.dialog.open(path + "/user/updatePwd.html", {
								id : "updatePwd",
								title : "修改密码",
								width : 740,
								height : 250,
								lock : true,
								fixed : true,
								resize : false,
								close: function () {
									var returnValue = art.dialog.data('returnValue');
									if(returnValue == 1){
										window.location.reload();
									}
								}
							});
						});
					});
					/* 密码框的弹出的点击事件-- end */

					/* 修改个人信息的保存按钮的点击事件--start */
					$("#saveuserInfo").on("click",function() {
										if (!check()) {
											return false;
										} else {
											var userModel = $('#userInfoedit').serialize();
											$.ajax({
														url : path+ "/user/doedit.json",
														type : "POST",
														data : userModel,
														dataType : "json",
														success : function(data) {
															if (data.error == 0) {
																window.location.href = path+ "/user/"+userlogin+"/userInfo.html";
															} else {
																var message = data.message;
																/* 提示信息，弹出显示6秒后隐藏 */
																$("#message").html(message).show(20).delay(2000).hide(20);
															}
														},
														error : function() {
															/* 提示信息，弹出显示6秒后隐藏 */
															$("#message").html("[提示]：保存失败！").show(20).delay(2000).hide(20);
														}

													});
										}
									});

					/* 效验信息脚本 --start */
					function check() {
						var userName = $("#userName").val();
						var mydatepicker = $("#mydatepicker").val();
						var userAddress = $("#userAddress").val();
						var userTelphone = $('#userTelphone').val();
						var userEmail = $('#userEmail').val();
						var userIntro = $('#userIntro').val();

						if (userName == null || userName == "") {
							/* 提示信息，弹出显示6秒后隐藏 */
							$("#message").html("[提示]：博客名不能为空！").show(20).delay(2000).hide(20);
							return false;
						}
						if (mydatepicker == null || mydatepicker == ""
								|| mydatepicker == "undefined"
								|| mydatepicker == undefined
								|| mydatepicker == "null") {
							/* 提示信息，弹出显示6秒后隐藏 */
							$("#message").html("[提示]：生日不能为空！").show(20).delay(2000).hide(20);
							return false;
						}
						if (userAddress == null || userAddress == "") {
							/* 提示信息，弹出显示6秒后隐藏 */
							$("#message").html("[提示]：地址不能为空！").show(20).delay(2000).hide(20);
							return false;
						}
						if (userTelphone == null || userTelphone == "") {
							/* 提示信息，弹出显示6秒后隐藏 */
							$("#message").html("[提示]：手机号不能为空！").show(20).delay(2000).hide(20);
							return false;
						}
						if (userEmail == null || userEmail == "") {
							/* 提示信息，弹出显示6秒后隐藏 */
							$("#message").html("[提示]：邮箱不能为空！").show(20).delay(2000).hide(20);
							return false;
						}
						if (userIntro == null || userIntro == "") {
							/* 提示信息，弹出显示6秒后隐藏 */
							$("#message").html("[提示]：个人简介不能为空！").show(20).delay(2000).hide(20);
							return false;
						}
						// 时间转换
						var now = (new Date(mydatepicker)).getTime();
						var newDate = new Date().getTime();
						// 效验时间是否为过去的日期
						if (now >= newDate) {
							/* 提示信息，弹出显示6秒后隐藏 */
							$("#message").html("[提示]：生日要为过去的时间！").show(20).delay(2000).hide(20);
							return false;
						}
						var exphone = /^1[3456789]\d{9}$/;
						if (!exphone.test(userTelphone)) {
							/* 提示信息，弹出显示6秒后隐藏 */
							$("#message").html("[提示]：手机号格式不正确！").show(20).delay(2000).hide(20);
							return false;
						}
						var exemail = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
						if (!exemail.test(userEmail)) {
							/* 提示信息，弹出显示6秒后隐藏 */
							$("#message").html("[提示]：邮箱格式不正确！").show(20).delay(2000).hide(20);
							return false;
						}
						return true;
					}
					/* 效验信息脚本 --end */
					/* 修改个人信息的保存按钮的点击事件--end */
				});
/* 初始化加载事件 --end */