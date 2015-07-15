<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>


<table width="400">
	<tr>
		<td width="100" nowrap>
		
		<table width="99%" class="hairLineTable" alngn="center">
			<tr height="33">
				<td class="hairLineTdF" align="center">
				
				<table cellpadding="0" cellspacing="0" align="center">
					<tr>
						<td><img src="images/icon/reverse_blue.gif"/></td>
						<td><a href="../Calendar/PubManager.do?requestCount=dec">前一個月</a></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		
		</td>
		<td >
		
		
		<table width="99%" class="hairLineTable" alngn="center">
			<tr height="33">
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/printer.gif" />				
				</td>
				<td class="hairLineTdF" align="center">
				<a href="#">${myMonth}月行事曆</a>
				</td>
			</tr>
		</table>
		
		
		</td>
		<td width="100" nowrap>
		
		<table width="98%" class="hairLineTable" alngn="center">
			<tr height="33">
				<td class="hairLineTdF" align="center">
				
				<table cellpadding="0" cellspacing="0" align="center">
					<tr>
						<td><a href="../Calendar/PubManager.do?requestCount=add">後一個月</a></td>
						<td><img src="images/icon/play_blue.gif"/></td>
					</tr>
				</table>
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>

<c:forEach items="${myCalendar}" var="m" varStatus="v">
<table width="99%" class="hairLineTable" alngn="center">
<tr>
	<td class="hairLineTdF">
	
	<table>
		<tr>			
			<c:if test="${fn:length(m.cs)>0}">
			<td><img src="images/icon/tag/tag_red.gif"/></td>
			</c:if>
			<c:if test="${fn:length(m.cs)<1}">
			<td><img src="images/icon/tag/tag_green.gif"/></td>
			</c:if>
			
			<td nowrap nowrap style="cursor:pointer;" onClick="showObj('old${v.count}');">${m.date}</td>
			<td nowrap valign="bottom" style="cursor:pointer;" onClick="showObj('old${v.count}');">		
			<font <c:if test="${m.week=='六'||m.week=='日'}">color="red"</c:if> size="1">週${m.week}</font>
			</td>
			
			
			<c:if test="${fn:length(m.cs)>0}">
			
			
			<td style="cursor:pointer;" onClick="showObj('old${v.count}');" nowrap>			
			<c:if test="${fn:length(m.cs)<=4}">
			<c:forEach begin="1" end="${fn:length(m.cs)}">
			<img src="images/icon/bell.gif"/>
			</c:forEach>
			</td>			
			</c:if>
			
			<c:if test="${fn:length(m.cs)>4}">
			<img src="images/icon/bell_error.gif"/>
			<td nowrap width="100" style="cursor:pointer;" onClick="showObj('old${v.count}');"> <b>${fn:length(m.cs)}</b>件事</td>
			</c:if>
			
			
			
			
			
			
			</c:if>
			
			<td width="100%"></td>
			<td nowrap><img src="images/icon/plus.gif"/></td>
			<td nowrap style="cursor:pointer;" onClick="showObj('new${v.count}');">建立事項</td>			
		</tr>
	</table>
	
	
	<%@ include file="add.jsp"%>
	<%@ include file="subList.jsp"%>
	
	
	
	
	
	</td>
</tr>
</table>
</c:forEach>
<script>
function checkDel(Oid){
	if(document.getElementById("delOid"+Oid).value==""){
		document.getElementById("delOid"+Oid).value=Oid;
	}else{
		document.getElementById("delOid"+Oid).value="";	
	}
}
</script>

