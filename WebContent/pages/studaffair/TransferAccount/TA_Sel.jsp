<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>

<%  
  response.setContentType("application/vnd.ms-excel");
  response.setHeader("Content-disposition","attachment;filename=TransferAccount.txt");
  
%>
<c:if test="${not empty DipostList}">
程式名稱:TransferAccount                                                     
          
班級名稱       學   號    姓  名   立帳局號  儲戶帳號  身分證字號   轉帳金額
----------------------------------------------------------------------------
    <%int i=0;%>
	<c:forEach items="${DipostList}" var="RL">		
	<%i=i+1;%>		
${RL.ClassName}  ${RL.studentNo}  ${RL.student_name}  ${RL.officeNo}  ${RL.acctNo}  ${RL.idno}    $${RL.pMoney}
----------------------------------------------------------------------------
	</c:forEach>
********** 轉帳人數: ${My_No} 轉帳金額: ${Money}
</c:if>