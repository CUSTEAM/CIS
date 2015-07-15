<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script language="javaScript">
history.go(1);
function makeSure() {
	if (confirm("確定建立註冊檔資料-此作業會將先前建立資料清除?"))
		return true;
	else	
		return false;	
}
</script>

<html:form action="/Registration/MakeOldRegister" method="post" onsubmit="init('報表產生中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left"><tr><td align="left">&nbsp;&nbsp;<img src="images/16-cube-debug.png"></td><td> 註 冊 管 理 系 統 </td></tr></table>');
</script>
	<tr>
		<td>
			<table class="empty-border">
				<tr>
					<td>
						<span id="year">學年學期：
							<html:select property="year" size="1">
	    						<html:options property="years" labelProperty="years" />	    						
	    					</html:select>學年
	    				</span>	
						 第 
						<html:select property="sterm" styleId="stermS">
    						<html:option value="1">1</html:option>
    						<html:option value="2">2</html:option>
    					</html:select>學期&nbsp;&nbsp;
    				</td>    				
				</tr>
				<tr>
					<td>
						<span id="campus">校區:
    						<html:select property="campusCode" styleId="campusCode">  					
    							<html:option value="1">台北</html:option>
    							<html:option value="2">新竹</html:option>
    						</html:select>&nbsp;&nbsp;&nbsp;&nbsp;部別
    						<input type="radio" id="schoolType" name="schoolType" <c:if test="${RegisterForm.map.schoolType == 'D'}">checked</c:if> value="D"/>日間部&nbsp;
    						<input type="radio" id="schoolType" name="schoolType" <c:if test="${RegisterForm.map.schoolType == 'N'}">checked</c:if> value="N"/>進修部&nbsp;
    						<input type="radio" id="schoolType" name="schoolType" <c:if test="${RegisterForm.map.schoolType == 'H'}">checked</c:if> value="H"/>進專學院
    					</span>
					</td>
				</tr>
				
				<tr>
					<td>
	  			   		<table width="100%" id="alert" style="display:none">
							<tr>
								<td><br>
									<div class="modulecontainer filled nomessages">
										<div class="first">
											<span class="first"></span>
											<span class="last"></span>
									</div>
									<div>
										<div>
											<table width="100%">
												<tr>
													<td id="helpMsg">
														&nbsp;&nbsp;<img src="images/24-book-green-message.png">
													</td>
													<td align="left"></td>
													<td width="150">
														<a href="javascript:void(0)" onclick="if(document.getElementById('alert').style.display='none'){document.getElementById('alert').style.display='none';}">
														<img src="images/16-tag-check.png" border="0"><font size="1">隱藏說明</font></a>
													</td>
												</tr>
											</table>
										</div>
									</div>
									<div class="last">
										<span class="first"></span>
										<span class="last"></span>
									</div><br>
								</td>
							</tr>
						</table>
					</span></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center" class="fullColorTable">
			<table align="center">
				<tr>
					<td>
						<html:submit property="method" styleClass="CourseButton"><bean:message key="course.onlineAddRemoveCourse.query" bundle="COU"/></html:submit>&nbsp;
						<html:submit property="method" styleClass="CourseButton" onclick="return makeSure();"><bean:message key="createReg" /></html:submit>
					</td>					
				</tr>
			</table>
		</td>
	</tr>	
	
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${registerList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="" class="center" >
					<script>generateCheckbox("${row.oid}", "RegisterInfo");</script>
				</display:column>
				<display:column title="姓名" property="studentName" sortable="true" class="center" />
				<display:column title="真實班級代碼" property="realClassNo" sortable="true" class="center" />
				<display:column title="真實學號" property="realStudentNo" sortable="false" class="center" />
				<display:column title="虛擬班級代碼" property="virClassNo" sortable="false" class="center" />
				<display:column title="虛擬學號" property="virStudentNo" sortable="false" class="center" />
				<display:column title="學雜費金額" property="tuitionFee" sortable="false" class="center" />
				<display:column title="已繳學雜費金額" property="tuitionAmount" sortable="false" class="center" />
				<display:column title="代辦費金額" property="agencyFee" sortable="false" class="center" />
				<display:column title="已繳代辦費金額" property="agencyAmount" sortable="false" class="center" />					
			</display:table>
			
    		</td>
	</tr>
	
</html:form>
</table>   
