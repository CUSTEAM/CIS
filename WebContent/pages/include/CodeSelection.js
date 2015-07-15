<script language="javascript">
<!--
	function updateSelect(targetId, selValue) {
		selCtr = document.getElementById(targetId);
		selCtr.options[0].selected = true;
		for (i=1; i < selCtr.options.length; i++) {
			if (selValue == selCtr.options[i].value) {
				selCtr.options[i].selected = true;
				break;
			}
		}
	};
	
	function updateCode(targetId, selValue) {
		document.getElementById(targetId).value = selValue;
	};
		
// -->
</script>