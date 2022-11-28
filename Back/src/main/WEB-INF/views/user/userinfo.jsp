<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>

<div class="content">
  <div class="container bg-secondary text-secondary p-5 pt-4 pb-4">
    <div class="rounded-3 bg-white m-5 mt-2 mb-2 pt-3 pb-3">
      <div class="mb-3 mt-3 row">
        <div class="col-sm-6 m-4 mt-0 mb-0 text-end">
          <label class="h3">회원 정보 확인</label>
        </div>
        <div class="col-sm-6"></div>
      </div>
      <div class="mb-3 mt-3 row">
        <div class="col-sm-5 text-end">
          <label for="text" class="form-label">아이디</label>
        </div>
        <div class="col-sm-7">
          <label for="text">${userinfo.id}</label>
        </div>
      </div>
      <div class="mb-3 row">
        <div class="col-sm-5 text-end">
          <label for="text" class="form-label">이름</label>
        </div>
        <div class="col-sm-7">
          <label for="text">${userinfo.name}</label>
        </div>
      </div>
      <div class="mb-3 row">
        <div class="col-sm-5 text-end">
          <label for="text" class="form-label">이메일</label>
        </div>
        <div class="col-sm-7">
          <label for="text">${userinfo.email}</label>
        </div>
      </div>
      <div class="mb-3 row">
        <div class="col-sm-5 text-end">
          <label for="text" class="form-label">주소</label>
        </div>
        <div class="col-sm-7">
          <label for="text">${userinfo.address}</label>
        </div>
      </div>
    </div>
    <div class="mb-3 row">
      <div class="col-sm-6 text-end">
        <button type="button" onclick="location.href='${root}/index'" id="sign-btn" class="btn btn-info text-white">확인</button>
        <button type="button" onclick="location.href='${root}/user/updateform'"id="sign-btn" class="btn btn-info text-white">수정</button>
        <button type="button" onclick="location.href='${root}/user/delete/${userinfo.id}'"id="sign-btn" class="btn btn-info text-white">탈퇴</button>
      </div>
      <div class="col-sm-6">
      </div>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>