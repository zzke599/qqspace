var path = $("#path").val();
var userLogin = null;
var userPasswrod = null;
var valid = null;
var savebtn = null;
var yanzhen = null;
var error_login = null;
var success = null;
/*初始化加载事件 -- start*/
$(document).ready(function() {
					userLogin = $("#login");
					userPasswrod = $("#userPwd");
					valid = $("#valid");
					yanzhen = $("#yanzhen");
					savebtn = $("#savebtn");
					error_login = $("#error_login");
					success = $("#success");
					// 用户名友情提示
					$(error_login).css("color", "#CC9900");
					$(error_login).text("*用户名可以为手机号、邮箱和首字母为英文的名字");

					// 首次获取验证码
					$(yanzhen).attr("src", path + "/seccode/getverifycode");
					// 获取验证码
					function refreshcode() {
						var time = new Date();
						$(yanzhen).attr(
								"src",
								path + "/seccode/getverifycode?time="
										+ time.getTime());
					}
					;
					yanzhen.bind("click", function() {
						refreshcode();
					}

					);

					/* 验证 失焦 jquery的方法传递 -- start*/
					var flag;
					userLogin
							.bind(
									"blur",
									function() {
										var exemail = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
										var exphone = /^1[3456789]\d{9}$/;
										var exfirst = /^[a-zA-Z]{1}/;
										var login = $("#login").val();
										if (login == "" || login == null) {
											$(error_login).css("color", "red");
											$(error_login).text("*用户名不能为空！");
											flag = "null";
											return;
										}
										if (exemail.test(userLogin.val())
												|| exphone
														.test(userLogin.val())
												|| exfirst
														.test(userLogin.val())
												&& 5 < userLogin.val().length) {
											$.post(path + "/user/existlogin.json", {
												userLogin : login
											}, exlogin, "json");

											function exlogin(data) {
												if (data.userLogin == "exist") {
													$(error_login).css("color",
															"red");
													$(error_login).text(
															"*该用户名已被使用！");
													flag = "exist";
												} else {
													$(error_login).css("color",
															"green");
													$(error_login).text(
															"*该用户名可以使用！");
													flag = "noexist";
												}
											}
										} else {
											$(error_login).css("color", "red");
											$(error_login).text("*用户名格式错误！");
											flag = "formaterror";
											return false;
										}
									});
					/* 验证 失焦 jquery的方法传递 -- end*/
					// 页面的跳转
					function go() {
						window.location.href = path + "/user/login.html";
					}
					/*注册提交按钮事件-- start*/
					savebtn.bind("click", function() {
						var _userLogin = userLogin.val();
						var _userPasswrod = userPasswrod.val();
						var _valid = valid.val();
						if (_userLogin == null || _userLogin == "") {
							$(error_login).css("color", "red");
							$(error_login).text("*用户名不能为空！");
							flag = "null";
						}
						if (flag == "null") {
							$(success).css("color", "red");
							$(success).text("*用户名为空！");
							return false;
						} else if (flag == "exist") {
							$(success).css("color", "red");
							$(success).text("*用户名已被使用！");
							refreshcode();
							return false;
						} else if (flag == "formaterror") {
							$(success).css("color", "red");
							$(success).text("*用户名格式错误！");
							refreshcode();
						}
						if (_userPasswrod == null || _userPasswrod == "") {
							$(success).css("color", "red");
							$(success).text("*密码为空！");
							
							return false;
						}
						if( 7 > userPasswrod.val().length || userPasswrod.val().length >16 ){
							$(success).css("color", "red");
							$(success).text("*密码长度在7 - 16位");
							refreshcode();
							return false;
						}
						if (_valid == null || _valid == "") {
							$(success).css("color", "red");
							$(success).text("*验证码为空！");
							
							return false;
						}

						if (flag == "noexist") {

							$.ajax({							
								url : path + "/user/doregister.json",						
								data : {
									"userLogin" : _userLogin,
									"userPassword" : _userPasswrod,
									"vaild" : _valid.toUpperCase()
								},
								type : "post",
								dataType : "json",
								success : function(data) {
									var str;
									if (data.success == "succeed") {
										str = "*注册成功,正在跳转至登录页!";
										$(success).css("color", "green");
										$(success).text(str);
										setTimeout(go, 700);// 1秒后页面跳转登录页面
									} else if (data.error == "errorExlogin") {
										str = "*注册失败,账号已存在！";
										$(success).css("color", "red");
										$(success).text(str);
										userLogin.val("");
										 userPasswrod.val("");
										 valid.val("");
										refreshcode();
									} else if (data.error == "errorCode") {
										str = "*注册失败,验证码错误！";
										$(success).css("color", "red");
										$(success).text(str);
										 valid.val("");
										refreshcode();
									}

								},
								error : function(data) {
									$(success).css("color", "red");
									$(success).text("*注册失败,未知异常！");

								}
							});
						}

					});
					/*注册提交按钮事件-- end*/
				});
/*初始化加载事件 -- start*/