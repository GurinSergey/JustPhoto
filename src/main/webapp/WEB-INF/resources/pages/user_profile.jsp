<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page session="true" %>
<%@page contentType="text/html; charset=UTF-8" %>
<link type="text/css" rel="stylesheet"
      href="http://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css"/>
<html>
<style>
    <%@ include file="/WEB-INF/resources/css/style.css"%>
</style>
<head>
    <title>JustPhoto</title>

    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="/resources/js/functions.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/resources/pages/header.jsp"/>
<jsp:include page="/WEB-INF/resources/pages/user_profile_header.jsp"/>

<script>
    chooseItem('item-profile');
</script>

<c:choose>
    <c:when test="${iAm}">
        <div class="profile-edit">
            <form:form commandName="profileForm" action="/id${id}/profile" enctype="multipart/form-data"
                       method="post">
                <table class="profile-table-edit">
                    <tr>
                        <th colspan="2"><h3>Редактирование профайла</h3></th>
                    </tr>
                    <tr>
                        <td class="profile-info">
                            <table class="profile-table-info">
                                <td></td>
                                <tr>
                                    <td><font color="red">*</font>Имя, Фамилия:</td>
                                    <td><form:input type="text" path="name" value="${user.username}"/></td>
                                    <td><form:errors path="name" cssClass="error"/></td>
                                </tr>
                                <tr>
                                    <td><font color="red">*</font>Почта:</td>
                                    <td><form:input type="text" path="email" value="${user.email}"/></td>
                                    <td><form:errors path="email" cssClass="error"/></td>
                                </tr>
                                <tr>
                                    <td>Дата рождения:</td>
                                    <td><form:input type="date" path="birthday" value="${user.userInfo.birthday}"
                                                    style="width:173px"/></td>
                                    <td><form:errors path="birthday" cssClass="error"/></td>
                                </tr>
                                <tr>
                                    <td>Страна:</td>
                                    <td><form:input type="text" path="country"
                                                    value="${user.userInfo.country}"/></td>
                                    <td><form:errors path="country" cssClass="error"/></td>
                                </tr>
                                <tr>
                                    <td>Город:</td>
                                    <td><form:input type="text" path="town" value="${user.userInfo.town}"/></td>
                                    <td><form:errors path="town" cssClass="error"/></td>
                                </tr>
                                <tr class="vertical-align-top">
                                    <td>О себе:</td>
                                    <td><textarea type="text" name="aboutMyself"
                                                  class="about-myself">${user.userInfo.aboutMyself}</textarea></td>
                                </tr>
                            </table>
                        </td>
                        <td class="profile-avatar">
                            <table class="table-profile-avatar">
                                <tr>
                                    <td>
                                        <img src="data:image/jpg;base64,${user.userInfo.encoded}"
                                             class="avatar" id='avatar'/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="avatar-under-text">
                                            <a href="javascript:deleteAvatar(${user.id})"
                                               onclick="return confirm('Фотография будет удалена, Вы уверены?') ? true : false;"><img
                                                    src="/resources/media/img/icon_del.png"
                                                    class="icon-del-photo">
                                            </a>
                                            или
                                            <a href="javascript:displayInput()"> загрузить новую</a>
                                        </div>
                                        <div id="file-id" class="input-hide">
                                            <form:input type="file" path="file"/>
                                            <form:errors path="file" cssClass="error"/>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr align="center">
                        <td colspan="2"><input type="submit" value="Сохранить"/></td>
                    </tr>
                </table>
            </form:form>
        </div>
    </c:when>
    <c:otherwise>
        <div class="profile-user">
            <table class="table-profile-user">
                <tr>
                    <td>
                        Статус:
                    </td>
                    <td class="row-user-info">
                        Средний балл по фото: ${pointAvg}
                    </td>
                </tr>
                <c:if test="${not empty user.userInfo.birthday}">
                    <tr>
                        <td>
                            День рождения:
                        </td>
                        <td class="row-user-info"><fmt:formatDate value="${user.userInfo.birthday}"
                                                                  pattern="dd.MM.yyyy"/>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty user.userInfo.aboutMyself}">
                    <tr>
                        <td>
                            О себе:
                        </td>
                        <td class="row-user-info">
                                ${user.userInfo.aboutMyself}
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td>
                    </td>
                    <td class="row-user-info">
                        Был на сайте: *
                    </td>
                </tr>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/resources/pages/footer.jsp"/>
</body>

</html>