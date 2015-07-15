<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<table class="hairLineTable" width="300">
	<tr>
		<td class="hairLineTdF" align="center"><b>第2步: 基本資料</b></td>		
	</tr>
</table>
<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" nowrap width="120" align="center">學號</td>
		<td class="hairLineTdF"><input disabled type="text" value="${std.student_no}"></td>
		<td class="hairLineTdF" nowrap width="120" align="center">姓名</td>
		<td class="hairLineTdF"><input disabled type="text" value="${std.student_name}"></td>
		<td class="hairLineTdF" nowrap width="120" align="center">系所組別</td>
		<td class="hairLineTdF"><input disabled type="text" value="${std.ClassName}"></td>
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="120" align="center">英文姓名</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.student_ename}"></td>
		<td class="hairLineTdF" nowrap width="120" align="center">外國國籍</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.BirthCountry}"></td>
		<td class="hairLineTdF" nowrap width="120" align="center"><font size="-2">身分證或居留證號</font></td>
		<td class="hairLineTdF"><input value="${std.idno}" disabled type="text"></td>
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="120" align="center">性別</td>
		<td class="hairLineTdF">
		
		<select disabled style="width:155px;">
			<option>選擇性別</option>
			<option <c:if test="${std.sex=='1'}">selected</c:if>>男</option>
			<option <c:if test="${std.sex=='2'}">selected</c:if>>女</option>
		</select>
		
		
		</td>
		<td class="hairLineTdF" nowrap width="120" align="center" >出生日期</td>
		<td class="hairLineTdF"><input disabled type="text" value="${std.birthday}"></td>
		<td class="hairLineTdF" nowrap width="120" align="center">出生地</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.birth_county}"></td>
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="120" align="center">入學身份</td>
		<td class="hairLineTdF">
		
		<select disabled style="width:155px;">
			<option>選擇入學身份</option>
			<c:forEach items="${ident}" var="i">
			<option <c:if test="${i.idno==std.ident}">selected</c:if>>${i.name}</option>
			</c:forEach>
		</select>
		
		</td>
		<td class="hairLineTdF" nowrap width="120" align="center">原住民族籍</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.Aborigine}"></td>
		<td class="hairLineTdF" nowrap width="120" align="center">僑生僑居地</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.ForeignPlace}"></td>
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="120" align="center">畢業學校</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.schl_name}"></td>
		<td class="hairLineTdF" nowrap width="120" align="center">畢業年度</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.gradyear}" maxlength="3"></td>
		<td class="hairLineTdF" nowrap width="120" align="center">畢業狀況</td>
		<td class="hairLineTdF">
		<select name="field" style="width:155px;">
			<option value="">選擇畢業狀況</option>
			<option <c:if test="${std.gradu_status=='1'}">selected</c:if> value="1">畢業</option>
			<option <c:if test="${std.gradu_status=='2'}">selected</c:if> value="2">肄業</option>
		</select>
		</td>
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="120" align="center">畢業科系</td>
		
		<td class="hairLineTdF" width="155">		
		<input name="field" type="text" style="width:100%;" value="${std.grad_dept}">
		</td>
		<td class="hairLineTdF" width="120" align="center">學生證照片</td>
		
		<td class="hairLineTdF" width="155">		
		<input type="file" name="stdimage">
		</td>
		<td class="hairLineTdF" width="155">
		<c:if test="${imageOid==''|| imageOid==null}">尚未上傳照片</c:if>
		<c:if test="${imageOid!=''&& imageOid!=null}"><a href="/CIS/ShowImage4T?Oid=${std.ImageOid}" target="_blank">檢視照片</a></c:if>
		</td>
		
	</tr>
</table>
<table class="hairLineTable" width="815">
	<tr>
		<td class="hairLineTdF" align="right">照片長寬比例必須為3:2，檔案大小不限制&nbsp;&nbsp;&nbsp;</td>		
	</tr>
</table>
<p></p>

<table class="hairLineTable" width="300">
	<tr>
		<td class="hairLineTdF" align="center"><b>第3步: 兵役資料</b></td>		
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="120" align="center">兵役類型</td>
		<td class="hairLineTdF">
		
		<select name="field" style="width:155px;">
			<option value="">選擇兵役類型</option>
			<option <c:if test="${std.ArmyType=='1'}">selected</c:if> value="1">義務役</option>
			<option <c:if test="${std.ArmyType=='2'}">selected</c:if> value="2">志願役</option>
			<option <c:if test="${std.ArmyType=='3'}">selected</c:if> value="3">替代役</option>
		</select>
		
		</td>
		<td class="hairLineTdF" nowrap width="120" align="center">軍種階級</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.ArmyLevel}"></td>
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="120" align="center">入伍日期</td>
		<td class="hairLineTdF">
		<input type="text" name="field" value="${std.ArmyIn}" onclick="ds_sh(this), this.value='';" 
		autocomplete="off" style="ime-mode:disabled" autocomplete="off" id="ArmyIn" readonly/>
		</td>
		<td class="hairLineTdF" nowrap width="120" align="center">退伍日期</td>
		<td class="hairLineTdF">
		<input type="text" name="field" value="${std.ArmyOut}" onclick="ds_sh(this), this.value='';" 
		autocomplete="off" style="ime-mode:disabled" autocomplete="off" id="ArmyOut" readonly/>
		<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
			<tr>
				<td id="ds_calclass"></td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>

<p></p>

<table class="hairLineTable" width="300">
	<tr>
		<td class="hairLineTdF" align="center"><b>第4步: 通訊資料</b></td>		
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="120" align="center">電子郵件</td>
		
		<td class="hairLineTdF" width="455">		
		<input name="field" type="text" style="width:100%;" value="${std.Email}">
		</td>
		
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="120" align="center">戶籍地址</td>
		
		<td class="hairLineTdF" width="455">
		<input name="field" type="text" style="width:19%; ime-mode:disabled;" maxlength="5" value="${std.perm_post}">
		<input name="field" type="text" style="width:80%;" value="${std.perm_addr}">
		</td>
		
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" nowrap width="120" align="center">電話</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.telephone}"></td>
		<td class="hairLineTdF" nowrap width="120" align="center">行動電話</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.CellPhone}"></td>
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="120" align="center">家長姓名</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.parent_name}"></td>
		<td class="hairLineTdF" nowrap width="120" align="center">家長關係</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.ParentRelationship}"></td>
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="120" align="center">家長年齡</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.ParentAge}"></td>
		<td class="hairLineTdF" nowrap width="120" align="center">家長職業</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.ParentCareer}"></td>
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="120" align="center">通訊地址</td>
		<td class="hairLineTdF" width="455">
		<input name="field" type="text" style="width:19%; ime-mode:disabled;" maxlength="5" value="${std.curr_post}">
		<input name="field" type="text" style="width:80%;" value="${std.curr_addr}">
		</td>
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" nowrap width="120" align="center">緊急連絡電話</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.EmergentPhone}"></td>
		<td class="hairLineTdF" nowrap width="120" align="center">行動電話</td>
		<td class="hairLineTdF"><input name="field" type="text" value="${std.EmergentCell}"></td>
	</tr>
</table>

<p></p>

<table class="hairLineTable" width="300">
	<tr>
		<td class="hairLineTdF" align="center"><b>第5步: 家庭成員</b></td>		
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="120" align="center">稱謂</td>		
		<td class="hairLineTdF" nowrap width="120" align="center">姓名</td>		
		<td class="hairLineTdF" nowrap width="120" align="center">年齡</td>		
		<td class="hairLineTdF" nowrap width="120" align="center">職業</td>		
	</tr>
	<tr>
		<td class="hairLineTdF" width="120" align="center"><input name="field" type="text" value="${std.MemberTitle1}"></td>		
		<td class="hairLineTdF" nowrap width="120" align="center"><input name="field" type="text" value="${std.MemberName1}"></td>		
		<td class="hairLineTdF" nowrap width="120" align="center"><input name="field" type="text" value="${std.MemberAge1}"></td>		
		<td class="hairLineTdF" nowrap width="120" align="center"><input name="field" type="text" value="${std.MemberCareer1}"></td>		
	</tr>
	<tr>
		<td class="hairLineTdF" width="120" align="center"><input name="field" type="text" value="${std.MemberTitle2}"></td>		
		<td class="hairLineTdF" nowrap width="120" align="center"><input name="field" type="text" value="${std.MemberName2}"></td>		
		<td class="hairLineTdF" nowrap width="120" align="center"><input name="field" type="text" value="${std.MemberAge2}"></td>		
		<td class="hairLineTdF" nowrap width="120" align="center"><input name="field" type="text" value="${std.MemberCareer2}"></td>		
	</tr>
	<tr>
		<td class="hairLineTdF" width="120" align="center"><input name="field" type="text" value="${std.MemberTitle3}"></td>		
		<td class="hairLineTdF" nowrap width="120" align="center"><input name="field" type="text" value="${std.MemberName3}"></td>		
		<td class="hairLineTdF" nowrap width="120" align="center"><input name="field" type="text" value="${std.MemberAge3}"></td>		
		<td class="hairLineTdF" nowrap width="120" align="center"><input name="field" type="text" value="${std.MemberCareer3}"></td>		
	</tr>
	<tr>
		<td class="hairLineTdF" width="120" align="center"><input name="field" type="text" value="${std.MemberTitle4}"></td>		
		<td class="hairLineTdF" nowrap width="120" align="center"><input name="field" type="text" value="${std.MemberName4}"></td>		
		<td class="hairLineTdF" nowrap width="120" align="center"><input name="field" type="text" value="${std.MemberAge4}"></td>		
		<td class="hairLineTdF" nowrap width="120" align="center"><input name="field" type="text" value="${std.MemberCareer4}"></td>		
	</tr>
</table>

<table class="hairLineTable" width="300">
	<tr>
		<td class="hairLineTdF" align="center"><b>第6步: 經歷</b></td>		
	</tr>
</table>
<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="120" align="center">服務機關</td>		
		<td class="hairLineTdF" width="120" align="center">職務</td>		
		<td class="hairLineTdF" width="120" align="center">開始日期</td>		
		<td class="hairLineTdF" width="120" align="center">結束日期</td>		
	</tr>
	<tr>
		<td class="hairLineTdF" width="120" align="center"><input name="field" type="text" value="${std.WorkPlace1}"></td>		
		<td class="hairLineTdF" width="120" align="center"><input name="field" type="text" value="${std.WorkTitle1}"></td>		
		<td class="hairLineTdF" width="120" align="center">
		<input type="text" name="field" value="${std.WorkBegin1}" onclick="ds_sh(this), this.value='';" 
		autocomplete="off" style="ime-mode:disabled" autocomplete="off" id="WorkBegin1" readonly/>
		</td>		
		<td class="hairLineTdF" width="120" align="center">
		<input type="text" name="field" value="${std.WorkEnd1}" onclick="ds_sh(this), this.value='';" 
		autocomplete="off" style="ime-mode:disabled" autocomplete="off" id="WorkEnd1" readonly/>
		</td>		
	</tr>
	<tr>
		<td class="hairLineTdF" width="120" align="center"><input name="field" type="text" value="${std.WorkPlace2}"></td>		
		<td class="hairLineTdF" width="120" align="center"><input name="field" type="text" value="${std.WorkTitle2}"></td>		
		<td class="hairLineTdF" width="120" align="center">
		<input type="text" name="field" value="${std.WorkBegin2}" onclick="ds_sh(this), this.value='';" 
		autocomplete="off" style="ime-mode:disabled" autocomplete="off" id="WorkBegin2" readonly/>
		</td>		
		<td class="hairLineTdF" width="120" align="center">
		<input type="text" name="field" value="${std.WorkEnd2}" onclick="ds_sh(this), this.value='';" 
		autocomplete="off" style="ime-mode:disabled" autocomplete="off" id="WorkEnd2" readonly/>		
		</td>
	</tr>

</table>
<p></p>

<table class="hairLineTable" width="300">
	<tr>
		<td class="hairLineTdF" align="center"><b>最後1步: 儲存</b></td>		
	</tr>
</table>

<table class="hairLineTable" width="300">
	<tr>
		<td class="hairLineTdF" align="center">
		<input type="submit" name="method" 
		value="<bean:message key='Save'/>" 
		id="Save" class="gSubmit">
		</td>		
	</tr>
</table>


<%@ include file="/pages/include/MyCalendarAD.jsp" %>