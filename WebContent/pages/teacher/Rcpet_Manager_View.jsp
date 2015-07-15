<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>


<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/Rcpet_Manager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
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
                  <input type=hidden name="oid_s" value="${oid_s }"/>
                  <td width="15%">年度</td><td class="hairLineTdF" width="18%">${school_year }</td>
                  <td width="15%">系所</td><td class="hairLineTdF" width="18%">${TeacherUnit}</td>
                  <td width="15%">教師</td><td class="hairLineTdF" width="18%">${TeacherName}</td>                  
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>            
              <table width="99%" border="2">
                <tr>
                  <td width="15%">計畫案號</td><td class="hairLineTdF" width="35%">${projno }</td>
                  <td width="15%">專利名稱</td><td class="hairLineTdF" width="35%">${title }</td>
                </tr>
              </table>
              <table width="99%" border="2">                
                <tr>
                  <td width="15%">區域</td><td class="hairLineTdF" width="35%">${area }</td>
                  <td width="15%">類型</td><td class="hairLineTdF" width="35%">${petType }</td>                  
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="15%">技術報告分數</td><td class="hairLineTdF" width="18%">${score }</td>
                  <td width="15%">進度狀況</td><td class="hairLineTdF" width="18%">${schedule }</td>
                  <td width="15%">作者順序</td><td class="hairLineTdF" width="18%">${authorno }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="15%">申請人/權利人</td><td class="hairLineTdF" width="35%">${proposer }</td>
                  <td width="15%">申請/權利人類型</td><td class="hairLineTdF" width="35%">${proposerType }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="15%">申請/生效日期</td><td class="hairLineTdF" width="35%">${bdate }</td>
                  <td width="15%">終止日期</td><td class="hairLineTdF" width="35%">${edate }</td>
                </tr> 
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="15%">發照機關</td><td class="hairLineTdF" width="35%">${inst }</td>
                  <td width="15%">證書字號</td><td class="hairLineTdF" width="35%">${certno }</td>
                </tr>
              </table>
              <table width="99%" border="2">
                <tr>                  
                  <td width="15%">技術轉移或授權</td><td class="hairLineTdF" width="35%">${depute }</td>
                  <c:if test="${depute=='授權' || depute=='技術轉移'}" >
                  <td width="15%" >轉移/授權廠商</td><td class="hairLineTdF" width="35%">${deputeBusiness }</td>
                </tr> 
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="15%" >轉移/授權金額</td><td class="hairLineTdF" width="18%">${deputeMoney }</td>
                  <td width="15%" >轉移/授權起始日</td><td class="hairLineTdF" width="18%">${deputeSdate }</td>
                  <td width="15%" >轉移/授權終止日</td><td class="hairLineTdF" width="18%">${deputeEdate }</td>
                  </c:if>
                </tr> 
              </table>
              <table width="99%" border="2">
                <tr>
                  <td width="15%">摘要/簡述</td><td class="hairLineTdF">${intor }</td>
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