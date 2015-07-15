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
  <html:form action="/Teacher/QueryRcproj" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><h3><b>${SchoolName_ZH}-承接政府部門計劃與產學案資料查詢&nbsp;</b></h3></td>
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
    <c:if test="${RcprojList != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${RcprojList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty RcprojList}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(RcprojList)}, 'rcproj');</script>" class="center" >
	                <script>generateCheckbox("${row.oid}", "rcproj");</script></display:column>
 	              <display:column title="年度"		property="school_year"	sortable="true" class="center" />
 	              <display:column title="教師姓名"	property="idno"     	sortable="true" class="center" />
	              <display:column title="專案案號"	property="projno"		sortable="true" class="center" />
	              <display:column title="專案名稱"	property="projname"		sortable="true" class="center" />	              	              
	              <display:column title="起始日期" 	property="bdate" 		sortable="true" class="center" />
	              <display:column title="結束日期" 	property="edate" 		sortable="true" class="center" />	                            
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
              <table width="99%" border="1">
                <tr>
                  <td width="10%" bgcolor="#E1D2A6">年度</td><td class="hairLineTdF">${school_year }</td>
                  <td width="10%" bgcolor="#E1D2A6">系所</td><td class="hairLineTdF">${TeacherUnit}</td>
                  <td width="10%" bgcolor="#E1D2A6">教師</td><td class="hairLineTdF">${TeacherName}</td>                  
                </tr>
                <tr>
                  <td width="10%" bgcolor="#E1D2A6">專案案號</td><td class="hairLineTdF">${projno }</td>
                  <td width="10%" bgcolor="#E1D2A6">專案名稱</td><td class="hairLineTdF">${projname }</td>
                  <td width="10%" bgcolor="#E1D2A6">專案類型</td><td class="hairLineTdF">${kindid}</td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>            
              <table width="99%" border="1">
                
                <tr>
                  <td width="10%" bgcolor="#E1D2A6">起始日期</td><td class="hairLineTdF">${bdate }</td>
                  <td width="10%" bgcolor="#E1D2A6">結束日期</td><td class="hairLineTdF">${edate }</td>
                  <td width="10%" bgcolor="#E1D2A6">工作類別</td><td class="hairLineTdF">${jobid}</td>
                  <td width="10%" bgcolor="#E1D2A6">研究金額</td><td class="hairLineTdF">${money }</td>
                </tr>
                <tr>
                  <td width="10%" bgcolor="#E1D2A6">政府出資</td><td class="hairLineTdF">${G_money }</td>
                  <td width="10%" bgcolor="#E1D2A6">企業出資</td><td class="hairLineTdF">${B_money }</td>
                  <td width="10%" bgcolor="#E1D2A6">其他單位出資</td><td class="hairLineTdF">${O_money }</td>
                  <td width="10%" bgcolor="#E1D2A6">學校出資</td><td class="hairLineTdF">${S_money }</td>
                </tr>                  
                <tr>
                  <td width="10%" bgcolor="#E1D2A6">主經費來源</td><td class="hairLineTdF">${budgetid1}</td>
                  <td width="10%" bgcolor="#E1D2A6">單位名稱</td><td class="hairLineTdF">${unitname }</td>
                  <td width="10%" bgcolor="#E1D2A6">次經費來源</td><td class="hairLineTdF">${budgetid2 }</td>
                  <td width="10%" bgcolor="#E1D2A6">受惠單位</td><td class="hairLineTdF">${favorunit }</td>
                </tr>
                <tr>
                  <td width="10%" bgcolor="#E1D2A6">國內委託單位</td><td class="hairLineTdF">${authorunit1 }</td>
                  <td width="10%" bgcolor="#E1D2A6">國外委託單位</td><td class="hairLineTdF">${authorunit2 }</td>
                  <td width="10%" bgcolor="#E1D2A6">國內合作單位</td><td class="hairLineTdF">${coopunit1 }</td>
                  <td width="10%" bgcolor="#E1D2A6">國外合作單位</td><td class="hairLineTdF">${coopunit2 }</td>
                </tr>
              </table>
              <!--  
              <table width="99%" border="1">
                <tr>
                  <td width="10%" bgcolor="#E1D2A6">專任人員</td><td class="hairLineTdF">${FullTime }</td>
                  <td width="10%" bgcolor="#E1D2A6">兼任人員</td><td class="hairLineTdF">${PartTime }</td>
                  <td width="10%" bgcolor="#E1D2A6">政府委訓人數</td><td class="hairLineTdF">${G_trainee }</td>
                  <td width="10%" bgcolor="#E1D2A6">企業委訓人數</td><td class="hairLineTdF">${B_trainee }</td>
                  <td width="12%" bgcolor="#E1D2A6">其他單位委訓人數</td><td class="hairLineTdF">${O_trainee }</td>
                </tr>
              </table>
              -->
              <table width="99%" border="1">
                <tr>
                  <td width="10%" bgcolor="#E1D2A6">經費狀況</td><td class="hairLineTdF">${budgetidState }</td>
                  <td width="10%" bgcolor="#E1D2A6">他校轉入的專案</td><td class="hairLineTdF">${turnIn }</td>
                  <td width="10%" bgcolor="#E1D2A6">專案已轉至他校</td><td class="hairLineTdF">${turnOut }</td>                  
                </tr>
              </table>
              <table width="99%" border="1">
                <c:if test="${turnIn == '是'}" >
                <tr>
                  <td width="15%" bgcolor="#E1D2A6">他校轉入政府出資</td><td class="hairLineTdF">${turnIn_G }</td>
                  <td width="15%" bgcolor="#E1D2A6">他校轉入企業出資</td><td class="hairLineTdF">${turnIn_B }</td>
                  <td width="15%" bgcolor="#E1D2A6">他校轉入其他單位出資</td><td class="hairLineTdF">${turnIn_O }</td>                  
                </tr>
                </c:if>
                <c:if test="${turnOut == '是'}" >
                <tr>
                  <td width="15%" bgcolor="#E1D2A6">已轉至他校政府出資</td><td class="hairLineTdF">${turnOut_G }</td>
                  <td width="15%" bgcolor="#E1D2A6">已轉至他校企業出資</td><td class="hairLineTdF">${turnOut_B }</td>
                  <td width="15%" bgcolor="#E1D2A6">已轉至他校其他單位出資</td><td class="hairLineTdF">${turnOut_O }</td>                  
                </tr>
                </c:if>
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
    iCount = getCookie("rcprojCount");    
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
    Oid = getCookie("rcproj");
    //alert(Oid);
    return true;
  }
   </script>
