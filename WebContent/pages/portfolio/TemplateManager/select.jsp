<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<input type="hidden" name="checkTemplate" id="checkTemplate"/>
<script>
function checkOid(id){	
	if(document.getElementById("checkTemplate").value==id){
		document.getElementById("checkTemplate").value="";
	}else{	
		if(document.getElementById("checkTemplate").value==""){		
			document.getElementById("checkTemplate").value=id;
		}else{
			document.getElementById("temp"+document.getElementById("checkTemplate").value).checked=false;
			document.getElementById("checkTemplate").value=id;
		}	
	}
}
</script>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td width="10" align="left" nowrap>
  			<hr noshade class="myHr"/>
			</td>
			<td width="24" align="center" nowrap>
			<img src="images/icon/application_side_boxes.gif" />
			</td>
			<td nowrap style="cursor:pointer;" onClick="checkView('selectTemplate')">
			<font class="gray_15">樣式選擇</font>
			</td>
			<td width="100%" align="left">
  			<hr noshade class="myHr"/>
		</td>
	</tr>
</table>

<table width="100%" id="selectTemplate" style="display:none;">
	<tr>
		<td colspan="4">
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="30" align="center"><img src="images/icon/icon_info_exclamation.gif"/></td>
				<td class="hairLineTdF">沒有喜歡的嗎? 沒關係, 到「樣式設計」自訂一個特別的樣式吧!</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
<c:forEach items="${templates}" var="t" varStatus="ts">
		<td>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF">				
				<img src="images/template/${t.icon}" />
				</td>
			</tr>
			<tr>
				<td class="hairLineTdF">				
				<input type="checkBox" name="sysTemplate" onClick="checkOid('${t.Oid}')" id="temp${t.Oid}" />
				${t.name}
				</td>
			</tr>
		</table>
		
		</td>
<c:if test="${ts.count%4==0||ts.count==0}"></tr></c:if>

</c:forEach>
	<tr>
		<td colspan="4">
		
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
