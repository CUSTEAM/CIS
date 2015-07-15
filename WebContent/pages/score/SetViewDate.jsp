<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Score/SetViewDate" method="post" onsubmit="init()">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/date.gif">
				</td>
				<td align="left">
				&nbsp;成績開放查詢日期設定&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>
<!-- 標題列 end -->
	<tr>
		<td>
			<table width="100%">
				<tr>
					<td>
					
					<table class="hairLineTable" width="99%">
					<tr>
					<td class="hairLineTdF">
					
					<table width="100%" onClick="showInfo('createTime')" style="cursor:pointer;">
						<tr>
							<td>
							
							<table>
								<tr>
									<td width="1"><img src="images/16-icon_calendar-point.gif"></td>
									<td>新增時段</td>
								</tr>
							</table>
							
							
							</td>
						</tr>
						<tr>
							<td>
							
							<table style="display:none;" id="createTime" width="100%">
								<tr >
									<td align="center">
							
									<table cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td><img src="downloads/course/orz.gif"/></td>
											<td></td>
										</tr>
									</table>
										
									</td>
								</tr>
						
								<tr>
									<td align="center">
									<INPUT type="submit" name="method" id="create" onMouseOver="showHelpMessage('學號完整並對應姓名會按照學生目前狀態進行下一步驟,<br>'+
									'若無對應則認定為新生直接進入轉入模式', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"
									value="<bean:message key='Create'/>" class="CourseButton" disabled>
									</td>
									
									
								</tr>
							</table>
							
							
							
						
							
							</td>
						</tr>
					</table>
		
					</td>
					</tr>
					</table>
					
					</td>
				</tr>
			</table>
		
				
		</td>
	</tr>
	
	<tr>
		<td>
		
		
		
		<c:forEach items="${myTime}" var="m">
		
		<table width="100%">
				<tr>
					<td>
					
					<table class="hairLineTable" width="99%">
					<tr>
					<td class="hairLineTdF">
		
		
		
		
		
		
					<table width="100%">
						<tr>
							<td>
							<table>
								<tr>
									<td width="1">
									<img src="images/16-icon_calendar-edit.gif">
									</td>
									<td onClick="showInfo('info${m.departNo}')" style="cursor:pointer;" width="100%" align="left">
							
									點這裡設定包含有
									<c:forEach items="${m.depart}" var="d">
									${d.name},
									</c:forEach>
									通常稱為: ${m.departName}, 也有人叫它 ${m.departNo}							
							
							</td>
								</tr>
							</table>
							</td>
						</tr>
						
						<tr>
							<td id="info${m.departNo}" style="display:none;">
							
							
							
							
							
							
							
							
							
							
							<table class="hairLineTable" width="98%" align="center">
								<tr>
									<td class="hairLineTd">學制</td>
				<td class="hairLineTd">型態</td>
				<td class="hairLineTd">開始日期?</td>
				<td class="hairLineTd">開始時間?</td>
				<td class="hairLineTd">結束日期?</td>
				<td class="hairLineTd">結束時間?</td>
				<td class="hairLineTd">開放瀏覽日期</td>
			</tr>
			
			<c:forEach items="${m.list}" var="a">
			<tr>
				<td class="hairLineTdF">
				
				
		
				<input type="hidden" name="Oid" value="${a.Oid}" />
				${a.c5name}
				
				</td>
				<td class="hairLineTdF">${a.level}</td>
				<td class="hairLineTdF">${a.begin_date}</td>
				<td class="hairLineTdF">${a.begin_time}</td>
				<td class="hairLineTdF">${a.end_date}</td>
				<td class="hairLineTdF">${a.end_time}</td>
				
				<td class="hairLineTdF">
				
				<input type="text" name="view_date" id="view_date${a.Oid}"
				size="12" value="${a.view_date}"
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				onMouseOver="showHelpMessage('點我點我',
				'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"/><img src="images/date.gif">
				
				</td>
			</tr>
			</c:forEach>
			
			
		</table>
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							</td>
						</tr>
						
					</table>
		
		
					
		
		
		
		
					</td>
					</tr>
					</table>
					
					</td>
				</tr>
			</table>
		
		
		
		</c:forEach>
		
		
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit"
			name="method"
			id="save"
			onMouseOver="showHelpMessage('更新開放時間', 'inline', this.id)" 
			onMouseOut="showHelpMessage('', 'none', this.id)"
			value="<bean:message key='Save'/>"
			class="CourseButton">
		</td>
	</tr>

</html:form>
</table>
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
	<tr>
		<td id="ds_calclass"></td>
	</tr>
</table>


<script>
function showInfo(id){
	if(document.getElementById(id).style.display=="none"){
		document.getElementById(id).style.display="inline";
	}else{
		document.getElementById(id).style.display="none";
	}
}
</script>


<%@ include file="/pages/include/MyCalendar.jsp" %>