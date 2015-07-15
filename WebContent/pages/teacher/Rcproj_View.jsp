<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>


<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rcproj_View" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
  <body  onload="myLoad('${turnIn_v}','${turnOut_v}','${myKind }')">
<!--標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>承接政府部門計劃與產學案資料表&nbsp;</b></td>
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
                  <td width="15%" align="center" class="hairLineTdF">年度</td>
                  <td class="hairLineTd">
                    <input type=hidden name="oid_s" value="${oid_s }"/>
                    <input type="text" name="schoolYear" id="schoolYear" size="1" value="${school_year }"
                           onMouseOver="showHelpMessage('民國年', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />                    
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">專案案號</td>
                  <td class="hairLineTd">
                    <input type="text" name="projno" value="${projno }"/>
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
                    <input type="text" name="projname" value="${projname }" size="45"/>
                  </td>                
                  <td width="15%" align="center" class="hairLineTdF">專案類型</td>
                  <td class="hairLineTd" colspan="3">
                    <select name=kindid onChange="NSC_Turn()">					  
					  <c:forEach items="${kindid}" var="c">					    
					    <option <c:if test="${myKind==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">  
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">執行起始日期(YYYY/MM/DD)</td>
                  <td class="hairLineTd">
                    <input type="text" name="bdate" value="${bdate }" readonly="ture"
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
                    <input type="text" name="edate" value="${edate }" readonly="ture"
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
					    <option <c:if test="${jobid_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>                    
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">經費狀況</td>
                  <td class="hairLineTd">
                    <select name=budgetidState>
					  
					  <c:forEach items="${budgetidState}" var="c">
					    <option <c:if test="${budgetidState_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>                    
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">計劃總金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="money" id="money" value="${money }"
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable"> 
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">政府出資</td>
                  <td class="hairLineTd">
                    <input type="text" name="G_money" id="G_money" value="${G_money }" size="5" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">企業出資</td>
                  <td class="hairLineTd">
                    <input type="text" name="B_money" id="B_money" value="${B_money }" size="5" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">其他單位出資</td>
                  <td class="hairLineTd">
                    <input type="text" name="O_money" id="O_money" value="${O_money }" size="5" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">學校出資</td>
                  <td class="hairLineTd">
                    <input type="text" name="S_money" id="S_money" value="${S_money }" size="5" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)"/>
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
					    <option <c:if test="${budgetid1_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="20%" align="center" class="hairLineTdF">主要經費來源單位名稱單位名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="unitname" value="${unitname }" size="30"/>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">次要經費來源</td>
                  <td class="hairLineTd">
                    <input type="text" name="budgetid2" value="${budgetid2 }" size="30"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">受惠機構名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="favorunit" value="${favorunit }" size="30"/>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">國內委託單位</td>
                  <td class="hairLineTd">
                    <input type="text" name="authorunit1" value="${authorunit1 }" size="30"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">國外委託單位</td>
                  <td class="hairLineTd">
                    <input type="text" name="authorunit2" value="${authorunit2 }" size="30"/>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">國內合作單位</td>
                  <td class="hairLineTd">
                    <input type="text" name="coopunit1" value="${coopunit1 }" size="30"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">國外合作單位</td>
                  <td class="hairLineTd">
                    <input type="text" name="coopunit2" value="${coopunit2 }" size="30"/>
                  </td>
                </tr>
                <!-- 
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">聘任專任人員</td>
                  <td class="hairLineTd">
                    <input type="text" name="FullTime" id="FullTime" value="${FullTime }"
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">聘任兼任人員</td>
                  <td class="hairLineTd">
                    <input type="text" name="PartTime" id="PartTime" value="${PartTime }"
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable"> 
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">政府委訓人數</td>
                  <td class="hairLineTd">
                    <input type="text" name="G_trainee" id="G_trainee" value="${G_trainee }" size="10" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">企業委訓人數</td>
                  <td class="hairLineTd">
                    <input type="text" name="B_trainee" id="B_trainee" value="${B_trainee }" size="10" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">其他單位委訓人數</td>
                  <td class="hairLineTd">
                    <input type="text" name="O_trainee" id="O_trainee" value="${O_trainee }" size="10" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)"/>
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
					    <option <c:if test="${turnIn_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">專案已轉至他校</td>
                  <td class="hairLineTd">
                    <select name="turnOut" onChange="myTurn('turnOut')">					  
					  <c:forEach items="${YesNo}" var="c">
					    <option <c:if test="${turnOut_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                
                <tr id="turnIns">                  
                  <td width="15%" align="center" class="hairLineTdF">他校轉入政府出資金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="turnIn_G" id="turnIn_G" value="${turnIn_G }" size="10" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)"/>
                  </td>             
                  <td width="15%" align="center" class="hairLineTdF">他校轉入企業出資金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="turnIn_B" id="turnIn_B" value="${turnIn_B }" size="10" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">他校轉入其他單位出資金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="turnIn_O" id="turnIn_O" value="${turnIn_O }" size="10" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)"/>
                  </td>
                </tr>
                
                <tr id="turnOuts">                  
                  <td width="15%" align="center" class="hairLineTdF">轉至他校政府出資金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="turnOut_G" id="turnOut_G" value="${turnOut_G }" size="10" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)"/>
                  </td>             
                  <td width="15%" align="center" class="hairLineTdF">轉至他校企業出資金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="turnOut_B" id="turnOut_B" value="${turnOut_B }" size="10" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">轉至他校其他單位出資金額</td>
                  <td class="hairLineTd">
                    <input type="text" name="turnOut_O" id="turnOut_O" value="${turnOut_O }" size="10" 
                           onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)"/>
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
					          <option <c:if test="${Transfer_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					        </c:forEach>
				          </select>
				        </td>         
                        <td width="15%" align="center" class="hairLineTdF">研發收入</td>
                        <td class="hairLineTd">
                          <input type="text" name="Income" id="Income" size="10" value="${Income }"
                                 onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" 
                                 onMouseOut="showHelpMessage('', 'none', this.id)" />
                        </td>
                        <td width="15%" align="center" class="hairLineTdF">研發成果保管處</td>
                        <td class="hairLineTd">
                          <input type="text" name="Storage" id="Storage" size="10" readonly="ture" value="${Storage}"/>
                        </td>                                    
                      </tr>
                      <tr>
                        <td width="15%" align="center" class="hairLineTdF" rowspan="6">研發成果</td>
                        <td class="hairLineTd" colspan="5">
                          <INPUT type=checkbox name=Fruitful id=chec1 value="1" <c:if test="${chec1==1}">checked</c:if>/>
                            1.國內外專利                    <input type="text" name="chec1text" id="chec1text" value="${chec1text }" size="50" /><br>
                          <INPUT type=checkbox name=Fruitful id=chec2 value="2" <c:if test="${chec2==1}">checked</c:if>/>
                            2.商標權                             <input type="text" name="chec2text" id="chec2text" value="${chec2text }" size="50" /><br>
                          <INPUT type=checkbox name=Fruitful id=chec3 value="3" <c:if test="${chec3==1}">checked</c:if>/>
                            3.營業機密                         <input type="text" name="chec3text" id="chec3text" value="${chec3text }" size="50" /><br>
                          <INPUT type=checkbox name=Fruitful id=chec4 value="4" <c:if test="${chec4==1}">checked</c:if>/>
                            4.積體電路電路佈局權   <input type="text" name="chec4text" id="chec4text" value="${chec4text }" size="50" /><br>
                          <INPUT type=checkbox name=Fruitful id=chec5 value="5" <c:if test="${chec5==1}">checked</c:if>/>
                            5.著作權                              <input type="text" name="chec5text" id="chec5text" value="${chec5text }" size="50" /><br>
                          <INPUT type=checkbox name=Fruitful id=chec6 value="6" <c:if test="${chec6==1}">checked</c:if>/>
                            6.其他智慧財產權及成果<input type="text" name="chec6text" id="chec6text" value="${chec6text }" size="50" />      
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
              <input type=hidden name="approve" value="${approve }"/>
              <c:if test="${approve == '98'}" >
              <table width="99%" class="hairLineTable">
                <tr>
                  <td>
                  	不核可原因
                  </td>                  
				  <td align="center">
					<textarea name="approveTemp" rows="5" cols="81" readonly="ture">${approveTemp }</textarea>					
                  </td>
				</tr>
              </table>
              </c:if>
            </td>
          </tr>
          <tr>
            <td class="hairLineTd" align="center">
              <c:if test="${approve != '97'}" >
                <input type="submit" name="method" value="更新" Key="Update" class="CourseButton" onClick="return mySave()"/>
              </c:if>
              <!-- <input type="submit" name="method" value="刪除" Key="Delete" class="CourseButton"/>  -->
              <input type="submit" name="method" value="返回" Key="Back" class="CourseButton"/>
              <input type="submit" name="method" value="匯出" Key="PrintOut" class="CourseButton" 
                     onClick= "window.open('/CIS/pages/teacher/Rcproj_Out.jsp','承接政府部門計劃與產學案資料表', 'width=1000 scrollbars=yes,resizable=no,left=0,top=0');"/>
              <hr class="myHr"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </body>      
  </html:form>
</table>

<script>
  function myLoad(turnIn_v,turnOut_v,myKind)
  {
    var In = turnIn_v;
    var Out = turnOut_v;
    var Kind = myKind;
    
    if(In == '92'){
      document.getElementById("turnIns").style.display = 'inline';
    }else{
      document.getElementById("turnIns").style.display = 'none';
    }
    if(Out == '92'){
      document.getElementById("turnOuts").style.display = 'inline';
    }else{
      document.getElementById("turnOuts").style.display = 'none';
    }
    if(Kind == '83'){
      document.getElementById("turnNSC").style.display = 'inline';
    }else{
      document.getElementById("turnNSC").style.display = 'none';
    }
  }
  
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
      document.getElementById(id + "_G").value = '0';
      document.getElementById(id + "_B").value = '0';
      document.getElementById(id + "_O").value = '0';      
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