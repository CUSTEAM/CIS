<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<style type="text/css">
<!--
.input {
	width:20px;
}
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
-->
</style>
<script type="text/javascript">
<!--
function lockInput(tagId)
{
	var obj=document.getElementById(tagId);
	obj.readOnly=true;
}
// -->
</script>


<form action="/CIS/StudAffair/Racing/BonusPenaltySel.do" method="post" name="tfForm">

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0" border="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.BonusPenaltySel" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="left">
<!-- End Content Page Table Header -->
	<table align="center" cellspacing="5" class="empty-border">
      <tr>
        	<td>起始日期:<input type="text" name="start_date" size="10">(YY/MM/DD)</td>
        	<td>&nbsp;&nbsp;</td>
        	<td>結束日期:<input type="text" name="end_date" size="10">(YY/MM/DD)</td>
	   </tr>
	</table>
		</td>
	</tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Query'/>" >');
	</script>
	
	<c:if test="${! empty DesdSelList}">
	<tr><td align="left"><font style="size:13px; color:#FF0000">算入的部份請輸入Y,空白或輸入N表示不算入!
	</td></tr>
	
	<tr>
		<td align="left">

	<table cellspacing="1" class="empty-border" bgcolor="#ffffff">
      <tr>
        	<td>&nbsp;</td>
        	<td width="80" align="center" valign="middle">日期</td>
        	<td width="60" align="center" valign="middle">文號</td>
        	<td width="200" align="center" valign="middle">原因</td>
        	<td width="50" align="center" valign="middle">種類1</td>
        	<td width="50" align="center" valign="middle">次數1</td>
        	<td width="50" align="center" valign="middle">種類2</td>
        	<td width="50" align="center" valign="middle">次數2</td>
        	<td width="120" align="center" valign="middle">班級</td>
        	<td width="80" align="center" valign="middle">學號</td>
        	<td width="80" align="center" valign="middle">姓名</td>
        	<td width="30" align="center" valign="middle">算入</td>
         	<td>&nbsp;</td>
      </tr>
      <c:choose>
        <c:when test="${DesdSelInEdit != null}">
        	<c:set var="sels" value="${DesdSelInEdit.actIllegal}"/>
        	<c:set var="rcnt" value="0"/>
			<c:forEach items="${DesdSelList}" var="desd">
		      	<tr>
        			<td>&nbsp;</td>
		        	<td width="80" align="left" valign="middle" bgcolor="#f5deb3">
		        		<c:out value="${desd.ddate}" />
		        	</td>
		        	<td width="60" align="left" valign="middle" bgcolor="#f5deb3">
		        		<c:out value="${desd.no}" />
		        	</td>
		        	<td width="200" align="left" valign="middle" bgcolor="#f5deb3">
		        		<c:out value="${desd.reason}" />[<c:out value="${desd.reasonName}" />]		        		
		        	</td>
        			<td width="50" align="center" valign="middle" bgcolor="#f5deb3">${desd.desdName1}</td>
        			<td width="50" align="center" valign="middle" bgcolor="#f5deb3">${desd.cnt1}</td>
        			<td width="50" align="center" valign="middle" bgcolor="#f5deb3">${desd.desdName2}</td>
        			<td width="50" align="center" valign="middle" bgcolor="#f5deb3">${desd.cnt2}</td>
		        	<td width="120" align="left" valign="middle" bgcolor="#add8e6">
		        		<c:out value="${desd.departClass}" />[<c:out value="${desd.deptClassName}" />]
		        	</td>
		        	<td width="80" align="left" valign="middle" bgcolor="#add8e6">
		        		<c:out value="${desd.studentNo}" />
		        	</td>
		        	<td width="80" align="left" valign="middle" bgcolor="#add8e6">
						<c:out value="${desd.studentName}" />
		        	</td>
		        	<td width="30" align="left" valign="middle" bgcolor="#f5deb3">
		           	<input name="actIllegal" value="${sels[rcnt]}" 
		           	type="text" size="1" maxlength="1" 
		          		onblur="if(this.value!='') checkYN(this);">
		        	</td>
        			<td>&nbsp;</td>
        		</tr>
        		<c:set var="rcnt" value="${rcnt+1}"/>
			</c:forEach>
         </c:when>
        <c:when test="${! empty DesdSelList}">
			<c:forEach items="${DesdSelList}" var="desd">
		      	<tr>
        			<td>&nbsp;</td>
		        	<td width="80" align="left" valign="middle" bgcolor="#f5deb3">
		        		<c:out value="${desd.sddate}" />
		        	</td>
		        	<td width="60" align="left" valign="middle" bgcolor="#f5deb3">
		        		<c:out value="${desd.no}" />
		        	</td>
		        	<td width="200" align="left" valign="middle" bgcolor="#f5deb3">
		        		<c:out value="${desd.reason}" />[<c:out value="${desd.reasonName}" />]		        		
		        	</td>
        			<td width="50" align="center" valign="middle" bgcolor="#f5deb3">${desd.desdName1}</td>
        			<td width="50" align="center" valign="middle" bgcolor="#f5deb3">${desd.cnt1}</td>
        			<td width="50" align="center" valign="middle" bgcolor="#f5deb3">${desd.desdName2}</td>
        			<td width="50" align="center" valign="middle" bgcolor="#f5deb3">${desd.cnt2}</td>
		        	<td width="120" align="left" valign="middle" bgcolor="#add8e6">
		        		<c:out value="${desd.departClass}" />[<c:out value="${desd.deptClassName}" />]
		        	</td>
		        	<td width="80" align="left" valign="middle" bgcolor="#add8e6">
		        		<c:out value="${desd.studentNo}" />
		        	</td>
		        	<td width="80" align="left" valign="middle" bgcolor="#add8e6">
						<c:out value="${desd.studentName}" />
		        	</td>
		        	<td width="30" align="left" valign="middle" bgcolor="#f5deb3">
		           	<input name="actIllegal" value="${desd.actIllegal}" 
		           	type="text" size="1" maxlength="1" 
		          		onblur="if(this.value!='') checkYN(this);">
		        	</td>
        			<td>&nbsp;</td>
        		</tr>
			</c:forEach>
         </c:when>
         </c:choose>
        </table>
       </td>
      </tr>
    
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" >');
	</script>
	</c:if>
</table>
</form>
<script>history.go(1);</script>
<script language="javascript">
//<!--
function checkYN(obj) {
	//alert("value:" + obj.value());
	if((obj.value != "y" || obj.value != "Y" ||
	obj.value != "n" || obj.value != "N")) {
		alert("算入欄位必須填寫 Y 或 N !");
		return false;
	}
	return true;
};
//-->
</script>
