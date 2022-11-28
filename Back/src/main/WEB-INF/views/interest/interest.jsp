<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>

<div class="content">
  <div class="container bg-secondary text-secondary p-5 pt-4 pb-4">
    <div class="rounded-3 bg-white m-5 mt-2 mb-2 pt-3 pb-3">
      <div class="mb-3 mt-3 row">
        <div class="col-sm-12 mt-0 mb-0 text-center">
          <label class="h3">관심 지역 목록</label>
        </div>
      </div>
      <div class="mb-3 mt-3 row">
      	<div class="col-sm-4"></div>
      	<div class="col-sm-4 text-center">
	   		<table class="table table-bordered table-hover table-striped align-middle table-sm">
   				<c:if test="${empty interestlist}">관심지역이 없습니다</c:if>
	   			<c:if test="${!empty interestlist}">
		   			<thead><tr><th>번호</th><th>관심지역</th><th>삭제버튼</th></tr></thead>
		   			<tbody>
		   				<c:forEach var="list" items="${interestlist}" varStatus="vs">
			   				<tr>
				   				<td>
				          			<label for="text">${vs.count}</label>
				   				</td>
				   				<td>
				          			<label for="text">${list.sidoName}&nbsp;${list.gugunName}&nbsp;${list.dongName}</label>
				   				</td>
				   				<td>
					      			<button type="button" class="btn btn-danger btn-sm" onclick="location.href='${root}/interest/delete/${list.sidoName}&gugun=${list.gugunName}&dong=${list.dongName}&no=${list.baseaddress_no}'">삭제</button>
				   				</td>
			   				</tr>
		   				</c:forEach>
		   			</tbody>
	   			</c:if>
	   		</table>
   			<div>
		      <select class="selectpicker" data-style="btn-success" id="sido">도/광역시</select>
		      <select class="selectpicker" data-style="btn-success" id="gugun">
		        <option value="">시/구/군</option>
		      </select>
		      <select class="selectpicker" data-style="btn-success" id="interestdong">
		        <option value="">동</option>
		      </select>
		    </div>
      	</div>
      	<div class="col-sm-4"></div>
      </div>
      <form>
      </form>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>