var root = "${pageContext.request.contextPath}"
	
let colorArr = ['table-primary','table-success','table-danger'];

$(document).ready(function(){	
	
	// 초기 시,군,동 셋팅
	var sidocodes = JSON.parse(sessionStorage.getItem("sidocodes"));
	var sidonames = JSON.parse(sessionStorage.getItem("sidonames"));
	
	$("#sido").empty();
	for (var i = 0; i < sidocodes.length; i++) {
		$("#sido").append("<option value='"+sidocodes[i]+"'>"+sidonames[i]+"</option>");
    }
	
	var sidocode = sessionStorage.getItem("sidocode");
	$("#sido").val(sidocode).prop("selected", true);
	
	// 구군
	var guguncodes = JSON.parse(sessionStorage.getItem("guguncodes"));
	var gugunnames = JSON.parse(sessionStorage.getItem("gugunnames"));
	
	$("#gugun").empty();
	for (var i = 0; i < guguncodes.length; i++) {
		$("#gugun").append("<option value='"+guguncodes[i]+"'>"+gugunnames[i]+"</option>");
    }
	
	var guguncode = sessionStorage.getItem("guguncode");
	$("#gugun").val(guguncode).prop("selected", true);
	
	// 동
	var dongcodes = JSON.parse(sessionStorage.getItem("dongcodes"));
	var dongnames = JSON.parse(sessionStorage.getItem("dongnames"));
	
	$("#dong").empty();
	for (var i = 0; i < guguncodes.length; i++) {
		$("#dong").append("<option value='"+dongcodes[i]+"'>"+dongnames[i]+"</option>");
    }
	
	var dongcode = sessionStorage.getItem("dongcode");
	$("#dong").val(dongcode).prop("selected", true);
	
	// 아파트 설정
	$.get(root + "/map/apt"
			,{dong: $("#dong").val()}
			,function(data, status){
				
				$.each(data, function(index, vo){
					let str = `
						<div class="media margin-clear">
							<div class="media-body">
								<h4 class="media-heading" id='deal'>${vo.aptCode} ${vo.aptName}</h4>
								<h6 class="media-heading"  id='deal'>위치 :    ${vo.gugunName} ${vo.dongName} ${vo.jibun}</h6>
    							<h6 class="media-heading"  id='deal'>거래금액 :     ${vo.recentPrice}</h6>
      							<p class="small margin-clear">
      								<i class="fa fa-calendar pr-10"></i></i>${vo.buildYear}
      							</p>
      						</div>
      					</div>
      					<hr>
					`;
					$("#aptlist").append(str);
				});
				
				displayMarkers(data);
			}
			, "json"
	);
	
});

$(document).on("change", "#sido", function() {
	$.get(root + "/map/gugun"
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

$(document).on("change", "#gugun", function() {
	$.get(root + "/map/dong"
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

$(document).on("change", "#dong", function() {
	// 선택된 시, 구, 동을 세션에 저장
	sessionStorage.setItem("sidocode", $("#sido").val());
	sessionStorage.setItem("guguncode", $("#gugun").val());
	sessionStorage.setItem("dongcode", $("#dong").val());
	
	var sidocodes = [];
    var sidonames = [];

    $("#sido option").each(function (i, element) {
    	sidocodes.push($(this).val());
    	sidonames.push($(this).text());
    });
    
    sessionStorage.setItem("sidocodes", JSON.stringify(sidocodes));
    sessionStorage.setItem("sidonames", JSON.stringify(sidonames));
    
    var guguncodes = [];
    var gugunnames = [];

    $("#gugun option").each(function (i, element) {
    	guguncodes.push($(this).val());
    	gugunnames.push($(this).text());
    });
    
    sessionStorage.setItem("guguncodes", JSON.stringify(guguncodes));
    sessionStorage.setItem("gugunnames", JSON.stringify(gugunnames));
    
    var dongcodes = [];
    var dongnames = [];

    $("#dong option").each(function (i, element) {
    	dongcodes.push($(this).val());
    	dongnames.push($(this).text());
    });
    
    sessionStorage.setItem("dongcodes", JSON.stringify(dongcodes));
    sessionStorage.setItem("dongnames", JSON.stringify(dongnames));

    window.location.reload();
});

$(document).on("change", "#sortingApt", function() {
	
	if($("#sortingApt").val() == "lower"){
		// aptlist 초기화 -> 가격 순으로 정렬 -> 다시 뿌림
		$("#aptlist").empty();
		$.get(root + "/map/apt"
				,{dong: $("#dong").val()}
				,function(data, status){
					
					data = mergeSort(data);
					
					$.each(data, function(index, vo){
						let str = `
							<div class="media margin-clear">
								<div class="media-body">
									<h4 class="media-heading" id='deal'>${vo.aptCode} ${vo.aptName}</h4>
									<h6 class="media-heading"  id='deal'>위치 :    ${vo.gugunName} ${vo.dongName} ${vo.jibun}</h6>
	    							<h6 class="media-heading"  id='deal'>거래금액 :     ${vo.recentPrice}</h6>
	      							<p class="small margin-clear">
	      								<i class="fa fa-calendar pr-10"></i></i>${vo.buildYear}
	      							</p>
	      						</div>
	      					</div>
	      					<hr>
						`;
						$("#aptlist").append(str);
					});
					
					displayMarkers(data);
				}
				, "json"
		);
	}
});

const mergeSort = function (array) {
	// ending condition: length === 1 인 배열이 들어올 때, 정렬이 끝난 것. 
	if (array.length === 1) return array;
	
	// 2로 나누고 내림을 해야
	// length 가 2일 때도 안전하게 배열을 slice 할 수 있다.
	const middleIndex = Math.floor(array.length / 2); 
	const left = array.slice(0, middleIndex);
	const right = array.slice(middleIndex);
	
	// 재귀로 계속해서 반으로 나누면서 length 가 1이 될때까지 쪼개고, 
	// 거꾸로 올라오면서 순수한 함수인 merge에 인자로 넣어서 다시 병합되어서 최종값을 리턴한다.
	return merge(mergeSort(left), mergeSort(right));
}

const merge = function (left, right) { // 정렬된 왼쪽과 오른쪽 배열을 받아서 하나로 합치는 순수한 함수
	// left, right already sorted
	const result = [];
	while (left.length !== 0 && right.length !== 0) {
		left[0].recentPrice <= right[0].recentPrice ? result.push(left.shift()) : result.push(right.shift());	
	}

	return [...result, ...left, ...right]; // 아래 세줄과 같은 역할을 하는 코드
    // if(left.length === 0) results.push(...right);
    // if(right.length === 0) results.push(...left);
    // return results;
}

