<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" rel="stylesheet"
      href="http://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css"/>
<html>
<head>
    <title>JustPhoto</title>
</head>
<style>
    <%@ include file="/WEB-INF/resources/css/style.css"%>
</style>

<body onload='document.loginForm.username.focus();'>
<jsp:include page="/WEB-INF/resources/pages/header.jsp"/>

<div id="box">

    <h3>Login with Email and Password</h3>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>

    <form name='loginForm' action="<c:url value='/login' />" method='POST'>
        <table>
            <tr>
                <td>
                    <i class="fa fa-envelope-o fa-fw"></i>
                    <input type='text' name='username' placeholder="Email address">
                </td>
            </tr>
            <tr>
                <td>
                    <i class="fa fa-key fa-fw"></i>
                    <input type='password' name='password' placeholder="Password">
                </td>
            </tr>
            <tr>
                <td colspan='2'><input name="Enter" type="submit" value="Enter"/></td>
            </tr>
        </table>

        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
    </form>
</div>

<jsp:include page="/WEB-INF/resources/pages/footer.jsp"/>
</body>
</html>