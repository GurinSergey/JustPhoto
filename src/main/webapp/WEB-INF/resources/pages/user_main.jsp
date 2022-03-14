<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<%@page contentType="text/html; charset=UTF-8" %>
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
    chooseItem('item-photos');
</script>

<table class="gallery_table">
    <tr>
        <td id="all_photo" class="all_photo_common all_photo_by_user">
            <%--photos--%>
        </td>
        <script>
            drawPhotos(${allPhotosByUser}, '${title}${user.username}', false, null, true);
        </script>
    </tr>
</table>

<jsp:include page="/WEB-INF/resources/pages/footer.jsp"/>
</body>
</html>