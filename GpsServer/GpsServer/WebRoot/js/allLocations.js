/*JavaScript source file, sq */

/*-----------------google map api service------------------*/

 var arrPoints=new Array();  //所有定位点
 var mylat = new Array();    //纬度
 var mylong = new Array();   //经度
 var sims = new Array();    //终端号
 var speeds = new Array();   //速度
 var directions = new Array(); //方向
 var times = new Array();        //定位时间
 var statuss = new Array();       //定位状态
 var carnumbers = new Array();   //车辆编号
var map;
var infowindow;
/**
 * 地图初始化并定位
 * 
 * @param lng
 *            经度
 * @param lat
 *            纬度
 * @param zoom
 *            地图比例
 * @param id
 *            承载地图ID
 * @param status
 *            终端状态
 * @param  style
 *             1-显示标记 2-不显示标记
 * @return
 */
function google_location(id,zoom) {
	var myLatlng = new google.maps.LatLng(20.92,116.404);
	var myOptions = {
		zoom : zoom,
		center : myLatlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	//初始化google地图
	 map = new google.maps.Map(document.getElementById(id), myOptions);
	//初始化所有maker
	 for(var i=0;i<arrPoints.length;i++)
       {
       	var point = arrPoints[i];
       	var myHtml = "";
       	if(statuss[i] == 0)
      		myHtml = "<b>经度：" + mylong[i] + "  纬度："+mylat[i]+"</b><br/><b>车牌号码:"+carnumbers[i]+"</b><br/><b>终端号码:"+sims[i]+"</b><br/><b>速度:"+speeds[i]+"</b><br/><b>方向:"+directions[i]+"</b><br/><b>时间:"+times[i]+"</b><br/><b>状态:信号弱或丢失电源<b>";
       	else
       		myHtml = "<b>经度：" + mylong[i] + "  纬度："+mylat[i]+"</b><br/><b>车牌号码:"+carnumbers[i]+"</b><br/><b>终端号码:"+sims[i]+"</b><br/><b>速度:"+speeds[i]+"</b><br/><b>方向:"+directions[i]+"</b><br/><b>时间:"+times[i]+"</b><br/><b>状态:在线<b>";
       	createMarker(point,myHtml,statuss[i]);
		}
	 }

	function createMarker(point,myhtml,status) {
		var marker;
		if(status == 0){
			 marker = new google.maps.Marker( {
				position : point,
				map : map,
				icon: 'images/blue-dot.png'
			});
		}else{
			marker = new google.maps.Marker( {
				position : point,
				map : map,
				title: 'hello'
			});
		}
		var infowindow = new google.maps.InfoWindow();
		infowindow.setContent(myhtml);
		infowindow.setPosition(point);
		google.maps.event.addListener(marker,'click',function(){
				infowindow.open(map);
			});
  		return marker; 
	} 

