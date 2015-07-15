<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
function search(key) {
	var x = "http://www.google.com.tw/search?hl=zh-TW&q=" + encodeURIComponent(key);
	window.open(x, '查詢書單', 'menubar=yes, toolbar=yes, resizable=yes, scrollbars=yes');
}
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Student/MyBook" method="post">
    <script>
			generateTableBanner('<table align="left">'+
									'<tr>'+
										'<td align="left">&nbsp;&nbsp;<img src="images/24-book-green-mark.png"></td>'+
										'<td>我的本學期教科書</td>'+
									'</tr>'+
								'</table>');
		</script>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr height="30">
				<td></td>
			</tr>
		</table>
		<c:forEach items='${bookList}' var='b'>
		<table width="85%" align="center">
						<tr>
							<td>
								<div class="modulecontainer filled nomessages">
								<div class="first">
								<span class="first"></span>
								<span class="last"></span>
								</div>
								<div>
								<div>
									<table width="100%">
										<tr>
											<td>
											&nbsp;&nbsp;<img src="images/24-book-green-message.png">
											</td>
											<td align="left" width="100%">
											<table width="100%">
												<tr>
													<td width="60" align="right">
													課程用書: 
													</td>
													<td align="left">
													<a href="javascript:void(0), showDetail('cs${b.oid}', 'th${b.oid}', 'sh${b.oid}')">${b.BookSuggest} <img src="images/16-exc-mark.gif" border="0"></a>
													</td>
												</tr>
												<tr id="cs${b.oid}" style="display='none'">
													<td width="60" align="right">
													課程名稱: 
													</td>
													<td align="left">
													${b.chi_name}
													</td>
												</tr>
												<tr id="th${b.oid}" style="display='none'">
													<td width="60" align="right">
													授課教師: 
													</td>
													<td align="left">
													${b.cname}
													</td>
												</tr>
												<tr id="sh${b.oid}" style="display='none'">
													<td width="60" align="right">
													尋找書在: 
													</td>													
													<td align="left">
													<a href="/CIS/Student/MyBook.do"><img src="images/12-icon-google.png" border="0" onClick="search('${b.BookSuggest}');"></a>
													<!-- 
													&nbsp;&nbsp;<a href="http://tw.search.yahoo.com/search?fr=fp-tab-web-t&ei=BIG-5&p=${b.BookSuggest}" target="_blank"><img src="images/12-icon-yahoo.png" border="0"></a>
													&nbsp;&nbsp;<a href="http://search.yam.com/wps?k=${b.BookSuggest}" target="_blank"><img src="images/12-icon-ebay.png" border="0"></a>
													&nbsp;&nbsp;<a href="http://tw.search.bid.yahoo.com/search/ac?p=${b.BookSuggest}" target="_blank"><img src="images/12-icon-cc.png" border="0"></a>
													&nbsp;&nbsp;<a href="http://tw.search.yahoo.com/search?fr=fp-tab-web-t&ei=UTF-8&p=${b.BookSuggest}" target="_blank"><img src="images/12-icon-wiki.png" border="0"></a>
													-->
													</td>
												</tr>
											</table>
											
											
											
											
											
											</td>
										</tr>
									</table>
								</div>
								</div>
								<div class="last">
								<span class="first"></span>
								<span class="last"></span>
								</div>
								</div>	
							</td>
						</tr>
					</table>
		</c:forEach>
</html:form>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr height="30">
			<td bgcolor="#CFE69F" clospan="2"></td>
		</tr>
	</table>
<script>
	function showDetail(cs, th, sh){
		
		<c:forEach items='${bookList}' var='c'>
		document.getElementById('cs${c.oid}').style.display='none';
		document.getElementById('th${c.oid}').style.display='none';
		document.getElementById('sh${c.oid}').style.display='none';
		</c:forEach>
	
		document.getElementById(cs).style.display='inline';
		document.getElementById(th).style.display='inline';
		document.getElementById(sh).style.display='inline';
		
		
		
		
	}
</script>