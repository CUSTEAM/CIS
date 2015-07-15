<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<br>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td width="10" align="left" nowrap>
  			<hr noshade class="myHr"/>
			</td>
			<td width="24" align="center" nowrap>
			<img src="images/icon/wrench.gif" />
			</td>
			<td nowrap style="cursor:pointer;" onClick="checkView('editTemplate');">
			<font class="gray_15">樣式設計</font>
			</td>
			<td width="100%" align="left">
  			<hr noshade class="myHr"/>
		</td>
	</tr>
</table>
<table width="100%">
	<tr>
		<td id="editTemplate" style="display:none;">
		
		

<FCK:editor instanceName="userTemplate" toolbarSet="TemplatEditor" basePath="/pages/include/fckeditor">
	<jsp:attribute name="value">${myTemplate.template}</jsp:attribute>
	<jsp:body>
		<FCK:config
		SkinPath="skins/office2003/"
		ImageBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
		LinkBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
		FlashBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
		ImageUploadURL="/CIS/Simpleuploader?Type=Image" 
		LinkUploadURL="/CIS/Simpleuploader?Type=File" 
		FlashUploadURL="/CIS/Simpleuploader?Type=Flash"
		Height="300px"/>
	</jsp:body>
</FCK:editor>



<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" align="right">
		<INPUT type="submit" name="method" value="<bean:message key='Save'/>" <c:if test="${notExist}">disabled</c:if> class="gSubmit">
		</td>
	</tr>
</table>


		</td>
	</tr>
</table>