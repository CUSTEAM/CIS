<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rcbook_Manager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>專書(含篇章)資料查詢&nbsp;</b></td>
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
                  <td width="10%" align="center" class="hairLineTdF">專書名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="title" value="" size="60" id="title"
                           onMouseOver="showHelpMessage('輸入關鍵字即可搜尋', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="10%" align="center" class="hairLineTdF">專書類別</td>
                  <td width="15%" class="hairLineTd">
                    <select name=type>
					  <option value=""></option>
					  <c:forEach items="${type}" var="c">
					    <option value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="10%" align="center" class="hairLineTdF">作者順序</td>
                  <td width="15%" class="hairLineTd">
                    <select name=authorno>
					  <option value=""></option>
					  <c:forEach items="${authorno}" var="c">
					    <option value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="10%" align="center" class="hairLineTdF">通訊作者</td>
                  <td width="15%" class="hairLineTd">
                    <select name=COM_authorno>
                      <option value=""></option>
                      <option value="Y">是</option>
                      <option value="N">否</option>
                    </select>
                  </td>
                  <td width="10%" align="center" class="hairLineTdF">使用語言</td>
                  <td width="15%" class="hairLineTd">
                    <select name=language>
					  <option value=""></option>
					  <c:forEach items="${language}" var="c">
					    <option value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="10%" align="center" class="hairLineTdF">出版社</td>
                  <td class="hairLineTd">
                    <input type="text" name="publisher" value="" size="60" id="publisher"
                           onMouseOver="showHelpMessage('輸入關鍵字即可搜尋', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
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
                  <td width="15%" class="hairLineTdF">審查狀態</td>
                  <td class="hairLineTd">
                    <select name=approve>
					  <option value="">不分狀態</option>
					  <c:forEach items="${approve}" var="c">
					    <option <c:if test="${aEmpl.joinid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
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
    <c:if test="${RcbookList != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <c:if test="${Approve == ''}" ><font color="red"><b>審核狀態/不分狀態</b></font></c:if>
	      <c:if test="${Approve != ''}" ><font color="red"><b>審核狀態/${Approve }</b></font></c:if>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${RcbookList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty RcbookList}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(RcbookList)}, 'rcbook');</script>" class="center" >
	                <script>generateCheckbox("${row.oid}", "rcbook");</script></display:column>
 	              <display:column title="年度"		property="school_year"	sortable="true" class="center" />
 	              <display:column title="教師姓名"	property="idno"     	sortable="true" class="center" />
	              <display:column title="專書名稱"	property="title"		sortable="true" class="center" />
	              <display:column title="出版社" 	    property="publisher"	sortable="true" class="center" />
	              <display:column title="審核狀態"	property="approve"		sortable="true" class="center" />            
 	            </display:table>
 	          </td>
 	        </tr>	      
	      </table>
	    </td>
	  </tr>
	  <tr>
        <td class="hairLineTd" align="center">
          <input type="submit" name="method" value="檢視" key="View" onClick="return myTest()" class="CourseButton"/>
          <c:if test="${UserUnit == '技研服務組' || UserUnit == '軟體開發組'}" >
            <input type="submit" name="method" value="刪除" Key="Delete" onClick="return myTest2()" class="CourseButton"/>   
          </c:if>          
          <input name="Submit04" type="submit" value="匯出Excel" class="CourseButton"
                 onclick="MM_goToURL('parent','/CIS/pages/teacher/Article/RcbookSel.jsp');return document.MM_returnValue"/>          
        </td>
      </tr>
    </c:if>
  </html:form>
</table>

<script>
  function MM_goToURL() { //v3.0 匯出Excel
    var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
    for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
  }
  
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
  
  function myTest() //檢查檢視項目
  {
    var iCount;
    iCount = getCookie("rcbookCount");    
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
    Oid = getCookie("rcbook");
    //alert(Oid);
    return true;
  }
  
  function myTest2() //檢查刪除項目
  {
    var iCount;
    iCount = getCookie("rcbookCount");    
    if (iCount == 0) 
    {
      alert("請勾選至少一項資料進行刪除!!");
      return false;
    } 
    else if(iCount > 1) 
    {
      alert("請僅勾選一項資料進行刪除!!");
      return false;
    }
    
    var ret = confirm("您確定要刪除此筆資料!?");
    if(ret == true){
      return true;
    }else{
      return false;
    }
    
    Oid = getCookie("rcbook");
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
      alert("專書名稱,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("authorno").value == '') 
    {
      alert("作者順序,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("COM_authorno").value == '') 
    {
      alert("通訊作者,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("language").value == '') 
    {
      alert("使用語言,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("pdate").value == '') 
    {
      alert("出版日期,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("publisher").value == '') 
    {
      alert("出版社,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("isbn").value == '') 
    {
      alert("ISBN編號,欄位不得為空白");
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