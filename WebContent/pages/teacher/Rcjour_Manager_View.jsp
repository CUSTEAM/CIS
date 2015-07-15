<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>


<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/Rcjour_Manager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>${SchoolName_ZH}-期刊論文發表資料表&nbsp;</b></td>
          </tr>
        </table>
      </td>
    </tr>	
<!-- 標題列 end -->
    
    <tr>
      <td>      
        <table width="100%" align="left" cellpadding="1" cellspacing="1">
          <tr>
            <td>            
              <table width="99%" border="2">
                <tr>
                  <input type=hidden name="oid_s" value="${oid_s }"/>
                  <td width="10%">年度</td><td width="15%" class="hairLineTdF">${school_year }</td>
                  <td width="10%">系所</td><td class="hairLineTdF">${TeacherUnit}</td>
                  <td width="10%">教師</td><td width="15%" class="hairLineTdF">${TeacherName}</td>                  
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>            
              <table width="99%" border="2">
                <tr>
                  <td width="10%">計畫案號</td><td width="15%" class="hairLineTdF">${projno }</td>
                  <td width="10%">論文名稱</td><td class="hairLineTdF">${title }</td>
                </tr>
              </table>
              <table width="99%" border="2">                
                <tr>
                  <td width="10%">收錄分類</td><td width="15%" class="hairLineTdF">${kindid }</td>
                  <td width="10%">作者順序</td><td width="15%" class="hairLineTdF">${authorno }</td>
                  <td width="10%">通訊作者</td><td width="15%" class="hairLineTdF">${COM_authorno }</td>
                  <td width="10%">發刊地點</td><td width="15%" class="hairLineTdF">${place }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="10%">刊物名稱</td><td class="hairLineTdF">${jname }</td>
                  <td width="10%">發表型式</td><td width="15%" class="hairLineTdF">${type }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="10%">發表卷數</td><td width="15%" class="hairLineTdF">${volume }</td>
                  <td width="10%">發表期數</td><td width="15%" class="hairLineTdF">${period }</td>
                  <td width="10%">發表年份</td><td width="15%" class="hairLineTdF">${pyear }</td>
                  <td width="10%">發表月份</td><td width="15%" class="hairLineTdF">${pmonth }</td>
                </tr> 
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="10%">摘要/簡述</td><td class="hairLineTdF">${intor }</td>
                </tr>                
              </table>
              <table width="99%" border="2">
                <tr>
                  <font color=red><b>資料審查作業</b></font>
                </tr>
                <tr>
                  <td width="10%">審查狀態</td>
                  <td class="hairLineTd">
                    <select name="approve" onChange="showTemp()">
					  <c:forEach items="${approve}" var="c">
					    <option <c:if test="${approve_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
                <tr id="appTemp" <c:if test="${approve_v!='98'}">style="display:none"</c:if>>                  
                  <td width="10%">不核可原因</td>                  
				  <td>
					<textarea name="approveTemp" rows="5" cols="70">${approveTemp }</textarea>					
                  </td>
                </tr>
              </table>              
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td class="hairLineTd" align="center">
        <c:if test="${UserUnit == '技研服務組' || UserUnit == '軟體開發組'}" >
          <input type="submit" name="method" value="儲存" Key="Save" class="CourseButton"/>
        </c:if>
        <input type="submit" name="method" value="返回" Key="Back" class="CourseButton"/>       
      </td>
    </tr>      
</html:form>
</table>

  

<script>

  function printpage() {
	window.print();
  }
  
  function printpage() {
	window.print();
  }
  
  function showTemp() 
  {
    
    if(document.getElementById("approve").value == '98') 
    {
      document.getElementById("appTemp").style.display = 'inline';      
    }
    else
    {
      document.getElementById("appTemp").style.display = 'none';      
    }
  }
    
</script>		