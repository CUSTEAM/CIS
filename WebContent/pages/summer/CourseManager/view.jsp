<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
	<tr>
		<td>
		<table width="100%">
			<tr>
				<td colspan="2">
					<table width="100%" cellpadding="0" cellspacing="0">
  						<tr>
  							<td width="5%" align="left">
      							<hr noshade size="1" color="cfe69f"/>
    						</td>
    						<td width="1%" nowrap>
      						開課基本資料
    						</td>
    						<td width="99%" align="left">
      						<hr noshade size="1" color="cfe69f"/>
    						</td>
  						</tr>
					</table>
		
				</td>
			</tr>
			<tr>
				<td width="40%">
					<table>
						<tr>
							<td>開課班級:</td>
							<td>
							
							<input type="text" id="departClass" name="departClass"
							 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				 			 value="${sDtime.depart_class}" disabled/>

							<input type="text" name="classLess" id="classLess"
							 value="${sDtime.name}" size="12" disabled/>
							
							
							
							</td>
						</tr>
					
						<tr>
							<td>課程代碼:</td>
							<td>
							
							<input type="text" name="courseNumber" id="cscodeS" size="8"
							 autocomplete="off" style="ime-mode:disabled" autocomplete="off"
							 value="${sDtime.cscode}" disabled />
							 
							<input type="text" 
							 name="courseName" id="csnameS" size="16"
							 value="${sDtime.chi_name}" disabled />
							
							</td>	
						</tr>
					
						<tr>
							<td>教師代碼:</td>
							<td>
							
							<input type="text" name="teacherId" id="techidS" size="8" style="ime-mode:disabled" autocomplete="off"
							 value="${sDtime.techid}"
							 onkeyup="if(this.value.length>2)getAny(this.value, 'techidS', 'technameS', 'empl', 'no')"
							 onclick="this.value='', document.getElementById('technameS').value=''" disabled />

							<input type="text" onkeyup="getAny(this.value, 'technameS', 'techidS', 'empl', 'name')"
							 onkeydown="document.getElementById('Acsname').style.display='none';" autocomplete="off"
							 onclick="this.value='', document.getElementById('techidS').value=''"
							 name="teacherName" id="technameS" size="12" value="${sDtime.cname}" disabled />
							
							</td>	
					</tr>
				</table>
				
				</td>
				<td valign="top">
				<table>
					<tr>
						<td>
						梯次:
						
						</td>
						<td>
						<select name="seqno" disabled>
							<c:forEach items="${sweek}" var="sweek">
    							<option <c:if test="${sDtime.seqno==sweek.seqno}" > selected</c:if> value="${sweek.seqno}">${sweek.seqno}</option>
    						</c:forEach>
    						</select>
						<select name="choseType" disabled>
							<option <c:if test="${sDtime.opt=='1'}" > selected</c:if> value="1">必修</option>
							<option <c:if test="${sDtime.opt=='2'}" > selected</c:if> value="2">選修</option>
							<option <c:if test="${sDtime.opt=='3'}" > selected</c:if> value="3">通識</option>
						</select>
						</td>
					</tr>
					
					<tr>
						<td>
						總時數: 
						</td>
						<td>
						<input type="text" name="thour" size="1" value="${sDtime.thour}" disabled/>
						<input type="text" name="credit" size="1" value="${sDtime.credit}" disabled/> 學分
						</td>
					</tr>
					
					<tr>
						<td>
						上限: 
						</td>
						<td>
						<input type="text" name="" size="1" value="50" disabled/>
						<select name="status" disabled>
							<option <c:if test="${sDtime.status=='0'}" > selected</c:if> value="0">開課</option>
							<option <c:if test="${sDtime.status=='1'}" > selected</c:if> value="1">退費</option>
						</select>
						</td>
					</tr>
				</table>
				
				</td>
			</tr>
			<tr>
			<tr>
				<td colspan="2">
					<table width="100%" cellpadding="0" cellspacing="0">
  						<tr>
  							<td width="5%" align="left">
      							<hr noshade size="1" color="cfe69f"/>
    						</td>
    						<td width="1%" nowrap>
      						上課時間/地點
    						</td>
    						<td width="99%" align="left">
      						<hr noshade size="1" color="cfe69f"/>
    						</td>
  						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
				
				<table>
								<tr>
  									<td>
  									地點&nbsp;&nbsp;
  									</td>
  									<td>
  									<input type="text" name="place" size="4" value="${sDtime.clascode}" disabled/>
  									<img src="images/16-cube-room.png" />
  									</td>
  								</tr>
  								<tr>
  									<td>
  									星期一
  									</td>
  									<td>
  									<input type="text" id="day1" name="day1" size="4" value="${sDtime.day1}" readonly/>
  									<select name="day1option" disabled>
  									<% for(int i=1; i<=11; i++){%>
  										<option value="<%=i%>"><%=i%></option>
  									<%}%>
  									</select>
  									</td>
  								</tr>
  								<tr>
  									<td>
  									星期二
  									</td>
  									<td>
  									<input type="text" id="day2" name="day2" size="4" value="${sDtime.day2}" readonly/>
  									<select name="day2option" disabled>
  									<% for(int i=1; i<=11; i++){%>
  										<option value="<%=i%>"><%=i%></option>
  									<%}%>
  									</select>
  									</td>
  								</tr>
  								<tr>
  									<td>
  									星期三
  									</td>
  									<td>
  									<input type="text" id="day3" name="day3" size="4" value="${sDtime.day3}" readonly/>
  									<select name="day3option" disabled>
  									<% for(int i=1; i<=11; i++){%>
  										<option value="<%=i%>"><%=i%></option>
  									<%}%>
  									</select>
  									</td>
  								</tr>
  								<tr>
  									<td>
  									星期四
  									</td>
  									<td>
  									<input type="text" id="day4" name="day4" size="4" value="${sDtime.day4}" readonly/>
  									<select name="day4option" disabled>
  									<% for(int i=1; i<=11; i++){%>
  										<option value="<%=i%>"><%=i%></option>
  									<%}%>
  									</select>
  									</td>
  								</tr>
  								<tr>
  									<td>
  									星期五
  									</td>
  									<td>
  									<input type="text" id="day5" name="day5" size="4" value="${sDtime.day5}" readonly/>
  									<select name="day5option" disabled>
  									<% for(int i=1; i<=11; i++){%>
  										<option value="<%=i%>"><%=i%></option>
  									<%}%>
  									</select>
  									</td>
  								</tr>
  								<tr>
  									<td>
  									星期六
  									</td>
  									<td>
  									<input type="text" id="day6" name="day6" size="4" value="${sDtime.day6}" readonly/>
  									<select name="day6option" disabled>
  									<% for(int i=1; i<=11; i++){%>
  										<option value="<%=i%>"><%=i%></option>
  									<%}%>
  									</select>
  									</td>
  								</tr>
  								<tr>
  									<td>
  									星期日
  									</td>
  									<td>
  									<input type="text" id="day7" name="day7" size="4" value="${sDtime.day7}" readonly/>
  									<select name="day7option" disabled>
  									<% for(int i=1; i<=11; i++){%>
  										<option value="<%=i%>"><%=i%></option>
  									<%}%>
  									</select>
  									<input type="hidden" name="oid" value="${sDtime.Oid}" />
  									</td>
  								</tr>
  							
  							
  							
  							</table>
				</td>
			</tr>				
		</table>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center" width="100%">
		<INPUT type="submit"
						   name="method"
						   value="<bean:message key='Modify'/>"
						   class="gSubmit">
						 <INPUT type="submit"
						   						 name="method"
						   						 value="<bean:message key='Cancel'/>"
						  						 class="gCancle">
		</td>
	</tr>