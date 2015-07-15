<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script language="javaScript">
history.go(1);
function makeSure() {
	if (confirm("確定建立應屆畢業生資料-此作業會將先前建立資料清除?"))
		return true;
	else	
		return false;	
}
</script>

<html:form action="/Research/InvestigationMake" method="post" onsubmit="init('報表產生中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left"><tr><td align="left">&nbsp;&nbsp;<img src="images/16-cube-debug.png"></td><td> 建 立 應 屆 畢 業 生 資 料 </td></tr></table>');
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
						<html:submit property="method" styleClass="CourseButton" onclick="return makeSure();"><bean:message key="createInves" /></html:submit>
					</td>					
				</tr>
			</table>
		</td>
	</tr>	
	
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${investigationList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="學號" property="studentNo" sortable="true" class="center" />
				<display:column title="姓名" property="studentName" sortable="true" class="center" />
				<display:column title="班級" property="departClass" sortable="true" class="center" />
				<display:column title="導師" property="tutorName" sortable="true" class="center" />
			</display:table>
    	</td>
	</tr>
	
</html:form>
</table>   
