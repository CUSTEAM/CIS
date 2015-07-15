<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>


<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>${SchoolName_ZH}-教師專書(含篇章)資料表&nbsp;</b></td>
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
                  <td width="10%">年度</td><td class="hairLineTdF">${school_year }</td>
                  <td width="10%">系所</td><td class="hairLineTdF">${TeacherUnit}</td>
                  <td width="10%">教師</td><td class="hairLineTdF">${TeacherName}</td>                  
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>            
              <table width="99%" border="2">
                <tr>
                  <td width="10%">計畫案號</td><td class="hairLineTdF">${projno }</td>
                  <td width="10%">專書名稱</td><td class="hairLineTdF">${title }</td>
                </tr>
              </table>
              <table width="99%" border="2">                
                <tr>
                  <td width="10%">專書類別</td><td class="hairLineTdF">${type }</td>                  
                  <td width="10%">作者順序</td><td class="hairLineTdF">${authorno }</td>
                  <td width="10%">通訊作者</td><td class="hairLineTdF">${COM_authorno }</td>
                  <td width="10%">使用語言</td><td class="hairLineTdF">${language }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="10%">出版社</td><td class="hairLineTdF">${publisher }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="10%">出版日期</td><td class="hairLineTdF">${pdate }</td>
                  <td width="10%">ISBN編號</td><td class="hairLineTdF">${isbn }</td>
                </tr> 
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="10%">摘要/簡述</td><td class="hairLineTdF">${intor }</td>
                </tr>
              </table>              
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td>
        <input type="submit" name="Submit" value="列印" onclick="printpage();"/>列印前,請先按F5重新整理!!        
      </td>
    </tr>      

</table>

  

<script>

  function printpage() {
	window.print();
  }  
</script>		