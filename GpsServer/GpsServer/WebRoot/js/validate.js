// ��֤�绰����
function validateTel(input){
	var myReg = /^(([0\+]\d{2,3}-{0,1})?(0\d{2,3})-{0,1})?(\d{7,8})(-(\d{3,}))?$/;
	return myReg.test(input);
}

// ��֤�ֻ�����
function validateMobile(input) {
	var tmp = /^1[3-9]\d{9}$/; // 11λ�ֻ�������֤
	return tmp.test(input);
}

// ��֤�Ƿ��ǵ绰��������ֻ�����
function validatePhone(input){
	if(!validateTel(input) && !validateMobile(input)){
		return false;
	}
	return true;
}

// ֻ�������ֺ���ĸ
function validateString(input){
	var exp = /^[A-Za-z0-9]+$/;
	return exp.test(input);
}



