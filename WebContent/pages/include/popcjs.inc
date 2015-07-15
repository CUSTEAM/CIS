<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>PopCalendar Millennium, By Liming(Victor) Weng</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<style type="text/css">
a:link { text-decoration: none;}
a:visited { text-decoration: none;}
TD { text-align: center; vertical-align: middle; font:9pt;}
.CalHead { font:bold 8pt Arial; color: #FFFFFF;}
.CalCell { font:8pt Arial; cursor: hand;}
.HeadBtn { vertical-align:middle; height:20; font:9pt Arial;}
.HeadBox { vertical-align:middle; font:8pt Arial;}
.Today { font:normal 8pt Arial; border: 1px outset white; }
</style>
</head>
<body leftmargin=0 topmargin=0 marginwidth=0 marginheight=0>
<script src='localize.js'></script>
<script type="text/javascript" language="javascript">
<!-- Begin Configurable Part
var giStartYear = 1900;
var giEndYear = 2073;
var giCellWidth = 23;
// var gMonths = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
var gMonths = new Array("01","02","03","04","05","06","07","08","09","10","11","12");
var gcOtherDay = "gray";
var gcOtherDayBG = "silver";
var gcToggle = "yellow";
var gcBG = "#f0f0f0";
var gcTodayBG = "gold";
var gcFrame = "#CFE69F";
var gcHead = "white";
var gcWeekend = "red";
var gcWorkday = "black";
var gcCalBG = "#f0fcd7";
//-- End Configurable Part -->

<!-- Begin Non-editable part
/****************************************************************************
 PopCalendar Millennium (emailware), by Liming(Victor) Weng
 Version 1.1        Release date: 2001.1.21           Email: popcal@yahoo.com
Notice:
 1. Thanks to my wife Yang Liu who contributes to this development a lot.
 2. This emailware is free only for non-commercial use, and must mail me first!
 3. For any commercial use, contact me to pay a small amount of license fee.
 4. Please respect my hard work. Any usage of or modification to this script without
 the authorization from the author may be accused for a reimbursement up to USD$10,000 !!!
*****************************************************************************/
var gcTemp = gcBG;
var gdCurDate = new Date();
var giYear = gdCurDate.getFullYear();
var giMonth = gdCurDate.getMonth()+1;
var giDay = gdCurDate.getDate();
var tbMonSelect, tbYearSelect;
var gCellSet = new Array;
var gfPop = parent.document.getElementById(self.name);
var gdCtrl = new Object;

//****************************************************************************
// Here, you could modify the date format as you need !
//****************************************************************************
function fSetDate(iYear, iMonth, iDay){
  gdCtrl.value = LocalizedYear(iYear, 1)+"-"+iMonth+"-"+iDay; // <--Modify this
  gfPop.style.visibility = "hidden";
  gfPop.style.display = "none";
  // added by Toad
  if ( parent.ifCompare ){
    parent.compareValue( oObj1, oObj2, thisObj );
  }
  gdCtrl.focus();
  gdCtrl.blur();
}

function fClearDate(){
  gdCtrl.value = "";
  gfPop.style.visibility = "hidden";
  gfPop.style.display = "none";
}

//****************************************************************************
// Param: popCtrl is the widget beyond which you want popup this calendar;
//        dateCtrl is the widget into which you want to put the selected date;
//****************************************************************************

var oObj1, oObj2, thisObj;

function fPopCalendar(popCtrl, dateCtrl, anEvent){
  anEvent.cancelBubble=true;
  gdCtrl = parent.document.getElementById(dateCtrl);
  var pc = parent.document.getElementById(popCtrl);
  fSetYearMon(giYear, giMonth);

  // defaultY = document.body.scrollTop + window.event.clientY;
  // limitY = document.body.scrollTop + document.body.offsetHeight - obj.offsetHeight - 4;
  // obj.style.pixelTop = Math.min(defaultY,limitY);

  var point = fGetXY(pc);
  with (gfPop.style) {
	visibility = "visible";
	display = "block";
  	left = point.x-1;
	top1 = point.y+pc.offsetHeight+1;
	top2 = parent.document.body.scrollTop + parent.document.body.offsetHeight - document.body.offsetHeight - -pc.offsetHeight - 75;
	top = Math.min( top1, top2 );
  }
  if ( arguments[3]!=null ){
    parent.ifCompare = 1;
    oObj1 = eval( "parent.document.forms.oForm." + arguments[3] );
    oObj2 = eval( "parent.document.forms.oForm." + arguments[4] );
    thisObj = arguments[5];
  }
}

function Point(iX, iY){
	this.x = iX;
	this.y = iY;
}

function fGetXY(aTag){
  var pt = new Point(aTag.offsetLeft, aTag.offsetTop);
  do {
  	aTag = aTag.offsetParent;
  	pt.x += aTag.offsetLeft;
  	pt.y += aTag.offsetTop;
  } while(aTag.tagName!="BODY");
  return pt;
}

function fSetSelected(aCell){
  var iOffset = 0;
  var iYear = parseInt(tbSelYear.value);
  var iMonth = parseInt(tbSelMonth.value);

  aCell.bgColor = gcBG;
  with (aCell.firstChild){
  	var iDate = parseInt(innerHTML);
  	if (style.color==gcOtherDay)
		iOffset = (id<10)?-1:1;
	iMonth += iOffset;
	if (iMonth<1) {
		iYear--;
		iMonth = 12;
	}else if (iMonth>12){
		iYear++;
		iMonth = 1;
	}
  }

  fSetDate(iYear, iMonth, iDate);

}

function fBuildCal(iYear, iMonth) {
  var aMonth=new Array();
  for(i=1;i<7;i++)
  	aMonth[i]=new Array(i);

  var dCalDate=new Date(iYear, iMonth-1, 1);
  var iDayOfFirst=dCalDate.getDay();
  var iDaysInMonth=new Date(iYear, iMonth, 0).getDate();
  var iOffsetLast=new Date(iYear, iMonth-1, 0).getDate()-iDayOfFirst+1;
  var iDate = 1;
  var iNext = 1;

  for (d = 0; d < 7; d++)
	aMonth[1][d] = (d<iDayOfFirst)?-(iOffsetLast+d):iDate++;
  for (w = 2; w < 7; w++)
  	for (d = 0; d < 7; d++)
		aMonth[w][d] = (iDate<=iDaysInMonth)?iDate++:-(iNext++);
  return aMonth;
}

function fDrawCal(iCellWidth) {
  // var WeekDay = new Array("Su","M","Tu","W","Th","F","Sa");
  var WeekDay = new Array("日","一","二","三","四","五","六");
  var styleTD = " width='"+iCellWidth+"' ";            //Coded by Liming Weng(Victor Won)  email:victorwon@netease.com

  with (document) {
	write("<tr>");
	for(i=0; i<7; i++)
		write("<td class='CalHead' "+styleTD+">" + WeekDay[i] + "</td>");
	write("</tr>");

  	for (w = 1; w < 7; w++) {
		write("<tr>");
		for (d = 0; d < 7; d++) {
			write("<td class='CalCell' "+styleTD+" onMouseOver='gcTemp=this.bgColor;this.bgColor=gcToggle;this.bgColor=gcToggle' onMouseOut='this.bgColor=gcTemp;this.bgColor=gcTemp' onclick=\"fSetSelected(this);\">");
			write("<A href='#null' onfocus='this.blur();'>00</A></td>")
		}
		write("</tr>");
	}
  }
}

function fUpdateCal(iYear, iMonth) {
  myMonth = fBuildCal(iYear, iMonth);
  var i = 0;
  var iDate = 0;
  for (w = 0; w < 6; w++)
	for (d = 0; d < 7; d++)
		with (gCellSet[(7*w)+d]) {
			id = i++;
			if (myMonth[w+1][d]<0) {
				style.color = gcOtherDay;
				// parentNode.style.backgroundColor = gcOtherDayBG;
				innerHTML = -myMonth[w+1][d];
				iDate = 0;
			}else{
				style.color = ((d==0)||(d==6))?gcWeekend:gcWorkday;
				innerHTML = myMonth[w+1][d];
				iDate++;
			}
			parentNode.bgColor = ((iYear==giYear)&&(iMonth==giMonth)&&(iDate==giDay))?gcTodayBG:gcBG;
			parentNode.bgColor = parentNode.bgColor;
		}
}

function fSetYearMon(iYear, iMon){
  tbSelMonth.options[iMon-1].selected = true;
  if (iYear>giEndYear) iYear=giEndYear;
  if (iYear<giStartYear) iYear=giStartYear;
  tbSelYear.options[iYear-giStartYear].selected = true;
  fUpdateCal(iYear, iMon);
}

function fPrevMonth(){
  var iMon = tbSelMonth.value;
  var iYear = tbSelYear.value;

  if (--iMon<1) {
	  iMon = 12;
	  iYear--;
  }

  fSetYearMon(iYear, iMon);
}

function fNextMonth(){
  var iMon = tbSelMonth.value;
  var iYear = tbSelYear.value;

  if (++iMon>12) {
	  iMon = 1;
	  iYear++;
  }

  fSetYearMon(iYear, iMon);
}
with (document) {
  write("<table id='popTable' bgcolor='"+gcCalBG+"' width='100%' height='100%' border='0' cellpadding='0' cellspacing='1'><TR>");
  write("<td align='center'>");
  write("<input type='button' value='<' class='HeadBtn' onClick='fPrevMonth()'>");
  write("民國<SELECT id='tbYearSelect' class='HeadBox' onChange='fUpdateCal(tbSelYear.value, tbSelMonth.value)' Victor='Won'>");
  for(i=giStartYear;i<=giEndYear;i++)
	write("<OPTION value='"+i+"'>"+LocalizedYear(i, 1)+"</OPTION>");
  write("</SELECT>&nbsp;年");

  write("<select id='tbMonSelect' class='HeadBox' onChange='fUpdateCal(tbSelYear.value, tbSelMonth.value)' Victor='Won'>");
  for (i=0; i<12; i++)
	//write("<option value='"+(i+1)+"'>"+gMonths[i]+"</option>");
	write("<option value='"+(i+1)+"'>"+(i+1)+"</option>");
  write("</SELECT>&nbsp;月");
  write("<input type='button' value='>' class='HeadBtn' onclick='fNextMonth()'> &nbsp;");
  write("<input type='button' value='清除' class='HeadBtn' onclick='fClearDate();this.blur();'>");
  write("</td></TR><TR><td align='center'>");
  write("<DIV style='background-color:"+gcFrame+";width:"+((giCellWidth+6)*7+2)+"px;'><table border='0' cellpadding='2' height='100%' >");

  tbSelMonth = getElementById("tbMonSelect");
  tbSelYear = getElementById("tbYearSelect");
  fDrawCal(giCellWidth);
  gCellSet = getElementsByTagName("A");
  fSetYearMon(giYear, giMonth);

  write("</table></DIV></td></TR><TR><TD align='center'>");
  write("<input type=button value='今天: 民國 "+LocalizedYear(giYear, 1)+" 年 "+giMonth+" 月 "+giDay+" 日' onclick='fSetDate(giYear,giMonth,giDay); this.blur();' class='today'>");
  write("</TD></TR></TD></TR><TR></TR></TABLE>");

  gfPop.width = getElementById("popTable").offsetWidth;
  gfPop.height = getElementById("popTable").offsetHeight;
}
// End -- Coded by Liming Weng, email: victorwon@netease.com -->
</script>
</body>
</html>

