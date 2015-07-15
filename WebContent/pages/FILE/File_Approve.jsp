<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Secretary/File_Approve" method="post" enctype="multipart/form-data">
<!-- 標題列 Start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>會議紀錄/法規文件 審核作業&nbsp;</b></td>
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
	            1. 本程式提供秘書室人員執行法規條例文件上傳後之審核作業。 <br>
	            2. 初始畫面，系統帶出所有等待處理或是不核可的資料，顯示欄位有「會議/法規名稱」、「最後發佈時間」、「審核狀態」。 <br>	            
	                                操作方式如下:<br>
	            &nbsp;&nbsp;1) 勾選要上傳的名稱點擊「下一步」按鈕，畫面下方顯示資料。<br>
	            &nbsp;&nbsp;2) 點擊「下載」檢閱該單位上傳的文件。 <br>
	            &nbsp;&nbsp;3) 選擇「審查狀態」後直接點擊「儲存」按鈕，則完成審查作業。<br>
	                                  特別說明：<br>
	            &nbsp;&nbsp;1) 「審查狀態」為不核可時，不核可原因為必填欄位，不可空白。<br>
	            &nbsp;&nbsp;2) 審核文件時，畫面下方會顯示該筆資料的審核歷程，提供審核人員參考。<br>	 
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
    <c:if test="${Fn != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${Fn}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty Fn}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(Fn)}, 'FN');</script>" class="center" >
	                <script>generateCheckbox("${row.Oid}", "FN");</script></display:column> 	              
	              <display:column title="會議/法規名稱"	property="Name"	  sortable="true" class="center" />	              
	              <display:column title="最後發佈時間"	property="lastModified"  sortable="true" class="center" />
	              <display:column title="審核狀態"	property="approve"  sortable="true" class="center" />
 	            </display:table>
 	          </td>
 	        </tr>	      
	      </table>
	    </td>
	  </tr>
  <!-- 動作按鍵列 Start -->
	  <tr>
        <td class="hairLineTd" align="center">
          <input type="submit" name="method" value="<bean:message key='GoOn'/>" onClick="return myTest0()" class="gSubmit"/>
          <!-- 
          <input type="submit" class="gCancle" name="method" value="<bean:message key='Create'/>" class="CourseButton"/>
          <input type="submit" class="gCancle" name="method" value="<bean:message key='Modify'/>" onClick="return myTest()" class="CourseButton"/>
          <input type="submit" class="gCancle" name="method" value="<bean:message key='Delete'/>" onClick="return myTest()" class="CourseButton"/>
          <input type="submit" class="gCancle" name="method" value="<bean:message key='PowerAppoint'/>" onClick="return myTest()" class="CourseButton"/>
           -->
          <input type="button" class="gCancle" value="返回" id="Back" onclick="location='/CIS/Secretary/File_Manager.do';"/>
        </td>
      </tr>
  <!-- 動作按鍵列 End -->
    </c:if>
<!-- 資料明系列 End -->
<!-- 資料輸入列 Start -->    
    <input type="hidden" name="myOpen" value="${myOpen}" />
    <input type="hidden" name="myTest" value="${myTest}" />    
    <tr>      
      <td>       
        <table width="100%" cellpadding="0" cellspacing="0">
          <input type="hidden" name="FL_Oid" value="${FL_Oid}" />
          <input type="hidden" name="FN_Oid" value="${FN_Oid}" />
          <input type="hidden" name="FN_Type" value="${FN_Type}" />
          <input type="hidden" name="FN_name" value="${FN_name}" />
          <c:if test="${myOpen == 'open'}" >            
<!--   
            <c:if test="${FN_Type == '549'}" >  
          <tr>
            <td>
              <table width="99%" class="hairLineTable">
                <tr>                  
                  <td width="10%">學年</td><td class="hairLineTdF">${schoolyear }</td>
                  <td width="10%">學期</td><td class="hairLineTdF">${schoolTerm }</td>                  
              </table>
            </td>
          </tr>
            </c:if>        
-->
          <tr>
            <td>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="75">法規名稱</td><td class="hairLineTdF">${FN_name }</td>
                  <td width="75">版本類別</td><td class="hairLineTdF">${ShareType_SQL }</td>                                                 
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="75">檔案名稱</td><td class="hairLineTdF">${Data_name }</td> 
                  <td class="hairLineTdF" width="10%">
                    <input type="submit" class="gCancle" name="method" value="<bean:message key='Download'/>" onClick="return myTest()" class="CourseButton"/>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="75">發文字號</td><td class="hairLineTdF">${DocNo }</td>
                  <td width="75">會議名稱</td><td class="hairLineTdF">${MeetingType }</td>
                  <td width="75">會議日期</td><td class="hairLineTdF">${MeetingDate }</td>                  
                </tr>
              </table>
              <table width="99%" class="hairLineTable">               
                <tr>
                  <td width="75">關鍵字</td><td class="hairLineTdF">${KeyWords }</td>                   
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
					<font id="point01" <c:if test="${approve_v!='98'}">style="display:none"</c:if> color="red">
					  <b>當審查狀態為"不核可"時，按下儲存，文件則會退回上傳單位執行修改作業。</b>
					</font>
					<font id="point02" <c:if test="${approve_v!='97'}">style="display:none"</c:if> color="blue">
					  <b>當審查狀態為"核可"時，按下儲存，法規文件則會發佈公告。</b>
					</font>
                  </td>
                  <td></td>
                  
                </tr>
                <tr id="appTemp" <c:if test="${approve_v!='98'}">style="display:none"</c:if>>                  
                  <td width="10%">不核可原因</td>                  
				  <td>
					<input type="text" name="approveTemp" id="approveTemp" size="70" value="${approveTemp }"/>
										
                  </td>
                </tr>
              </table>
            </td>
          </tr>
           
      
	  </c:if>                 
<!-- 資料輸入列 End -->
<!-- 存檔按鍵列 Start -->          
       
        <c:if test="${myOpen != 'close'}" >
          <tr>
            <td class="hairLineTd" align="center">
              <input type="submit" name="method" value="<bean:message key='Save'/>" onClick="return mySave()" class="CourseButton"/>              
              <hr class="myHr"/>
            </td>
          </tr>
        </c:if>
<!-- 存檔按鍵列 End -->
        </table>
        <table width="100%" cellpadding="0" cellspacing="0">
        <c:if test="${FaList != null}" >
	    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	      <tr>
	        <td>
	          <table width="100%" cellpadding="0" cellspacing="0">
		        <tr>
		          <td align="center">  
	                <display:table name="${FaList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		          <c:if test="${empty FaList}">
	     		      <%@ include file="../include/NoBanner.jsp" %>
	  		          </c:if>	                                
	                  <display:column title="審核狀態"	property="approve"	  sortable="true" class="center" />	              
	                  <display:column title="審核原因"	property="approveTemp"  sortable="true" class="center" />
	                  <display:column title="審核時間"	property="lastModified"  sortable="true" class="center" />
	                  <display:column title="審核人員"	property="approveID"  sortable="true" class="center" />
 	                </display:table>
 	              </td>
 	            </tr> 	        
	          </table>
	        </td>
	      </tr>
        </c:if>
        </table>	
      </td>
    </tr>    
  </html:form>
</table>

<script>
  function showTemp() 
  {
    
    if(document.getElementById("approve").value == '98') 
    {
      document.getElementById("appTemp").style.display = 'inline';
      document.getElementById("point01").style.display = 'inline';
      document.getElementById("point02").style.display = 'none';      
    }
    else if(document.getElementById("approve").value == '97') 
    {
      document.getElementById("appTemp").style.display = 'none';
      document.getElementById("point01").style.display = 'none';
      document.getElementById("point02").style.display = 'inline';      
    }
    else
    {
      document.getElementById("appTemp").style.display = 'none';
      document.getElementById("point01").style.display = 'none';
      document.getElementById("point02").style.display = 'none';      
    }
  }
  
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
  
  function mySave()   //執行存檔前的檢查作業
  {        
    if (document.getElementById("approve").value == '98' && document.getElementById("approveTemp").value == '')
    {
      alert("審查狀態為不核可時，<不核可原因>欄位為必填，不能為空白!!");
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