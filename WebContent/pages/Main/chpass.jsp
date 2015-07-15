<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" height="100%" id="coansForm" style="z-index:32768; position:absolute; top:110px;">
	<tr>
		<td align="center">		
		
		<table class="hairLineTable" width="80%" height="60%">
			<tr height="60%">
				<td class="hairLineTdF" valign="top">
				
				
				
				
				<table>
					<tr>
						<td width="1"><img src="images/icon/exclamation.gif" /></td>
						<td class="gray_15">注意</td>
					</tr>
					<tr>
						<td></td>
						<td class="gray_15">依據《教育體系資通安全管理規範》 A.11.4.2 條文規定，<br>
						指示要求使用者定期更改通行密碼，最長不宜超過一學期。<br>
						您的密碼已超過期限或申請後未曾更新，請立即更新密碼！</td>
					</tr>
				</table>
				
				
				<table height="50">
					<tr>
						<td>
						
						</td>
					</tr>
				</table>
				
				<table align="center">
					<html:form action="/ChPass.do" method="post" onsubmit="init('更新中, 請稍後...')">
					<tr>
						<td class="gray_15">輸入新密碼，請輸入6個以上英文或數字</td>
					</tr>
					<tr>
						<td>
						
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="images/input_left.gif"/></td>
								<td><input class="colorInput" type="text" size="60" onKeyUp="check2();" name="password" id="password" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/></td>
								<td><img src="images/input_right.gif"/></td>
							</tr>
						</table>
						
						</td>
					</tr>
					<tr>
						<td class="gray_15">確認新密碼，請再輸入一次並確認</td>
					</tr>	
					<tr>
						<td>
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="images/input_left.gif"/></td>
								<td><input class="colorInput" type="text" size="60" onKeyUp="check2();" name="confirm" id="confirm" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/></td>
								<td><img src="images/input_right.gif"/></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td id="but" style="display:none;">
						<input type="submit" value="更新密碼" class="gSubmit" />
						</td>
					</tr>
					</html:form>
				</table>
				
				<table height="75">
					<tr>
						<td>
						
						</td>
					</tr>
				</table>
					
				
<script>
function check2(){
	if(document.getElementById("password").value.length>=6){
		if(document.getElementById("password").value!=""&&document.getElementById("confirm").value!=""){
			if(document.getElementById("password").value==document.getElementById("confirm").value){
				document.getElementById("but").style.display="inline";	
			}else{
				document.getElementById("but").style.display="none";	
			}		
		}	
	}else{
		document.getElementById("but").style.display="none";	
	}
}
</script>
				
				
				</td>				
			</tr>
		</table>
		
		</td>
	</tr>
</table>


<script>
	document.getElementById("loadMsg").style.display="inline";
	history.go(1);
</script>

<script>
	function openView(){
		document.getElementById("loadMsg").style.display="none";
		document.getElementById("coansForm").style.display="none";
	}
</script>

<script>
	function showLoginButton(){
		document.getElementById("loginIco").src="/CIS/pages/images/catch_button_over_white.gif";
		document.getElementById("loginLab").color="#f7941d";
	}

	function hideLoginButton(){

		document.getElementById("loginIco").src="/CIS/pages/images/catch_button_white.gif";
		document.getElementById("loginLab").color="#000000";

	}
</script>

<script>
	function showQust(id){
		if(document.getElementById(id).style.display=='none'){
			document.getElementById(id).style.display='inline';
		}else{
			document.getElementById(id).style.display='none';
		}
		document.getElementById('loadMsg').style.height=document.body.scrollHeight;
		document.getElementById('loadMsg').style.width=document.body.scrollWidth;
	}
</script>


<script>
var scrollHeight=document.body.scrollHeight;
var clientHeight=document.body.clientHeight;
//document.getElementById('loadMsg').style.width=document.body.scrollWidth*1.1;
//document.getElementById('loadMsg').style.height=document.body.scrollHeight*30;
document.getElementById('loadMsg').style.width=document.body.clientWidth;
document.getElementById('loadMsg').style.height=document.body.clientHeight*3;
//document.getElementById('loadMsg').style.height=scrollHeight+50;// IE
//document.getElementById('loadMsg').style.height=clientHeight+50;// IE
//document.getElementById('loadMsg').style.height=document.body.scrollHeight+50;// FF, ns
</script>