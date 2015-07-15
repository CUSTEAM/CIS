package tw.edu.chit.struts.action.deptassist;

import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Member;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseAction;

public class CheckCsGroupAction extends BaseAction{
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
		HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		
		Member member = (Member) getUserCredential(session).getMember();
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		Empl empl = mm.findEmplByOid(member.getOid());		
		
		String depart_class=manager.ezGetString("SELECT idno FROM code5 WHERE Oid='"+empl.getUnit()+"'"); //班級
		System.out.println(depart_class);
		
		int schoolYear=manager.getSchoolYear();//現在學年	
		
		List students=manager.ezGetBy("SELECT st.*, c.*, c5.name as schoolName, c51.name as deptName FROM " +
					"((stmd st LEFT JOIN Class c ON st.depart_class=c.ClassNo)LEFT JOIN " +
					"code5 c5 ON c5.idno=c.SchoolNo)LEFT JOIN code5 c51 ON c51.idno=c.DeptNo WHERE " +
					"c5.category='School' AND c51.category='Dept' AND " +
					"c.DeptNo='"+depart_class+"'");
		
		int pa=60;
		
		
		Map aStudent;//定義1個學生
		List biGroup;//定義學程
		List myScore;//定義成績
		//及格標-
		
		session.setAttribute("pa", pa);//及格標？
		int sum=0;
		List list=new ArrayList();
		Map map;
		int tmp;
		//學生大圈
		for(int i=0; i<students.size(); i++){
			
			if( Integer.parseInt( ((Map)students.get(i)).get("Grade").toString())>1 ){
				//System.out.println("一年級以上");
				//TODO 若研究所有開學程再用這個
				//int pa=manager.getPassScore(((Map)students.get(i)).get("depart_class").toString(), ((Map)students.get(0)).get("student_no").toString());
				
				aStudent=((Map)students.get(i));
				tmp=0;
				map=new HashMap();
				map.put("student_no", aStudent.get("student_no"));
				map.put("student_name", aStudent.get("student_name"));
				
				try{//TODO 實際年度的正確度待驗證
					StringBuilder sb=new StringBuilder(aStudent.get("entrance").toString());
					aStudent.put("inYear", sb.delete(sb.length()-2, sb.length()).toString());
				}catch(Exception e){//取得錯誤立即給予最近4年
					aStudent.put("inYear", schoolYear-4);
				}
				map.put("inYear", aStudent.get("inYear"));
					
				//TODO 效能評估
				biGroup=new ArrayList();
				try{
					biGroup.addAll(manager.ezGetBy("SELECT cg.*, cgr.* FROM CsGroup cg, CsGroupRule cgr WHERE " +
							"cg.Oid=cgr.group_oid AND cgr.schoolNo='"+aStudent.get("SchoolNo")+"' AND " +
							"(cg.entrance>"+aStudent.get("inYear")+" OR cg.entrance='' OR cg.entrance is null)"));//大圈
				}catch(Exception e){
					msg.add(ActionErrors.GLOBAL_MESSAGE, 
					new ActionMessage("Course.messageN1",aStudent.get("student_no")+""+aStudent.get("student_name")+"學籍資料有誤無法自動比對, "));
				}
				
				//學生成績
				myScore=manager.ezGetBy("SELECT sh.score, sh.credit, sh.cscode, sh.opt, c.DeptNo " +
						"FROM ScoreHist sh, Class c WHERE c.ClassNo=sh.stdepart_class AND " +
						"sh.cscode !='99999' AND sh.student_no='"+aStudent.get("student_no")+"' AND sh.score>="+pa);				
				List smallGroupMajor;//必修小圈
				List smallGroupMinor;//選修小圈
				
				for(int x=0; x<biGroup.size(); x++){//跑大圈
					//跑大圈
					float opt1=0; //必修已得
					float opt2=0; //選修已得
					float outOpt=0; //外系已得
					//取專業必修
					smallGroupMajor=manager.ezGetBy("SELECT cgs.*, c.chi_name, c5.name as deptName FROM " +
							"CsGroupSet cgs, Csno c, code5 c5 WHERE " +
							"cgs.cscode=c.cscode AND cgs.opt='1' AND " +
							"c5.category='Dept' AND c5.idno=cgs.deptNo AND " +
							"cgs.group_oid='"+((Map)biGroup.get(x)).get("Oid")+"'");					
					for(int j=0; j<smallGroupMajor.size(); j++){
						
						//((Map)smallGroupMajor.get(j)).put("igot", false);//預設取得為 "假"	
						float smallGroupMajorCredit=Float.parseFloat(((Map)smallGroupMajor.get(j)).get("credit").toString());//預先轉學程學分
						//多重抵免因此不移除得到的
						for(int k=0; k<myScore.size(); k++){
							float myScoreCredit=Float.parseFloat(((Map)myScore.get(k)).get("credit").toString());//預先轉成績學分
							if(							
									((Map)myScore.get(k)).get("cscode").equals(((Map)smallGroupMajor.get(j)).get("cscode"))&&//代碼相同
									((Map)myScore.get(k)).get("DeptNo").equals(((Map)smallGroupMajor.get(j)).get("deptNo"))&&//系所相同
									myScoreCredit>=smallGroupMajorCredit//學分數大於等於
							){						
								tmp=tmp+1;
								sum=sum+1;
								opt1=opt1+smallGroupMajorCredit;//專業必修++
								//((Map)smallGroupMajor.get(j)).put("igot", true);//設為"真"
								//計算外系課程
								if(!aStudent.get("DeptNo").equals(((Map)smallGroupMajor.get(j)).get("deptNo"))){//外系課程
									outOpt=outOpt+smallGroupMajorCredit;//外系課程++
								}
							}
						}
					}
					//((Map)biGroup.get(x)).put("smallGroupMajor", smallGroupMajor);					
					//取專業選修
					smallGroupMinor=manager.ezGetBy("SELECT cgs.*, c.chi_name, c5.name as deptName FROM " +
							"CsGroupSet cgs, Csno c, code5 c5 WHERE " +
							"cgs.cscode=c.cscode AND cgs.opt='2' AND " +
							"c5.category='Dept' AND c5.idno=cgs.deptNo AND " +
							"cgs.group_oid='"+((Map)biGroup.get(x)).get("Oid")+"'");
					
					for(int j=0; j<smallGroupMinor.size(); j++){
						//((Map)smallGroupMinor.get(j)).put("igot", false);//預設取得為 "假"	
						
						//System.out.println(((Map)smallGroupMinor.get(j)+"|"+((Map)smallGroupMinor.get(j))));				
						
						float smallGroupMinorCredit=Float.parseFloat(((Map)smallGroupMinor.get(j)).get("credit").toString());//預先轉學程學分
						//多重抵免因此不移除得到的
						for(int k=0; k<myScore.size(); k++){
							float myScoreCredit=Float.parseFloat(((Map)myScore.get(k)).get("credit").toString());//預先轉成績學分
							if(							
									((Map)myScore.get(k)).get("cscode").equals(((Map)smallGroupMinor.get(j)).get("cscode"))&&//代碼相同
									((Map)myScore.get(k)).get("DeptNo").equals(((Map)smallGroupMinor.get(j)).get("deptNo"))&&//系所相同
									myScoreCredit>=smallGroupMinorCredit//學分數大於等於
							){	
								tmp=tmp+1;
								sum=sum+1;
								opt2=opt2+smallGroupMinorCredit;//專業必修++
								
								//計算外系課程
								if(!aStudent.get("DeptNo").equals(((Map)smallGroupMinor.get(j)).get("deptNo"))){//外系課程
									outOpt=outOpt+smallGroupMinorCredit;//外系課程++
								}
							}
						}
					}			
					//((Map)biGroup.get(x)).put("smallGroupMinor", smallGroupMinor);				
					((Map)biGroup.get(x)).put("opt1", opt1);
					((Map)biGroup.get(x)).put("opt2", opt2);
					((Map)biGroup.get(x)).put("optOut", outOpt);					
				}
				
				map.put("count", tmp);
				map.put("deptName", aStudent.get("deptName"));
				map.put("schoolName", aStudent.get("schoolName"));
				map.put("biGroup", biGroup);
				list.add(map);				
			}			
		}
		
		//System.out.println(list.size());
		
		session.setAttribute("relult", list);
		
		if(sum>0){
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查詢完成, 系統總共自動完成"+sum+"次抵免"));
		}else{
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查詢完成, 系統沒有發現學生可以取得學程"));
		}
		
		saveMessages(request, msg);
		setContentPage(request.getSession(false), "assistant/CheckCsGroup.jsp");
		return mapping.findForward("Main");

	}
	
}