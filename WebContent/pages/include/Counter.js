<script language="javascript">
<!--
function val(num){
	 var num=parseFloat(num);
	 if(isNaN(num)) return 0;
	 return num;
	}
	
	function abs(num){
	 if(num<0) return -num;
	 return num;
	}
	
	function DecPlaces(num){
	 var str=""+num;
	 str=str.substr(str.indexOf(".")+1);
	 return str.length;
	}
	
	function Fix(num,numOfDec){
	 if(!numOfDec) numOfDec=0;
	 
	 num=num*Math.pow(10,numOfDec);
	 num=Math.round(num);
	 num=num/Math.pow(10,numOfDec);
	 return num;
	}
	
	String.prototype.chkIsContain=function(){
	 var arg=arguments;
	 for(var i=0;i<arg.length;i++){
	  if(this.indexOf(arg[i])!=-1) return true;
	 }
	 return false;
	}
	
	String.prototype.chkIsMatch=function(){
	 var arg=arguments;
	 for(var i=0;i<arg.length;i++){
	  if(this==arg[i]) return true;
	 }
	 return false;
	}
	
	function chkIsExist(name){
	 for(var i=0;i<document.forms.length;i++){
	  for(var j=0;j<document.forms[i].elements.length;j++){
	   if(document.forms[i].elements[j].name==name) return true;
	   if(document.forms[i].elements[j].id==name) return true;
	  }
	 }
	 return false;
	}
	
	var UpDownObj=new Object();
	UpDownObj.length=0;
	
	UpDownObj.checkUpDown=function(obj){
	 obj.value=Fix(val(obj.value),DecPlaces(val(obj.inc) ) );
	 if(val(obj.value)>val(obj.max)) obj.value=obj.max;
	 if(val(obj.value)<val(obj.min)) obj.value=obj.min;
	}
	
	UpDownObj.plusUpDown=function(obj){
	 if(!obj.disabled){
	  obj.value-=-val(obj.inc);
	  UpDownObj.checkUpDown(obj);
	 }
	}
	
	UpDownObj.substUpDown=function(obj){
	 if(!obj.disabled){
	  obj.value-=val(obj.inc);
	  UpDownObj.checkUpDown(obj);
	 }
	}
	
	UpDownObj.checkKeyIsNum=function(obj,e){
	 if(document.all){
	  var e=window.event;
	  var k=e.keyCode;
	 }else{
	  var k=e.which;
	 }
	 
	 function isthiskey(keyCode){
	  var arg=arguments;
	  for(var i=1;i<arg.length;i++){
	   if(keyCode==arg[i]) return true;
	  }
	 }
	
	 if(k==13){
	  UpDownObj.checkUpDown(obj);
	  return true;
	 }
	 if(k==38) UpDownObj.plusUpDown(obj);
	 if(k==40) UpDownObj.substUpDown(obj);
	 
	 if(k>="0".charCodeAt(0) && k<="9".charCodeAt(0)){
	  return true;
	 }
	 
	 if(k>=96 && k<=105){
	  return true;
	 }
	 
	 if(k==110 || k==190){
	  if(val(obj.inc)!=val(Math.floor(obj.inc)) && !obj.value.chkIsContain(".") ) return true;
	 }
	 
	 if(k==109 || k==189 || k==45){
	  if(val(obj.min)<0 && !obj.value.chkIsContain("-") ) return true;
	 }
	 
	 if( isthiskey(k,35,36,37,39,46,8,9,20,144,145) ) return true;
	
	 return false;
	}
	
	UpDownObj.mkUpDown=function mkUpDown(name,size,value,min,max,inc,mustNum,maxlength,propertiesForText,propertiesForBtn){
	 UpDownObj.length+=1;
	 
	 if(!name || name==""){
	  alert("建立第 "+ UpDownObj.length +" 個 UpDown 欄位失敗。\n\n請為欄位取名 !");
	  return false;
	 }else if( !isNaN(parseFloat(name))
	  || name.chkIsMatch("null","undefined","if","else","for","while","do","break","continue","return","true","false","top","parent","window","self")
	  || name.chkIsContain(" ",",",".","<",">","/","\\",'"',"'","[","]","=","+","-","*",":",";","?","!","@","#","$","%","^","&","(",")","{","}","`","~","|") ){
	  alert("建立第 "+ UpDownObj.length +" 個 UpDown 欄位失敗。\n\n名稱「"+name+"」並不符合 JavaScript 規格，請另外取名 !");
	  return false;
	 }else if(chkIsExist(name)){
	  alert("建立第 "+ UpDownObj.length +" 個 UpDown 欄位失敗。\n\n名稱「"+name+"」已經使用，請另外取名 !");
	  return false;
	 }
	 
	 if(!!size) size=abs(val(size));
	 if(!size) size=5;
	 
	 if(!value || isNaN(value)) value=0;
	 
	 if(!min || isNaN(min)) min=0;
	 
	 if((!max && max!=0) || isNaN(max)) max=100;
	 
	 if(!!maxlength || maxlength==0){
	  if(isNaN(maxlength) || val(maxlength)<0){
	   maxlength='""';
	  }else{
	   maxlength=val(maxlength);
	  }
	 }else if(!maxlength && maxlength!=0){
	  maxlength='""';
	 }
	 
	 if(!inc || isNaN(inc)) inc=1;
	 if(!propertiesForText) propertiesForText='';
	 if(!propertiesForBtn) propertiesForBtn='';
	 
	 if((typeof mustNum).toLowerCase=="boolean") mustNum=true;
	 mustNum=!!mustNum;
	 if(mustNum) propertiesForText=' onkeydown="return UpDownObj.checkKeyIsNum(this)" onkeypress="return UpDownObj.checkKeyIsNum(this)" '+propertiesForText;
	 
	 document.write('<table border="0" align="left" cellpadding="0" cellspacing="0"><tr><td rowspan="2"><input type=text onpaste="return false" maxlength="'+maxlength+'" onblur="UpDownObj.checkUpDown(this)" name='+name+' value='+value+' max='+max+' min='+min+' size='+size+' inc='+inc+' '+propertiesForText+' ></td>');
	 document.write('<td><input name=btnUp'+name+' type=button value=+ onclick="UpDownObj.plusUpDown(this.form.'+name+')" ondblclick=this.onclick() '+propertiesForBtn+' ></td></tr>');
	 document.write('<tr><td><input name=btnDown'+name+' type=button value=- onclick="UpDownObj.substUpDown(this.form.'+name+')" ondblclick=this.onclick() '+propertiesForBtn+' ></td></tr></table>');
	}
	// -->
</script>