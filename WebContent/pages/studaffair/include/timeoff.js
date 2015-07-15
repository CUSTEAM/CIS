<script language="javascript">
//<!--
	var iplimit = 1;
	function nextfocus(ntab) {
		var code = event.keyCode;
		var iptx1s = document.getElementById("1");
		if(code==46 || code==8) {
			return;
		}
		if(code==37 || code==38) {
			if(ntab==2) return;
			ntab = ntab -2;
		} else if(code==40) {
		
		}
		if(ntab > iplimit) return;
		var nextElem = document.getElementById(ntab);
		nextElem.focus();
	};
//-->
</script>
