<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link type="text/css" rel="stylesheet"
      href="http://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css"/>
<html>
<head>
    <title>JustPhoto</title>
</head>
<style>
    <%@ include file="/WEB-INF/resources/css/style.css"%>
</style>
<body>
<jsp:include page="/WEB-INF/resources/pages/header.jsp"/>
<div id="box">
    <form:form commandName="user" action="register" method="post">
        <%--<table>--%>
        <%--<tr>--%>
        <%--<th colspan="2">Enter Information Here</th>--%>
        <%--</tr>--%>
        <%--<tr>--%>
        <%--<td>Email:</td>--%>
        <%--<td><form:input path="email"/></td>--%>
        <%--<td><form:errors path="email" cssClass="error"/></td>--%>
        <%--</tr>--%>

        <%--<tr>--%>
        <%--<td>Name:</td>--%>
        <%--<td><form:input path="username"/></td>--%>
        <%--<td><form:errors path="username" cssClass="error"/></td>--%>
        <%--</tr>--%>

        <%--<tr>--%>
        <%--<td>Password:</td>--%>
        <%--<td><form:input type="password" path="password"/></td>--%>
        <%--<td><form:errors path="password" cssClass="error"/></td>--%>
        <%--</tr>--%>

        <%--<tr>--%>
        <%--<td>ConfirmPassword:</td>--%>
        <%--<td><form:input type="password" path="confirmPassword"/></td>--%>
        <%--<td><form:errors path="confirmPassword" cssClass="error"/></td>--%>
        <%--</tr>--%>

        <%--<tr>--%>
        <%--<td colspan="3">--%>
        <%--<input type="submit" value="Sign up"/>--%>
        <%--<input type="reset" value="Reset"/>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
        <%--<td colspan="2">Already registered!! <a href="login">Login Here</a></td>--%>
        <%--</tr>--%>
        <%--</table>--%>

        <table>
            <tr>
                <th colspan="2">Enter Information Here</th>
            </tr>
            <tr>
                <td><i class="fa fa-envelope-o fa-fw"></i></td>
                <td><form:input type="text" path="email" placeholder="Email address"/></td>
                <td><form:errors path="email" cssClass="error"/></td>
            </tr>

            <tr>
                <td><i class="fa fa-user"></i></td>
                <td><form:input path="username" placeholder="Username"/></td>
                <td><form:errors path="username" cssClass="error"/></td>
            </tr>

            <tr>
                <td><i class="fa fa-key fa-fw"></i></td>
                <td><form:input type="password" path="password" placeholder="Password"/></td>
                <td><form:errors path="password" cssClass="error"/></td>
            </tr>

            <tr>
                <td><i class="fa fa-key fa-fw"></i><i class="fa fa-repeat"></i></td>
                <td><form:input type="password" path="confirmPassword" placeholder="ConfirmPassword"/></td>
                <td><form:errors path="confirmPassword" cssClass="error"/></td>
            </tr>

            <tr>
                <td colspan="3">
                    <input type="submit" value="Sign up"/>
                    <input type="reset" value="Reset"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">Already registered!! <a href="login">Login Here</a></td>
            </tr>
        </table>
        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
    </form:form>
    <div class="descr">
        <h3>Зачем мне регистрироваться?</h3>

        Регистрация на сайте позволит <br/> Вам загружать свои фотографии, <br/> оставлять комментарии под <br/>
        фотографиями других <br/> пользователей, голосовать, <br/> оставаться на связи, следить за <br/> новостями
        других участников, <br/> участвовать в обсуждении на <br/> форуме, и многое другое.
    </div>
</div>
</div>
<jsp:include page="/WEB-INF/resources/pages/footer.jsp"/>
</body>
</html>
