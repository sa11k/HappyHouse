var root = "${pageContext.request.contextPath}";

$(document).ready(function () {
    $.get(root + "/house/sido"
		,function(data, status){
			$.each(data, function(index, vo) {
				$("#sido").append("<option value='"+vo.sidoCode+"'>"+vo.sidoName+"</option>");
			});
		}
		, "json"
	);

});

///////////////////////////////시, 도 선택 이벤트 처리 
$(document).on("change", "#sido", function() {
	$.get(root + "/house/gugun"
			,{sido: $("#sido").val()}
			,function(data, status){
				$("#gugun").empty();
				$("#gugun").append('<option value="0">선택</option>');
				$.each(data, function(index, vo) {
					$("#gugun").append("<option value='"+vo.gugunCode+"'>"+vo.gugunName+"</option>");
				});
			}
			, "json"
	);
});

/////////////////////////////구, 군 선택 이벤트 처리 
$(document).on("change", "#gugun", function() {
	$.get(root + "/house/dong"
			,{gugun: $("#gugun").val()}
			,function(data, status){
				$("#dong").empty();
				$("#dong").append('<option value="0">선택</option>');
				$.each(data, function(index, vo) {
					$("#dong").append("<option value='"+vo.dongCode+"'>"+vo.dongName+"</option>");
				});
			}
			, "json"
	);
});

////////////////////////////동 선택 이벤트 처리
let colorArr = ['table-primary','table-success','table-danger'];
$(document).on("change", "#dong", function() {
	$.get(root + "/house/apt"
			,{dong: $("#dong").val()}
			,function(data, status){
				$("tbody").empty();
				$.each(data, function(index, vo) {
					let str = `
						<tr class="${colorArr[index%3]}">
						<td>${vo.aptCode}</td>
						<td>${vo.aptName}</td>
						<td>${vo.sidoName} ${vo.gugunName} ${vo.dongName} ${vo.jibun}</td>
						<td>${vo.buildYear}</td>
						<td>${vo.recentPrice}</td>
					`;
					$("tbody").append(str);
				});
				displayMarkers(data);
			}
			, "json"
	);
});
    
    // //////////////////카카오 맵 설정
//  var mapContainer = document.getElementById("map"); // 지도를 표시할 div
//  var latitude = "33.450701";
//  var longtitude = "126.570667";
//
//  mapOption = {
//    center: new kakao.maps.LatLng(latitude, longtitude), // 지도의 중심좌표
//    level: 3, // 지도의 확대 레벨
//  };
//
//  // 지도를 표시할 div와 지도 옵션으로 지도를 생성합니다
//  var map = new kakao.maps.Map(mapContainer, mapOption);
  
//});
