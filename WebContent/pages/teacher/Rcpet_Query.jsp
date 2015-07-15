<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rcpet_Query" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>專利資料表 &nbsp;</b></td>
          </tr>
        </table>
      </td>
    </tr>	    
<!-- 標題列 end -->    
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">
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
                  <td width="15%" align="center" class="hairLineTdF">專利名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="title" value="" size="45" id="title"
                           onMouseOver="showHelpMessage('輸入關鍵字即可搜尋', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">區域</td>
                  <td class="hairLineTd">
                    <select name=area>
					  <option value=""></option>
					  <c:forEach items="${area}" var="c">
					    <option <c:if test="${aEmpl.area==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">類型</td>
                  <td class="hairLineTd">
                    <select name=petType>
					  <option value=""></option>
					  <c:forEach items="${petType}" var="c">
					    <option <c:if test="${aEmpl.petType==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">報告分數</td>
                  <td class="hairLineTd">
                    <select name=score>					  
					  <c:forEach items="${score}" var="c">
					    <option value=""></option>
					    <option value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">進度狀況</td>
                  <td class="hairLineTd">
                    <select name=schedule>
					  <option value=""></option>
					  <c:forEach items="${schedule}" var="c">
					    <option <c:if test="${aEmpl.schedule==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">作者順序</td>
                  <td class="hairLineTd">
                    <select name=authorno>
					  <option value=""></option>
					  <c:forEach items="${authorno}" var="c">
					    <option <c:if test="${aEmpl.authorno==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
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
              <table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
		        <tr>
				  <td id="ds_calclass"></td>
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
    <c:if test="${RcpetList != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${RcpetList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty RcpetList}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(RcpetList)}, 'rcpet');</script>" class="center" >
	                <script>generateCheckbox("${row.oid}", "rcpet");</script></display:column>
 	              <display:column title="年度"		property="school_year"	sortable="true" class="center" />
	              <display:column title="教師姓名"	property="idno"     	sortable="true" class="center" />
	              <display:column title="專利名稱"	property="title"		sortable="true" class="center" />
	              <display:column title="開始日期" 	property="bdate"		sortable="true" class="center" />
	              <display:column title="結束日期" 	property="edate"		sortable="true" class="center" />
	              <display:column title="發照機關" 	property="inst"		sortable="true" class="center" />	                       
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
    iCount = getCookie("rcpetCount");    
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
    Oid = getCookie("rcpet");
    //alert(Oid);
    return true;
  }
  
  function mySave() 
  {        
    if (document.getElementById("projno").value == '') 
    {
      alert("計畫案號,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("title").value == '') 
    {
      alert("專利名稱,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("authorno").value == '') 
    {
      alert("作者順序,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("area").value == '') 
    {
      alert("專利區域,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("petType").value == '') 
    {
      alert("專利類型,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("schedule").value == '') 
    {
      alert("進度狀況,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("bdate").value == '') 
    {
      alert("開始日期,欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("edate").value == '') 
    {
      alert("結束日期,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("inst").value == '') 
    {
      alert("發照機關,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("certno").value == '') 
    {
      alert("證書字號,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("depute").value == '') 
    {
      alert("授權轉移,此欄位不得為空白");
      return false;
    } 
    else 
    
    return true;
  }
</script>	

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/leo_Calendar.jsp" %>