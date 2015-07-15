<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rcact_Query" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>${SchoolName_ZH}-教師/職員參加學術活動資料表&nbsp;</b></td>
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
              <table width="99%" border="1">
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
              <table width="99%" border="1">
                <tr>
                  <td>活動名稱</td><td class="hairLineTdF">${actname }</td>
                  <td>主案單位</td><td class="hairLineTdF">${sponoff }</td> 
                </tr>
              </table>
              <table width="99%" border="1">
                <tr>
                  <td>活動類型</td><td class="hairLineTdF">${kindid}</td>
                  <td>活動型態</td><td class="hairLineTdF">${typeid}</td>
                  <td>活動地點</td><td class="hairLineTdF">${placeid}</td>
                  <td>參與情形</td><td class="hairLineTdF">${joinid }</td>
                  <td>時         數</td><td class="hairLineTdF">${hour }</td>                  
                </tr>
                <tr>
                  <td>起始日期</td><td class="hairLineTdF">${bdate }</td>
                  <td>結束日期</td><td class="hairLineTdF">${edate }</td>
                  <td>學校補助情形</td><td class="hairLineTdF">${schspon }</td>
                  <td>是否有證明</td><td class="hairLineTdF">${certyn }</td>
                  <td>證書字號</td><td class="hairLineTdF">${certno }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="10%">研習心得</td><td class="hairLineTdF">${intor }</td>
                </tr>
              </table>             
            </td>
          </tr>
        </table>
      </td>
    </tr>        
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">          
          <tr>
            <td class="hairLineTd" align="center">
              <input type="submit" name="method" value="返回" Key="Back" class="CourseButton"/>
              <hr class="myHr"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </html:form>
</table>

<script>

</script>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/leo_Calendar.jsp" %>