/*JavaScript source file, sq */

/*-----------------google map api service------------------*/

var map;
var marker;
var infowindow;

var arrPoints=new Array();   //定位点集合
var speeds = new Array();   //速度集合
var times = new Array();    //定位时间集合
var j ;
var marker;
var polyline;
var length;
var intervalId;

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
 *             1-定位 2-跟踪
 * @return
 */
function google_location(lng, lat, zoom, id, status) {
	var myLatlng = new google.maps.LatLng(lat, lng);
	var myOptions = {
		zoom : zoom,
		center : myLatlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	//初始化google地图
	 map = new google.maps.Map(document.getElementById(id), myOptions);
	
		if(status=="0"){  //初始化离线图标
		 marker = new google.maps.Marker( {
			position : myLatlng,
			map : map,
			icon: 'images/blue-dot.png'
		});
		}else{
		 marker = new google.maps.Marker( {
				position : myLatlng,
				map : map,
				title: 'hello'
			});
		}
}

function mapinit(lng, lat, zoom, id, status) {
	var myLatlng = new google.maps.LatLng(lat, lng);
	var myOptions = {
		zoom : zoom,
		center : myLatlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	//初始化google地图
	 map = new google.maps.Map(document.getElementById(id), myOptions);
}

/**********************************轨迹查询 start*********************************************/

function initPath(zoom, id){
	var myOptions = {
	zoom : zoom,
	center : arrPoints[0],
	mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	//初始化google地图
	map = new google.maps.Map(document.getElementById(id), myOptions);
	var flightPath = new google.maps.Polyline({
    path: arrPoints,
    strokeColor: "#FF0000",
    strokeOpacity: 1.0,
    strokeWeight: 2
   });
   flightPath.setMap(map);
   var mar = new google.maps.Marker( {
			position : arrPoints[0],
			map : map,
			icon: 'images/start.png',
		    title: 'start'
		});
    
   marker = new google.maps.Marker( {
		position : arrPoints[arrPoints.length-1],
		map : map,
		icon: 'images/end.png',
		title: 'end'
	});
   map.setCenter(arrPoints[arrPoints.length-1]);
}

function initStops(zoom, id){
	var myOptions = {
	zoom : zoom,
	center : arrPoints[0],
	mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	//初始化google地图
	map = new google.maps.Map(document.getElementById(id), myOptions);
//	var flightPath = new google.maps.Polyline({
//    path: arrPoints,
//    strokeColor: "#FF0000",
//    strokeOpacity: 1.0,
//    strokeWeight: 2
//   });
//   flightPath.setMap(map);
   
   for(var i=0;i<arrPoints.length;i++){
	  createMarker(arrPoints[i],times[i]);
   }
   map.setCenter(arrPoints[arrPoints.length-1]);
}

function createMarker(point,time) {
		var marker;
		 marker = new google.maps.Marker( {
			position : point,
			map : map,
			title: time
		});
		var infowindow = new google.maps.InfoWindow();
		infowindow.setContent(time);
		infowindow.setPosition(point);
		google.maps.event.addListener(marker,'click',function(){
				infowindow.open(map);
			});
  		return marker; 
	} 

/**
 * 直接显示轨迹
 */
function drawline(){
	for(var i=0;i<arrPoints.length-1;i++){
		polyline=new GPolyline([arrPoints[i],arrPoints[i+1]],"#ff0000", 3);
		map.addOverlay(polyline);
	}
}

/**
 * 开始显示轨迹
 */
function startOrbit(){
	j  = 0;
	length=arrPoints.length;
	intervalId=window.setInterval(addMarker,1000);
}

/**
 * 定时显示轨迹
 */
function addMarker(){
	if(j>=length){
		window.clearInterval(intervalId);
	}else{
		marker = new GMarker(arrPoints[j]);
		map.addOverlay(marker);
		map.panTo(arrPoints[j]);
		window.setTimeout(remove,500);
		if(j>=1){
			polyline=new GPolyline([arrPoints[j-1],arrPoints[j]],"#ff0000", 3);
			map.addOverlay(polyline);
		}
		j=j+1;	
	}
}
//移除标记点
function remove(){
	map.removeOverlay(marker);
}
