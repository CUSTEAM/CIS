<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>


<table class="hairlineTable" width="99%">

	
	<tr>
		<td class="fullColorTablePad" onClick="showObj('ViewMsg')" style="cursor:pointer;">		
		<div style="float:left; padding:2 5 0 0;">		
		<img src="images/leaf.gif"/>
		</div>
		<div nowrap style="float:left;" style="float:left; padding:2 5 0 0;"><font class="gray_15">訊息</font></div>	
		</td>
	</tr>
	

		
	<tr>
		<td class="hairLineTd" >
		
		<div style="display:inline;" id="ViewMsg">
		<table width="99%" class="hairLineTable">			
			<c:forEach items="${ViewMsg}" var="v" varStatus="c" begin="0" end="14">
			<tr>			
				<td class="hairLineTdF">				
				<table onClick="showObj('content${v.Oid}');" style="cursor:pointer;">
					<tr>
						<td>
						<c:if test="${v.Icon==null||v.Icon==''}"><img src="images/icon_info.gif"/></c:if>
						<c:if test="${v.Icon!=null&&v.Icon!=''}"><img src="images/${v.Icon}"/></c:if>
						</td>						
						<td>${v.Label}</td>						
						<td>-</td>						
						<td>
						<c:if test="${v.title==null||v.title==''}">無標題訊息</c:if>
						<c:if test="${v.title!=null&&v.title!=''}">${v.title}</c:if>
						</td>
						
						<td>發佈者</td>
						<td>${v.Sender}</td>
						<td>發佈期間</td>
						<td>${v.StartDate}</td>
						<td>${v.DueDate}</td>
					</tr>
				</table>
				<table id="content${v.Oid}" style="display:none;">
					<tr>
						<td>
						${v.Content}
						
						</td>
					</tr>
				</table>
				
				</td>
			</tr>
			</c:forEach>			
		</table>
		
		
		<table align="right">
			<tr>
				<td><img src="images/icon/find.gif" /></td>
				<td onClick="showObj('allMsg');" style="cursor:pointer;"><b>瀏覽更多訊息</b></td>
			</tr>
		</table>
		<br>
		
		<table width="99%" class="hairLineTable" id="allMsg" style="display:none;">			
			<c:forEach items="${ViewMsg}" var="v" varStatus="c" begin="15">
			<tr>			
				<td class="hairLineTdF">				
				<table onClick="showObj('content${v.Oid}');" style="cursor:pointer;">
					<tr>
						<td>
						<c:if test="${v.Icon==null||v.Icon==''}"><img src="images/icon_info.gif"/></c:if>
						<c:if test="${v.Icon!=null&&v.Icon!=''}"><img src="images/${v.Icon}"/></c:if>
						</td>						
						<td>${v.Label}</td>						
						<td>-</td>						
						<td>
						<c:if test="${v.title==null||v.title==''}">無標題訊息</c:if>
						<c:if test="${v.title!=null&&v.title!=''}">${v.title}</c:if>
						</td>
						
						<td>發佈者</td>
						<td>${v.Sender}</td>
						<td>發佈期間</td>
						<td>${v.StartDate}</td>
						<td>${v.DueDate}</td>
					</tr>
				</table>
				
				<table id="content${v.Oid}" style="display:none;">
					<tr>
						<td>
						${v.Content}
						
						</td>
					</tr>
				</table>
				
				
				</td>
			</tr>
			</c:forEach>			
		</table>
		
		</div>
		
		</td>
	</tr>
	
	
	
	
	
	

</table>