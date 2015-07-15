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
      						課程基本資料
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
				 			 value="${sDtime.depart_class}"
				 			 onkeyup="getAny(this.value, 'departClass', 'classLess', 'Sabbr', 'no')"
				 			 onclick="this.value='', document.getElementById('classLess').value=''"/>

							<input type="text" name="classLess" id="classLess"
							 value="${sDtime.name}" size="12"
				 			 onkeyup="getAny(this.value, 'classLess', 'departClass', 'Sabbr', 'name')"
				 			 onclick="this.value='', document.getElementById('departClass').value=''"/>
				 			<img src="images/16-exc-mark.gif" />
							
							
							
							</td>
						</tr>
					
						<tr>
							<td>課程代碼:</td>
							<td>
							
							<input type="text" name="courseNumber" id="cscodeS" size="8"
							 autocomplete="off" style="ime-mode:disabled" autocomplete="off"
							 value="${sDtime.cscode}"
							 onkeyup="if(this.value.length>2)getAny(this.value, 'cscodeS', 'csnameS', 'Csno', 'no')"
							 onclick="this.value='', courseName.value=''"/>
							 
							<input type="text" autocomplete="off"
							 name="courseName" id="csnameS" size="16"
							 value="${sDtime.chi_name}"
							 onkeyup="getAny(this.value, 'csnameS', 'cscodeS', 'Csno', 'name')"
							 onkeydown="document.getElementById('Acsname').style.display='none';"
							 onclick="this.value='', courseNumber.value=''"/><img src="images/16-exc-mark.gif" />
							
							</td>	
						</tr>
					
						<tr>
							<td>教師代碼:</td>
							<td>
							
							<input type="text" name="teacherId" id="techidS" size="8" style="ime-mode:disabled" autocomplete="off"
							 value="${sDtime.techid}"
							 onkeyup="if(this.value.length>2)getAny(this.value, 'techidS', 'technameS', 'empl', 'no')"
							 onclick="this.value='', document.getElementById('technameS').value=''"/>

							<input type="text" onkeyup="getAny(this.value, 'technameS', 'techidS', 'empl', 'name')"
							 onkeydown="document.getElementById('Acsname').style.display='none';" autocomplete="off"
							 onclick="this.value='', document.getElementById('techidS').value=''"
							 name="teacherName" id="technameS" size="12" value="${sDtime.cname}"/><img src="images/16-exc-mark.gif" />
							
							</td>	
					</tr>
				</table>
				
				</td>
				<td valign="top">
				<table>
					<tr>
						<td>
						梯次						
						</td>
						<td>
						<select name="seqno">
							<c:forEach items="${sweek}" var="sweek">
    							<option <c:if test="${sDtime.seqno==sweek.seqno}" > selected</c:if> value="${sweek.seqno}">${sweek.seqno}</option>
    						</c:forEach>
    						</select>
						<select name="choseType">
							<option <c:if test="${sDtime.opt=='1'}" > selected</c:if> value="1">必修</option>
							<option <c:if test="${sDtime.opt=='2'}" > selected</c:if> value="2">選修</option>
							<option <c:if test="${sDtime.opt=='3'}" > selected</c:if> value="3">通識</option>
						</select>
						</td>
					</tr>
					
					<tr>
						<td>
						總時數
						</td>
						<td>
						<input type="text" name="thour" size="1" value="${sDtime.thour}"/>
						<input type="text" name="credit" size="1" value="${sDtime.credit}"/> 學分
						</td>
					</tr>
					
					<tr>
						<td>
						上限
						</td>
						<td>
						<input type="text" name="" size="1" value="50" disabled/>
						<select name="status">
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
  									<input type="text" name="place" size="4" value="${sDtime.clascode}"/>
  									<img src="images/16-cube-room.png" />
  									</td>
  								</tr>
  								<tr>
  									<td>
  									星期一
  									</td>
  									<td>
  									<input type="text" id="day1" name="day1" size="4" value="${sDtime.day1}" />
  									<select name="day1option">
  									<% for(int i=1; i<=11; i++){%>
  										<option value="<%=i%>"><%=i%></option>
  									<%}%>
  									</select>
  									<img src="images/16-em-plus.png" onclick="setClass('day1', day1option.value)"/>
  									<img src="images/16-em-minus.gif" onclick="document.getElementById('day1').value=''"/>
  									</td>
  								</tr>
  								<tr>
  									<td>
  									星期二
  									</td>
  									<td>
  									<input type="text" id="day2" name="day2" size="4" value="${sDtime.day2}" />
  									<select name="day2option">
  									<% for(int i=1; i<=11; i++){%>
  										<option value="<%=i%>"><%=i%></option>
  									<%}%>
  									</select>
  									<img src="images/16-em-plus.png" onclick="setClass('day2', day2option.value)"/>
  									<img src="images/16-em-minus.gif" onclick="document.getElementById('day2').value=''"/>
  									</td>
  								</tr>
  								<tr>
  									<td>
  									星期三
  									</td>
  									<td>
  									<input type="text" id="day3" name="day3" size="4" value="${sDtime.day3}" />
  									<select name="day3option">
  									<% for(int i=1; i<=11; i++){%>
  										<option value="<%=i%>"><%=i%></option>
  									<%}%>
  									</select>
  									<img src="images/16-em-plus.png" onclick="setClass('day3', day3option.value)"/>
  									<img src="images/16-em-minus.gif" onclick="document.getElementById('day3').value=''"/>
  									</td>
  								</tr>
  								<tr>
  									<td>
  									星期四
  									</td>
  									<td>
  									<input type="text" id="day4" name="day4" size="4" value="${sDtime.day4}" />
  									<select name="day4option">
  									<% for(int i=1; i<=11; i++){%>
  										<option value="<%=i%>"><%=i%></option>
  									<%}%>
  									</select>
  									<img src="images/16-em-plus.png" onclick="setClass('day4', day4option.value)"/>
  									<img src="images/16-em-minus.gif" onclick="document.getElementById('day4').value=''"/>
  									</td>
  								</tr>
  								<tr>
  									<td>
  									星期五
  									</td>
  									<td>
  									<input type="text" id="day5" name="day5" size="4" value="${sDtime.day5}" />
  									<select name="day5option">
  									<% for(int i=1; i<=11; i++){%>
  										<option value="<%=i%>"><%=i%></option>
  									<%}%>
  									</select>
  									<img src="images/16-em-plus.png" onclick="setClass('day5', day5option.value)"/>
  									<img src="images/16-em-minus.gif" onclick="document.getElementById('day5').value=''"/>
  									</td>
  								</tr>
  								<tr>
  									<td>
  									星期六
  									</td>
  									<td>
  									<input type="text" id="day6" name="day6" size="4" value="${sDtime.day6}" />
  									<select name="day6option">
  									<% for(int i=1; i<=11; i++){%>
  										<option value="<%=i%>"><%=i%></option>
  									<%}%>
  									</select>
  									<img src="images/16-em-plus.png" onclick="setClass('day6', day6option.value)"/>
  									<img src="images/16-em-minus.gif" onclick="document.getElementById('day6').value=''"/>
  									</td>
  								</tr>
  								<tr>
  									<td>
  									星期日
  									</td>
  									<td>
  									<input type="text" id="day7" name="day7" size="4" value="${sDtime.day7}" />
  									<select name="day7option">
  									<% for(int i=1; i<=11; i++){%>
  										<option value="<%=i%>"><%=i%></option>
  									<%}%>
  									</select>
  									<img src="images/16-em-plus.png" onclick="setClass('day7', day7option.value)"/>
  									<img src="images/16-em-minus.gif" onclick="document.getElementById('day7').value=''"/>
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
						   value="<bean:message key='Save'/>"
						   class="CourseButton"><INPUT type="submit"
						   						 name="method"
						   						 value="<bean:message key='Cancel'/>"
						  						 class="CourseButton">
		</td>
	</tr>