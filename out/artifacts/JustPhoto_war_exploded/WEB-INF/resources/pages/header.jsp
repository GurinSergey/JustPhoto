<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="sec"
          uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<style>
    <%@ include file="/WEB-INF/resources/css/style.css"%>
</style>

<sec:authorize access="hasAnyRole('ADMIN','USER')" var="isAuthorized"/>

<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>

<c:url value="/logout" var="logoutUrl"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>

<script src="/resources/js/functions.js"></script>
<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>

<header>
    <div class="header-container">
        <table class="t">
            <tr class="tr">
                <td class="td1">
                    <ul class="menu">
                        <li><a href="/" title="На главную">
                            <img src="/resources/media/img/Logo.jpg" id="logo">
                        </a></li>

                        <li><a href="/gallery">Галерея</a></li>
                        <li><a href="">Топ<b class="arrow-down"></b></a>
                            <ul class="submenu">
                                <li><a href="">ТОП 100 фотографов</a></li>
                                <li><a href="">ТОП 100 фотографий</a></li>
                                <li><a href="">Картина дня</a></li>
                            </ul>
                        </li>
                        <li><a href="">Еще<b class="arrow-down"></b></a>
                            <ul class="submenu">
                                <li><a href="">О нас</a></li>
                                <li><a href="">Контакты</a></li>
                            </ul>
                        </li>
                    </ul>
                </td>
                <td class="td2">
                    <ul class="menu">
                        <c:choose>
                            <c:when test="${isAuthorized}">
                                <ul class="loginregister">
                                    <li><a href="">${pageContext.request.userPrincipal.name}<b
                                            class="arrow-down"></b></a>
                                        <ul class="submenu">
                                            <li><a href="#" onclick="openProfilePage()">Мой профиль</a>
                                            </li>
                                            <sec:authorize access="hasAnyRole('ADMIN')">
                                                <li><a href="/admin">Админка</a></li>
                                            </sec:authorize>

                                            <li><a href="/upload">Загрузить фото</a></li>
                                            <li><a href="javascript:formSubmit()"> Выход</a></li>
                                        </ul>
                                    </li>
                                </ul>
                            </c:when>
                            <c:otherwise>
                                <ul class="loginregister">
                                    <li><a href="/login">Вход</a></li>
                                    <li><a href="/register">Регистрация</a></li>
                                </ul>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </td>
            </tr>
        </table>
    </div>
</header>
</html>
