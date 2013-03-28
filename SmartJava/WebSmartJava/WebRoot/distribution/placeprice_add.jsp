<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>新增订单 - 物流管理系统</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<style media="all" type="text/css">@import "../style/all.css";</style>
	<script type="text/javascript">
		function del(url){
			if(window.confirm("确认删除吗？")){
				window.location.href=url;
			}
		}
		//查空
		function CheckForm(){ 
			var placeName = document.all.placeName; 
			var pricePrice = document.all.pricePrice; 
			var placeTime = document.all.placeTime; 	
			if(placeName.value.length==0||placeTime.value.length==0||placeTime.value.length==0){ 
				placeName.focus(); 
				alert("温馨提示:\n带*内容必填"); 
				return false; 
			}else{
				return true;
			}
		}		
	</script>
</head>
<body style="background: none">
			<div class="top-bar">
				<h1>新增配送范围及价格</h1>
				<div class="breadcrumbs">
					<a href="../indexdistribution.jsp" target="_top">首页</a> / <a href="placepriceManager.jsp" target="_top">配送范围及价格管理</a>
				</div>
			</div>
<form action="actionplaceprice.jsp?action=placeprice_add" method="post" onsubmit="return CheckForm();">
		  <div class="select-bar">
			<table class="select-table">
			<tr>
					<th>输入配送范围(*)</th>
					<td><input type="text" name="placeName"  class="text"/>
					</td>
					<th>输入配送价格(*)</th>
					<td><input type="text" name="pricePrice"  class="text"/>(单位:元)</td>

				</tr>
				<tr>
					<th>输入配送时间(*)</th>
					<td><input type="text" name="placeTime"  class="text"/></td>
					<th>输入配送范围备注</th>
					<td><input type="text" name="remark"  class="text"/></td>					
				</tr>
				<tr>	
					<th></th>			
					<th><input type="submit" name="Submit" value="提交"class="button" /></th>				
					<th><input type="button" name="Submit" value="取消" class="button" onclick="javascript:history.go(-1);"/></th>
					<th></th>
				</tr>
			</table>
			</div>
	</form>
			
</body>
</html>
