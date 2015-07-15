<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Secretary/File_Upload" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 Start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>會議紀錄/法規文件 資料上傳作業&nbsp;</b></td>
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
	            1. 本程式提供行政單位執行會議記錄或法規條例文件上傳作業。 <br>
	            2. 初始畫面，系統帶出所有已經設定過的資料，顯示欄位有「會議/法規名稱」、「類別」。 <br>
	            3. 資料維護方面，提供「上傳」、「查詢」、「修改」、「刪除」..等過動作。  <br>
	                                操作方式如下:<br>
	            &nbsp;&nbsp;1) 上傳：勾選要上傳的名稱點擊「下一步」按鈕，畫面下方顯示資料輸入區，依據欄位名稱填入相關資料，輸入完畢點擊「上傳」則即完成新增作業。<br>
	            &nbsp;&nbsp;2) 查詢：勾選要修改的名稱點擊「下一步」按鈕，依據欄位輸入查詢的條件點擊「查詢」，畫面下方顯示出符合查詢條件的資料條列。 <br>
	            &nbsp;&nbsp;3) 修改：執行完查詢作業後，勾選要修改的資料後，直接點擊「修改」按鈕，畫面下方顯示資料輸入區，依據欄位名稱填入要修改的資料，點擊「上傳」則即完成新增作業。<br>
	            &nbsp;&nbsp;4) 刪除：執行完查詢作業後，勾選要刪除的資料後，直接點擊「刪除」按鈕執行刪除作業。法規類別資料，經過審核核可後，不可刪除。<br>
	            &nbsp;&nbsp;5) 返回：此按鈕功能為回到「會議記錄/法規條例 文件管理」主頁面。<br>
	                                特別說明：<br>
	            &nbsp;&nbsp;1) 法規類別文件資料，經過審核機制核可後，只能修改版本類別。<br>
	            &nbsp;&nbsp;2) 法規類別文件資料，經過審核機制核可後，不得刪除。<br>
	            &nbsp;&nbsp;3) 上傳資料時，關鍵字欄位為必填欄位，不可空白。<br>	            
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
	            <display:column title="名稱"	property="FN_Name"	  sortable="true" class="center" />
	            <display:column title="類別"	property="FN_Type"  sortable="true" class="center" />
	            <display:column title="單位"	property="Unit"  sortable="true" class="center" />	              	              
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
    <input type="hidden" name="showType" value="${showType}" />  
    <tr>
      <td>       
        <table width="100%" cellpadding="0" cellspacing="0">
          <input type="hidden" name="FL_Oid" value="${FL_Oid}" />
          <input type="hidden" name="FN_Oid" value="${FN_Oid}" />
          <input type="hidden" name="FN_Type" value="${FN_Type}" />
          <input type="hidden" name="FN_name" value="${FN_name}" />
          <c:if test="${myOpen == 'open'}" >
            
          <tr>
            <td>
              <table width="99%" class="hairLineTable">
                <tr>                  
                  <td width="10%" align="center" class="hairLineTdF">學年</td>
                  <td class="hairLineTd">
                    <input type="text" name="schoolYear" id="schoolYear" value="${schoolYear }" size="5"/>
                  </td>
                  <td width="10%" align="center" class="hairLineTdF">學期</td>
                  <td class="hairLineTd">
                    <input type="text" name="schoolTerm" id="schoolTerm" value="${schoolTerm }" size="5"/>
                  </td>                        
                </tr>
              </table>
            </td>
          </tr>                  
          
          <tr>
            <td>
              <table width="99%" class="hairLineTable">
                <!-- 
                <tr>                  
                  <td width="10%" align="center" class="hairLineTdF">檔案名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="Data_name" id="Data_name" value="${Data_name }" size="60"/>
                  </td>
                </tr>
                 -->
                <c:if test="${showType !='Modify'}" >  
                <tr>
                  <td width="10%" align="center" class="hairLineTdF">選擇檔案</td>
                  <td class="hairLineTd" colspan="7">
                    <input type="file" name="myImage" size="50" id="myImage" value=""/>
                  </td>
                </tr>
                </c:if>
                <tr>      
                  <td width="10%" align="center" class="hairLineTdF">版本類別</td>
                  <td class="hairLineTd">
                    <select name="ShareType" id="ShareType">			          
					  <c:forEach items="${ShareType}" var="c">
					    <option <c:if test="${ShareType_SQL==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
				  </td>
				  <td width="10%" align="center" class="hairLineTdF">開會日期</td>
                  <td class="hairLineTd">                    
                    <input type="text" name="MeetingDate" id="MeetingDate" value="${MeetingDate }"                            
                             onclick="ds_sh(this), this.value='';"                           
                             onfocus="ds_sh(this), this.value='';"                           
                           autocomplete="off" 
                           style="ime-mode:disabled" 
                           autocomplete="off" />
                  </td>               
                </tr>  
                <c:if test="${FN_Type == '550'}" >
                <tr>				  
				  <td width="10%" align="center" class="hairLineTdF">發文字號</td>
                  <td class="hairLineTd">
                    <input type="text" name="DocNo" id="DocNo" 
                      <c:if test="${showType =='Modify' && approve == '97'}">readonly="ture"</c:if> value="${DocNo }"/>
                  </td>
                  <td width="10%" align="center" class="hairLineTdF">發文日期</td>
                  <td class="hairLineTd">                    
                    <input type="text" name="DocDate" id="DocDate" value="${DocDate }"                          
                           onclick="ds_sh(this), this.value='';" 
                           onfocus="ds_sh(this), this.value='';"                            
                           autocomplete="off" 
                           style="ime-mode:disabled" 
                           autocomplete="off" />
                  </td>
                </tr>
                <tr>
                  <td width="10%" align="center" class="hairLineTdF">會議名稱</td>
                  <td class="hairLineTd" colspan="3">
                    <input type="text" name="MeetingType" id="MeetingType" size="70" 
                      <c:if test="${showType =='Modify' && approve == '97'}">readonly="ture"</c:if> value="${MeetingType }"/>
                  </td>                 
                </tr>
                </c:if>
                <tr>
                  <td width="10%" align="center" class="hairLineTdF">關鍵字</td>
                  <td class="hairLineTd" colspan="7">                    
                    <textarea name="KeyWords" rows="5" cols="70" 
                      <c:if test="${FN_Type == '550' && showType =='Modify' && approve == '97'}">readonly="ture"</c:if>>${KeyWords }</textarea>
                  </td>
                </tr>             
              </table>
              <table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
		        <tr>
				  <td id="ds_calclass"></td>
			    </tr>
			  </table>
              <input type=hidden name="approve" value="${approve }"/>
              <c:if test="${approve == '98'}" >
              <table width="99%" class="hairLineTable">
                <tr>
                  <td>
                  	不核可原因
                  </td>                  
				  <td align="center">
					<textarea name="approveTemp" rows="5" cols="81" readonly="ture">${approveTemp }</textarea>					
                  </td>
				</tr>
              </table>
              </c:if>
            </td>
          </tr>
<!-- 資料輸入列 End -->
<!-- 存檔按鍵列 Start -->          
          <tr>
            <td class="hairLineTd" align="center">
              <input type="submit" class="gSubmit" name="method" value="<bean:message key='UploadFile'/>"  onClick="return mySave()" class="CourseButton"/>
              <input type="submit" class="gCancle" name="method" value="<bean:message key='Query'/>" class="CourseButton"/>              
              <hr class="myHr"/>
            </td>
          </tr>
          </c:if>
<!-- 存檔按鍵列 End -->        
        </table>        
        <c:if test="${FN_List != null}" >
	    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	    <tr>
	      <td  bgcolor="#CCCCFF" align="center" valign="middle">
	        <c:if test="${FileName == ''}" ><font color="red"><b>不核可項目</b></font></c:if>
	        <c:if test="${FileName != ''}" ><font color="blue"><b>${FileName }</b></font></c:if>	        
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
	                <display:column title="名稱"	property="FN_Name"  sortable="true" class="center" />
	                <display:column title="開會日期"	property="MeetingDate"  sortable="true" class="center" />	                
	                <c:if test="${FN_Type == '550'}" >
	                  <display:column title="發文字號"	property="DocNo"  sortable="true" class="center" />
	                  <display:column title="會議名稱"	property="MeetingType"  sortable="true" class="center" />
	                  <display:column title="審核狀態"	property="name"  sortable="true" class="center" />
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
            <input type="submit" class="gCancle" name="method" value="<bean:message key='Modify'/>" onClick="return myTest()" class="CourseButton"/>
            <input type="submit" class="gCancle" name="method" value="<bean:message key='Delete'/>" onClick="return myTest()" class="CourseButton"/>          
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
    
      if (document.getElementById("KeyWords").value == '') 
      {
        alert("關鍵字欄位不可為空白，請輸入關鍵字!!~");
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