<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JustPhoto</title>
</head>
<body>
<jsp:include page="/WEB-INF/resources/pages/header.jsp"/>

<h1 class="title">Загрузка фотографии</h1>

<form:form commandName="uploadForm" action="upload" enctype="multipart/form-data" method="post">
    <table class="upload">
        <tr>
            <td>
                <form:input id="file-id" type="file" path="file"/>
                <form:errors path="file" cssClass="error"/>
            </td>
        </tr>

        <tr>
            <td>Категория:</td>
            <td>
                <form:select path="category">
                <form:options items="${allCategories}"></form:options>
            </form:select>
            </td>
            <td><form:errors path="category" cssClass="error"/></td>
        </tr>

        <tr>
            <td>Название:</td>
            <td><form:input type="text" path="name"/></td>
        </tr>
        <tr>
            <td>Описание:</td>
            <td><form:input type="text" path="description"/></td>
        </tr>
        <tr>
            <td align="right">
                <input type="submit" value="Сохранить"/></td>
            </td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form:form>
<jsp:include page="/WEB-INF/resources/pages/footer.jsp"/>
</body>
</html>
