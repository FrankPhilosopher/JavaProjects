/*JavaScript source file, sq */

var count;
var timer;
var curLevel = 1;
/**
 * ������Ȩ����
 * @param {Object} sim
 * @param {Object} p1
 * @param {Object} p2
 * @param {Object} p3
 */
function setPrivilege(sim,p1,p2,p3){
	if(bXmlHttpSupport){
   			var sUrl = "privilege.action?sim="+sim+"&p1="+p1+"&p2="+p2+"&p3="+p3;
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					document.getElementById("msg").innerHTML = "����Ȩ����ָ���Ѿ�����,��ȴ��ظ�...";
   					disableButtons();
   					count=0;
   					timer = window.setInterval(checkResult,5000);
   				}
   			};
   			oRequest.open("POST",sUrl,true);
   			oRequest.send(null);
   		}
}

/**
 * 
 * @param {Object} sim     �ն˺���
 * @param {Object} phone    ��������
 */
function setWarnPhone(sim,phone){
	if(bXmlHttpSupport){
   			var sUrl = "warnphone.action?sim="+sim+"&phone="+phone;
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					document.getElementById("msg").innerHTML = "�󶨱�������ָ���Ѿ�����,��ȴ��ظ�...";
   					disableButtons();
   					count=0;
   					timer = window.setInterval(checkResult,5000);
   				}
   			};
   			oRequest.open("POST",sUrl,true);
   			oRequest.send(null);
   		}
}

/**
 * 
 * @param {Object} sim   �ն˺���
 * @param {Object} period   ��λʱ����
 */
function setPeriod(sim,period){
	if(bXmlHttpSupport){
   			var sUrl = "period.action?sim="+sim+"&period="+period;
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					document.getElementById("msg").innerHTML = "�����ն˶�λʱ����ָ���Ѿ�����,��ȴ��ظ�...";
   					disableButtons();
   					count=0;
   					timer = window.setInterval(checkResult,5000);
   				}
   			};
   			oRequest.open("POST",sUrl,true);
   			oRequest.send(null);
   		}
}

/**
 * ���Ͷϵ�
 * 
 * @param {Object} sim
 * @param {Object} level  1-һ������   2-��������
 */
function stopOil(sim,level){
		if(bXmlHttpSupport){
			curLevel = level;
   			var sUrl = "stopoil.action?sim="+sim+"&level="+level;
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					document.getElementById("msg").innerHTML = level+"������ָ���Ѿ�����,��ȴ��ظ�...";
   					disableButtons();
   					count=0;
   					timer = window.setInterval(lockResult,5000);
   				}
   			};
   			oRequest.open("POST",sUrl,true);
   			oRequest.send(null);
   		}
}

/**
 * �ָ��͵�
 * @param {Object} sim
 */
function recoverOil(sim){
	if(bXmlHttpSupport){
   			var sUrl = "recoveroil.action?sim="+sim;
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					document.getElementById("msg").innerHTML = "����ָ���Ѿ�����,��ȴ��ظ�...";
   					disableButtons();
   					count=0;
   					timer = window.setInterval(unlockResult,5000);
   				}
   			};
   			oRequest.open("POST",sUrl,true);
   			oRequest.send(null);
   		}
}

/**
 * ���ð�ť
 */
function disableButtons(){
	document.getElementById("b1").disabled="disabled";
   	document.getElementById("b2").disabled="disabled";
   	document.getElementById("b3").disabled="disabled";
   	document.getElementById("b4").disabled="disabled";
   	document.getElementById("b5").disabled="disabled";
   	document.getElementById("b6").disabled="disabled";
}
/**
 * ���ð�ť
 */
function enableButtons(){
	document.getElementById("b1").disabled=false;
   	document.getElementById("b2").disabled=false;
   	document.getElementById("b3").disabled=false;
   	document.getElementById("b4").disabled=false;
   	document.getElementById("b5").disabled=false;
   	document.getElementById("b6").disabled=false;
}

/**
 * ��ͨ�������
 * @return {TypeName} 
 */
   function checkResult(){
   		if(bXmlHttpSupport){
   			var sUrl = "checkResult.action?sim="+document.getElementById("hidid").value;
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					var position = eval('('+oRequest.responseText+')');
   					    count++;
   					    if(position.canstop){
   					    	enableButtons();
   					    	 window.clearInterval(timer);
   					    	 if(position.result){ //ִ�гɹ�
   					    		 document.getElementById("msg").innerHTML = "ָ��ִ�гɹ�";
   					    	 }else{
   					    		 document.getElementById("msg").innerHTML = "ָ��ִ��ʧ��,���Ժ�����!";
   					    	 }
   					    }else{
   					    	if(count>5){
   								window.clearInterval(timer);
   								document.getElementById("msg").innerHTML="�ȴ���ʱ";
   								enableButtons();
   								return;
   							}
   					    }
   				}
   			};
   			oRequest.open("POST",sUrl);
   			oRequest.send(null);
   		}
   }
/**
 * ���������
 * @return {TypeName} 
 */
   function lockResult(){
   		if(bXmlHttpSupport){
   			var sUrl = "lockResult.action?sim="+document.getElementById("hidid").value+"&level="+curLevel;
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					var position = eval('('+oRequest.responseText+')');
   					    count++;
   					    if(position.canstop){
   					    	enableButtons();
   					    	 window.clearInterval(timer);
   					    	 if(position.result){ //ִ�гɹ�
   					    		 document.getElementById("msg").innerHTML = "ָ��ִ�гɹ�";
   					    	 }else{
   					    		 document.getElementById("msg").innerHTML = "ָ��ִ��ʧ��,���Ժ�����!";
   					    	 }
   					    }else{
   					    	if(count>5){
   								window.clearInterval(timer);
   								document.getElementById("msg").innerHTML="�ȴ���ʱ";
   								enableButtons();
   								return;
   							}
   					    }
   				}
   			};
   			oRequest.open("POST",sUrl);
   			oRequest.send(null);
   		}
   }
   /**
    * �ָ��������
    * @return {TypeName} 
    */
   function unlockResult(){
   		if(bXmlHttpSupport){
   			var sUrl = "unlockResult.action?sim="+document.getElementById("hidid").value;
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					var position = eval('('+oRequest.responseText+')');
   					    count++;
   					    if(position.canstop){
   					    	enableButtons();
   					    	 window.clearInterval(timer);
   					    	 if(position.result){ //ִ�гɹ�
   					    		 document.getElementById("msg").innerHTML = "ָ��ִ�гɹ�";
   					    	 }else{
   					    		 document.getElementById("msg").innerHTML = "ָ��ִ��ʧ��,���Ժ�����!";
   					    	 }
   					    }else{
   					    	if(count>5){
   								window.clearInterval(timer);
   								document.getElementById("msg").innerHTML="�ȴ���ʱ";
   								enableButtons();
   								return;
   							}
   					    }
   				}
   			};
   			oRequest.open("POST",sUrl);
   			oRequest.send(null);
   		}
   }
/**********************************ajax  start*********************************************/
var bXmlHttpSupport = (typeof XMLHttpRequest != "undefined" || window.ActiveXObject);
if (typeof XMLHttpRequest == "undefined" && window.ActiveXObject) {
	function XMLHttpRequest() {
		var arrSignatures = [ "MSXML2.XMLHTTP.5.0", "MSXML2.XMLHTTP.4.0",
				"MSXML2.XMLHTTP.3.0", "MSXML2.XMLHTTP", "Microsoft.XMLHTTP" ];
		for ( var i = 0; i < arrSignatures.length; i++) {
			try {
				var oRrequest = new ActiveXObject(arrSignatures[i]);
				return oRrequest;
			} catch (oError) {
			}
		}
		throw new Error("MSXML is not installed on your system");
	}
}
 