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
  <html:form action="/Teacher/QueryRcpet" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><h3><b>${SchoolName_ZH}-專利資料表查詢&nbsp;</b></h3></td>
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
    <c:if test="${RcpetList != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>	      
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${RcpetList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty RcpetList}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(RcpetList)}, 'rcpet');</script>" class="center" >
	                <script>generateCheckbox("${row.oid}", "rcpet");</script></display:column>
 	              <display:column title="年度"		property="school_year"	sortable="true" class="center" />
	              <display:column title="教師姓名"	property="idno"     	sortable="true" class="center" />
	              <display:column title="專利名稱"	property="title"		sortable="true" class="center" />
	              <display:column title="開始日期" 	property="bdate"		sortable="true" class="center" />
	              <display:column title="結束日期" 	property="edate"		sortable="true" class="center" />
	              <display:column title="發照機關" 	property="inst"		sortable="true" class="center" />	              	              
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
                  <td width="20%" bgcolor="#E1D2A6">年度</td><td class="hairLineTdF">${school_year }</td>
                  <td width="20%" bgcolor="#E1D2A6">系所</td><td class="hairLineTdF">${TeacherUnit}</td>
                  <td width="20%" bgcolor="#E1D2A6">教師</td><td class="hairLineTdF">${TeacherName}</td>                  
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>            
              <table width="99%" border="2">
                <tr>
                  <td width="20%" bgcolor="#E1D2A6">計畫案號</td><td class="hairLineTdF">${projno }</td>
                  <td width="20%" bgcolor="#E1D2A6">專利名稱</td><td class="hairLineTdF">${title }</td>
                </tr>
              </table>
              <table width="99%" border="2">                
                <tr>
                  <td width="20%" bgcolor="#E1D2A6">區域</td><td class="hairLineTdF">${area }</td>
                  <td width="20%" bgcolor="#E1D2A6">類型</td><td class="hairLineTdF">${petType }</td>                  
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="20%" bgcolor="#E1D2A6">技術報告分數</td><td class="hairLineTdF">${score }</td>
                  <td width="20%" bgcolor="#E1D2A6">進度狀況</td><td class="hairLineTdF">${schedule }</td>
                  <td width="20%" bgcolor="#E1D2A6">作者順序</td><td class="hairLineTdF">${authorno }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="20%" bgcolor="#E1D2A6">申請人/權利人</td><td class="hairLineTdF">${proposer }</td>
                  <td width="20%" bgcolor="#E1D2A6">申請/權利人類型</td><td class="hairLineTdF">${proposerType }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="20%" bgcolor="#E1D2A6">申請/生效日期</td><td class="hairLineTdF">${bdate }</td>
                  <td width="20%" bgcolor="#E1D2A6">終止日期</td><td class="hairLineTdF">${edate }</td>
                </tr> 
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="20%" bgcolor="#E1D2A6">發照機關</td><td class="hairLineTdF">${inst }</td>
                  <td width="20%" bgcolor="#E1D2A6">證書字號</td><td class="hairLineTdF">${certno }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>                  
                  <td width="20%" bgcolor="#E1D2A6">技術轉移或授權</td><td class="hairLineTdF">${depute }</td>
                  <c:if test="${depute=='授權' || depute=='技術轉移'}" >
                  <td width="20%" bgcolor="#E1D2A6" >轉移/授權廠商</td><td class="hairLineTdF" width="35%">${deputeBusiness }</td>
                </tr> 
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="20%" bgcolor="#E1D2A6">轉移/授權金額</td><td class="hairLineTdF" width="18%">${deputeMoney }</td>
                  <td width="20%" bgcolor="#E1D2A6">轉移/授權起始日</td><td class="hairLineTdF" width="18%">${deputeSdate }</td>
                  <td width="20%" bgcolor="#E1D2A6">轉移/授權終止日</td><td class="hairLineTdF" width="18%">${deputeEdate }</td>
                  </c:if>
                </tr> 
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="20%" bgcolor="#E1D2A6">摘要/簡述</td><td class="hairLineTdF">${intor }</td>
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
    iCount = getCookie("rcpetCount");    
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
    Oid = getCookie("rcpet");
    //alert(Oid);
    return true;
  }
   </script>
