<%@ page language="java" import="java.util.*" pageEncoding="BIG5"%>
<%@ include file="/taglibs.jsp"%>


<table class="hairLineTable" width="99%" id="reChoseList">
	<tr>
		<td class="hairLineTdF" nowrap width="30" align="center"><img src="images/16-cube-bug.png"></td>
		<td class="hairLineTd" nowrap>學年</td>
		<td class="hairLineTd" nowrap>學期</td>
		<td class="hairLineTd" nowrap>學號</td>
		<td class="hairLineTd" nowrap>姓名</td>
		<td class="hairLineTd" nowrap>選別</td>
		<td class="hairLineTd" nowrap>代碼</td>
		<td class="hairLineTd" width="100%">課程名稱</td>				
		<td class="hairLineTd" nowrap>成績</td>		
	</tr>
<c:forEach items="${SelectException}" var="r">
	

	<tr>
		<td class="hairLineTd" width="30" align="center" nowrap><img src="images/16-cube-bug.png"></td>
		<td class="hairLineTdF" nowrap>${r.school_year}</td>
		<td class="hairLineTdF" nowrap>${r.school_term}</td>
		<td class="hairLineTdF" nowrap>${r.student_no}</td>
		<td class="hairLineTdF" nowrap>${r.student_name}</td>
		<td class="hairLineTdF" nowrap>${r.name}</td>
		<td class="hairLineTdF" nowrap>${r.cscode}</td>
		<td class="hairLineTdF" nowrap>${r.chi_name}</td>				
		<td class="hairLineTdF" nowrap>${r.score}</td>		
	</tr>
</c:forEach>
</table>























<script>
function MakeExcel(id){
var i,j;
    try {
      var xls=new ActiveXObject ( "Excel.Application" );
     }
    catch(e) {
         alert( "請允許瀏覽器(IE)用 ActiveX 控件(別管那是什麼), 而且必須安裝 M$ Excel 電子表格軟體\n L(-_-;");
         return "";
     }

    xls.visible =true;  //設置excel為可見

    var xlBook = xls.Workbooks.Add;
    var xlsheet = xlBook.Worksheets(1);
    
    //xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,8)).mergecells=true;
    //xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,8)).value="這是什麼？";
    //  xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,6)).Interior.ColorIndex=5;//設置底色為藍色 
    //   xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,6)).Font.ColorIndex=4;//設置字體色         
   	// xlsheet.Rows(1). Interior .ColorIndex = 5 ;//設置底色為藍色  設置背景色 Rows(1).Font.ColorIndex=4  

    
    xlsheet.Rows(1).RowHeight = 25;    
    xlsheet.Rows(1).Font.Size=14;    
    xlsheet.Rows(1).Font.Name="新細明體";    

    xlsheet.Columns("A:D").ColumnWidth =18;
     
    xlsheet.Columns(2).NumberFormatLocal="@";
    xlsheet.Columns(7).NumberFormatLocal="@";


    //設置單元格內容自動換行 range.WrapText  =  true  ;
    //設置單元格內容水準對齊方式 range.HorizontalAlignment  =  Excel.XlHAlign.xlHAlignCenter;//設置單元格內容豎直堆砌方式
    //range.VerticalAlignment=Excel.XlVAlign.xlVAlignCenter
    //range.WrapText  =  true;  xlsheet.Rows(3).WrapText=true  自動換行
  
    //設置標題欄    
    xlsheet.Cells(2,1).Value="學年";
    xlsheet.Cells(2,2).Value="學期";
    xlsheet.Cells(2,3).Value="學號";
    xlsheet.Cells(2,4).Value="姓名";
    xlsheet.Cells(2,5).Value="選別";
    xlsheet.Cells(2,6).Value="代碼";
    xlsheet.Cells(2,7).Value="課程名稱";
    xlsheet.Cells(2,8).Value="成績";

    var oTable=document.all[id];
    var rowNum=oTable.rows.length;
     
    for(i=1;i<=rowNum;i++){
     	for (j=1;j<=8;j++){
     		xlsheet.Cells(i,j).Value=oTable.rows(i-1).cells(j).innerHTML;
    	}
    }
    
    <!--   xlsheet.Range(xls.Cells(i+4,2),xls.Cells(rowNum,4)).Merge; -->
    // xlsheet.Range(xlsheet.Cells(i, 4), xlsheet.Cells(i-1, 6)).BorderAround , 4
    // for(mn=1,mn<=6;mn++) .     xlsheet.Range(xlsheet.Cells(1, mn), xlsheet.Cells(i1, j)).Columns.AutoFit;
    xlsheet.Columns.AutoFit;
    xlsheet.Range( xlsheet.Cells(1,1),xlsheet.Cells(rowNum+1,8)).HorizontalAlignment =-4108;//置中
    xlsheet.Range( xlsheet.Cells(1,1),xlsheet.Cells(1,8)).VerticalAlignment =-4108;
    xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+1,8)).Font.Size=10;

    xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum,8)).Borders(3).Weight = 2; //左間距
    xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum,8)).Borders(4).Weight = 2;//右間距
    xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum,8)).Borders(1).Weight = 2;//頂部間距
    xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum,8)).Borders(2).Weight = 2;//底部間距

 

       
    xls.UserControl = true;  //excel交由用戶控制
    xls=null;
    xlBook=null;
    xlsheet=null;

}

 


</script>