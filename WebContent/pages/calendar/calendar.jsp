<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html>
  	<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">	      
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script src="/CIS/pages/calendar/scheduler/dhtmlxscheduler.js" type="text/javascript" charset="utf-8"></script>
	<script src="/CIS/pages/calendar/scheduler/ext/dhtmlxscheduler_recurring.js" type="text/javascript" charset="utf-8"></script>
	<script src="/CIS/pages/calendar/scheduler/ext/dhtmlxscheduler_editors.js" type="text/javascript" charset="utf-8"></script>
	<script src="/CIS/pages/calendar/scheduler/ext/dhtmlxscheduler_minical.js" type="text/javascript" charset="utf-8"></script>
	<script src="/CIS/pages/calendar/scheduler/ext/dhtmlxscheduler_dhx_terrace.js" type="text/javascript" charset="utf-8"></script>	
	<script src="/CIS/pages/calendar/scheduler/access.js" type="text/javascript" charset="utf-8"></script>
	     
	<link rel="stylesheet" href="/CIS/pages/calendar/scheduler/dhtmlxscheduler_dhx_terrace.css">	
	<link rel="stylesheet" href="/CIS/pages/calendar/ext/dhtmlxscheduler_recurring.css" type="text/css" media="screen" title="no title" charset="utf-8">
	
	<style type="text/css" media="screen">
	html, body {margin: 0px; padding: 0px; height: 100%; overflow: hidden;}			
	.dhx_cal_event.past_event div{background-color:#AAAAAA !important;color:#FFFFFF !important;}		
	.dhx_cal_event_line.past_event{background-color:#AAAAAA !important;color:white !important;}		
	.dhx_cal_event_clear.past_event{color:#AAAAAA !important;}
	</style>
	
	<script type="text/javascript" charset="utf-8">
		function init() {
			scheduler.config.multi_day = true;
			scheduler.config.fix_tab_position = false; // 看要不要改位置
			scheduler.config.minicalendar.mark_events = false; // 不匯入mini日曆
			scheduler.config.full_day = true;
			scheduler.config.xml_date = "%Y-%m-%d %H:%i";
			scheduler.init('myCalendar', new Date(), "week");
			
			scheduler.config.cascade_event_display = true; //時間衝突遮蔽
			
			scheduler.attachEvent("onEventAdded", addEvent);//新增方法
        	scheduler.attachEvent("onEventChanged", changeEvent);
        	scheduler.attachEvent("onEventDeleted", deleteEvent);
        	
        	scheduler.config.prevent_cache = true;    		
    		scheduler.config.details_on_create=true;
    		scheduler.config.details_on_dblclick=true;
			
			//工具欄位			
			scheduler.config.lightbox.sections=[	
      			{name:"description", height:43, map_to:"text", type:"textarea" , focus:true},
      			{name:"detail", height:130, type:"textarea", map_to:"details" },
      			{name:"members", height:86, type:"textarea", map_to:"members" },
      			{name:"recurring", type: "recurring", map_to: "rec_type", button: "recurring"},      			
      			{name:"time", height:72, type:"time", map_to:"auto"}
      		];
			
      		scheduler.locale.labels.section_detail="細節";
      		scheduler.locale.labels.section_members="參與者";
      		scheduler.locale.labels.reccuring="週期";			
			scheduler.load("/CIS/Print/MyCalendar.do?"+Math.floor(Math.random()*999));
			
			
			scheduler.templates.event_class=function(start,end,event){
	 		   if (start < (new Date())) //if date in past
	        	  return "past_event"; //then set special css class for it
			};
		}
	</script>
</head>
<body onload="init();">
<div id="myCalendar" class="dhx_cal_container" style='width:100%; height:100%;'>
	<div class="dhx_cal_navline">
		<div class="dhx_cal_prev_button">&nbsp;</div>
		<div class="dhx_cal_next_button">&nbsp;</div>
		<div class="dhx_cal_today_button"></div>
		<div class="dhx_cal_date"></div>
		<div class="dhx_cal_tab dhx_cal_tab_first" name="day_tab" style="left:14px;"></div>
		<div class="dhx_cal_tab" name="week_tab" style="left:75px;"></div>
		<div class="dhx_cal_tab dhx_cal_tab_last" name="month_tab" style="left:136px;"></div>		
	</div>
	
	<div class="dhx_cal_header">
	</div>
	<div class="dhx_cal_data">
	</div>
</div>
<div style="position:absolute; bottom:14px; right:14px; padding:5px; color:#444444;">
	<div style="float:left; backGround:#444444; width:16px; height:16px; margin:5px;"></div>
	<div style="float:left; margin:2px;">循環</div>
	<div style="float:left; backGround:#AAAAAA; width:16px; height:16px; margin:5px;"></div>
	<div style="float:left; margin:2px;">過期</div>
	<div style="float:left; backGround:#FFAD46; width:16px; height:16px; margin:5px;"></div>
	<div style="float:left; margin:2px;">受邀</div>
	<div style="float:left; backGround:#5484ED; width:16px; height:16px; margin:5px;"></div>
	<div style="float:left; margin:2px;">個人</div>
</div>

<div style="position:absolute; bottom:14px; padding:5px; color:#444444;">
	<div style="float:left; margin:2px;"><img src="/CIS/pages/images/icon_info.gif"/></div>
	<div style="float:left; margin:2px;">滑鼠點擊時間欄點2下，或滑鼠拖拉時間欄，即可開始編輯</div>
</div>
</body>
</html>