var mapContainer = document.getElementById('map'),
    mapOption = { 
        center: new kakao.maps.LatLng(36.35535495701599, 127.29840379609664),
        level: 5
    };
if(mapContainer!=null){
	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	
// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
	var mapTypeControl = new kakao.maps.MapTypeControl();
	
// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
	map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
	
// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
	var zoomControl = new kakao.maps.ZoomControl();
	map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
}

var markers=[];
var bounds = new kakao.maps.LatLngBounds();

function makeinterest(lat,lng){
	// 마커가 표시될 위치입니다 
	var markerPosition  = new kakao.maps.LatLng(lat, lng);
	
	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
		position: markerPosition
	});
	// 이후 제어를 위해 전역 배열에 객체 삽입 
	markers.push(marker);
	// 지도범위 재설정 객체에 좌표추가
	bounds.extend(new kakao.maps.LatLng(lat, lng));
	
	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);
	
	// 지도의 모든 마커가 표시되도록 지도 범위를 재설정
	map.setBounds(bounds);
	if(map.getLevel()<4)
		map.setLevel(4);
}

// 지도리셋
function resetinterest(lat, lng){
	for(var i=0; i<markers.length; i++)
		markers[i].setMap(null);
	markers.length=0;
	bounds=new kakao.maps.LatLngBounds();
	
	if(map.getLevel()>7)
		map.setLevel(7);
	
	var moveLatLon=null;
	if(lat==null && lng==null) moveLatLon = new kakao.maps.LatLng(36.35535495701599, 127.29840379609664);
	else moveLatLon = new kakao.maps.LatLng(lat, lng);
	map.panTo(moveLatLon);
}