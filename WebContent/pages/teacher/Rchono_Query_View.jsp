<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>


<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/Rchono_Query" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>${SchoolName_ZH}-頒獎與榮譽資料表 &nbsp;</b></td>
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
                  <td>獲獎名稱</td><td class="hairLineTdF">${title }</td>
                </tr>
              </table>              
              <table width="99%" border="2">
                <tr>                  
                  <td>作者順序</td><td class="hairLineTdF">${authorno }</td>
                  <td>頒獎國別</td><td class="hairLineTdF">${nation }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>                  
                  <td>頒獎機構</td><td class="hairLineTdF">${inst }</td>
                  <td>頒獎日期</td><td class="hairLineTdF">${bdate }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td>摘要/簡述</td><td class="hairLineTdF">${intor }</td>
                </tr>
              </table>              
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td>
        <input type="submit" name="method" value="返回" Key="Back" class="CourseButton"/>       
      </td>
    </tr>      
</html:form>
</table>

  

<script>

  function printpage() {
	window.print();
  }  
</script>		