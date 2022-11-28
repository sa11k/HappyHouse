<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<link rel="shortcut icon" href="img/favicon.ico">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	
	<link rel="stylesheet" href="${root}/css/header.css" />
	<link rel="stylesheet" href="${root}/css/index.css" />
	<title>HAPPYHOUSE</title>
	<script>
		let msg="${msg}"; 
		if(msg) {
			alert(msg);
		}
	</script>
</head>
<body>
<header class="header">
	<div class="login">
		<c:if test="${empty logininfo}">
			<div class="header_login_confirm_off">
				<div class="header_login_menuitem">
					<img src="${root}/img/people.png" alt="">
					<a class="login_link" data-bs-toggle="modal" data-bs-target="#logModal">Login</a>
					
					<div class="modal fade" id="logModal">
					  <div class="modal-dialog modal-dialog-centered">
					    <div class="modal-content">
					
					      <!-- Modal Header -->
					      <div class="modal-header">
					        <h4 class="modal-title">로그인</h4>
					        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					      </div>
					
					      <!-- Modal body -->
					      <div class="modal-body">
							<form id="signform" method="post" action="${root}/user/login">
							  <input type="hidden" name="action" value="login">
					          <div class="mb-3 mt-3 row">
					            <div class="col-sm-3 text-end">
					              <label for="id" class="form-label">아이디</label>
					            </div>
					            <div class="col-sm-8">
					              <input type="text" id="id" name="id" class="form-control col-sm-10" value="ssafy">
					            </div>
					            <div class="col-sm-1"></div>
					          </div>
					          <div class="mb-3 row">
					            <div class="col-sm-3 text-end">
					              <label for="pw" class="form-label">비밀번호</label>
					            </div>
					            <div class="col-sm-8">
					              <input type="password" id="pw" name="pw" class="form-control" value="1234">
					            </div>
					            <div class="col-sm-1"></div>
					          </div>
					          <div class="mb-3 text-center">
						        <button type="submit" class="btn btn-primary" data-bs-dismiss="modal">로그인</button>
					      	  </div>
					        </form>
					      </div>
					    </div>
					  </div>
					</div>
				</div>
				<div class="header_login_menuitem">
					<img src="${root}/img/people.png" alt="">
					<a class="login_link" href="${root}/user/register">회원가입</a>
				</div>
			</div>
		</c:if>
		<c:if test="${!empty logininfo}">
			<div class="header_login_confirm_on">
				<div class="header_login_menuitem">
					<img src="${root}/img/people.png" alt="">
					<a class="login_link" href="${root}/user/logout">Logout</a>
				</div>
				<div class="header_login_menuitem">
					<img src="${root}/img/people.png" alt="">
					<a class="login_link" href="${root}/user/userinfo/${logininfo.id}">회원정보</a>
				</div>
			</div>
		</c:if>
	</div>
	<nav class="nav">
		<img src="${root}/img/logo.jpg" alt="홈으로가기" class="logo" usemap="#logo">
		<map name="logo">
			<area shape="default" title="홈으로" href="${root}">
		</map>
    	<div class="header_nav">
        	<div class="header_nav_menuitem">
	          	<a class="nav_link" href="${root}/notice/index">공지 사항</a>
	        </div>
	        <div class="header_nav_menuitem">
	          	<a class="nav_link" href="${root}/interest/interestform">관심 지역 설정</a>
	        </div>
	        <div class="header_nav_menuitem">
	          	<a class="nav_link" href="${root}/interest/interestmap">관심 지역 둘러보기</a>
	        </div>
	        <div class="header_nav_menuitem">
	          	<img src="${root}/img/search.png" alt="" class="searchicon">
	        </div>
      	</div>
	</nav>
</header>
