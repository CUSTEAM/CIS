<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ page import="org.apache.struts.Globals"%>
<%@ include file="/taglibs.jsp"%>	
<link href="/CIS/pages/course/export/SWFUpload/css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/CIS/pages/course/export/SWFUpload/swfupload.js"></script>
<script type="text/javascript" src="/CIS/pages/course/export/SWFUpload/js/handlers.js"></script>
<script type="text/javascript">
	var swfu;
	window.onload = function () {
		swfu = new SWFUpload({
			// Backend Settings
			upload_url: "/CIS/UploadStdImage.do",
			//post_params: {"PHPSESSID": "<?php echo session_id(); ?>"},

			// File Upload Settings
			file_size_limit : "10 MB",	
			file_types : "*.jpg",
			file_types_description : "JPG Images",
			file_upload_limit : "0",

			// Event Handler Settings - these functions as defined in Handlers.js
			//  The handlers are not part of SWFUpload but are part of my website and control how
			//  my website reacts to the SWFUpload events.
			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadComplete,

			// Button Settings
			button_image_url : "/CIS/pages/course/export/SWFUpload/images/SmallSpyGlassWithTransperancy_17x18.png",
			button_placeholder_id : "spanButtonPlaceholder",
			button_width: 180,
			button_height: 18,
			button_text : '選擇檔案(可複選), 檔名為學號',
			button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
			button_text_top_padding: 0,
			button_text_left_padding: 18,
			button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
			button_cursor: SWFUpload.CURSOR.HAND,
			
			// Flash Settings
			flash_url : "/CIS/pages/course/export/SWFUpload/swfupload.swf",

			custom_settings : {
				upload_target : "divFileProgressContainer"
			},
			
			// Debug Settings
			debug: false
		});
	};
</script>



fff

<table width="100%" cellspacing="0" cellpadding="0" border="0"> 			  	
 	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:5 5 0 5;"><img src="images/icon/camera.gif"/></div>
		<div nowrap style="float:left; padding: 5;"><font class="gray_15">學生照片批次建檔</font></div>		
		</td>
	</tr>
 			  	 
	<tr>
		<td style="padding:10px 0 0 0">
				
		
		<!-- 內容 -->
		
		<div id="content">			
				
			<form id="form1" runat="server">					
				<div id="content">							
			    <div id="swfu_container" style="margin: 0px 10px;">
				    
				    <table class="hairLineTable" align="left">
						<tr>
							<td class="hairLineTdF">
							<input type="file" name="Filedata" id="spanButtonPlaceholder" />
				    		</td>
				    	</tr>
				    </table>
				   
				   
				    <div id="divFileProgressContainer">
				    
				    </div>
				    
				     
				    <div id="thumbnails"></div>
			    </div>
				</div>
		    </form>							
				
				<div id="divFileProgressContainer" style="height: 75px;"></div>
			<div id="thumbnails"></div>
		</div>
		
		
			<!-- 內容 -->
		
 					
 					
 					
 		</td>
 	</tr>
 	<tr height="30">
		<td class="fullColorTable">		
			
		</td>
	</tr>
</table>			
 					
 					
 					
 					
 					
 					
 			
  

