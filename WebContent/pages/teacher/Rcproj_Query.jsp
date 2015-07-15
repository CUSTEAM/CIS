<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rcproj_Query" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>承接政府部門計劃與產學案資料查詢&nbsp;</b></td>
          </tr>
        </table>
      </td>
    </tr>	     
<!-- 標題列 end -->    
    <tr>
      <td>      
        <table width="99%" class="hairLineTable">
          <tr>
            <td>            
              <table>      
                <tr>
                  <td width="20%" class="hairLineTdF">查詢條件
                    <select name="select_type" onChange="showSpecs()">
                      <option value="">請選擇</option>
                      <option value="N">教師姓名</option>
                      <option value="U">系所名稱</option>
                    </select>
                  </td>
                  <td width="30%" class="hairLineTd" align="left" id="nullName" >選擇要依教師或是系所做查詢</td>
                  <td width="30%" class="hairLineTd" align="left" id="tchName" style="display:none">
			        <input type=hidden name="fsidno" id="fsidno" size="12" value=""/>
			        <input type="text" name="fscname" id="fscname" size="10" value=""
				           onMouseOver="showHelpMessage('姓名輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成姓名', 'inline', this.id)" 
				           onMouseOut="showHelpMessage('', 'none', this.id)"										
					       onkeyup="if(this.value.length>0)GgetAny(this.value, 'fscname', 'fsidno', 'dempl', 'name')" 
					       onClick="clearQuery()" />
			      </td>                  
                  <td width="30%" class="hairLineTd" id="unitName" style="display:none">
                    <select name=Tch_Unit>
					  <option value=""></option>
					  <c:forEach items="${Tch_Unit}" var="c">
					    <option value="${c.idno}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>           
                  <td width="15%" align="center" class="hairLineTdF">年度</td>
                  <td class="hairLineTd">
                    <input type="text" name="schoolYear" id="schoolYear" size="1" value=""
                           onMouseOver="showHelpMessage('民國年', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)"/>
                  </td>                          
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>                         
              <table width="99%" class="hairLineTable">
                <tr>                  
                  <td width="15%" align="center" class="hairLineTdF">專案名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="projname" value="" size="45" id="projname"
                           onMouseOver="showHelpMessage('輸入關鍵字即可搜尋', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>                  
                </tr>                
              </table>              
              <table width="99%" class="hairLineTable"> 
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">專案類型</td>
                  <td class="hairLineTd" colspan="3">
                    <select name=kindid>
					  <option value=""></option>
					  <c:forEach items="${kindid}" var="c">
					    <option <c:if test="${aEmpl.kindid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">工作類別</td>
                  <td class="hairLineTd">
                    <select name=jobid>
					  <option value=""></option>
					  <c:forEach items="${jobid}" var="c">
					    <option <c:if test="${aEmpl.jobid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>                    
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">主要經費來源</td>
                  <td class="hairLineTd">
                    <select name=budgetid1>
					  <option value=""></option>
					  <c:forEach items="${budgetid1}" var="c">
					    <option <c:if test="${aEmpl.budgetid1==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
              </table>              
              <table width="99%" class="hairLineTable"> 
                <tr>
                  <td>
                  	摘要/簡述
                  </td>
                  <td colspan="3">
                    <input type="text" name="intor" id="intor" size="50" value=""
                           onMouseOver="showHelpMessage('輸入關鍵字即可搜尋', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                </tr>
              </table>              
            </td>
          </tr>
          <tr>
            <td class="hairLineTd" align="center">
              
              <input type="submit" name="method" value="查詢" Key="Query" class="CourseButton"/>
              
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <c:if test="${RcprojList != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${RcprojList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
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
          <input type="submit" name="method" value="檢視" key="View" onClick="return myTest()" class="CourseButton"/>
          
        </td>
      </tr>
    </c:if>    
  </html:form>
</table>

<script>  
  
  function showSpecs() 
  {
    
    if(document.getElementById("select_type").value == 'N') 
    {
      document.getElementById("tchName").style.display = 'inline';
      document.getElementById("unitName").style.display = 'none';
      document.getElementById("nullName").style.display = 'none';      
    } 
    else if(document.getElementById("select_type").value == 'U')
    {
      document.getElementById("tchName").style.display = 'none';
      document.getElementById("unitName").style.display = 'inline';
      document.getElementById("nullName").style.display = 'none';      				
    }
    else
    {
      document.getElementById("tchName").style.display = 'none';
      document.getElementById("unitName").style.display = 'none';
      document.getElementById("nullName").style.display = 'inline';
    }
  }
  
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

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/leo_Calendar.jsp" %>	