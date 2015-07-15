<script type="text/javascript">
<!--
var key;
var bar_color='gray';
var span_id="block";
var clear="&nbsp;&nbsp;&nbsp;";

function pollServer() {
	httpRequest("GET", pollurl, true, "pollCallback");
}

function pollCallback() {
	if(request.readyState== 4) {
		if(request.status == 200)  {
			var methodName = request.responseXML.getElementByTagName("methodName")[0].firstChild.data;
			var step = request.responseXML.getElementByTagName("step")[0].firstChild.data;
			var percentage = request.responseXML.getElementByTagName("percentage")[0].firstChild.data;
			var complete = request.responseXML.getElementByTagName("complete")[0].firstChild.data;
			
			document.getElementById("title").innerHTML = "部制：" + step;
			var index = processResult(percentage);
			for(var i=1; i<=index; i++) {
				var elem = document.getElementById("block" + i);
				elem.innerHTML = clear;
				elem.style.backgroundColor = bar_color;
				var netx_cell = i+1;
				if(next_cell >index && next_cell <= 9) {
					document.getElementById("block" + next_cell).innerHTML = percentage + "%";
				
				}
			}
			if (index < 9) {
				setTimeout("pollServer()", gpstimer);
			}
			if(complete=='yes') {
				document.getElementById("complete").innerHTML = "執行完畢!";
				document.getElementById("ok").disabled = false;
			}
		}
	}
	
}

function processResult(percentage) {
//取出百分比的值以10個block來表示
	var ind;
	if(percentage.length == 1){
		ind = 1;
	} else if(percentage.length == 2) {
		ind = percentage.substring(0,1);
	} else {
		ind = 9;
	}
	return ind;
}

function checkDiv() {
	var progess_bar =document.getElementById("progressBar");
	if(progress_bar.style.visibility == "visible") {
	clesrBar();
	document.getElementById("complete").innerHTML = "";
	} else {
		progress_bar.style.visibility == "visible";
	}
}

function clearBar() {
	for(var j = 1; j < 10; j++) {
		var elem = document.getElementById("block" + j);
		elem.innerHTML = clear;
		elem.style.backgroundColor = "White";
	}
}
//-->
</script>
