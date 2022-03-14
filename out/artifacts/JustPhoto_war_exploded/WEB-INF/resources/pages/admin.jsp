<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<%@page contentType="text/html; charset=UTF-8" %>
<html>
<style>
    <%@ include file="/WEB-INF/resources/css/style.css"%>
</style>
<head>
    <title>JustPhoto</title>
</head>
<body>
<jsp:include page="/WEB-INF/resources/pages/header.jsp"/>

<h1>Message : ${message}</h1>

<jsp:include page="/WEB-INF/resources/pages/footer.jsp"/>
</body>
</html>