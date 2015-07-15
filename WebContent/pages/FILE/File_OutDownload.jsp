<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <!-- link rel="stylesheet" href="/CIS/pages/images/home.css" type="text/css" />  -->
    <link rel="stylesheet" href="http://www.cust.edu.tw/www/info/rule.css" type="text/css" />
    <!-- link rel="stylesheet" href="http://www.cust.edu.tw/www/info/infotable.css" type="text/css" />  -->
  </head>
  <body>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Secretary/File_OutDownload" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 Start -->
<!-- 
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
           <td align="center" valign="middle"><h3><b>${SchoolName_ZH}--法規檢索&nbsp;</b></h3></td>
          </tr>
        </table>
      </td>
    </tr>
-->
<!-- 標題列 End -->
<!-- 資料明系列 Start -->     
	<tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td>                         
			  <table width="99%" class="hairLineTable">
                <tr align="center">
                  <td align="center">輸入關鍵字            
                    <input type="text" name="Data_name" id="Data_name" value="" size="50"/>
		                                 所屬單位			
			        <select name="FN_Unit">
				      <option value="">請選擇單位</option>
				      <c:forEach items="${FN_Unit}" var="c">
				        <option value="${c.Oid}">${c.name}</option>
				      </c:forEach>
			        </select>			
			        <input type="submit" class="gSubmit" name="method" value="<bean:message key='Query'/>" class="CourseButton"/>
			      </td>                          
                </tr>                
              </table>
            </td>
          </tr>                    
        </table>
      </td>
    </tr>  
    <c:if test="${FN_List != null}" >    
	<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	<tr>
	  <td>	        
	    <table width="100%" cellpadding="1" cellspacing="1">
	      <tr>
	       <td align="center">  
	          <display:table name="${FN_List}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		    <c:if test="${empty FN_List}">
	     	    <%@ include file="../include/NoBanner.jsp" %>
	  		    </c:if>
	            <display:column title="<script>generateTriggerAll(${fn:length(FN_List)}, 'FNList');</script>" >
	              <script>generateCheckbox("${row.Oid}", "FNList");</script></display:column> 
	            <display:column title="日期" property="MeetingDate"  sortable="true" />
	            <display:column title="標題"	property="FN_name"	  sortable="true"  />	
	            <display:column title="單位"	property="Unit"  sortable="true"  />	
 	          </display:table>
 	        </td>
 	      </tr>	      
	    </table>
	  </td>
	</tr>
  <!-- 動作按鍵列 Start -->
	<tr>
      <td class="hairLineTd" align="center">
        <input type="submit" class="gCancle" name="method" value="<bean:message key='Download'/>" onClick="return myTest()" class="CourseButton"/>
      </td>
    </tr>
    </c:if>
  <!-- 動作按鍵列 End -->    		
  </html:form>
</table>
</body>
</html>

<script>  
  function myTest()   //執行修改 刪除前的檢查作業
  {
    var iCount;
    iCount = getCookie("FNListCount");
    ID = getCookie("FNList");    
    if (iCount == 0) 
    {
      alert("請勾選至少一項資料!!");
      return false;
    } 
    else if(iCount > 1) 
    {
      alert("請僅勾選一項資料!!");
      return false;
    }
    return true;    
  }

</script>
