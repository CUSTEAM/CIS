package tw.edu.chit.struts.action.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.DtimeExam;
import tw.edu.chit.model.DtimeTeacher;
import tw.edu.chit.model.Opencs;


public class OpenCourseActionServices {
	
	/**
	 * 尋找所有遭到變更的欄位
	 * @return a, b, c, d, e,
	 */
	public Map checkAllEq(String departClass,
						  String cscode,
						  String techid,
						  String term,
						  String opt,
						  String thour,
						  String credit,
						  String selectLimit,
						  String examDate[],
						  String Ebegin[],
						  String Etech[],
						  String Eplace[],
						  String week[],
						  String begin[],
						  String end[],
						  String place[],
						  String cidno[],
						  String sidno[],
						  String didno[],
						  String grade[],
						  String departClassM[],
						  String techidM[],
						  String hoursM[],
						  String techM[],
						  String fillscoreM[],
						  String open,
						  String elearning,
						  String extrapay,
						  
						  String SdepartClass,
						  String Scscode,
						  String Stechid,
						  String Sterm,
						  String Sopt,
						  String Sthour,
						  String Scredit,
						  String SselectLimit,
						  List dtimeExamList,
						  List DtimeClasses,
						  List opencsList,
						  List dtimeTeachers,
						  String Sopen,
						  String Selearning,
						  String Sextrapay
						  
						  ) {

		HashMap map = new HashMap();
		map.put("a", "true");
		map.put("b", "true");
		map.put("c", "true");
		map.put("d", "true");
		map.put("e", "true");
		StringBuffer aVO;
		StringBuffer aPO;
		StringBuffer bVO=new StringBuffer();
		StringBuffer bPO=new StringBuffer();
		StringBuffer cVO = new StringBuffer();
		StringBuffer cPO = new StringBuffer();
		StringBuffer dVO = new StringBuffer();
		StringBuffer dPO = new StringBuffer();
		StringBuffer eVO=new StringBuffer();
		StringBuffer ePO=new StringBuffer();
		//boolean checkTimeArrayEq = false;
		//boolean reOpenCourse = false;
				DtimeClass dtimeClass;
		int Btmp = 0;
		int Ctmp = 0;
		int Dtmp = 0;
		int Etmp = 0;
		
		// 尋找頁面上的元素陣列長度
		for (int i = 0; i < week.length; i++) {
			if (!week[i].trim().equals("") && !begin[i].trim().equals("") && !end[i].trim().equals("")
					) {//&& place[i] != ""
				Btmp = Btmp + 1;
			}
		}
		for(int i=0; i<techidM.length; i++){
			if(!techidM[i].trim().equals("") ){
				Ctmp = Ctmp+1;
			}
			
		}
		for(int i=0; i<examDate.length; i++){
			if(!examDate[i].trim().equals("")|| 
			   !Ebegin[i].trim().equals("")|| 
			   !Etech[i].trim().equals("")|| 
			   !Eplace[i].trim().equals("")){
				Dtmp = Dtmp+1;
			}
		}
		//System.out.println("Dtmp="+Dtmp);
		//System.out.println("dtimeExamList.size="+dtimeExamList.size());
		for(int i=0; i<cidno.length; i++){
			if(!cidno[i].trim().equals("")|| 
					!sidno[i].trim().equals("")|| 
					!didno[i].trim().equals("")|| 
					!grade[i].trim().equals("")|| 
					!departClassM[i].trim().equals("")){
				Etmp = Etmp+1;
			}
		}
		
		/**
		 * 檢查基本資料是否修改
		 */
		aVO = new StringBuffer(departClass+"|"+cscode+"|"+techid+"|"+term+"|"+credit+"|"+thour+"|"+opt+"|"+selectLimit+"|"+open+"|"+elearning+"|"+extrapay);
		aPO = new StringBuffer(SdepartClass+"|"+Scscode+"|"+Stechid+"|"+Sterm+"|"+Scredit+"|"+Sthour+"|"+Sopt+"|"+SselectLimit+"|"+Sopen+"|"+Selearning+"|"+Sextrapay);
		if (aPO.toString().equals(aVO.toString())) {
			map.put("a", "true");
		}else{
			map.put("a", "false");
		}
		
		/**
		 * 檢查時段是否修改
		 */
		if (Btmp != DtimeClasses.size()) {
			map.put("b", "false");
		} else {
			//若Bvo長度符合則豬一檢查每個欄位
			for (int i = 0; i < DtimeClasses.size(); i++) {
				dtimeClass = (DtimeClass) DtimeClasses.get(i);
				if(dtimeClass.getPlace()==null){
					dtimeClass.setPlace("");
				}				
				bVO.append(week[i]+"|"+begin[i]+"|"+end[i]+"|"+place[i]);
				bPO.append(dtimeClass.getWeek().toString()+"|"+
						                            dtimeClass.getBegin().toString()+"|"+
						                            dtimeClass.getEnd().toString()+"|"+
						                            dtimeClass.getPlace());
				}
			if (bPO.toString().equals(bVO.toString())) {
				map.put("b", "true");
			}else{
				map.put("b", "false");
			}
			
			
			}
			
			/**
			 * 檢查一科目多教師是否修改
			 */
			
			
			//長度不同
			if(Ctmp!=dtimeTeachers.size()){
				map.put("c", "false");
			}else{
			//長度相同
				DtimeTeacher dtimeTeacher;
				
				for(int i=0; i<dtimeTeachers.size(); i++){
					dtimeTeacher=(DtimeTeacher)dtimeTeachers.get(i);
					if(techM[i].trim().equals("")){
						techM[i]="0";
					}
					if(fillscoreM[i].trim().equals("")){
						fillscoreM[i]="0";
					}else
					cVO.append(techidM[i]+"|"+hoursM[i]+"|"+techM[i]+"|"+fillscoreM[i]);
					cPO.append(dtimeTeacher.getTeachId()+"|"+dtimeTeacher.getHours()+"|"+
							dtimeTeacher.getTeach()+"|"+dtimeTeacher.getFillscore());
				}
				if(cPO.toString().trim().equals(cVO.toString().trim())){
					map.put("c", "true");
				}else{
					map.put("c", "false");
				}
			}
			
			/**
			 * 檢查考試資料是否修改
			 */
			//System.out.println(Dtmp+"....."+dtimeExamList.size());
			if(Dtmp!=dtimeExamList.size()){
				map.put("d", "false");
			}else{
				
				String dtimeExam2[] = new String[dtimeExamList.size()];
				DtimeExam dtimeExam;// = (DtimeExam) dtimeExamList;
				for(int i=0; i<dtimeExamList.size(); i++){
					dtimeExam=(DtimeExam) dtimeExamList.get(i);
					//System.out.println("asdfaf"+dtimeExamList.size());
					if(dtimeExamList.size()>0){
						
						if(dtimeExam.getExamDate().equals(null)){
							dtimeExam2[i]="";
						}else{
							dtimeExam2[i]=dtimeExam.getExamDate2().toString();
						}
						if(dtimeExam.getEbegin().equals(null)){
							dtimeExam.setEbegin("");
						}
						if(dtimeExam.getExamEmpl().equals(null)){
							dtimeExam.setExamEmpl("");
						}
						if(dtimeExam.getPlace().equals(null)){
							dtimeExam.setPlace("");
						}
						
					}
					
					dtimeExam=(DtimeExam) dtimeExamList.get(i);
					dVO.append(examDate[i]+"|"+Ebegin[i]+"|"+Etech[i]+"|"+Eplace[i]+"|");
					dPO.append(dtimeExam2[i]+"|"+dtimeExam.getEbegin()+"|"+
							   dtimeExam.getExamEmpl()+"|"+dtimeExam.getPlace()+"|");
				}
				
				
				if (dPO.toString().equals(dVO.toString())) {
					map.put("d", "true");
				}else{
					map.put("d", "false");
				}
			}
			
			
			
			
			//dVO = new StringBuffer(examDate+"|"+Ebegin+"|"+Etech+"|"+Eplace);
			//dPO = new StringBuffer(SexamDate+"|"+SEbegin+"|"+SEtech+"|"+SEplace);
			
			
			/**
			 * 檢查選課規則
			 */
			if(Etmp!=opencsList.size()){
				map.put("e", "false");
			}else{
				Opencs opencs;
				for(int i=0; i<opencsList.size(); i++){
					opencs=(Opencs)opencsList.get(i);
					eVO.append(cidno[i]+"|"+sidno[i]+"|"+didno[i]+"|"+grade[i]+"|"+departClassM[i]);
					ePO.append(opencs.getCidno()+"|"+opencs.getSidno()+"|"+opencs.getDidno()+"|"+
							opencs.getGrade()+"|"+opencs.getClassNo());
				}
				if (ePO.toString().equals(eVO.toString())) {
					map.put("e", "true");
				}else{
					map.put("e", "false");
				}
			}
			/*
			System.out.println("Btmp="+Btmp);
			System.out.println("Ctmp="+Ctmp);
			System.out.println("Etmp="+Etmp);
			System.out.println("aVO="+aVO);
			System.out.println("aPO="+aPO);
			System.out.println("bVO="+bVO);
			System.out.println("bPO="+bPO);
			System.out.println("cVO="+cVO);
			System.out.println("cPO="+cPO);
			System.out.println("dVO="+dVO);
			System.out.println("dPO="+dPO);
			System.out.println("eVO="+eVO);
			System.out.println("ePO="+ePO);
			System.out.println("a="+map.get("a").toString());
			System.out.println("b="+map.get("b").toString());
			System.out.println("c="+map.get("c").toString());
			System.out.println("d="+map.get("d").toString());
			System.out.println("e="+map.get("e").toString());
			*/
		
		return map;
	}


	/**
	 * 檢查長度矛盾
	 */
	public boolean checkThours(int thour, String[]begin, String[]end){
		
		int tmp=0;
		int thours=0;
		
		//抓有值的元素陣列長度
		for(int i=0; i<begin.length; i++){
			if(begin[i]!=""){
				tmp=tmp+1;
			}
		}
		for(int i=0; i<tmp; i++){
			
			if(begin[i]==""|| end[i]==""){
				return true;
			}
			
			thours=thours+((Integer.parseInt(end[i])-Integer.parseInt(begin[i]))+1);
		}
		//System.out.println("thour="+thour+" thours="+thours);
		if(thours>thour){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 檢查時間矛盾
	 * @param begin
	 * @param end
	 * @return
	 */
	public boolean checkTime(String[]begin, String[]end){
		int tmp=0;
		//抓有值的元素陣列長度
		for(int i=0; i<begin.length; i++){
			if(begin[i]!=""&& end[i]!=""){
				tmp=tmp+1;
			}
		}
		for(int i=0; i<tmp; i++){
			if(Integer.parseInt(begin[i])>Integer.parseInt(end[i])){
				return true;
			}
		}
		
		return false;
	}
}