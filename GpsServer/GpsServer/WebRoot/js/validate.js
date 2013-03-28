// 验证电话号码
function validateTel(input){
	var myReg = /^(([0\+]\d{2,3}-{0,1})?(0\d{2,3})-{0,1})?(\d{7,8})(-(\d{3,}))?$/;
	return myReg.test(input);
}

// 验证手机号码
function validateMobile(input) {
	var tmp = /^1[3-9]\d{9}$/; // 11位手机号码验证
	return tmp.test(input);
}

// 验证是否是电话号码或者手机号码
function validatePhone(input){
	if(!validateTel(input) && !validateMobile(input)){
		return false;
	}
	return true;
}

// 只能是数字和字母
function validateString(input){
	var exp = /^[A-Za-z0-9]+$/;
	return exp.test(input);
}



