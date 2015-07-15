<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Individual/EditProfile" enctype="multipart/form-data" method="post" onsubmit="init('執行中, 請稍後')">
<!-- 標題列 start-->		
		
		<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/user_edit.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;個人聯絡資料&nbsp;(更改資料請洽人事室)<input type="hidden" name="exSearch" value="${HRmanagerForm.map.exSearch}" />
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	<tr>
		<td valign="top" align="center">
		
<script>
history.go(1);
</script>
<style>
#test{
    position:absolute;
    top:160px;
    right:10px;
    height:500px;
    filter:
        progid:DXImageTransform.Microsoft.Matrix(enabled=true,SizingMethod=clip to original,FilterType=nearest neighbor)
        FlipH(enabled=false)
        FlipV(enabled=false);
    }
</style>
<div id=test style="width:800px; height:800px;">	
	<table width="662">
	<tr height="508">
		<td background="images/CardReshoot.gif">
		
		<table>
			<tr height="300">
				<td width="170"></td>
				<td valign="bottom">
				<img src=/CIS/Personnel/getFTPhoto4Empl?idno=${myProfile.idno}&notEdit height=150  style="border-style:solid;border-width:1px;"/>
				
				</td>
				<td width="10"></td>
				<td valign="bottom">
				單位:
				<br><br>
				姓名: 
				<br><br>
				職稱:
				<br><br>
				<img src="http://ap.cust.edu.tw/CIS/getBarcode39?no=${myProfile.idno}" width="200" />
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>
	
	
</div>











<script>
function doRotate(sita,obj){
    var t=Math.PI*sita/180;
    var c=Math.cos(t);
    var s=Math.sin(t);
    with(obj.filters.item(0)){
        Dx=0;
        M11=c;
        M12=-1*s;
        M21=s;
        M22=c;
        }
    }
doRotate(15,test)
</script>
		
		<table width="100%">
			<tr>
				<td>		
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF">中文姓名</td>
						<td class="hairLineTdF"><input type="text" size="6" value="${myProfile.cname}" disabled/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF">英文姓名</td>
						<td class="hairLineTdF"><input type="text" name="ename" value="${myProfile.ename}" disabled/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" nowrap>
						識別照片
						</td>
						<td class="hairLineTd">
						<input size="4" type="file" name="image" id="image" 
						onMouseOver="showHelpMessage('請選擇「<b>證件用</b>」檔案(2吋大頭照), <br>不符合標準的檔案會被系統改變比例或切割', 'inline', this.id)" 
			   			onMouseOut="showHelpMessage('', 'none', this.id)"/>
						</td>
					</tr>
				</table>				
				
				</td>
			</tr>			
			<tr>
				<td>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF">室內電話</td>
						<td class="hairLineTd"><input type="text" name="telephone" value="${myProfile.telephone}" disabled/></td>
					</tr>
				</table>	
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF">行動電話</td>
						<td class="hairLineTd"><input type="text" name="CellPhone" value="${myProfile.CellPhone}" disabled/></td>
					</tr>
				</table>
				
				
				</td>
			</tr>
			
			<tr>
				<td>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" nowrap>戶籍地址</td>
						<td class="hairLineTd"><input size="5" type="text" name="pzip" value="${myProfile.pzip}" disabled/></td>
						<td class="hairLineTd"><input size="48" type="text" name="paddr" value="${myProfile.paddr}" disabled/></td>
					</tr>
				</table>
				
				</td>
			</tr>
			
			<tr>
				<td>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" nowrap>現居地址</td>
						<td class="hairLineTd"><input size="5" type="text" name="czip" value="${myProfile.czip}" disabled/></td>
						<td class="hairLineTd"><input size="48" type="text" name="caddr" value="${myProfile.caddr}" disabled/></td>
					</tr>
				</table>
				
				</td>
			</tr>
			
			<tr>
				<td>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" nowrap>電子郵件</td>
						<td class="hairLineTd"><input size="48" type="text" name="Email" value="${myProfile.Email}" disabled/></td>
					</tr>
				</table>
				
				</td>
			</tr>
		</table>		
		
		</td>
	</tr>
	
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<INPUT type="submit"
			   name="method" id="Save"
			   value="<bean:message
			   key='Save'/>"
			   class="gSubmit" 
			   onMouseOver="showHelpMessage('將以上資料儲存', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
			   
		<INPUT type="submit"
			   name="method" id="Cancel"
			   value="<bean:message
			   key='Cancel'/>"
			   class="gCancle" 
			   onMouseOver="showHelpMessage('不儲存並且重新載入資料', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
		
		</td>
	</tr>
	
	
</html:form>
</table>

<!--  
<script type="text/javascript" src="include/tjpzoom.js"></script>
<script type="text/javascript" src="include/tjpzoom_config_natgeo.js"></script>
<img src=/CIS/Personnel/getFTPhoto4Empl?idno=${myProfile.idno} onmouseover="TJPzoom(this);"/>
-->


				