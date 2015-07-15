<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<c:if test="${empty myOffLineWork}">
<table align="center">
	<tr>
		<td width="30" align="center"><img src="images/icon/bell_delete.gif" /></td>
		<td>
		目前沒有完成項目
		</td>
	</tr>
</table><br>
</c:if>
<c:forEach items="${myOffLineWork}" var="m">		
<table width="100%" cellpadding="0" cellspacing="0">		
	<tr>
		<td>
		<table width="99%" cellpadding="0" cellspacing="0" class="hairLineTable">
			<tr height="35">
				<td width="100" align="center" class="hairLineTdF">
				<input type="hidden" name="Oid" value="${m.Oid}" size="1"/>
				<input type="hidden" name="checkOid" size="1" id="checkOid${m.Oid}"/>
				<select name="status" class="CourseButton" onChange="checkService('${m.Oid}');">					
					<option value="W">等待</option>
					<option value="O">處理</option>
					<option <c:if test="${m.status=='B'}">selected</c:if> value="B">暫停</option>
					<option value="">------------</option>
					<option <c:if test="${m.status=='F'}">selected</c:if> value="F">完成</option>					
					<option <c:if test="${m.status=='M'}">selected</c:if> value="M">寄出</option>
					<option value="">------------</option>
				</select>						
									
				</td>
			
				<td class="hairLineTdF" onClick="showObj('doc${m.Oid}'), checkService('${m.Oid}');" style="cursor:pointer;">
				編號: <b><a href="../getOnlineServiceDoc?docNo=${m.doc_no}">${m.doc_no}</a></b>, 
				申請人: <b><a href="#">${m.ClassName}${m.student_no}</b> - <b>${m.student_name}</a></b>, 
				申請日期: <b>${m.send_time}</b>
				<c:if test="${m.connects!='0'}">, 已通知: <b>${m.connects}</b>次</c:if>
				
				</td>
				<td class="hairLineTdF" width="30" align="center">
				
				<table>
					<tr>
						<td><input type="checkBox" id="check${m.Oid}"  onClick="unCheckService('${m.Oid}')"/></td>
						<td><img src="images/email_go.gif" /></td>
					</tr>
				</table>
				
				</td>
			</tr>
		</table>				
		</td>
	</tr>
</table>

<table class="hairLineTable" width="99%" id="doc${m.Oid}" style="display:none;">
	
	<c:if test="${m.tcv!='' && m.tcv!=null}">
	<tr>
		<td class="hairLineTdF">中文成績單${m.tcv}份</td>
	</tr>
	</c:if>
	
	<c:if test="${m.tcv_army!='' && m.tcv_army!=null}">
	<tr>
		<td class="hairLineTdF">中文成績單（折抵役期證明）${m.tcv_army}份</td>
	</tr>
	</c:if>
	
	<c:if test="${m.tcvigr!='' && m.tcvigr!=null}">
	<tr>
		<td class="hairLineTdF">中文成績附排名 ${m.tcvigr}份</td>
	</tr>
	</c:if>
	
	<c:if test="${m.gcr!='' && m.gcr!=null}">
	<tr>
		<td class="hairLineTdF">畢業證明書 ${m.gcr}份</td>
	</tr>
	</c:if>
	
	<c:if test="${m.cscna!='' && m.cscna!=null}">
	<tr>
		<td class="hairLineTdF">補發修業證明 ${m.cscna}份</td>
	</tr>
	</c:if>
	
	<c:if test="${m.tev!='' && m.tev!=null}">
	<tr>
		<td class="hairLineTdF">英文成績單 ${m.tev}份</td>
	</tr>
	</c:if>
	
	<c:if test="${m.gcev!='' && m.gcev!=null}">
	<tr>
		<td class="hairLineTdF">英文畢業證書 ${m.gcev}份</td>
	</tr>
	</c:if>
	<tr>
		<td class="hairLineTdF" align="right">		
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td>備註&nbsp;</td>
				<td><img src="images/input_left.gif"/></td>
				<td>
				<input type="hidden" name="passport"/>
				<input type="hidden" name="idCard"/>
				<input type="hidden" name="familyBook"/>
				<input type="hidden" name="payMoney"/>
				<input type="text" size="90" class="colorInput" name="note" value="${m.note}"/>
				
				</td>
				<td><img src="images/input_right.gif"/></td>
			</tr>
		</table>		
		</td>
	</tr>
	<tr>
		<td class="hairLineTdF" align="right">預計 <b>${m.expect_time}</b>, 於 <b>${m.get_method}</b> 取件, 金額估計 $.<b>${m.total_pay}</b></td>
	</tr>
	
	
</table>
</c:forEach>