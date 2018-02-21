function checkForm(form) {
	if (form.oldPwd.value == "") {
		Alert("请输入的原密码!");
		return false;
	}
	if (form.newPwd.value == "") {
		Alert("请输入的新密码!");
		return false;
	}
	if (form.newPwdConfirm.value == "") {
		Alert("请确认新密码!");
		return false;
	}
	if (form.newPwd.value != form.newPwdConfirm.value) {
		Alert("您两次输入的新密码不一致，请重新输入!");
		form.newPwd.value = "";
		form.newPwdConfirm.value = "";
		return false;
	}
	changePassword(form);
}

function showTips(txt, time, status) {
	var htmlCon = '';
	if (txt != '') {
		if (status != 0 && status != undefined) {
			htmlCon = '<div class="tipsBox" style="width:220px;padding:10px;background-color:#4AAF33;border-radius:4px;-webkit-border-radius: 4px;-moz-border-radius: 4px;color:#fff;box-shadow:0 0 3px #ddd inset;-webkit-box-shadow: 0 0 3px #ddd inset;text-align:center;position:fixed;top:25%;left:50%;z-index:999999;margin-left:-120px;"><img src="images/ok.png" style="vertical-align: middle;margin-right:5px;" alt="OK"/>'
					+ txt + '</div>';
		} else {
			htmlCon = '<div class="tipsBox" style="width:220px;padding:10px;background-color:#D84C31;border-radius:4px;-webkit-border-radius: 4px;-moz-border-radius: 4px;color:#fff;box-shadow:0 0 3px #ddd inset;-webkit-box-shadow: 0 0 3px #ddd inset;text-align:center;position:fixed;top:25%;left:50%;z-index:999999;margin-left:-120px;"><img src="images/err.png" style="vertical-align: middle;margin-right:5px;" alt="Error"/>'
					+ txt + '</div>';
		}
		$('body').prepend(htmlCon);
		if (time == '' || time == undefined) {
			time = 1500;
		}
		setTimeout(function() {
			$('.tipsBox').remove();
		}, time);
	}
}

function reset() {
	location.reload();
}

function changePassword(form) {
	$.ajax({
		url : "/console/admin.htm?action=changePassword",
		type : 'post',
		dataType : 'json',
		data : {
			oldPassword : form.oldPwd.value,
			newPassword : form.newPwd.value,
			renewPassword : form.newPwd.value
		},
		success : function(data) {
			$.messager.progress("close");
			if (data.success) {
				Alert('密码修改成功！', reset);
				// $.messager.alert("密码", "修改成功", "info");
			} else {
				Alert(data.resultMsg);
			}
		}
	});

}