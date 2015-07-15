<%@ page language="java" pageEncoding="UTF-8"%>
<table id="helpMessage" class="ds_box" style="display:none">
	<tr>
		<td id="helpMessageInfo">
		
		
		</td>
	</tr>
</table>
<script>
	function getLeft(ed) {
					var tmp = ed.offsetLeft;
					ed = ed.offsetParent
				while(ed) {
					tmp += ed.offsetLeft;
					ed = ed.offsetParent;
					}
				return tmp;
				}
	
	function getTop(ed) {
					var tmp = ed.offsetTop;
					ed = ed.offsetParent
				while(ed) {
					tmp += ed.offsetTop;
					ed = ed.offsetParent;
					}
				return tmp+24;
				}



	function showHelpMessage(info, mode, id){
		
		document.getElementById('helpMessage').style.left=getLeft(document.getElementById(id))+5;	//y座標
 		document.getElementById('helpMessage').style.top=getTop(document.getElementById(id))-5;	//x座標
		document.getElementById('helpMessage').style.display=mode;
		document.getElementById('helpMessageInfo').innerHTML="";
		
		document.getElementById('helpMessageInfo').innerHTML=info;
	
	}
</script>