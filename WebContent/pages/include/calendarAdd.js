function inputMember(id, id1){
	document.getElementById(id).value=document.getElementById(id).value+document.getElementById("g"+ document.getElementById(id1).value).innerHTML;
}

function checkDel(Oid){
	if(document.getElementById("delOid"+Oid).value==""){
		document.getElementById("delOid"+Oid).value=Oid;
	}else{
		document.getElementById("delOid"+Oid).value="";	
	}
}

function cp(id, name, name1){
	document.getElementById(id).value=document.getElementById(name).value+document.getElementById(name1).value;
	document.getElementById(name1).style.display="inline";
}

function opFile(id){
	document.getElementById(id).innerHTML=document.getElementById("addFile").innerHTML;
}

document.write("<OBJECT id=\"dlgHelper\" CLASSID=\"clsid:3050f819-98b5-11cf-bb82-00aa00bdce0b\" width=\"0px\" height=\"0px\"></OBJECT>");
var ecolorPopup=null;

function colordialogmouseout(obj){
    obj.style.borderColor="";
    obj.bgColor="";
}
 
function colordialogmouseover(obj){
    obj.style.borderColor="#0A66EE";
    obj.bgColor="#EEEEEE";
}
 
function colordialogmousedown(color){
	ocolorPopup.style.display="inline";
    ecolorPopup.value=color;
    //ocolorPopup.document.body.blur();
    
    //alert(document.getElementById("tpad").bgColor);
    document.getElementById("tpad").bgColor="#"+color;
}
 
function colordialogmore(){
    
    var sColor=document.getElementById("dlgHelper").ChooseColorDlg(ecolorPopup.value);
    
    sColor = sColor.toString(16);
    if (sColor.length < 6) {
        var sTempString = "000000".substring(0,6-sColor.length);
        sColor = sTempString.concat(sColor);
    }
    ecolorPopup.value="#"+sColor.toUpperCase();
    document.getElementById("tpad").bgColor="#"+sColor.toUpperCase();
    //alert(document.getElementById("tpad").bgColor);
    //document.body.bgColor="#"+sColor.toUpperCase();
    //ocolorPopup.document.body.blur();
    ocolorPopup.style.display="inline";
}
 
function colordialog(e){  
    try{
    	e=event.srcElement;
    }catch(x){
    	e=e.target;
    }
        
    e.onkeyup=colordialog;
    ecolorPopup=e;
    var ocbody;
    //var oPopBody = ocolorPopup.document.body;
    var colorlist=new Array(40);
    ocolorPopup.style.backgroundColor = "#f9f8f7";
    ocolorPopup.style.border = "solid #999999 1px";
    ocolorPopup.style.fontSize = "12px";
 
    colorlist[0]="#000000";    colorlist[1]="#993300";    colorlist[2]="#333300";    colorlist[3]="#003300";
    colorlist[4]="#003366";    colorlist[5]="#000080";    colorlist[6]="#333399";    colorlist[7]="#333333";
 
    colorlist[8]="#800000";    colorlist[9]="#FF6600";    colorlist[10]="#808000";colorlist[11]="#008000";
    colorlist[12]="#008080";colorlist[13]="#0000FF";colorlist[14]="#666699";colorlist[15]="#808080";
 
    colorlist[16]="#FF0000";colorlist[17]="#FF9900";colorlist[18]="#99CC00";colorlist[19]="#339966";
    colorlist[20]="#33CCCC";colorlist[21]="#3366FF";colorlist[22]="#800080";colorlist[23]="#999999";
 
    colorlist[24]="#FF00FF";colorlist[25]="#FFCC00";colorlist[26]="#FFFF00";colorlist[27]="#00FF00";
    colorlist[28]="#00FFFF";colorlist[29]="#00CCFF";colorlist[30]="#993366";colorlist[31]="#CCCCCC";
 
    colorlist[32]="#FF99CC";colorlist[33]="#FFCC99";colorlist[34]="#FFFF99";colorlist[35]="#CCFFCC";
    colorlist[36]="#CCFFFF";colorlist[37]="#99CCFF";colorlist[38]="#CC99FF";colorlist[39]="#FFFFFF";
 
    ocbody = "";
    ocbody += "<table CELLPADDING=0 CELLSPACING=3>";
    ocbody += "<tr height=\"20\" width=\"20\"><td align=\"center\"><table style=\"border:1px solid #808080;\" width=\"12\" height=\"12\" id='tpad' bgcolor='#FFFFFF'><tr><td></td></tr></table></td><td align=\"center\" height=\"22\" colspan=\"8\" onMouseOut=\"parent.colordialogmouseout(this);\" onMouseOver=\"parent.colordialogmouseover(this);\" style=\"border:1px solid;font-size:12px;cursor:pointer;\" onMouseDown=\"parent.colordialogmore()\">選擇其它顏色</td></tr>";
    for(var i=0;i<colorlist.length;i++){
        if(i%8==0)
            ocbody += "<tr>";
        ocbody += "<td width=\"14\" height=\"16\" style=\"border:1px solid;\" onMouseOut=\"parent.colordialogmouseout(this);\" onMouseOver=\"parent.colordialogmouseover(this);\" onMouseDown=\"parent.colordialogmousedown('"+colorlist[i]+"')\" align=\"center\" valign=\"middle\"><table style=\"border:1px solid #808080;\" width=\"12\" height=\"12\" bgcolor=\""+colorlist[i]+"\"><tr><td></td></tr></table></td>";
        if(i%8==7)
            ocbody += "</tr>";
    }
    ocbody += "<tr><td align=\"center\" height=\"22\" colspan=\"8\" onMouseOut=\"parent.colordialogmouseout(this);\" onMouseOver=\"parent.colordialogmouseover(this);\" style=\"border:1px solid;font-size:12px;cursor:pointer;\" onMouseDown=\"showObj('ocolorPopup')\">關閉</td></tr>";
    ocbody += "</table>";
 
    ocolorPopup.innerHTML=ocbody;
    //ocolorPopup.show(e.offsetLeft, e.offsetTop+e.offsetHeight, 158, 147, document.body);
    
    //ocolorPopup.show(getLeft(e), getTop(e), 158, 147, document.body);
    ocolorPopup.style.left=getLeft(e);
    ocolorPopup.style.top=getTop(e);
    ocolorPopup.style.display="inline";
}