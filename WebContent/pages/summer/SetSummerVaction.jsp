<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Summer/SetSummerVaction" method="post" onsubmit="init('查詢進行中, 請稍後')">
<script>
	generateTableBanner('<table align="left">'+
									'<tr>'+
										'<td align="left">&nbsp;&nbsp;<img src="images/icon_calendar.gif"></td>'+
										'<td>暑修 梯次/日期 設定</td>'+
									'</tr>'+
								'</table>');
</script>
	<tr>
		<td>
		
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" align="center"><img src="images/16-exc-mark.gif"></td>
				<td class="hairLineTdF">代碼</td>
				<td class="hairLineTdF">名稱</td>
				<td class="hairLineTdF">梯次	</td>
				<td class="hairLineTdF" align="center"><img src="images/icon_calendar.gif"></td>
				<td class="hairLineTdF">日期</td>
			</tr>
		<c:forEach items="${sweek}" var="s">
			<tr>
				<td class="hairLineTdF" width="30" align="center"><img src="images/lightbulb_off.gif"></td>
				<td class="hairLineTd">
				<select name="daynite" onchange="document.getElementById('dayniteName${s.Oid}').value=this.value">
					<option value=""></option>
					<c:forEach items="${allSchool}" var="a">
					<option value="${a.no}"<c:if test="${a.no==s.daynite}">selected</c:if> >${a.name}</option>
					</c:forEach>
				</select>
				</td>
				<td class="hairLineTd"><input type="text" id="dayniteName${s.Oid}" name="dayniteName"" value="${s.daynite}" size="2" readonly/></td>
				<td class="hairLineTd">
				<select name="seqno">
					<option value="1" <c:if test="${s.seqno==''}">selected</c:if>></option>
					<option value="1" <c:if test="${s.seqno==1}">selected</c:if>>1</option>
					<option value="2" <c:if test="${s.seqno==2}">selected</c:if>>2</option>
					<option value="3" <c:if test="${s.seqno==3}">selected</c:if>>3</option>
					<option value="4" <c:if test="${s.seqno==4}">selected</c:if>>4</option>
					<option value="5" <c:if test="${s.seqno==5}">selected</c:if>>5</option>
				</select>
				</td>
				<td class="hairLineTdF" width="30" align="center"><img src="images/icon_calendar.gif"></td>
				<td class="hairLineTd">
					<input type="text" name="wdate" value="${s.wdate}" onclick="ds_sh(this), this.value='';" />
					<input type="hidden" name="Oid" value="${s.Oid}" />
				</td>
			</tr>
		</c:forEach>
		
			<tr id="add1" style="display:none">
				<td class="hairLineTdF" align="center"><img src="images/16-exc-mark.gif"></td>
				<td class="hairLineTd">
					<select name="daynite" onchange="document.getElementById('XdayniteName1').value=this.value">
					<option value=""></option>
					<c:forEach items="${allSchool}" var="a1">
					<option value="${a1.no}">${a1.name}</option>
					</c:forEach>
					</select>
				</td>
				<td class="hairLineTdF"><input type="text" name="dayniteName"" id="XdayniteName1" value="" size="2" readonly/></td>
				<td class="hairLineTdF">
					<select name="seqno">
					<option value=""></option>
					<option value="1" <c:if test="${s.seqno==1}">selected</c:if>>1</option>
					<option value="2" <c:if test="${s.seqno==2}">selected</c:if>>2</option>
					<option value="3" <c:if test="${s.seqno==3}">selected</c:if>>3</option>
					<option value="4" <c:if test="${s.seqno==4}">selected</c:if>>4</option>
					<option value="5" <c:if test="${s.seqno==5}">selected</c:if>>5</option>
					</select>
				</td>
				<td class="hairLineTdF" align="center"><img src="images/icon_calendar.gif"></td>
				<td class="hairLineTd">
					<input type="text" name="wdate"" onclick="ds_sh(this), this.value='';" />
					<input type="hidden" name="Oid" value=""/>
				</td>
			</tr>
			<tr id="add2" style="display:none">
				<td class="hairLineTdF" align="center"><img src="images/16-exc-mark.gif"></td>
				<td class="hairLineTd">
					<select name="daynite" onchange="document.getElementById('XdayniteName2').value=this.value">
					<option value=""></option>
					<c:forEach items="${allSchool}" var="a1">
					<option value="${a1.no}">${a1.name}</option>
					</c:forEach>
					</select>
				</td>
				<td class="hairLineTd"><input type="text" name="dayniteName"" id="XdayniteName2" value="" size="2" readonly/></td>
				<td class="hairLineTd">
					<select name="seqno">
					<option value=""></option>
					<option value="1" <c:if test="${s.seqno==1}">selected</c:if>>1</option>
					<option value="2" <c:if test="${s.seqno==2}">selected</c:if>>2</option>
					<option value="3" <c:if test="${s.seqno==3}">selected</c:if>>3</option>
					<option value="4" <c:if test="${s.seqno==4}">selected</c:if>>4</option>
					<option value="5" <c:if test="${s.seqno==5}">selected</c:if>>5</option>
					</select>
				</td>
				<td class="hairLineTdF" align="center"><img src="images/icon_calendar.gif"></td>
				<td class="hairLineTd">
					<input type="text" name="wdate"" onclick="ds_sh(this), this.value='';" />
					<input type="hidden" name="Oid" value=""/>
				</td>
			</tr>
			<tr id="add3" style="display:none">
				<td class="hairLineTdF" align="center"><img src="images/16-exc-mark.gif"></td>
				<td class="hairLineTd">
					<select name="daynite" onchange="document.getElementById('XdayniteName3').value=this.value">
					<option value=""></option>
					<c:forEach items="${allSchool}" var="a1">
					<option value="${a1.no}">${a1.name}</option>
					</c:forEach>
					</select>
				</td>
				<td class="hairLineTd"><input type="text" name="dayniteName"" id="XdayniteName3" value="" size="2" readonly/></td>
				<td class="hairLineTd">
					<select name="seqno">
					<option value=""></option>
					<option value="1" <c:if test="${s.seqno==1}">selected</c:if>>1</option>
					<option value="2" <c:if test="${s.seqno==2}">selected</c:if>>2</option>
					<option value="3" <c:if test="${s.seqno==3}">selected</c:if>>3</option>
					<option value="4" <c:if test="${s.seqno==4}">selected</c:if>>4</option>
					<option value="5" <c:if test="${s.seqno==5}">selected</c:if>>5</option>
					</select>
				</td>
				<td class="hairLineTdF" align="center"><img src="images/icon_calendar.gif"></td>
				<td class="hairLineTd">
					<input type="text" name="wdate"" onclick="ds_sh(this), this.value='';" />
					<input type="hidden" name="Oid" value=""/>
				</td>
			</tr>
			<tr id="addSwitch">
				<td class="hairLineTdF" colspan="6"><input value="&nbsp;&nbsp;&nbsp;增加開課梯次" type="button" onClick="javascript:AddNumInput()" class="addCourseButton"/></td>
			</tr>
			
		</table>
		<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
			<tr>
				<td id="ds_calclass"></td>
			</tr>
		</table>		
		</td>
	<tr height="30">
		<td class="fullColorTable" align="center" width="100%">
		<INPUT type="submit"
						   name="method"
						   value="<bean:message key='Save'/>"
						   class="CourseButton"><INPUT type="submit" name="method" value="<bean:message key='Clear'/>" class="CourseButton" disabled>
		</td>
	</tr>
	</html:form>
</table>


<script>
	var count=0;
	function AddNumInput(){
		count=count+1;
		document.getElementById("add"+count).style.display="inline";
		if(count==3){
			document.getElementById("addSwitch").style.display="none";
		}
	}
</script>
<%@ include file="/pages/include/MyCalendar.jsp" %>