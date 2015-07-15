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
					
					<tr>
						<td>					
						<img src="images/user_delete.gif">
						</td>
						<td>				
						歡迎 ${aEmpl.cname}同仁				
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
						<td align="right">姓名:</td>
						<td>${aEmpl.cname}</td>
					</tr>
					<tr>
						<td align="right">性別:</td>
						<td>${aEmpl.sex}</td>
					</tr>
					<tr>
						<td align="right">兼任導師:</td>
						<td>${aEmpl.Tutor}</td>
					</tr>
					<tr>
						<td align="right">戶地:</td>
						<td>${aEmpl.pzip}${aEmpl.paddr}</td>
					</tr>
					<tr>
						<td align="right">居地:</td>
						<td>${aEmpl.czip}${aEmpl.caddr}</td>
					</tr>
					<tr>
						<td align="right">離職原因:</td>
						<td>${aEmpl.StatusCause}</td>
					</tr>
					<tr>
						<td align="right">本校職稱:</td>
						<td>${aEmpl.sname}</td>
					</tr>
					<tr>
						<td align="right">任職日期:</td>
						<td>${aEmpl.StartDate}</td>
					</tr>
					<tr>
						<td align="right">任教日期:</td>
						<td>${aEmpl.TeachStartDate}</td>
					</tr>
					
					<tr>
						<td align="right">兼任職位:</td>
						<td>${aEmpl.Director}</td>
					</tr>
					<tr>
						<td align="right">任教日期:</td>
						<td>${aEmpl.TeachStartDate}</td>
					</tr>
					<tr>
						<td align="right">保險證號:</td>
						<td>${aEmpl.insno}</td>
					</tr>
					<tr>
						<td align="right">職級代碼:</td>
						<td>${aEmpl.pcode}</td>
					</tr>
					<tr>
						<td align="right">學術單位:</td>
						<td>${aEmpl.unit}</td>
					</tr>
					<tr>
						<td align="right">行政單位:</td>
						<td>${aEmpl.unit_module}</td>
					</tr>
					<tr>
						<td align="right">任職狀態:</td>
						<td>${aEmpl.Status}</td>
					</tr>
					<tr>
						<td align="right">員工分類:</td>
						<td>${aEmpl.category}</td>
					</tr>
					<tr>
						<td align="right">離職日期:</td>
						<td>${aEmpl.EndDate}</td>
					</tr>
					<tr>
						<td align="right">行動電話:</td>
						<td>${aEmpl.CellPhone}</td>
					</tr>
					<tr>
						<td align="right">電子郵件:</td>
						<td>${aEmpl.Email}</td>
					</tr>
					
					<tr>
						<td align="right">學歷:</td>
						<td>${aEmpl.Degree}</td>
					</tr>
					<tr>
						<td align="right">英文姓名:</td>
						<td>${aEmpl.ename}</td>
					</tr>
					<tr>
						<td align="right">生日:</td>
						<td>${aEmpl.bdate}</td>
					</tr>
					<tr>
						<td align="right">備註:</td>
						<td>${aEmpl.Remark}</td>
					</tr>
					<tr>
						<td align="right">身分證號:</td>
						<td>${aEmpl.idno}</td>
					</tr>
					<tr>
						<td align="right">電話:</td>
						<td>${aEmpl.telephone}</td>
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
						<img src="images/key.png">
						</td>
						<td>												
						準備建立帳號資料
						</td>
					</tr>
					<tr>
						<td colspan="2">
						帳號: ${aEmpl.idno}<br>
						密碼: ${aEmpl.password}
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
						<img src="images/user_delete.gif">
						</td>
						<td>				
						開課資料
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
						<img src="images/user_delete.gif">
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
	
	<tr height="30">
		<td class="fullColorTable" align="center" colspan="2">
		<INPUT type="submit"
						   name="method"
						   id="continue"
						   onMouseOver="showHelpMessage('確定新增', 'inline', this.id)" 
						   onMouseOut="showHelpMessage('', 'none', this.id)"
						   value="<bean:message key='NewEmplConfirm'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method" id="Cancel"
													   value="<bean:message
													   key='Cancel'/>"
													   class="CourseButton" 
													   onMouseOver="showHelpMessage('取消所有變更', 'inline', this.id)" 
													   onMouseOut="showHelpMessage('', 'none', this.id)" />
		</td>
	</tr>
	
</table>

