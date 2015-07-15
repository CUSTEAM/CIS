<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/StudAffair/StudJustice.do" method="post" onsubmit="init('查詢進行中, 請稍後')">
	<script>
		generateTableBanner('<table align="left">'+
			'<tr>'+
				'<td align="left">&nbsp;&nbsp;<img src="images/24-zoom-1.png"></td>'+
				'<td>獎懲公佈查詢</td>'+
			'</tr>'+
		'</table>');
	</script>
	<tr>
		<td>
		<table>
			<tr>
				<td>學生班級:</td>
				<td>
				<%@include file="/pages/include/ClassSelect4tech.jsp"%>
				<SELECT name="school" onChange="clearClassNo()">
						<option value="">或選擇部制</option>
						<c:forEach items="${allSchoolType}" var="as">
						<option value="${as.idno}" <c:if test="${SetCsquestionaryForm.map.schoolType==as.idno}">selected</c:if>>${as.name}</option>
					</c:forEach>
					</SELECT>
				</td>
			</tr>
		</table>
<script>
function clearClassNo(){
	document.getElementById("classLess").value="";

}
</script>
		<table>
			<tr>
				<td>學生學號:</td>
				<td><input type="text" name="stuNo" size="8"/></td>
				<td>學生姓名:</td>
				<td><input type="text" name="stuName" size="6"/></td>
			</tr>
		</table>
		<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
			<tr>
				<td id="ds_calclass"></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>起算日期:
				<INPUT type="text" size="7" name="begin" onclick="ds_sh(this), this.value='';" style="cursor: text"
				value="${dTEime.examDate2}" readonly />
				</td>
				<td>結算日期:
				<INPUT type="text" size="7" name="end" onclick="ds_sh(this), this.value='';" style="cursor: text"
				value="${dTEime.examDate2}" readonly />
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td>
				獎懲文號: <input type="text" name="docNo"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<script>
		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Query'/>" class="CourseButton">'+
		'<INPUT type="submit" name="method" value="<bean:message key='Clear'/>" class="CourseButton">');
	</script>
<c:if test="${jList!=null}">
	<tr>
		<td>
			<display:table name="${jList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
			<display:column title="班級" property="ClassName" sortable="true" class="left" />
	        <display:column title="學號" property="student_no" sortable="true" class="left" />
	        <display:column title="性別" property="sex" sortable="true" class="left" />
	        <display:column title="姓名"  property="student_name" sortable="true" class="left" />
	        <display:column title="獎懲文號" property="no" sortable="true" class="center" />
	        <display:column title="獎懲日期" property="ddate" sortable="true" class="center" />
	        <display:column title="獎懲原因" property="name" sortable="true" class="left" />
	        <display:column title="種類" property="n1" sortable="true" class="center" />
	        <display:column title="次數" property="cnt1" sortable="true" class="center" />
	        <display:column title="種類" property="n2" sortable="true" class="center" />
	        <display:column title="次數" property="cnt2" sortable="true" class="center" />
			</display:table>

		</td>
	</tr>
	<tr height="40">
		<td align="center">

		<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="CFE69F">
  				<tr>
    				<td bgcolor="#FFFFFF">
    					<table>
    						<tr>
    							<td>
    							 報表列印:
    							</td>
    							<td>
    							<a href="course/export/list4StudJustice.jsp?type=excel">
    							<img src="images/ico_file_excel.png" border="0"> Excel
    							</a>
    							
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>

		</td>

	</tr>
	<script>
   		generateTableBanner('');
 	</script>
</c:if>
</html:form>
</table>






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
		return '<td class="ds_cell" onclick="ds_onclick(' + d + ',' + m + ',' + (y-1911) + ')">' + d + '</td>';
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
		ds_echo (ds_template_main_above(ds_monthnames[m - 1] + ' ' + (y-1911)));
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