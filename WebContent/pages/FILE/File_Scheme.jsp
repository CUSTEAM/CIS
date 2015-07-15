<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Secretary/File_Scheme" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 Start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>會議紀錄/法規文件  設定作業&nbsp;</b></td>
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
	            1. 本程式提供秘書室，執行申請單位會議/法規名稱以及權限賦予之設定。 <br>
	            2. 初始畫面，系統帶出所有已經設定過的資料，顯示欄位有「會議/法規名稱」、「類別」、「所屬單位」。 <br>
	            3. 資料維護方面，提供「新增」、「修改」、「刪除」、「指定權限」、「返回」..等過動作。  <br>
	                                操作方式如下:<br>
	            &nbsp;&nbsp;1) 新增：點擊「新增」按鈕，畫面下方顯示資料輸入區，依據欄位名稱填入相關資料，輸入完畢點擊「儲存」則即完成新增作業。<br>
	            &nbsp;&nbsp;2) 修改：勾選要修改的資料後點擊「修改」按鈕，畫面下方顯示出該筆資料的修改區，將欄位資料修改完畢後點擊「儲存」則完成作業。 <br>
	            &nbsp;&nbsp;3) 刪除：勾選要刪除的資料後，直接點擊「刪除」按鈕執行刪除作業。<br>
	            &nbsp;&nbsp;4) 指定權限：勾選要指定的資料後，點擊「指定權限」按鈕，畫面帶出權限設定的畫面，分為依個人、依單位兩種設定方式。<br>
	            &nbsp;&nbsp;&nbsp;&nbsp;4.1) 依個人：在「姓名」欄位輸入人員姓名，系統會帶出名單提供點選，選定人名後從「權限」欄位選定賦予的權限，點擊點擊「儲存」則即完成。<br>
	            &nbsp;&nbsp;&nbsp;&nbsp;4.2) 依單位：選定要指定的單位後點擊「下一步」，系統會帶出該單位人員名單，賦予權限後點擊點擊「儲存」則即完成。<br>
	            &nbsp;&nbsp;5) 返回：此按鈕功能為回到「會議記錄/法規條例 文件管理」主頁面。<br>
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
	              <display:column title="名稱"	property="Name"	  sortable="true" class="center" />
	              <display:column title="類別"	property="FN_Type"  sortable="true" class="center" />
	              <display:column title="所屬單位"	property="CEname"  sortable="true" class="center" />
	              	              	              
 	            </display:table>
 	          </td>
 	        </tr>	      
	      </table>
	    </td>
	  </tr>
  <!-- 動作按鍵列 Start -->
	  <tr>
        <td class="hairLineTd" align="center">
          <input type="submit" class="gCancle" name="method" value="<bean:message key='Create'/>"/>
          <input type="submit" class="gCancle" name="method" value="<bean:message key='Modify'/>" onClick="return myTest01()"/>
          <input type="submit" class="gCancle" name="method" value="<bean:message key='Delete'/>" onClick="return myTest01()"/>
          <input type="submit" class="gCancle" name="method" value="<bean:message key='PowerAppoint'/>" onClick="return myTest01()"/>
          <input type="submit" class="gCancle" name="method" value="<bean:message key='CommitteeAppoint'/>" onClick="return myTest01()"/>
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
          <c:if test="${myOpen == 'open'}" >
          <tr>
            <td>            
              <table width="99%" class="hairLineTable">
                <tr>
                  <input type="hidden" name="FN_Oid" value="${FN_Oid}" />
                  <td width="15%" align="center" class="hairLineTdF">會議/法規名稱</td>
                  <td class="hairLineTd" colspan="5" >
                    <input type="text" name="FN_name" id="FN_name" size="70" value="${FN_name }"/>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">歸屬類別</td>
                  <td class="hairLineTd">
                    <select name=FN_type onChange="showOpenType()">
					  <option value=""></option>
					  <c:forEach items="${FN_type}" var="c">
					    <option <c:if test="${FN_type_SQL==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
				  </td>
				  <td width="15%" align="center" class="hairLineTdF">開放狀態</td>
                  <td class="hairLineTd" id="Rule" <c:if test="${OpenLine_SQL==''}">style="display:none"</c:if> style="display:inline">
                    <select name=OpenLine>
					  <option value=""></option>
					  <c:forEach items="${OpenLine}" var="c">					    
					    <option <c:if test="${OpenLine_SQL==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
				  </td>
				  <td class="hairLineTd" id="Meeting"  <c:if test="${Share_SQL==''}">style="display:none"</c:if> style="display:inline">
                    <select name=Share>
					  <option value=""></option>
					  <c:forEach items="${Share}" var="c">					    
					    <option <c:if test="${Share_SQL==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
				  </td>
				  <td width="15%" align="center" class="hairLineTdF" nowrap>所屬單位</td>
			      <td class="hairLineTd">
			        <select name="FN_Unit">
				      <option value="">請選擇單位</option>
				      <c:forEach items="${FN_Unit}" var="c">
				        <option <c:if test="${FN_Unit_SQL==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
				      </c:forEach>
			        </select>
			      </td>                               
                </tr>
              </table>
            </td>
          </tr>
          </c:if>
<!-- 資料輸入列 End -->
<!-- 指定權限_單位搜尋 Start -->
        <c:if test="${myOpen == 'Power'}" >
          <tr>
            <td>
              <table width="99%" class="hairLineTable">
                <tr>
                  <input type="hidden" name="FN_Oid" value="${FN_Oid}" />
                  <td class="hairLineTdF" nowrap>依個人單一授權</td>
                  <td  class="hairLineTd" align="left" id="tchName" >姓名
			        <input type="hidden" name="fsidno" id="fsidno" size="12" value=""/>
			        <input type="text" name="fscname" id="fscname" size="10" value=""				           									
					       onkeyup="if(this.value.length>0)GgetAny(this.value, 'fscname', 'fsidno', 'dempl', 'name')" />
					權限				    
		            <select name="FU_type1">
			          <option value="null">請選擇</option>
			          <c:forEach items="${FU_type}" var="c">
			            <option <c:if test="${e.FU_T==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
			          </c:forEach>
	                </select>		           	
			      </td>     
                </tr>
                <tr>
                  <td class="hairLineTdF" nowrap>依單位批次授權</td>
			      <td class="hairLineTd">
			        <select name="sunit">
				      <option value="">所有單位</option>
				      <c:forEach items="${allUnit}" var="c">
				        <option value="${c.idno}">${c.name}</option>
				      </c:forEach>
			        </select>
			        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        <input type="submit" name="method" value="<bean:message key='GoOn'/>" class="gSubmit"/>
                  </td>
                </tr>
              </table>
            </td>
          </tr>          
<!-- 指定權限_單位搜尋 End -->
<!-- 指定權限_人員明細 Start -->
          <c:if test="${!empty UserList}">
	      <tr>
		    <td>		
		      <table width="99%" class="hairLineTable">
                <tr>
		          <td class="hairLineTdF" align="center" width="20%">單位</td>
		          <td class="hairLineTdF">姓名</td>
		          <td class="hairLineTdF">權限類別</td>		
                </tr>
<script>
function setAllColumn(){	
	setAll('FU_type', document.getElementById('allId').value);
}
</script>
                <tr>		
		          <td class="hairLineTd" colspan="2" align="center">
		            <input type="button" class="gGreen" value="向下填滿" id="setAllId" 
		                   onClick="setAllColumn();" 
		                   onMouseOver="showHelpMessage('填滿權限類別<br><font size=-2>(先選左邊欄位)</font>', 'inline', this.id)" 
		                   onMouseOut="showHelpMessage('', 'none', this.id)"/>
		            <input type="hidden" name="idno"/>		
		          </td>
		          <td class="hairLineTd" nowrap>		
		            <select name="FU_type" id="allId" class="courseButton">
			          <option value="null"></option>
			          <c:forEach items="${FU_type}" var="c">
			            <option <c:if test="${e.FU_T==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
			          </c:forEach>
		            </select>		
		          </td>
		        </tr>	
<script>
function setAll(name, value){
	//alert(value);
	var a=document.getElementsByName(name);
	for(i=0; i<a.length; i++){
		a[i].value=value;
	}
}
</script>
                <c:forEach items="${UserList}" var="e">
	              <tr>		
		            <td class="hairLineTdF" align="center" width="20%">${e.CE_N }</td>
		            <td class="hairLineTdF">${e.E_SN }&nbsp;&nbsp;&nbsp;&nbsp;${e.E_CN}
		              <input type="hidden" name="FU_emplidno" value="${e.E_ID}" />
		              <input type="hidden" name="FU_fnoid" value="${e.FU_NO}" />
		            </td>		
		            <td class="hairLineTdF">
		              <select name="FU_type">
			            <option value="null"></option>
			            <c:forEach items="${FU_type}" var="c">
			              <option <c:if test="${e.FU_T==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
			            </c:forEach>
	                  </select>
		            </td>		
	              </tr>
                </c:forEach>
		      </table>
		    </td>
	      </tr>
	      </c:if>	      
<!-- 指定權限_人員明細 End -->
<!-- 指定委員任期明細 Start -->          
        </c:if>
        <c:if test="${myOpen == 'CommitteeAppoint'}" >
        <c:if test="${!empty CMList}">
	      <tr>
		    <td>		
		      <table width="99%" class="hairLineTable">
                <tr>
		          <td class="hairLineTdF" align="center" width="20%">單位</td>
		          <td class="hairLineTdF">姓名</td>
		          <td class="hairLineTdF">任期</td>		
                </tr>
<script>
function setAllDate(){	
	setAll('startDate', document.getElementById('startDate').value);
	setAll('endDate', document.getElementById('endDate').value);
}
</script>
                <tr>		
		          <td class="hairLineTd" colspan="2" align="center">
		            <input type="button" class="gGreen" value="向下填滿" id="setAllId" 
		                   onClick="setAllDate();" 
		                   onMouseOver="showHelpMessage('填滿日期<br><font size=-2>(先選右邊日期欄位)</font>', 'inline', this.id)" 
		                   onMouseOut="showHelpMessage('', 'none', this.id)"/>
		            <input type="hidden" name="idno"/>		
		          </td>
		          <td class="hairLineTd" nowrap>
		            <input id="startDate" name="startDate" type="text" size="10"
		                   onclick="ds_sh(this), this.value='';" 
		                   autocomplete="off" 
		                   style="ime-mode:disabled" 
		                   autocomplete="off"/>~		
		            <input id="endDate" name="endDate" id="endDate" type="text" size="10" 
		                   onclick="ds_sh(this), this.value='';" 
		                   autocomplete="off" 
		                   style="ime-mode:disabled" 
		                   autocomplete="off"/>		
		            <table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
			          <tr>
				        <td id="ds_calclass"></td>
				      </tr>
				    </table>
				  </td>
		        </tr>	
<script>
function setAll(name, value){
	//alert(value);
	var a=document.getElementsByName(name);
	for(i=0; i<a.length; i++){
		a[i].value=value;
	}
}
</script>
                <c:forEach items="${CMList}" var="e">
	              <tr>		
		            <td class="hairLineTdF" align="center" width="20%">${e.CE_name }</td>
		            <td class="hairLineTdF">${e.E_SN }&nbsp;&nbsp;&nbsp;&nbsp;${e.E_CN}
		              <input type="hidden" name="FU_emplidno" value="${e.E_ID}" />
		              <input type="hidden" name="FU_fnoid" value="${e.FU_NO}" />
		              <input type="hidden" name="FU_Oid" value="${e.FU_Oid}" />
		            </td>
		            <td class="hairLineTdF">
		              <input type="text" name="startDate" id="startDate" size="10" value="${e.BD }"
	 	                     onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">~
	 	              <input type="text" name="endDate"   id="endDate"   size="10" value="${e.ED }"
	 	                     onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
	 	            </td>		            				    
	              </tr>
                </c:forEach>
		      </table>
		    </td>
	      </tr>
	      </c:if>	
        </c:if>
<!-- 指定委員任期明細 End -->
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
      </td>
    </tr>    
  </html:form>
</table>

<script>
  
  function showOpenType() 
  {
    
    if(document.getElementById("FN_type").value == '550') 
    {
      document.getElementById("Rule").style.display = 'inline';
      document.getElementById("Meeting").style.display = 'none';     
    } 
    else if(document.getElementById("FN_type").value == '549')
    {
      document.getElementById("Rule").style.display = 'none';
      document.getElementById("Meeting").style.display = 'inline';      			
    }
    else
    {
      document.getElementById("Rule").style.display = 'none';
      document.getElementById("Meeting").style.display = 'none';      
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
  
  
  function myTest01()   //執行修改 刪除前的檢查作業
  {    
    //alert("修改");
    var iCount;
    iCount = getCookie("FNCount");
    //ID = getCookie("FN");    
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
      else if (document.getElementById("FN_Unit").value == '') 
      {
        alert("<所屬單位>欄位不得為空白!!");
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