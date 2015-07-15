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
                    <input type=hidden name="oid_s" value="${oid_s }"/>
                    <select name="type" onChange="yearType()">
                      <option value="">請選擇</option>
                      <option <c:if test="${kind == 1}">selected</c:if> value="1">助學貸款</option>
                      <option <c:if test="${kind == 2}">selected</c:if> value="2">學雜費</option>
                      <option <c:if test="${kind == 3}">selected</c:if> value="3">工讀費</option>
                      <option <c:if test="${kind == 4}">selected</c:if> value="4">退費</option>
                    </select>             
                  </td>  
                  <td width="13%" align="center" class="hairLineTdF">學號 / 姓名</td>                  
                  <td class="hairLineTd">
				    <input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						   name="studentNo" id="studentNo" size="10" value="${studentNo}"
						   onkeyup="if(this.value.length>=2)GgetAny(this.value, 'studentNo', 'studentName', 'gstmd', 'no')" 
						   onMouseOver="showHelpMessage('學號輸入這邊, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 以便自動完成姓名', 'inline', this.id)" 
				   		   onMouseOut="showHelpMessage('', 'none', this.id)" onFocus="chInput(this.id)"
						   onClick="clearQuery()" /> / 
				    <input type="text" name="studentName" id="studentName" size="10" value="${studentName}"
				           onMouseOver="showHelpMessage('姓名輸入這邊, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 以便自動完成學號', 'inline', this.id)" 
				   		   onMouseOut="showHelpMessage('', 'none', this.id)" onFocus="chInput(this.id)"						   
						   onkeyup="if(this.value.length>1)GgetAny(this.value, 'studentName', 'studentNo', 'gstmd', 'name')" 
						   onClick="clearQuery()"/>
				  </td>                  
                </tr>
              </table>
              <table width="99%" class="hairLineTable">   
                <c:if test="${kind != '3'}" >
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
                </c:if>
                <c:if test="${kind == '3'}" >
                <tr id="myYear" style="display:none">
                  <td width="13%" align="center" class="hairLineTdF">年度</td>
                  <td class="hairLineTd">
                    <input type="text" name="year" id="year" size="1" value="${school_Year }"/>
                  </td>
                  <td width="13%" align="center" class="hairLineTdF">月份</td>
                  <td class="hairLineTd">
                    <input type="text" name="month" id="month" size="1" value="${school_Term }"/>
                  </td>
                </tr>
                </c:if>
              </table>                      
              <table width="99%" class="hairLineTable">   
                <tr>
                  <td width="13%" align="center" class="hairLineTdF">郵局局號</td>
                  <td class="hairLineTd">
                    <input type="text" name="officeNo" id="officeNo" size="20" value="${officeNo }"/>
                  </td>
                  <td width="13%" align="center" class="hairLineTdF">郵局帳號</td>
                  <td class="hairLineTd">
                    <input type="text" name="accountNo" id="accountNo" size="20" value="${acctNo }"/>
                  </td>                   
                  <td width="13%" align="center" class="hairLineTdF">匯款金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="money" id="money" size="10" value="${money }"/>
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
        <input type="submit" name="method" value="<bean:message key='Save'/>" onClick="return mySave(1)" class="CourseButton"/>
        <input type="submit" name="method" value="<bean:message key='Back'/>" class="CourseButton"/>
        <hr class="myHr"/>
      </td>
    </tr>
  <!-- 查詢列 End -->    	
  
  
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
    
    if (document.getElementById("officeNo").size != 7) 
    {
      alert("局號長度不正確");
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