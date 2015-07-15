<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<%@ page import="org.apache.struts.Globals" %> 
<%@ include file="include/HelpMessage.jsp" %>		
<table width="100%" cellpadding="0" cellspacing="0" bgColor="#222222">
	<tr height="30">		
		<td onMouseOut="this.style.background='#222222'" onMouseOver="this.style.background='#555555'" style="padding:0px 5px; 0px; 5px;" nowrap>
		
		<c:if test="${Credential.employee}">
		<td onMouseOut="this.style.background='#222222'" onMouseOver="this.style.background='#555555'" style="padding:0px 5px; 0px; 5px;" nowrap><a href="/eis/Calendar" style="font-size:18px; color:#ffffff;">中華科技大學</a></td>
		<td onMouseOut="this.style.background='#222222'" onMouseOver="this.style.background='#555555'" style="padding:0px 5px; 0px; 5px;" nowrap><html:link page="/Personnel/MailManager.do" style="font-size:18px; color:#ffffff;">群組郵件</html:link></td>
		<td onMouseOut="this.style.background='#222222'" onMouseOver="this.style.background='#555555'" style="padding:0px 5px; 0px; 5px;" nowrap><html:link page="/Personnel/ListStudent.do" style="font-size:18px; color:#ffffff;">班級查詢</html:link></td>
		<td onMouseOut="this.style.background='#222222'" onMouseOver="this.style.background='#555555'" style="padding:0px 5px; 0px; 5px;" nowrap><html:link page="/Teacher/StdinfoSh.do" style="font-size:18px; color:#ffffff;">學生查詢</html:link></td>
		</c:if>
		</td>
		
		<td onMouseOut="this.style.background='#222222'" onMouseOver="this.style.background='#555555'" style="padding:0px 5px; 0px; 5px;" nowrap><html:link page="/Personnel/ListCourse.do" style="font-size:18px; color:#ffffff;">課程查詢</html:link></td>
		<td onMouseOut="this.style.background='#222222'" onMouseOver="this.style.background='#555555'" style="padding:0px 5px; 0px; 5px;" nowrap><html:link page="/Personnel/RooManager.do" style="font-size:18px; color:#ffffff;"></html:link></td>
		
		
		<td style="padding:0px 5px; 0px; 5px; font-size:18px; color:#ffffff;" width="100%" align="right">		
		<html:link page="/Logout.do" style="font-size:18px; color:#ffffff;"><bean:message key="Logout" /></html:link> ${Credential.member.name}
		</td>
		
		<td style="padding:0px 5px; 0px; 5px; color:#ffffff;" id="myPic"></td>
		<td style="padding:0px 5px; 0px; 5px;">			
		
		</td>		
	</tr>
</table>