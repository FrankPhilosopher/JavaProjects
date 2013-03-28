/*JavaScript source file, sq */

/*-----------------google map api service------------------*/

var map;
var marker;
var infowindow;

var arrPoints=new Array();   //��λ�㼯��
var speeds = new Array();   //�ٶȼ���
var times = new Array();    //��λʱ�伯��
var j ;
var marker;
var polyline;
var length;
var intervalId;

/**
 * ��ͼ��ʼ������λ
 * 
 * @param lng
 *            ����
 * @param lat
 *            γ��
 * @param zoom
 *            ��ͼ����
 * @param id
 *            ���ص�ͼID
 * @param status
 *            �ն�״̬
 * @param  style
 *             1-��λ 2-����
 * @return
 */
function google_location(lng, lat, zoom, id, status) {
	var myLatlng = new google.maps.LatLng(lat, lng);
	var myOptions = {
		zoom : zoom,
		center : myLatlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	//��ʼ��google��ͼ
	 map = new google.maps.Map(document.getElementById(id), myOptions);
	
		if(status=="0"){  //��ʼ������ͼ��
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
	//��ʼ��google��ͼ
	 map = new google.maps.Map(document.getElementById(id), myOptions);
}

/**********************************�켣��ѯ start*********************************************/

function initPath(zoom, id){
	var myOptions = {
	zoom : zoom,
	center : arrPoints[0],
	mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	//��ʼ��google��ͼ
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
	//��ʼ��google��ͼ
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
 * ֱ����ʾ�켣
 */
function drawline(){
	for(var i=0;i<arrPoints.length-1;i++){
		polyline=new GPolyline([arrPoints[i],arrPoints[i+1]],"#ff0000", 3);
		map.addOverlay(polyline);
	}
}

/**
 * ��ʼ��ʾ�켣
 */
function startOrbit(){
	j  = 0;
	length=arrPoints.length;
	intervalId=window.setInterval(addMarker,1000);
}

/**
 * ��ʱ��ʾ�켣
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
//�Ƴ���ǵ�
function remove(){
	map.removeOverlay(marker);
}
