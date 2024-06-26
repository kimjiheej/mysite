<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    pageContext.setAttribute("newline", "\n");
%>
<!DOCTYPE html>
<html>
<head>
    <title>mysite</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div id="container">
        <c:import url="/WEB-INF/views/includes/header.jsp" />
        
        <div id="wrapper">
            <div id="content">
                <div id="board" class="board-form">
                    <table class="tbl-ex">
                        <tr>
                            <th colspan="2">글보기</th>
                        </tr>
                        <tr>
                            <td class="label">제목</td>
                            <td>${board.title}</td>
                        </tr>
                        <tr>
                            <td class="label">내용</td>
                            <td>
                                <div class="view-content">${fn:replace(board.contents, newline, "<br>")}</div>
                            </td>
                        </tr>
                    </table>
                   <div class="bottom">
    <c:choose>
        <c:when test="${board.user_no == authUser.no}">
            <a href="${pageContext.request.contextPath}/board/modifyform?no=${board.no}">글수정</a>
            <sec:authorize access="isAuthenticated()">
                <a href="${pageContext.request.contextPath}/board/writeform?no=${board.no}">답글 달기</a>
            </sec:authorize>
            <a href="${pageContext.request.contextPath}/board">글목록</a>
        </c:when>
        <c:otherwise>
            <sec:authorize access="isAuthenticated()">
                <a href="${pageContext.request.contextPath}/board/writeform?no=${board.no}">답글 달기</a>
            </sec:authorize>
            <a href="${pageContext.request.contextPath}/board?page=${currentPage}">글목록</a>
        </c:otherwise>
    </c:choose>
                    </div>
                </div>
            </div>
        </div>
        
       
    
        
        <c:import url="/WEB-INF/views/includes/navigation.jsp" />
        <c:import url="/WEB-INF/views/includes/footer.jsp" />
    </div>
</body>
</html>