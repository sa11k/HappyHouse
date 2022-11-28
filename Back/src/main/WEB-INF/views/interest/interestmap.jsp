<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>

<div class="content">
  <div class="container bg-secondary text-secondary p-5 pt-4 pb-4">
    <div class="rounded-3 bg-white m-5 mt-2 mb-2 pt-3 pb-3">
      <div class="mb-3 mt-3 row">
        <div class="col-sm-12 mt-0 mb-0 text-center">
          <label class="h3">관심 지역</label>
        </div>
      </div>
      <div class="mb-3 mt-3 row">
        <div class="col-sm-1"></div>
        <div class="col-sm-4 text-center">
          <table class="table table-bordered table-hover table-striped align-middle table-sm">
            <c:if test="${empty interestlist}">관심지역이 없습니다</c:if>
            <c:if test="${!empty interestlist}">
              <thead>
                <tr>
                  <th>번호</th>
                  <th>관심지역</th>
                  <th>지도표시</th>
                </tr>
              </tr>
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
                      <button type="button" class="btn btn-info btn-sm text-white" onclick="makeinterest(${list.lat},${list.lng})">표시</button>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </c:if>
          </table>
        </div>
        <div class="col-sm-1"></div>
        <div class="col-sm-5">
          <button type="button" class="btn btn-info btn-sm text-white" onclick="resetinterest()">지도리셋</button>
          <div class="map" id="map" style="width: 100%; height: 500px"></div>
          <div class="col-sm-1"></div>
          <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=ad9f987344bc71ba15bbda2b09d4c4d7&libraries=services"></script>
          <script src="${root}/js/interest.js"></script>
        </div>
      </div>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>
