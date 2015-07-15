<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rcact" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>教師/職員參加學術活動資料表&nbsp;</b></td>
          </tr>
        </table>
      </td>
    </tr>	
    <tr>
      <td>
        <table width="100%" cellpadding="0" cellspacing="0" onClick="showSpecs('Specs1_7')">
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
          <tr height="35" valign="middle" bgcolor="#E1D2A6" style="display:none" id="Specs1_7">
            <td>
              <div class="modulecontainer filled nomessages">
	        <div class="first">
	          <span class="first"></span>
	          <span class="last"></span>
	        </div>
	        <div>
	          <div> 
	            1. 本程式仿雲科大校務基本資料庫格式與介面，所有欄位不能空白，如果沒有資料請填<font color=red>「無」</font>。 <br>
	            2. 如果要新增，請依照畫面欄位填入相關資料，填寫完畢按下新增即可。 <br>
	            3. 如果要修改記錄，可以直接按下查詢或輸入年度後按下查詢，即可顯示已輸入資料清單，勾選要作業的資料按下檢視即可執行。  <br>
	                                欄位說明如下:<br>
	            &nbsp;&nbsp;1) 年度：請輸入民國年<font color=red>(非學年度)</font>，如98年度，即是98年1月1日至98年12月31日。<br>
	            &nbsp;&nbsp;2) 活動名稱/進修課程：該學術活動或進修課程的名稱，例如「智慧財產權管理人員培訓」。 <br>
	            &nbsp;&nbsp;3) 主辦單位/進修機構：該學術活動或進修機構的單位，例如「國立台灣大學」。<br>
	            &nbsp;&nbsp;4) 活動種類：該學術活動是何種性質，請選擇進修研習、學術研討會、作品發表會(含展覽)。 <br>                
                &nbsp;&nbsp;5) 活動型態：該學術活動是否公開徵稿，請選擇公開徵稿、其他形式。 <br>
                &nbsp;&nbsp;6) 活動地點：該學術活動是舉行的地點是在那個地區，請選擇國內、國際、兩岸。 <br>
                &nbsp;&nbsp;7) 參與情形：教師為主辦活動的主持人或為該活動的參加者，請選擇主辦、參加。 <br>
                &nbsp;&nbsp;8) 開始日期：活動的開始時間，例如<font color=red>「2005/01/01」</font>。 <br>
                &nbsp;&nbsp;9) 結束日期/完成日期：活動或課程的結束時間，例如<font color=red>「2005/01/01」</font>。 <br>
	            &nbsp;&nbsp;10)時數：學術活動的總時數，請填數字。  <br>
	            &nbsp;&nbsp;11)研習證明：是否具有主辦單位官防的研習證書。(不可空白) <br>
	            &nbsp;&nbsp;12)證書字號：若研習證明有證書字號。 <br>
	            &nbsp;&nbsp;13)學校補助情形：請填寫學校所提供的補助項目，例如「補助報名費1000元」或「無」或「0」。
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
                  <td width="13%" align="center" class="hairLineTdF">民國年度</td>
                  <td class="hairLineTd">
                    <input type="text" name="schoolYear" id="schoolYear" size="1" value=""
                           onMouseOver="showHelpMessage('請輸入民國年,非學年度', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>                   
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>            
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" class="hairLineTdF" align="center">活動名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="actname" id="actname" onMouseOver="showHelpMessage('進修課程', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" value="" size="45"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">主辦單位(進修機構)</td>
                  <td class="hairLineTd">
                    <input type="text" name="sponoff" value="" size="45"/>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">活動種類</td>
                  <td class="hairLineTd">
                    <select name=kindid>
					  <option value="">請選擇</option>
					  <c:forEach items="${kindid}" var="c">
					    <option <c:if test="${aEmpl.kindid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">活動類型</td>
                  <td class="hairLineTd">
                    <select name=typeid>
					  <option value="">請選擇</option>
					  <c:forEach items="${typeid}" var="c">
					    <option <c:if test="${aEmpl.typeid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">活動地點</td>
                  <td class="hairLineTd">
                    <select name=placeid>
					  <option value="">請選擇</option>
					  <c:forEach items="${placeid}" var="c">
					    <option <c:if test="${aEmpl.placeid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">參與情形</td>
                  <td class="hairLineTd">
                    <select name=joinid>
					  <option value="">請選擇</option>
					  <c:forEach items="${joinid}" var="c">
					    <option <c:if test="${aEmpl.joinid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">開始日期</td>
                  <td class="hairLineTd">
                    <input type="text" name="bdate" id="bdate" value="" onclick="ds_sh(this), this.value='';" onfocus="ds_sh(this), this.value='';" 
                           autocomplete="off" style="ime-mode:disabled" autocomplete="off" readonly="ture" />
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">結束日期</td>
                  <td class="hairLineTd">
                    <input type="text" name="edate" id="edate" value="" onclick="ds_sh(this), this.value='';" onfocus="ds_sh(this), this.value='';" 
                           autocomplete="off" style="ime-mode:disabled" autocomplete="off" readonly="ture" />
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">時數</td>
                  <td class="hairLineTd">
                    <input type="text" name="hour" id="hour" onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" value=""/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">學校補助情形</td>
                  <td class="hairLineTd">
                    <input type="text" name="schspon" value="" size="45"/>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">是否有研習證明</td>
                  <td class="hairLineTd">
                    <select name=certyn>
                      <option value="">請選擇</option>
                      <option value="Y">有</option>
                      <option value="N">無</option>
                    </select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">證書字號</td>
                  <td class="hairLineTd">
                    <input type="text" name="certno" value=""/>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td>
                  	研習心得<font color=red>輸入字數不得低於350字</font>
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
    <c:if test="${RcactList != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${RcactList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty RcactList}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(RcactList)}, 'rcact');</script>" class="center" >
	                <script>generateCheckbox("${row.oid}", "rcact");</script></display:column>
 	              <display:column title="年度"		property="school_year"	sortable="true" class="center" />
	              <display:column title="活動名稱"	property="actname"		sortable="true" class="center" />	                 
	              <display:column title="開始日期" 	property="bdate"		sortable="true" class="center" />
	              <display:column title="結束日期" 	property="edate"		sortable="true" class="center" />
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
  
  function myTest() 
  {
    var iCount;
    iCount = getCookie("rcactCount");    
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
    Oid = getCookie("rcact");
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
    else if (document.getElementById("actname").value == '') 
    {
      alert("活動名稱,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("sponoff").value == '') 
    {
      alert("主辦單位,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("kindid").value == '') 
    {
      alert("活動種類,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("typeid").value == '') 
    {
      alert("活動型態,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("placeid").value == '') 
    {
      alert("活動地點,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("joinid").value == '') 
    {
      alert("參與情形,此欄位不得為空白");
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
    else if (document.getElementById("hour").value == '') 
    {
      alert("時數,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("certyn").value == '') 
    {
      alert("研習證明,此欄位不得為空白");
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