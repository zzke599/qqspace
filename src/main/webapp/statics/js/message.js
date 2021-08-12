
	var path =$("#path").val();
	// 页面的跳转
	function go() {
		window.location.href = path + "/user/logout.html";
	}
	// 退出当前系统提示
	function doExit(){
		art.dialog({
			title:'提示',
			content:'您确定要退出系统吗？',
			ok:function(){
				go();
			},
			cancel:function(){
				
			}

		});
		return false;
	}

	// 通用提示信息
		function showMsg(msg){
			//提示信息
		  	art.dialog({
		  		id:"a",
		  		lock:true,
		  		content:msg,
		  		title: '提示信息',
		  		button: [
		        {
		            name: '确定'
		        }
		        ]
		  	});
		}
		// 无按钮提示信息
		function showTips(msg){
			//提示信息
		  	art.dialog.tips(msg);
		}
		// 通用删除提示信息
		function doDel(obj){
			art.dialog({
				title:'提示',
				content:'确定要删除吗？',
				ok:function(){
					window.location.href=obj.href;
				},
				cancel:function(){
				
				}
			});
			return false;
		}
		
		// 数据为空通用提示信息
		function dataNull(msg){
			//提示信息
		  	art.dialog({
		  		content:msg,
		  		title: '提示信息',
		  		button: [
		        {
		            name: '确定',
		            callback: function () {
		            	history.go(-1);
		               
		            },
		        }
		        ]
		  	
		  	});
		}
	
	
	
	
