u;3<!DOCTYPE html PUBLIC"-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ page import="org.apache.struts.Globals" %>
<%@ include file="/taglibs.jsp" %>  
<html>
<head>	
	<script type="text/javascript" src="/CIS/pages/include/classes.js"></script>
	<link href="/CIS/pages/include/style.css" type="text/css" rel="stylesheet">
</head>

<body>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  	<tr>    	
    	<td valign="top">
    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
      			<tr>
        			<td valign="top" id="maincontent"><div>
        				<html:form action="/ClassCourseSearch_guide" method="post">
        					
        						<!--  type="hidden" -->
        						<input type="hidden" id="CampusNo" />
        						<input type="hidden" id="SchoolNo" />
        						<input type="hidden" id="DeptNo" />
        						<input type="hidden" id="Grade" />
        						<input type="hidden" id="ClassNo" />
        						<table>
        							<tr>
        								<td id="CampusAreaTd" align="left" valign="top" class="area">
        									<table>
												<tr>
													<td>
														<b class='xb1'></b><b class='xb2'></b><b class='xb3'></b><b class='xb4'></b>
														<div class='CampusDiv' onClick="document.getElementById('CampusNo').value='1', getClass()">台北</div>
														<b class='xb4'></b><b class='xb3'></b><b class='xb2'></b><b class='xb1'></b>
													</td>
												</tr>
											</table>

											<table>
												<tr>
													<td>
														<b class='xb1'></b><b class='xb2'></b><b class='xb3'></b><b class='xb4'></b>
														<div class='CampusDiv' onClick="document.getElementById('CampusNo').value='2', getClass()">新竹</div>
														<b class='xb4'></b><b class='xb3'></b><b class='xb2'></b><b class='xb1'></b>
													</td>
												</tr>
											</table>
											<table>
												<tr>
													<td>
														<b class='xb1'></b><b class='xb2'></b><b class='xb3'></b><b class='xb4'></b>
														<div class='CampusDiv' onClick="document.getElementById('CampusNo').value='2', getClass()">高雄</div>
														<b class='xb4'></b><b class='xb3'></b><b class='xb2'></b><b class='xb1'></b>
													</td>
												</tr>
											</table>
										</td>
										<td id="SchoolAreaTd" align="left" valign="top" class="area"></td>
										<td id="DeptAreaTd" align="left" valign="top" class="area"></td>
										<td id="GradeAreaTd" align="left" valign="top" class="area"></td>
										<td id="ClassAreaTd" align="left" valign="top" class="area"></td>
										<td id="myShow" style="display: none;">
											<input type="hidden" type="text" name="classInCharge" id="classInCharge" />
											<input style="font-size:15pt" type="submit" name="method" id="method" value="<bean:message key='course.courseSearch.btn.search' bundle="COU" />" class="CourseButton" />
											
										</td>										
									</tr>
									<tr></tr>
								</table>
								<!--
								<div style="display: none;" >
									<form name="SearchToolForm" method="post" action="ClassCourseSearch_guide">
										<input type="hidden" type="text" name="classInCharge" id="classInCharge" />
										<input type="submit" value="查詢" /> 
										<<script>alert(document.getElementById('classInCharge').value);</script>
									</form>
								</div>
								-->
          				</html:form>
          			</td>          			
          		</tr>							
        				<!-- 
        				開課班級：
							<select name="campusInCharge" Style="Font-Size:25pt" onchange="fillSchools();">
								<option value=""><bean:message key="SelCampuses" /></option>
								<c:forEach items="${AllCampuses}" var="campus">
								<option value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</option>	
								</c:forEach>
							</select>
							<select name="schoolInCharge" Style="Font-Size:25pt" onchange="fillDepts();"></select>
							<select name="deptInCharge" Style="Font-Size:25pt" onchange="fillClasses();"></select>
							<select name="classInCharge" Style="Font-Size:25pt"></select>
							<input type="submit" name="method" Style="Font-Size:22pt" value="<bean:message key='course.courseSearch.btn.search' bundle="COU" />" class="CourseButton" />
          				
          				 
 
          				
          				<logic:present name="<%= Globals.ERROR_KEY %>">
		          			<div><font color="red">          				
		          					<html:errors/>          				
		          			</font></div>
		          		</logic:present> 
		          		-->        				

          		<c:if test="${not empty courseList}">
          		
				<tr>
					<td>
					    <font size="+2" color="black">${classFullName}</font>
						<table width="100%" id="table1" cellpadding="5" cellspacing="0" border="1">							
							<tr>
								<th height="30" width="10%" >&nbsp;</th>
								<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status2">
								<th  align="center" ><b><font color="black"><c:out value="${weekdayList[status2.index]}" /></font></b></th>
								</c:forEach>
							</tr>
							<c:if test="${rowsCols['mode'] == 'D'}">
							<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
							<tr>	
								<td align="center"  >
									<b><font color="black"><c:out value="${nodeList[status.index]}" escapeXml="false" /></font><b/>
								</td>	
								<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
								<td width="18%"><strong><font color="black">		
									<span Style="Font-Size:20pt"><c:out value="${courseList[(status1.index * 15) + status.index]['chi_name']}" /></span></strong><br/><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index]['cname']}" />&nbsp;&nbsp;&nbsp;&nbsp;
									<!--<c:out value="${courseList[(status1.index * 15) + status.index]['cscode']}" /><br/>-->
									<!--<c:out value="${courseList[(status1.index * 15) + status.index]['name2']}" /><br/>-->
									<c:out value="${courseList[(status1.index * 15) + status.index]['place']}" />
									<!--<c:out value="${courseList[(status1.index * 15) + status.index]['elearning']}" /><br/></font></strong>-->
								</td>						
								</c:forEach>
							</tr>						
							</c:forEach>
							</c:if>
	
							<c:if test="${rowsCols['mode'] == 'N'}">
							<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
							<tr>	
								<td align="center" >
									<b><font color="black"><c:out value="${nodeList[status.index]}" escapeXml="false" /></font><b/>
								</td>	
								<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
								<td width="15%" ><strong><font color="black">		
									<span Style="Font-Size:20pt"><c:out value="${courseList[(status1.index * 15) + status.index+5]['chi_name']}" /></span></strong><br/><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index+5]['cname']}" />&nbsp;&nbsp;&nbsp;&nbsp;
									<!--<c:out value="${courseList[(status1.index * 15) + status.index]['cscode']}" /><br/>-->
									<!--<c:out value="${courseList[(status1.index * 15) + status.index]['name2']}" /><br/>-->
									<c:out value="${courseList[(status1.index * 15) + status.index+5]['place']}" />
									<!--<c:out value="${courseList[(status1.index * 15) + status.index]['elearning']}" /><br/></font></strong>-->
								</td>						
								</c:forEach>
							</tr>						
							</c:forEach>
							</c:if>
									
							<c:if test="${rowsCols['mode'] == 'H'}">
							<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
							<tr>	
								<td align="center"  >
									<b><font color="black"><c:out value="${nodeList[status.index]}" escapeXml="false" /></font><b/>
								</td>	
								<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
								<td  ><strong><font color="black">		
									<span Style="Font-Size:20pt"><c:out value="${courseList[(status1.index * 15) + status.index]['chi_name']}" /></span></strong><br/><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index]['cname']}" />&nbsp;&nbsp;&nbsp;&nbsp;
									<!--<c:out value="${courseList[(status1.index * 15) + status.index]['cscode']}" /><br/>-->
									<!--<c:out value="${courseList[(status1.index * 15) + status.index]['name2']}" /><br/>-->
									<c:out value="${courseList[(status1.index * 15) + status.index]['place']}" />
									<!--<c:out value="${courseList[(status1.index * 15) + status.index]['elearning']}" /><br/></font></strong>-->
								</td>						
								</c:forEach>
							</tr>						
							</c:forEach>
							</c:if>
							
							<c:if test="${rowsCols['mode'] == 'S'}">
							<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
							<tr>	
								<td align="center">
									<b><font color="black"><c:out value="${nodeList[status.index]}" escapeXml="false" /></font><b/>
								</td>	
								<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
								<td  width="15%" ><strong><font color="black">		
									<span Style="Font-Size:20pt"><c:out value="${courseList[(status1.index * 15) + status.index]['chi_name']}" /></span></strong><br/><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index]['cname']}" />&nbsp;&nbsp;&nbsp;&nbsp;
									<!--<c:out value="${courseList[(status1.index * 15) + status.index]['cscode']}" /><br/>-->
									<!--<c:out value="${courseList[(status1.index * 15) + status.index]['name2']}" /><br/>-->
									<c:out value="${courseList[(status1.index * 15) + status.index]['place']}" />
									<!--<c:out value="${courseList[(status1.index * 15) + status.index]['elearning']}" /><br/></font></strong>-->
								</td>						
								</c:forEach>
							</tr>						
							</c:forEach>
							</c:if>
				
						</table>
					</td>
				</tr>
				</c:if>
			</table>
		</td>
	</tr>
</table>
</body>
</html>

