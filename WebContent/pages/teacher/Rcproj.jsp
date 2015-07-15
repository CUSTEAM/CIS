<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rcproj" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>承接政府部門計劃與產學案資料表&nbsp;</b></td>
          </tr>
        </table>
      </td>
    </tr>	
    <tr>
      <td>
        <table width="100%" cellpadding="0" cellspacing="0" onClick="showSpecs('Specs1_8')">
          <tr style="cursor:pointer;">
            <td width="10" align="left" nowrap>
              <hr class="myHr"/>
            </td>
            <td width="24" align="center" nowrap>
              <img src="images/folder_explore.gif" id="searchEx">
            </td>
            <td nowrap><font color=red><b>填表說明&nbsp;</b></font></td>
            <td width="100%" align="left">
              <hr class="myHr"/>
            </td>
          </tr>
        </table>
        <table width="100%" cellpadding="0" cellspacing="0">  <!-- 填表說明區 -->
          <tr height="35" valign="middle" bgcolor="#E1D2A6" style="display:none" id="Specs1_8">
            <td>
              <div class="modulecontainer filled nomessages">
	        <div class="first">
	          <span class="first"></span>
	          <span class="last"></span>
	        </div>
	        <div>
	          <div>
	            1. 本程式仿雲科大校務基本資料庫格式與介面，所有欄位不能空白，<font color=red>如果沒有資料請填「無」</font>。 <br>
	            2. 如果要新增，請依照畫面欄位填入相關資料，填寫完畢按下新增即可。 <br>
	            3. 如果要修改記錄，可以直接按下查詢或輸入年度後按下查詢，即可顯示已輸入資料清單，勾選要作業的資料按下檢視即可執行。  <br>
	                                欄位說明如下:<br>
	            &nbsp;&nbsp;1) 年度：請輸入民國年(非學年度)<br>
	            &nbsp;&nbsp;2) 專案案號：專案的編號。若為國科會請填入國科會所給之案號，否則請學校編列案號。 <br>
	            &nbsp;&nbsp;3) 專案案名：計畫的名稱。 <br>
	            &nbsp;&nbsp;4) 專案類型：請點選：教育部計畫型獎助岸、政府產學計畫、政府委訓計畫、政府學術研究計畫、政府其他案件、<br>
	            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                                                                              企業產學計畫(公、私營企業)、企業委訓計畫、其他單位產學計畫、其他單位委訓計畫、技術服務案、校內補助案。 <br>                
                &nbsp;&nbsp;5) 工作類別：請選擇主持人、共同(協同)主持人。 <br>
                &nbsp;&nbsp;6) 經費狀況：該計畫經費所屬合約之狀況，由下拉選單選出原核定、變更-增加經費、變更-刪減經費。<br>
	            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                                                                              合約執行期間，若有增刪其執行預算，請依該筆變更經費之變更生效的起始年度作認列。 <br>
                &nbsp;&nbsp;7) 「執行起始日期」與「執行結束日期」：專案簽約確定的日期，日期格式為西元年，例如<font color=red>「2005/01/01」</font>。 <br>
                &nbsp;&nbsp;8) 計畫總金額：所提供的研究費用，以元為單位。若為共同(協同)主持人，則不需填此欄位。 <br>
                &nbsp;&nbsp;9) 政府出資金額：該計畫之經費，其中來自政府單位的部份。<br>
                &nbsp;&nbsp;10) 企業出資金額：該計畫之經費，其中來自公營或私人企業的部份。 <br>
	            &nbsp;&nbsp;11)其他出資金額：該計畫之經費，其中來自其他單位(法人、學會、專業學術團體...等)的部份。 <br>
	            &nbsp;&nbsp;12)學校出資金額：該計畫之經費，其中來自學校本身的部份。 <br>
	            &nbsp;&nbsp;13)主要經費來源：專案所屬機關分類，請選擇國科會、教育部、政府其他部會、企業、其他單位、學校(非本校請填其他單位)。 <br>
	            &nbsp;&nbsp;14)主要經費來源單位：提供主要提供經費之單位名稱。 <br>
	            &nbsp;&nbsp;15)次要經費來源單位：若有兩個以上的經費來源，可於此再填寫第二個經費來源單位。 <br>
	            &nbsp;&nbsp;16)受惠機構名稱：因本專案而受惠之機構名稱，可填寫多筆。包括專案改良產品品質、改善原有缺失、改進作業程序等。<br>
	            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                                                                                       此合作機構指政府機關或依法登記領有營利事業登記證者。 <br>
	            &nbsp;&nbsp;17)委託單位：專案所屬機關名稱，分別國內及國外機關名稱。 <br>
	            &nbsp;&nbsp;18)合作單位：與本專案相關之機關名稱，分別國內及國外機關名稱。 <br>
	            <!--  
	            &nbsp;&nbsp;18)專案聘任之專任人員：因執行本計劃所需，以本計劃經費聘用之專任人員<font color=red>人數</font>。 <br>
	            &nbsp;&nbsp;19)專案聘任之間任人員：因執行本計劃所需，以本計劃經費聘用之兼任人員<font color=red>人數</font>。 <br>
	            &nbsp;&nbsp;20)政府委訓計畫受訓人次：因政府機構(國科會、教育部、政府其他單位)委訓計畫我托訓練結訓之總人次。 <br>
	            &nbsp;&nbsp;21)企業委訓計畫受訓人次：因企業委訓計畫我托訓練結訓之總人次。 <br>
	            &nbsp;&nbsp;22)其他單位委訓計畫受訓人次：因其他單位委訓計畫我托訓練結訓之總人次。 <br>
	            -->
	            &nbsp;&nbsp;19)他校轉入的案件：請選擇「是」或「否」。 <br>
	            &nbsp;&nbsp;20)他校轉入的政府出資金額：輸入政府資出金額，若為共同(協同)主持人，則不需填此欄位。 <br>
	            &nbsp;&nbsp;21)他校轉入的企業出資金額：輸入企業資出金額，若為共同(協同)主持人，則不需填此欄位。 <br>
	            &nbsp;&nbsp;22)他校轉入的其他單位出資金額：輸入其他單位資出金額，若為共同(協同)主持人，則不需填此欄位。 <br>
	            &nbsp;&nbsp;23)專案已轉至他校：請選擇「是」或「否」。 <br>
	            &nbsp;&nbsp;24)已轉至他校的政府出資金額：輸入政府資出金額，若為共同(協同)主持人，則不需填此欄位。 <br>
	            &nbsp;&nbsp;25)已轉至他校的企業出資金額：輸入企業資出金額，若為共同(協同)主持人，則不需填此欄位。 <br>
	            &nbsp;&nbsp;26)已轉至他校的其他單位出資金額：輸入其他單位資出金額，若為共同(協同)主持人，則不需填此欄位。 <br>
	            &nbsp;&nbsp;27)摘要/簡述：輸入專案相關摘要敘述，<font color=red>字數不可低於250字</font>。
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
      </td>
    </tr> 
<!-- 標題列 end -->
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">  
          <tr height="35" align="center" valign="middle" bgcolor="#CCCCFF">
            <font color="blue">${TeacherUnit}&nbsp;&nbsp;${TeacherName}&nbsp;老師</font>
          </tr>
        </table>        
      </td>
    </tr>
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td>            
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">民國年度</td>
                  <td class="hairLineTd">
                    <input type="text" name="schoolYear" id="schoolYear" size="1" value=""
                           onMouseOver="showHelpMessage('請輸入民國年,非學年度', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">專案案號</td>
                  <td class="hairLineTd">
                    <input type="text" name="projno" value=""/>
                  </td>                   
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>                         
              <table width="99%" class="hairLineTable">
                <tr>                  
                  <td width="15%" align="center" class="hairLineTdF">專案名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="projname" value="" size="45"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">專案類型</td>
                  <td class="hairLineTd" >
                    <select name="kindid" onChange="NSC_Turn()">                    
					  <option value=""></option>
					  <c:forEach items="${kindid}" var="c">
					    <option <c:if test="${aEmpl.kindid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">  
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">執行起始日期(YYYY/MM/DD)</td>
                  <td class="hairLineTd">
                    <input type="text" name="bdate" value="" readonly="ture"
                           style="ime-mode:disabled" autocomplete="off"
                           onclick="ds_sh(this), this.value='';" 
                           onfocus="ds_sh(this), this.value='';" />
                  </td>
                  <td>
                    <table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
		              <tr>
				        <td id="ds_calclass"></td>
			          </tr>
			        </table> 
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">執行結束日期(YYYY/MM/DD)</td>
                  <td class="hairLineTd">
                    <input type="text" name="edate" value="" readonly="ture"
                           style="ime-mode:disabled" autocomplete="off"
                           onclick="ds_sh(this), this.value='';" 
                           onfocus="ds_sh(this), this.value='';" />
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable"> 
                <tr>                  
                  <td width="15%" align="center" class="hairLineTdF">工作類別</td>
                  <td class="hairLineTd">
                    <select name=jobid>
					  <option value=""></option>
					  <c:forEach items="${jobid}" var="c">
					    <option <c:if test="${aEmpl.jobid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>                    
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">經費狀況</td>
                  <td class="hairLineTd">
                    <select name=budgetidState>
					  
					  <c:forEach items="${budgetidState}" var="c">
					    <option value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>                    
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">計劃總金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="money" id="money" value=""
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable"> 
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">政府出資</td>
                  <td class="hairLineTd">
                    <input type="text" name="G_money" id="G_money" size="5" value=""
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">企業出資</td>
                  <td class="hairLineTd">
                    <input type="text" name="B_money" id="B_money" size="5" value=""
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>                                 
                  <td width="15%" align="center" class="hairLineTdF">其他單位出資</td>
                  <td class="hairLineTd">
                    <input type="text" name="O_money" id="O_money" size="5" value=""
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">學校出資</td>
                  <td class="hairLineTd">
                    <input type="text" name="S_money" id="S_money" size="5" value=""
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>                 
                  <td width="15%" align="center" class="hairLineTdF">主要經費來源</td>
                  <td class="hairLineTd">
                    <select name=budgetid1>
					  <option value=""></option>
					  <c:forEach items="${budgetid1}" var="c">
					    <option <c:if test="${aEmpl.budgetid1==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="20%" align="center" class="hairLineTdF">主要經費來源單位名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="unitname" value="" size="30"/>
                  </td>
                </tr>
                <tr>                 
                  <td width="15%" align="center" class="hairLineTdF">次要經費來源</td>
                  <td class="hairLineTd">
                    <input type="text" name="budgetid2" value="" size="30"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">受惠機構名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="favorunit" value="" size="30"/>
                  </td>
                </tr>
                <tr>                  
                  <td width="15%" align="center" class="hairLineTdF">國內委託單位</td>
                  <td class="hairLineTd">
                    <input type="text" name="authorunit1" value="" size="30"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">國外委託單位</td>
                  <td class="hairLineTd">
                    <input type="text" name="authorunit2" value="" size="30"/>
                  </td>
                </tr>
                <tr>                  
                  <td width="15%" align="center" class="hairLineTdF">國內合作單位</td>
                  <td class="hairLineTd">
                    <input type="text" name="coopunit1" value="" size="30"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">國外合作單位</td>
                  <td class="hairLineTd">
                    <input type="text" name="coopunit2" value="" size="30"/>
                  </td>
                </tr>
                <!-- 
                <tr>                  
                  <td width="15%" align="center" class="hairLineTdF">聘任專任人員</td>
                  <td class="hairLineTd">
                    <input type="text" name="FullTime" id="FullTime" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" value="0"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">聘任兼任人員</td>
                  <td class="hairLineTd">
                    <input type="text" name="PartTime" id="PartTime" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" value="0"/>
                  </td>
                </tr>                 
              </table>
              <table width="99%" class="hairLineTable">
                <tr>                  
                  <td width="15%" align="center" class="hairLineTdF">政府委訓人數</td>
                  <td class="hairLineTd">
                    <input type="text" name="G_trainee" id="G_trainee" size="10" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" value="0"/>
                  </td>             
                  <td width="15%" align="center" class="hairLineTdF">企業委訓人數</td>
                  <td class="hairLineTd">
                    <input type="text" name="B_trainee" id="B_trainee" size="10" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" value="0"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">其他單位委訓人數</td>
                  <td class="hairLineTd">
                    <input type="text" name="O_trainee" id="O_trainee" size="10" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" value="0"/>
                  </td>
                </tr>
                -->
              </table>              
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">他校轉入的專案</td>
                  <td class="hairLineTd">
                    <select name="turnIn" onChange="myTurn('turnIn')">					  
					  <c:forEach items="${YesNo}" var="c">
					    <option <c:if test="${93==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">專案已轉至他校</td>
                  <td class="hairLineTd">
                    <select name="turnOut" onChange="myTurn('turnOut')">					  
					  <c:forEach items="${YesNo}" var="c">
					    <option <c:if test="${93==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr style="display:none" id="turnIns">                  
                  <td width="15%" align="center" class="hairLineTdF">他校轉入政府出資金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="turnIn_G" id="turnIn_G" size="10" value="0"
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>             
                  <td width="15%" align="center" class="hairLineTdF">他校轉入企業出資金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="turnIn_B" id="turnIn_B" size="10" value="0"
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">他校轉入其他單位出資金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="turnIn_O" id="turnIn_O" size="10" value="0"
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                </tr>
                <tr style="display:none" id="turnOuts">                  
                  <td width="15%" align="center" class="hairLineTdF">轉至他校政府出資金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="turnOut_G" id="turnOut_G" size="10" value="0"
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>             
                  <td width="15%" align="center" class="hairLineTdF">轉至他校企業出資金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="turnOut_B" id="turnOut_B" size="10" value="0"
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">轉至他校其他單位出資金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="turnOut_O" id="turnOut_O" size="10" value="0"
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                </tr>
              </table>
              
              <table width="99%" class="hairLineTable"><!-- 國科會資料區 -->
                <tr style="display:none" id="turnNSC">
                  <td>
                    <table width="99%" class="hairLineTable">
                      <tr>                 
                        <td width="15%" align="center" class="hairLineTdF">有無技轉</td>
                        <td class="hairLineTd">
                          <select name="Transfer">					  
					        <c:forEach items="${YesNo}" var="c">
					          <option <c:if test="${93==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					        </c:forEach>
				          </select>
				        </td>         
                        <td width="15%" align="center" class="hairLineTdF">研發收入</td>
                        <td class="hairLineTd">
                          <input type="text" name="Income" id="Income" size="10" value="0"
                                 onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                                 onMouseOut="showHelpMessage('', 'none', this.id)" />
                        </td>
                        <td width="15%" align="center" class="hairLineTdF">研發成果保管處</td>
                        <td class="hairLineTd">
                          <input type="text" name="Storage" id="Storage" size="10" readonly="ture" value="${TeacherUnit}"/>
                        </td>                                    
                      </tr>
                      <tr>
                        <td width="15%" align="center" class="hairLineTdF" rowspan="6">研發成果</td>
                        <td class="hairLineTd" colspan="5">
                          <INPUT type=checkbox name=Fruitful id=chec1 value="1"/>1.國內外專利                            
                            <input type="text" name="chec1text" id="chec1text" value="" size="50" /><br>
                          <INPUT type=checkbox name=Fruitful id=chec2 value="2"/>2.商標權
                            <input type="text" name="chec2text" id="chec2text" value="" size="50" /><br>
                          <INPUT type=checkbox name=Fruitful id=chec3 value="3"/>3.營業機密
                            <input type="text" name="chec3text" id="chec3text" value="" size="50" /><br>
                          <INPUT type=checkbox name=Fruitful id=chec4 value="4"/>4.積體電路電路佈局權
                            <input type="text" name="chec4text" id="chec4text" value="" size="50" /><br>
                          <INPUT type=checkbox name=Fruitful id=chec5 value="5"/>5.著作權
                            <input type="text" name="chec5text" id="chec5text" value="" size="50" /><br>
                          <INPUT type=checkbox name=Fruitful id=chec6 value="6"/>6.其他智慧財產權及成果
                            <input type="text" name="chec6text" id="chec6text" value="" size="50" />      
                        </td>                      
                      </tr>
                    </table>
                  </td>
                </tr>                
              </table>                
                              
              <table width="99%" class="hairLineTable"> 
                <tr>
                  <td>
                  	摘要/簡述<br><font color=red>輸入字數不得低於350字</font>
                  </td>                  
                </tr>
                <tr>
				  <td>
					<textarea name="intor" rows="10" cols="90">${intor }</textarea>
                  </td>
				</tr>
              </table>              
            </td>
          </tr>
          <tr>
            <td class="hairLineTd" align="center">
              <input type="submit" name="method" value="<bean:message key='Create'/>" class="CourseButton" onClick="return mySave()"/>
              <input type="submit" name="method" value="查詢" Key="Query" class="CourseButton"/>
              <input type="button" class="CourseButton" value="返回" id="Back" onclick="location='/CIS/Teacher/Article.do';"/>
              <hr class="myHr"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <c:if test="${RcprojList != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <table width="100%" cellpadding="0" cellspacing="0"> <!-- 查詢後條列表 -->
		    <tr>
		      <td align="center">  
	            <display:table name="${RcprojList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty RcprojList}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(RcprojList)}, 'rcproj');</script>" class="center" >
	                <script>generateCheckbox("${row.oid}", "rcproj");</script></display:column>
 	              <display:column title="年度"		property="school_year"	sortable="true" class="center" />	              
	              <display:column title="專案名稱"	property="projname"		sortable="true" class="center" />
	              <display:column title="起始日期" 	property="bdate" 		sortable="true" class="center" />
	              <display:column title="結束日期" 	property="edate" 		sortable="true" class="center" />
	              <display:column title="審核狀態"	property="name"		sortable="true" class="center" />	                            
 	            </display:table>
 	          </td>
 	        </tr>	      
	      </table>
	    </td>
	  </tr>
	  <tr>
        <td class="hairLineTd" align="center">
          <input type="submit" name="method" value="檢視" key="View" onClick="return myTest()" class="CourseButton"/>
          
        </td>
      </tr>
    </c:if>    
  </html:form>
</table>

<script>
  function showSpecs(ID) 
  {
    var id = ID;
    if(document.getElementById(id).style.display == 'none') 
    {
      document.getElementById(id).style.display = 'inline';      
    } 
    else 
    {
      document.getElementById(id).style.display = 'none';				
    }
  }
  
  function NSC_Turn()
  {      
    if(document.getElementById("kindid").value == '83'){      
      document.getElementById("turnNSC").style.display = 'inline';      
    }else{
      document.getElementById("turnNSC").style.display = 'none';				
    }
  }  
  
  function myTurn(ID) 
  {    
    var id = ID;
    if(document.getElementById(id).value == '92') 
    {
      document.getElementById(id + "s").style.display = 'inline';      
    }    
    else
    {
      document.getElementById(id + "s").style.display = 'none';				
    }
  }
  
  function myTest() 
  {
    var iCount;
    iCount = getCookie("rcprojCount");    
    if (iCount == 0) 
    {
      alert("請勾選至少一項資料進行檢視!!");
      return false;
    } 
    else if(iCount > 1) 
    {
      alert("請僅勾選一項資料進行檢視!!");
      return false;
    }
    Oid = getCookie("rcproj");
    //alert(Oid);
    return true;
  }
  
  function mySave() 
  {        
    var i;
    for(i=1; i<7; i++){      
      if(document.getElementById("chec"+i).checked & document.getElementById("chec"+i+"text").value=='')
      {
        alert("勾選的項目"+i+"，備註不可為空白。");
        return false;
      }
    }  
    if(document.getElementById("kindid").value == '83'
     & document.getElementById("chec1").checked!=true & document.getElementById("chec2").checked!=true
     & document.getElementById("chec3").checked!=true & document.getElementById("chec4").checked!=true 
     & document.getElementById("chec5").checked!=true & document.getElementById("chec6").checked!=true)
    {
      alert("請至少勾選一項研發成果");
      return false;
    }      
    else if 
    (document.getElementById("schoolYear").value == '') 
    {
      alert("年度,此欄位不得為空白");
      return false;
    }
    else if (document.getElementById("projno").value == '') 
    {
      alert("專案案號,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("projname").value == '') 
    {
      alert("專案名稱,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("kindid").value == '') 
    {
      alert("專案類型,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("bdate").value == '') 
    {
      alert("起始日期,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("edate").value == '') 
    {
      alert("結束日期,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("jobid").value == '') 
    {
      alert("工作類別,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("money").value == '') 
    {
      alert("研究金額,欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("G_money").value == '' & document.getElementById("G_money").value == '' & 
             document.getElementById("O_money").value == '' & document.getElementById("S_money").value == '') 
    {
      alert("出資金額四個欄位,不可均為空白");
      return false;
    }
    else if (document.getElementById("budgetid1").value == '') 
    {
      alert("主要經費來源,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("unitname").value == '') 
    {
      alert("單位名稱,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("favorunit").value == '') 
    {
      alert("受惠單位,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("authorunit1").value == '' & document.getElementById("authorunit2").value == '') 
    {
      alert("委託單位,此欄位不得為空白");
      return false;
    } 
    else 
    
    return true;
  }
</script>	

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/leo_Calendar.jsp" %>	