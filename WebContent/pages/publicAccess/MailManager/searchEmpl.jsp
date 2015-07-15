<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp"%>


<div style="display:inline" id="emplSearch">

<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" nowrap>
		<select id="group" style="font-size:18px;">
			<option value="">選擇公用群組</option>
			<c:forEach items="${group}" var="g">
			<option value="${g.Oid}">${g.name}</option>
			</c:forEach>
		</select>
		</td>
		<td class="hairLineTdF" nowrap>
		<select id="myGroup" style="font-size:18px;">
			<option value="">選擇自訂群組</option>
			<c:forEach items="${myGroup}" var="g">
			<option value="${g.Oid}">${g.name}</option>
			</c:forEach>
		</select>
		</td>
		<td class="hairLineTdF" width="100%" nowrap><font size="-1">選擇群組或<a href="/CIS/Calendar/TxtGroupManager.do">管理已建立的群組</a></font></td>
	</tr>
</table>

<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" nowrap>同仁姓名</td>
		<td class="hairLineTdF">
			<input type="text" name="cname" id="cname" size="6" style="font-size:18px;" value="${MailManagerForm.map.cname}"/>			
		</td>
		<td class="hairLineTdF" nowrap>職務名稱</td>
		<td class="hairLineTdF">
			<input type="text" name="sname" id="sname" size="8" style="font-size:18px;" value="${MailManagerForm.map.sname}" />
		</td>
		<td class="hairLineTdF" width="100%"><font size="-1">職務關鍵字如:系主任、系助理、主任、助理、長</font></td>
	</tr>
</table>

<div id="advsh" style="display:none;">
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" nowrap>單位</td>
		<td class="hairLineTdF" nowrap>
			<select name="unit" id="unit" style="font-size:18px;">
				<option value="">所有單位</option>
				<c:forEach items="${allUnit}" var="c">
					<option <c:if test="${MailManagerForm.map.unit==c.idno}">selected</c:if>
						value="${c.idno}">
						${c.name}
					</option>
				</c:forEach>
			</select>
			
			<select name="category" id="category" style="font-size:18px;">
				<option value="">身份類型</option>
				<c:forEach items="${allCategory}" var="c">
					<option <c:if test="${MailManagerForm.map.category==c.idno}">selected</c:if>
						value="${c.idno}">
						${c.name}
					</option>
				</c:forEach>
			</select>
		</td>
		<td class="hairLineTdF" width="100%"><font size="-1">任職單位與任職身份</font></td>
	</tr>
</table>

<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" nowrap>兼任導師</td>
		<td class="hairLineTdF">
			<select name="Tutor" id="Tutor" style="font-size:18px;">
				<option value="">不限</option>
				<c:forEach items="${allTutor}" var="c">
					<option <c:if test="${MailManagerForm.map.Tutor==c.idno}">selected</c:if>
						value="${c.idno}">
						${c.name}
					</option>
				</c:forEach>
			</select>
		</td>
		<td class="hairLineTdF">
			<select name="pcode" id="pcode" style="font-size:18px;">
				<option value="">專任職位</option>
				<c:forEach items="${allPcode}" var="c">
					<option <c:if test="${MailManagerForm.map.pcode==c.idno}">selected</c:if>
						value="${c.idno}">
						${c.name}
					</option>
				</c:forEach>
			</select>
		</td>
		<td class="hairLineTdF">
			<select name="Director" id="Director" style="font-size:18px;">
				<option value="">兼任職位</option>
				<c:forEach items="${allDirector}" var="c">
					<option <c:if test="${MailManagerForm.map.Director==c.idno}">selected</c:if>
						value="${c.idno}">
						${c.name}
					</option>
				</c:forEach>
			</select>
		</td>
		<td class="hairLineTdF" width="100%"><font size="-1">選單中找不到職位請以公用群組或職稱搜尋<br>如:系主任、系助理</font></td>
	</tr>
</table>
</div>

<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" width="100%">
			<input type="button" value="找尋成員" id="gm" name="gm" onClick="getMB();" class="gSubmit" />
			<input type="button" value="更多條件" onClick="showObj('advsh');" class="gGreen"/>
		</td>
	</tr>
</table>		
</div>
