<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<br>
		<table cellpadding="0" cellspacing="0" border="0" width="100%">					
			<tr>
				<td>
				<table width="100%" cellpadding="0" cellspacing="0" style="cursor:pointer;" onClick="checkView('editBanner')">
		  			<tr>
		  				<td width="10" align="left" nowrap>
		      			<hr noshade class="myHr"/>
		    			</td>
		    			<td width="24" align="center" nowrap>
		    			<img src="images/icon/application_go.gif" />
		    			</td>
		    			<td nowrap>
		    			<font class="gray_15">橫幅管理</font>
		    			</td>
		    			<td width="100%" align="left">
		      			<hr noshade class="myHr"/>
		    			</td>
		  			</tr>
				</table>				
				
				<table width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td id="editBanner" style="display:none;">
						
						<table class="hairLineTable" width="99%">
							<tr>
								<td class="hairLineTdF" width="30" align="center"><img src="images/icon/icon_info_exclamation.gif"/></td>
								<td class="hairLineTdF">下方編輯區域為目前設定的橫幅, 可以依照喜好設計, 編輯完成別忘了按下「儲存」</td>
							</tr>
						</table>
						
						<FCK:editor instanceName="banner" basePath="/pages/include/fckeditor">
							<jsp:attribute name="value">${mySite.banner}</jsp:attribute>
							<jsp:body>
								<FCK:config 
								SkinPath="skins/office2003/"
								ImageBrowserURL="../include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
								LinkBrowserURL="../include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
								FlashBrowserURL="../include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
								ImageUploadURL="/CIS/Simpleuploader?Type=Image" 
								LinkUploadURL="/CIS/Simpleuploader?Type=File" 
								FlashUploadURL="/CIS/Simpleuploader?Type=Flash"/>
							</jsp:body>
						</FCK:editor>
						<table class="hairLineTable" width="99%">
							<tr>
								<td class="hairLineTdF" width="30" align="center"><img src="images/icon/icon_info_exclamation.gif"/></td>
								<td class="hairLineTdF">設計不出漂亮的橫幅? 可以使用系統預設的橫幅, <b>請注意: 若選了系統預設值將會取代上方編輯區域的內容</b></td>
							</tr>
						</table>
						<c:forEach items="${banners}" var="b">				
						<table class="hairLineTable" width="99%" height="50">
							<tr height="1">
								<td class="hairLineTdF" width="1" nowrap>
								<input name="bannerTemp" type="radio" value="${b.Oid}">
														
								</td>
								<td class="hairLineTdF"  nowrap>
								${b.name}
								</td>
								<td class="hairLineTdF" width="100%" nowrap onClick="getBanner()">
								${b.template}						
								</td>					
							</tr>
						</table>				
						</c:forEach>
						
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