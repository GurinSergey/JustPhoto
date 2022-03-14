<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<html>
<style>
    <%@ include file="/WEB-INF/resources/css/style.css"%>
</style>
<head>
    <title>JustPhoto</title>
    <sec:authorize access="hasAnyRole('ADMIN','USER')" var="isAuthorized"/>

    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="/resources/js/functions.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/resources/pages/header.jsp"/>

<div class="full-photo">
    <table class="photo-container">
        <tr>
            <h2 class="photo-name">${photo.name}</h2>
        </tr>
        <tr>
            <td class="image-td">
                <img src="data:image/jpg;base64,${photo.encoded}"/>
            </td>
        </tr>
    </table>
</div>

<div class="under-photo">

    <h3 class="center">${photo.description}</h3>

    <table class="under-container">
        <c:if test="${isAuthorized
                      && not photo.alreadyEvaluatedByCurrentUser
                      && not (photo.user.id == currentUser.id)}">
            <tr>
                <td class="evaluation">
                    Ваша оценка:
                    <div id="star-rating">
                        <input class="star-input" id="star-5" type="radio" name="rating" value="2.5"
                               onClick="estimate(2.5,${photo.id})">
                        <label class="star-ico fa fa-star-o fa-lg" for="star-5" custom_title="2.5"></label>
                        <input class="star-input" id="star-4" type="radio" name="rating" value="2"
                               onClick="estimate(2,${photo.id})">
                        <label class="star-ico fa fa-star-o fa-lg" for="star-4" custom_title="2"></label>
                        <input class="star-input" id="star-3" type="radio" name="rating" value="1.5"
                               onClick="estimate(1.5,${photo.id})">
                        <label class="star-ico fa fa-star-o fa-lg" for="star-3" custom_title="1.5"></label>
                        <input class="star-input" id="star-2" type="radio" name="rating" value="1"
                               onClick="estimate(1,${photo.id})">
                        <label class="star-ico fa fa-star-o fa-lg" for="star-2" custom_title="1"></label>
                        <input class="star-input" id="star-1" type="radio" name="rating" value="0.5"
                               onclick="estimate(0.5,${photo.id})">
                        <label class="star-ico fa fa-star-o fa-lg" for="star-1" custom_title="0.5"></label>
                    </div>
                </td>
            </tr>
        </c:if>
        <tr>
            <td class="about-photo">
                <a href="/id${photo.user.id}"><p>${photo.user.username}</p></a>

                <p></p>

                <p></p>
                <table>
                    <tr>
                        <td><b>О фото</b></td>
                    </tr>
                    <tr>
                        <td><p></p></td>
                    </tr>
                    <tr>
                        <td> Рейтинг:</td>
                        <td></td>
                        <td id="total">${photo.totalPoints}</td>
                    </tr>
                    <tr>
                        <td> Загружено:</td>
                        <td></td>
                        <td><fmt:formatDate pattern="dd MMM, HH:mm"
                                            value="${photo.createDate}"/></td>
                    </tr>
                    <tr>
                        <td> Категория:</td>
                        <td></td>
                        <td>${photo.category}</td>
                    </tr>
                    <tr>
                        <td> Альбом:</td>
                    </tr>
                </table>
            </td>
            <td>
                <div id="comments">
                    <script>
                        drawComments(${empty currentUser ? 0 : currentUser.id}, ${photo.id}, ${isAuthorized});
                    </script>
                </div>
                <c:choose>
                    <c:when test="${isAuthorized}">
                        <div id="input">
                            <script>
                                drawInput(${empty currentUser ? 0 : currentUser.id}, ${photo.id});
                            </script>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div id="comments">
                            <a href="/login">Войдите в систему,</a> чтобы иметь возможность оставлять комментарии и
                            голосовать.
                        </div>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
</div>

<jsp:include page="/WEB-INF/resources/pages/footer.jsp"/>
</body>
</html>
