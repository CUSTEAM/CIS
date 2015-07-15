<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">
		<OL>
		
			<li>建立新進人員班表
				<ul>
				<li>班別代碼不可空白
				<li>編號必須匹配1個姓名
				<li>必須輸入開始時間
				<li>結束時間可空白，空白時將會自動建立365天班表，請先確認假日是否已建立
				<li>按下新增才會儲存 
			</ul>	
			
			<li>修改人員班表					
			<ul>
				<li>以條件搜尋出人員列表
				<li>以批次或逐一點選的方式建立新班表
				<li>按下完成修改才會儲存 
			</ul>
			
			<li>特定人員排班表
			<ul>
				<li>請輸入開始及結束日期為條件搜尋出人員列表 (如果以編號搜尋可跳過第2個步驟)
				<li>選擇要排班表的人員並點選修改
				<li>更改期間內的應上/應下班時間
				<li>按下完成修改才會儲存 
			</ul>
			
			<li>各項操作如有矛盾情況均不會繼續執行
		</OL>
		
		</td>
	</tr>
</table>