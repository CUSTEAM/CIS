<script type="text/javascript">
//<!--

var arrOptions4LC = new Array();
var strLastValue4LC = "";
var theTextBox4LC;
var currentValueSelected4LC = -1;
	
function GiveOptions4LC() {
	var intKey = -1;
	if (window.event) {
		intKey = event.keyCode;
		theTextBox4LC = event.srcElement;
		// TypeAhead4LC(theTextBox4LC.value);
	}
		
	if (theTextBox4LC.value.length == 0) {
		HideTheBox4LC();
		strLastValue4LC = "";
		return false;
	}
	
	if (intKey == 13) {			
		GrabHighlighted4LC();
		theTextBox4LC.blur();
		return false;
	} else if (intKey == 38) {		
		MoveHighlight4LC(-1);
		return false;
	} else if (intKey == 40) {		
		MoveHighlight4LC(1);
		return false;
	}
	
	if (theTextBox4LC.value.length > 0) {
		strLastValue4LC = theTextBox4LC.value;
		TypeAhead4LC(theTextBox4LC.value);
	} else {		
		BuildList4LC(theTextBox4LC.value);
		// strLastValue4LC = theTextBox4LC.value;
	}
}

function callback4LC() {
	if (req.readyState == 4) {
	    if (req.status == 200) {
	        parseMessage4LC();
		} else {
			alert("Now can not connect to Server : " + req.statusText);
        }       
    } else {
    	
    }
}
   
function parseMessage4LC() {
	var xmlDoc = req.responseXML.documentElement;
	if (xmlDoc != null) {
    	var node = xmlDoc.getElementsByTagName('name');
    	arrOptions4LC = new Array();
    	for (var i = 0; i < node.length; i++) {
    		arrOptions4LC[i] = node[i].firstChild.nodeValue;
    	}
    
    	BuildList4LC(theTextBox4LC.value);
    	strLastValue4LC = theTextBox4LC.value;
    }
}

function BuildList4LC(theText) {
	SetElementPosition4LC();
	var inner = "";
	var theMatches = MakeMatches4LC(theText);
	for(var i = 0; i < theMatches.length; i++) {
		 inner += theMatches[i];
	}
						
	if (theMatches.length > 0){	
		document.getElementById("spanOutput").innerHTML = inner;
		document.getElementById("OptionsList_0").className = "spanHighElement";
		currentValueSelected4LC = 0;
	} else {
		HideTheBox4LC();
	}
}
	
function SetElementPosition4LC() {
	var selectedPosX = 0;
	var selectedPosY = 0;
	var theElement = document.AJAXForm.txtUserInput4LC;
	var theTextBoxInt = document.AJAXForm.txtUserInput4LC;
	if (!theElement) return;
	var theElemHeight = theElement.offsetHeight;
	var theElemWidth = theElement.offsetWidth;
	while (theElement != null) {
		selectedPosX += theElement.offsetLeft;
		selectedPosY += theElement.offsetTop;
		theElement = theElement.offsetParent;
	}
	
	xPosElement = document.getElementById("spanOutput");
	xPosElement.style.left = selectedPosX;
	xPosElement.style.width = theElemWidth;
	xPosElement.style.top = selectedPosY + theElemHeight
	xPosElement.style.display = "block";
}
		
var countForId = 0;
	
function MakeMatches4LC(xCompareStr) {
	countForId = 0;
	var matchArray = new Array();
	
	for (var i = 0; i < arrOptions4LC.length; i++) {
		var regExp = new RegExp(xCompareStr, "ig");
		if ((arrOptions4LC[i].search(regExp)) >= 0) {			
			matchArray[matchArray.length] = CreateUnderline4LC(arrOptions4LC[i], xCompareStr, i);
		} else {
			continue;
		}
	}

	return matchArray;
}
	
var undeStart = "<span class='spanMatchText'>";
var undeEnd = "</span>";
var selectSpanStart = "<span	 style='width:100%;display:block;' class='spanNormalElement' onmouseover='SetHighColor4LC(this)' ";
var selectSpanEnd ="</span>";
	
function CreateUnderline4LC(xStr,xTextMatch,xVal) {
	selectSpanMid = "onclick='SetText4LC(" + xVal + ")'" + " id='OptionsList_" + countForId + "' theArrayNumber='"+ xVal +"'>";
	countForId++;
	var regExp = new RegExp(xTextMatch, "ig");
	var start = xStr.search(regExp);
	var matchedText = xStr.substring(start,start + xTextMatch.length);
	var Replacestr = xStr.replace(regExp, undeStart + matchedText + undeEnd);
	return selectSpanStart + selectSpanMid + Replacestr+ selectSpanEnd;
}
	
function SetHighColor4LC(theTextBox4LC) {
	if (theTextBox4LC) {
		currentValueSelected4LC =
			theTextBox4LC.id.slice(theTextBox4LC.id.indexOf("_") + 1,
			theTextBox4LC.id.length);
	}
	for(var i = 0; i < countForId; i++) {
		document.getElementById('OptionsList_' + i).className = 'spanNormalElement';
	}
	document.getElementById('OptionsList_' + currentValueSelected4LC).className = 'spanHighElement';
}
	
function SetText4LC(xVal) {
	theTextBox4LC = document.AJAXForm.txtUserInput4LC;
	theTextBox4LC.value = arrOptions4LC[xVal]; //set text value
	document.getElementById("spanOutput").style.display = "none";
	currentValueSelected4LC = -1; //remove the selected index
}
	
function GrabHighlighted4LC() {
	if (currentValueSelected4LC >= 0) {
		xVal = document.getElementById("OptionsList_" + currentValueSelected4LC).getAttribute("theArrayNumber");
		SetText4LC(xVal);
		HideTheBox4LC();
	}
}
	
function HideTheBox4LC() {
	document.getElementById("spanOutput").style.display = "none";
	currentValueSelected4LC = -1;
}
	
function MoveHighlight4LC(xDir) {
	if (currentValueSelected4LC >= 0) {
		newValue = parseInt(currentValueSelected4LC) + parseInt(xDir);
		if(newValue > -1 && newValue < countForId) {
			currentValueSelected4LC = newValue;
			SetHighColor4LC (null);
		}
	}
}
	
function ReDraw4LC() {
	BuildList4LC(document.AJAXForm.txtUserInput4LC.value);
}
	
//-->
</script>	