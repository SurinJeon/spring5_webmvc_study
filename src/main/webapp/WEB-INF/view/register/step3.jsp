<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원가입</title>
</head>
<body>
	<p>
		<strong>${registRequest.name }님</strong>
		회원가입을 완료했습니다.
	</p>
	<p><a href="<c:url value='/main'/>">[첫 화면 이동]</a></p> <!-- 컨트롤러 구현 없는 경로 매핑(그냥 그 페이지로 바로 가면 됨) -->
</body>
</html>