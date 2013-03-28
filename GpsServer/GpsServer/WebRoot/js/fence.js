/*JavaScript source file, sq */

/*-----------------google map api service------------------*/

var map;
var marker;
var infowindow;
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
 *             1-�ӱ�� 2-���ӱ��
 * @return
 */
function google_location(lng, lat, zoom, id, status, style) {
	var myLatlng = new google.maps.LatLng(lat, lng);
	var myOptions = {
		zoom : zoom,
		center : myLatlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	//��ʼ��google��ͼ
	 map = new google.maps.Map(document.getElementById(id), myOptions);
	
	if(style == 1){ //��ע��ǵ�
		if(status=="2"){  //��ʼ������ͼ��
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
}


/**********************************ajax ��ȡ��ַ������Ϣ start*********************************************/
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
 /**
  * ��ַ����
  */
 function getAddress(lat,lng,point){
   		if(bXmlHttpSupport){
   			var sUrl = "getAddress.action?lat="+lat+"&lng="+lng+"&zoom="+map.getZoom();
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					var position = eval('('+oRequest.responseText+')');
   					map.openInfoWindowHtml(point,""+position.address);
   				}
   			};
   			oRequest.open("POST",sUrl,true);
   			oRequest.send(null);
   		}
   }
/**********************************ajax ��ȡ��ַ������Ϣ end*********************************************/


/*************************************�������� ��ʱ��ȡ��λ��Ϣ start************************************/
/**
 * ��������
 */
function retrievePosition(){
   		if(bXmlHttpSupport){
   			var sUrl = "/GpsServer/position.action?sim=345";
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					var position = eval('('+oRequest.responseText+')');
   					document.getElementById("p_sim").innerHTML = position.sim;
   					document.getElementById("p_station").innerHTML = position.locationMode;
   					document.getElementById("p_p_time").innerHTML = position.p_time;
   					document.getElementById("p_lati").innerHTML = position.lati_direction+position.latitude;
   					document.getElementById("p_long").innerHTML = position.long_direction+position.longitude;
   					document.getElementById("p_speed").innerHTML = position.speed;
   					document.getElementById("p_direction").innerHTML = position.direction;
   					document.getElementById("p_net").innerHTML = position.netstatus;
   					document.getElementById("p_status").innerHTML = position.pstatus;
   					if(marker != null)map.removeOverlay(marker);
   					point = new GLatLng(position.latitude,position.longitude);
   					map.setCenter(point, map.getZoom());
   					if(position.net=="2"){
		   					var blueIcon = new GIcon(G_DEFAULT_ICON); 
									blueIcon.image = "js/blue-dot.png";
									markerOptions = { icon:blueIcon };
									marker = new GMarker(point,markerOptions);
		   					}
   					else 
   							marker = new GMarker(point);
					map.addOverlay(marker);
   				}
   			};
   			oRequest.open("POST",sUrl,"true");
   			oRequest.send(null);
   		}
}
/*************************************�������� ��ʱ��ȡ��λ��Ϣ end************************************/
