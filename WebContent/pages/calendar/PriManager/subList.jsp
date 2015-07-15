<%@ page language="java" import="java.util.*" pageEncoding="BIG5"%>
<table id="old${v.count}" style="display:none;" width="100%">
	<tr>
		<td>
		<c:forEach items="${m.cs}" var="cs">
		<table width="99%" class="hairLineTable">
		<tr>
			<td class="hairLineTdF">
			
			<%@ include file="../Directory/listContent.jsp"%>
			
			
			
			
			
			
			
			
			
			
			</td>
		</tr>
		</table>
		</c:forEach>
		
		</td>
	</tr>
</table>

