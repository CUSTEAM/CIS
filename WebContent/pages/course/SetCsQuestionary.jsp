<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題列 start-->	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/user_tick.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">教學評量管理</font></div>		
		</td>
	</tr>
<!-- 標題列 end -->
<!--html:form action="/Course/SetCsquestionary" method="post" enctype="multipart/form-data" onsubmit="init('執行中, 請稍後')"-->
<html:form action="/Course/SetCsquestionary" method="post" onsubmit="init('執行中, 請稍後')">
	<tr>
		<td>
		<%@ include file="SetCsQuestionary/timeManager.jsp"%>
<c:if test="${!editCoQ}">
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" width="30" align="center"><img src="images/icon/icon_info_exclamation.gif" border="0"></td>
		<td class="hairLineTdF">問卷進行中無法修改問卷內容。</td>
	</tr>
</table>
</c:if>

<!-- 時間未開始前
<c:if test="${editCoQ}">
<include file="SetCsQuestionary/add.jsp">


<table>	
	<tr>	
		<td>		
		<table width="100%">
		<tr>
		<td width="50%" valign="top">				
				<table width="100%">
					<tr>
						<td>						
						<table width="100%" onClick="showTable('rNq')">
							<tr>
								<td>
								<img src="images/interface_dialog.gif">
								</td>
								<td width="100%" align="left">
								一般課程題目及選項設定
								</td>
							</tr>
						</table>						
						</td>
					</tr>					
					<tr id="rNq" style="display:none;">
						<td>						
						<c:forEach items="${nQuest}" var="n">
						<table cellpadding="5" cellspacing="1" bgcolor="#cfe69f">
							<tr bgcolor="f0fcd7" onClick="addOne${n.Oid}('coadd${n.Oid}')">
								<td>刪除</td>
								<td>順序</td>
								<td>分數</td>
								<td>題目 / 選項</td>
							</tr>
							<tr bgcolor="f0fcd7">
								<td><input type="hidden" name="Oid" value="${n.Oid}" /><input type="checkbox" onClick="checkEdit('edit${n.Oid}')"/>
								<input type="hidden" name="checkDel" id="edit${n.Oid}"/></td>
								<td><input type="text" name="sequence" value="${n.sequence}" size="1"/></td>
								<td><input type="text" value="N/A" disabled size="1"/><input type="hidden" name="value" value="${n.value}" size="1" readonly/></td>
								<td><input type="text" name="options" value="${n.options}"/></td>							
							</tr>
							<c:forEach items="${n.subOptions}" var="sn">
								<tr bgcolor="ffffff">
								<td><input type="hidden" name="Oid" value="${sn.Oid}" />
									<input type="checkbox" onClick="checkEdit('edit${sn.Oid}')"/>
									<input type="hidden" name="checkDel" id="edit${sn.Oid}"/></td>
								<td><input type="text" name="sequence" value="${sn.sequence}" size="1"/></td>
								<td><input type="text" name="value" value="${sn.value}" size="1"/></td>
								<td><input type="text" name="options" value="${sn.options}"/></td>							
							</tr>
							</c:forEach>							
						</table>
						
						<div id="coadd${n.Oid}">
						<table><tr height="5"><td></td></tr></table>
						</div>
						
<script>
function addOne${n.Oid}(id){
		var component=document.getElementById(id);
		
		component.innerHTML=component.innerHTML+"<table cellpadding='5' cellspacing='1' bgcolor='#cfe69f'><tr bgcolor='f0fcd7'>"+
		"<td><input type='hidden' name='aParentOid' value='${n.Oid}'/><font color='f0fcd7'>囧兀</font>"+
		"<input type='hidden' name='aType' value='n'/><input type='hidden' name='aTextValue' value='0'/></td><td>"+
		"<input type='text' name='aSequence' size='1' />"+
		"</td><td><input type='text' name='aValue' size='1' /></td><td><input type='text' name='aOptions' /></td></tr></table>"+
		"<table><tr height='5'><td></td></tr></table>";
	}
</script>

						</c:forEach>					
						</td>
					</tr>
				</table>
				
		
		</td>
			
		<td width="50%" valign="top">
		
		
		
		
		<table width="100%">
			<tr>
				<td>
						
				<table width="100%" onClick="showTable('rEq')">
					<tr>
						<td>
						<img src="images/list_world.gif">
						</td>
						<td width="100%" align="left">
						遠距課程題目及選項設定
						</td>
					</tr>
				</table>
						
				</td>
			</tr>
			<tr id="rEq" style="display:none">
				<td>
				
				<c:forEach items="${eQuest}" var="n">
						<table cellpadding="5" cellspacing="1" bgcolor="#cfe69f">
							<tr bgcolor="f0fcd7" onClick="eaddOne${n.Oid}('coadd${n.Oid}')">
								<td>刪除</td>
								<td>順序</td>
								<td>分數</td>
								<td>題目 / 選項</td>
							</tr>
							<tr bgcolor="f0fcd7">
								<td><input type="hidden" name="Oid" value="${n.Oid}" /><input type="checkbox" onClick="checkEdit('edit${n.Oid}')"/>
								<input type="hidden" name="checkDel" id="edit${n.Oid}"/></td>
								<td><input type="text" name="sequence" value="${n.sequence}" size="1"/></td>
								<td><input type="text" value="N/A" disabled size="1"/><input type="hidden" name="value" value="${n.value}" size="1" readonly/></td>
								<td><input type="text" name="options" value="${n.options}"/></td>							
							</tr>
							<c:forEach items="${n.subOptions}" var="sn">
								<tr bgcolor="ffffff">
								<td><input type="hidden" name="Oid" value="${sn.Oid}" />
									<input type="checkbox" onClick="checkEdit('edit${sn.Oid}')"/>
									<input type="hidden" name="checkDel" id="edit${sn.Oid}"/></td>
								<td><input type="text" name="sequence" value="${sn.sequence}" size="1"/></td>
								<td><input type="text" name="value" value="${sn.value}" size="1"/></td>
								<td><input type="text" name="options" value="${sn.options}"/></td>							
							</tr>
							</c:forEach>
							
						</table>
						
						<div id="coadd${n.Oid}">
						<table><tr height="5"><td></td></tr></table>
						</div>
						
<script>
function eaddOne${n.Oid}(id){
		var component=document.getElementById(id);
		
		component.innerHTML=component.innerHTML+"<table cellpadding='5' cellspacing='1' bgcolor='#cfe69f'><tr bgcolor='f0fcd7'>"+
		"<td><input type='hidden' name='aParentOid' value='${n.Oid}'/><font color='f0fcd7'>囧兀</font>"+
		"<input type='hidden' name='aType' value='n'/><input type='hidden' name='aTextValue' value='1'/></td><td>"+
		"<input type='text' name='aSequence' size='1' />"+
		"</td><td><input type='text' name='aValue' size='1' /></td><td><input type='text' name='aOptions' /></td></tr></table>"+
		"<table><tr height='5'><td></td></tr></table>";
	}
</script>
						</c:forEach>				
				</td>
			</tr>
		</table>			
		</td>
		</tr>
		</table>		
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<table width="100%">
			<tr>
				<td>		
					<table align="left">
						<tr>
							<td>		
							包含線上課程: <a href="/CIS/pages/course/export/coQuestion.jsp?type=e"><img src="images/ico_file_word.png" border="0"></a>
							</td>
							<td>		
							不含線上課程: <a href="/CIS/pages/course/export/coQuestion.jsp?type=n"><img src="images/ico_file_word.png" border="0"></a>
							</td>
						</tr>
					</table>	
				</td>
			</tr>
		</table>
		</td>
	</tr>	
	<tr>
		<td class="fullColorTable" align="center" width="100%">
		<INPUT type="submit"
						   name="method"
						   value="<bean:message key='SetQuest'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Back'/>" disabled
													   class="CourseButton"><INPUT type="submit"
													   						 name="method"
													   						 value="<bean:message
													   						 key='Cancel'/>"
													   						 class="CourseButton" disabled>
		</td>
	</tr>
</table>
</c:if>
時間未開始前 -->















		</td>
	</tr>
	<tr height="30" class="fullColorTr">
		<td align="center">
		<INPUT type="submit" name="method" value="<bean:message key='SetQuestDate'/>" class="gGreen">
		</td>
	</tr>
<!-- 時間開始後 -->
<c:if test="${!editCoQ}">
	<tr>
		<td>		
		<table>
			
			<tr>				
				<td>
				<table class="hairLineTable">
					<tr>
					
					
					<td class="hairLineTdF">
						<select name="school_term">
						<option <c:if test="${SetCsquestionaryForm.map.school_term=='1'}">selected</c:if> value="1">第1學期</option>
						<option <c:if test="${SetCsquestionaryForm.map.school_term=='2'}">selected</c:if> value="2">第2學期</option>
						</select>
					</td>
					
					
					<td class="hairLineTdF">選擇班級</td>
					<td class="hairLineTd">					
					<input type="text" id="classNo" name="classNo" size="10" 
					autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				 	value="${SetCsquestionaryForm.map.classNo}"
				 	onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
				 	onclick="this.value='', document.getElementById('className').value='', document.getElementById('schoolType').value=''"/><input 
				 	type="text" name="className" id="className"
				 	value="${SetCsquestionaryForm.map.className}"
				 	size="12" onkeyup="getAny(this.value, 'className', 'classNo', 'Class', 'name')"
				 	onclick="this.value='', document.getElementById('classNo').value='', document.getElementById('schoolType').value=''"/>
				 	<SELECT name="schoolType" onChange="clearClassNo()">
						<option value="">或選擇部制</option>
						<c:forEach items="${allSchoolType}" var="as">
						<option value="${as.idno}" <c:if test="${SetCsquestionaryForm.map.schoolType==as.idno}">selected</c:if>>${as.name}</option>
					</c:forEach>
					</SELECT>
					</td>
					<td class="hairLineTdF" width="30" align="center">
				 	<img src="images/16-exc-mark.gif" />
					</td>
					
					</tr>
				</table>
				
				</td>
				
			</tr>
		</table>
		<table>
			<tr>			
				<td>				
				<table class="hairLineTable">
				<tr>				
				<td class="hairLineTdF">找某位教師</td>
				<td class="hairLineTd">					
				<input type="text" name="techid" id="techidS" size="12" style="ime-mode:disabled" autocomplete="off"
				value="${SetCsquestionaryForm.map.teacherId}" onkeyup="if(this.value.length>2)getAny(this.value, 'techidS', 'technameS', 'empl', 'no')"
				onclick="this.value='', document.getElementById('technameS').value=''" /><input type="text" 
				onkeyup="getAny(this.value, 'technameS', 'techidS', 'empl', 'name')" autocomplete="off" onclick="this.value='', 
				document.getElementById('techidS').value=''" name="techname" id="technameS" size="10" value="${SetCsquestionaryForm.map.teacherName}"/>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/16-exc-mark.gif" />					
				</td>
				</tr>
				</table>				
				</td>				
			</tr>
		</table>		
		</td>
	</tr>
	<tr>
		<td>
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" colspan="4">下載紙本問卷</td>
			</tr>
			<tr>
				<td class="hairLineTdF" nowrap>包含線上課程:</td>
				<td class="hairLineTdF" width="30" align="center"><a href="/CIS/pages/course/export/coQuestion.jsp?type=e"><img src="images/icon/mimetypes/ico_file_word.gif" border="0"></a></td>
				<td class="hairLineTdF" nowrap>不含線上課程:</td>
				<td class="hairLineTdF" width="30" align="center"><a href="/CIS/pages/course/export/coQuestion.jsp?type=n"><img src="images/ico_file_word.png" border="0"></a></td>
			</tr>
		</table>
		</td>
	</tr>

	<tr height="30">
		<td class="fullColorTable" align="center" width="100%">
		<table>
			<tr>
				<td>
				<INPUT type="submit"
						   name="method"
						   value="<bean:message key='Query'/>"
						   class="gSubmit">
				</td>
				<td>					
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
<c:if test="${allCoansw!=null}">
	<tr>
		<td>
		<display:table name="${allCoansw}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list" >
			<display:column title="開課班級" property="ClassName" sortable="true" class="center" />
			<display:column title="班級代碼" property="depart_class" sortable="true" class="center" />
			<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
			<display:column title="課程代碼" property="cscode" sortable="true" class="center" />			
			<display:column title="選別" property="opt" sortable="true" class="center" />
			<display:column title="學分" property="credit" sortable="true" class="center" />
			<display:column title="型態" property="elearning" sortable="true" class="center" />			
			<display:column title="任課教師" property="cname" sortable="true" class="center" />			
			<display:column title="樣本數" property="sumAns" sortable="true" class="center" />
			<display:column title="總平均" property="total" sortable="true" class="center" />
		</display:table>
		</td>
	</tr>
	<tr height="40">
		<td>
		<table border="0" cellpadding="0" cellspacing="1" bgcolor="CFE69F" width="98%" align="center">
  			<tr>
    			<td bgcolor="#FFFFFF">    				
    				<table width="100%"> 
    					<tr>
    						<td align="left" width="1">
    						<img src="images/ico_file_excel.png" border="0">
    						</td>
    						<td>    						
    						<select name="reportType" onchange="jumpMenu('parent',this,0)" >
    								<option value="javascript:void(0)">選擇報表</option>
    								<option value="/CIS/pages/course/export/list4Coansw.jsp?type=excel">統計表</option>
    								<option value="/CIS/Course/CoanswReview?depart_class=${SetCsquestionaryForm.map.classNo}">教師改善表</option>							
    							</select>    						
    						</td>
    					</tr>
    				</table>    				
<script>
			
function jumpMenu(targ,selObj,restore){
	eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
	eval(targ+".location.target='_blank'");
	if (restore) selObj.selectedIndex=0;
}
</script>
    				
    				
    				
    				
    				
    			</td>
    		</tr>
    	</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>
</c:if>




</c:if>
<!-- 時間開始後 -->








</html:form>
</table>

<script>
	function showTable(id){
		if(document.getElementById(id).style.display=='none'){
			document.getElementById(id).style.display='inline';
		}else{
			document.getElementById(id).style.display='none';
		}
	}
</script>

<script>
	function checkEdit(id){
		if(document.getElementById(id).value=='1'){
			document.getElementById(id).value='';
		}else{
			document.getElementById(id).value='1';
		}	
	}
</script>

<script>
	function clearClassNo(){
		document.getElementById('className').value='';
		document.getElementById('classNo').value='';
	}
</script>

<%@ include file="/pages/include/MyCalendar.jsp" %>
<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>



