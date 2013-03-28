/*JavaScript source file, sq */

/*-----------------google map api service------------------*/

 var arrPoints=new Array();  //���ж�λ��
 var mylat = new Array();    //γ��
 var mylong = new Array();   //����
 var sims = new Array();    //�ն˺�
 var speeds = new Array();   //�ٶ�
 var directions = new Array(); //����
 var times = new Array();        //��λʱ��
 var statuss = new Array();       //��λ״̬
 var carnumbers = new Array();   //�������
var map;
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
 *             1-��ʾ��� 2-����ʾ���
 * @return
 */
function google_location(id,zoom) {
	var myLatlng = new google.maps.LatLng(20.92,116.404);
	var myOptions = {
		zoom : zoom,
		center : myLatlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	//��ʼ��google��ͼ
	 map = new google.maps.Map(document.getElementById(id), myOptions);
	//��ʼ������maker
	 for(var i=0;i<arrPoints.length;i++)
       {
       	var point = arrPoints[i];
       	var myHtml = "";
       	if(statuss[i] == 0)
      		myHtml = "<b>���ȣ�" + mylong[i] + "  γ�ȣ�"+mylat[i]+"</b><br/><b>���ƺ���:"+carnumbers[i]+"</b><br/><b>�ն˺���:"+sims[i]+"</b><br/><b>�ٶ�:"+speeds[i]+"</b><br/><b>����:"+directions[i]+"</b><br/><b>ʱ��:"+times[i]+"</b><br/><b>״̬:�ź�����ʧ��Դ<b>";
       	else
       		myHtml = "<b>���ȣ�" + mylong[i] + "  γ�ȣ�"+mylat[i]+"</b><br/><b>���ƺ���:"+carnumbers[i]+"</b><br/><b>�ն˺���:"+sims[i]+"</b><br/><b>�ٶ�:"+speeds[i]+"</b><br/><b>����:"+directions[i]+"</b><br/><b>ʱ��:"+times[i]+"</b><br/><b>״̬:����<b>";
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

