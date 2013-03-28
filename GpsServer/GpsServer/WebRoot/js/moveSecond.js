/*JavaScript source file, sq */

/*-----------------google map api service------------------*/

var map;
var marker;
var infowindow;
var myhtml = "";
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
 *             1-��ʾ��� 2-����ʾ���
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
		infowindow = new google.maps.InfoWindow();
		google.maps.event.addListener(marker,'click',function(){
				infowindow.open(map);
			});
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
   					infowindow.setContent("<br>"
						+ position.address);
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
   			var sUrl = "position.action?sim="+document.getElementById("simid").value;
   			var oRequest = new XMLHttpRequest();
   			oRequest.onreadystatechange= function(){
   				if(oRequest.readyState==4){
   					var position = eval('('+oRequest.responseText+')');
   					document.getElementById("p_sim").innerHTML = position.sim;
   					if(position.locationMode == 1)
   						document.getElementById("p_station").innerHTML = "��վ��λ";
   					else
   						document.getElementById("p_station").innerHTML = "GPS��λ";
   					document.getElementById("p_p_time").innerHTML = position.p_time;
   					document.getElementById("p_lati").innerHTML = position.lati_direction+position.latitude;
   					document.getElementById("p_long").innerHTML = position.long_direction+position.longitude;
   					document.getElementById("p_speed").innerHTML = position.speed;
   					document.getElementById("p_direction").innerHTML = position.direction;
   					if(position.netstatus == 1)
   						document.getElementById("p_net").innerHTML = "����";
   					else
   						document.getElementById("p_net").innerHTML = "�ź�����ʧ��Դ";
   					if(position.pstatus == 1)
   						document.getElementById("p_status").innerHTML = "����";
   					else
   						document.getElementById("p_status").innerHTML = "����";
   					var myLatlng = new google.maps.LatLng(position.latitude, position.longitude);
					//if(infowindow!=null)
					//	infowindow.close();
					
					myhtml = "<b>���ȣ�" + position.longitude + "  γ�ȣ�"+position.latitude+"</b><br/><b>�ն˺���:"+position.sim+"</b><br/><b>�ٶ�:"+position.speed+"</b><br/><b>����:"+position.direction+"</b><br/><b>ʱ��:"+position.p_time+"</b>";
					infowindow.setContent(myhtml);
					infowindow.setPosition(myLatlng);
					marker.setPosition(myLatlng);
					if(position.pstatus=='����'){
						marker.setIcon('images/blue-dot.png');
					}
				    map.setCenter(myLatlng);
   				}
   			};
   			oRequest.open("POST",sUrl,"true");
   			oRequest.send(null);
   		}
}
/*************************************�������� ��ʱ��ȡ��λ��Ϣ end************************************/
