/* ����֤��ؽű� */
function getStringLength(str) {
	var endvalue=0;
	var sourcestr=new String(str);
	var tempstr;
	for (var strposition = 0; strposition < sourcestr.length; strposition ++) {
		tempstr=sourcestr.charAt(strposition);
		if (tempstr.charCodeAt(0)>255 || tempstr.charCodeAt(0)<0) {
			endvalue=endvalue+2;
		} else {
			endvalue=endvalue+1;
		}
	}
	return(endvalue);
}
function trim(str) {
	if(str==null) return "";
	if(str.length==0) return "";
	var i=0,j=str.length-1,c;
	for(;i<str.length;i++) {
		c=str.charAt(i);
		if(c!=' ') break;
	}
	for(;j>-1;j--) {
		c=str.charAt(j);
		if(c!=' ') break;
	}
	if(i>j) return "";
	return str.substring(i,j+1); 
}
function isNumber(input) {
	var isNumber = /^[\d]+$/;
	if(isNumber.test(input)){
		return true;
	}
	return false;
}

// ƥ���κοհ��ַ�
function isNotChinese(input) {
	var isNotChinese = /^[\sa-zA-Z\d]*$/;
	if(isNotChinese.test(input)){
		return true;
	}
	return false;
}

function isNullOrNumber(input) {
	var isNumber = /^[\d]*$/;
	if(isNumber.test(input)){
		return true;
	}
	return false;
}

function validateDate(date,format,alt) {
	var time=trim(date.value);
	if(time=="") return;
	var reg=format;
	var reg=reg.replace(/yyyy/,"[0-9]{4}");
	var reg=reg.replace(/yy/,"[0-9]{2}");
	var reg=reg.replace(/MM/,"((0[1-9])|1[0-2])");
	var reg=reg.replace(/M/,"(([1-9])|1[0-2])");
	var reg=reg.replace(/dd/,"((0[1-9])|([1-2][0-9])|30|31)");
	var reg=reg.replace(/d/,"([1-9]|[1-2][0-9]|30|31))");
	var reg=reg.replace(/HH/,"(([0-1][0-9])|20|21|22|23)");
	var reg=reg.replace(/H/,"([0-9]|1[0-9]|20|21|22|23)");
	var reg=reg.replace(/mm/,"([0-5][0-9])");
	var reg=reg.replace(/m/,"([0-9]|([1-5][0-9]))");
	var reg=reg.replace(/ss/,"([0-5][0-9])");
	var reg=reg.replace(/s/,"([0-9]|([1-5][0-9]))");
	reg=new RegExp("^"+reg+"$");
	if(reg.test(time)==false) {// ��֤��ʽ�Ƿ�Ϸ�
		alert(alt);
		date.focus();
		return false;
	}
	return true;
}
function validateDateGroup(year,month,day,alt) {
	var array=new Array(31,28,31,30,31,30,31,31,30,31,30,31);
	var y=parseInt(year.value,10);
	var m=parseInt(month.value,10);
	var d=parseInt(day.value,10);
	var maxday=array[m-1];
	if(m==2) {
		if((y%4==0&&y%100!=0)||y%400==0) {
			maxday=29;
		}
	}
	if(d>maxday) {
		alert(alt);
		return false;
	}
	return true;
}
function validateCheckbox(obj,alt) {
	var rs=false;
	if(obj!=null) {
		if(obj.length==null) {
			return obj.checked;
		}
		for(i=0;i<obj.length;i++) {
			if(obj[i].checked==true) {
				return true;
			}
		}
	}
	alert(alt);
	return rs;
}
function validateRadio(obj,alt) {
	var rs=false;
	if(obj!=null) {
		if(obj.length==null) {
			return obj.checked;
		}
		for(i=0;i<obj.length;i++) {
			if(obj[i].checked==true) {
				return true;
			}
		}
	}
	alert(alt);
	return rs;
}
function validateSelect(obj,alt) {
	var rs=false;
	if(obj!=null) {
		for(i=0;i<obj.options.length;i++) {
			if(obj.options[i].selected==true) {
				return true;
			}
		}
	}
	alert(alt);
	return rs;
}



function validateEmails(email,alt,separator) {
	var mail=trim(email.value);
	alert("mail");
	alert(mail);
	if(mail=="") return;
	var em;
	// var myReg = /^[_a-z0-9]+@([_a-z0-9]+\.)+[a-z0-9]{2,3}$/;
	 var myReg =/^[a-zA-Z0-9_\-]{1,}@[a-zA-Z0-9_\-]{1,}\.[a-zA-Z0-9_\-.]{2,3}$/;
	if(separator==null) {
		if(myReg.test(email.value)==false) {
			alert(alt);
			email.focus();
			return false;
		}
	} else {
		em=email.value.split(separator);
		for(i=0;i<em.length;i++) {
			em[i]=em[i].trim();
			if(em[i].length>0&&myReg.test(em[i])==false) {
				alert(alt);
				email.focus();
				return false;
			}
		}
	}
	return true;
}

// ��֤Email
function validateEmail(input){
	var myReg =/^[a-zA-Z0-9_\-]{1,}@[a-zA-Z0-9_\-]{1,}\.[a-zA-Z0-9_\-.]{2,3}$/;
	if(myReg.test(input)){
		return true;
	}
	return false;
}

// ��֤�ֻ�����
function validateMobile(input){
	var myReg = /^(?:13\d|15\d|18\d)-?\d{5}(\d{3}|\*{3})$/;
	if(myReg.test(input)){
		return true;
	}
	return false;
}

// ��֤�������
function validateFax(input){
	var myReg = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
	if(myReg.test(input)){
		return true;
	}
	return false;
}

// ��֤�绰����
function validateTel(input){
	var myReg = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
	if(myReg.test(input)){
		return true;
	}
	return false;
}

// ��֤�ʱ�
function validatePost(input){
	var myReg  =/^[0-9]{6}$/;
	if(myReg.test(input)){
		return true;
	}
	return false;
}

// ��֤���֤��
function  checkIDCard(str){
	// ���֤������ʽ(15λ)
	isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{2}[\0-9xX]{1}$/; 
	// ���֤17λ
	isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}[0-9xX]{1}$/;
	
	if((isIDCard1.test(str)||isIDCard2.test(str))){
		return true;
	}
	return false;
  } 


/* ��֤������ͨ���򷵻�true */
function validateForm(theForm) {
	var disableList=new Array();
	var field = theForm.elements;// �����е�����Ԫ�ط�������
	for(var i = 0; i < field.length; i++) {

		// ���ж��Ƿ��趨�˲���֤��
		var vali=theForm.validate;
		if(vali!=null) {
			if(vali.value=="0") {
				var fun=vali.functionName;
				if(fun!=null) {
					return eval(fun+"()");
				} else {
					return true;
				}
			}
		}

		var empty=false;
		var value=trim(field[i].value);
		if(value.length==0) {// �Ƿ��ֵ
			empty=true;
		}
		var notFocus=field[i].notFocus;// ���ý���
		if (notFocus!=null) {
			notFocus = true;
		}
		var emptyInfo=field[i].emptyInfo;// ��ֵ��֤
		if(emptyInfo!=null&&empty==true) {
			alert(emptyInfo);
			if (!notFocus){
				field[i].focus();
			}
			return false;
		}
		var nfEmptyInfo=field[i].emptyInfo;// ��focus�Ŀ�ֵ��֤
		if(nfEmptyInfo!=null&&empty==true) {
			alert(nfEmptyInfo);
			return false;
		}
		var lengthInfo=field[i].lengthInfo;// ��󳤶���֤
		if(lengthInfo!=null&&getStringLength(value)>field[i].maxLen) {
			alert(lengthInfo);
			field[i].focus();
			return false;
		}
		var lengthMinInfo=field[i].lengthMinInfo;// ��С������֤
		if(lengthMinInfo!=null&&getStringLength(value)<field[i].minLen) {
			alert(lengthMinInfo);
			field[i].focus();
			return false;
		}
		var numberInfo=field[i].numberInfo;// �Ƿ���������
		if (numberInfo!=null) {
			if (!isNumber(value)) {
				alert(numberInfo);
				field[i].select();
				return false;
			}
		}

		var notChineseInfo=field[i].notChineseInfo;// ƥ������»��ߵ��κε����ַ�
		if (notChineseInfo!=null) {
			if (!isNotChinese(value)) {
				alert(notChineseInfo);
				field[i].select();
				return false;
			}
		}
		
		var noChineseInfo=field[i].noChineseInfo;// �Ƿ�Ϊ����
		if (noChineseInfo!=null) {
			if (!isNotChinese(value)) {
				alert(noChineseInfo);
				field[i].focus();
				return false;
			}
		}
		
		var numberOrNullInfo=field[i].numberOrNullInfo;// �Ƿ��ǿջ���������
		if (numberOrNullInfo!=null) {
			if (!isNullOrNumber(value)) {
				alert(numberOrNullInfo);
				field[i].select();
				return false;
			}
		}
		
		var telValidatorInfo = field[i].telValidatorInfo; // ��֤��ͨ�绰����
		if(telValidatorInfo != null){
			if(!validateTel(value)&&value.length!=0){
				alert(telValidatorInfo);
				field[i].focus();
				return false;
			}
		}

		var mobileValidatorInfo = field[i].mobileValidatorInfo; // ��֤�ֻ�����
		if(mobileValidatorInfo != null){
			if(!validateMobile(value)&&value.length!=0){
				alert(mobileValidatorInfo);
				field[i].focus();
				return false;
			}
		}

		var faxValidatorInfo = field[i].faxValidatorInfo; // ��֤�������
		if(faxValidatorInfo != null){
			if(!validateFax(value)&&value.length!=0){
				alert(faxValidatorInfo);
				field[i].focus();
				return false;
			}
		}

		var emailValidatorInfo = field[i].emailValidatorInfo; // ��֤Email
		if(emailValidatorInfo != null){
			if(!validateEmail(value) && value.length != 0){
				alert(emailValidatorInfo);
				field[i].focus();
				return false;
			}
		}

		var postValidatorInfo = field[i].postValidatorInfo; // ��֤�ʱ�
		if(postValidatorInfo != null){
			if(!validatePost(value) && value.length != 0){
				alert(postValidatorInfo);
				field[i].focus();
				return false;
			}
		}

		var IDCardtValidatorInfo = field[i].IDCardtValidatorInfo; // ��֤���֤��
		if(IDCardtValidatorInfo != null){
			if(!checkIDCard(value) && value.length != 0){
				alert(IDCardtValidatorInfo);
				field[i].focus();
				return false;
			}
		}

		var validatorType=field[i].validatorType;
		if(validatorType!=null) {// ����javascript
			var rs=true;
			if(validatorType=="javascript") {
				eval("rs="+field[i].functionName+"()");
				if(rs==false) {
					return false;
				} else {
					continue;
				}
			} else if (validatorType=="disable") {// �ύ��ǰdisable�İ�ť
				disableList.length++;
				disableList[disableList.length-1]=field[i];
				continue;
			} else if (validatorType=="date") {
				rs=validateDate(theForm.elements(field[i].fieldName),field[i].format,field[i].errorInfo);
			} else if (validatorType=="dateGroup") {
				rs=validateDateGroup(theForm.elements(field[i].year),theForm.elements(field[i].month),theForm.elements(field[i].day),field[i].errorInfo);
			} else if(validatorType=="timeCmp") {
			    rs=comparetime3(theForm.elements(field[i].startdate).value,theForm.elements(field[i].enddate).value,field[i].errorInfo);
		    } else if (validatorType=="checkbox") {
				rs=validateCheckbox(theForm.elements(field[i].fieldName),field[i].errorInfo);
			} else if (validatorType=="radio") {
				rs=validateRadio(theForm.elements(field[i].fieldName),field[i].errorInfo);
			} else if (validatorType=="select") {
				rs=validateSelect(theForm.elements(field[i].fieldName),field[i].errorInfo);
			} else if (validatorType=="email") {
				rs=validateEmails(theForm.elements(field[i].fieldName),field[i].EmailErrorInfo);
			}else {
				alert("��֤���Ͳ���֧��, fieldName: "+field[i].name);
				return false;
			}
			
			if(rs==false) {
				return false;
			}
		} else {// һ����֤
			if(empty==false) {
				var v = field[i].validator; // ��ȡ��validator����
				if(!v) continue;            // ��������Բ�����,���Ե�ǰԪ��
				var reg=new RegExp(v);
				if(reg.test(field[i].value)==false) {
					alert(field[i].errorInfo);
					field[i].focus();
					return false;
				}
			}
		}
	}
	for(i=0;i<disableList.length;i++) {
		disableList[i].disabled=true;
	}
	return true;
}
/* iframe �߶�����Ӧ */
var getFFVersion=navigator.userAgent.substring(navigator.userAgent.indexOf("Firefox")).split("/")[1]
var FFextraHeight=getFFVersion>=0.1? 16 : 0 

function dyniframesize(iframename) {
  var pTar = null;
  if (document.getElementById){
    pTar = document.getElementById(iframename);
  }
  else{
    eval('pTar = ' + iframename + ';');
  }
  if (pTar && !window.opera){
    // begin resizing iframe
    pTar.style.display="block"
    
    if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){
      // ns6 syntax
      pTar.height = pTar.contentDocument.body.offsetHeight+FFextraHeight; 
    }
    else if (pTar.Document && pTar.Document.body.scrollHeight){
      // ie5+ syntax
      pTar.height = pTar.Document.body.scrollHeight;
    }
  }
}
// CheckBoxɾ��
function del(formname,cbname){
	
	var a = document.getElementsByName(cbname);
	var sum=0;
	var c=false;
	for(i=0;i< a.length; i++)
	{
		if(a[i].checked){
		sum++;
		}
	}
	if(sum>0)
	{
	c=window.confirm('ȷ��ɾ����')
		if(c)
		{
		document.forms(formname).submit();
		}
	}
	else
	{
	alert('����û��ѡ��');
	}	
}
// CheckBox����
function docheck(formname,cbname){
	
	var a = document.getElementsByName(cbname);
	var c=false;
	for(i=0;i< a.length; i++)
	{
		if(a[i].checked){
		sum++;
		}
	}
	if(sum>0)
	{
	document.forms(formname).submit();
	}
	else
	{
	alert('����û��ѡ��');
	}	
}
// �ж�ҳ�������������ֵ�Ƿ���ͬ,��ͬ��ʾ����,��ִͬ�в���
function unequal(formname,inputname0,inputname2)
{
	var a = trim(inputname0.value);
	var b = trim(inputname2.value);
	if(a==b)
	{
	alert('�������ظ�');	
	}
	else
	{
	document.forms(formname).submit();
	}
}
// �ж�ʱ���Ⱥ�˳��,aΪǰ���ʱ��,bΪ�����ʱ��,��������ʾ
function comparetime(a,b)
{
	var time1 = a.substring(0,4).concat(a.substring(5,7)).concat(a.substring(8,10));
	var time2 = b.substring(0,4).concat(b.substring(5,7)).concat(b.substring(8,10));
	if((time1)>time2)
	{
		alert('ʱ��˳�����');
		return false;
	}
	else
	{	
		return true;
	}
}

// ,a,b,
function comparetime3(a,b,alt)
{
	var time1 = a.substring(0,2).concat(a.substring(3,5)).concat(a.substring(6,10));
	var time2 = b.substring(0,2).concat(b.substring(3,5)).concat(b.substring(6,10));
	
	if(time2 == null || time2 == "")
	{
		return true;
	}
	else if((time1)>time2)
	{
		alert(alt);
		return false;
	}
	else
	{	
		return true;
	}
}



// Cookie����

function Setcookie (name, value) { // ��������Ϊname,ֵΪvalue��Cookie
	document.cookie = name + "=" + value + "";
} 

function Deletecookie (name) { // ɾ������Ϊname��Cookie
	var exp = new Date(); 
	exp.setTime (exp.getTime() - 1); 
	var cval = GetCookie (name); 
	document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString(); 
} 

function getCookieVal (offset) { // ȡ��������Ϊoffset��cookieֵ
	var endstr = document.cookie.indexOf (";", offset); 
	if (endstr == -1) 
	endstr = document.cookie.length; 
	return unescape(document.cookie.substring(offset, endstr)); 
} 

function GetCookie (name) { // ȡ������Ϊname��cookieֵ
	var arg = name + "="; 
	var alen = arg.length; 
	var clen = document.cookie.length; 
	var i = 0; 
	while (i < clen) { 
		var j = i + alen; 
		if (document.cookie.substring(i, j) == arg) 
		return getCookieVal (j); 
		i = document.cookie.indexOf(" ", i) + 1; 
		if (i == 0) break; 
	} 
	return null; 
} 

function comparetime2(a,a2,a3,b,b2,b3){ // �Ƚ�ʱ��˳�򣬾�ȷ������
	var time1 = a.substring(0,4).concat(a.substring(5,7)).concat(a.substring(8,10)).concat(a2).concat(a3);
	var time2 = b.substring(0,4).concat(b.substring(5,7)).concat(b.substring(8,10)).concat(b2).concat(b3);

	if((time1)>time2)
	{
		alert('ʱ��˳�����');
		return false;
	}
	else
	{	
		return true;
	}
}

/* �ж�ѡ�����ظ��� */
function checkOptionSame(formSrc, optNum, optPre) {
	var z=0;
	var a=new Array(optNum);
	for (var i=0; i<optNum; i++) {
		var index = i+1;
		a[i] = formSrc[optPre + index].value;
		if (a[i]=="") {
			z = i;
			break;
		}
	}
	for(var i=0; i<z; i++) {
		for (var j=i+1; j<=z; j++) {
			if(a[i]==a[j]) {
				alert("ѡ�������ظ������ݣ�");
				return false;
			}
		}
	}
	return true;
}

/* div ��ʾ������ؽű� */
function changediv(divid) {
	clearalldiv();
	showdiv(divid);
}
function cleardiv(divid) {
	document.getElementById(divid).style.display="none";
}
function showdiv(divid) {
	document.getElementById(divid).style.display="";
}