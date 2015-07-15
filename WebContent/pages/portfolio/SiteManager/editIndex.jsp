<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<br>
		<table cellpadding="0" cellspacing="0" border="0" width="100%">					
			<tr>
				<td>
				<table width="100%" cellpadding="0" cellspacing="0" style="cursor:pointer;" onClick="checkView('editIndex')">
		  			<tr>
		  				<td width="10" align="left" nowrap>
		      			<hr noshade class="myHr"/>
		    			</td>
		    			<td width="24" align="center" nowrap>
		    			<img src="images/icon/house_star.gif" />
		    			</td>
		    			<td nowrap>
		    			<font class="gray_15">編輯歡迎頁</font>
		    			</td>
		    			<td width="100%" align="left">
		      			<hr noshade class="myHr"/>
		    			</td>
		  			</tr>
				</table>
				
				<table width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td id="editIndex" style="display:none;">
						
						<table class="hairLineTable" width="99%">
							<tr>
								<td class="hairLineTdF" width="30" align="center"><img src="images/icon/icon_info_exclamation.gif"/></td>
								<td class="hairLineTdF">下方編輯區域為目前設定的歡迎頁, 可以依照喜好設計, 編輯完成別忘了按下「儲存」</td>
							</tr>
						</table>
						
						<FCK:editor instanceName="index" toolbarSet="PageEditor" basePath="/pages/include/fckeditor">
							<jsp:attribute name="value">${myIndex}</jsp:attribute>
							<jsp:body>
								<FCK:config 
								SkinPath="skins/office2003/"
								ImageBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
								LinkBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
								FlashBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 				
								ImageUploadURL="/CIS/Simpleuploader?Type=Image" 
								LinkUploadURL="/CIS/Simpleuploader?Type=File" 
								FlashUploadURL="/CIS/Simpleuploader?Type=Flash"/>
							</jsp:body>
						</FCK:editor>
						
						
						
						
						
						
						<table class="hairLineTable" width="99%">
							<tr>
								<td class="hairLineTdF" align="right">
								<INPUT type="submit" name="method" value="<bean:message key='Save'/>" class="gSubmit">
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>