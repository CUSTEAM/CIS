<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<br>
<table width="100%" cellpadding="0" cellspacing="0" onClick="showEdit('groupCourse')">
	<tr style="cursor:pointer;">
		<td width="10" align="left" nowrap>
    		<hr noshade class="myHr"/>
  		</td>
  		<td width="24" align="center" nowrap >
 		<img src="images/icon/images.gif" id="add" onMouseOver="showHelpMessage('...', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)">
  		</td>
  		<td nowrap style="cursor:pointer;">
  		學程課程組合&nbsp;
  		</td>
  		<td width="100%" align="left">
    		<hr noshade class="myHr"/>
  		</td>
	</tr>
</table>
<table cellspacing="0" cellpadding="0" width="100%">	
	<tr>
		<td id="groupCourse" <c:if test="${groupType!='editCs'}">style="display:none"</c:if>>
		
		<table cellspacing="0" cellpadding="0" width="100%" >
			<tr>
				<td id="addCs">
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF"></td>
						<td class="hairLineTdF">系所</td>
						<td class="hairLineTdF">課程代碼</td>
						<td class="hairLineTdF">課程名稱</td>
						<td class="hairLineTdF">選別</td>
						<td class="hairLineTdF">學分數</td>
					</tr>
					
					<tr>
						<td class="hairLineTdF" width="30" align="center"><img src="images/icon/image_add.gif"/></td>
						<td class="hairLineTdF">
						<input type="hidden" name="groupSetGroup" size="3" /><input type="hidden" name="groupSetOid" size="3" />
						<select name="groupSetDeptNo">
							<option value="">開課系所</option>
							<c:forEach items="${allDept}" var="ad">
							<option value="${ad.idno}">${ad.name}</option>
							</c:forEach>
						</select>
						
						</td>
						
						<td class="hairLineTdF">
						
						<input type="text" name="cscode" id="cscode" size="8" autocomplete="off" style="ime-mode:disabled" 
						autocomplete="off" value="${ags.cscode}" 
						onkeyup="if(this.value.length>2)getAny(this.value, 'cscode', 'chiName', 'Csno', 'no')"/>
						
						</td>
						
						<td class="hairLineTdF">
						
						<input type="text" autocomplete="off" name="chiName" id="chiName" size="16" value="${ags.chi_name}" 
						onkeyup="getAny(this.value, 'chiName', 'cscode', 'Csno', 'name')"
						onkeydown="document.getElementById('Acsname').style.display='none';"/>
						</td>
						
						<td class="hairLineTdF">
						<select name="opt">
							<option value="2">核心選修</option>
							<option value="1">核心必修</option>							
						</select>
						</td>
						
						<td class="hairLineTdF">
						<input type="text" name="credit" size="3" />
						<input type="hidden" name="groupSetEnd" size="3" />
						</td>
						
					</tr>
				</table>
				
				</td>
			</tr>
		</table>
		
		
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTd">				
				<INPUT type="submit" name="method" value="<bean:message key='EditGroupSet' bundle='COU'/>" class="CourseButton" />
				</td>
			</tr>
		</table>
<c:if test="${!empty aGroupSet}">		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF"></td>
				<td class="hairLineTdF">系所</td>
				<td class="hairLineTdF">課程代碼</td>
				<td class="hairLineTdF">課程名稱</td>
				<td class="hairLineTdF">選別</td>
				<td class="hairLineTdF">學分數</td>
				<td class="hairLineTdF">適用最後入學年</td>
			</tr>
			<c:forEach items="${aGroupSet}" var="ags">
			<tr>
				<td class="hairLineTdF" width="30" align="center"><img src="images/icon/image_edit.gif"/></td>
				<td class="hairLineTdF">
				<input type="hidden" name="groupSetGroup" size="3" value="${ags.group_oid}" /><input type="hidden" name="groupSetOid" value="${ags.Oid}" size="3" />
				
				<select name="groupSetDeptNo">
					<option value="">刪除課程</option>
					<c:forEach items="${allDept}" var="ad">
					<option <c:if test="${ad.idno==ags.deptNo}">selected</c:if> value="${ad.idno}">${ad.name}</option>
					</c:forEach>
				</select>
				</td>
				
				<td class="hairLineTdF">
				
				<input type="text" name="cscode" id="cscode${ags.Oid}" size="8" autocomplete="off" style="ime-mode:disabled" 
				autocomplete="off" value="${ags.cscode}" 
				onkeyup="if(this.value.length>2)getAny(this.value, 'cscode${ags.Oid}', 'chiName${ags.Oid}', 'Csno', 'no')"/>
				
				</td>
				<td class="hairLineTdF">
				
				<input type="text" autocomplete="off" name="chiName" id="chiName${ags.Oid}" size="16" value="${ags.chi_name}" 
				onkeyup="getAny(this.value, 'chiName${ags.Oid}', 'cscode${ags.Oid}', 'Csno', 'name')"
				onkeydown="document.getElementById('Acsname').style.display='none';"/>
				</td>
				
				<td class="hairLineTdF">				
				<select name="opt">
					<option <c:if test="${ags.opt=='2'}">selected</c:if> value="2">核心選修</option>
					<option <c:if test="${ags.opt=='1'}">selected</c:if> value="1">核心必修</option>							
				</select>				
				</td>
				
				<td class="hairLineTdF">
					<input type="text" name="credit" size="3" value="${ags.credit}" />
				</td>
				
				<td class="hairLineTdF">
					<input type="text" name="groupSetEnd" size="3" value="${ags.entrance}" />
				</td>
				
			</tr>
			</c:forEach>
		</table>
		
		
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTd">				
				<INPUT type="submit" name="method" value="<bean:message key='EditGroupSet' bundle='COU'/>" class="CourseButton" />
				</td>
			</tr>
		</table>
</c:if>		
		</td>
	</tr>
</table>

<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %> 