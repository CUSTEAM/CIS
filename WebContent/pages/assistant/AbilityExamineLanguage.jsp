<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<!-- 檢查 Function -->
<script>
  history.go(1);
  function stdCheck() 
  {	
    var iCount;
    iCount = getCookie("resultDataCount");
    if (iCount == 0) 
    {
      alert("請勾選至少一位學生進行技能檢定檢視!!");
      return false;
    } 
    else if(iCount > 1) 
    {
      alert("請僅勾選一位學生進行技能檢定檢視!!");
      return false;
    }
    return true;
  }
  
  function deleteCheck() 
  {
    var StdAbility = getCookie("stdAbility");
    if(StdAbility=="||")
    {
      alert("您無權限刪除此項目");
      return false;
    }
    
    var iCount = getCookie("stdAbilityCount");
    if (iCount == 0) 
    {
      alert("請勾選至少一筆技能檢定進行刪除!!");
      return false;
    } 
    return confirm("確定刪除學生技能檢定?");
  }
  
  function modifyCheck() 
  {
    var StdAbility = getCookie("stdAbility");
    if(StdAbility=="||")
    {
      alert("您無權限修改此項目");
      return false;
    }
    
    var iCount = getCookie("stdAbilityCount");
    if (iCount == 0) 
    {
      alert("請勾選至少一筆技能檢定進行修改!!");
      return false;
    } 
    else if(iCount > 1) 
    {
      alert("請僅勾選一位學生進行技能檢定修改!!");
      return false;
    }
    return true;
  }
</script>
<!-- 檢查 Function -->

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <!-- 標題列 start-->	
  <tr>
    <td class="fullColorTable" width="100%">
      <table width="100%" cellpadding="0" cellspacing="0" border="0">
        <tr height="30">
          <td width="28" align="right">
            <img src="images/16-manager-st.gif">
          </td>
          <td align="left">&nbsp;技能檢定資料維護－
            <font color="red">${year}</font>學年度&nbsp;
          </td>
        </tr>
      </table>
    </td>
  </tr>	
  <!-- 標題列 end -->
  
  <html:form action="/DeptAssistant/AbilityExamineLanguage" method="post" onsubmit="init('執行中, 請稍後')">
  
  <!-- 主查詢界面 start-->
  
  <!-- 快速查詢 start -->
  <tr>
    <td><input type="hidden" name="exSearch" value="${AbilityExamineLanguageForm.map.exSearch}"/><br>
      <table width="100%" cellpadding="0" cellspacing="0">
        <tr onClick="showSearch()">
          <td width="10" align="left" nowrap>
            <hr noshade size="1" color="cfe69f"/>
          </td>
          <td width="24" align="center" nowrap>
            <img src="images/folder_find.gif" id="searchNorm">
          </td>
          <td nowrap>快速搜索&nbsp;	</td>
          <td width="100%" align="left">
            <hr noshade size="1" color="cfe69f"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  
  <tr>
    <td>
      <table width="100%">
        <tr>
          <td>	
            <table width="100%">
              <tr>
                <td>
                  <table width="100%">														
                    <tr <c:if test="${AbilityExamineLanguageForm.map.exSearch!=''}">style="display:none"</c:if> id="onlyEdit">
                      <td>
                        <table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          <tr bgcolor="#f0fcd7">
                            <td>學號</td>
                            <td>
                              <input type="text" style="ime-mode:disabled" 
                              	name="studentNoC" id="studentNo" size="12" value="${AbilityExamineLanguageForm.map.studentNoC}"
                              	onkeyup="if(this.value.length>=6)GgetAny(this.value, 'studentNo', 'studentName', 'gstmd', 'no')" onClick="clearQuery()" />
                              
                              <input type="text" name="nameC" id="studentName" size="10" value="${AbilityExamineLanguageForm.map.nameC}"
                              	onkeyup="if(this.value.length>1)GgetAny(this.value, 'studentName', 'studentNo', 'gstmd', 'name')" onClick="clearQuery()" />
                              <img src="images/16-exc-mark.gif" id="smart" />
                            </td>
                          </tr>
                        </table>								
                      </td>
                    </tr>							
                  </table>
                </td>
              </tr>
            </table>				
          </td>
        </tr>			
      </table>		
    </td>
  </tr>
  <!-- 快速查詢 end-->
  
  <!-- 進階查詢 start-->
  <tr>
    <td>
      <table width="100%" cellpadding="0" cellspacing="0" onClick="showSearch()">
        <tr>
          <td width="10" align="left" nowrap>
            <hr noshade size="1" color="cfe69f"/>
          </td>
          <td width="24" align="center" nowrap>
            <img src="images/folder_explore.gif" id="searchEx">
          </td>
          <td nowrap>進階搜索&nbsp;</td>
          <td width="100%" align="left">
            <hr noshade size="1" color="cfe69f"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  
  <tr>
    <td>				
      <table width="100%">
        <tr <c:if test="${AbilityExamineLanguageForm.map.exSearch==''}">style="display:none"</c:if> id="searchBar">
          <td>
            <table>					
              <tr>
                <td>							
                  <table>
                    <tr>									
                      <td>
                        <table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          <tr bgcolor="#f0fcd7">
                            <td>班別</td><!-- 語言中心下拉式選單班及權限設定必須在ClassInCharge的各班級|87|的模組權限 -->
                            <td colspan="3">
                              <c:set var="campusSel" value=""/>
                              <c:set var="schoolSel" value=""/>
                              <c:set var="deptSel"   value=""/>
                              <c:set var="classSel"  value=""/>
                              <%@ include file="/pages/include/ClassSelectC.jsp" %>
                            </td>
                          </tr>
                          <tr bgcolor="#f0fcd7">
                            <!-- 
                            <td>項目別</td>
                            <td colspan="3">
                              <html:select property="abilityNo" size="1">
                                <html:options property="abilityNos" labelProperty="abilityNames" />	    						
                              </html:select>
                            </td>
                             -->
                            <td>是否取得證照</td>
                            <td class="hairLineTd">
                              <select name=count>
                                <option value=""></option>
                                <option value="Y">有取得</option>
                                <option value="N">無取得</option>
                              </select>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>	
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>							
  <!-- 進階查詢 end -->	
  <!-- Button(批次新增&查詢) Start-->
  <tr>
    <td bgcolor="#cfe69f" align="center" width="100%">
      <table>
        <tr>
          <td>
            <input type="button" name="method" value="批次新增" onClick="showAddBar();" class="CourseButton" />&nbsp;
            <input type="submit" name="method" id="continue" value="<bean:message key='Search' bundle="TCH"/>" class="CourseButton">
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <!-- Button(批次新增&查詢) End -->
  <!-- 主查詢界面 end-->
  
  <!-- 列表 start -->	
  <c:if test="${not empty result}">
  <tr>
    <td>
      <table width="100%">
        <tr>
          <td>
            <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
            <display:table name="${result}" pagesize="10" id="row" sort="list" class="list">
              <%@ include file="../include/NoBanner.jsp" %>
              <display:column title="" class="center" >
                <script>generateCheckbox("${row.map.no}", "resultData")</script>
              </display:column>
              <display:column title="姓名" property="name" sortable="true" class="center" />
              <display:column title="學號" property="no" sortable="true" class="center" />							
              <display:column title="姓別" property="sex" sortable="true" class="center" />
              <display:column title="班級名稱" property="class" sortable="true" class="center" />
              <display:column title="登錄件數" property="counts" sortable="true" class="center" />				
            </display:table>
          </td>
        </tr>
      </table>		
    </td>
  </tr>
  <!-- Button(新增&檢視) Start-->
  <tr>
    <td bgcolor="#cfe69f" align="center" width="100%">
      <table>
        <tr>
          <td>
            <input type="button" name="method" value="<bean:message key='Create'/>" onClick="showAddBarOne();" class="CourseButton" />&nbsp;           
            <INPUT type="submit" name="method" value="<bean:message key='View' bundle="TCH" />" onclick="return stdCheck();" class="CourseButton" />
          </td>
        </tr>
      </table>
    </td>
  </tr> 
  </c:if>		
  <!-- Button(新增&檢視) End-->
  <!-- 列表 end-->
  
  <!-- 學生登錄資料檢視 start-->      
  <c:if test="${not empty stdAbilityResult}">
  <tr>
    <td>
      <table width="100%">
        <tr>
          <td>
                               學號：${stdInfo.map.no}　姓名：${stdInfo.map.name}
          </td>
        </tr>
        <tr>
          <td>
            <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
            <display:table name="${stdAbilityResult}" pagesize="10" id="row" sort="list" class="list">
              <%@ include file="../include/NoBanner.jsp" %>
              <display:column title="" class="center">
              	<script>generateCheckbox("${row.oid}", "stdAbility")</script>
              </display:column>
              <display:column title="項目別" property="abilityName" sortable="true" class="center" />
              <display:column title="名稱說明" property="description" class="center" />
              <display:column title="級次說明" property="levelDesc" class="center" />
              <display:column title="單位說明" property="deptDesc" class="center" />
              <display:column title="登錄時間" property="lastModified" sortable="true" class="center" />					
            </display:table>
          </td>
        </tr>
      </table>		
    </td>
  </tr>  
  <!-- Button(修改&刪除) start--> 
  <tr>  
    <td bgcolor="#cfe69f" align="center" width="100%">
      <table>
        <tr>
          <td>

            <INPUT type="submit" name="method" value="<bean:message key='Modify' />" onclick="return modifyCheck();" class="CourseButton" />
            <INPUT type="submit" name="method" value="<bean:message key='Delete' />" onclick="return deleteCheck();" class="CourseButton" />&nbsp;
          </td>
        </tr>
      </table>
    </td>
  </tr> 
  </c:if>	
  <!-- Button(修改&刪除) end--> 
  <!-- 學生登錄資料檢視 end-->     
  
  <!-- 單一學生新增 start-->
  <tr id="addBarOne" style="display:none;">
    <td>				
      <table width="100%">
        <tr>
          <td>
            <table>
              <tr>
                <td>							
                  <table>
                    <tr>									
                      <td>
                        <table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F" width="100%">
                          <tr bgcolor="#f0fcd7" align="center">
                            <td>學號</td>
                            <td>項目別</td>
                            <td>名稱說明</td>
                            <td>級次說明</td>
                            <td>單位說明</td>
                          </tr>
                          <c:forEach begin="1" end="1">
                          <tr bgcolor="#f0fcd7">
                            <td>
                              <input type="text" name="stdNoA" size="10"  readonly="ture" />
                            </td>
                            <td>
                              <html:select property="abilityNoA" size="1">                                
                                <html:options property="abilityNos" labelProperty="abilityNames" />                                	    						
                              </html:select>
                            </td>
                            <td>
                              <input type="text" name="descA" size="30"/>
                            </td>
                            <td>
                              <input type="text" name="levelA" size="30"/>
                            </td>
                            <td>
                              <input type="text" name="deptA" size="30"/>
                            </td>
                          </tr>
                          </c:forEach>
                        </table>
                      </td>
                    </tr>										
                  </table>
                </td>	
              </tr>							
            </table>
          </td>
        </tr>				
      </table>
    </td>
  </tr>
  <tr id="addBarOneButton" style="display:none;">
    <td bgcolor="#cfe69f" align="center" width="100%">
      <table>
        <tr>
          <td>
            <INPUT type="submit" name="method" value="<bean:message key='makeSure' />" class="CourseButton" />
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <!-- 單一學生新增 end-->
  
  <!-- 修改區 start-->
  <c:if test="${not empty modifyStdAbility}">
  <tr>
    <td>
      <table width="100%">
        <tr>
          <td>
                               學號：${stdInfo.map.no}　姓名：${stdInfo.map.name}
          </td>
        </tr>
        <tr>
          <td>
            <table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F" width="100%">
              <tr bgcolor="#f0fcd7" align="center">
                <td>項目別</td>
                <td>名稱說明</td>
                <td>級次說明</td>
                <td>單位說明</td>
              </tr>
              <tr bgcolor="#f0fcd7">
                <td>
                  <html:select property="abilityNoM" size="1" value="${modifyStdAbility.abilityNo}">
                    <html:options property="abilityNos" labelProperty="abilityNames" />	    						
                  </html:select>
                </td>
                <td>
                  <input type="text" name="descM" size="30" value="${modifyStdAbility.description}"/>
                </td>
                <td>
                  <input type="text" name="levelM" size="30" value="${modifyStdAbility.levelDesc}"/>
                </td>
                <td>
                  <input type="text" name="deptM" size="30" value="${modifyStdAbility.deptDesc}"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>		
    </td>
  </tr>
  <tr>
    <td bgcolor="#cfe69f" align="center" width="100%">
      <table>
        <tr>
          <td>
            <input type="submit" name="method" value="<bean:message key='Update' bundle="TCH" />" class="CourseButton" />&nbsp;						
          </td>
        </tr>
      </table>
    </td>
  </tr>
  </c:if>
  <!-- 修改區 end-->
  
  <!-- 批次學生新增 start-->
  <tr id="addBar" style="display:none;">
    <td>				
      <table width="100%">
        <tr>
          <td>
            <table>					
              <tr>
                <td>							
                  <table>
                    <tr>									
                      <td>
                        <table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F" width="100%">
                          <tr bgcolor="#f0fcd7" align="center">
                            <td>學號</td>
                            <td>項目別</td>
                            <td>名稱說明</td>
                            <td>級次說明</td>
                            <td>單位說明</td>
                          </tr>
                          <c:forEach begin="1" end="5">
                            <tr bgcolor="#f0fcd7">
                              <td>
                                <input type="text" name="stdNoA" size="10"/>
                              </td>
                              <td>
                                <html:select property="abilityNoA" size="1">                                
                                  <html:options property="abilityNos" labelProperty="abilityNames" />                                	    						
                                </html:select>
                              </td>
                              <td>
                                <input type="text" name="descA" size="30"/>
                              </td>
                              <td>
                                <input type="text" name="levelA" size="30"/>
                              </td>
                              <td>
                                <input type="text" name="deptA" size="30"/>
                              </td>
                            </tr>
                          </c:forEach>
                        </table>
                      </td>
                    </tr>										
                  </table>
                </td>	
              </tr>							
            </table>
          </td>
        </tr>				
      </table>
    </td>
  </tr>
  <tr id="addBarButton" style="display:none;">
    <td bgcolor="#cfe69f" align="center" width="100%">
      <table>
        <tr>
          <td>
            <INPUT type="submit" name="method" value="<bean:message key='makeSure' />" class="CourseButton" />
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <!-- 批次學生新增 end-->
  </html:form>
</table>



<!-- 啟用 Function Start-->
<script>  
  function showAddBar() //展開批次新增區塊
  {
    document.getElementById("addBar").style.display = 'inline';
    document.getElementById("addBarButton").style.display = 'inline';
  }
  
  function showAddBarOne() //單一新增區塊
  {
    var iCount;
    iCount = getCookie("resultDataCount");
    if (iCount == 0) 
    {
      alert("請勾選至少一位學生進行技能檢定新增!!");
      return false;
    } 
    else if(iCount > 1) 
    {
      alert("請僅勾選一位學生進行技能檢定新增!!");
      return false;
    }
    var stdNoA;
    stdNoA = getCookie("resultData");
    document.getElementById("stdNoA").value = stdNoA.substring(1,9);
    document.getElementById("addBarOne").style.display = 'inline';
    document.getElementById("addBarOneButton").style.display = 'inline';
  }
</script>

<script>
  function takeTraNo(type)
  {
    document.getElementById('traStu').style.display="none";
    document.getElementById('newStu').style.display="inline";
    if(type=='tra')
    {
      document.getElementById('traStu').style.display="inline";
      document.getElementById('newStu').style.display="none";
    }
  }
</script>

<script>
  function showInfo(id)
  {
    if(document.getElementById(id).style.display=='none')
    {
      document.getElementById(id).style.display='inline';
    }
    else
    {
      document.getElementById(id).style.display='none';
    }
  }
</script>

<script>
  function clearQuery() 
  {
    document.getElementById("studentNo").value="";
    document.getElementById("studentName").value="";
  }							
</script>

<script>
  function showSearch() 
  {
    if(document.getElementById('searchBar').style.display == 'none') 
    {
      document.getElementById('searchBar').style.display = 'inline';
      document.getElementById('onlyEdit').style.display = 'none';			
      document.getElementById('exSearch').value = '1'; //進階搜尋開關			
    } 
    else 
    {
      document.getElementById('searchBar').style.display = 'none';
      document.getElementById('onlyEdit').style.display = 'inline';
      document.getElementById('exSearch').value = '';
      //document.getElementById('studentNo').value='';
      //document.getElementById('studentName').value='';					
    }
  }
</script>
<!-- 啟用 Function End-->
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/MyCalendar.jsp" %>
<%@ include file="/pages/include/AjaxGetStmdOrGstmd.jsp" %>
