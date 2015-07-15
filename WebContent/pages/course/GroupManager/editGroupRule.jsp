<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<br>
<table width="100%" cellpadding="0" cellspacing="0" onClick="showEdit('groupRule')">
	<tr style="cursor:pointer;">
		<td width="10" align="left" nowrap>
    		<hr noshade class="myHr"/>
  		</td>
  		<td width="24" align="center" nowrap>
 		<img src="images/icon/image_link.gif" id="add" onMouseOver="showHelpMessage('...', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)">
  		</td>
  		<td nowrap style="cursor:pointer;">
  		學程基本規則&nbsp;
  		</td>
  		<td width="100%" align="left">
    		<hr noshade class="myHr"/>
  		</td>
	</tr>
</table>
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td id="groupRule" <c:if test="${groupType!='editRule'}">style="display:none"</c:if>>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" width="30" align="center"></td>
				<td class="hairLineTdF">學制</td>
				<td class="hairLineTdF">核心必修</td>
				<td class="hairLineTdF">核心選修</td>
				<td class="hairLineTdF">外系學分</td>		
			</tr>
			<tr>
				<td class="hairLineTdF" width="30" align="center"><img src="images/icon/image_add.gif" /></td>
				<td class="hairLineTdF"><input type="hidden" name="groupRuleOid" value="" />
				<select name="schoolNo">
					<option value="">新增部制</option>
					<c:forEach items="${allSchool}" var="as">
					<option value="${as.idno}">${as.name}</option>
					</c:forEach>
				</select>				
				</td>
				
				<td class="hairLineTdF"><input type="text" size="3" name="major" /></td>
				<td class="hairLineTdF"><input type="text" size="3" name="minor" /></td>
				<td class="hairLineTdF"><input type="text" size="3" name="outdept" /><input type="hidden" size="3" name="groupRuleEnd" /></td>
			</tr>
		</table>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTd">
				<INPUT type="submit" name="method" value="<bean:message key='EditGroupRule' bundle='COU'/>" class="CourseButton" />
				</td>
			</tr>
		</table>
<c:if test="${!empty aGroupRule}">
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" width="30" align="center"></td>
				<td class="hairLineTdF">學制</td>
				<td class="hairLineTdF">核心必修</td>
				<td class="hairLineTdF">核心選修</td>
				<td class="hairLineTdF">外系學分</td>
				<td class="hairLineTdF">適用最後入學年</td>				
			</tr>		
			<c:forEach items="${aGroupRule}" var="ar">
			<tr>
				<td class="hairLineTdF" width="30" align="center"><img src="images/icon/image_edit.gif" /></td>
				<td class="hairLineTdF">
				<input type="hidden" name="groupRuleOid" value="${ar.Oid}" />
							
				<select name="schoolNo">
					<option value="">刪除部制</option>
					<c:forEach items="${allSchool}" var="as">
					<option <c:if test="${as.idno==ar.schoolNo}">selected</c:if> value="${as.idno}">${as.name}</option>
					</c:forEach>
				</select>
				</td>
				
				<td class="hairLineTdF"><input type="text" size="3" name="major" value="${ar.major}" /></td>
				<td class="hairLineTdF"><input type="text" size="3" name="minor" value="${ar.minor}" /></td>
				<td class="hairLineTdF"><input type="text" size="3" name="outdept" value="${ar.outdept}" /></td>
				<td class="hairLineTdF"><input type="text" size="3" name="groupRuleEnd" value="${ar.entrance}" /></td>
				</c:forEach>
		</table>
		
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTd">
				<INPUT type="submit" name="method" value="<bean:message key='EditGroupRule' bundle='COU'/>" class="CourseButton" />
				</td>
			</tr>
		</table>
</c:if>		
		
		
		</td>
	</tr>
</table>	
	