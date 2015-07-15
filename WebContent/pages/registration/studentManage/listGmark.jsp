<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<html:form action="/Registration/GmarkManager" method="post" onsubmit="init('系統處理中...')">
<tr>
		<td>
		
		
			
			<table class="hairLineTable" width="99%">
			<tr>
			<td class="hairLineTdF">
				
			<table width="100%">
				<tr>
					<td width="1">
					<img id="folder_blue" src="images/folder_blue.png" style="display:inline;"><img id="folder_blue_open" src="images/folder_blue_open.png" style="display:none;">
					</td>
					<td width="100%" onClick="folderBlue(), showInfo('Hinfo')" style="cursor:pointer;" nowrap>
					歷年備註
					</td>
				</tr>
<script>
function folderBlue(){
	
	if(document.getElementById("folder_blue").style.display=="inline"){
		document.getElementById("folder_blue").style.display="none";
		document.getElementById("folder_blue_open").style.display="inline";
	}else{
		document.getElementById("folder_blue").style.display="inline";
		document.getElementById("folder_blue_open").style.display="none";
	}

}

function clearGmarkOid(id){
	if(document.getElementById(id).value==""){
		document.getElementById(id).value="d";
	}else{
		document.getElementById(id).value="";	
	}

}
</script>
				<tr id="Hinfo" style="display:none">
					<td colspan="2">
						
					<table width="100%">
						<tr>
							<td>
							
							<c:forEach items="${myGmark}" var="myG">
							<table class="hairLineTable" width="99%">
								<tr>
									<td align="center" class="hairLineTdF" nowrap>
										<img src="images/comment_delete.gif" />
										<input type="checkBox" onClick="clearGmarkOid('gmark_delete${myG.Oid}', '${myG.Oid}')" />
									</td>

									
									<td class="hairLineTd">
									<input type="hidden" name="gmark_delete" size="1" id="gmark_delete${myG.Oid}" />
									<input type="hidden" name="gmark_oid" size="2" value="${myG.Oid}" />
										<input type="text" name="gmark_school_year" id="gmark_school_year${myG.Oid}" size="2" value="${myG.school_year}" 
										onMouseOver="showHelpMessage('既存的事件', 'inline', this.id)"
										onMouseOut="showHelpMessage('', 'none', this.id)"/><select 
										name="gmark_school_term">
										<option value="1" <c:if test="${myG.school_term=='1'}">selected</c:if>>第1學期</option>
										<option value="2" <c:if test="${myG.school_term=='2'}">selected</c:if>>第2學期</option>
										</select>
									</td>
									<td class="hairLineTdF">
										
										<select name="gmark_occur_status">
										<option <c:if test="${myG.occur_status==''}">selected</c:if> value="">狀態</option>
										<c:forEach items="${occur_status}" var="i">
											<option <c:if test="${myG.occur_status==i.idno}">selected</c:if> value="${i.idno}">${i.name}</option>
										</c:forEach>
										</select>
									</td>
									<td class="hairLineTd">
										<select name="gmark_cause">
										<option value="">未選擇原因</option>
										<c:forEach items="${cause}" var="c">
										<option <c:if test="${myG.occur_cause==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
										</c:forEach>
										</select>
									</td>
									<td class="hairLineTdF" width="40" align="center" nowrap>備註</td>
									<td class="hairLineTd" width="100%">
										<input type="text" name="gmark_remark" id="gmark_remark${myG.Oid}" value="${myG.remark}"
										onMouseOver="showHelpMessage('可以手動變更文字註記', 'inline', this.id)" style="width:100%;"
										onMouseOut="showHelpMessage('', 'none', this.id)"/>
									</td>
									
								</tr>
							</table>
							</c:forEach>
							
							<table class="hairLineTable" width="99%">
								<tr>
									<td align="center" class="hairLineTdF" nowrap>
										<img src="images/comment_add.gif" />
										<input type="checkBox" disabled onClick="clearGmarkOid('gmark_delete${myG.Oid}', '${myG.Oid}')" />
									</td>

									
									<td class="hairLineTd">
									<input type="hidden" name="gmark_delete" size="1" id="gmark_delete${myG.Oid}" />
									<input type="hidden" name="gmark_oid" size="2" value="${myG.Oid}" />
										<input type="text" name="gmark_school_year" size="2" id="newMark"
										onMouseOver="showHelpMessage('學年度請用數字半形', 'inline', this.id)"
										onMouseOut="showHelpMessage('', 'none', this.id)"/><select 
										name="gmark_school_term">
										<option value="1">第1學期</option>
										<option value="2">第2學期</option>
										</select>
									</td>
									<td class="hairLineTdF">
										
										<select name="gmark_occur_status">
										<option value="">狀態</option>
										<c:forEach items="${occur_status}" var="i">
											<option value="${i.idno}">${i.name}</option>
										</c:forEach>
										</select>
									</td>
									<td class="hairLineTd">
										<select name="gmark_cause">
										<option value="">未選擇原因</option>
										<c:forEach items="${cause}" var="c">
										<option value="${c.idno}">${c.name}</option>
										</c:forEach>
										</select>
									</td>
									<td class="hairLineTdF" align="center" nowrap>備註</td>
									<td class="hairLineTd" width="100%">
										<input type="text" name="gmark_remark" id="addRemark" value=""
										onMouseOver="showHelpMessage('可以手動變更文字註記', 'inline', this.id)" style="width:100%;"
										onMouseOut="showHelpMessage('', 'none', this.id)"/>
									</td>
									
								</tr>
							</table>						
														
							</td>
						</tr>
					</table>
					<INPUT type="submit" name="method" value="<bean:message key='EditGmark' bundle="REG"/>" class="CourseButton" id="EditGmark"
					onMouseOver="showHelpMessage('刪除有勾選的欄位, 並儲存上列所有編輯的資料', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)">
					</td>
				</tr>
					
			</table>
			
			</td>
			</tr>
			</table>
		
		</td>
	</tr>
</html:form>