<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<table width="90%" id="coansForm" align="center" style="z-index:32768; position:absolute; left:62px; top:100px">
	<tr height="100%">
		<td>

		<table class="hairLineTable" width="99%">
		<tr>
		<td class="hairLineTdF">


			<table width="100%" height="100%" align="center">
				<tr>
					<td align="center" valign="top">


<!-- form start-->
<table width="100%">
	<tr>
		<td align="left" valign="top">
		
		<table class="hairLineTable" width="99%">
			<tr>
				
				<td class="hairLineTdF">親愛的同學：本問卷主要是想瞭解同學們對於本學期課程學習方面的意見。
				妳(你)的寶貴意見，將有助於學校瞭解所提供的學習課程，是否符合同學們的需要，調查結果將做為未來教學改進之參考。
				我們需要的是同學們內心真實的想法，你(妳)的誠懇作答，能使我們獲得有用而寶貴的資料，也可以讓學校行政單位及教師瞭解同學們真正的心聲。
				填答時不需填寫姓名，請同學放心做答。<font color="red"><b>題目中包含偵錯題請認真填答，才能送出問卷</b></font>，謝謝同學們的合作。<b>教務處 敬啟</b></td>
			</tr>
		</table>
		
			
		<c:if test="${fn:length(myQuestion)>0}"><%@ include file="CourseQuestionaryForm/course.jsp"%></c:if>
		<!-- c:if test="{fn:length(mySeQuestion)>0}"-->
		<!--%@ include file="CourseQuestionaryForm/self.jsp"%-->
		<!--/c:if-->
		<%@ include file="CourseQuestionaryForm/totur.jsp"%>
		
		
		
		</td>
	</tr>
	<tr>
		<td align="center">
		<!--  
		<img src="/CIS/pages/images/catch_button_white.gif" id="loginIco"
		            		 		 onMouseOver="showLoginButton()" onMouseOut="hideLoginButton()" onClick="document.forms[0].submit()" /><br><font id="loginLab" onMouseOver="showLoginButton()"
		            		 		 onMouseOut="hideLoginButton()" onClick="document.forms[0].submit()">送出問卷</font>
		-->
		
		</td>
	</tr>
</table>


<!-- form END-->

					</td>
				</tr>
			</table>


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
document.getElementById('loadMsg').style.width=document.body.scrollWidth*1.1;
document.getElementById('loadMsg').style.height=scrollHeight+50;// IE
document.getElementById('loadMsg').style.height=clientHeight+50;// IE
document.getElementById('loadMsg').style.height=document.body.scrollHeight+50;// FF, ns
</script>