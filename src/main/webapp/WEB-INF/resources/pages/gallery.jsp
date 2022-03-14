<%@taglib prefix="sec"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html; charset=UTF-8" %>
<link type="text/css" rel="stylesheet"
      href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"/>
<html>
<style>
    <%@ include file="/WEB-INF/resources/css/style.css"%>
</style>
<head>
    <title>JustPhoto</title>

    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="/resources/js/functions.js"></script>


    <script type="text/javascript">
        $(function () {
            $(window).scroll(function () {
                if ($(this).scrollTop() != 0) {
                    $('#toTop').fadeIn();
                } else {
                    $('#toTop').fadeOut();
                }
            });
            $('#toTop').click(function () {
                $('body,html').animate({scrollTop: 0}, 800);
            });
        });
    </script>

</head>
<body>
<jsp:include page="/WEB-INF/resources/pages/header.jsp"/>

<table class="gallery_table">
    <tr>
        <td id="all_photo" class="all_photo_common all_photo">
            <%--photos--%>
        </td>
        <td id="categories">
            <%--categories--%>
        </td>
        <script>
            drawGalleryBody(${allPhotos}, '${title}', ${empty byPopularity ? false : byPopularity}, ${empty currentCategoryId ? null : currentCategoryId}, ${allCategories}, false);
        </script>
    </tr>
</table>

<div id="toTop"><img src="/resources/media/img/toTop.png" class="toTop"></div>

<jsp:include page="/WEB-INF/resources/pages/footer.jsp"/>
</body>
</html>