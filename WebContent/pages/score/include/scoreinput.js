<script language="JavaScript">
<!--
function makeReadOnlyByName(compName)
{
	var ips = document.getElementsByName(compName);

  	for(var i=0; i<=ips.length-1;i++)
  	{
      	ips[i].readOnly=true;
  	}
};

function checkyn() 
{
	var ips = document.getElementById("yn");
	var oldyn = ips.value;
	if(ips.value=="no") ips.value="yes";
	else if(ips.value=="yes") ips.value="no";
	// alert("regsyn=" + oldyn + "," + ips.value);
};

function checkscore()
{
	var ips = document.getElementsByName("scrinput");
    var cks = document.getElementsByName("scrcheck");
	var stus = document.getElementsByName("studentNo");
	var msg = "";
	
  	for(var i=0; i<=ips.length-1;i++)
  	{
      	if(Math.round(ips[i].value)!=Math.round(cks[i].value)) {
      		msg = msg + "No:" + stus[i].value + ", Orig:" + Math.round(cks[i].value) + " , Check:" + Math.round(ips[i].value) + "\n";
    	}
  	}
  	if(msg=="")
  	{
    	alert("All score is right!!!");
  	}else
  	{
  		alert(msg);
  	}
  	
};

function d2int(tagName)
{
	var ips = document.getElementsByName(tagName);

  	for(var i=0; i<=ips.length-1;i++)
  	{
  		if(ips[i].value != "")
      		ips[i].value=Math.round(ips[i].value);
  	}

};

function generateScoreInput(tbid)
{
	var tbody = document.getElementById(tbid).getElementsByTagName("TBODY")[0]; 
	var tr = document.createElement("TR");

	var td1 = document.createElement("TD");
	td1.width="15";
	td1.innerHTML = "&nbsp;";
	tr.appendChild(td1);
	var td2 = document.createElement("TD");
	td2.innerHTML = "<input type='text' name='paramName["+idx+"]' value='' class='form_field' size='10' maxlength='20'>";
	tr.appendChild(td2);
	tbody.appendChild(tr);
	idx = idx+1;
};

function inputtransfer() 
{
  var ips = document.getElementsByName("scrinput");

  for(var i=0; i<=ips.length-1;i++)
  {
      if(ips[i].value!="") scrarray.push(Integer.parseInt(ips[j].value));
      else scrarray.push(0);
  }
  document.writeln("<Input type=\"hidden\" name=\"scores\" value=\"" + scarray + "\"");
  document.writeln("<Input type=\"hidden\" name=\"scrcount\" value=\"" + ips.length +"\"");
};

/* function chgFocus() 
{
	k=event.keyCode;
	
};

window.document.onkeydown=chgFocus();
*/
// -->
</script>