//重置表单
function resetForm(form){
	//document.getElementById(form.name).reset();//这种方式得到form失败！传参数时将form的name传过来
	form.reset();
}