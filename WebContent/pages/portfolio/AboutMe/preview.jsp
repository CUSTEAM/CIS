<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
#test{
    position:absolute;
    top=100px;
    height=500px;
    filter:
        progid:DXImageTransform.Microsoft.Matrix(enabled=true,SizingMethod=clip to original,FilterType=nearest neighbor)
        FlipH(enabled=false)
        FlipV(enabled=false);
    }
</style>
<table width="99%" >
	<tr>
		<td width="10" align="left" nowrap>
  			<hr noshade class="myHr"/>
			</td>
			<td width="24" align="center" nowrap>
			<img src="images/icon/find.gif" />
			</td>
			<td nowrap style="cursor:pointer;"">
			預覽
			</td>
			<td width="100%" align="left">
  			<hr noshade class="myHr"/>
		</td>
	</tr>
</table>

<c:if test="${!empty stmd}">
<!-- 基本資料 -->		
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">部制</td>
		<td class="hairLineTdF" width="100%">${stmd.school_name}</td>
	</tr>
	<tr>
		<td class="hairLineTdF">科系(所)</td>
		<td class="hairLineTdF" width="100%">${stmd.fname}</td>
	</tr>
	<tr>
		<td class="hairLineTdF">就讀班級</td>
		<td class="hairLineTdF" width="100%">${stmd.ClassName}</td>
	</tr>
	<tr>
		<td class="hairLineTdF">學號</td>
		<td class="hairLineTdF" width="100%">${stmd.student_no}</td>
	</tr>
	<tr>
		<td class="hairLineTdF">姓名</td>
		<td class="hairLineTdF" width="100%">${stmd.student_name}</td>
	</tr>
	<tr>
		<td class="hairLineTdF" nowrap>出生日期</td>
		<td class="hairLineTdF">${stmd.birthday}</td>
	</tr>
	<tr>
		<td class="hairLineTdF">電子郵件</td>
		<td class="hairLineTdF">${stmd.Email}</td>
	</tr>	
</table>
<style>
#test{
    /*position:absolute;*/
    /*top=100px;*/
    /*height=500px;*/
    filter:
        progid:DXImageTransform.Microsoft.Matrix(enabled=true,SizingMethod=clip to original,FilterType=nearest neighbor)
        FlipH(enabled=false)
        FlipV(enabled=false);
    }
</style>
<div id=test style="width:250px;">
	<table class="hairLineTable">
		<tr>
			<td class="hairLineTdF">
			<img src=/CIS/ShowImage?studentNo=${Uid} width=134 />
			</td>
		</tr>
	</table>	
</div>
<script>
function doRotate(sita,obj){
    var t=Math.PI*sita/180;
    var c=Math.cos(t);
    var s=Math.sin(t);
    with(obj.filters.item(0)){
        Dx=0;
        M11=c;
        M12=-1*s;
        M21=s;
        M22=c;
        }
    }
doRotate(15,test)
</script>
</c:if>

<c:if test="${!empty seld}">
<!-- 本學期修課 -->		
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">課程代碼</td>
		<td class="hairLineTdF" nowrap>課程名稱</td>
		<td class="hairLineTdF">學分</td>
		<td class="hairLineTdF" nowrap>時數</td>				
	</tr>
	<c:forEach items="${seld}" var="s">	
	<tr>				
		<td class="hairLineTdF">${s.cscode}</td>
		<td class="hairLineTdF">${s.chi_name}</td>			
		<td class="hairLineTdF">${s.credit}</td>			
		<td class="hairLineTdF">${s.thour}</td>
	</tr>
	</c:forEach>
</table>
</c:if>
<c:if test="${seld!=null && empty seld}">
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" width="1"><img src="images/icon/icon_info_exclamation.gif"/></td>
		<td class="hairLineTdF">系統中沒有您的本學期的修課記錄, 如有疑問請洽各部制教務單位</td>
	</tr>
</table>
</c:if>

<c:if test="${!empty Savedesd}">
<!-- 歷年獎懲 -->		
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">學年</td>
		<td class="hairLineTdF" nowrap>學期</td>
		<td class="hairLineTdF">種類</td>
		<td class="hairLineTdF" nowrap>次數</td>
		<td class="hairLineTdF" nowrap>種類</td>
		<td class="hairLineTdF">次數</td>
		<td class="hairLineTdF" nowrap>原因</td>				
	</tr>
	<c:forEach items="${Savedesd}" var="s">	
	<tr>				
		<td class="hairLineTdF">${s.school_year}</td>
		<td class="hairLineTdF">${s.school_term}</td>			
		<td class="hairLineTdF">${s.kind1}</td>			
		<td class="hairLineTdF">${s.cnt1}</td>
		<td class="hairLineTdF">${s.kind2}</td>
		<td class="hairLineTdF">${s.cnt2}</td>
		<td class="hairLineTdF">${s.name}</td>
	</tr>
	</c:forEach>
</table>
</c:if>
<c:if test="${Savedesd!=null && empty Savedesd}">
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" width="1"><img src="images/icon/icon_info_exclamation.gif"/></td>
		<td class="hairLineTdF">系統中沒有您的獎懲記錄, 如有疑問請洽各部制學務單位</td>
	</tr>
</table>
</c:if>

<c:if test="${!empty Savejust}">
<!-- 歷年操行 -->		
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">學年</td>
		<td class="hairLineTdF" nowrap>學期</td>
		<td class="hairLineTdF">評語1</td>
		<td class="hairLineTdF" nowrap>評語2</td>
		<td class="hairLineTdF" nowrap>評語3</td>
		<td class="hairLineTdF">成績</td>				
	</tr>
	<c:forEach items="${Savejust}" var="s">	
	<tr>				
		<td class="hairLineTdF">${s.school_year}</td>
		<td class="hairLineTdF">${s.school_term}</td>			
		<td class="hairLineTdF">${s.com_name1}</td>			
		<td class="hairLineTdF">${s.com_name2}</td>
		<td class="hairLineTdF">${s.com_name3}</td>
		<td class="hairLineTdF">${s.total_score}</td>
	</tr>
	</c:forEach>
</table>
</c:if>
<c:if test="${Savejust!=null && empty Savejust}">
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" width="1"><img src="images/icon/icon_info_exclamation.gif"/></td>
		<td class="hairLineTdF">系統中沒有您的操行記錄, 如有疑問請洽各部制學務單位</td>
	</tr>
</table>
</c:if>

<c:if test="${!empty ScoreHist}">
<!-- 歷年成績 -->		
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">學年</td>
		<td class="hairLineTdF" nowrap>學期</td>
		<td class="hairLineTdF">課程名稱</td>
		<td class="hairLineTdF" nowrap>選別</td>
		<td class="hairLineTdF" nowrap>學分</td>
		<td class="hairLineTdF">成績</td>
		<td class="hairLineTdF">備註</td>
	</tr>
	<c:forEach items="${ScoreHist}" var="s">	
	<tr>				
		<td class="hairLineTdF">${s.school_year}</td>
		<td class="hairLineTdF">${s.school_term}</td>
		<td class="hairLineTdF">${s.chi_name}</td>		
		<td class="hairLineTdF">${s.opt}</td>			
		<td class="hairLineTdF">${s.credit}</td>
		<td class="hairLineTdF">${s.score}</td>
		<td class="hairLineTdF">${s.evgr_type}</td>
	</tr>
	</c:forEach>
</table>
</c:if>
<c:if test="${ScoreHist!=null && empty ScoreHist}">
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" width="1"><img src="images/icon/icon_info_exclamation.gif"/></td>
		<td class="hairLineTdF">系統中沒有您的歷年成績記錄, 如有疑問請洽各部制學務單位</td>
	</tr>
</table>
</c:if>

<c:if test="${!empty CsGroup}">
<!-- 學程 -->	
<%@ include file="CsGroup.jsp" %>
</c:if>
<c:if test="${CsGroup!=null && empty CsGroup}">
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" width="1"><img src="images/icon/icon_info_exclamation.gif"/></td>
		<td class="hairLineTdF">系統中沒有關於您的跨領域學程資訊, 如有疑問請洽各部制教務單位</td>
	</tr>
</table>
</c:if>

<c:if test="${!empty Table}">
<!-- 課表 -->	
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" width="10"></td>
		<td class="hairLineTdF" width="14%">星期一</td>
		<td class="hairLineTdF" width="14%">星期二</td>
		<td class="hairLineTdF" width="14%">星期三</td>		
		<td class="hairLineTdF" width="14%">星期四</td>			
		<td class="hairLineTdF" width="14%">星期五</td>
		<td class="hairLineTdF" width="14%">星期六</td>
		<td class="hairLineTdF" width="14%">星期日</td>
	</tr>
<c:forEach items="${Table}" var="s" varStatus="ss">	
	<tr height="40">
		<td class="hairLineTdF"><font size="-2">第${ss.count}節</font></td>
		<td class="hairLineTdF"><font size="-2">${s.c1}</font></td>
		<td class="hairLineTdF">${s.c2}</td>
		<td class="hairLineTdF">${s.c3}</td>		
		<td class="hairLineTdF">${s.c4}</td>			
		<td class="hairLineTdF">${s.c5}</td>
		<td class="hairLineTdF">${s.c6}</td>
		<td class="hairLineTdF">${s.c7}</td>
	</tr>
</c:forEach>
</table>
</c:if>


<c:if test="${!empty CsCore}">
<table width="100%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF">
		<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" wmode="transparent"
			 codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0"
			 WIDTH="100%" HEIGHT="280" id="charts" ALIGN="center">
			<param name="wmode" value="transparent" />
			<PARAM NAME=movie VALUE=course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/Print/course/StudentChartPlus.do?student_no=${CsCore}&<%=(int)(Math.random()*49)%>">
			<PARAM NAME=quality VALUE=high>
			<PARAM NAME=bgcolor VALUE=#f0fcd7>
			<EMBED src="course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/Print/course/StudentChartPlus.do?student_no=${CsCore}&<%=(int)(Math.random()*49)%>"
			quality=high bgcolor=#cccccc  WIDTH="100%" HEIGHT="350" NAME="charts" ALIGN="center"
			TYPE="application/x-shockwave-flash" PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer"></EMBED>
		</OBJECT>
		</td>

		<td width="50%" class="hairLineTdF">
		<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" wmode="transparent"
			 codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0"
			 WIDTH="100%" HEIGHT="280" id="charts" ALIGN="center">
			<param name="wmode" value="transparent" />
			<PARAM NAME=movie VALUE=course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/Print/course/StudentChartNoPlus.do?student_no=${CsCore}&<%=(int)(Math.random()*49)%>">
			<PARAM NAME=quality VALUE=high>
			<PARAM NAME=bgcolor VALUE=#f0fcd7>
			<EMBED src="course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/Print/course/StudentChartNoPlus.do?student_no=${CsCore}&<%=(int)(Math.random()*49)%>"
			quality=high bgcolor=#cccccc  WIDTH="100%" HEIGHT="350" NAME="charts" ALIGN="center"
			TYPE="application/x-shockwave-flash" PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer"></EMBED>
		</OBJECT>
		
		</td>
	</tr>
</table>
</c:if>








<c:if test="${!empty Rcact}">
<table width="99%" class="hairLineTable">
	
	<tr>
		<td class="hairLineTdF">
		
		<c:forEach items="${Rcact}" var="r">
		<table class="hairLineTable" width="99%">
		<tr>
		  <td width="100" align="right" class="hairLineTdF">年度</td>
		  <td class="hairLineTdF">${r.school_year}</td>
		</tr>
		<tr>
		  <td align="right" class="hairLineTdF">系所名稱</td>
		  <td class="hairLineTdF">${r.school_year}</td>
		</tr>
		<tr>
		  <td align="right" class="hairLineTdF">主辦單位</td>
		  <td class="hairLineTdF" align="left">${r.sponoff}</td></tr>		  
		<tr>
		  <td align="right" class="hairLineTdF">活動名稱</td><td class="hairLineTdF" align="left">${r.actname}</td></tr><tr>
		  <td align="right" class="hairLineTdF">活動種類</td><td class="hairLineTdF" align="left">${r.kindid}</td></tr><tr>
		  <td align="right" class="hairLineTdF">活動類型</td><td class="hairLineTdF" align="left">${r.typeid}</td></tr><tr>
		  <td align="right" class="hairLineTdF">活動地點</td><td class="hairLineTdF" align="left">${r.placeid}</td></tr><tr>
		  <td align="right" class="hairLineTdF">參與情形</td><td class="hairLineTdF" align="left">${r.joinid}</td></tr><tr>
		  <td align="right" class="hairLineTdF">開始日期</td><td class="hairLineTdF" align="left">${r.bdate}</td></tr><tr>
		  <td align="right" class="hairLineTdF">結束日期</td><td class="hairLineTdF" align="left">${r.edate}</td></tr><tr>
		  <td align="right" class="hairLineTdF">時數</td><td class="hairLineTdF" align="left">${r.hour}</td></tr><tr>
		  <td align="right" class="hairLineTdF">研習證明</td><td class="hairLineTdF" align="left">${r.certyn}</td></tr><tr>
		  <td align="right" class="hairLineTdF">證書字號</td><td class="hairLineTdF" align="left">${r.certno}</td></tr><tr>
		  <td align="right" class="hairLineTdF">學校補助情形</td><td class="hairLineTdF" align="left">${r.schspon}</td></tr><tr>
		  <td align="right" class="hairLineTdF">審核狀態</td><td class="hairLineTdF" align="left">${r.approveTemp}</td></tr>
		</tr>
		</table>
		
		</c:forEach>
		
		</td>
	</tr>
	
</table>
</c:if>




<c:if test="${!empty Rcproj}">
<table width="99%" class="hairLineTable">
	
	<tr>
		<td class="hairLineTdF">
		
		<c:forEach items="${Rcproj}" var="r">
		<table class="hairLineTable" width="99%">
		<tr>
		  <td width="100" nowrap align="right" class="hairLineTdF">年度</td>
		  <td class="hairLineTdF" align="left" width="100%">${r.school_year}</td>
		</tr>
		<tr>
		  <td align="right" class="hairLineTdF">系所</td><td class="hairLineTdF" align="left"></td>
		</tr>
		<tr>
		  <td align="right" class="hairLineTdF">教師</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">案號</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">案名</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">類型</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">執行起始日期</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">執行結束日期</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">工作類別</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">金額(元)</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">政府出資金額</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">企業出資金額</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">其他單位出資金額</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">學校出資金額</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">主要經費來源</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">單位名稱</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">次要經費來源</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">受惠機構名稱</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">委託單位(國內)</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">委託單位(國外)</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">合作單位(國內)</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">合作單位(國外)</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">專案聘任之專任人員</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">專案聘任之兼職人員</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF" width="100" nowrap>國科會、教育部或是政府委訓計畫受訓人次</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">企業委訓計畫受訓人數</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">其他單位委訓計畫受訓人次</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">他校轉入的專案</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">他校轉入的政府出資金額</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">他校轉入的企業出資金額</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">他校轉入的其他單位出資金額</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">專案已轉至他校</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">已轉至他校的政府出資金額</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">已轉至他校的企業出資金額</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">已轉至他校的其他單位出資金額</td><td class="hairLineTdF" align="left"></td></tr><tr>
		  <td align="right" class="hairLineTdF">審核狀態</td><td class="hairLineTdF" align="left"></td></tr>
		</table>
		
		</c:forEach>
		
		</td>
	</tr>
	
</table>
</c:if>



<c:if test="${!empty Rcjour}">
<c:forEach items="${Rcjour}" var="r">
<table width="99%" class="hairLineTable">
  <tr>
    <td class="hairLineTdF" width="100">年度</td><td class="hairLineTdF">${r.school_year}</td></tr><tr>

  
    <td class="hairLineTdF">計畫案號</td><td class="hairLineTdF">${r.projno}</td></tr><tr>
    <td class="hairLineTdF">論文名稱</td><td class="hairLineTdF">${r.title}</td></tr><tr>
    <td class="hairLineTdF">收錄分類</td><td class="hairLineTdF">${r.kindid}</td></tr><tr>
    <td class="hairLineTdF">作者順序</td><td class="hairLineTdF">${r.authorno}</td></tr><tr>
    <td class="hairLineTdF">通訊作者</td><td class="hairLineTdF">${r.COM_authorno}</td></tr><tr>

  
    <td class="hairLineTdF">刊物名稱</td><td class="hairLineTdF">${r.jname}</td></tr><tr>
    <td class="hairLineTdF">發表形式</td><td class="hairLineTdF">${r.type}</td></tr><tr>
    <td class="hairLineTdF">發刊地點</td><td class="hairLineTdF">${r.place}</td></tr><tr>

 
    <td class="hairLineTdF">發表卷數</td><td class="hairLineTdF">${r.volume}</td></tr><tr>
    <td class="hairLineTdF">發表期數</td><td class="hairLineTdF">${r.period}</td></tr><tr>
    <td class="hairLineTdF">發表年份</td><td class="hairLineTdF">${r.pyear}</td></tr><tr>
    <td class="hairLineTdF">發表月份</td><td class="hairLineTdF">${r.pmonth}</td></tr><tr>

               
    <td class="hairLineTdF">摘要/簡述</td><td class="hairLineTdF">${r.intor}</td></tr>
</table>
</c:forEach>
</c:if>



<c:if test="${!empty Rcconf}">
<c:forEach items="${Rcconf}" var="r">
<table width="99%" class="hairLineTable">                
                  
    <tr><td class="hairLineTdF" width="100" nowrap align="right">計畫案號</td>
    <td class="hairLineTdF">${r.projno }</td></tr>
                      
    <tr><td class="hairLineTdF" align="right">論文名稱</td>
    <td class="hairLineTdF">${r.title }</td></tr>                
                  
    <tr><td class="hairLineTdF" align="right">作者順序</td>
    <td class="hairLineTdF">${r.authorno }</td></tr>                  
    <tr><td class="hairLineTdF" align="right">通訊作者</td>
    <td class="hairLineTdF">${r.COM_authorno }</td></tr>                
                 
    <tr><td class="hairLineTdF" align="right">研討會名稱</td>
    <td class="hairLineTdF">${r.jname }</td></tr>                
              
    <tr><td class="hairLineTdF" align="right">舉行國家</td>
    <td class="hairLineTdF">${r.nation }</td></tr>                  
    <tr><td class="hairLineTdF" align="right">舉行城市</td>
    <td class="hairLineTdF">${r.city }</td></tr>                  
    <tr><td class="hairLineTdF" align="right">開始日期</td>
    <td class="hairLineTdF">${r.bdate }</td></tr>                  
    <tr><td class="hairLineTdF" align="right">結束日期</td>
    <td class="hairLineTdF">${r.edate }</td></tr>                  
    <tr><td class="hairLineTdF" align="right">發表年份</td>
    <td class="hairLineTdF">${r.pyear }</td></tr>                
          
    <tr><td class="hairLineTdF" align="right">摘要/簡述</td>
    <td class="hairLineTdF">${r.intor }</td></tr>      
             
</table>
</c:forEach>
</c:if>


<c:if test="${!empty Rcbook}">
<c:forEach items="${Rcbook}" var="r">
<table width="99%" class="hairLineTable">

	<tr><td width="100" class="hairLineTdF" align="right">計畫案號</td>
    <td class="hairLineTdF">${r.projno }</td></tr>                  
    <tr><td class="hairLineTdF" align="right">專書名稱</td>
    <td class="hairLineTdF">${r.title }</td></tr>                
              
    <tr><td class="hairLineTdF" align="right">專書類別</td>
    <td class="hairLineTdF">${r.type }</td></tr>                                     
    <tr><td class="hairLineTdF" align="right">作者順序</td>
    <td class="hairLineTdF">${r.authorno }</td></tr>                  
    <tr><td class="hairLineTdF" align="right">通訊作者</td>
    <td class="hairLineTdF">${r.COM_authorno }</td></tr>                  
    <tr><td class="hairLineTdF" align="right">使用語言</td>
    <td class="hairLineTdF">${r.language }</td></tr>                
               
    <tr><td class="hairLineTdF" align="right">出版社</td>
    <td class="hairLineTdF">${r.publisher }</td></tr>                
                
    <tr><td class="hairLineTdF" align="right">出版日期</td>
    <td class="hairLineTdF">${r.pdate }</td></tr>                  
    <tr><td class="hairLineTdF" align="right">ISBN編號</td>
    <td class="hairLineTdF">${r.isbn }</td></tr>                
               
    <tr><td class="hairLineTdF" align="right">摘要/簡述</td>
    <td class="hairLineTdF">${r.intor }</td></tr>


</table>
</c:forEach>
</c:if>


<c:if test="${!empty Rcpet}">
<c:forEach items="${Rcpet}" var="r">
<table width="99%" class="hairLineTable">
	
	<tr><td width="100" class="hairLineTdF" align="right">計畫案號</td>
    <td class="hairLineTdF" width="100%">${r.projno}</td></tr>                  
    
    <tr><td class="hairLineTdF" align="right" >專利名稱</td>
    <td class="hairLineTdF">${r.title}</td></tr>                
  
    <tr><td class="hairLineTdF" align="right" >區域</td>
    <td class="hairLineTdF">${r.area}</td></tr>                  
    
    <tr><td class="hairLineTdF" align="right" >類型</td>
    <td class="hairLineTdF">${r.petType}</td></tr>                                   
  
    <tr><td class="hairLineTdF" align="right" >技術報告分數</td>
    <td class="hairLineTdF">${r.score}</td></tr>                  
    
    <tr><td class="hairLineTdF" align="right" >進度狀況</td>
    <td class="hairLineTdF">${r.schedule}</td></tr>                  
    
    <tr><td class="hairLineTdF" align="right" >作者順序</td>
    <td class="hairLineTdF">${r.authorno}</td></tr>                
  
    <tr><td class="hairLineTdF" align="right" >申請人/權利人</td>
    <td class="hairLineTdF">${r.proposer}</td></tr>                  
   
    <tr><td class="hairLineTdF" align="right" >申請/權利人類型 </td>
    <td class="hairLineTdF">${r.proposerType}</td></tr>                
  
    <tr><td class="hairLineTdF" align="right" >申請/生效日期</td>
    <td class="hairLineTdF">${r.bdate}</td></tr>                  
    
    <tr><td class="hairLineTdF" align="right" >終止日期</td>
    <td class="hairLineTdF">${r.edate}</td></tr>                
  
    <tr><td class="hairLineTdF" align="right" >發照機關</td>
    <td class="hairLineTdF">${r.inst}</td></tr>                  
    
    <tr><td class="hairLineTdF" align="right" >證書字號</td>
    <td class="hairLineTdF">${r.certno}</td></tr>                
 
    <tr><td class="hairLineTdF" align="right" >技術轉移或授權</td>
    <td class="hairLineTdF">${r.depute}</td></tr>                  
    
    <tr><td class="hairLineTdF" align="right" nowrap width="100">技術轉移或授權金額</td>
    <td class="hairLineTdF">${r.deputeMoney}</td></tr>                
  
    <tr><td class="hairLineTdF" align="right" >摘要/簡述</td>
    <td class="hairLineTdF">${r.intor}</td></tr> 
</table>

</c:forEach>
</c:if>



<c:if test="${!empty Rchono}">
<c:forEach items="${Rchono}" var="r">
	<table width="99%" class="hairLineTable">	
		
	<tr><td class="hairLineTdF" width="100" align="right">計畫案號</td>
    <td class="hairLineTdF">${r.projno}</td></tr>                  
    <tr><td class="hairLineTdF" align="right">獲獎名稱</td>
    <td class="hairLineTdF">${r.title}</td></tr>                
                                  
    <tr><td class="hairLineTdF" align="right">作者順序</td>
    <td class="hairLineTdF">${r.authorno}</td></tr>                  
    <tr><td class="hairLineTdF" align="right">頒獎國別</td>
    <td class="hairLineTdF">${r.nation}</td></tr>                
                                 
    <tr><td class="hairLineTdF" align="right">頒獎機構</td>
   	<td class="hairLineTdF">${r.inst}</td></tr>                  
    <tr><td class="hairLineTdF" align="right">頒獎日期</td>
    <td class="hairLineTdF">${r.bdate}</td></tr>                
                
    <tr><td class="hairLineTdF" align="right">摘要/簡述</td>
    <td class="hairLineTdF">${r.intor}</td></tr>	
	</table>
	
</c:forEach>
</c:if>





<c:if test="${!empty empl}">
<!-- 基本資料 -->		
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">姓名</td>
		<td class="hairLineTdF" width="100%">${empl.cname}</td>
	</tr>
	<tr>
		<td class="hairLineTdF" nowrap>出生日期</td>
		<td class="hairLineTdF">${empl.bdate}</td>
	</tr>
	<tr>
		<td class="hairLineTdF">電子郵件</td>
		<td class="hairLineTdF">${empl.Email}</td>
	</tr>
	<tr>
		<td class="hairLineTdF">本校職稱</td>
		<td class="hairLineTdF" width="100%">${empl.sname}</td>
	</tr>
</table>
</c:if>








