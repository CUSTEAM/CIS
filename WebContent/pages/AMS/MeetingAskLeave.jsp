<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/AMS/MeetingAskLeave" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 Start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>重要集會請假單&nbsp;</b></td>
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
	            1. 本程式提供教職員，執行重要集會未出席之請假作業。 <br>
	            2. 初始畫面，系統帶出所有已經輸入過的集會請假資料 。<br>
	            3. 資料維護方面，提供新增、修改、刪除以及列印四個動作。  <br>
	                                操作方式如下:<br>
	            &nbsp;&nbsp;1) 新增：點擊"新增"按鈕，畫面下方顯示資料輸入區，依據欄位名稱填入相關資料，輸入完畢點擊"儲存"則即完成新增作業。<br>
	            &nbsp;&nbsp;2) 修改：勾選要修改的資料後點擊"修改"按鈕，畫面下方顯示出該筆資料的修改區，將欄位資料修改完畢後點擊"儲存"則完成作業。 <br>
	            &nbsp;&nbsp;3) 刪除：勾選要刪除的資料後，直接點擊"刪除"按鈕執行刪除作業。<br>
	            &nbsp;&nbsp;4) 列印：點擊該筆資料最後一個欄位中的印表機圖樣，系統匯出Excel檔案格式提供列印作業。<br>
	                                 注意事項如下:<br>	            
	            &nbsp;&nbsp;1) 請假資料的審查狀態為"審查中"階可執行修改/刪除/列印作業。<br>
	            &nbsp;&nbsp;&nbsp;&nbsp;  反之該筆資料審查狀態為"已核准"，則該資料將不能再執行修改/刪除/列印。<br>
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
    <c:if test="${MAL_List != null}" >
	   <%@include file="/pages/AMS/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${MAL_List}" export="false" id="row" pagesize="5" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty MAL_List}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(MAL_List)}, 'MeetingAskLeave');</script>" class="center" >
	                <script>generateCheckbox("${row.Oid}", "MeetingAskLeave");</script></display:column>
 	              <display:column title="集會名稱"	property="MN"	   sortable="true" class="center" />
	              <display:column title="假別名稱"	property="AN"	   sortable="true" class="center" />
	              <display:column title="假單編號"	property="askCode" sortable="true" class="center" />
	              <c:if test="${row.status == 0}">
	                <display:column title="審查狀態"	value="審核中" sortable="true" class="center" />
	              </c:if>
	              <c:if test="${row.status == 1}">
	                <display:column title="審查狀態"	value="已核准" sortable="true" class="center" />
	              </c:if>
	              <c:if test="${row.status == 0}">
	        	    <display:column title="列印" 		sortable="true" >
	        	     <script>generateMeetingAskLeave("${row.oid}", "");</script>
	        	   </display:column>
	        	  </c:if>
				  <c:if test="${row.status != 0}">
	        	    <display:column title="列印" sortable="false" > </display:column>
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
              <input type=hidden name="Oid" value="${MAL_Oid }"/>            
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF" nowrap>會議名稱	</td>
				  <td class="hairLineTd">
				    <select name="meetingOid">
					  <option value="">請選擇</option>
					    <c:forEach items="${meetingOid}" var="c">
					      <option <c:if test="${sel_meetingOid==c.Oid}">selected</c:if> value="${c.Oid}">${c.Name}</option>
					    </c:forEach>
				    </select>
				  </td>
                  <td width="15%" align="center" class="hairLineTdF">假別名稱</td>
                  <td class="hairLineTd">
		            <select name="askleaveId">
		              <c:forEach items="${askleaveId}" var="c">
		                <option <c:if test="${sel_askleaveId==c.ID}">selected</c:if> value="${c.ID}">${c.Name}</option>
		              </c:forEach>
		            </select>
		          </td>
		        </tr>
		      </table>
              <table width="99%" class="hairLineTable">
                <tr>		          		
                  <td width="15%" align="center" class="hairLineTdF">未出席原因</td>
                  <td class="hairLineTd">
                    <input type="text" name="Temp" id="Temp" size="80" value="${Temp }"/>
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
    iCount = getCookie("MeetingAskLeaveCount");
    ID = getCookie("MeetingAskLeave");    
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