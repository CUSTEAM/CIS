<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<input type="hidden" name="checkGroupOid" id="checkGroupOid"/>
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td>
		
		
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr style="cursor:pointer;">
				<td width="10" align="left" nowrap>
		    		<hr noshade class="myHr"/>
		  		</td>
		  		<td width="24" align="center" nowrap onClick="showMod('fast')">
		 		<img src="images/icon/image_add.gif" id="add" 
		 		onMouseOver="showHelpMessage('...', 'inline', this.id)" 
			 	onMouseOut="showHelpMessage('', 'none', this.id)">
		  		</td>
		  		<td nowrap style="cursor:pointer;">
		  		建立學程&nbsp;
		  		</td>
		  		<td width="100%" align="left">
		    		<hr noshade class="myHr"/>
		  		</td>
			</tr>
		</table>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" align="center" width="30">
				<img src="images/icon/image.gif" />
				</td>
				<td class="hairLineTdF">
				建立新學程
				</td>
				<td class="hairLineTd">				
				<input type="text" name="cname" />
				</td>
				<td class="hairLineTdF">				
				<INPUT type="submit" name="method" value="<bean:message key='Create'/>" class="CourseButton" />
				</td>
			</tr>
		</table>
		
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr style="cursor:pointer;">
				<td width="10" align="left" nowrap>
		    		<hr noshade class="myHr"/>
		  		</td>
		  		<td width="24" align="center" nowrap onClick="showMod('fast')">
		 		<img src="images/icon/image_edit.gif" id="edit" 
		 		onMouseOver="showHelpMessage('...', 'inline', this.id)" 
			 	onMouseOut="showHelpMessage('', 'none', this.id)">
		  		</td>
		  		<td nowrap style="cursor:pointer;">
		  		現有學程&nbsp;
		  		</td>
		  		<td width="100%" align="left">
		    		<hr noshade class="myHr"/>
		  		</td>
			</tr>
		</table>

		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" align="center">
				<img src="images/icon/image_delete.gif" />
				</td>
				<td class="hairLineTdF">
				學程名稱
				</td>
				<td class="hairLineTdF">				
				英文名稱
				</td>
				<td class="hairLineTdF">				
				適用最後入學年
				</td>
			</tr>
<script>
function checkGroup(Oid){
	
	if(document.getElementById("checkGroupOid").value==Oid){
		document.getElementById("checkGroupOid").value="";
		
	}else{
		try{
			document.getElementById("groupOid"+document.getElementById("checkGroupOid").value).checked=false;
			document.getElementById("checkGroupOid").value=Oid;
		}catch(e){
			document.getElementById("checkGroupOid").value=Oid;
		}
		
		
	}
	
	

}
</script>			
			<c:forEach items="${allGroup}" var="ag">
			<tr>
				<td class="hairLineTdF" align="center">
				<input type="checkbox" name="checkGroupBox" id="groupOid${ag.Oid}" onClick="checkGroup('${ag.Oid}')"/>
				</td>
				<td class="hairLineTdF">
				${ag.cname}<input type="hidden" name="groupOid" id="groupOid${ag.Oid}" value="${ag.Oid}" />
				</td>
				<td class="hairLineTdF">				
				${ag.ename}
				</td>
				<td class="hairLineTdF">				
				${ag.entrance}
				</td>
			</tr>
			</c:forEach>
			
		</table>
		
		
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				<img src="images/printer.gif" border="0">
					<select name="reportType" onchange="jumpMenu('parent',this,0)">
					<option value="javascript:void(0)">報表選擇</option>
					<option value="/CIS/List4CsGroup?type=group">學程列表</option>
					<option value="/CIS/ListCsGroup4Now?type=all">本學期開課列表</option>
					</select>    							

				<script>
				<!--
				function jumpMenu(targ,selObj,restore){
						eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
						eval(targ+".location.target='_blank'");
						if (restore) selObj.selectedIndex=0;
					}
				//-->
				</script>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
			<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" class="CourseButton" />
		</td>
	</tr>
</table>