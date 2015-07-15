<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/CodeSelection.js" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>

<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>

<!-- Calendar view 1.2 -->
<link type="text/css" href="<%=basePath%>pages/include/calendarview-1.2/stylesheets/calendarview.css" rel="stylesheet" />	
<script type="text/javascript" src="<%=basePath%>pages/include/calendarview-1.2/javascripts/prototype.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/include/calendarview-1.2/javascripts/calendarview.js"></script>
<script>
      function setupCalendars() {
        // Embedded Calendar
        //Calendar.setup(
        //  {
        //    dateField: 'embeddedDateField',
        //    parentElement: 'embeddedCalendar'
        //  }
        //)

        // Popup Calendar
        Calendar.setup(
          {
            //dateField: 'startDate',
            dateField: 'cdate',
            triggerElement: 'sCalImg',
            dateFormat: '%Y-%m-%d'
          }
        )
      }

      Event.observe(window, 'load', function() { setupCalendars() })
</script>
<!-- End Calendar view 1.2 -->


<style type="text/css">
<!--
.input {
	width:20px;
}
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
-->
</style>
			<!-- for calendar use -->
			<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none; z-index:65530;">
				<tr>
					<td id="ds_calclass"></td>
				</tr>
			</table>
			

<form action="/CIS/Teacher/Tutor/CounselingT.do" method="post" name="bpForm">
<input type="hidden" name="cscode" value=""/>
<input type="hidden" name="courseName" value=""/>


<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="CounselingRecordEdit" bundle="TCH"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
	
	<c:choose>
	<c:when test="${CounselingBatchEdit4T != null}">
	<c:set var="formback" value="${CounselingBatchEdit4T}"/>
		<tr>
			<td><table width="100%">
				<tr>
				<td width="80" align="right">學生：</td>
				<td><table>
				<c:set var="cnt" value="0"/>
				<c:forEach items="${CounselingTStuds}" var="stud">
					<c:if test="${cnt==0}">
					<tr>
					</c:if>
				        <td  height="30" colspan="4" align="left" valign="middle" class="hairLineTd">
				        	${stud.departClass2}&nbsp;&nbsp;${stud.studentNo} ${stud.studentName}
				        </td>
					<td>&nbsp;</td>
					<c:set var="cnt" value="${cnt+1}"/>
					<c:if test="${cnt==3}">
					</tr>
					<c:set var="cnt" value="0"/>
					</c:if>
				</c:forEach>
				</table></td>
		        </tr>			
			</table></td>
        </tr>
        <tr>
	      	<td><table width="100%"><tr>
			<td width="80" align="right" class="td_lable_normal">輔導日期：</td>
	        <td  width="90" class="hairlineTdF">
	            <input type="text" name="cdate" id="cdate" size="10" readonly="readonly" maxlength="10" value="${formback.cdate}">
	        </td>
			<td width="20">	  
	     			<img src="<%=basePath%>pages/images/cal.gif" name="sCalImg" id="sCalImg" width="20" height="20"
			      		align="top" style="cursor:hand" alt="點選此處選擇日期">&nbsp;
			</td>
			<td>&nbsp;</td>
			
	      	</tr>
	      	</table></td>
        </tr>
        <tr>
        	<td><table width="100%"><tr>
        	<td width="80" align="right" valign="middle">輔導類別：</td>
        	<td align="left" valign="middle" class="hairLineTd">
        		<select name="ctype" onchange="changeItem(this.value);">
        			<c:if test="${formback.ctype=='T'}">
        			<option value="T" selected="selected">職涯輔導</option>
         			<option value="U">學習輔導</option>
        			</c:if>
        			<c:if test="${formback.ctype=='U'}">
         			<option value="T">職涯輔導</option>
        			<option value="U" selected="selected">學習輔導</option>
        			</c:if>
        		</select>
        	</td>
 			<td>&nbsp;</td>
        	</tr></table></td>
        </tr>
        <c:if test="${formback.ctype=='T'}">
        	<c:set var="CounselCode" value="${StudCounselCodeT}"/>
        </c:if>
       	<c:if test="${formback.ctype=='U'}">
        	<c:set var="CounselCode" value="${StudCounselCodeL}"/>
        </c:if>
        <tr>
        	<td><table width="100%"><tr>
        	<td width="80" align="left" valign="middle">輔導項目：</td>
        	<td align="left" valign="middle" class="hairLineTd">
        	<select name="itemNo" id="itemNo">
        	<c:forEach items="${CounselCode}" var="code">
        		<c:if test="${code.itemNo==formback.itemNo}">
        		<option value="${code.itemNo}" selected="selected">${code.itemName}</option>
        		</c:if>
        		<c:if test="${code.itemNo!=formback.itemNo}">
        		<option value="${code.itemNo}">${code.itemName}</option>
        		</c:if>
        	</c:forEach>
        	</select>
        	</td>
 			<td>&nbsp;</td>
        	</tr></table></td>
        </tr>
        <tr>
        	<td><table width="100%"><tr>
        	<td width="80" align="left" valign="middle">輔導內容：</td>
        	<td align="left" valign="middle" class="hairLineTd">
        	<textarea name="content" rows="3" cols="60">${formback.content}</textarea>
        	</td>
 			<td>&nbsp;</td>
        	</tr></table></td>
        </tr>
	</c:when>
	
	<c:otherwise>
		<tr>
			<td><table width="100%">
	        <tr>
				<td width="80" align="right">學生：</td>
				<td><table>
				<c:set var="cnt" value="0"/>
				<c:forEach items="${CounselingTStuds}" var="stud">
					<c:if test="${cnt==0}">
					<tr>
					</c:if>
				        <td  height="30" colspan="4" align="left" valign="middle" class="hairLineTd">
				        	${stud.departClass2}&nbsp;&nbsp;${stud.studentNo} ${stud.studentName}
				        </td>
					<td>&nbsp;</td>
					<c:set var="cnt" value="${cnt+1}"/>
					<c:if test="${cnt==5}">
					</tr>
					<c:set var="cnt" value="0"/>
					</c:if>
				</c:forEach>
				</table></td>
		   </tr>			
	        </table></td>
        </tr>
        <tr>
	      	<td><table width="100%"><tr>
			<td width="80" align="right" class="td_lable_normal">輔導日期：</td>
	        <td  width="90" class="hairlineTdF">
	            <input type="text" name="cdate" id="cdate" size="10" readonly="readonly" maxlength="10" value="">
	        </td>
			<td width="20">	  
	     			<img src="<%=basePath%>pages/images/cal.gif" name="sCalImg" id="sCalImg" width="20" height="20"
			      		align="top" style="cursor:hand" alt="點選此處選擇日期">&nbsp;
			</td>
			<td>&nbsp;</td>
	      	</tr>
	      	</table></td>
        </tr>
        <tr>
        	<td><table width="100%"><tr>
        	<td width="80" align="right" valign="middle">輔導類別：</td>
        	<td align="left" valign="middle" class="hairLineTd">
        		<select name="ctype" onchange="changeItem(this.value);">
          			<option value="">請選擇輔導類別</option>
        			<option value="T">職涯輔導</option>
         			<option value="U">學習輔導</option>
        		</select>
        	</td>
 			<td>&nbsp;</td>
        	</tr></table></td>
        </tr>
        <tr>
	        <td align="left"><table width="100%"><tr>
	        	<td width="80" align="right" valign="middle">輔導項目：</td>
	        	<td align="left" valign="middle">
	        	<select name="itemNo" id="itemNo">
	        		<option value="">請先選擇輔導類別</option>
	        	</select>
	        	</td>
			<td>&nbsp;</td>
	        </tr></table></td>
        </tr>
        <tr>
	        <td align="left"><table width="100%"><tr>
	        	<td width="80" align="right" valign="middle">輔導內容：</td>
	        	<td align="left" valign="middle">
	        	<textarea name="content" rows="3" cols="60"></textarea>
	        	</td>
			<td>&nbsp;</td>
	        </tr></table></td>
        </tr>
	</c:otherwise>
	
	</c:choose>	
	</table>
	</td>
	</tr>
	
	<input type="hidden" name="opmode" value="" id="opmode">	
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='BatchSave'/>" id="ok">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" id="cancel">');
	//generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	</script>
</table>
</form>

<script>
<!--
	/*
		建立Select 的 Option 項目
		objId : Select Object id
		jsonObj = [{"text":apple, "value":1}, {},...]
		value_field : 選項的值, 在jsonObj 中所取用的欄位
		label_field : 選項的標題, 如果有設定則選用 jsonObj 中的欄位, 若無則以選項的值為標題
		selOption : 預設的選項值
	*/
	function createOptions(objId, jsonObj, value_field, label_field, selOption){
		var option = null;
		var selObj = document.getElementById(objId);
		clearOptions(objId);
		//window.alert("option json length:" + jsonObj.length + ", value_field:" + value_field+ ", label_field:" + label_field + ", selOption:" + selOption);
		option = document.createElement("option");
		option.text = "";
		option.value = "";
		selObj.add(option);
		for(var i=0; i<jsonObj.length; i++){
			option = document.createElement("option");
			//window.alert("jsonObj[" + i + "][" + value_field + "]=" + jsonObj[i][value_field]);
			if(jsonObj[i][value_field] != null){
				if(jsonObj[i][label_field] != null){
					option.text = jsonObj[i][label_field];
					option.value = jsonObj[i][value_field];
				}else{
					option.text = jsonObj[i][value_field];
					option.value = jsonObj[i][value_field];
				}
				selObj.add(option);
			}
			if(jsonObj[i][value_field] != null){
				if(jsonObj[i][label_field] != null){
					if(jsonObj[i][value_field] == selOption){
						option.selected = true;
					}else{
						option.selected = false;
					}
				}else{
					if(jsonObj[i][value_field] == selOption){
						option.selected = true;
					}else{
						option.selected = false;
					}
				}
			}
		}
	}
	
	/*
	**	清除選單
	**	objId : Select 選單的 id
	*/
	function clearOptions(objId){
		var selObj = document.getElementById(objId);
		if(selObj != null){
			//window.alert("clear options:" + selObj.length);
			while(selObj.length > 0){
				selObj.remove(0);
			}
		}
	}
	
var codeT = '${StudCounselCodeArrayT}';
var codeL = '${StudCounselCodeArrayL}';

function changeItem(myValue){
	var mycode;
	if(myValue=='T'){
		mycode = codeT;
	}
	if(myValue=='U'){
		mycode = codeL;
	}
	clearOptions("itemNo");
	var jsonObj = JSON.parse(mycode);
	createOptions("itemNo", jsonObj, "itemNo", "itemName", "");
}

history.go(1);
//-->
</script>
<%@ include file="/pages/studaffair/include/MyCalendarAD.inc" %>	
