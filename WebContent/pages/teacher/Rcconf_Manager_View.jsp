<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>


<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/Rcconf_Manager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>${SchoolName_ZH}-研討會論文發表資料表&nbsp;</b></td>
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
                  <td>年度</td><td class="hairLineTdF">${school_year }</td>
                  <td>系所</td><td class="hairLineTdF">${TeacherUnit}</td>
                  <td>教師</td><td class="hairLineTdF">${TeacherName}</td>                  
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>            
              <table width="99%" border="2">
                <tr>
                  <td>計畫案號</td><td class="hairLineTdF">${projno }</td>
                  <td>論文名稱</td><td class="hairLineTdF">${title }</td>
                </tr>
              </table>
              <table width="99%" border="2">                
                <tr>
                  <td>作者順序</td><td class="hairLineTdF">${authorno }</td>
                  <td>通訊作者</td><td class="hairLineTdF">${COM_authorno }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td>研討會名稱</td><td class="hairLineTdF">${jname }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td>舉行國家</td><td class="hairLineTdF">${nation }</td>
                  <td>舉行城市</td><td class="hairLineTdF">${city }</td>
                  <td>開始日期</td><td class="hairLineTdF">${bdate }</td>
                  <td>結束日期</td><td class="hairLineTdF">${edate }</td>
                  <td>發表年份</td><td class="hairLineTdF">${pyear }</td>
                </tr> 
              </table>
              <table width="99%" border="2">
                <tr>
                  <td>摘要/簡述</td><td class="hairLineTdF">${intor }</td>
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
        <c:if test="${UserUnit == '技研服務組' || UserUnit == '軟體開發組'}" >'}" >
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