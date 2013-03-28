//一些跳转方法
function help(msg){
	alert('Welcome use '+msg);
}
function to(url){
	window.location.href=url;
}
function succeed(msg,url){
	alert(msg);
	if(url) to(url);
}
function reload(){
	window.location.reload();
}
function del(url){
    if(window.confirm("确认删除吗？")){
		to(url);
	}
}
function back(){
	window.history.go(-1);
}
//复选框批量加载
function mycheckall(){
	var bool=document.getElementById('myname').checked;
	if(bool){
	//alert(bool);
	var emp=document.formTable.elements;	
	for(var i=0;i<emp.length;i++){
		if(emp[i].type=='checkbox'){
		emp[i].checked=bool;
		}
	}
					
	}else{
		var emp=document.formTable.elements;	
		for(var i=0;i<emp.length;i++){
			if(emp[i].type=='checkbox'){
			emp[i].checked=bool;
			}
		}
					
	}
}
//缺货状态验证
		function checkType(obj){
			var emp=document.formTable.elements;
			var temp=new Array();
			var n=0;
			var j=0;
			for(var i=0;i<emp.length;i++){
				if(emp[i].checked){
				  temp[n]=emp[i];
				  n++;
				}
			}
			if(n==0){
			  alert('请选择一项！');
			}else{
				for(var i=0;i<temp.length;i++){
					if(temp[i].value=="缺货"){
						alert('缺货状态不可调度！，请修改状态。');
						j++;
						return;
					}
				}
				if(j==0){
				  to(obj);
				}
			}
		  
		}

