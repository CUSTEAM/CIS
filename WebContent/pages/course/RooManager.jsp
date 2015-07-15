<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/SetRoom" method="post" onsubmit="init('處理中, 請稍後')">
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/building_go.gif">
				</td>
				<td align="left">
				&nbsp;教室管理&nbsp;<input type="hidden" name="searchType" id="searchType" value="sim" />
				</td>
			</tr>
		</table>
		</td>
	</tr>
	
	
	
	<c:if test="${aRoom==null}">	
	<tr>
		<td>
		<%@ include file="RoomManager/search.jsp"%>
		</td>
	</tr>	
	<c:if test="${rooms!=null}">
	<tr>
		<td>
		<%@ include file="RoomManager/list.jsp"%>		
		</td>
	</tr>
	
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit" 
		name="method" value="<bean:message key='Details'/>" class="CourseButton"><INPUT type="submit" 
		name="method" value="<bean:message key='Modify'/>" class="CourseButton" disabled><INPUT type="submit"
		name="method" value="<bean:message key='Delete'/>" class="CourseButton" >
		</td>
	</tr>
	</c:if>

</c:if>















<c:if test="${aRoom!=null}">
<tr>
	<td>
	
	<table width="100%">
		<tr>
			<td align="left">
						
						<table>
							<tr>
								<td>
								
									<table class="hairlineTable">
										<tr>
											<td class="hairlineTdF">教室編號</td>
											<td class="hairlineTd">
											<input type="text" size="8" disabled value="${aRoom.room_id}" /></td>
											
											<td class="hairlineTdF">教室名稱</td>
											<td class="hairlineTd">
											<input type="text" size="12" name="name2" id="name2" value="${aRoom.name2}" /><select 
												name="boro">
												<option value="" <c:if test="${aRoom.boro==''}">selected</c:if>>租借選項</option>
												<option value="Y" <c:if test="${aRoom.boro=='Y'}">selected</c:if>>開放租借</option>
												<option value="N" <c:if test="${aRoom.boro=='N'}">selected</c:if>>不可租借</option>
											</select>
											</td>
											<td class="hairlineTdf" align="center" width="30">
											<img src="images/16-exc-mark.gif" />
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
						
						<table>
							<tr>
								<td>
								
								<table class="hairlineTable"">	
									<tr>
										<td class="hairlineTdF">所屬系所</td>
										<td class="hairlineTd">
										<select name="dept" id="dept">
											<option value="">選擇系所</option>
											<c:forEach items="${dept}" var="d">
											<option value="${d.idno}" <c:if test="${aRoom.dept==d.idno}">selected</c:if> >${d.name}</option>
											</c:forEach>
										</select>
										</td>
										</td>
										<td class="hairlineTdF">所在位置</td>
										<td class="hairlineTd">
										<select name="building" id="building">
											<option value="">選擇建築物</option>
											<c:forEach items="${build}" var="b">
											<option value="${b.idno}" <c:if test="${aRoom.building==b.idno}">selected</c:if>>${b.name}</option>
											</c:forEach>
										</select>
										</td>
										</td>
										<td class="hairlineTdf" align="center" width="30">
										<img src="images/icon_component.gif" />
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
						
						<table>
							<tr>
								<td>
								
								<table class="hairlineTable">	
									<tr>
										<td class="hairlineTdF">管理單位</td>
										<td class="hairlineTd">
										<select name="unit">
											<option value="">選擇單位</option>
											<c:forEach items="${unit}" var="u">
											<option value="${u.idno}" <c:if test="${aRoom.unit==u.idno}">selected</c:if>>${u.name}</option>
											</c:forEach>
										</select>
										</td>
										</td>
									
										<td class="hairlineTdF">樓層/容量</td>
										<td class="hairlineTd">
										<select name="floor">
											<option value=""  <c:if test="${aRoom.floor==''}">selected</c:if>selected>幾樓</option>
											<option value="Z" <c:if test="${aRoom.floor=='Z'}">selected</c:if>>B3樓</option>
											<option value="Y" <c:if test="${aRoom.floor=='Y'}">selected</c:if>>B2樓</option>
											<option value="X" <c:if test="${aRoom.floor=='X'}">selected</c:if>>B1樓</option>
											
											<option value="1" <c:if test="${aRoom.floor=='1'}">selected</c:if>>&nbsp;1樓</option>
											<option value="2" <c:if test="${aRoom.floor=='2'}">selected</c:if>>&nbsp;2樓</option>
											<option value="3" <c:if test="${aRoom.floor=='3'}">selected</c:if>>&nbsp;3樓</option>
											<option value="4" <c:if test="${aRoom.floor=='4'}">selected</c:if>>&nbsp;4樓</option>
											<option value="5" <c:if test="${aRoom.floor=='5'}">selected</c:if>>&nbsp;5樓</option>
											<option value="6" <c:if test="${aRoom.floor=='6'}">selected</c:if>>&nbsp;6樓</option>
											<option value="7" <c:if test="${aRoom.floor=='7'}">selected</c:if>>&nbsp;7樓</option>
											<option value="8"<c:if test="${aRoom.floor=='8'}">selected</c:if>>&nbsp;8樓</option>
											<option value="9"<c:if test="${aRoom.floor=='9'}">selected</c:if>>&nbsp;9樓</option>
											<option value="A"<c:if test="${aRoom.floor=='A'}">selected</c:if>>10樓</option>
											<option value="B"<c:if test="${aRoom.floor=='B'}">selected</c:if>>11樓</option>
											<option value="C"<c:if test="${aRoom.floor=='C'}">selected</c:if>>12樓</option>
										</select>
										</td>
										<td class="hairLineTd">
										<input type="text" name="seat" value="${aRoom.seat}" size="2"/>
										</td>
										<td class="hairlineTdf" align="center" width="30">
										<img src="images/icon_component.gif" />
										</td>
									</tr>
								</table>
								
								</td>
							</tr>
						</table>
						
			
			
			
			
			
			
			
			
			
			
			
			<input type="hidden" name="aOid" value="${aRoom.Oid}"/>
			
			</td>
		</tr>
		
		<tr>
			<td>
				
			<FCK:editor instanceName="remark" toolbarSet="Basic"
			basePath="/pages/include/fckeditor">
				<jsp:attribute name="value">${aRoom.remark}	
				</jsp:attribute>
				<jsp:body>
					<FCK:config 
					SkinPath="skins/office2003/"
					ImageBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
					LinkBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
					FlashBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
					ImageUploadURL="/CIS/Simpleuploader?Type=Image" 
					LinkUploadURL="/CIS/Simpleuploader?Type=File" 
					FlashUploadURL="/CIS/Simpleuploader?Type=Flash"/>
				</jsp:body>
			</FCK:editor>
			
			
			
			</td>
		</tr>
		
		
		
	</table>
	
	
	
	
	
	
	
	
	
	
	
	
	
	</td>
</tr>



<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='OK'/>" class="CourseButton" ><INPUT type="submit" 
		name="method" value="<bean:message key='Clear'/>" class="CourseButton" >
		
		
		
		</td>
	</tr>
</c:if>

</html:form>
</table>

<script>
function showSearch(a, b, type){
	object=document.getElementById(a);
	object1=document.getElementById(b);
	
	if(object.style.display=="none"){
		object.style.display="inline";
		object1.style.display="none";
	}else{
		object.style.display="none";
		object1.style.display="inline";
	}
	
	if(document.getElementById("sim").style.display=="inline"){
		document.getElementById("searchType").value="sim";
	}else{
		document.getElementById("searchType").value="exp";
	}
}

</script>