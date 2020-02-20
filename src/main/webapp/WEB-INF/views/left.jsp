<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type="text/javascript">
		function toShow(url) {
			window.top.main.location.href=url;
		}
	</script>
</head>
<body>
<p>
	这是A的菜单
	<input type="button" value="跳转B" onclick="toShow('http://127.0.0.1:8081/zzxt/testssm/index')"/>
	<%--<input type="button" value="跳转B" onclick="toShow('http://192.168.7.196:8081/zzxt/testssm/index')"/>--%>
</p>
</body>
</html>