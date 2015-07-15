<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Student/Reward" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 Start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>獎懲紀錄表&nbsp;</b></td>
          </tr>
        </table>
      </td>
    </tr>
<!-- 標題列 End -->

<!-- 問候列 Start -->		
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr height="35" align="center" valign="middle" bgcolor="#CCCCFF">
            <font color="blue">${TeacherUnit}&nbsp;&nbsp;${userName}&nbsp;同學&nbsp;</font>
          </tr>
        </table>        
      </td>
    </tr>
<!-- 問候列 End -->
<!-- 資料明系列 Start -->    
    <c:if test="${Desd_List != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${Desd_List}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty Desd_List}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>	              
 	              <display:column title="日期"	property="ddate"  sortable="true" class="center" />
	              <display:column title="文號 "	property="no"	  sortable="true" class="center" />
	              <display:column title="原因"	property="name"   sortable="true" class="center" />	
	              <display:column title="種類1"	property="name1"  sortable="true" class="center" />
	              <display:column title="次數"	property="cnt1"   sortable="true" class="center" />
	              <display:column title="種類2"	property="name2"  sortable="true" class="center" />
	              <display:column title="次數"	property="cnt2"   sortable="true" class="center" />	                            	              
 	            </display:table>
 	          </td>
 	        </tr>	      
	      </table>
	    </td>
	  </tr>  
    </c:if>
<!-- 資料明系列 End -->
  </html:form>
</table>

<script>
 
</script>

