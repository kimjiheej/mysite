<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <div id="content">
            <div id="board">
              <form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
                <table class="tbl-ex">
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>글쓴이</th>
                        <th>조회수</th>
                        <th>작성일</th>
                        <th>&nbsp;</th>
                    </tr>
                    <c:forEach var="lists" items="${list}" varStatus="status">
                        <tr>
                            <td>${totalItem - (current - 1) * onePagesItem - status.index}</td>
                            <td class="fixed-width">
                                <c:choose>
                                    <c:when test="${lists.depth == 0}">
                                        <div style="text-align: center;">
                                  <a href="${pageContext.request.contextPath}/board/view/${lists.no}?curPage=${current}" >
                                                ${lists.title}
                                            </a>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div style="display: flex; align-items: center; margin-left: ${lists.depth * 20}px;">
                                            <c:if test="${lists.depth >= 1}">
                                                <img src="${pageContext.request.contextPath}/assets/images/reply.png" />
                                            </c:if>
                                           <a href="${pageContext.request.contextPath}/board/view/${lists.no}?curPage=${current}" style="margin-left: 10px;">
                                                ${lists.title}
                                            </a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${lists.name}</td>
                            <td>${lists.hit}</td>
                            <td>${lists.reg_date}</td>
                            <c:if test="${authUser.no eq lists.user_no}">
                                <td><a href="${pageContext.request.contextPath}/board/delete/${lists.no}" class="del">삭제</a></td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
                <div class="pager">
                    <ul>
                        <c:if test="${start > 1}">
                            <li><a href="?page=${start - 1}">◀</a></li>
                        </c:if>
                        <c:forEach var="index" begin="${start}" end="${end}">
                            <c:choose>
                                <c:when test="${index == current}">
                                    <li class="selected">${index}</li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="?page=${index}">${index}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${end < totalPage}">
                            <li><a href="?page=${end + 1}">▶</a></li>
                        </c:if>
                    </ul>
                </div>
                     <div class="bottom">
                    <c:if test="${not empty authUser}">
                      <!--  /board/writeform 완료  -->
                        <a href="${pageContext.request.contextPath}/board/writeform" id="new-book">글쓰기</a>
                    </c:if>
                </div>
            </div>
        </div>
        <jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />
    </div>
</body>
</html>