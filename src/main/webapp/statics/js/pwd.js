/*初始化加载事件 -- start*/
$(document).ready(
		function() {
			var path = $("#path").val();
			var message1 = $("#message1");
			var message2 = $("#message2");
			var message3 = $("#message3");
			var beforePassword = $("#beforePassword").val();
			var tbPassword = $("#tbPassword").val();
			var againPassword = $("#againPassword").val();
			/* 输入框获得失焦,焦点事件--start */
			var flag1 = false;
			$("#beforePassword").bind(
					"blur",
					function() {
						if ($("#beforePassword").val() != null
								&& $("#beforePassword").val() != "") {

							$.ajax({
								url : path + "/user/expwd.json",
								data : {
									"userPassword" : $("#beforePassword").val()
								},
								type : "POST",
								dataType : "json",
								success : function(data) {

									if (data.error == 0) {
										message1.css("color", "green");
										$(message1).attr("class",
												"icon iconfont icon-success");
										message1.text("");
										flag1 = true;
									} else {
										message1.css("color", "red");
										$(message1).attr("class",
												"icon iconfont icon-error");
										message1.text(data.message);
										flag1 = false;

									}
									if ($("#tbPassword").val() == $(
											"#beforePassword").val()) {
										message2.css("color", "red");
										$(message2).attr("class",
												"icon iconfont icon-error");
										message2.text("新密码不能和旧密码相同");
										flag2 = false;

									}
								},
								error : function() {
									
									$("#message").html("与服务器失去连接...").show(20);
								}
							});
						} else {
							message1.css("color", "red");
							$(message1).attr("class", "");
							message1.text("6-16位，由字母、数字、符号组成");
							flag1 = false;

						}
					}).bind("focus", function() {
				// 显示友情提示
				message1.css("color", "red");
				$(message1).attr("class", "");
				message1.text("7-16位，由字母、数字、符号组成");
			}).focus();
			var flag2 = false;
			$("#tbPassword")
					.bind(
							"blur",
							function() {
								if ($("#tbPassword").val() != $(
										"#againPassword").val()) {
									message3.css("color", "red");
									$(message3).attr("class",
											"icon iconfont icon-error");
									message3.text("两次密码不一致！");
									flag3 = false;

								}
								if ($("#againPassword").val() != null
										&& $("#againPassword").val() != ""
										&& $("#againPassword").val() == $(
												"#tbPassword").val()) {
									message3.css("color", "green");
									$(message3).attr("class",
											"icon iconfont icon-success");
									message3.text("");
									flag3 = true;

								}
								if ($("#tbPassword").val() != null
										&& $("#tbPassword").val() != ""
										&& $("#tbPassword").val().length > 6 
										&& $("#tbPassword").val().length <17 
										&& $("#tbPassword").val() != $(
												"#beforePassword").val()) {

									message2.css("color", "green");
									$(message2).attr("class",
											"icon iconfont icon-success");
									message2.text("");
									flag2 = true;
								} else {

									if ($("#tbPassword").val() == $(
											"#beforePassword").val()) {
										message2.css("color", "red");
										$(message2).attr("class",
												"icon iconfont icon-error");
										message2.text("新密码不能和旧密码相同");
										flag2 = false;

									} else {
										message2.css("color", "red");
										$(message2).attr("class", "");
										message2.text("7-16位，由字母、数字、符号组成");
										flag2 = false;
									}
								}
							}).bind("focus", function() {
						// 显示友情提示
						$('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
						message2.css("color", "red");
						$(message2).attr("class", "");
						message2.text("7-16位，由字母、数字、符号组成");
						$('#tbPassword').keyup();
					});

			var flag3 = false;
			$("#againPassword")
					.bind(
							"blur",
							function() {
								if ($("#againPassword").val() != null
										&& $("#againPassword").val() != ""
										&& $("#againPassword").val() == $(
												"#tbPassword").val()) {
									message3.css("color", "green");
									$(message3).attr("class",
											"icon iconfont icon-success");
									message3.text("");
									flag3 = true;

								} else {
									message3.css("color", "red");
									$(message3).attr("class", "");
									message3.text("请重复输入新密码");
									flag3 = false;

								}
								if ($("#againPassword").val() != $(
										"#tbPassword").val()) {
									message3.css("color", "red");
									$(message3).attr("class",
											"icon iconfont icon-error");
									message3.text("两次密码不一致！");
									flag3 = false;

								}
							}).bind("focus", function() {
						message3.css("color", "red");
						$(message3).attr("class", "");
						message3.text("请重复输入新密码");
					});
			/* 输入框获得失焦,焦点事件--end */

			/* 保存按钮事件--start */
			$("#savebtn").click(function() {

				if (flag1 == true && flag2 == true && flag3 == true) {
					$.ajax({
						url : path + "/user/updatepwd.json",
						data : {
							"nowUserPassword" : $("#tbPassword").val()
						},
						type : "POST",
						dataType : "json",
						success : function(data) {
							if (data.error == 0) {

								$("#message").html("修改成功<br/>1.5秒后自动返回登录页").show(20);
								setTimeout(close,1500);
								

							} else {
								
								$("#message").html(data.message).show(20).delay(1500).hide(20);
							}

						},
						error : function() {
							
							$("#message").html("修改密码失败").show(20).delay(1500).hide(20);
						}
					});
				} else {
					return false;
				}
				
			});
			/*关闭窗体*/
			function close (){
				
				artDialog.data("returnValue", 1);
				top.art.dialog({
					id : "updatePwd"
				}).close();
			}				
			/* 保存按钮事件--end */
			

			/* 输入框键盘按键弹起事件-start */
			$('#tbPassword').keyup(function() {
				var __th = $(this);

				if (!__th.val()) {
					$('#pwd_tip').hide();
					$('#pwd_err').show();
					Primary();
					return;
				}
				if (__th.val().length < 6) {
					$('#pwd_tip').hide();
					$('#pwd_err').show();
					Weak();
					return;
				}
				var _r = checkPassword(__th);
				if (_r < 1) {
					$('#pwd_tip').hide();
					$('#pwd_err').show();
					Primary();
					return;
				}

				if (_r > 0 && _r < 2) {
					Weak();
				} else if (_r >= 2 && _r < 4) {
					Medium();
				} else if (_r >= 4) {
					Tough();
				}

				$('#pwd_tip').hide();
				$('#pwd_err').hide();
			});
			/* 输入框键盘按键弹起事件-end */
			
			/* 添加密码强度css样式脚本- start */
			function Primary() {
				$('#pwdLevel_1').attr('class', 'ywz_zhuce_huixian');
				$('#pwdLevel_2').attr('class', 'ywz_zhuce_huixian');
				$('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
			}

			function Weak() {
				$('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
				$('#pwdLevel_2').attr('class', 'ywz_zhuce_huixian');
				$('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
			}

			function Medium() {
				$('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
				$('#pwdLevel_2').attr('class', 'ywz_zhuce_hongxian2');
				$('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
			}

			function Tough() {
				$('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
				$('#pwdLevel_2').attr('class', 'ywz_zhuce_hongxian2');
				$('#pwdLevel_3').attr('class', 'ywz_zhuce_hongxian3');
			}
			/* 添加密码强度css样式脚本 -end */
			function corpses(pwdinput) {
				var cat = /./g
				var str = $(pwdinput).val();
				var sz = str.match(cat)
				for (var i = 0; i < sz.length; i++) {
					cat = /\d/;
					maths_01 = cat.test(sz[i]);
					cat = /[a-z]/;
					smalls_01 = cat.test(sz[i]);
					cat = /[A-Z]/;
					bigs_01 = cat.test(sz[i]);
					if (!maths_01 && !smalls_01 && !bigs_01) {
						return true;
					}
				}
				return false;
			}
			/* ~ */

			/* 效验密码强度 ==start */
			function checkPassword(pwdinput) {
				var maths, smalls, bigs, corps, cat, num;
				var str = $(pwdinput).val()
				var len = str.length;

				var cat = /.{16}/g
				if (len == 0)
					return 1;
				if (len > 16) {
					$(pwdinput).val(str.match(cat)[0]);
				}
				cat = /.*[\u4e00-\u9fa5]+.*$/
				if (cat.test(str)) {
					return -1;
				}
				cat = /\d/;
				var maths = cat.test(str);
				cat = /[a-z]/;
				var smalls = cat.test(str);
				cat = /[A-Z]/;
				var bigs = cat.test(str);
				var corps = corpses(pwdinput);
				var num = maths + smalls + bigs + corps;

				if (len < 6) {
					return 1;
				}

				if (len >= 6 && len <= 8) {
					if (num == 1)
						return 1;
					if (num == 2 || num == 3)
						return 2;
					if (num == 4)
						return 3;
				}

				if (len > 8 && len <= 11) {
					if (num == 1)
						return 2;
					if (num == 2)
						return 3;
					if (num == 3)
						return 4;
					if (num == 4)
						return 5;
				}

				if (len > 11) {
					if (num == 1)
						return 3;
					if (num == 2)
						return 4;
					if (num > 2)
						return 5;
				}
			}
			/* 效验密码强度 ==end */

			$("#closebtn").click(function() {
				top.art.dialog({
					id : "updatePwd"
				}).close();

			});
		});
/* 初始化加载事件 -- end */

