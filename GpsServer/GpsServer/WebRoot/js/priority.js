/*JavaScript source file, sq */

var count;
var timer;
var curLevel = 1;
/**
 * 设置特权号码
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
   					document.getElementById("msg").innerHTML = "绑定特权号码指令已经发送,请等待回复...";
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
 * @param {Object} sim     终端号码
 * @param {Object} phone    报警号码
 */
function setWarnPhone(sim,phone){
	if(bXmlHttpSupport){
   			var sUrl = "warnphone.action?sim="+sim+"&phone="+phone;
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					document.getElementById("msg").innerHTML = "绑定报警号码指令已经发送,请等待回复...";
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
 * @param {Object} sim   终端号码
 * @param {Object} period   定位时间间隔
 */
function setPeriod(sim,period){
	if(bXmlHttpSupport){
   			var sUrl = "period.action?sim="+sim+"&period="+period;
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					document.getElementById("msg").innerHTML = "设置终端定位时间间隔指令已经发送,请等待回复...";
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
 * 断油断电
 * 
 * @param {Object} sim
 * @param {Object} level  1-一级锁机   2-二级锁机
 */
function stopOil(sim,level){
		if(bXmlHttpSupport){
			curLevel = level;
   			var sUrl = "stopoil.action?sim="+sim+"&level="+level;
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					document.getElementById("msg").innerHTML = level+"级锁机指令已经发送,请等待回复...";
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
 * 恢复油电
 * @param {Object} sim
 */
function recoverOil(sim){
	if(bXmlHttpSupport){
   			var sUrl = "recoveroil.action?sim="+sim;
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					document.getElementById("msg").innerHTML = "解锁指令已经发送,请等待回复...";
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
 * 禁用按钮
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
 * 启用按钮
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
 * 普通操作检测
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
   					    	 if(position.result){ //执行成功
   					    		 document.getElementById("msg").innerHTML = "指令执行成功";
   					    	 }else{
   					    		 document.getElementById("msg").innerHTML = "指令执行失败,请稍后重试!";
   					    	 }
   					    }else{
   					    	if(count>5){
   								window.clearInterval(timer);
   								document.getElementById("msg").innerHTML="等待超时";
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
 * 锁机器检测
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
   					    	 if(position.result){ //执行成功
   					    		 document.getElementById("msg").innerHTML = "指令执行成功";
   					    	 }else{
   					    		 document.getElementById("msg").innerHTML = "指令执行失败,请稍后重试!";
   					    	 }
   					    }else{
   					    	if(count>5){
   								window.clearInterval(timer);
   								document.getElementById("msg").innerHTML="等待超时";
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
    * 恢复锁机检测
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
   					    	 if(position.result){ //执行成功
   					    		 document.getElementById("msg").innerHTML = "指令执行成功";
   					    	 }else{
   					    		 document.getElementById("msg").innerHTML = "指令执行失败,请稍后重试!";
   					    	 }
   					    }else{
   					    	if(count>5){
   								window.clearInterval(timer);
   								document.getElementById("msg").innerHTML="等待超时";
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
 