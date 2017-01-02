<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header-bar">
    <div class="container">
        <a href="/home" class="brand">
            <i class="fa fa-coffee"></i>
        </a>
        <ul class="unstyled inline pull-right">
            <c:choose>
                <c:when test="${not empty sessionScope.curr_user}">
                    <li>
                        <a href="/setting">
                            <img id="navbar_avatar" src="${sessionScope.curr_user.avatar}?imageView2/1/w/35/h/35" class="img-circle" alt="">
                        </a>
                    </li>
                    <li>
                        <a href="/newTopic"><i class="fa fa-plus"></i></a>
                    </li>
                    <li>

                        <a href="/notify"><i class="fa fa-bell"></i>
                            <%--<c:if test="${count gt 0}">--%>
                                <span id="countNotify" class="badge"></span>
                            <%--</c:if>--%>
                        </a>
                    </li>
                    <li>
                        <a href="/setting"><i class="fa fa-cog"></i></a>
                    </li>
                    <li>
                        <a href="/logout"><i class="fa fa-sign-out"></i></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li><a href="/login"><i class="fa fa-sign-in"></i></a></li>
                </c:otherwise>
            </c:choose>

        </ul>
    </div>
</div>
<!--header-bar end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/user/notify.js"></script>