<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Secretary/File_Download" method="post" enctype="multipart/form-data">
<!-- 標題列 Start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>會議紀錄/法規文件 資料下載作業&nbsp;</b></td>
          </tr>
        </table>
      </td>
    </tr>
<!-- 標題列 End -->
<!-- 操作說明 Start -->	
    <tr>
      <td>
        <table width="100%" cellpadding="0" cellspacing="0" onClick="showSpecs('exposition')">
          <tr style="cursor:pointer;">
            <td width="10" align="left" nowrap>
              <hr class="myHr"/>
            </td>
            <td width="24" align="center" nowrap>
              <img src="images/folder_explore.gif" id="searchEx">
            </td>
            <td nowrap><font color=red><b>操作說明&nbsp;</b></font></td>
            <td width="100%" align="left">
              <hr class="myHr"/>
            </td>
          </tr>
        </table>
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr height="35" valign="middle" bgcolor="#E1D2A6" style="display:none" id="exposition">
            <td>
              <div class="modulecontainer filled nomessages">
	            <div class="first">
	              <span class="first"></span>
	              <span class="last"></span>
	            </div>
	            <div>
	              <div> 
	                1. 本程式提供執會議記錄/行法規條例文件查詢下載檢閱。 <br>
	                2. 初始畫面為資料輸入畫面，提供人員依據畫面欄位執行查詢。 <br>
	                                           操作方式如下:<br>
	                &nbsp;&nbsp;1) 依據畫面欄位，填入要查詢的條件。<br>
	                &nbsp;&nbsp;2) 點擊「查詢」，系統帶出符合查詢條件的資料條列表。 <br>
	                &nbsp;&nbsp;3) 勾選要檢閱的文件，點擊「下載」按鈕，即可下載檢閱文件。<br>	                                           	
	              </div>
	            </div>
	            <div class="last">
	              <span class="first"></span>
	              <span class="last"></span>
	            </div>
	          </div>
            </td>               
          </tr>
        </table>
      </td>
    </tr> 
<!-- 操作說明 End -->
<!-- 問候列 Start -->		
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr height="35" align="center" valign="middle" bgcolor="#CCCCFF">
            <!-- <font color="blue">${TeacherUnit}&nbsp;&nbsp;${TeacherName}同仁&nbsp;您好</font> -->
          </tr>
        </table>        
      </td>
    </tr>
<!-- 問候列 End -->
<!-- 資料明系列 Start -->
    <tr>
      <td>
        <table width="100%" cellpadding="0" cellspacing="0">       
          <tr>
          </tr>           
        </table>        
      </td>
    </tr>    
	<tr>
	  <td>	    
	    <table width="100%" class="hairLineTable" cellpadding="1" cellspacing="1">          
          <tr >
            <td width="10%" align="center" class="hairLineTdF">輸入關鍵字</td>
            <td class="hairLineTd" colspan="5">
              <input type="text" name="Data_name" id="Data_name" value="" size="70"/>              
            </td>
          </tr>
          <tr>  
		    <td width="15%" align="center" class="hairLineTdF" nowrap>所屬單位</td>
			<td class="hairLineTd">
			  <select name="FN_Unit">
				<option value="">請選擇單位</option>
				<c:forEach items="${FN_Unit}" var="c">
				  <option value="${c.Oid}">${c.name}</option>
				</c:forEach>
			  </select>
			</td>
			<td width="15%" align="center" class="hairLineTdF">歸屬類別</td>
            <td class="hairLineTd">
              <select name=FN_type onChange="showOpenType()">				
				<c:forEach items="${FN_type}" var="c">
				  <option value="${c.Oid}">${c.name}</option>
				</c:forEach>
			  </select>
			</td>
			<td width="15%" align="center" class="hairLineTdF">版本類別</td>
            <td class="hairLineTd">
              <select name=FD_ShareType onChange="showOpenType()">				
				<c:forEach items="${FD_ShareType}" var="c">
				  <option value="${c.Oid}">${c.name}</option>
				</c:forEach>
			  </select>
			</td>
		  </tr>
		  <c:if test="${CMsize != '0'}" >		  
		  <tr >
            <td width="10%" align="center" class="hairLineTdF">委員會議</td>
            <td class="hairLineTd" colspan="5">
              <select name=CM_FN onChange="showOpenType()">
                <option value="">請選擇</option>			
				<c:forEach items="${Committee}" var="c">
				  <option value="${c.Oid}">${c.name}</option>
				</c:forEach>
			  </select>
            </td>
          </tr>
          </c:if>			         
	    </table>
	  </td>
	</tr>
  <!-- 動作按鍵列 Start -->
    <tr>
      <td class="hairLineTd" align="center"> 
        <input type="submit" class="gSubmit" name="method" value="<bean:message key='Query'/>" class="CourseButton" onclick="myQuery()"/>               
        <input type="button" class="gCancle" value="返回" id="Back" onclick="location='/CIS/Secretary/File_Manager.do';"/>        
      </td>
    </tr>
  <!-- 動作按鍵列 End -->
    <input type="hidden" name="FN_Type" value="${FN_Type}" />       
        <c:if test="${FN_List != null}" >
	    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	    <tr>
	      <td>	        
	        <table width="100%" cellpadding="0" cellspacing="0">
		      <tr>
		        <td align="center">  
	              <display:table name="${FN_List}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		        <c:if test="${empty FN_List}">
	     		      <%@ include file="../include/NoBanner.jsp" %>
	  		        </c:if>
	                <display:column title="<script>generateTriggerAll(${fn:length(FN_List)}, 'FNList');</script>" class="center" >
	                  <script>generateCheckbox("${row.Oid}", "FNList");</script></display:column> 	              
	                <c:if test="${FN_Type == '549'}" >
	                  <display:column title="年度"	property="schoolYear"	  sortable="true" class="center" />
	                  <display:column title="學期"	property="schoolTerm"  sortable="true" class="center" />
	                </c:if>
	                <display:column title="會議/法規名稱"	    property="FN_name"	  sortable="true" class="center" />		                
	                <display:column title="會議日期"	property="MeetingDate"  sortable="true" class="center" />	                
	                <c:if test="${FN_Type == '550'}" >
	                  <display:column title="文號"	property="DocNo"  sortable="true" class="center" />
	                  <display:column title="會議類別"	property="MeetingType"  sortable="true" class="center" />	                  
	                </c:if> 
 	              </display:table>
 	            </td>
 	          </tr>	      
	        </table>
	      </td>
	    </tr>
  <!-- 動作按鍵列 Start -->
	    <tr>
          <td class="hairLineTd" align="center">
            <input type="submit" class="gCancle" name="method" value="<bean:message key='Download'/>" onClick="return myTest()" class="CourseButton"/>
          </td>
        </tr>
        </c:if>
  <!-- 動作按鍵列 End -->    		
      </td>
    </tr>    
  </html:form>
</table>
<script>
  function showSpecs(ID) 
  {
    // var id = ID;
    var id = ID;
    if(document.getElementById(id).style.display == 'none') 
    {
      document.getElementById(id).style.display = 'inline';      
    } 
    else 
    {
      document.getElementById(id).style.display = 'none';				
    }
  }
  
  function myQuery()   //執行查詢前的檢查作業
  {        
    
      if (document.getElementById("FN_Unit").value != '' && document.getElementById("FN_type").value == '') 
      {
        alert("請選擇歸屬類別後，再執行查詢!!~");
        return false;
      }
      else    
      return true;
       
  }
  
  function myTest0()   //執行修改 刪除前的檢查作業
  {
    var iCount;
    iCount = getCookie("FNCount");
    ID = getCookie("FN");    
    if (iCount == 0) 
    {
      alert("請勾選至少一項資料!!");
      return false;
    } 
    else if(iCount > 1) 
    {
      alert("請僅勾選一項資料!!");
      return false;
    }
    return true;    
  }
  
  
  function myTest()   //執行修改 刪除前的檢查作業
  {
    var iCount;
    iCount = getCookie("FNListCount");
    ID = getCookie("FNList");    
    if (iCount == 0) 
    {
      alert("請勾選至少一項資料!!");
      return false;
    } 
    else if(iCount > 1) 
    {
      alert("請僅勾選一項資料!!");
      return false;
    }
    return true;    
  }
  
  function mySave()   //執行存檔前的檢查作業
  {        
    if (document.getElementById("myOpen").value == 'open')
    {
      if (document.getElementById("FN_name").value == '') 
      {
        alert("<會議/法規名稱>欄位不得為空白!!");
        return false;
      } 
      else if (document.getElementById("FN_type").value == '') 
      {
        alert("<歸屬類別>欄位不得為空白!!");
        return false;
      } 
      else    
      return true;
    }     
  }
</script>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/leo_Calendar.jsp" %>