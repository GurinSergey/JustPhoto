<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<%@page contentType="text/html; charset=UTF-8" %>
<html>
<style>
    <%@ include file="/WEB-INF/resources/css/style.css"%>
</style>
<head>
    <title>JustPhoto</title>

    <script type="text/javascript" src="//yastatic.net/es5-shims/0.0.2/es5-shims.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="//yastatic.net/share2/share.js" charset="utf-8"></script>
    <script src="/resources/js/functions.js"></script>

</head>
<body>

<div class="user-profile-header">
    <div class="profile-top">
        <div><img src="data:image/jpg;base64,${user.userInfo.encoded}"
                  class="avatar-mini"></div>
        <div class="user-name">${user.username}</div>

        <div class="share-social">
            <%--https://tech.yandex.ru/share/--%>
            <div class="ya-share2" data-services="facebook,vkontakte"></div>
        </div>
    </div>
    <div class="profile-medium">
        <div id="item-profile" class="profile-menu-item"><a href="/id${user.id}/profile">Профиль</a></div>
        <div id="item-photos" class="profile-menu-item"><a href="/id${user.id}">Фотографии</a></div>
        <div id="item-blog" class="profile-menu-item"><a href="/blog">Блог</a></div>
        <div id="item-elect" class="profile-menu-item"><a href="/elect">Избранные</a></div>
    </div>
</div>

</body>
</html>