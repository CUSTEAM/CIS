<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/AMS/AskLeave" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 Start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>假別資料維護&nbsp;</b></td>
          </tr>
        </table>
      </td>
    </tr>
<!-- 標題列 End -->
<!-- 操作說明 Start -->	
    <tr>
      <td>
        <table width="100%" cellpadding="0" cellspacing="0" onClick="showSpecs('Specs1_7')">
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
          <tr height="35" valign="middle" bgcolor="#E1D2A6" style="display:none" id="Specs1_7">
            <td>
              <div class="modulecontainer filled nomessages">
	        <div class="first">
	          <span class="first"></span>
	          <span class="last"></span>
	        </div>
	        <div>
	          <div> 
	            1. 本程式提供人事單位，執行請假類別的設定。 <br>
	            2. 初始畫面，系統帶出所有已經設定過的資料，顯示欄位有"假別代碼"、"假別名稱"、"扣分標準"。 <br>
	            3. 資料維護方面，提供新增、修改、刪除三個動作。  <br>
	                                操作方式如下:<br>
	            &nbsp;&nbsp;1) 新增：點擊"新增"按鈕，畫面下方顯示資料輸入區，依據欄位名稱填入相關資料，輸入完畢點擊"儲存"則即完成新增作業。<br>
	            &nbsp;&nbsp;2) 修改：勾選要修改的資料後點擊"修改"按鈕，畫面下方顯示出該筆資料的修改區，將欄位資料修改完畢後點擊"儲存"則完成作業。 <br>
	            &nbsp;&nbsp;3) 刪除：勾選要刪除的資料後，直接點擊"刪除"按鈕執行刪除作業。<br>
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
    <c:if test="${AL_List != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${AL_List}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty AL_List}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(AL_List)}, 'AskLeave');</script>" class="center" >
	                <script>generateCheckbox("${row.ID}", "AskLeave");</script></display:column>
 	              <display:column title="假別代碼"	property="ID"	  sortable="true" class="center" />
	              <display:column title="假別名稱"	property="Name"	  sortable="true" class="center" />
	              <display:column title="扣分標準"	property="Score"  sortable="true" class="center" />	              	              
 	            </display:table>
 	          </td>
 	        </tr>	      
	      </table>
	    </td>
	  </tr>
  <!-- 動作按鍵列 Start -->
	  <tr>
        <td class="hairLineTd" align="center">
          <input type="submit" name="method" value="<bean:message key='Create'/>" class="CourseButton"/>
          <input type="submit" name="method" value="<bean:message key='Modify'/>" onClick="return myTest()" class="CourseButton"/>
          <input type="submit" name="method" value="<bean:message key='Delete'/>" onClick="return myTest()" class="CourseButton"/>
        </td>
      </tr>
  <!-- 動作按鍵列 End -->
    </c:if>
<!-- 資料明系列 End -->
<!-- 資料輸入列 Start -->    
    <c:if test="${myOpen == 'open'}" >
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td>            
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="10%" align="center" class="hairLineTdF">假別代碼</td>
                  <td class="hairLineTd">
                    <input type="text" name="AL_id" id="AL_id" size="2" value="${AL_id }"/>
                  </td>
                  <td width="10%" align="center" class="hairLineTdF">假別名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="AL_name" id="AL_name" size="10" value="${AL_name }"/>
                  </td>
                  <td width="10%" align="center" class="hairLineTdF">扣分標準</td>
                  <td class="hairLineTd">
                    <input type="text" name="score" id="score" size="10" value="${score }"/>
                  </td>                   
                </tr>
              </table>
            </td>
          </tr>
  <!-- 存檔按鍵列 Start -->          
          <tr>
            <td class="hairLineTd" align="center">
              <input type="submit" name="method" value="<bean:message key='Save'/>" onClick="return mySave()" class="CourseButton"/>
              
              <hr class="myHr"/>
            </td>
          </tr>
  <!-- 存檔按鍵列 End -->
        </table>		
      </td>
    </tr>
    </c:if>
<!-- 資料輸入列 End -->
  </html:form>
</table>

<script>
  function showSpecs(ID) 
  {
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
  
  
  function myTest()   //執行修改 刪除前的檢查作業
  {
    var iCount;
    iCount = getCookie("AskLeaveCount");
    ID = getCookie("AskLeave");    
    if (iCount == 0) 
    {
      alert("請勾選至少一項資料!!");
      return false;
    } 
    else if(iCount > 1) 
    {
      alert("請僅勾選一項資料!!");
      return false;
    }else if(ID == "|07|" || ID == "|09|" || ID == "|12|")
    {
      alert("該假別已與其他參數關聯，如需異動請洽電算中心。");
      return false;
    }
    return true;    
  }
  
  function mySave()   //執行存檔前的檢查作業
  {        
    
    if (document.getElementById("AL_id").value == '') 
    {
      alert("假別代碼不得為空白!!");
      return false;
    } 
    else if (document.getElementById("AL_name").value == '') 
    {
      alert("假別名稱不得為空白!!");
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