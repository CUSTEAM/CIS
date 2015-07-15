<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="Portfolio/PageManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/user_gray.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">任課教師列表</font></div>		
		</td>
	</tr>
	
	<tr>
		<td>
		
		<c:forEach items="${alldtime}" var="a">
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" colspan="4"><b>${a.school_year}學年</b></td>
				
			</tr>
			
			<tr>
				<td class="hairLineTdF" width="50" nowrap align="center">學期</td>
				<td class="hairLineTdF" width="300" nowrap>課程名稱</td>
				<td class="hairLineTdF" width="100" nowrap>任課老師</td>
				<td class="hairLineTdF" width="100%">教學歷程 / 電子郵件</td>
			</tr>
			<c:forEach items="${a.courses}" var="c">
			<tr>
				<td class="hairLineTdF" width="50" nowrap align="center">${c.school_term}</td>
				<td class="hairLineTdF" width="200" nowrap>${c.chi_name}</td>
				<td class="hairLineTdF" width="50" nowrap>${c.teacher.cname}</td>
				<td class="hairLineTdF" width="100%">
				
				
				<c:if test="${c.teacher.path!=''&&c.teacher.path!=null}">
				<a target="_blank" href="http://${server}/portfolio/myPortfolio?path=${c.teacher.path}"><img src="images/icon/house.gif" border="0"/></a>
				</c:if>
				
				<c:if test="${c.teacher.path==null||c.teacher.path==''}">
				<img src="images/icon/delete.gif" border="0"/>
				</c:if>
				
				
				&nbsp;&nbsp;
				<c:if test="${c.teacher.Email!=''&&c.teacher.Email!=null}">
				<a href="mailto:${c.teacher.Email}"><img src="images/icon/email.gif" border="0"/></a>
				</c:if>
				
				<c:if test="${c.teacher.Email==''||c.teacher.Email==null}">
				<img src="images/icon/delete.gif" border="0"/>
				</c:if>
				
				
				
				
				</td>
			</tr>
			</c:forEach>
			
		</table>
		</c:forEach>
		
		
		
		
		
		</td>
	</tr>
	
</html:form>
</table>