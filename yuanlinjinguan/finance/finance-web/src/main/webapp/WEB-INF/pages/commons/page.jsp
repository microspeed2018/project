<%--@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="mod-pages-group w1002 white-bg clearfix">
  <div class="inner fr page-box">
    <c:choose>
      <c:when test="${paging.totalPages == 1 }">
        <span><span id="row-index"></span>第 1 - <c:out value="${paging.pageSize }"></c:out> 条，共 <span id="row-size"><c:out value="${paging.totalResults }"/> </span> 条</span>
        <span class="start">　首页</span>
        <span class="pre">上一页</span>
        <span class="next">下一页</span>
        <span class="end">尾页</span>
      </c:when>
      <c:when test="${paging.currentPage == 1 }">
	      <span><span id="row-index"></span>第 1 - <c:out value="${paging.pageSize }"></c:out> 条，共 <span id="row-size"><c:out value="${paging.totalResults }"/> </span> 条</span>
	      <span class="start">　首页</span>
	      <span class="pre">上一页</span>
	      <span class="next pointer font-link" onclick="X.page.pageJump('hmDetail', ${paging.currentPage + 1 })">下一页</span>
	      <span class="end  pointer font-link" onclick="X.page.pageJump('hmDetail', ${paging.totalPages })">尾页</span>
      </c:when>
      <c:when test="${paging.currentPage == paging.totalPages }">
        <span><span id="row-index"></span>第 ${(paging.currentPage - 1)*paging.pageSize +1} - <c:out value="${paging.totalResults }"></c:out> 条，共 <span id="row-size"><c:out value="${paging.totalResults }"/> </span> 条</span>
        <span class="start pointer font-link" onclick="X.page.pageJump('hmDetail', 1)">　首页</span>
        <span class="pre  pointer font-link" onclick="X.page.pageJump('hmDetail', ${paging.currentPage - 1 })">上一页</span>
        <span class="next">下一页</span>
        <span class="end">尾页</span>
      </c:when>
      <c:otherwise>
        <span><span id="row-index"></span>第 ${(paging.currentPage - 1)*paging.pageSize +1} - <c:out value="${paging.currentPage * paging.pageSize }"></c:out> 条，共 <span id="row-size"><c:out value="${paging.totalResults }"/> </span> 条</span>
        <span class="start pointer font-link" onclick="X.page.pageJump('hmDetail', 1)">　首页</span>
        <span class="pre pointer font-link" onclick="X.page.pageJump('hmDetail', ${paging.currentPage - 1 })">上一页</span>
        <span class="next pointer font-link" onclick="X.page.pageJump('hmDetail', ${paging.currentPage + 1 })">下一页</span>
        <span class="end  pointer font-link" onclick="X.page.pageJump('hmDetail', ${paging.totalPages })">尾页</span>
      </c:otherwise>
    </c:choose>
  </div>
</div>
<input type="hidden" id="pageUrl" name="pageUrl" value="${pageUrl }"/>
 --%>