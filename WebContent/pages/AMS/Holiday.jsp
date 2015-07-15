<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/AMS/Holiday" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>特殊日期資料維護&nbsp;</b></td>
          </tr>
        </table>
      </td>
    </tr>	
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
	                1. 本程式提供人事單位，執行特定假日以及特殊上班日的設定。例如：國定假日，或是校慶當日上班。<br>
	                2. 系統初始畫面提供日期區間提供操作者作為查詢條件。<br>
	                3. 系統功能操作分為"新增"、"刪除"以及"查詢"三個動作。<br>
	                                                   操作說明如下:<br>
	                &nbsp;&nbsp;1) 新增：點擊"新增"按鈕，畫面下方顯示資料輸入區，依據欄位名稱填入相關資料，輸入完畢點擊"儲存"則即完成新增作業。<br>
	                &nbsp;&nbsp;2) 查詢：依造系統畫面日期區間欄位，輸入查詢的日期條件點擊"查詢"即可。如需要查詢全部資料，日期條件空白直接點擊"查詢" 。<br>
	                &nbsp;&nbsp;3) 刪除：勾選要刪除的資料後，直接點擊"刪除"按鈕執行刪除作業。 <br> 
	                                                  資料顯示欄位說明如下:<br>
	                &nbsp;&nbsp;1) 日         期：表示資料設定的日期。<br>
	                &nbsp;&nbsp;2) 原因名稱：表示該日期休假/上班的原因。<br>
	                &nbsp;&nbsp;3) 上班/休假：顯示該日期是上班或是休假。(H-表示休假，W-表示上班) <br> 
	                &nbsp;&nbsp;4) 上班時間：顯示該日期的應上班時間。 <br>                
                    &nbsp;&nbsp;5) 下班時間：顯示該日期的應下班時間。 <br>
                    &nbsp;&nbsp;6) 適用職員：顯示該日期適用的職員類別。(A-全體，1-專任教師，2-兼任教師，3-職員工，4-軍護教師)。
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
<!-- 標題列 end -->		
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr height="35" align="center" valign="middle" bgcolor="#CCCCFF">
            <!-- <font color="blue">${TeacherUnit}&nbsp;&nbsp;${TeacherName}同仁&nbsp;您好</font> -->
          </tr>
        </table>        
      </td>
    </tr>
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td>            
              <table width="60%" class="hairLineTable">
                <tr>
                  <td width="20%" align="center" class="hairLineTdF">日期區間</td>
                  <td class="hairLineTd">
                    <input type="text" name="sdate" id="sdate" size="10" value="" 
                           onclick="ds_sh(this), this.value='';" onfocus="ds_sh(this), this.value='';"
                           autocomplete="off" style="ime-mode:disabled" autocomplete="off" readonly="ture"/>
                  </td>
                  <td width="5%" align="center" class="hairLineTdF">~</td>
                  <td class="hairLineTd">
                    <input type="text" name="edate" id="edate" size="10" value="" 
                           onclick="ds_sh(this), this.value='';" onfocus="ds_sh(this), this.value='';"
                           autocomplete="off" style="ime-mode:disabled" autocomplete="off" readonly="ture"/>
                  </td>                   
                </tr>
              </table>
            </td>
            <td>
              <input type="submit" name="method" value="<bean:message key='Query'/>" class="CourseButton"/>
              <input type="submit" name="method" value="<bean:message key='Create'/>" class="CourseButton"/>
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
    <c:if test="${myOpen == 'open'}">
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td>
              <input type=hidden name="saveType" value="${saveType }"/>                 
              <table width="99%" class="hairLineTable">
                <tr>
                  <td align="center" class="hairLineTdF">日期</td>
                  <td align="center" class="hairLineTdF">原因</td>
                  <td align="center" class="hairLineTdF">上班/休假</td>
                  <td align="center" class="hairLineTdF">上班時間</td>
                  <td align="center" class="hairLineTdF">下班時間</td>
                  <td align="center" class="hairLineTdF">適用員工</td>
                </tr>
                <tr>
                  <td align="center" class="hairLineTd">
                    <input type="text" name="date_s" id="date" size="10" value="${date }" 
                           onclick="ds_sh(this), this.value='';" onfocus="ds_sh(this), this.value='';"
                           autocomplete="off" style="ime-mode:disabled" autocomplete="off" readonly="ture"/>~
                    <input type="text" name="date_e" id="date" size="10" value="${date }" 
                           onclick="ds_sh(this), this.value='';" onfocus="ds_sh(this), this.value='';"
                           autocomplete="off" style="ime-mode:disabled" autocomplete="off" readonly="ture"/>
                  </td>                  
                  <td align="center" class="hairLineTd">
                    <input type="text" name="name" id="name" size="15" value="${name }"/>
                  </td>                  
                  <td align="center" class="hairLineTd">
                    <select name=type>
					  <option <c:if test="${type=='H'}">selected</c:if> value="H">休假</option>
					  <option <c:if test="${type=='W'}">selected</c:if> value="W">上班</option>
					</select>
                    
                  </td>
                  <td align="center" class="hairLineTd">
                    <input type="text" name="startTime" id="startTime" size="10" value="${startTime }"/>
                  </td>                  
                  <td align="center" class="hairLineTd">
                    <input type="text" name="endTime" id="endTime" size="10" value="${endTime }"/>
                  </td>                  
                  <td align="center" class="hairLineTd">
                    <select name=emplType>
					  <option <c:if test="${emplType=='A'}">selected</c:if> value="A">全體教職員</option>
					  <option <c:if test="${emplType==1}">selected</c:if> value="1">專任教師</option>
					  <option <c:if test="${emplType==2}">selected</c:if> value="2">兼任教師</option>
					  <option <c:if test="${emplType==3}">selected</c:if> value="3">職員工</option>
					  <option <c:if test="${emplType==4}">selected</c:if> value="4">軍護教師</option>
					</select>
                  </td>                   
                </tr>
              </table>
            </td>
          </tr>          
          <tr>
            <td class="hairLineTd" align="center">
              <input type="submit" name="method" value="<bean:message key='Save'/>" onClick="return mySave()" class="CourseButton"/>              
              <hr class="myHr"/>
            </td>
          </tr>
        </table>         		
      </td>
    </tr>
    </c:if>    
    <c:if test="${HD_List != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${HD_List}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty HD_List}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(HD_List)}, 'holiday');</script>" class="center" >
	                <script>generateCheckbox("${row.Oid}", "holiday");</script></display:column>
 	              <display:column title="日期"	    property="Date"	      sortable="true" class="center" />
	              <display:column title="原因"	property="Name"	      sortable="true" class="center" />
	              <display:column title="上班/休假"	property="Type"       sortable="true" class="center" />
	              <display:column title="上班時間"	property="StartTime"  sortable="true" class="center" />
	              <display:column title="下班時間"	property="EndTime"	  sortable="true" class="center" />
	              <display:column title="適用員工"	property="EmplType"	  sortable="true" class="center" />             	              
 	            </display:table>
 	          </td>
 	        </tr>	      
	      </table>
	    </td>
	  </tr>
	  <tr>
        <td class="hairLineTd" align="center">          
          <!-- <input type="submit" name="method" value="<bean:message key='Modify'/>" onClick="return myTest()" class="CourseButton"/>  -->
          <input type="submit" name="method" value="<bean:message key='Delete'/>" onClick="return myTest()" class="CourseButton"/>
        </td>
      </tr>
    </c:if>    
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
  
  
  function myTest() 
  {
    var iCount;
    iCount = getCookie("holidayCount");    
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
    ID = getCookie("holiday");
    //alert(Oid);
    return true;    
  }
  
  function mySave() 
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