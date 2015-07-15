<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <link rel="stylesheet" href="/CIS/pages/images/home.css" type="text/css" />
  <link rel="stylesheet" href="http://www.cust.edu.tw/www/info/info.css" type="text/css" />
  <link rel="stylesheet" href="http://www.cust.edu.tw/www/info/infotable.css" type="text/css" />
    </head>
  <body>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/QueryRchono" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><h3><b>${SchoolName_ZH}-頒獎與榮譽資料表查詢&nbsp;</b></h3></td>
          </tr>
        </table>
      </td>
    </tr>
<!-- 條件列 -->
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td>                         
			  <table width="99%" class="hairLineTable">
                <tr>
                  <td width="20%" class="hairLineTdF">教師姓名
                    <input type="text" name="fscname" id="fscname" size="10" value="" />
                                                       年度
                    <input type="text" name="schoolYear" id="schoolYear" size="1" value=""/>
                    <input type="submit" name="method" value="查詢" Key="Query" class="CourseButton"/>
                  </td>                          
                </tr>                
              </table>
            </td>
          </tr>                    
        </table>
      </td>
    </tr>
<!-- 清單列 -->
    <c:if test="${RchonoList != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>	      
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${RchonoList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty RchonoList}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(RchonoList)}, 'rchono');</script>" class="center" >
	                <script>generateCheckbox("${row.oid}", "rchono");</script></display:column>
 	              <display:column title="年度"		property="school_year"	sortable="true" class="center" />
	              <display:column title="教師姓名"	property="idno"     	sortable="true" class="center" />
	              <display:column title="獲獎名稱"	property="title"		sortable="true" class="center" />
	              <display:column title="頒獎國別" 	property="nation" 		sortable="true" class="center" />
	              <display:column title="頒獎機構" 	property="inst"			sortable="true" class="center" />	              	              
 	            </display:table>
 	          </td>
 	        </tr>	      
	      </table>
	    </td>
	  </tr>
	  <tr>
        <td class="hairLineTd" align="center">
          <input type="submit" name="method" value="<bean:message key='View' bundle="TCH" />" key="View" onClick="return myTest()" class="CourseButton"/>          
        </td>
      </tr>
    </c:if>
<!-- 明細列 -->
    <c:if test="${oid_s != null}" >
    <tr>
      <td>      
        <table width="100%" align="left" cellpadding="1" cellspacing="1">
          <tr>
            <td>            
              <table width="99%" border="2">
                <tr>
                  <td width="10%" bgcolor="#E1D2A6">年度</td><td class="hairLineTdF">${school_year }</td>
                  <td width="10%" bgcolor="#E1D2A6">系所</td><td class="hairLineTdF">${TeacherUnit}</td>
                  <td width="10%" bgcolor="#E1D2A6">教師</td><td class="hairLineTdF">${TeacherName}</td>                  
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>            
              <table width="99%" border="2">
                <tr>
                  <td width="10%" bgcolor="#E1D2A6">計畫案號</td><td class="hairLineTdF">${projno }</td>
                  <td width="10%" bgcolor="#E1D2A6">獲獎名稱</td><td class="hairLineTdF">${title }</td>
                </tr>
              </table>              
              <table width="99%" border="2">
                <tr>                  
                  <td width="10%" bgcolor="#E1D2A6">作者順序</td><td class="hairLineTdF">${authorno }</td>
                  <td width="10%" bgcolor="#E1D2A6">頒獎國別</td><td class="hairLineTdF">${nation }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>                  
                  <td width="10%" bgcolor="#E1D2A6">頒獎機構</td><td class="hairLineTdF">${inst }</td>
                  <td width="10%" bgcolor="#E1D2A6">頒獎日期</td><td class="hairLineTdF">${bdate }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="10%" bgcolor="#E1D2A6">摘要/簡述</td><td class="hairLineTdF">${intor }</td>
                </tr>
              </table>              
            </td>
          </tr>
        </table>
      </td>
    </tr> 
    </c:if> 
<!-- 標題列 end -->	
  </html:form>
</table>
  </body>
</html>


<script>
  function myTest() 
  {
    var iCount;
    iCount = getCookie("rchonoCount");    
    if (iCount == 0) 
    {
      alert("請勾選至少一項資料進行檢視!!");
      return false;
    } 
    else if(iCount > 1) 
    {
      alert("請僅勾選一項資料進行檢視!!");
      return false;
    }
    Oid = getCookie("rchono");
    //alert(Oid);
    return true;
  }
   </script>
