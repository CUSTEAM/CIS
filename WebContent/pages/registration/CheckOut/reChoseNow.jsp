<%@ page language="java" import="java.util.*" pageEncoding="BIG5"%>
<%@ include file="/taglibs.jsp"%>


<table class="hairLineTable" width="99%" id="reChoseList">
	<tr>
		<td class="hairLineTdF" nowrap width="30" align="center"><img src="images/16-cube-bug.png"></td>
		<td class="hairLineTd" nowrap>�Ǧ~</td>
		<td class="hairLineTd" nowrap>�Ǵ�</td>
		<td class="hairLineTd" nowrap>�Ǹ�</td>
		<td class="hairLineTd" nowrap>�m�W</td>
		<td class="hairLineTd" nowrap>��O</td>
		<td class="hairLineTd" nowrap>�N�X</td>
		<td class="hairLineTd" width="100%">�ҵ{�W��</td>				
		<td class="hairLineTd" nowrap>���Z</td>		
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
         alert( "�Ф��\�s����(IE)�� ActiveX ����(�O�ި��O����), �ӥB�����w�� M$ Excel �q�l���n��\n L(-_-;");
         return "";
     }

    xls.visible =true;  //�]�mexcel���i��

    var xlBook = xls.Workbooks.Add;
    var xlsheet = xlBook.Worksheets(1);
    
    //xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,8)).mergecells=true;
    //xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,8)).value="�o�O����H";
    //  xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,6)).Interior.ColorIndex=5;//�]�m���⬰�Ŧ� 
    //   xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,6)).Font.ColorIndex=4;//�]�m�r���         
   	// xlsheet.Rows(1). Interior .ColorIndex = 5 ;//�]�m���⬰�Ŧ�  �]�m�I���� Rows(1).Font.ColorIndex=4  

    
    xlsheet.Rows(1).RowHeight = 25;    
    xlsheet.Rows(1).Font.Size=14;    
    xlsheet.Rows(1).Font.Name="�s�ө���";    

    xlsheet.Columns("A:D").ColumnWidth =18;
     
    xlsheet.Columns(2).NumberFormatLocal="@";
    xlsheet.Columns(7).NumberFormatLocal="@";


    //�]�m�椸�椺�e�۰ʴ��� range.WrapText  =  true  ;
    //�]�m�椸�椺�e���ǹ���覡 range.HorizontalAlignment  =  Excel.XlHAlign.xlHAlignCenter;//�]�m�椸�椺�e�ݪ����覡
    //range.VerticalAlignment=Excel.XlVAlign.xlVAlignCenter
    //range.WrapText  =  true;  xlsheet.Rows(3).WrapText=true  �۰ʴ���
  
    //�]�m���D��    
    xlsheet.Cells(2,1).Value="�Ǧ~";
    xlsheet.Cells(2,2).Value="�Ǵ�";
    xlsheet.Cells(2,3).Value="�Ǹ�";
    xlsheet.Cells(2,4).Value="�m�W";
    xlsheet.Cells(2,5).Value="��O";
    xlsheet.Cells(2,6).Value="�N�X";
    xlsheet.Cells(2,7).Value="�ҵ{�W��";
    xlsheet.Cells(2,8).Value="���Z";

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
    xlsheet.Range( xlsheet.Cells(1,1),xlsheet.Cells(rowNum+1,8)).HorizontalAlignment =-4108;//�m��
    xlsheet.Range( xlsheet.Cells(1,1),xlsheet.Cells(1,8)).VerticalAlignment =-4108;
    xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+1,8)).Font.Size=10;

    xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum,8)).Borders(3).Weight = 2; //�����Z
    xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum,8)).Borders(4).Weight = 2;//�k���Z
    xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum,8)).Borders(1).Weight = 2;//�������Z
    xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum,8)).Borders(2).Weight = 2;//�������Z

 

       
    xls.UserControl = true;  //excel��ѥΤᱱ��
    xls=null;
    xlBook=null;
    xlsheet=null;

}

 


</script>