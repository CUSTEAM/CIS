<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/StudAffair/TransferAccount" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
  <!-- 標題列 Start -->
	<tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>學生轉帳帳號資料維護&nbsp;</b></td>
          </tr>
        </table>
      </td>
    </tr>
  <!-- 標題列 End -->

  <!-- 說明列 start -->
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
            <td nowrap>使用說明&nbsp;</td>
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
	            1. <br>
	            2. <br>
	            3. <br>
	            4. <br>
	            5. <br>
                6. <br>
                7. <br>
                8. <br>
                9. <br>
                10.<br>
                11.<br>
                12.<br>
                13.<br>
                14.   
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
  <!-- 說明列 End -->
  <!-- 查詢列 Start -->
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td>            
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="13%" align="center" class="hairLineTdF">費用種類</td>
                  <td class="hairLineTd" colspan="5">
                    <select name="type" onChange="yearType()">
                      <option value="">請選擇</option>
                      <option value="1">助學貸款</option>
                      <option value="2">學雜費</option>
                      <option value="3">工讀費</option>
                      <option value="4">退費</option>
                    </select>             
                  </td>  
                  <td width="13%" align="center" class="hairLineTdF">學號 / 姓名</td>                  
                  <td class="hairLineTd">
				    <input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						   name="studentNo" id="studentNo" size="10" value="${StudentManagerForm.map.studentNo}"
						   onkeyup="if(this.value.length>=2)GgetAny(this.value, 'studentNo', 'studentName', 'gstmd', 'no')" 
						   onMouseOver="showHelpMessage('學號輸入這邊, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 以便自動完成姓名', 'inline', this.id)" 
				   		   onMouseOut="showHelpMessage('', 'none', this.id)" onFocus="chInput(this.id)"
						   onClick="clearQuery()" /> / 
				    <input type="text" name="studentName" id="studentName" size="10" value="${StudentManagerForm.map.studentName}"
				           onMouseOver="showHelpMessage('姓名輸入這邊, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 以便自動完成學號', 'inline', this.id)" 
				   		   onMouseOut="showHelpMessage('', 'none', this.id)" onFocus="chInput(this.id)"						   
						   onkeyup="if(this.value.length>1)GgetAny(this.value, 'studentName', 'studentNo', 'gstmd', 'name')" 
						   onClick="clearQuery()"/>
				  </td>                  
                </tr>
              </table>
              <table width="99%" class="hairLineTable">   
                <tr id="schoolYear">
                  <td width="13%" align="center" class="hairLineTdF">學年度</td>
                  <td class="hairLineTd">
                    <input type="text" name="schoolYear" id="schoolYear" size="1" value="${school_Year }"/>
                  </td>
                  <td width="13%" align="center" class="hairLineTdF">學期</td>
                  <td class="hairLineTd">
                    <input type="text" name="schoolTerm" id="schoolTerm" size="1" value="${school_Term }"/>
                  </td>
                </tr>
                <tr id="myYear" style="display:none">
                  <td width="13%" align="center" class="hairLineTdF">年度</td>
                  <td class="hairLineTd">
                    <input type="text" name="year" id="year" size="1" value=""/>
                  </td>
                  <td width="13%" align="center" class="hairLineTdF">月份</td>
                  <td class="hairLineTd">
                    <input type="text" name="month" id="month" size="1" value=""/>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td class="hairLineTd" align="center">        
        <input type="submit" name="method" value="<bean:message key='Create'/>" onClick="return mySave(1)" class="CourseButton"/>
        <input type="submit" name="method" value="<bean:message key='Query'/>"  onClick="return mySave()" class="CourseButton"/>
        <hr class="myHr"/>
      </td>
    </tr>
  <!-- 查詢列 End -->    	
  <!-- 資料列 Start -->
  <c:if test="${DipostList != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <c:if test="${kind == '1'}" >費用種類::<font color="blue"><b>助學貸款 </b></font></c:if>
	      <c:if test="${kind == '2'}" >費用種類::<font color="blue"><b>學雜費 </b></font></c:if>
	      <c:if test="${kind == '3'}" >費用種類::<font color="blue"><b>工讀費 </b></font></c:if>
	      <c:if test="${kind == '4'}" >費用種類::<font color="blue"><b>退費 </b></font></c:if>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${DipostList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty DipostList}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(DipostList)}, 'dipost');</script>" class="center" >
	                <script>generateCheckbox("${row.oid}", "dipost");</script></display:column>
 	              <c:if test="${kind == '3'}" >
 	                <display:column title="年度"	property="schoolYear"	sortable="true" class="center" />
	                <display:column title="月份"	property="schoolTerm"	sortable="true" class="center" />
 	              </c:if>
 	              <c:if test="${kind != '3'}" >
 	                <display:column title="學年"	property="schoolYear"	sortable="true" class="center" />
	                <display:column title="學期"	property="schoolTerm"	sortable="true" class="center" />
	              </c:if>
	              <display:column title="班級名稱"	property="ClassName"	sortable="true" class="center" />	              
	              <display:column title="學號" 	    property="studentNo"	sortable="true" class="center" />
	              <display:column title="姓名" 	    property="student_name"	sortable="true" class="center" />
	              <display:column title="局號" 	    property="officeNo"		sortable="true" class="center" />
	              <display:column title="帳號" 	    property="acctNo"		sortable="true" class="center" />
	              <display:column title="金額" 	    property="pMoney"		sortable="true" class="center" />	              	              
 	            </display:table>
 	          </td>
 	        </tr>	      
	      </table>
	    </td>
	  </tr>
	  <tr>
        <td class="hairLineTd" align="center">
          <input type="submit" name="method" value="<bean:message key='Modify'/>" onClick="return myTest()" class="CourseButton"/>
          <input type="submit" name="method" value="<bean:message key='Delete'/>" onClick="return myTest()" class="CourseButton"/>
          <input name="Submit04" type="submit" value="匯出Excel" class="CourseButton"
                 onclick="MM_goToURL('parent','/CIS/pages/studaffair/TransferAccount/TA_Sel.jsp');return document.MM_returnValue"/>
        </td>
      </tr>
    </c:if>
  <!-- 資料列 End -->
  
  <!-- 新增列 Start -->
  <!-- 新增列 End -->
  
  </html:form>
</table>


<script>
	function clearQuery(){
		document.getElementById("studentNo").value="";
		document.getElementById("studentName").value="";
	}							
</script>
<script>

  function MM_goToURL() { //v3.0 匯出Excel
    var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
    for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
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
  function yearType() 
  {
    //alert(document.getElementById("Type").value);
    if(document.getElementById("type").value == 3) 
    {
      document.getElementById("myYear").style.display = 'inline';
      document.getElementById("schoolYear").style.display = 'none';   
    } 
    
    else
    {
      document.getElementById("myYear").style.display = 'none';
      document.getElementById("schoolYear").style.display = 'inline';
    }
  }
  
  function mySave(a) 
  {        
    
    if (document.getElementById("type").value == '') 
    {
      alert("請選擇一項費用種類");
      return false;
    } 
    else   
          
      return true;
   
    
  }
  
</script>



<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetStmdOrGstmd.jsp" %>
<%@ include file="/pages/include/MyCalendar.jsp" %>