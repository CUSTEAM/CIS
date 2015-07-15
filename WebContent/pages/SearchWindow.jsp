<!DOCTYPE html PUBLIC"-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.struts.Globals" %> 
<%@ include file="/taglibs.jsp" %>
<html>
  <head>
  	<style>
	body {font-size:12px;font-family:Arial}
	ul.TabBarLevel1{
		list-style:none;
		margin:0;
		padding:0;
		height:29px;
		background-image:url(/CIS/pages/images/tabbar_level1_bk.gif);
	}
	ul.TabBarLevel1 li{
		float:left;
		padding:0;
		height:29px;
		margin-right:1px;
		background:url(/CIS/pages/images/tabbar_level1_slice_left_bk.gif) left top no-repeat;
	}
	ul.TabBarLevel1 li a{
		display:block;
		line-height:29px;
		padding:0 10px;
		color:#333;
		background:url(/CIS/pages/images/tabbar_level1_slice_right_bk.gif) right top no-repeat;
		white-space: nowrap;
	}
	ul.TabBarLevel1 li.Selected{
		background:url(/CIS/pages/images/tabbar_level1_slice_selected_left_bk.gif) left top no-repeat;
	}
	ul.TabBarLevel1 li.Selected a{
		background:url(/CIS/pages/images/tabbar_level1_slice_selected_right_bk.gif) right top no-repeat;
	}
	
	ul.TabBarLevel1 li a:link,ul.TabBarLevel1 li a:visited{
		color:#333;
		text-decoration:none;
	}
	ul.TabBarLevel1 li a:hover,ul.TabBarLevel1 li a:active{
		color:#F30;
		text-decoration:none;
	}
	ul.TabBarLevel1 li.Selected a:link,ul.TabBarLevel1 li.Selected a:visited{
		color:#000;
		text-decoration:none;
	}
	ul.TabBarLevel1 li.Selected a:hover,ul.TabBarLevel1 li.Selected a:active{
		color:#F30;
		text-decoration:none;
	}
	div.HackBox {
	  padding : 0 0 0 0;
	  border-left: 3px solid #CFE69F;
	  border-right: 3px solid #CFE69F;
	  border-bottom: 0px solid #CFE69F;
	  border-top: 12px solid #CFE69F;
	  display:none;
	}
	div.rtop{
		display:block;
		background: #FFF
  	}
  	div.r1f{margin: 0 5px;}
	div.r2f{margin: 0 3px}
	div.r3f{margin: 0 2px}
	div.r4f{margin: 0 1px;
	height: 2px
	}
	div#nifty{
   		margin: 0 0%;
   		background: #CFE69F
  }
	
	div.rtop, div.rbottom{
		display:block;
		background: #FFF
  }
  
	div.rtop div, div.rbottom div{
		display:block;
		height: 1px;
		overflow: hidden;
		background: #CFE69F
  }
  
    /*full square for msgBox*/
	  div#niftyMsg{
   		margin: 0 0%;
   		background: #FAD163
  }  
	div.rtopMsg div, div.rbottomMsg div{
		display:block;
		height: 1px;
		overflow: hidden;
		background: #FAD163
  }
  
    /*full square for ErroRmsgBox*/
	  div#niftyEMsg{
   		margin: 0 0%;
   		background: #ff7373
  }  
	div.rtopEMsg div, div.rbottomEMsg div{
		display:block;
		height: 1px;
		overflow: hidden;
		background: #ff7373
  }
   .SearchButton {
	font:normal 12px Arial;
	border: 1px outset white;
	width: 46px;
	height: 24px;
	background-image: url(/CIS/pages/images/24-zoom.png);
	}
	.createButton {
	font:normal 12px Arial;
	border: 1px outset white;
	width: 46px;
	height: 24px;
	background-image: url(/CIS/pages/images/12-em-check.png);
	}
	.deleteButton {
	font:normal 12px Arial;
	border: 1px outset white;
	width: 46px;
	height: 24px;
	background-image: url(/CIS/pages/images/12-em-cross.png);
	}
	.modifyButton {
	font:normal 12px Arial;
	border: 1px outset white;
	width: 70px;
	height: 24px;
	background-image: url(/CIS/pages/images/16-security-key.png.png);
	}
	a{blr:expression(this.onFocus=this.close());}

	a{blr:expression(this.onFocus=this.blur());}

	</style>
	<style type="text/css">

	.ds_box {
		background-color: #FFF;
		border: 1px solid #CFE69F;
		position: absolute;
		z-index: 32767;
	}
	
	.ds_tbl {
		background-color: #FFF;
	}
	
	.ds_header {
		background-color: #CFE69F;
		color: #FFF;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 10px;
		font-weight: solid;
		text-align: center;
		letter-spacing: 2px;
	}
	
	.ds_head {
		background-color: #F0FCD7;
		color: #000;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 10px;
		font-weight: solid;
		text-align: center;
		letter-spacing: 2px;
	}
	
	.ds_subhead {
		background-color: #CFE69F;
		color: #000;
		font-size: 10px;
		font-weight: solid;
		text-align: center;
		font-family: Arial, Helvetica, sans-serif;
		width: 12px;
	}
	
	.ds_cell {
		background-color: #F0FCD7;
		color: #000;
		font-size: 10px;
		text-align: center;
		font-family: Arial, Helvetica, sans-serif;
		padding: 0px;
		cursor: pointer;
	}
	
	.ds_cell:hover {
		background-color: #CFE69F;
	} /* This hover code won't work for IE */
	
	</style>
  </head>  
  <body>

<table width="100%">
	<tr>
		<td width="1">
		</td>
		<td valign="bottom" align="center">
			<table align="center">
				<tr>
					<td>
					<logic:present name="<%=Globals.MESSAGE_KEY%>">	
			 		<table>
			 			<tr>
			 			  <td><img src="/CIS/pages/images/allowed1.png"></td>
			 			  <td>
			 			  		<div class="rtopMsg">
			 					<div class="r1f"></div>
			 					<div class="r2f"></div>
			 					<div class="r3f"></div>
			 					<div class="r4f"></div>
			 				</div>
			 					<table cellspacing="0" cellpadding="0" border="0"><tr><td bgcolor="#FAD163" align="left"> 
			 					<html:messages id="msg" message="true">		  			
			 			  			&nbsp;&nbsp;&nbsp;&nbsp;<b>${msg}</b>&nbsp;&nbsp;&nbsp;&nbsp;
			 			  		</html:messages>
			 			  		</td></tr></table>
			 			  	<div class="rtopMsg">
								<div class="r4f"></div>
								<div class="r3f"></div>
								<div class="r2f"></div>
								<div class="r1f"></div>
						  </div>		
			 			  			
			  			  </td>
				  		</tr>
				  	</table>
	  	</logic:present>
		<logic:present name="<%=Globals.ERROR_KEY%>">
		<table>
 			<tr>
 				<td><img src="/CIS/pages/images/blocked1.png"></td>
 				<td>
 			  	<div class="rtopEMsg">
 					<div class="r1f"></div>
 					<div class="r2f"></div>
 					<div class="r3f"></div>
 					<div class="r4f"></div>
 				</div>
 				
 				<table cellspacing="0" cellpadding="0" border="0">
 					<tr>
 						<td bgcolor="#ff7373">
          					<html:errors/>
          				</td>
          			</tr>
          		</table>
 			  	<div class="rtopEMsg">
					<div class="r4f"></div>
					<div class="r3f"></div>
					<div class="r2f"></div>
					<div class="r1f"></div>
			  </div>	
  			</td>
	  		</tr>
	  	</table> 
		</logic:present>
					</td>
				</tr>
			</table>		
		</td>
	</tr>
</table>
















 
  
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
	<tr>
		<td id="ds_calclass"></td>
	</tr>
</table>
  
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td width="100%" align="center" border="0" align="center" cellpadding="0" cellspacing="0">
			<html:form action="/Search.do">
			<input type="hidden" name="tab0" value="${SearchToolForm.map.tab0}"/>
			<div id="Whatever">
				<ul class="TabBarLevel1" id="TabPage1">
					<li id="Tab1"><a href="#" onclick="javascript:switchTab('TabPage1','Tab1'); tab0.value='1';">教職員工</a></li>
					<li  id="Tab2" class="Selected"><a href="#" onclick="javascript:switchTab('TabPage1','Tab2'); tab0.value='2';">課程代碼</a></li>
					<li  id="Tab3"><a href="#" onclick="javascript:switchTab('TabPage1','Tab3'); tab0.value='3';">班級代碼</a></li>
					<li  id="Tab4"><a href="#" onclick="javascript:switchTab('TabPage1','Tab4'); tab0.value='4';">群組訊息</a></li>
					<li  id="Tab5"><a href="#" onclick="javascript:switchTab('TabPage1','Tab5'); tab0.value='5';">修改密碼</a></li>
				</ul>
				<div id="cnt">
				<div id="dTab1" class="HackBox" <c:if test="${SearchToolForm.map.tab0=='1'}">style="display:block"</c:if>>
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr height="40">
						<td>
							教師姓名 <input type="text" name="cname" size="6" value="${SearchToolForm.map.cname}"/> &nbsp;
							身分證字號 <input type="text" title="經指示不允許以身分證字號反查!" name="idno" size="10" value="${SearchToolForm.map.idno}" disabled style="ime-mode:disabled"/>
							
							<INPUT type="submit" name="method" value="<bean:message key='Query'/>" class="SearchButton" style="text-align:right" />
						</td>
					</tr>
					<tr height="10">
						<td bgcolor="#CFE69F"></td>
					</tr>
					<c:if test="${piggieSize<1}">
					<tr height="30">
						<td align="left">&nbsp;沒這個人...</td>
					</tr>
					</c:if>
					<c:if test="${not empty piggies}">
					<tr>
						<td>
							<table border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
								
								<tr height="20">
									<td align="center" bgcolor="#f0fcd7">教師編號</td>
									<td align="center" bgcolor="#f0fcd7">教師姓名</td>
									<td align="center" bgcolor="#f0fcd7">性別</td>
									<td align="center" bgcolor="#f0fcd7">單位</td>
									<td align="center" bgcolor="#f0fcd7">職稱</td>
								</tr>
									<%int i=0;%>
									<c:forEach items="${piggies}" var="peo">
									<%i=i+1;%>
										<tr height="20">
											<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%>
											onClick="copythis('piggy<%=i%>')" name="piggy<%=i%>" 
											onmouseover="window.status='輕點左鍵即可複製'; return true;" 
											onmouseout="window.status='';"
											>
											<input type="hidden" value="${peo.idno}" name="piggy<%=i%>"/>
											********** 
											</td>
											<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${peo.cname}</td>
											<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${peo.sex2}</td>
											<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${peo.unit2}</td>
											<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${peo.pcode2}</td>
										</tr>
									</c:forEach>
							</table>
						</td>
					</tr>
					</c:if>
					<tr height="10">
						<td bgcolor="#CFE69F"></td>
					</tr>
				</table>
				</div>
				<div id="dTab2" class="HackBox" <c:if test="${SearchToolForm.map.tab0=='2'}">style="display:block"</c:if>>
				<table width="100%" align="center" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr height="40">
						<td>
							課程名稱 <input type="text" name="chiName" size="10" value="${SearchToolForm.map.chiName}"/> &nbsp;
							課程代碼 <input type="text" name="cscode" size="6" value="${SearchToolForm.map.cscode}" style="ime-mode:disabled"/>
							<INPUT type="submit" name="method" value="<bean:message key='Query'/>" class="SearchButton" style="text-align:right" />
						</td>
					</tr>
					<tr height="10">
						<td bgcolor="#CFE69F"></td>
					</tr>
					<c:if test="${subjectSize<1}">
					<tr height="30">
						<td align="left">&nbsp;沒這門課...</td>
					</tr>
					</c:if>
					<c:if test="${not empty subjects}">
					<tr>
						<td>
							<%int j=0;%>
							<table border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
								<tr height="20">
									<td align="center" bgcolor="#f0fcd7">科目代碼</td>
									<td align="center" bgcolor="#f0fcd7">科目名稱</td>
								</tr>
									<c:forEach items="${subjects}" var="subj">
									<%j=j+1;%>
										<tr height="20">
											<td align="center" <%if(j%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%>
											onClick="copythis('subj<%=j%>')" name="subj<%=j%>" 
											onmouseover="window.status='輕點左鍵即可複製'; return true;" 
											onmouseout="window.status='';"
											>
											<input type="hidden" value="${subj.cscode}" name="subj<%=j%>"/>
											${subj.cscode}
											</td>
											<td align="center" <%if(j%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${subj.chiName}</td>
											
										</tr>
									</c:forEach>
							</table>
						</td>
					</tr>
					</c:if>
					<tr height="10">
						<td bgcolor="#CFE69F"></td>
					</tr>
				</table>				
				</div>
				<div id="dTab3" class="HackBox" <c:if test="${SearchToolForm.map.tab0=='3'}">style="display:block"</c:if>>
				<%@include file="/pages/include/AllClassSelect.jsp" %>
				<table width="100%" align="center" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr height="10">
						<td bgcolor="#CFE69F"></td>
					</tr>
				</table>
				</div>
				
				
				<div id="dTab4" class="HackBox" <c:if test="${SearchToolForm.map.tab0=='4'}">style="display:block"</c:if>>				
				<table width="100%" align="center" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">
						發佈人
						</td>
						<td align="left">
						<INPUT type="text" size="10" name="pigsName" value="${piggyName}" disabled/> 
						發送單位 <select name="pigsMoudle">
							<c:forEach items="${piggysMoudles}" var="pigsMoudles">
								<option value="${pigsMoudles.name}">${pigsMoudles.label }</option>
							</c:forEach>
						</select> 
						</td>
					</tr>
					<tr>
					<%
						Date day=new Date();
						long parseDay=day.getTime();
						
						Date ExpiredDate=new Date();
						int ExpiredPeriod=30;
						ExpiredDate.setDate(ExpiredDate.getDate()+ExpiredPeriod);
						long LExpiredDate=ExpiredDate.getTime();
						
						Date today=new Date();
						today.setTime(LExpiredDate);
						
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
					%>
						<td align="right">
						發佈期間 
						</td>
						<td align="left">
						 <INPUT type="text" size="10" name="msgStart" onclick="ds_sh(this);" style="cursor: text"
						 value="<%=sdf.format(new Date())%>" readonly/> ~ 
						<INPUT type="text" size="10" name="msgEnd" onclick="ds_sh(this);" style="cursor: text"
						 value="<%=sdf.format(today)%>" readonly/>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="left">
						<textarea name="pigsMsg" rows="5" cols="40"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2" bgcolor="#CFE69F"><INPUT type="submit" name="method" value="<bean:message key='Create'/>" class="createButton" style="text-align:right"/><INPUT type="submit" name="method" value="<bean:message key='Delete'/>" class="deleteButton" style="text-align:right"/></td>
					</tr>
					<c:if test="${not empty piggyMsgList}">
					<tr>					
						<td colspan="2">
							<%int k=0;%>
							<table border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
								<tr height="20">
									<td align="center" bgcolor="#f0fcd7"><input type="checkbox" name="msgSwitch" onClick="checkAllmsg()"/></td>
									<td align="center" bgcolor="#f0fcd7" width="80">開始日期</td>
									<td align="center" bgcolor="#f0fcd7" width="80">結束日期</td>
									<td align="center" bgcolor="#f0fcd7">內容概要</td>
								</tr>
								<c:forEach items="${piggyMsgList}" var="pMsg">
								<%k=k+1;%>
								<tr height="20">
									<td align="center" <%if(k%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>><input type="checkbox" name="checkPmsg" value="${pMsg.oid}"/></td>
									<td align="center" <%if(k%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>><fmt:formatDate pattern="yyyy-MM-dd" type="date" value="${pMsg.startDate}"/></td>
									
									<td align="left" <%if(k%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>><fmt:formatDate pattern="yyyy-MM-dd" type="date" value="${pMsg.dueDate}"/></td>
									<td align="left" <%if(k%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>><c:out value="${fn:substring(pMsg.content, 0, 12) } ..."/></td>
								</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
					</c:if>
				</table>
				</div>
				
				
				<div id="dTab5" class="HackBox" <c:if test="${SearchToolForm.map.tab0=='5'}">style="display:block"</c:if>>
				
				<table width="65%" align="center" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr height="30">
						<td align="right">帳號</td>
						<td align="left">&nbsp;<input type="text" name="pigId" value="${pigId}" readonly/></td>
					</tr>
					<tr height="30">
						<td align="right">現用密碼</td>
						<td align="left">&nbsp;<input type="password" name="pigPwd" style="ime-mode:disabled" 
						value="${SearchToolForm.map.pigPwd}"/></td>
					</tr>
					<tr height="30">
						<td align="right">新密碼</td>
						<td align="left">&nbsp;<input type="password" name="pigNewPwd" style="ime-mode:disabled" 
						value="${SearchToolForm.map.pigNewPwd}"/></td>
					</tr>
					<tr height="30">
						<td align="right">確認新密碼</td>
						<td align="left">&nbsp;<input type="password" name="pigNewPwd2" style="ime-mode:disabled" 
						value="${SearchToolForm.map.pigNewPwd2}"/></td>
					</tr>
					<tr height="30">
						<td></td>
						<td align="left">&nbsp;<INPUT type="submit" name="method" value="<bean:message key='ModifyRecord'/>" class="modifyButton" style="text-align:right"/></td>
					</tr>
				</table>
				
				
				<table width="100%" align="center" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr height="10">
						<td bgcolor="#CFE69F"></td>
					</tr>
				</table>
				</div>
				
				
				
				
				
				
				
				</div>
			</div>
			<div class="rtop">
				<div class="r4f"></div>
				<div class="r3f"></div>
				<div class="r2f"></div>
				<div class="r1f"></div>
			</div>
			</html:form>
			
		</td>
	</tr>
</table>
</body>
</html>
<script>
	function checkAllmsg(){
	
	var len = document.forms[0].checkPmsg.length;
	var obj = document.forms[0].checkPmsg;
	    
		if(document.forms[0].msgSwitch.checked==true){
		
		    if (len>0){
			  for(i=0; i< obj.length; i++){
			    obj[i].checked = true;
			  }
			}else{
			  obj.checked = true;
			}
		
		
		}else{
			if (len>0){
		  		for(i=0; i< obj.length; i++){
		    		obj[i].checked = false;
		  		}	
			}else{
		  		obj.checked = false;
				}
		
		}
		
		return false;
	}
</script>	
<script>
//Switch Tab Effect
function switchTab(tabpage,tabid){
        var oItem = document.getElementById(tabpage);   
	for(var i=0;i<oItem.children.length;i++){
		var x = oItem.children(i);	
		x.className = "";
		var y = x.getElementsByTagName('a');
		y[0].style.color="#333333";
	}	
	document.getElementById(tabid).className = "Selected";
	var dvs=document.getElementById("cnt").getElementsByTagName("div");
	for (var i=0;i<dvs.length;i++){
	  if (dvs[i].id==('d'+tabid))
	    dvs[i].style.display='block';
	  else
  	  dvs[i].style.display='none';
	}
};

	switchTab('TabPage1', 'Tab${SearchToolForm.map.tab0}');
</script>
<script type="text/javascript">
	// <!-- <![CDATA[
	
	// Project: Dynamic Date Selector (DtTvB) - 2006-03-16
	// Script featured on JavaScript Kit- http://www.javascriptkit.com
	// Code begin...
	// Set the initial date.
	var ds_i_date = new Date();
	ds_c_month = ds_i_date.getMonth() + 1;
	ds_c_year = ds_i_date.getFullYear();
	
	// Get Element By Id
	function ds_getel(id) {
		return document.getElementById(id);
	}
	
	// Get the left and the top of the element.
	function ds_getleft(el) {
		var tmp = el.offsetLeft;
		el = el.offsetParent
		while(el) {
			tmp += el.offsetLeft;
			el = el.offsetParent;
		}
		return tmp;
	}
	function ds_gettop(el) {
		var tmp = el.offsetTop;
		el = el.offsetParent
		while(el) {
			tmp += el.offsetTop;
			el = el.offsetParent;
		}
		return tmp;
	}
	
	// Output Element
	var ds_oe = ds_getel('ds_calclass');
	// Container
	var ds_ce = ds_getel('ds_conclass');
	
	// Output Buffering
	var ds_ob = ''; 
	function ds_ob_clean() {
		ds_ob = '';
	}
	function ds_ob_flush() {
		ds_oe.innerHTML = ds_ob;
		ds_ob_clean();
	}
	function ds_echo(t) {
		ds_ob += t;
	}
	
	var ds_element; // Text Element...
	
	var ds_monthnames = [
	'1月', '2月', '3月', '4月', '5月', '6月',
	'7月', '8月', '9月', '10月', '11月', '12月'
	]; // You can translate it for your language.
	
	var ds_daynames = [
	'日', '一', '二', '三', '四', '五', '六'
	]; // You can translate it for your language.
	
	// Calendar template
	function ds_template_main_above(t) {
		return '<table cellpadding="3" cellspacing="1" class="ds_tbl">'
		     + '<tr>'
			 + '<td class="ds_header" style="cursor: pointer" onclick="ds_py();">&lt;&lt;</td>'
			 + '<td class="ds_header" style="cursor: pointer" onclick="ds_pm();">&lt;</td>'
			 + '<td class="ds_header" style="cursor: pointer" onclick="ds_hi();" colspan="3">關閉</td>'
			 + '<td class="ds_header" style="cursor: pointer" onclick="ds_nm();">&gt;</td>'
			 + '<td class="ds_header" style="cursor: pointer" onclick="ds_ny();">&gt;&gt;</td>'
			 + '</tr>'
		     + '<tr>'
			 + '<td colspan="7" class="ds_head">' + t + '</td>'
			 + '</tr>'
			 + '<tr>';
	}
	
	function ds_template_day_row(t) {
		return '<td class="ds_subhead">' + t + '</td>';
		// Define width in CSS, XHTML 1.0 Strict doesn't have width property for it.
	}
	
	function ds_template_new_week() {
		return '</tr><tr>';
	}
	
	function ds_template_blank_cell(colspan) {
		return '<td colspan="' + colspan + '"></td>'
	}
	
	function ds_template_day(d, m, y) {
		return '<td class="ds_cell" onclick="ds_onclick(' + d + ',' + m + ',' + y + ')">' + d + '</td>';
		// Define width the day row.
	}
	
	function ds_template_main_below() {
		return '</tr>'
		     + '</table>';
	}
	
	// This one draws calendar...
	function ds_draw_calendar(m, y) {
		// First clean the output buffer.
		ds_ob_clean();
		// Here we go, do the header
		ds_echo (ds_template_main_above(ds_monthnames[m - 1] + ' ' + y));
		for (i = 0; i < 7; i ++) {
			ds_echo (ds_template_day_row(ds_daynames[i]));
		}
		// Make a date object.
		var ds_dc_date = new Date();
		ds_dc_date.setMonth(m - 1);
		ds_dc_date.setFullYear(y);
		ds_dc_date.setDate(1);
		if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
			days = 31;
		} else if (m == 4 || m == 6 || m == 9 || m == 11) {
			days = 30;
		} else {
			days = (y % 4 == 0) ? 29 : 28;
		}
		var first_day = ds_dc_date.getDay();
		var first_loop = 1;
		// Start the first week
		ds_echo (ds_template_new_week());
		// If sunday is not the first day of the month, make a blank cell...
		if (first_day != 0) {
			ds_echo (ds_template_blank_cell(first_day));
		}
		var j = first_day;
		for (i = 0; i < days; i ++) {
			// Today is sunday, make a new week.
			// If this sunday is the first day of the month,
			// we've made a new row for you already.
			if (j == 0 && !first_loop) {
				// New week!!
				ds_echo (ds_template_new_week());
			}
			// Make a row of that day!
			ds_echo (ds_template_day(i + 1, m, y));
			// This is not first loop anymore...
			first_loop = 0;
			// What is the next day?
			j ++;
			j %= 7;
		}
		// Do the footer
		ds_echo (ds_template_main_below());
		// And let's display..
		ds_ob_flush();
		// Scroll it into view.
		ds_ce.scrollIntoView();
	}
	
	// A function to show the calendar.
	// When user click on the date, it will set the content of t.
	function ds_sh(t) {
		// Set the element to set...
		ds_element = t;
		// Make a new date, and set the current month and year.
		var ds_sh_date = new Date();
		ds_c_month = ds_sh_date.getMonth() + 1;
		ds_c_year = ds_sh_date.getFullYear();
		// Draw the calendar
		ds_draw_calendar(ds_c_month, ds_c_year);
		// To change the position properly, we must show it first.
		ds_ce.style.display = '';
		// Move the calendar container!
		the_left = ds_getleft(t);
		the_top = ds_gettop(t) + t.offsetHeight;
		ds_ce.style.left = the_left + 'px';
		ds_ce.style.top = the_top + 'px';
		// Scroll it into view.
		ds_ce.scrollIntoView();
	}
	
	// Hide the calendar.
	function ds_hi() {
		ds_ce.style.display = 'none';
	}
	
	// Moves to the next month...
	function ds_nm() {
		// Increase the current month.
		ds_c_month ++;
		// We have passed December, let's go to the next year.
		// Increase the current year, and set the current month to January.
		if (ds_c_month > 12) {
			ds_c_month = 1; 
			ds_c_year++;
		}
		// Redraw the calendar.
		ds_draw_calendar(ds_c_month, ds_c_year);
	}
	
	// Moves to the previous month...
	function ds_pm() {
		ds_c_month = ds_c_month - 1; // Can't use dash-dash here, it will make the page invalid.
		// We have passed January, let's go back to the previous year.
		// Decrease the current year, and set the current month to December.
		if (ds_c_month < 1) {
			ds_c_month = 12; 
			ds_c_year = ds_c_year - 1; // Can't use dash-dash here, it will make the page invalid.
		}
		// Redraw the calendar.
		ds_draw_calendar(ds_c_month, ds_c_year);
	}
	
	// Moves to the next year...
	function ds_ny() {
		// Increase the current year.
		ds_c_year++;
		// Redraw the calendar.
		ds_draw_calendar(ds_c_month, ds_c_year);
	}
	
	// Moves to the previous year...
	function ds_py() {
		// Decrease the current year.
		ds_c_year = ds_c_year - 1; // Can't use dash-dash here, it will make the page invalid.
		// Redraw the calendar.
		ds_draw_calendar(ds_c_month, ds_c_year);
	}
	
	// Format the date to output.
	function ds_format_date(d, m, y) {
		// 2 digits month.
		m2 = '00' + m;
		m2 = m2.substr(m2.length - 2);
		// 2 digits day.
		d2 = '00' + d;
		d2 = d2.substr(d2.length - 2);
		// YYYY-MM-DD
		return y + '-' + m2 + '-' + d2;
	}
	
	// When the user clicks the day.
	function ds_onclick(d, m, y) {
		// Hide the calendar.
		ds_hi();
		// Set the value of it, if we can.
		if (typeof(ds_element.value) != 'undefined') {
			ds_element.value = ds_format_date(d, m, y);
		// Maybe we want to set the HTML in it.
		} else if (typeof(ds_element.innerHTML) != 'undefined') {
			ds_element.innerHTML = ds_format_date(d, m, y);
		// I don't know how should we display it, just alert it to user.
		} else {
			alert (ds_format_date(d, m, y));
		}
	}
	
	// And here is the end.
	
	// ]]> -->
</script>
<script>
	function copythis(theField) {
	var tempval=eval("document.forms[0]."+theField)
	therange=tempval.createTextRange()
	therange.execCommand("Copy")
}
</script>