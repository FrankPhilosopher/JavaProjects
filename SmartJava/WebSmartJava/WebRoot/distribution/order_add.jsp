<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="manager.DistributionManager"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="entity.User"%>
<%@page import="entity.Distribution"%>
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
		//判空
		//function CheckForm(){ 
			//收件人
			var tName = document.all.tName; 
			var tPhone = document.all.tPhone; 
			var tcity = document.all.tcity; 
			var tcounty = document.all.tcounty; 
			//寄件人	
			var sName = document.all.sName; 	
			var sPhone = document.all.sPhone; 
			var scity = document.all.scity; 
			var scounty = document.all.scounty; 	

			//重量体积
			var weight = document.all.weight; 
			var volumn = document.all.volumn; 	
			//类别
			var goodsType = document.all.goodsType;	
			//日期
			var workDate = document.all.workDate;
			
			if(tName.value.length==0||tPhone.value.length==0||
					sName.value.length==0||sPhone.value.length==0||
					weight.value.length==0||volumn.value.length==0||
					goodsType.value.length==0||scity.value.length==0||
					tcity.value.length==0||scounty.value.length==0||
					tcounty.value.length==0){ 
				tName.focus(); 
				alert("温馨提示:\n带*的内容为必填!"); 
				return false; 
			}else{
				return true;
			}
		}				
	</script>
</head>
<body style="background: none">
			<div class="top-bar">
				<h1>新增订单</h1>
				<div class="breadcrumbs">
					<a href="../indexdistribution.jsp" target="_top">首页</a> / <a href="orderManager.jsp" target="_top">订单管理</a>
				</div>
			</div>
<form action="actionorder.jsp?action=order_add" method="post" onsubmit="return CheckForm();">
		  <div class="select-bar">
			<table class="select-table">
				<tr>
					<th>收件人姓名(*)</th>
					<td><input type="text" name="tName"  class="text"/></td>
					<th>收件人地址(*)</th>
					<td>.0
						<select name="tcity" style="width:70px">
					<%
						int i=0;
						ArrayList<Distribution> list=new ArrayList<Distribution>();
						DistributionManager dm=new DistributionManager();
						list=(ArrayList<Distribution>)dm.search();
						out.print("<option></option>");
						for(i=0;i<list.size();i++)
						{biezu
							out.print("<option value="+list.get(i).getAddress()+">"+list.get(i).getAddress()+"</option>");
						}	
					 %>								
					<input type="text" name="tcounty""  class="text"/>
					</td>					
				</tr>
				<tr>
					<th>收件人电话号码(*)</th>
					<td><input type="text" name="tPhone" class="text"/></td>
					<th>收件人邮编</th>
					<td><input type="text" name="tCode"  class="text"/></td>
				</tr>
				<tr>
					<th>寄件人姓名(*)</th>
					<td><input type="text" name="sName"  class="text"/></td>
					<th>寄件人地址(*)</th>
					<td>
						<select name="scity" style="width:70px">
					<%
						User user=(User)session.getAttribute("loginuser");					
						for(i=0;i<list.size();i++)
						{
							out.print("<option value="+list.get(i).getAddress()+">"+list.get(i).getAddress()+"</option>");
						}	
					 %>	
					<input type="text" name="scounty"  class="text"/>
					</td>
				</tr>
				<tr>
					<th>寄件人电话号码(*)</th>
					<td><input type="text" name="sPhone"  class="text"/></td>
					<th>寄件人邮编</th>
					<td><input type="text" name="sCode"  class="text"/></td>
				</tr>
				<tr>
					<th>寄件重量(*)</th>
					<td><input type="text" name="weight"  class="text"/>(单位:KG)</td>
					<th>寄件体积(*)</th>
					<td><input type="text" name="volumn"  class="text"/>(单位:M3)</td>
				</tr>
				
				<tr>
					<th>寄件物品类别(*)</th>
					<td>
						<select name="goodsType" style="width:70px">
							<option value="0"></option>						
							<option value="1">易碎</option>
							<option value="2">危险</option>
							<option value="3">快件</option>
							<option value="4">普邮</option>
						</select>
					</td>	
					<th>备注信息</th>
					<td>
						<input type="text" name="remark"  class="text"/>
					</td>														
				</tr>
		
				<tr>
					<th>揽件日期(*)</th>
					<td><input type='text' class="date-six text" name="workDate"  readonly/></td>					
					<th>揽件人姓名</th>
					<%
						out.print("<td>"+user.getRealname()+"</td>	");
					%>			
				</tr>
				<tr>
					<th><input type="submit" name="Submit" value="提交订单" class="button" /></th>
					<th></th>
					<th><input type="reset" name="reset" value="清空重填" class="button" /></th>
					<th></th>
				</tr> 				
			</table>
		</div>
		<p>注:运输价格计算方法(单位:元)</p>
		<p>货物运送价格=货物运输费用+货物配送费用</p>
		<p>货物运输费用=货物首公斤价格+(货物重量-1)*货物次公斤价格(不足一公斤按一公斤算)</p>
		<p>另:货物配送费用为各配送点自行决定</p>
	</form>
			
</body>
</html>
