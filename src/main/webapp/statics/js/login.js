var path = $("#path").val();

var yanzhen = null;
$(document).ready(function(){
	$("#userLogin").val("");
	$("#userPassword").val("");
	yanzhen = $("#yanzhen");
	//登录效验	
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
	yanzhen.bind("click", function() {
		refreshcode();
	});

	
	
	$(document).on("click","#btnlogin",function() {
		var userLogin = $("#userLogin").val();
		var userPassword = $("#userPassword").val();
		var success = $("#success");
		var valid = $("#valid").val();
		var autologin = $("#auto").is(':checked');
		//基本效验通过提交
		if(check()){
			$.ajax({
				type : "post",
				url:path+"/user/dologin.json",
				data:{"userLogin":userLogin,
					     "userPassword":userPassword,
					     "valid":valid,
					     "autologin":autologin
				},
				dataType : "json",
				success : function(data) {
					if (data.error == 0) {
						window.location.href = path+ "/user/"+data.message+"/main.html";
					}else if(data.error == 2){
						$(success).text("*"+data.message);
						$("#valid").val("");
						refreshcode();
					}else {						
						$(success).text("*"+data.message);
						$("#userPassword").val("");
						$("#valid").val("");
						$("#auto").prop("checked",false);
						refreshcode();
					}
				},
				error : function() {
					$(success).text("*登录失败");
					$("#userPassword").val("");
					$("#valid").val("");
					$("#auto").prop("checked",false);
					refreshcode();
				}
			});
		}
	});
	
	function check(){
		var userLogin = $("#userLogin").val();
		var userPassword = $("#userPassword").val();
		var success = $("#success");
		var valid = $("#valid").val();
		var autologin = $("#auto").is(':checked');
		if(userLogin ==null || userLogin == ""){
			success.text("*用户名不能为空!");	
			return false;  
		}
		if(userPassword ==null || userPassword == ""){
			success.text("*密码不能为空!");
			
			return false;  
		}
		if (valid == null || valid == "") {
			
			$(success).text("*验证码为空！");
			
			return false;
		}
		return true;
	}
});
