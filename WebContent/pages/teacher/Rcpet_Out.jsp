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
            <td align="center" valign="middle"><b>${SchoolName_ZH}-專利資料表&nbsp;</b></td>
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
                  <td width="19%" >年度</td><td class="hairLineTdF">${school_year }</td>
                  <td width="19%" >系所</td><td class="hairLineTdF">${TeacherUnit}</td>
                  <td width="19%" >教師</td><td class="hairLineTdF">${TeacherName}</td>                  
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>            
              <table width="99%" border="2">
                <tr>
                  <td width="19%" >計畫案號</td><td class="hairLineTdF">${projno }</td>
                  <td width="19%" >專利名稱</td><td class="hairLineTdF">${title }</td>
                </tr>
              </table>
              <table width="99%" border="2">                
                <tr>
                  <td width="19%" >區域</td><td class="hairLineTdF">${area }</td>
                  <td width="19%" >類型</td><td class="hairLineTdF">${petType }</td>                  
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="19%" >技術報告分數</td><td class="hairLineTdF">${score }</td>
                  <td width="19%" >進度狀況</td><td class="hairLineTdF">${schedule }</td>
                  <td width="19%" >作者順序</td><td class="hairLineTdF">${authorno }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="19%" >申請人/權利人</td><td class="hairLineTdF">${proposer }</td>
                  <td width="19%" >申請/權利人類型 </td><td class="hairLineTdF">${proposerType }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="19%" >申請/生效日期</td><td class="hairLineTdF">${bdate }</td>
                  <td width="19%" >終止日期</td><td class="hairLineTdF">${edate }</td>
                </tr> 
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="19%" >發照機關</td><td class="hairLineTdF">${inst }</td>
                  <td width="19%" >證書字號</td><td class="hairLineTdF">${certno }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="19%" >技術轉移或授權</td><td class="hairLineTdF">${depute }</td>
                  <c:if test="${depute=='授權' || depute=='技術轉移'}" >
                  <td width="19%" >轉移/授權廠商</td><td class="hairLineTdF">${deputeBusiness }</td>
                </tr> 
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="19%" >轉移/授權金額</td><td class="hairLineTdF">${deputeMoney }</td>
                  <td width="19%" >轉移/授權起始日</td><td class="hairLineTdF">${deputeSdate }</td>
                  <td width="19%" >轉移/授權終止日</td><td class="hairLineTdF">${deputeEdate }</td>
                  </c:if>
                </tr>                
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="19%" >摘要/簡述</td><td class="hairLineTdF">${intor }</td>
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