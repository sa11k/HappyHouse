<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>

<div class="content">
    <div class="container bg-secondary text-secondary p-5 pt-4 pb-4">
      <div class="rounded-3 bg-white m-5 mt-2 mb-2 pt-3 pb-3">
        <form id="signform" method="post" action="${root}/user/update">
          <input type="hidden" name="action" value="update">
          <div class="mb-3 mt-3 row">
            <div class="col-sm-6 m-4 mt-0 mb-0 text-end">
              <label class="h3">회원 정보 수정</label>
            </div>
            <div class="col-sm-6"></div>
          </div>
          <div class="mb-3 mt-3 row">
            <div class="col-sm-4 text-end">
              <label for="text" class="form-label">아이디</label>
            </div>
            <div class="col-sm-4">
              <input type="text" id="id" name="id" class="form-control col-sm-10" value="${userinfo.id}" readonly>
            </div>
            <div class="col-sm-4"></div>
          </div>
          <div class="mb-3 row">
            <div class="col-sm-4 text-end">
              <label for="pw" class="form-label">비밀번호</label><span class="text-danger small">*</span>
            </div>
            <div class="col-sm-4">
              <input type="password" id="pw" name="pw" class="form-control" placeholder="미 입력시 수정 불가" required>
            </div>
            <div class="col-sm-4"></div>
          </div>
          <div class="mb-3 row">
            <div class="col-sm-4 text-end">
              <label for="name" class="form-label">이름</label>
            </div>
            <div class="col-sm-4">
              <input type="text" id="name" name="name" class="form-control" placeholder="User Name" required value="${userinfo.name}">
            </div>
            <div class="col-sm-4"></div>
          </div>
          <div class="mb-3 row">
            <div class="col-sm-4 text-end">
              <label for="address" class="form-label">주소</label>
            </div>
            <div class="col-sm-4">
              <input type="address" id="address" name="address" class="form-control" placeholder="address" value="${userinfo.address}">
            </div>
            <div class="col-sm-4"></div>
          </div>
          <div class="mb-3 row">
            <div class="col-sm-4 text-end">
              <label for="email" class="form-label">이메일</label>
            </div>
            <div class="col-sm-4">
              <input type="email" id="email" name="email" class="form-control" placeholder="a@a" value="${userinfo.email}">
            </div>
            <div class="col-sm-4"></div>
          </div>
		  <div class="mb-3 row">
		    <div class="col-sm-6 text-end">
		      <button type="submit" id="sign-btn" class="btn btn-warning">등록</button>
		    </div>
		    <div class="col-sm-6"></div>
		  </div>
        </form>
      </div>
    </div>
  </div>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>