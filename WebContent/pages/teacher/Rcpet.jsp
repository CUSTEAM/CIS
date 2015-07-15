<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rcpet" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>專利/新品種資料表 &nbsp;</b></td>
          </tr>
        </table>
      </td>
    </tr>	
    <tr>
      <td>
        <table width="100%" cellpadding="0" cellspacing="0" onClick="showSpecs('Specs1_12')">
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
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr height="35" valign="middle" bgcolor="#E1D2A6" style="display:none" id="Specs1_12">
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
	            &nbsp;&nbsp;2) 所屬計畫案號：請輸入由哪一計畫案所得到的成果，若沒有研究案請填「無」。 <br>
	            &nbsp;&nbsp;3) 專利/新品種名稱：輸入專利/新品種的名稱。<br>
	            &nbsp;&nbsp;4) 區域：為本國專利或是外國專利，請選擇國內、大陸、美國、國外(不包含美國)。 <br>                
                &nbsp;&nbsp;5) 專利類型：請選擇發明專利、新型專利、新式樣專利、新品種。 <br>
                &nbsp;&nbsp;6) 報告分數：技術報告分數，新型專利有申請技術審查者，請填寫此欄位，分數為1~6；無申請者請選擇「無」。 <br>
                &nbsp;&nbsp;7) 進度狀況：專利目前的進度狀況，請選擇「申請中」、「已核准」。 <br>
                &nbsp;&nbsp;8) 作者順序：請點選專利發表之排名次序。<br>
                &nbsp;&nbsp;9) 申請人/權利人 ：申請中案件請填申請人、若案件已核准請填權利人。 <br>
	            &nbsp;&nbsp;10)申請人/權利人類型：請選擇「本校」、「企業」、「其他學校或機構」、「一般個人」<br>
	            &nbsp;&nbsp;11)生效日期：專利、技術移轉或授權生效的日期，例如<font color=red>「2005/01/01」</font>。 <br>
	            &nbsp;&nbsp;12)終止日期：專利的到期日期，例如<font color=red>「2005/01/01」</font>。<br>
	            &nbsp;&nbsp;13)發照機關：頒發證書的機關。 <br>                
                &nbsp;&nbsp;14)證書字號：專利的證書字號。 <br>
                &nbsp;&nbsp;15)技術移轉或授權：請選擇「技術移轉」、「授權」或「無」。 <br>
                &nbsp;&nbsp;<font color=red>※※※</font>若「技術轉移或授權」選擇「技術轉移」或是選擇「授權」，則須填寫以下欄位<font color=red>※※※</font> <br>
                &nbsp;&nbsp;16)轉移/授權廠商名稱：請輸入技術轉移或授權廠商名稱。<br>
                &nbsp;&nbsp;17)技術轉移/授權金額：請填報契約上所註明之技術轉移或授權的金額。<br>
                &nbsp;&nbsp;18)技術轉移/授權起始日：請輸入技術轉移或授權的起始日期，例如「2005/01/01」。<br>
                &nbsp;&nbsp;19)技術轉移/授權終止日：請輸入技術轉移或授權的終止日期，例如「2005/01/01」。
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
                    <input type="text" name="schoolYear" id="schoolYear" size="1"
                           onMouseOver="showHelpMessage('請輸入民國年,非學年度', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" value=""/>
                  </td>                   
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">所屬計畫案號</td>
                  <td class="hairLineTd">
                    <input type="text" name="projno" value=""/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">專利名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="title" value="" size="60"/>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">區域</td>
                  <td class="hairLineTd">
                    <select name=area>
					  <option value="">請選擇</option>
					  <c:forEach items="${area}" var="c">
					    <option <c:if test="${aEmpl.area==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">專利類型</td>
                  <td class="hairLineTd">
                    <select name=petType>
					  <option value="">請選擇</option>
					  <c:forEach items="${petType}" var="c">
					    <option <c:if test="${aEmpl.petType==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">報告分數</td>
                  <td class="hairLineTd">
                    <select name=score>					  
					  <c:forEach items="${score}" var="c">
					    <option value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">進度狀況</td>
                  <td class="hairLineTd">
                    <select name=schedule>
					  <option value="">請選擇</option>
					  <c:forEach items="${schedule}" var="c">
					    <option <c:if test="${aEmpl.schedule==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">作者順序</td>
                  <td class="hairLineTd">
                    <select name=authorno>
					  <option value="">請選擇</option>
					  <c:forEach items="${authorno}" var="c">
					    <option <c:if test="${aEmpl.authorno==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>                  
                </tr>                
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">申請人/權利人</td>
                  <td class="hairLineTd">
                    <input type="text" name="proposer" value="" size="60"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">申請人類型</td>
                  <td class="hairLineTd">
                    <select name=proposerType>					  
					  <c:forEach items="${proposerType}" var="c">
					    <option <c:if test="${76==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
				  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">申請日期(YYYY/MM/DD)</td>
                  <td class="hairLineTd">
                    <input type="text" name="bdate" id="bdate" value=""                           
                           onfocus="ds_sh(this), this.value='';" 
                           onclick="ds_sh(this), this.value='';" 
                           autocomplete="off" 
                           style="ime-mode:disabled" 
                           autocomplete="off" 
                           readonly="ture"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">終止日期(YYYY/MM/DD)</td>
                  <td class="hairLineTd">
                    <input type="text" name="edate" value="" 
                           onclick="ds_sh(this), this.value='';" 
                           onfocus="ds_sh(this), this.value='';"
                           autocomplete="off" 
                           style="ime-mode:disabled" 
                           autocomplete="off" 
                           readonly="ture"/>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">發照機關</td>
                  <td class="hairLineTd">
                    <input type="text" name="inst" value="" size="70"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">證書字號</td>
                  <td width="35%" class="hairLineTd">
                    <input type="text" name="certno" value=""/>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">技術轉移或授權</td>
                  <td  class="hairLineTd">
                    <select name="depute" onChange="showDepute()">
					  <option value="">請選擇</option>
					  <c:forEach items="${depute}" var="c">
					    <option <c:if test="${aEmpl.depute==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>                    
                  </td>
                </tr>
              </ttable>
              <table id="deputeInfo" style="display:none">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">轉移/授權廠商名稱</td>
                  <td class="hairLineTd" colspan="5">
                    <input type="text" name="deputeBusiness" value="" size="60"/>
                  </td>
                </tr>                
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">技術轉移/授權金額</td>
                  <td class="hairLineTd" >
                    <input type="text" name="deputeMoney" value=""/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">技術轉移/授權起始日</td>
                  <td class="hairLineTd" >
                    <input type="text" name="deputeSdate" id="deputeSdate"  value=""                           
                           onfocus="ds_sh(this), this.value='';" 
                           onclick="ds_sh(this), this.value='';" 
                           autocomplete="off" 
                           style="ime-mode:disabled" 
                           autocomplete="off" 
                           readonly="ture"/>
                  </td><td width="15%" align="center" class="hairLineTdF">技術轉移/授權終止日</td>
                  <td class="hairLineTd" >
                    <input type="text" name="deputeEdate" id="deputeEdate" value=""                           
                           onfocus="ds_sh(this), this.value='';" 
                           onclick="ds_sh(this), this.value='';" 
                           autocomplete="off" 
                           style="ime-mode:disabled" 
                           autocomplete="off" 
                           readonly="ture"/>
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
					<textarea name="intor" rows="10" cols="90"></textarea>
                  </td>
				</tr>              
              </table>
              <table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
		        <tr>
				  <td id="ds_calclass"></td>
			    </tr>
			  </table>
            </td>
          </tr>
          <tr>
            <td class="hairLineTd" align="center">
              <input type="submit" name="method" value="<bean:message key='Create'/>" onClick="return mySave()" class="CourseButton"/>
              <input type="submit" name="method" value="查詢" Key="Query" class="CourseButton"/>
              <hr class="myHr"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <c:if test="${RcpetList != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${RcpetList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty RcpetList}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(RcpetList)}, 'rcpet');</script>" class="center" >
	                <script>generateCheckbox("${row.oid}", "rcpet");</script></display:column>
 	              <display:column title="年度"		property="school_year"	sortable="true" class="center" />
	              <display:column title="專利名稱"	property="title"		sortable="true" class="center" />	              
	              <display:column title="發照機關" 	property="inst"		sortable="true" class="center" />
	              <display:column title="審核狀態" 	property="name"		sortable="true" class="center" />   
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
  
  function showDepute() 
  {
    
    if(document.getElementById("depute").value == '42' || document.getElementById("depute").value == '44') 
    {
      document.getElementById("deputeInfo").style.display = 'inline';      
    } 
    else
    {
      document.getElementById("deputeInfo").style.display = 'none'; 
      document.getElementById("deputeBusiness").value = '';
      document.getElementById("deputeMoney").value = '';
      document.getElementById("deputeSdate").value = '';
      document.getElementById("deputeEdate").value = '';				
    }    
  }
  
  function myTest() 
  {
    var iCount;
    iCount = getCookie("rcpetCount");    
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
    Oid = getCookie("rcpet");
    //alert(Oid);
    return true;
  }
  
  function mySave() 
  {        
    
    if (document.getElementById("schoolYear").value == '') 
    {
      alert("年度,此欄位不得為空白");
      return false;
    }
    else if (document.getElementById("projno").value == '') 
    {
      alert("計畫案號,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("title").value == '') 
    {
      alert("專利名稱,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("authorno").value == '') 
    {
      alert("作者順序,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("area").value == '') 
    {
      alert("專利區域,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("petType").value == '') 
    {
      alert("專利類型,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("schedule").value == '') 
    {
      alert("進度狀況,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("bdate").value == '') 
    {
      alert("開始日期,欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("edate").value == '') 
    {
      alert("結束日期,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("inst").value == '') 
    {
      alert("發照機關,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("certno").value == '') 
    {
      alert("證書字號,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("depute").value == '') 
    {
      alert("授權轉移,此欄位不得為空白");
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