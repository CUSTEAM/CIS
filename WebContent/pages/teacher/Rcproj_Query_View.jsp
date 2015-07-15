<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>


<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/Rcproj_Query" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>${SchoolName_ZH}-承接政府部門計劃與產學案資料表&nbsp;</b></td>
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
                <tr>
                  <td>專案案號</td><td class="hairLineTdF">${projno }</td>
                  <td>專案名稱</td><td class="hairLineTdF">${projname }</td>
                  <td>專案類型</td><td class="hairLineTdF">${kindid}</td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>            
              <table width="99%" border="1">
                
                <tr>
                  <td>起始日期</td><td class="hairLineTdF">${bdate }</td>
                  <td>結束日期</td><td class="hairLineTdF">${edate }</td>
                  <td>工作類別</td><td class="hairLineTdF">${jobid}</td>
                  <td>研究金額</td><td class="hairLineTdF">${money }</td>
                </tr>
                <tr>
                  <td>政府出資</td><td class="hairLineTdF">${G_money }</td>
                  <td>企業出資</td><td class="hairLineTdF">${B_money }</td>
                  <td>其他單位出資</td><td class="hairLineTdF">${O_money }</td>
                  <td>學校出資</td><td class="hairLineTdF">${S_money }</td>
                </tr>                  
                <tr>
                  <td>主經費來源</td><td class="hairLineTdF">${budgetid1}</td>
                  <td>單位名稱</td><td class="hairLineTdF">${unitname }</td>
                  <td>次經費來源</td><td class="hairLineTdF">${budgetid2 }</td>
                  <td>受惠單位</td><td class="hairLineTdF">${favorunit }</td>
                </tr>
                <tr>
                  <td>國內委託單位</td><td class="hairLineTdF">${authorunit1 }</td>
                  <td>國外委託單位</td><td class="hairLineTdF">${authorunit2 }</td>
                  <td>國內合作單位</td><td class="hairLineTdF">${coopunit1 }</td>
                  <td>國外合作單位</td><td class="hairLineTdF">${coopunit2 }</td>
                </tr>
              </table>
              <!--  
              <table width="99%" border="1">
                <tr>
                  <td>專任人員</td><td class="hairLineTdF">${FullTime }</td>
                  <td>兼任人員</td><td class="hairLineTdF">${PartTime }</td>
                  <td>政府委訓人數</td><td class="hairLineTdF">${G_trainee }</td>
                  <td>企業委訓人數</td><td class="hairLineTdF">${B_trainee }</td>
                  <td>其他單位委訓人數</td><td class="hairLineTdF">${O_trainee }</td>
                </tr>
              </table>
              -->
              <table width="99%" border="1">
                <tr>
                  <td>經費狀況</td><td class="hairLineTdF">${budgetidState }</td>
                  <td>他校轉入的專案</td><td class="hairLineTdF">${turnIn }</td>
                  <td>專案已轉至他校</td><td class="hairLineTdF">${turnOut }</td>                  
                </tr>
              </table>
              <table width="99%" border="1">
                <c:if test="${turnIn == '是'}" >
                <tr>
                  <td>他校轉入政府出資</td><td class="hairLineTdF">${turnIn_G }</td>
                  <td>他校轉入企業出資</td><td class="hairLineTdF">${turnIn_B }</td>
                  <td>他校轉入其他單位出資</td><td class="hairLineTdF">${turnIn_O }</td>                  
                </tr>
                </c:if>
                <c:if test="${turnOut == '是'}" >
                <tr>
                  <td>已轉至他校政府出資</td><td class="hairLineTdF">${turnOut_G }</td>
                  <td>已轉至他校企業出資</td><td class="hairLineTdF">${turnOut_B }</td>
                  <td>已轉至他校其他單位出資</td><td class="hairLineTdF">${turnOut_O }</td>                  
                </tr>
                </c:if>
              </table>
              <table width="99%" border="1">
                <c:if test="${kind == '83'}" >
                <tr>
                  <td>有無技轉</td><td class="hairLineTdF">${Transfer }</td>
                  <td>研發收入</td><td class="hairLineTdF">${Income }</td>
                  <td>保管單位</td><td class="hairLineTdF">${Storage }</td>                  
                </tr>               
                <tr>
                  <td rowspan="6">研發成果</td><td class="hairLineTdF" colspan="5">
                    <c:if test="${chec1 == '1'}">(1.國內外專利):${chec1text }<br></c:if>
                    <c:if test="${chec2 == '1'}">(2.商標權 ):${chec2text }<br></c:if>
                    <c:if test="${chec3 == '1'}">(3.營業機密):${chec3text }<br></c:if>
                    <c:if test="${chec4 == '1'}">(4.積體電路電路佈局權):${chec4text }<br></c:if>
                    <c:if test="${chec5 == '1'}">(5.著作權 ):${chec5text }<br></c:if>
                    <c:if test="${chec6 == '1'}">(6.其他智慧財產權及成果):${chec6text }</c:if>
                  </td>
                </tr>
                </c:if>
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