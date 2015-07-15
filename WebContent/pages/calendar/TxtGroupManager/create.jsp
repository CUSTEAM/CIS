<%@ page language="java" import="java.util.*" pageEncoding="BIG5"%>
<table width="100%">
	<tr>
		<td>		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" align="center" nowrap>群組名稱</td>
				<td class="hairLineTdF" width="100%">
				<input type="text" name="name" style="width:100%" />
				<input type="hidden" name="delOid" />
				</td>
			</tr>
		
			<tr>
				<td class="hairLineTdF" align="center" nowrap>群組人員</td>
				<td class="hairLineTdF" width="100%">
				<textarea name="members" style="width:100%" onClick="this.rows=10;" rows="1"></textarea>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>