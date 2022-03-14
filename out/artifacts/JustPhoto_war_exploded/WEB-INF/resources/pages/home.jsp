<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<%@page contentType="text/html; charset=UTF-8" %>
<html>
<style>
    <%@ include file="/WEB-INF/resources/css/style.css"%>
</style>
<head>
    <title>JustPhoto</title>
    <%--<link rel="icon" type="image/png" href="http://iconizer.net/files/Wireframe_mono_icons/orig/photo.png"/>--%>

    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>

    <script type="text/javascript">
        var imgs = [];

        <c:forEach items="${slidePhotos}" var="photo">
        imgs[imgs.length] = "data:image/jpg;base64,${photo.encoded}";
        </c:forEach>

        var cnt = imgs.length;

        $(function () {
            setInterval(Slider, 5000);
            var $imageSlide = $('img[id$=imageSlide]');
            $imageSlide.attr('src', imgs[cnt - 1]);
        });
        function Slider() {
            $('#imageSlide').fadeOut("slow", function () {
                $(this).attr('src', imgs[(imgs.length++) % cnt]).fadeIn("slow");
            });
        }
    </script>
</head>

<body>
<jsp:include page="/WEB-INF/resources/pages/header.jsp"/>

<div>
    <p class="slide">
        <img id="imageSlide"/>
    </p>
</div>

<jsp:include page="/WEB-INF/resources/pages/footer.jsp"/>
</body>

</html>
