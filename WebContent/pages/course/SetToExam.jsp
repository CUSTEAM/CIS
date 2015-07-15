<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<html:form action="/Course/CheckOut" method="post" onsubmit="init('設定會考科目中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<script>
			generateTableBanner('<table align="left">'+
									'<tr>'+
										'<td align="left">&nbsp;&nbsp;<img src="images/16-em-pencil.png"></td>'+
										'<td>設定會考科目</td>'+
									'</tr>'+
								'</table>');
		</script>
	<tr>
		<td>
		
		<table class="empty-border">
			<tr>
				<td>開課學期: 第 <select name="sterm">
    						<option value="1" <c:if test="${CheckOutForm.map.sterm=='1'}" > selected</c:if>>1</option>
    						<option value="2" <c:if test="${CheckOutForm.map.sterm=='2'}" > selected</c:if>>2</option>
    				</select> 學期 
    				
    				
    			
    			</td>				
    							
    							
    							
    							 
			</tr>
			<tr>
				<td><bean:message key="OpenCourse.label.classNumber" bundle="COU"/>: 
				   <c:set var="campusSel" value="${CheckOutForm.map.campusInCharge2}"/>
	  			   <c:set var="schoolSel" value="${CheckOutForm.map.schoolInCharge2}"/>
	  			   <c:set var="deptSel"   value="${CheckOutForm.map.deptInCharge2}"/>
	  			   <c:set var="classSel"  value="${CheckOutForm.map.classInCharge2}"/>
	  			   <c:set var="classLess"  value="${CheckOutForm.map.classLess}"/>
	  			   <%@include file="/pages/include/ClassSelect5.jsp"%>
	  			   
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
											<td align="left">
											
											</td>
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
								</div>
								</div>	
								<br>
							</td>
						</tr>
					</table>
	  			   
	  			   
				</td>
			</tr>
			
		</table>		
		</td>
	</tr>
	<tr>
	<td align="center" class="fullColorTable">
		<INPUT type="submit" name="method" value="<bean:message key='Query'/>" class="CourseButton" disabled>
	</td>
	</tr>	
		
</html:form>
</table>