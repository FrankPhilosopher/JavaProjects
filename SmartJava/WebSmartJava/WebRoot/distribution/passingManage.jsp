<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<title>配送点-物流管理系统</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<style media="all" type="text/css">
@import "../style/all.css";
</style>
	<script type="text/javascript" src="../script/jquery-1.3.2.min.js"></script>
	<script type="text/javascript">
	<!--
		function reinitIframe(){
			var iframe = document.getElementById("frame_content");
			try{
				var bHeight = iframe.contentWindow.document.body.scrollHeight;
				var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
				var height = Math.max(bHeight, dHeight);
				iframe.style.height = height;
			}catch (ex){}
		}
		$(function(){
			window.setInterval("reinitIframe()", 200);
			$("#left-column h3").bind("click",function(){
				var t = $(this);
				t.toggleClass('link');
				t.next().toggle();
			});
		});
	//-->
	</script>
	</head>
	<body>
<div id="main">
      <div id="header"> <a href="index.html" class="logo"><img src="../style/img/logo.png" width="200" height="29" alt="" /></a>
    <ul id="top-navigation">
          <li><span><span><a href="index.html">首页</a></span></span></li>
          <li><span><span><a href="placepriceManager.html">配送范围及价格管理</a></span></span></li>
          <li><span><span><a href="orderManager.html">订单管理</a></span></span></li>
          <li><span><span><a href="vehicleManage.html">车辆管理</a></span></span></li>
          <li><span><span><a href="loadManage.html">装货管理</a></span></span></li>
          <li><span><span><a href="unloadManage.html">卸货管理</a></span></span></li>
          <li class="active"><span><span><a href="passingManage.html">交接单管理</a></span></span></li>
          <li><span><span><a href="userManager.html">人员管理</a></span></span></li>
          <li><span><span><a href="excelManager.html">报表管理</a></span></span></li>
        </ul>
    <div id="operator"class="active">配送员&nbsp;<span>admin</span>&nbsp;<a href="#">退出</a></div>
  </div>
     <div id="middle">
		<div id="left-column">
			<h3>交接单管理</h3>
			<ul class="nav">
                <li><a href="passingSheet.html" target="center-frame">查询本地车辆交接单</a></li>			
			</ul>						
		</div>
	  <div id="center-column">
	    <iframe id="frame_content" name="center-frame" src="passingSheet.html" frameborder="0" scrolling="no" allowtransparency="true" style="width:100%;height:500px;margin:0 0;padding:0 0;"></iframe>
	  </div>
	</div>
	<div id="footer"></div>
</div>
<div id="copyright">Copyright &copy;2012 中软实训第六小组</div>
</body>
</html>