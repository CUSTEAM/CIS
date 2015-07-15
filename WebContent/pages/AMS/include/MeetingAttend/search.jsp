<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">
	
	<tr>		
		<td>		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" nowrap>
			      ${SchoolYear }年度,第${SchoolTerm }學期
			    </td>
			</tr>
		</table>
	  </td>
	</tr>
	<tr>
	  <td>
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" nowrap>
				  會議名稱
				</td>
				<td class="hairLineTd">
				  <select name="smeeting">
					<option value="">請選擇</option>
					<c:forEach items="${smeeting}" var="c">
					<option value="${c.Oid}">${c.Name}</option>
					</c:forEach>
				  </select>
				</td>
				<td class="hairLineTdF" nowrap>
				單位
				</td>
				<td class="hairLineTd">
				  <select name="sunit">
					<option value="">所有單位</option>
					<c:forEach items="${allUnit}" var="c">
					<option value="${c.idno}">${c.name}</option>
					</c:forEach>
				  </select>
				</td>
						
			</tr>
		</table>
		
		</td>		
	</tr>
	<tr>
		<td>
		
		
		<table class="hairLineTable" align="left">
			<tr>
				
			</tr>
		</table>
		
		
		<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
			<tr>
				<td id="ds_calclass"></td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>
