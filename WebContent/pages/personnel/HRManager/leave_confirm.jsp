<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="2">
		
		<table width="100%">
			<tr>
				<td>
			
				<div class="modulecontainer filled nomessages">
				<div class="first">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				<div>
				<div>
		
				<table>
					<c:if test="${aEmpl.Status=='9'}">
					<tr>
						<td>					
						<img src="images/user_delete.gif">
						</td>
						<td>				
						${aEmpl.cname}準備離職
						</td>
					</tr>
					</c:if>
					
					<c:if test="${aEmpl.Status!='9'}">
					<tr>
						<td>					
						<img src="images/user_edit.gif">
						</td>
						<td>					
						${aEmpl.cname}準備異動資料${aEmpl.image}
						</td>
					</tr>
					</c:if>
					
				</table>
		
				</div>
				</div>
				<div class="last">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				</div>
				
				</td>
			</tr>
		</table>		
		
		</td>
	</tr>
	
	<tr>
		<td width="50%" valign="top">
		
		<table width="100%">
			<tr>
				<td>
			
				<div class="modulecontainer filled nomessages">
				<div class="first">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				<div>
				<div>
				
				<table>
					<tr>
						<td>
						<img src="images/overlays.png">
						</td>
						<td>
						基本資料
						</td>
					</tr>
				</table>
		
				<table class="hairLineTable" width="98%">
					<tr>
						<td class="hairLineTd" align="center">屬性</td>
						<td class="hairLineTd" align="center">值</td>
					</tr>
					<tr>
						<td align="right" class="hairLineTdF">姓名:</td>
						<td class="hairLineTdF">${aEmpl.cname}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">性別:</td>
						<td class="hairLineTdF">${aEmpl.sex}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right" nowrap>兼任導師:</td>
						<td class="hairLineTdF">${aEmpl.Tutor}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">戶籍地址:</td>
						<td class="hairLineTdF">${aEmpl.pzip}${aEmpl.paddr}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">現居地址:</td>
						<td class="hairLineTdF">${aEmpl.czip}${aEmpl.caddr}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">離職原因:</td>
						<td class="hairLineTdF">${aEmpl.StatusCause}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">本校職稱:</td>
						<td class="hairLineTdF">${aEmpl.sname}</td>
					</tr>
					
					<tr>
						<td class="hairLineTdF" align="right">任教日期:</td>
						<td class="hairLineTdF">${aEmpl.TeachStartDate}</td>
					</tr>
					
					<tr>
						<td class="hairLineTdF" align="right">兼任職位:</td>
						<td class="hairLineTdF">${aEmpl.Director}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">任教日期:</td>
						<td class="hairLineTdF">${aEmpl.TeachStartDate}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">保險證號:</td>
						<td class="hairLineTdF">${aEmpl.insno}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">職級代碼:</td>
						<td class="hairLineTdF">${aEmpl.pcode}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">學術單位:</td>
						<td class="hairLineTdF">${aEmpl.unit}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">行政單位:</td>
						<td class="hairLineTdF">${aEmpl.unit_module}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">任職狀態:</td>
						<td class="hairLineTdF">${aEmpl.Status}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">員工分類:</td>
						<td class="hairLineTdF">${aEmpl.category}</td>
					</tr>
					
					<tr>
						<td class="hairLineTdF" align="right">任職日期:</td>
						<td class="hairLineTdF">${aEmpl.StartDate}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">離職日期:</td>
						<td class="hairLineTdF">${aEmpl.EndDate}</td>
					</tr>
					
					<tr>
						<td class="hairLineTdF" align="right">行動電話:</td>
						<td class="hairLineTdF">${aEmpl.CellPhone}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">電子郵件:</td>
						<td class="hairLineTdF">${aEmpl.Email}</td>
					</tr>
					
					<tr>
						<td class="hairLineTdF" align="right" >學歷:</td>
						<td class="hairLineTdF">${aEmpl.Degree}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">英文姓名:</td>
						<td class="hairLineTdF">${aEmpl.ename}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">生日:</td>
						<td class="hairLineTdF">${aEmpl.bdate}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">備註:</td>
						<td class="hairLineTdF">${aEmpl.Remark}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">身分證號:</td>
						<td class="hairLineTdF">${aEmpl.idno}</td>
					</tr>
					<tr>
						<td class="hairLineTdF" align="right">電話:</td>
						<td class="hairLineTdF">${aEmpl.telephone}</td>
					</tr>
					
				</table>
		
				</div>
				</div>
				<div class="last">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				</div>
				
				</td>
			</tr>
		</table>		
		
		</td>
		<td valign="top">
		
		<c:if test="${aEmpl.Status=='9'}">
		<table width="100%">
			<tr>
				<td>
			
				<div class="modulecontainer filled nomessages">
				<div class="first">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				<div>
				<div>		
		
				<table>
					<tr>
						<td width="1">					
						<img src="images/key.png">
						</td>
						<td align="left" width="100%">												
						即將刪除帳號資料
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						<table class="hairLineTable">
							<tr>
								<td class="hairLineTd">帳號</td>
								<td class="hairLineTd">密碼</td>
							</tr>
							<tr>
								<td class="hairLineTdF"><input type="text" value="${myAccount.username}" disabled></td>
								<td class="hairLineTdF"><input type="password" value="${myAccount.password}" disabled></td>
							</tr>
						</table>
												
						</td>
					</tr>
				</table>		
		
				</div>
				</div>
				<div class="last">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				</div>
				
				</td>
			</tr>
		</table>
		</c:if>
		
		
		
		
		
		
		
		
		
		<c:if test="${aEmpl.Status=='9'}">
		<table width="100%">
			<tr>
				<td>
			
				<div class="modulecontainer filled nomessages">
				<div class="first">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				<div>
				<div>
		
		
				<table>
					<tr>
						<td width="1">					
						<img src="images/16-icon_calendar-point.gif">
						</td>
						<td align="left" width="100%">				
						開課資料
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						<table class="hairLineTable" width="98%">
							<tr>
								<td class="hairLineTd">學期</td>
								<td class="hairLineTd">開課班級</td>
								<td class="hairLineTd">課程名稱</td>
							</tr>
						<c:forEach items="${myCs}" var="c">
							<tr>
								<td class="hairLineTdF">${c.Sterm}</td>
								<td class="hairLineTdF">${c.ClassName}</td>
								<td class="hairLineTdF">${c.chi_name}</td>
							</tr>
						
						</c:forEach>
						</table>
						
						</td>
					</tr>
				</table>
		
		
		
				</div>
				</div>
				<div class="last">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				</div>
				
				</td>
			</tr>
		</table>
		</c:if>
		
		
		
		
		
		
		
		
		
		
		<c:if test="${aEmpl.Status=='9'}">
		<table width="100%">
			<tr>
				<td>
			
				<div class="modulecontainer filled nomessages">
				<div class="first">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				<div>
				<div>
		
		
				<table>
					<tr>
						<td>					
						<img src="images/24-book-green-message.png">
						</td>
						<td>				
						借書資料
						</td>
					</tr>
				</table>
		
		
		
				</div>
				</div>
				<div class="last">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				</div>
				
				</td>
			</tr>
			
			
			
			
			
			
		</table>
		</c:if>
		
		
		
		
		
		
		
		
		</td>
	</tr>
	
	<!-- 若有必要留記錄 -->
	<c:if test="${oldEmpl!=null}">
	<tr>
		<td colspan="2">
		
		
		<table width="100%">
			<tr>
				<td>
		
				<div class="modulecontainer filled nomessages">
				<div class="first">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				<div>
				<div>
				
				
				
				
				
				<table>
					<tr>
						<td>
						<img src="images/user_comment.gif">
						</td>
						<td>
						準備新增歷年記錄
						</td>
					</tr>
				</table>
				
				
				
				<table class="hairLineTable" width="98%">
						<tr>
							<!--td align="center" width="30" class="hairLineTdF"><img src="images/tag_orange.gif"></td-->
							<td class="hairLineTdF">員工分類</td>
							<td class="hairLineTdF">單位</td>
							<td class="hairLineTdF">標準職稱</td>
							<td class="hairLineTdF">本校職稱</td>	
							<td class="hairLineTdF">兼任導師</td>
							<td class="hairLineTdF">學歷</td>
							<td class="hairLineTdF">期間</td>
						</tr>
						
						<tr>
							<!--td align="center" width="30" class="hairLineTdF"><img src="images/tag_orange.gif"></td-->
							<td class="hairLineTd">
							<select disabled>
								<c:forEach items="${allCategory}" var="c">
								<option <c:if test="${oldEmpl.category==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							
							
							
							</td>
							<td class="hairLineTd">										
							<select disabled>
								<option value=""></option>
								<c:forEach items="${allUnit}" var="c">
								<option <c:if test="${oldEmpl.unit==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>										
							</td>
							
							<td class="hairLineTd" nowrap>										
							<select disabled>
								<option value=""></option>
								<c:forEach items="${allPcode}" var="c">
								<option <c:if test="${oldEmpl.pcode==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select><select disabled>
								<option value=""></option>
								<c:forEach items="${allDirector}" var="c">
								<option <c:if test="${oldEmpl.Director==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							</td>
							<td class="hairLineTd">${oldEmpl.sname}</td>
							
							<td class="hairLineTd">
							<select disabled>
								<option value=""></option>
								<c:forEach items="${allTutor}" var="c">
								<option <c:if test="${oldEmpl.Tutor==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							
							</td>
							<td class="hairLineTd">
							
							<select disabled>
								<option value=""></option>
								<c:forEach items="${allDegree}" var="c">
								<option <c:if test="${oldEmpl.Degree==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							
							
							</td>
							<td class="hairLineTd">
							${oldEmpl.StartDate}~${oldEmpl.EndDate}</td>
						</tr>
						<tr>
							<td colspan="7" class="hairLineTdF">
							
							<textarea rows="1" cols="75">${oldEmpl.Remark}</textarea>
							</td>
						</tr>
													
					</table>
				
				
				
				
				
				
				
				
				
				
				</div>
				</div>
				<div class="last">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				</div>
				
				</td>
			</tr>			
		</table>
		
		</td>
	</tr>
	</c:if>
	
	
	
	
	
	
	
	
	
	
	
<!-- 現存歷年記錄 -->	
<c:if test="${hist!=null}">	
	<tr>
		<td colspan="2">
		
		
		
		<table width="100%">
			<tr>
				<td>
		
				<div class="modulecontainer filled nomessages">
				<div class="first">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				<div>
				<div>
				
				
				
				
				
				
				<table>
					<tr>
						<td>
						<img src="images/user_comment.gif">
						</td>
						<td>
						歷年記錄
						</td>
					</tr>
				</table>
				
<c:forEach items="${hist}" var="h">
<c:if test="${h.saveStartDate!=''&&h.saveEndDate!=''}">
				<table class="hairLineTable" width="98%">
						<tr>
							<!--td align="center" width="30" class="hairLineTdF"><img src="images/tag_orange.gif"></td-->
							<td class="hairLineTdF">員工分類</td>
							<td class="hairLineTdF">單位<input type="text" size="4" class="smallInput" value="${h.saveOid}" /></td>
							<td class="hairLineTdF">標準職稱</td>
							<td class="hairLineTdF">本校職稱</td>	
							<td class="hairLineTdF">兼任導師</td>
							<td class="hairLineTdF">學歷</td>
							<td class="hairLineTdF">期間</td>
						</tr>
						
						<tr>
							<!--td align="center" width="30" class="hairLineTdF"><img src="images/tag_orange.gif"></td-->
							<td class="hairLineTd">
							<select disabled class="smallInput">
								<c:forEach items="${allCategory}" var="c">
								<option <c:if test="${h.saveCategory==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							
							
							
							</td>
							<td class="hairLineTd">										
							<select disabled class="smallInput">
								<option value=""></option>
								<c:forEach items="${allUnit}" var="c">
								<option <c:if test="${h.saveUnit==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>										
							</td>
							
							<td class="hairLineTd" nowrap>										
							<select disabled class="smallInput">
								<option value=""></option>
								<c:forEach items="${allPcode}" var="c">
								<option <c:if test="${h.savePcode==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select><select disabled class="smallInput">
								<option value=""></option>
								<c:forEach items="${allDirector}" var="c">
								<option <c:if test="${h.saveDirector==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							</td>
							<td class="hairLineTd"><font size="1">${h.saveSname}</font></td>
							
							<td class="hairLineTd">
							<select disabled class="smallInput">
								<option value=""></option>
								<c:forEach items="${allTutor}" var="c">
								<option <c:if test="${h.saveTutor==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							
							</td>
							<td class="hairLineTd">
							
							<select disabled class="smallInput">
								<option value=""></option>
								<c:forEach items="${allDegree}" var="c">
								<option <c:if test="${h.saveDegree==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							
							
							</td>
							<td class="hairLineTd">
							<font size="1">${h.saveStartDate}~${h.saveEndDate}</font></td>
						</tr>
						<tr>
							<td colspan="7" class="hairLineTdF">
							
							<textarea rows="1" cols="75">${h.saveRemark}</textarea>
							</td>
						</tr>
													
					</table>
</c:if>
</c:forEach>
				
				
				
				
				
				
				
				
				
				</div>
				</div>
				<div class="last">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				</div>
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</c:if>	
	
	
	
	
	
	
	
	
	
	
	<tr height="30">
		<td class="fullColorTable" align="center" colspan="2">
		<INPUT type="submit"
			   name="method"
			   id="continue"
			   onMouseOver="showHelpMessage('進行確認', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)"
			   value="<bean:message key='EditEmplConfirm'/>"
			   class="gSubmit">
						   
						   
		<INPUT type="submit"
			   name="method" id="Cancel"
			   value="<bean:message
			   key='Cancel'/>"
			   class="gCancle" 
			   onMouseOver="showHelpMessage('取消所有變更', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
		</td>
	</tr>
	
</table>

