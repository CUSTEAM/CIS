<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				移動分類
				</td>
				<td class="hairLineTdF">			
				
				<select name="tag" onChange="showNewTag(this.value)">
					<option value="">選擇分類</option>
				<c:forEach items="${myTag}" var="t">				
				<option <c:if test="${update.tag==t.tag}">selected</c:if> value="${t.tag}">${t.tag}</option>
				</c:forEach>				
					<option value="*">建立新分類</option>
				</select>
				
				</td>
				
				<td class="hairLineTdF" id="newTag" style="display:none;">
				<input name="newTag" type="text"/><input type="hidden" name="checkNewTag" id="checkNewTag"/>
				</td>
			</tr>
		</table>				
		
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" align="center" nowrap>
				文章標題
				</td>
				<td class="hairLineTdF" width="100%">
				<input name="title" type="text" value="${update.title}" style="width:100%"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
		<input type="hidden" name="Oid" value="${update.Oid}"/> 
		<FCK:editor instanceName="content" toolbarSet="PageEditor" basePath="/pages/include/fckeditor">
			<jsp:attribute name="value">${update.content}
			</jsp:attribute>
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
				<INPUT type="submit" name="method" value="<bean:message key='Update'/>" class="gSubmit">
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		
		</td>
	</tr>
</table>
