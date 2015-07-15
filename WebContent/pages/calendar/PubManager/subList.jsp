<%@ page language="java" import="java.util.*" pageEncoding="BIG5"%>
<table id="old${v.count}" style="display:none;" width="100%">
	<tr>
		<td>
		<c:forEach items="${m.cs}" var="cs">
		<table width="99%" class="hairLineTable">
		<tr>
			<td class="hairLineTdF">
			<table>
				<tr>
					<td width="100%">${cs.time}, ${cs.name}, ${cs.place}</td>
					<td>
					<input type="hidden" name="delOid" id="delOid${cs.Oid}" value=""/>					
					<input type="submit" name="method"value="<bean:message key='Delete'/>" 
					onClick="checkDel('${cs.Oid}');"
					class="gCancelSmall">					
					</td>
				</tr>
			</table>
			</td>
		</tr>
		</table>
		</c:forEach>
		
		</td>
	</tr>
</table>

