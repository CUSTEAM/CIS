<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<script src="/CIS/pages/include/decorate.js"></script>



<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/Portfolio/EditVitae" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/script_edit.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">編輯履歷表</font></div>		
		</td>
	</tr>
	
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" valign="top">
				
				<input type="checkbox" checked disabled />我輸入的中、英文履歷表內容，均同意提供任課教師調閱。
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	<tr>
		<td>
		<FCK:editor instanceName="ch" toolbarSet="vitae" basePath="/pages/include/fckeditor">
			<jsp:attribute name="value">
			
			<c:if test="${content.content_zh!=null }">
			${content.content_zh}
			</c:if>
			
			
			<c:if test="${content.content_zh==null }">
			<div style="text-align: center">
	  		<div style="text-align: center">
  <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt"><b>
      <span style="font-family: '新細明體','serif'">
        <font size="3">中文履歷表
          <span lang="EN-US">
            <o:p>
            </o:p>
          </span>
        </font>
      </span></b>
  </p>
  <p class="MsoNormal" align="right" style="text-align: right; margin: 0cm 0cm 0pt">
    <span lang="EN-US" style="font-family: '新細明體','serif'">
      <font size="3">1/1
        <o:p>
        </o:p>
      </font>
    </span>
  </p>
  <table class="MsoNormalTable" border="1" cellspacing="0" cellpadding="0" style="border-bottom: medium none; border-left: medium none; margin: auto auto auto 3.5pt; border-collapse: collapse; border-top: medium none; border-right: medium none; mso-border-alt: solid windowtext .5pt; mso-padding-alt: 0cm 1.4pt 0cm 1.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext">
    <tbody>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 0; mso-yfti-firstrow: yes">
        <td width="633" colspan="14" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 474.9pt; padding-right: 1.4pt; border-top: windowtext 1.5pt solid; border-right: #c0c0c0; padding-top: 0cm">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'">
              <font size="3">應徵職務
                <span lang="EN-US">:<b>
                    <o:p>
                    </o:p></b>
                </span>
              </font>
            </span>
          </p></td>
        <td width="110" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 82.4pt; padding-right: 1.4pt; border-top: windowtext 1.5pt solid; border-right: windowtext 1.5pt solid; padding-top: 0cm">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: '新細明體','serif'">
              <o:p>
                <font size="3">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="mso-yfti-irow: 1">
        <td width="225" colspan="4" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 168.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: #c0c0c0; padding-top: 0cm; mso-border-top-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'">
              <font size="3">姓
                <span lang="EN-US">:
                  <span style="color: #993300">
                    <o:p>
                    </o:p>
                  </span>
                </span>
              </font>
            </span>
          </p></td>
        <td width="228" colspan="7" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 171pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'">
              <font size="3">名
                <span lang="EN-US">:
                  <span style="color: blue">
                    <o:p>
                    </o:p>
                  </span>
                </span>
              </font>
            </span>
          </p></td>
        <td width="290" colspan="4" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 217.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext 1.5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'">
              <font size="3">性別
                <span lang="EN-US">: 
                </span>□男性 □女性<b>
                  <span lang="EN-US" style="color: blue">
                    <o:p>
                    </o:p>
                  </span></b>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 2">
        <td width="743" colspan="15" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'">
              <font size="3">身份證字號
                <span lang="EN-US"> (
                </span>非中華民國國民請填護照號碼
                <span lang="EN-US">):
                  <o:p>
                  </o:p>
                </span>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 3">
        <td width="309" colspan="6" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 231.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-top-alt: 1.5pt; mso-border-left-alt: 1.5pt; mso-border-bottom-alt: .5pt; mso-border-right-alt: .5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'">
              <font size="3">生日
                <span lang="EN-US"> (
                </span>月
                <span lang="EN-US">/
                </span>日
                <span lang="EN-US">/
                </span>年
                <span lang="EN-US">):
                  <o:p>
                  </o:p>
                </span>
              </font>
            </span>
          </p></td>
        <td width="434" colspan="9" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 325.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: 1.5pt; mso-border-left-alt: .5pt; mso-border-bottom-alt: .5pt; mso-border-right-alt: 1.5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'">
              <font size="3">婚姻狀態
                <span lang="EN-US">:
                  <o:p>
                  </o:p>
                </span>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 4">
        <td width="743" colspan="15" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: .5pt; mso-border-left-alt: 1.5pt; mso-border-bottom-alt: .5pt; mso-border-right-alt: 1.5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; text-indent: -0.3pt; margin: 0cm 0cm 0pt 1.4pt; mso-para-margin-left: .09gd">
            <span style="font-family: '新細明體','serif'">
              <font size="3">聯絡方式
                <span lang="EN-US">:
                  <o:p>
                  </o:p>
                </span>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 5">
        <td width="357" colspan="8" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 267.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt 12.6pt; mso-para-margin-left: 1.05gd">
            <font size="3">
              <span lang="EN-US" style="font-family: '新細明體','serif'">1.
              </span>
              <span style="font-family: '新細明體','serif'">手機
                <span lang="EN-US">:
                  <o:p>
                  </o:p>
                </span>
              </span>
            </font>
          </p></td>
        <td width="386" colspan="7" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 289.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: '新細明體','serif'">
              <font size="3">2.E-mail:
                <o:p>
                </o:p>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 6">
        <td width="743" colspan="15" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; text-indent: 12pt; margin: 0cm 0cm 0pt; mso-char-indent-count: 1.0">
            <font size="3">
              <span lang="EN-US" style="font-family: '新細明體','serif'">3.
              </span>
              <span style="font-family: '新細明體','serif'">通訊地址
                <span lang="EN-US">:
                  <o:p>
                  </o:p>
                </span>
              </span>
            </font>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 7">
        <td width="743" colspan="15" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'">
              <font size="3">主要交通工具
                <span lang="EN-US">:(
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>)
                </span>機車
                <span lang="EN-US"> (
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>)
                </span>汽車
                <span lang="EN-US"> (
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>)
                </span>腳踏車
                <span lang="EN-US"> (
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>)
                </span>大眾運輸公具
                <span lang="EN-US">
                  <o:p>
                  </o:p>
                </span>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 8">
        <td width="743" colspan="15" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext 1.5pt; mso-border-top-alt: solid windowtext 1.5pt; mso-border-bottom-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'">
              <font size="3">求職身份
                <span lang="EN-US">:(
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>)
                </span>全職
                <span lang="EN-US">
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>(
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>)
                </span>應屆畢業生
                <span lang="EN-US">
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>(
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>)
                </span>學生
                <span lang="EN-US">[
                </span>日間就讀中
                <span lang="EN-US">]
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>(
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>)
                </span>學生
                <span lang="EN-US">[
                </span>夜間就讀中
                <span lang="EN-US">]
                  <o:p>
                  </o:p>
                </span>
              </font>
            </span>
          </p>
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; text-indent: 60pt; margin: 0cm 0cm 0pt; mso-char-indent-count: 5.0">
            <font size="3">
              <span lang="EN-US" style="font-family: '新細明體','serif'">(
                <span style="mso-spacerun: yes">&nbsp; 
                </span>)
              </span>
              <span style="font-family: '新細明體','serif'">家庭主婦
                <span lang="EN-US">
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>(
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>)
                </span>中高齡就業
                <span lang="EN-US">(40
                </span>歲以上
                <span lang="EN-US">)
                  <o:p>
                  </o:p>
                </span>
              </span>
            </font>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 9">
        <td width="743" colspan="15" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: .5pt; mso-border-left-alt: 1.5pt; mso-border-bottom-alt: .5pt; mso-border-right-alt: 1.5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'">
              <font size="3">目前就業狀態
                <span lang="EN-US">:(
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>)
                </span>仍在職
                <span lang="EN-US">
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>(
                  <span style="mso-spacerun: yes">&nbsp; 
                  </span>)
                </span>待業中
                <span lang="EN-US">
                  <o:p>
                  </o:p>
                </span>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 10">
        <td valign="top" width="372" colspan="9" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 278.65pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-top-alt: 1.5pt; mso-border-left-alt: 1.5pt; mso-border-bottom-alt: .5pt; mso-border-right-alt: .5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <font size="3">
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">學歷
              </span>
              <font face="Times New Roman">
                <span lang="EN-US">:
                </span>
                <span lang="EN-US" style="font-family: '新細明體','serif'">
                  <o:p>
                  </o:p>
                </span>
              </font>
            </font>
          </p></td>
        <td valign="top" width="372" colspan="6" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 278.65pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: 1.5pt; mso-border-left-alt: .5pt; mso-border-bottom-alt: .5pt; mso-border-right-alt: 1.5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <font size="3">
              <span style="font-family: '新細明體','serif'; color: black; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'; mso-bidi-font-size: 9.0pt">畢業日期
              </span>
              <span lang="EN-US" style="color: black; mso-bidi-font-size: 9.0pt">
                <font face="Times New Roman"> (
                </font>
              </span>
              <span style="font-family: '新細明體','serif'; color: black; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'; mso-bidi-font-size: 9.0pt">月
              </span>
              <span lang="EN-US" style="color: black; mso-bidi-font-size: 9.0pt">
                <font face="Times New Roman">/
                </font>
              </span>
              <span style="font-family: '新細明體','serif'; color: black; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'; mso-bidi-font-size: 9.0pt">年
              </span>
              <font face="Times New Roman">
                <span lang="EN-US" style="color: black; mso-bidi-font-size: 9.0pt">):
                </span>
                <span lang="EN-US" style="font-family: '新細明體','serif'">
                  <o:p>
                  </o:p>
                </span>
              </font>
            </font>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 11">
        <td valign="top" width="153" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 114.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="336" colspan="10" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 252pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">
              <font size="3">學校
              </font>
            </span>
          </p></td>
        <td valign="top" width="254" colspan="3" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 190.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">
              <font size="3">科系
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 12">
        <td valign="top" width="153" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 114.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">
              <font size="3">高中
              </font>
            </span>
          </p></td>
        <td valign="top" width="336" colspan="10" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 252pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="254" colspan="3" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 190.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 13">
        <td valign="top" width="153" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 114.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <font size="3">
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">專科
              </span>
              <span lang="EN-US">
                <font face="Times New Roman"> / 
                </font>
              </span>
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">大學
              </span>
            </font>
          </p></td>
        <td valign="top" width="336" colspan="10" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 252pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="254" colspan="3" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 190.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 14">
        <td valign="top" width="153" colspan="2" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 114.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-top-alt: .5pt; mso-border-left-alt: 1.5pt; mso-border-bottom-alt: 1.5pt; mso-border-right-alt: .5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <font size="3">
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">碩士
              </span>
              <span lang="EN-US">
                <font face="Times New Roman"> / 
                </font>
              </span>
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">博士
              </span>
            </font>
          </p></td>
        <td valign="top" width="336" colspan="10" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 252pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="254" colspan="3" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 190.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 15">
        <td valign="top" width="743" colspan="15" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext 1.5pt; mso-border-top-alt: solid windowtext 1.5pt; mso-border-bottom-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <font size="3">
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">工作經驗
              </span>
              <span lang="EN-US">
                <font face="Times New Roman">:<u>
                    <span style="mso-tab-count: 1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                    </span>
                    <span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp; 
                    </span></u>
                </font>
              </span>
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">年
              </span>
              <span lang="EN-US">
                <font face="Times New Roman"> 
                </font>
              </span>
            </font>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 16">
        <td width="69" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 51.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td width="102" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 76.2pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <font size="3">
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">始
              </span>
              <span lang="EN-US">
                <font face="Times New Roman">(
                </font>
              </span>
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">月
              </span>
              <span lang="EN-US">
                <font face="Times New Roman">/
                </font>
              </span>
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">年
              </span>
              <span lang="EN-US">
                <font face="Times New Roman">)
                </font>
              </span>
            </font>
          </p></td>
        <td width="97" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 72.8pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <font size="3">
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">至
              </span>
              <span lang="EN-US">
                <font face="Times New Roman">(
                </font>
              </span>
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">月
              </span>
              <span lang="EN-US">
                <font face="Times New Roman">/
                </font>
              </span>
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">年
              </span>
              <span lang="EN-US">
                <font face="Times New Roman">)
                </font>
              </span>
            </font>
          </p></td>
        <td width="139" colspan="5" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 104.25pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">
              <font size="3">職稱
              </font>
            </span>
          </p></td>
        <td width="166" colspan="3" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 124.75pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">
              <font size="3">公司名稱
              </font>
            </span>
          </p></td>
        <td width="170" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 127.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">
              <font size="3">公司規模
              </font>
            </span>
          </p>
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <font size="3">
              <span lang="EN-US">
                <font face="Times New Roman">(
                </font>
              </span>
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">員工人數
              </span>
              <span lang="EN-US">
                <font face="Times New Roman">)
                </font>
              </span>
            </font>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 17">
        <td valign="top" width="69" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 51.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">
              <font size="3">目前
              </font>
            </span>
          </p></td>
        <td valign="top" width="102" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 76.2pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="97" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 72.8pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="139" colspan="5" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 104.25pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="166" colspan="3" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 124.75pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="170" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 127.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 18">
        <td valign="top" width="69" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 51.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">1
              </font>
            </span>
          </p></td>
        <td valign="top" width="102" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 76.2pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="97" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 72.8pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="139" colspan="5" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 104.25pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="166" colspan="3" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 124.75pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="170" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 127.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 19">
        <td valign="top" width="69" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 51.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">2
              </font>
            </span>
          </p></td>
        <td valign="top" width="102" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 76.2pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="97" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 72.8pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="139" colspan="5" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 104.25pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="166" colspan="3" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 124.75pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="170" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 127.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 20">
        <td valign="top" width="69" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 51.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-top-alt: .5pt; mso-border-left-alt: 1.5pt; mso-border-bottom-alt: 1.5pt; mso-border-right-alt: .5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">3
              </font>
            </span>
          </p></td>
        <td valign="top" width="102" colspan="2" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 76.2pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="97" colspan="2" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 72.8pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="139" colspan="5" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 104.25pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="166" colspan="3" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 124.75pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="170" colspan="2" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 127.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 21">
        <td valign="top" width="743" colspan="15" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'">
              <font size="3">語言能力
                <span lang="EN-US"> (
                </span>精通
                <span lang="EN-US"> / 
                </span>中等
                <span lang="EN-US">):
                </span>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 22">
        <td valign="top" width="743" colspan="15" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <font size="3">
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">軟體及程式語言能力
              </span>
              <span lang="EN-US">
                <font face="Times New Roman"> (
                </font>
              </span>
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">簡述
              </span>
              <span lang="EN-US">
                <font face="Times New Roman">):
                </font>
              </span>
            </font>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 23">
        <td valign="top" width="743" colspan="15" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <font size="3">
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">其他技能
              </span>
              <span lang="EN-US">
                <font face="Times New Roman"> (
                </font>
              </span>
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">簡述
              </span>
              <span lang="EN-US">
                <font face="Times New Roman">):
                </font>
              </span>
            </font>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 24">
        <td valign="top" width="743" colspan="15" style="border-bottom: #c0c0c0; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <font size="3">
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">自傳
              </span>
              <span lang="EN-US">
                <font face="Times New Roman"> (2000
                </font>
              </span>
              <span style="font-family: '新細明體','serif'; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">字以內
              </span>
              <span lang="EN-US">
                <font face="Times New Roman">):
                </font>
              </span>
            </font>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; height: 4pt; mso-yfti-irow: 25">
        <td valign="top" width="743" colspan="15" style="border-bottom: #c0c0c0; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; height: 4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; height: 4pt; mso-yfti-irow: 26">
        <td valign="top" width="743" colspan="15" style="border-bottom: #c0c0c0; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; height: 4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; height: 4pt; mso-yfti-irow: 27">
        <td valign="top" width="743" colspan="15" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; height: 4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 28; mso-yfti-lastrow: yes">
        <td width="321" colspan="7" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 240.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'">
              <font size="3">希望薪資待遇
                <span lang="EN-US">(NT$/
                </span>月
                <span lang="EN-US">): 
                  <o:p>
                  </o:p>
                </span>
              </font>
            </span>
          </p></td>
        <td width="422" colspan="8" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 316.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext 1.5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span style="font-family: '新細明體','serif'">
              <font size="3">可上班日
                <span lang="EN-US"> (
                </span>月
                <span lang="EN-US">/
                </span>日
                <span lang="EN-US">/
                </span>年
                <span lang="EN-US">):
                  <o:p>
                  </o:p>
                </span>
              </font>
            </span>
          </p></td>
      </tr>
      <tr>
        <td width="69" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="84" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="18" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="54" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="43" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="41" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="12" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="36" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="14" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="35" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="46" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="36" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="84" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="60" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="110" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
      </tr>
    </tbody>
  </table>
  <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
    <span lang="EN-US">
      <o:p>
        <font size="3" face="Times New Roman">&nbsp;
        </font>
      </o:p>
    </span>
  </p>
</div>

</div>

</c:if>
			
			</jsp:attribute>
			<jsp:body>
				<FCK:config 
				SkinPath="skins/office2003/"
				ImageBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
				LinkBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
				FlashBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
				ImageUploadURL="/CIS/Simpleuploader?Type=Image" 
				LinkUploadURL="/CIS/Simpleuploader?Type=File" 
				FlashUploadURL="/CIS/Simpleuploader?Type=Flash"/>
				
			</jsp:body>
		</FCK:editor>
		</td>
	</tr>	
	
	
	
	<tr height="40">
		<td class="fullColorTable" align="center">
		
		
		<INPUT type="submit" name="method" value="<bean:message key='Save'/>" class="gSubmit">
		
		<input type="button" value="取消" class="gCancel">
		
		
		</td>
	</tr>
	
	
	
	
	
	
	<tr>
		<td>
		
		<FCK:editor instanceName="en" toolbarSet="vitae" basePath="/pages/include/fckeditor">
			<jsp:attribute name="value">
			
			
			
			
			
			<c:if test="${content.content_en!=null}">
			${content.content_en}
			</c:if>
			
			
			<c:if test="${content.content_en==null}">
			
			
			
			
			
			<p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt"><b>
    <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
      <font size="3">English Resume
        <o:p>
        </o:p>
      </font>
    </span></b>
</p>
<p class="MsoNormal" align="right" style="text-align: right; margin: 0cm 0cm 0pt">
  <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
    <font size="3">1/1
      <o:p>
      </o:p>
    </font>
  </span>
</p>
<p style="text-align: center">
  <table class="MsoNormalTable" border="1" cellspacing="0" cellpadding="0" style="border-bottom: medium none; border-left: medium none; margin: auto auto auto 3.5pt; border-collapse: collapse; border-top: medium none; border-right: medium none; mso-border-alt: solid windowtext .5pt; mso-padding-alt: 0cm 1.4pt 0cm 1.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext">
    <tbody>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 0; mso-yfti-firstrow: yes">
        <td width="633" colspan="14" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 474.9pt; padding-right: 1.4pt; border-top: windowtext 1.5pt solid; border-right: #c0c0c0; padding-top: 0cm">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">Required Fields:<b>
                  <o:p>
                  </o:p></b>
              </font>
            </span>
          </p></td>
        <td width="110" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 82.4pt; padding-right: 1.4pt; border-top: windowtext 1.5pt solid; border-right: windowtext 1.5pt solid; padding-top: 0cm">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <o:p>
                <font size="3">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="mso-yfti-irow: 1">
        <td width="225" colspan="4" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 168.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: #c0c0c0; padding-top: 0cm; mso-border-top-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">First Name:
                <span style="color: #993300">
                  <o:p>
                  </o:p>
                </span>
              </font>
            </span>
          </p></td>
        <td width="228" colspan="7" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 171pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">Last Name:
                <span style="color: blue">
                  <o:p>
                  </o:p>
                </span>
              </font>
            </span>
          </p></td>
        <td width="290" colspan="4" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 217.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext 1.5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <font size="3">
              <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">Gender: 
              </span>
              <span style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">□
                <span lang="EN-US">Male 
                </span>□
                <span lang="EN-US">Female<b>
                    <span style="color: blue">
                      <o:p>
                      </o:p>
                    </span></b>
                </span>
              </span>
            </font>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 2">
        <td width="743" colspan="15" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">ID Number (for non-Taiwan resident, please fill Passport No.):
                <o:p>
                </o:p>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 3">
        <td width="309" colspan="6" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 231.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-top-alt: 1.5pt; mso-border-left-alt: 1.5pt; mso-border-bottom-alt: .5pt; mso-border-right-alt: .5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">Birthday (mm/dd/yyyy):
                <o:p>
                </o:p>
              </font>
            </span>
          </p></td>
        <td width="434" colspan="9" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 325.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: 1.5pt; mso-border-left-alt: .5pt; mso-border-bottom-alt: .5pt; mso-border-right-alt: 1.5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">Marital Status:
                <o:p>
                </o:p>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 4">
        <td width="743" colspan="15" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: .5pt; mso-border-left-alt: 1.5pt; mso-border-bottom-alt: .5pt; mso-border-right-alt: 1.5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; text-indent: -0.3pt; margin: 0cm 0cm 0pt 1.4pt; mso-para-margin-left: .09gd">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">Contact Information:
                <o:p>
                </o:p>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 5">
        <td width="357" colspan="8" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 267.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt 12.6pt; mso-para-margin-left: 1.05gd">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">1.Mobile Phone:
                <o:p>
                </o:p>
              </font>
            </span>
          </p></td>
        <td width="386" colspan="7" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 289.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">2.E-mail:
                <o:p>
                </o:p>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 6">
        <td width="743" colspan="15" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; text-indent: 12pt; margin: 0cm 0cm 0pt; mso-char-indent-count: 1.0">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">3. Mailing Address:
                <o:p>
                </o:p>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 7">
        <td width="743" colspan="15" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">Main Method of Transportation:(
                <span style="mso-spacerun: yes">&nbsp; 
                </span>)Motorcycle (
                <span style="mso-spacerun: yes">&nbsp; 
                </span>)Car (
                <span style="mso-spacerun: yes">&nbsp; 
                </span>)Bicycle (
                <span style="mso-spacerun: yes">&nbsp; 
                </span>)Mass Transit
                <o:p>
                </o:p>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 8">
        <td width="743" colspan="15" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext 1.5pt; mso-border-top-alt: solid windowtext 1.5pt; mso-border-bottom-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">Career Level:(
                <span style="mso-spacerun: yes">&nbsp; 
                </span>)Fulltime
                <span style="mso-spacerun: yes">&nbsp; 
                </span>(
                <span style="mso-spacerun: yes">&nbsp; 
                </span>)Just Graduated
                <span style="mso-spacerun: yes">&nbsp; 
                </span>(
                <span style="mso-spacerun: yes">&nbsp; 
                </span>)Part-time Study Daytime
                <span style="mso-spacerun: yes">&nbsp; 
                </span>(
                <span style="mso-spacerun: yes">&nbsp; 
                </span>)Part-time Study Nighttime
                <o:p>
                </o:p>
              </font>
            </span>
          </p>
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; text-indent: 60pt; margin: 0cm 0cm 0pt; mso-char-indent-count: 5.0">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">(
                <span style="mso-spacerun: yes">&nbsp; 
                </span>)Homemaker
                <span style="mso-spacerun: yes">&nbsp; 
                </span>(
                <span style="mso-spacerun: yes">&nbsp; 
                </span>)Over Age 40
                <o:p>
                </o:p>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 9">
        <td width="743" colspan="15" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: .5pt; mso-border-left-alt: 1.5pt; mso-border-bottom-alt: .5pt; mso-border-right-alt: 1.5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">Current Career Status:(
                <span style="mso-spacerun: yes">&nbsp; 
                </span>)Employed
                <span style="mso-spacerun: yes">&nbsp; 
                </span>(
                <span style="mso-spacerun: yes">&nbsp; 
                </span>)Between Jobs
                <o:p>
                </o:p>
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 10">
        <td valign="top" width="372" colspan="9" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 278.65pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-top-alt: 1.5pt; mso-border-left-alt: 1.5pt; mso-border-bottom-alt: .5pt; mso-border-right-alt: .5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <font face="Times New Roman">
              <font size="3">
                <span lang="EN-US">Educational Profile:
                </span>
                <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
                  <o:p>
                  </o:p>
                </span>
              </font>
            </font>
          </p></td>
        <td valign="top" width="372" colspan="6" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 278.65pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: 1.5pt; mso-border-left-alt: .5pt; mso-border-bottom-alt: .5pt; mso-border-right-alt: 1.5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <font face="Times New Roman">
              <font size="3">
                <span lang="EN-US" style="color: black; mso-bidi-font-size: 9.0pt">Completion Date (mm/yyyy):
                </span>
                <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
                  <o:p>
                  </o:p>
                </span>
              </font>
            </font>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 11">
        <td valign="top" width="153" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 114.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="336" colspan="10" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 252pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">School
              </font>
            </span>
          </p></td>
        <td valign="top" width="254" colspan="3" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 190.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">Major
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 12">
        <td valign="top" width="153" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 114.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">High school
              </font>
            </span>
          </p></td>
        <td valign="top" width="336" colspan="10" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 252pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="254" colspan="3" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 190.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 13">
        <td valign="top" width="153" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 114.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">College / University
              </font>
            </span>
          </p></td>
        <td valign="top" width="336" colspan="10" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 252pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="254" colspan="3" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 190.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 14">
        <td valign="top" width="153" colspan="2" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 114.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-top-alt: .5pt; mso-border-left-alt: 1.5pt; mso-border-bottom-alt: 1.5pt; mso-border-right-alt: .5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">Master / Doctorate
              </font>
            </span>
          </p></td>
        <td valign="top" width="336" colspan="10" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 252pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="254" colspan="3" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 190.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 15">
        <td valign="top" width="743" colspan="15" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext 1.5pt; mso-border-top-alt: solid windowtext 1.5pt; mso-border-bottom-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">Years of Working Experience:<u>
                  <span style="mso-tab-count: 1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  </span>
                  <span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp; 
                  </span></u>years 
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 16">
        <td width="69" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 51.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td width="102" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 76.2pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">From(mm/yy)
              </font>
            </span>
          </p></td>
        <td width="97" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 72.8pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">To(mm/yy)
              </font>
            </span>
          </p></td>
        <td width="139" colspan="5" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 104.25pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">Title
              </font>
            </span>
          </p></td>
        <td width="166" colspan="3" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 124.75pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">Company
              </font>
            </span>
          </p></td>
        <td width="170" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 127.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">Company size
              </font>
            </span>
          </p>
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">(Number of employees)
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 17">
        <td valign="top" width="69" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 51.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">Now
              </font>
            </span>
          </p></td>
        <td valign="top" width="102" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 76.2pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="97" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 72.8pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="139" colspan="5" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 104.25pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="166" colspan="3" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 124.75pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="170" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 127.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 18">
        <td valign="top" width="69" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 51.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">1
              </font>
            </span>
          </p></td>
        <td valign="top" width="102" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 76.2pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="97" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 72.8pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="139" colspan="5" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 104.25pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="166" colspan="3" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 124.75pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="170" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 127.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 19">
        <td valign="top" width="69" style="border-bottom: windowtext 1pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 51.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">2
              </font>
            </span>
          </p></td>
        <td valign="top" width="102" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 76.2pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="97" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 72.8pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="139" colspan="5" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 104.25pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="166" colspan="3" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 124.75pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="170" colspan="2" style="border-bottom: windowtext 1pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 127.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 20">
        <td valign="top" width="69" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 51.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-top-alt: .5pt; mso-border-left-alt: 1.5pt; mso-border-bottom-alt: 1.5pt; mso-border-right-alt: .5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">3
              </font>
            </span>
          </p></td>
        <td valign="top" width="102" colspan="2" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 76.2pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="97" colspan="2" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 72.8pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="139" colspan="5" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 104.25pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="166" colspan="3" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 124.75pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1pt solid; padding-top: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
        <td valign="top" width="170" colspan="2" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 127.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt">
          <p class="MsoNormal" align="center" style="text-align: center; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 21">
        <td valign="top" width="743" colspan="15" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">Language Fluency (excellent / fair):
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 22">
        <td valign="top" width="743" colspan="15" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">Software and Programming Language Proficiency (in brief):
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 23">
        <td valign="top" width="743" colspan="15" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext .5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">Other Talents (in brief):
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 24">
        <td valign="top" width="743" colspan="15" style="border-bottom: #c0c0c0; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <font size="3" face="Times New Roman">Autobiography (no more than 2000 words):
              </font>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; height: 4pt; mso-yfti-irow: 25">
        <td valign="top" width="743" colspan="15" style="border-bottom: #c0c0c0; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; height: 4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; height: 4pt; mso-yfti-irow: 26">
        <td valign="top" width="743" colspan="15" style="border-bottom: #c0c0c0; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; height: 4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; height: 4pt; mso-yfti-irow: 27">
        <td valign="top" width="743" colspan="15" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 557.3pt; padding-right: 1.4pt; height: 4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm">
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p>
          <p class="MsoNormal" style="margin: 0cm 0cm 0pt">
            <span lang="EN-US">
              <o:p>
                <font size="3" face="Times New Roman">&nbsp;
                </font>
              </o:p>
            </span>
          </p></td>
      </tr>
      <tr style="page-break-inside: avoid; mso-yfti-irow: 28; mso-yfti-lastrow: yes">
        <td width="321" colspan="7" style="border-bottom: windowtext 1.5pt solid; border-left: windowtext 1.5pt solid; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 240.9pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">Desired Salary(NT$/month): 
                <o:p>
                </o:p>
              </font>
            </span>
          </p></td>
        <td width="422" colspan="8" style="border-bottom: windowtext 1.5pt solid; border-left: #c0c0c0; padding-bottom: 0cm; background-color: transparent; padding-left: 1.4pt; width: 316.4pt; padding-right: 1.4pt; border-top: #c0c0c0; border-right: windowtext 1.5pt solid; padding-top: 0cm; mso-border-top-alt: solid windowtext 1.5pt; mso-border-left-alt: solid windowtext 1.5pt">
          <p class="MsoNormal" style="text-justify: inter-ideograph; text-align: justify; margin: 0cm 0cm 0pt">
            <span lang="EN-US" style="font-family: &quot;新細明體&quot;,&quot;serif&quot;">
              <font size="3">Available Date for Next Job (mm/dd/yyyy):
                <o:p>
                </o:p>
              </font>
            </span>
          </p></td>
      </tr>
      <tr>
        <td width="69" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="84" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="18" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="54" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="43" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="41" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="12" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="36" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="14" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="35" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="46" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="36" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="84" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="60" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
        <td width="110" style="border-bottom: #c0c0c0; border-left: #c0c0c0; background-color: transparent; border-top: #c0c0c0; border-right: #c0c0c0">&nbsp;</td>
      </tr>
    </tbody>
  </table>
</p>
<p class="MsoNormal" style="margin: 0cm 0cm 0pt">
  <span lang="EN-US">
    <o:p>
      <font size="3" face="Times New Roman">&nbsp;
      </font>
    </o:p>
  </span>
</p>
			
			
			
			</c:if>
			
			
			
			
			
			
			
			</jsp:attribute>
			<jsp:body>
				<FCK:config 
				SkinPath="skins/office2003/"
				ImageBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
				LinkBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
				FlashBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
				ImageUploadURL="/CIS/Simpleuploader?Type=Image" 
				LinkUploadURL="/CIS/Simpleuploader?Type=File" 
				FlashUploadURL="/CIS/Simpleuploader?Type=Flash"/>
			</jsp:body>
		</FCK:editor>
		
		
		
		
		</td>
	</tr>
	
	
	
	
	
	
	<tr height="40">
		<td class="fullColorTable" align="center">
		
		
		<INPUT type="submit" name="method" value="<bean:message key='Save'/>" class="gSubmit">
		
		<input type="button" value="取消" class="gCancel">
		
		
		</td>
	</tr>
	

	

	
	
	
</html:form>
</table>