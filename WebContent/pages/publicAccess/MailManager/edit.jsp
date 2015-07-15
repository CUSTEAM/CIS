<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" nowrap width="80">收件者</td>
		<td class="hairLineTdF" width="100%">
		<textarea id="emailist" name="receiver" style="font-size:18px; width:100%" rows="1" onClick="if(this.rows==5){this.rows=1}else{this.rows=5}"></textarea>
		</td>
	</tr>
</table>

<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" nowrap width="80">主旨</td>
		<td class="hairLineTd" width="100%">
		<input type="text" name="subject" id="subject" style="font-size:18px; width:100%;"
		onMouseOver="showHelpMessage('郵件上顯示的主旨, 若空白則顯示系統預設主旨', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)"/></td>
	</tr>
</table>

<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td>
		<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
			<tr>
				<td id="ds_calclass"></td>
			</tr>
		</table>
		</td>
	</tr>	
	<tr>
		<td>		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" class="hairLineTable" nowrap width="80">附件</td>
				<td class="hairLineTdF" class="hairLineTable" nowrap width="100%">
				<input type="file" name="file1" id="file1" style="font-size:18px; width:100%;" onClick="showObj('file2')" />
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		
		<table id="file2" class="hairLineTable" style="display:none;" width="99%">
			<tr>
				<td class="hairLineTdF" class="hairLineTable" nowrap width="80">附件</td>
				<td class="hairLineTdF" class="hairLineTable" nowrap width="100%">
				<input type="file" name="file2" style="font-size:18px; width:100%;" onClick="showObj('file3')"/>
				</td>
			</tr>
		</table>
	</td>
	</tr>
	<tr>
		<td>
		
		<table id="file3" class="hairLineTable" style="display:none;" width="99%">
			<tr>
				<td class="hairLineTdF" class="hairLineTable" nowrap width="80">附件</td>
				<td class="hairLineTdF" class="hairLineTable" nowrap width="100%">
				<input type="file" name="file3" style="font-size:18px; width:100%;" onClick="showObj('file4')"/>
				</td>
			</tr>
		</table>
	</td>
	</tr>
	<tr>
		<td>
		<table id="file4" class="hairLineTable" style="display:none;" width="99%">
			<tr>
				<td class="hairLineTdF" class="hairLineTable" nowrap width="80">附件</td>
				<td class="hairLineTdF" class="hairLineTable" nowrap width="100%">
				<input type="file" name="file4" style="font-size:18px; width:100%;" onClick="showObj('file5')"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>		
		<table id="file5" class="hairLineTable" style="display:none;" width="99%">
			<tr>
				<td class="hairLineTdF" class="hairLineTable" nowrap width="80">附件</td>
				<td class="hairLineTdF" class="hairLineTable" nowrap width="100%">
				<input type="file" name="file5" style="font-size:18px; width:100%;" onClick="alert('檔案過多請以壓縮軟體合併');"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>	
	<tr>
    	<td>     	
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" class="hairLineTable" width="100%">
				<FCK:editor instanceName="content" toolbarSet="Basic" basePath="/pages/include/fckeditor">
					<jsp:attribute name="value"></jsp:attribute>
					<jsp:body><FCK:config SkinPath="skins/office2003/"/></jsp:body>
				</FCK:editor>
				</td>
			</tr>
		</table>
				
		</td>
	</tr>
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF">
				<input type="submit" name="method" value="<bean:message bundle="PSN" key='SendMail'/>" class="gSubmit">
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>