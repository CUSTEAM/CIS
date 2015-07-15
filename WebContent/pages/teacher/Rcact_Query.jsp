<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rcact_Query" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>教師/職員參加學術活動資料查詢&nbsp;</b></td>
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
                                    
                </tr>
              </table>   
			  <table width="99%" class="hairLineTable">
                <tr>
                  <td width="20%" class="hairLineTdF">查詢條件
                    <select name="select_type" onChange="showSpecs()">
                      <option value="">請選擇</option>
                      <option value="N">教師姓名</option>
                      <option value="U">單位名稱</option>                      
                      <option value="UnitTeach">教師類</option>
                      <option value="Unit">職員類</option>
                    </select>
                  </td>
                  <td width="30%" class="hairLineTd" align="left" id="nullName" >選擇要依何條件做查詢</td>
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
                  <td width="15%" class="hairLineTdF" align="center">活動名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="actname" id="actname" value="" size="45"
                           onMouseOver="showHelpMessage('輸入關鍵字即可搜尋', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">主辦單位(進修機構)</td>
                  <td class="hairLineTd">
                    <input type="text" name="sponoff" id="sponoff"  value="" size="45" 
                           onMouseOver="showHelpMessage('輸入關鍵字即可搜尋', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">活動種類</td>
                  <td class="hairLineTd">
                    <select name=kindid>
					  <option value=""></option>
					  <c:forEach items="${kindid}" var="c">
					    <option <c:if test="${aEmpl.kindid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">活動類型</td>
                  <td class="hairLineTd">
                    <select name=typeid>
					  <option value=""></option>
					  <c:forEach items="${typeid}" var="c">
					    <option <c:if test="${aEmpl.typeid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">活動地點</td>
                  <td class="hairLineTd">
                    <select name=placeid>
					  <option value=""></option>
					  <c:forEach items="${placeid}" var="c">
					    <option <c:if test="${aEmpl.placeid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">參與情形</td>
                  <td class="hairLineTd">
                    <select name=joinid>
					  <option value=""></option>
					  <c:forEach items="${joinid}" var="c">
					    <option <c:if test="${aEmpl.joinid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>                
                
                <tr>
                  <td>
                  	研習心得
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
              <!-- input type="submit" name="method" value="<bean:message key='Create'/>" onClick="return mySave()" class="CourseButton"/ -->
              <input type="submit" name="method" value="查詢" Key="Query" class="CourseButton"/>
              <hr class="myHr"/>
            </td>
          </tr>
        </table>		
      </td>
    </tr>
    <c:if test="${RcactList != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td bgcolor="#CCCCFF">
	      <c:if test="${kind != ''}" ><font color="blue"><b>活動種類/${kind } ; </b></font></c:if>
	      <c:if test="${type != ''}" ><font color="blue"><b>活動類型/${type } ; </b></font></c:if>
	      <c:if test="${place != ''}" ><font color="blue"><b>活動種類/${place } ; </b></font></c:if>
	      <c:if test="${join != ''}" ><font color="blue"><b>活動類型/${join }</b></font></c:if>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${RcactList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty RcactList}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(RcactList)}, 'rcact');</script>" class="center" >
	                <script>generateCheckbox("${row.oid}", "rcact");</script></display:column>
 	              <display:column title="年度"		property="school_year"	sortable="true" class="center" />
 	              <display:column title="教師姓名"	property="idno"		sortable="true" class="center" />
	              <display:column title="活動名稱"	property="actname"		sortable="true" class="center" />
	              <display:column title="主辦單位"	property="sponoff"		sortable="true" class="center" />	              
	              <display:column title="開始日期" 	property="bdate"		sortable="true" class="center" />
	              <display:column title="結束日期" 	property="edate"		sortable="true" class="center" />	              	              
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
    iCount = getCookie("rcactCount");    
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
    Oid = getCookie("rcact");
    //alert(Oid);
    return true;
  }
  
  function mySave() 
  {        
    if (document.getElementById("actname").value == '') 
    {
      alert("活動名稱,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("sponoff").value == '') 
    {
      alert("主辦單位,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("kindid").value == '') 
    {
      alert("活動種類,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("typeid").value == '') 
    {
      alert("活動型態,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("placeid").value == '') 
    {
      alert("活動地點,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("joinid").value == '') 
    {
      alert("參與情形,此欄位不得為空白");
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
    else if (document.getElementById("hour").value == '') 
    {
      alert("時數,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("certyn").value == '') 
    {
      alert("研習證明,此欄位不得為空白");
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