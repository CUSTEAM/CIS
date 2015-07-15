var oEditor		= window.parent.InnerDialogLoaded() ;
var FCK			= oEditor.FCK ;
var FCKLang		= oEditor.FCKLang ;
var FCKConfig	= oEditor.FCKConfig ;
var FCKTools = oEditor.FCKTools ;

window.onload = function()
{
	oEditor.FCKLanguageManager.TranslatePage(document) ;	
	window.parent.SetOkButton( true ) ;
}

function makeRandomNum(){//根据时间生成数字串 月2+日2+时2+分2+秒2
	var nowDate=new Date();
	var theNum;
	theNum=(nowDate.getMonth()+1).toString()+nowDate.getDate().toString()+nowDate.getHours().toString()+nowDate.getMinutes().toString()+nowDate.getSeconds().toString();
	return  theNum;
}
function Ok()
{
	var sAutioPlay="";
	var sMp3Url = GetE('mp3Url').value;
	var sAutoStart = GetE('autoStart').value;
	//处理自动播放和循环播放参数
	if (sAutoStart=="Yes"){
		sAutoStart="autostart=yes&amp;"
	}
	else{
		sAutoStart="";
	}
	var sLoop = GetE('loop').value;
	if (sLoop=="Yes"){
		sLoop="loop=yes&amp;";	
	}
	else{
		sLoop="";
	}
	//处理title和Artist信息
	var musicTitles,musicArtists;
	musicTitles="";
	musicArtists="";

	if (GetE('musicTitle').value!=""){
		musicTitles="titles="+GetE('musicTitle').value+"&amp;";
	}
	if (GetE('musicArtist').value!=""){
		musicArtists="artists="+GetE('musicArtist').value+"&amp;";
	}
	//alert(FCKConfig.FCKConfig.PluginsPath);
	var numTemp;
	if( sMp3Url.length> 0) {
		numTemp=makeRandomNum(); 
		sAutioPlay =
		"<object id='audioplayer"+numTemp+"' data='"+FCKConfig.PluginsPath+"kmp/player.swf' width='290' height='24' type='application/x-shockwave-flash'>"+
		"<param value='"+FCKConfig.PluginsPath+"kmp/player.swf' name='movie' />"+
		"<param value='playerID="+numTemp+"&amp;"+musicTitles+musicArtists+sAutoStart+sLoop+
		"soundFile="+sMp3Url+"' name='FlashVars' /><param value='high' name='quality' /><param value='false' name='menu' /><param value='transparent' name='wmode' />"+
		"</object>";
		
		oEditor.FCK.InsertHtml(sAutioPlay) ;
    	window.parent.Cancel() ;
	} else {
		alert("请输入正确的MP3地址。（例：http://www.163.com/popmusic.mp3）");
	}
	return true ;
}