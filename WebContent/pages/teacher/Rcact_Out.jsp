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
            <td align="center" valign="middle"><b>${SchoolName_ZH}-教師/職員參加學術活動研習心得報告&nbsp;</b></td>
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
                  <td width="10%">年度</td><td class="hairLineTdF">${school_year }</td>
                  <td width="10%">系所</td><td class="hairLineTdF">${TeacherUnit}</td>
                  <td width="10%">教師</td><td class="hairLineTdF">${TeacherName}</td>                  
                </tr>                
              </table>
            </td>
          </tr>
          <tr>
            <td>            
              <table width="99%" border="1">
                <tr>
                  <td width="12%">活動名稱</td><td class="hairLineTdF">${actname }</td>
                  <td width="12%">主案單位</td><td class="hairLineTdF">${sponoff }</td>
                  <td width="12%">活動類型</td><td class="hairLineTdF">${kindid}</td>
                  <td width="12%">活動型態</td><td class="hairLineTdF">${typeid}</td>
                </tr>
                <tr>
                  <td width="12%">活動地點</td><td class="hairLineTdF">${placeid}</td>
                  <td width="12%">參與情形</td><td class="hairLineTdF">${joinid }</td>
                  <td width="12%">起始日期</td><td class="hairLineTdF">${bdate }</td>
                  <td width="12%">結束日期</td><td class="hairLineTdF">${edate }</td>
                </tr>
                <tr>
                  <td width="12%">時         數</td><td class="hairLineTdF">${hour }</td>
                  <td width="12%">學校補助情形</td><td class="hairLineTdF">${schspon }</td>
                  <td width="12%">是否有證明</td><td class="hairLineTdF">${certyn }</td>
                  <td width="12%">證書字號</td><td class="hairLineTdF">${certno }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="12%">研習心得</td><td class="hairLineTdF">${intor }</td>
                </tr>
              </table>              
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td>
        <input type="submit" name="Submit" id="Submit" onMouseOver="showHelpMessage('列印前,請先按F5重新整理', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" value="列印" onclick="printpage();"/>        
                      列印前,請先按F5重新整理!!
      </td>
    </tr>      

</table>

  

<script>

  function printpage() {
	window.print();
  }  
</script>		