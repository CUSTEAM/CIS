<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
<table width="100%" id="value${v.count}" style="<c:if test="${m.weekDay==weekDay}"></c:if><c:if test="${m.weekDay!=weekDay}">display:none;"</c:if> width:100%;">
	<tr>
		<td>		
		
		<c:if test="${empty m.cs}">
		<table width="99%" class="hairLineTable">
		<tr>
			<td class="hairLineTdF" >
			<table width="100%">
				<tr>					
					<td width="30" align="center"><img src="images/icon/holidays.gif"/></td>
					<td nowrap align="left" width="100%">當日未排定事項</td>				
				</tr>
			</table>
			</td>
		</tr>
		</table>		
		</c:if>
		
		<table width="99%" class="hairLineTable">
		<tr>
			<td class="hairLineTdF" align="left">
			
			
			<div style="width:100%;">
			<table width="100%">
				<tr height="24">					
					<td nowrap style="cursor:pointer;" onClick="showObj('new${v.count}');" width="30" align="center">
					<img src="images/icon/plus.gif"/></td>
					<td nowrap align="left" style="cursor:pointer;" onClick="showObj('new${v.count}');" onMouseOver="showHelpMessage('按一下開始建立「新事項」', 'inline', this.id)" 
				 		onMouseOut="showHelpMessage('', 'none', this.id)" id="ccc${v.count}">建立事項</td>						
				</tr>
			</table>
			</div>
			
			<table width="100%">
				<tr>
					<td colspan="2">
					<%@ include file="add.jsp"%>
					</td>
				</tr>
			</table>
			
			</td>
		</tr>
		</table>
		
		<c:if test="${!empty m.cs}">
		<c:forEach items="${m.cs}" var="cs">
		<table width="99%" class="hairLineTable">
		<tr>
			<td class="hairLineTdF">
			
			<%@ include file="listContent.jsp"%>
			
			
			</td>
		</tr>
		</table>
		</c:forEach>
		</c:if>
		
		
		
		</td>
	</tr>
</table>

