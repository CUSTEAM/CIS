<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="Portfolio/PageManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">

	<tr>
		<td class="fullColorTable" width="100%">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr height="30">
					<td width="28" align="right">
						<img src="images/folder_lock.gif">
					</td>
					<td nowrap>&nbsp;文章管理</td>
					<td>
						<table align="right">
							<tr>							
								<td width="1"><img src="images/icon/zoomout.gif"></td>
								<td><a href="${myUrl}" target="_blank">立即檢視</a></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
<script>
function showNewTag(value){

	if(value=="*"){
		document.getElementById("newTag").style.display="inline";
		document.getElementById("checkNewTag").value="*";	
	}else{
		document.getElementById("newTag").style.display="none";
		document.getElementById("checkNewTag").value="";		
	}
}
</script>
<c:if test="${update==null}">
	<tr>
		<td>		
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				選擇分類
				</td>
				<td class="hairLineTdF">			
				
				<select name="tag" onChange="showNewTag(this.value)">
					<option value="">選擇分類</option>
				<c:forEach items="${myTag}" var="t">				
				<option value="${t.tag}">${t.tag}</option>
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
				<input name="title" type="text" style="width:100%"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
		
		<FCK:editor instanceName="content" toolbarSet="PageEditor" basePath="/pages/include/fckeditor">
			<jsp:attribute name="value">歡迎使用 <strong>${SchoolName_ZH}e-Portfolio系統
				</strong>. 試著寫篇文章吧!</a>.
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
				<td class="hairLineTdF" align="right"><INPUT type="submit" name="method" value="<bean:message key='Create'/>" class="gSubmit"></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
		
		
		</td>
	</tr>
	
	
	<c:if test="${!empty myPages}">
	<tr>
		<td id="help" style="cursor:pointer;" onClick="hidHepl();">
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="30" align="center"><img src="images/icon/icon_info_exclamation.gif"/></td>
				<td class="hairLineTdF">點擊下方的分類名稱即可開啟文章列表</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
<script>
function hidHepl(){
	document.getElementById("help").style.display="none";
}
</script>		
		<c:forEach items="${myPages}" var="m" varStatus="c">
		
		<br>
		<table cellpadding="0" cellspacing="0" border="0" width="100%" style="cursor:pointer;" onClick="showObj('class${c.count}');">					
			<tr>
				<td>
				<table width="100%" cellpadding="0" cellspacing="0">
		  			<tr>
		  				<td width="10" align="left" nowrap>
		      			<hr noshade class="myHr"/>
		    			</td>
		    			<td width="24" align="center" nowrap>
		    			<img src="images/folder_star.gif" />
		    			</td>
		    			<td nowrap>
		    			${m.tag}&nbsp;
		    			</td>
		    			<td width="100%" align="left">
		      			<hr noshade class="myHr"/>
		    			</td>
		  			</tr>
				</table>
				
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" width="99%" id="class${c.count}" style="display:none;">			
			<c:forEach items="${m.pages}" var="s">
			<tr>
				
				<td class="hairLineTdF" width="100%">${s.title}</td>
				
				<td class="hairLineTdF" nowrap>${s.editime}</td>
				<td class="hairLineTdF" nowrap><a href="../Portfolio/PageManager.do?updateId=${s.Oid}">				
				更新</a> - <a onclick="return confirm('即將刪除《${s.title}》')" href="../Portfolio/PageManager.do?delId=${s.Oid}">刪除</a>				
				</td>
			</tr>
			</c:forEach>
		</table>
		
		</c:forEach>		
		<br>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>
	</c:if>
	</c:if>

<c:if test="${update!=null}">	
<!-- 文章管理 -->
	<tr>
		<td>
		
		<%@ include file="PageManage/edit.jsp"%>		
		
		</td>
	</tr>
</c:if>
</html:form>
</table>
