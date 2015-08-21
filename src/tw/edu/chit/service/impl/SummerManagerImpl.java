package tw.edu.chit.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.edu.chit.dao.CourseDAO;
import tw.edu.chit.dao.CourseJdbcDAO;
import tw.edu.chit.dao.SummerDAO;
import tw.edu.chit.dao.SummerJdbcDAO;
import tw.edu.chit.service.SummerManager;
import tw.edu.chit.util.Toolket;

public class SummerManagerImpl extends BaseManager implements SummerManager{
	private CourseDAO	courseDao;
	private CourseJdbcDAO	courscJdbcDao;
	private SummerDAO summerDao;
	private SummerJdbcDAO summerJdbcDao;

	public void setCourseDAO(CourseDAO dao) {
		this.courseDao = dao;
	}

	public void setCourseJdbcDAO(CourseJdbcDAO courscJdbcDao) {
		this.courscJdbcDao = courscJdbcDao;
	}

	public void setSummerDAO(SummerDAO dao) {
		this.summerDao = dao;
	}

	public void setSummerJdbcDAO(SummerJdbcDAO summerJdbcDao) {
		this.summerJdbcDao = summerJdbcDao;
	}



	/**
	 * 暑修開課查詢
	 */
	public List getSdtimeBy(String seqno, String opt, String departClass, String cscode, String techId, String status) {
		StringBuffer strBuf=new StringBuffer("SELECT d.status, d.seqno, d.techid, d.Oid, cs.name, d.depart_class, c.chi_name, c.cscode, c.eng_name, " +
				"e.cname, d.opt, d.credit, d.thour, d.stu_select, d.day1, d.day2, d.day3, d.day4, d.day5, d.day6, d.day7, d.clascode " +
				"FROM Sdtime d LEFT OUTER JOIN empl e ON d.techid=e.idno, Sabbr cs, Csno c " +
				"WHERE " +
				"c.cscode=d.cscode AND " +
				"cs.no=d.depart_class AND " +
				"d.seqno LIKE '"+seqno+"%' AND " +
				"d.opt LIKE '"+opt+"%' AND " +
				"d.depart_class LIKE '"+departClass+"%' AND " +
				"d.cscode LIKE '"+cscode+"%' AND d.status LIKE '"+status+"%'");
		if(!techId.trim().equals("")){
			strBuf.append(" AND d.techid='"+techId+"'");
		}

		Map map;
		List list=courscJdbcDao.StandardSqlQuery(strBuf.toString());
		List table=new ArrayList();

		for(int i=0; i<list.size(); i++){
			map=new HashMap();

			map.put("Oid",((Map)list.get(i)).get("Oid"));
			map.put("seqno",((Map)list.get(i)).get("seqno"));
			map.put("techid",((Map)list.get(i)).get("techid"));
			map.put("name",((Map)list.get(i)).get("name"));
			map.put("depart_class",((Map)list.get(i)).get("depart_class"));
			map.put("cscode",((Map)list.get(i)).get("cscode"));
			map.put("chi_name",((Map)list.get(i)).get("chi_name"));
			map.put("cname",((Map)list.get(i)).get("cname"));
			map.put("opt",((Map)list.get(i)).get("opt"));
			map.put("credit",((Map)list.get(i)).get("credit"));
			map.put("thour",((Map)list.get(i)).get("thour"));
			map.put("stu_select",countStuSelect(((Map)list.get(i)).get("cscode").toString(), ((Map)list.get(i)).get("depart_class").toString()));
			map.put("status",getStatus(((Map)list.get(i)).get("status").toString()));
			map.put("icon", "<img src='images/ico_file_excel1.png' border='0' title='選課人數'>");
			map.put("day1",((Map)list.get(i)).get("day1"));
			map.put("day2",((Map)list.get(i)).get("day2"));
			map.put("day3",((Map)list.get(i)).get("day3"));
			map.put("day4",((Map)list.get(i)).get("day4"));
			map.put("day5",((Map)list.get(i)).get("day5"));
			map.put("day6",((Map)list.get(i)).get("day6"));
			map.put("day7",((Map)list.get(i)).get("day7"));
			map.put("clascode",((Map)list.get(i)).get("clascode"));
			table.add(map);
		}
		return table;
	}

	/**
	 * 取得梯次
	 */
	public List getSweekBy(String daynite, String seqno, String wdate) {
		List list=summerJdbcDao.StandardSqlQuery("SELECT s.Oid, s.daynite, c.name, s.seqno, wdate FROM Sweek s, code5 c WHERE " +
				"c.category='Summer' AND c.idno=s.daynite AND daynite LIKE '"+daynite+"%' AND seqno LIKE '"+seqno+"%' AND wdate LIKE '"+wdate+"%'");

		List table=new ArrayList();
		Map map;
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		for(int i=0; i<list.size(); i++){

			map=new HashMap();
			map.put("Oid", ((Map)list.get(i)).get("Oid"));
			map.put("daynite", ((Map)list.get(i)).get("daynite"));
			map.put("name", ((Map)list.get(i)).get("name"));
			map.put("seqno", ((Map)list.get(i)).get("seqno"));
			try {
				date=dateFormat.parse(((Map)list.get(i)).get("wdate").toString());
				map.put("wdate", Toolket.printNativeDate(date)) ;
			} catch (ParseException e) {
				e.printStackTrace();
				map.put("wdate", "") ;
			}

			table.add(map);
		}

		return table;
	}

	/**
	 * 取得梯次資訊
	 */
	public List getSsterm() {

		return summerJdbcDao.StandardSqlQuery("SELECT seqno FROM Sweek GROUP BY seqno");
	}

	/**
	 * 取得選課人數
	 */
	private Integer countStuSelect(String cscode, String csdepartClass){
		return summerJdbcDao.StandardSqlQueryForInt("SELECT COUNT(*) FROM Sseld s WHERE " +
													"s.csdepart_class='"+csdepartClass+"' AND s.cscode='"+cscode+"'");
	}

	/**
	 * 隨便殺
	 */
	public void delAnything(String tableName, String Oid) {
		courscJdbcDao.getJdbcTemplate().execute("DELETE FROM "+tableName+" WHERE Oid='"+Oid+"'");
	}

	/**
	 * 隨便存
	 */
	public void saveObj(Object po) {
		summerDao.saveObject(po);
	}

	/**
	 * 查曠課
	 */
	public List getSdilgBy(String seqno, String csDepartClass, String stDepartClass, String studentNo, String ddate, String daynite, String cscode) {
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		if(!ddate.equals("")){
			ddate=df.format(Toolket.parseDate(ddate));
		}
		String sql="SELECT d.cscode, d.depart_class as DdepartClass, st.student_name, c.ClassName, cs.chi_name, sd.seqno, sd.depart_class, sd.student_no, " +
				"sd.week_no, sd.week_day, sd.ddate,sd.daynite, sd.abs1, sd.abs2, sd.abs3, sd.abs4, sd.abs5, sd.abs6, " +
				"sd.abs7, sd.abs8, sd.abs9, sd.abs10, sd.Oid FROM Sdilg sd, stmd st, Class c, Sseld s, Sdtime d, Csno cs " +
				"WHERE sd.student_no=s.student_no AND d.depart_class=s.csdepart_class AND d.cscode=s.cscode AND " +
				"cs.cscode=d.cscode AND sd.student_no=st.student_no AND st.depart_class=c.ClassNo AND sd.seqno LIKE '"+seqno+"%' " +
				"AND d.depart_class LIKE '"+csDepartClass+"%' AND st.depart_class LIKE '"+stDepartClass+"%' AND sd.student_no LIKE '"+studentNo+"%' " +
				"AND sd.ddate LIKE'"+ddate+"%' AND sd.daynite LIKE '"+daynite+"%' AND d.cscode LIKE '"+cscode+"%'";
		
		List list=summerJdbcDao.StandardSqlQuery(sql);
		List table=new ArrayList();
		Map map;

		Date date;
		for(int i=0; i<list.size(); i++){
			map=new HashMap();
			map.put("student_name", ((Map)list.get(i)).get("student_name"));
			map.put("ClassName", ((Map)list.get(i)).get("ClassName"));
			map.put("chi_name", ((Map)list.get(i)).get("chi_name"));
			map.put("seqno", ((Map)list.get(i)).get("seqno"));
			map.put("depart_class", ((Map)list.get(i)).get("depart_class"));
			map.put("student_no", ((Map)list.get(i)).get("student_no"));
			map.put("week_no", ((Map)list.get(i)).get("week_no"));
			map.put("week_day", ((Map)list.get(i)).get("week_day"));
			map.put("DdepartClass", ((Map)list.get(i)).get("DdepartClass"));
			map.put("cscode", ((Map)list.get(i)).get("cscode"));
			try {
				date=df.parse(((Map)list.get(i)).get("ddate").toString());
				map.put("ddate", Toolket.printNativeDate(date));
			} catch (ParseException e) {
				map.put("ddate", "");
				e.printStackTrace();
			}
			map.put("daynite", ((Map)list.get(i)).get("daynite"));

			int g=10;
			for(int j=1; j<=10; j++){
				if(((Map)list.get(i)).get("abs"+j)!=null){
					map.put("abs"+j, getTimeOffType(((Map)list.get(i)).get("abs"+j).toString()));
				}
			}
			map.put("Oid", ((Map)list.get(i)).get("Oid"));

			table.add(map);
		}

		return table;
	}
	
	/**
	 * 曠課形態
	 */
	public String getTimeOffType(String abs){
		if(abs.trim().equals("")){
			abs="0";
		}
		//我不要用code5的假別，因為有奇怪的數字在前面 ex:2曠課
		switch(Integer.parseInt(abs)) {
		case 1:
			return "昇旗";
		case 2:
			return "<font color='#C45F49'>曠</font>";
		case 3:
			return "<font color='#A9C163'>病</font>";
		case 4:
			return "<font color='#EBA066'>事</font>";
		case 5:
			return "<font color='#58698A'>遲</font>";
		case 6:
			return "<font color='#cccccc'>公</font>";
		case 7:
			return "<font color='#D7E3F3'>喪";
		case 8:
			return "早退";
		default:
			return "";
		}
	}

	/**
	 * JDBC隨便查
	 */
	public List ezGetList(String sql) {
		return summerJdbcDao.StandardSqlQuery(sql);
	}

	/**
	 * JDBC隨便查
	 */
	public Integer ezGetInt(String sql) {
		return summerJdbcDao.StandardSqlQueryForInt(sql);
	}
	
	/**
	 * 開課形態
	 */
	private String getStatus(String status){
		switch(Integer.parseInt(status)) {
		case 0:
			return "開課";
		case 1:
			return "退費";
		default:
			return "";
		}
	}
	
	/**
	 * 隨便殺by oid
	 */
	public void removeObject(String table, String oid) {
		String sql="DELETE FROM "+table+" WHERE Oid='"+oid+"'";
		summerJdbcDao.removeObj(sql);
		
	}
	
	/**
	 * 隨便改
	 */	
	public void saveOrUpdate(String sql) {
		summerJdbcDao.removeObj(sql);		
	}
	
	/**
	 * 取學生缺曠 (個人時數)
	 */
	public Integer getSdilgBy(String studentNo, String csdepartClass, String cscode, String seqno) {
		String sql="SELECT sd.abs1, sd.abs2, sd.abs3, sd.abs4, sd.abs5, sd.abs6, sd.abs7, sd.abs8, sd.abs9, sd.abs10, sd.abs11 " +
		"FROM Sdilg sd, Sdtime d, Sseld s " +
		"WHERE sd.student_no='"+studentNo+"' AND d.seqno=sd.seqno AND d.depart_class=sd.depart_class AND " +
		"s.student_no=sd.student_no AND s.csdepart_class=d.depart_class AND s.cscode=d.cscode AND " +
		"s.csdepart_class='"+csdepartClass+"' AND s.cscode='"+cscode+"'";

		//一門課的上課節次
		List sdtime=ezGetList("SELECT * FROM Sdtime WHERE seqno='"+seqno+"' AND cscode='"+cscode+"' AND depart_class='"+csdepartClass+"'");

		String day = null;
		for(int i=1; i<=11; i++){
	
		if(((Map)sdtime.get(0)).get("day"+i)!=null&&!((Map)sdtime.get(0)).get("day"+i).equals("")){
		day=((Map)sdtime.get(0)).get("day"+i).toString();
		
			}
		}

		List list=summerJdbcDao.StandardSqlQuery(sql);
		int tmp=0;
		for(int i=0; i<list.size(); i++){
			for(int k=0; k<day.length(); k++){
		
				if(((Map)list.get(i)).get("abs"+day.substring(k, k+1))!=null){					
			
					if( !((Map)list.get(i)).get("abs"+day.substring(k, k+1)).toString().trim().equals("")){
						tmp=tmp+1;
					}
				}				
			}
		}		
		//System.out.println("前面是 "+tmp);
		
		
		
		
		return tmp;
	}
	
	/**
	 * 取學生缺曠 (全域-時數)
	 */
	public Integer getSdilgsBy(String studentNo, String csdepartClass, String cscode, String seqno) {
		String sql="SELECT sd.abs1, sd.abs2, sd.abs3, sd.abs4, sd.abs5, sd.abs6, sd.abs7, sd.abs8, sd.abs9, sd.abs10, sd.abs11 " +
				"FROM Sdilg sd, Sdtime d, Sseld s, stmd st " +
				"WHERE st.student_no='"+studentNo+"' AND d.seqno=sd.seqno AND d.depart_class=sd.depart_class AND " +
				"s.student_no=sd.student_no AND s.csdepart_class=d.depart_class AND s.cscode=d.cscode AND " +
				"s.csdepart_class='"+csdepartClass+"' AND s.cscode='"+cscode+"' AND st.student_no=sd.student_no";
		
		//一門課的上課節次
		List sdtime=ezGetList("SELECT * FROM Sdtime WHERE seqno='"+seqno+"' AND cscode='"+cscode+"' AND depart_class='"+csdepartClass+"'");
		//System.out.println("SELECT * FROM Sdtime WHERE seqno='"+seqno+"' AND cscode='"+cscode+"' AND depart_class='"+csdepartClass+"'");
		String day = null;
		for(int i=1; i<=11; i++){
			if(((Map)sdtime.get(0)).get("day"+i)!=null && 
					!((Map)sdtime.get(0)).get("day"+i).equals("")){
				
				day=((Map)sdtime.get(0)).get("day"+i).toString();
			}
		}		
		//System.out.println(seqno+", "+csdepartClass+", "+cscode);
		List list=summerJdbcDao.StandardSqlQuery(sql);
		int tmp=0;
		for(int i=0; i<list.size(); i++){			
				
			for(int k=0; k<day.length(); k++){
				
				if( !((Map)list.get(i)).get("abs"+day.substring(k, k+1)).toString().trim().equals("") && 
						!((Map)list.get(i)).get("abs"+day.substring(k, k+1)).toString().trim().equals("5")){
					tmp=tmp+1;
					
				}
			}
		}		
		//System.out.println("後面是 "+tmp);
		return tmp;
	}
	
	/**
	 * 拿暑修成績建立歷史成績
	 */
	public List getScoreBy(String seqno, String departClass, String cscode) {
		
		int syear=ezGetInt("SELECT Value FROM Parameter WHERE name='School_year'");
		int sterm=ezGetInt("SELECT Value FROM Parameter WHERE name='School_term'");
		if(sterm==1)syear--;
		
		//TODO 暑修一定是下學期，寒假或隨時開的話再說
		//int sterm=ezGetInt("SELECT Value FROM Parameter WHERE name='2'");
		//int sterm=2;
		
		Map map;
		List table=new ArrayList();
		List list=ezGetList("SELECT d.Oid, d.seqno, sa.no, sa.name, d.cscode, c.chi_name, e.cname, d.credit, d.thour " +
				"FROM Sdtime d, Sabbr sa, Csno c, empl e WHERE d.depart_class=sa.no AND c.cscode=d.cscode " +
				"AND e.idno=d.techid AND d.seqno LIKE '"+seqno+"%' AND d.depart_class LIKE '"+departClass+"%' AND d.cscode LIKE '"+cscode+"%'");
		
		List students;
		StringBuilder sb;		
		
		int stu_select;
		int scored;
		int filed;		
		
		for(int i=0; i<list.size(); i++){
			map=new HashMap();
			map.put("Oid", ((Map)list.get(i)).get("Oid"));
			map.put("seqno", ((Map)list.get(i)).get("seqno"));
			map.put("no", ((Map)list.get(i)).get("no"));
			map.put("name", ((Map)list.get(i)).get("name"));
			map.put("cscode", ((Map)list.get(i)).get("cscode"));
			map.put("chi_name", ((Map)list.get(i)).get("chi_name"));
			map.put("cname", ((Map)list.get(i)).get("cname"));
			map.put("credit", ((Map)list.get(i)).get("credit"));
			map.put("thour", ((Map)list.get(i)).get("thour"));			
			sb=new StringBuilder("AND student_no IN(");			
			students=ezGetList("SELECT * FROM Sseld WHERE " +
					"csdepart_class='"+((Map)list.get(i)).get("no")+"' AND cscode='"+((Map)list.get(i)).get("cscode")+"'");
			
			for(int j=0; j<students.size(); j++){
				sb.append("'"+((Map)students.get(j)).get("student_no")+"', ");
			}
			sb.delete(sb.length()-2, sb.length());
			sb.append(")");			
			stu_select=students.size();
			map.put("stu_select", stu_select);//已選人數
			if(students.size()>0){
				filed=ezGetInt("SELECT COUNT(*)FROM ScoreHist WHERE evgr_type='3'AND cscode='"+((Map)list.get(i)).get("cscode")+"' AND school_year='"+syear+"' "+sb.toString());
				scored=ezGetInt("SELECT COUNT(*) FROM Sseld WHERE " +//已建檔人數
						"csdepart_class='"+((Map)list.get(i)).get("no")+"' AND cscode='"+((Map)list.get(i)).get("cscode")+"' AND (score!='' AND score IS NOT NULL) "+sb.toString());
						map.put("filed", scored);
			}else{
				filed=ezGetInt("SELECT COUNT(*)FROM ScoreHist WHERE evgr_type='3'AND cscode='"+((Map)list.get(i)).get("cscode")+"' AND school_year='"+syear+"'");
				scored=ezGetInt("SELECT COUNT(*) FROM Sseld WHERE " +//已建檔人數
						"csdepart_class='"+((Map)list.get(i)).get("no")+"' AND cscode='"+((Map)list.get(i)).get("cscode")+"' AND (score!='' AND score IS NOT NULL)");
						map.put("filed", scored);
			}			
			//if(map.get("stu_select")==tmp){
			if(stu_select==filed){
				map.put("descripton", "<img src='images/icon/accept.gif' title='已建立此課程的歷史成績(參考用)'>");
			}else{
				map.put("descripton", "");
			}			
			if(students.size()>0)
			table.add(map);			
		}		
		return table;
	}

	public List hqlGetList(String hql) {
		return courseDao.StandardHqlQuery(hql);
	}

	/**
	 * 暑修先修班和正常課程的衝堂
	 */
	public boolean checkReOptionForSummer(String studentNo, String sDtimeOid){
		
		List myDc=summerJdbcDao.StandardSqlQuery("SELECT dc.begin, dc.end, dc.week FROM Seld s, Dtime d, Dtime_class dc " +
				"WHERE s.Dtime_oid=d.Oid AND d.Sterm='2' AND dc.Dtime_oid=d.Oid AND student_no='"+studentNo+"'");
		
		int begin;
		int end;
		//System.out.println("SELECT * FROM Sdtime WHERE Oid='"+sDtimeOid+"'");
		Map map=((Map)ezGetList("SELECT * FROM Sdtime WHERE Oid='"+sDtimeOid+"'").get(0));
		try{
			for(int i=0; i<myDc.size(); i++){				
				begin=Integer.parseInt(((Map)myDc.get(i)).get("begin").toString());
				end=Integer.parseInt(((Map)myDc.get(i)).get("end").toString());				
				for(int j=begin; j<=end; j++){					
					String day="day"+((Map)myDc.get(i)).get("week");					
					//System.out.println("class"+map.get(day));
					//System.out.println(j);
					if(map.get(day).toString().indexOf(j+"")>-1){					
						return true;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public Map checkReOption4Summer(String studentNo, String sDtimeOid){
		//Map resoult=new HashMap();
		List myDc=summerJdbcDao.StandardSqlQuery("SELECT d.cscode as dcscode, d.depart_class as ddepart_class, dc.begin, dc.end, dc.week FROM Seld s, Dtime d, Dtime_class dc " +
				"WHERE s.Dtime_oid=d.Oid AND d.Sterm='2' AND dc.Dtime_oid=d.Oid AND student_no='"+studentNo+"'");
		
		int begin;
		int end;
		//System.out.println("SELECT * FROM Sdtime WHERE Oid='"+sDtimeOid+"'");
		Map map=((Map)ezGetList("SELECT * FROM Sdtime WHERE Oid='"+sDtimeOid+"'").get(0));
		try{
			for(int i=0; i<myDc.size(); i++){				
				begin=Integer.parseInt(((Map)myDc.get(i)).get("begin").toString());
				end=Integer.parseInt(((Map)myDc.get(i)).get("end").toString());
				
				//System.out.println("begin="+begin+", end="+end);
				for(int j=begin; j<=end; j++){					
					String day="day"+((Map)myDc.get(i)).get("week");
					
					//System.out.println(map.get(day));
					//System.out.println(j);
					
					
					if(map.get(day).toString().indexOf(j+"")>-1){
						
						map.put("checked", "Y");
						map.put("dcscode", ((Map)myDc.get(i)).get("dcscode"));
						map.put("ddepart_class", ((Map)myDc.get(i)).get("ddepart_class"));
						map.put("week", ((Map)myDc.get(i)).get("week"));
						map.put("begin", begin);
						map.put("end", end);
						return map;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return map;
		}
		map.put("checked", "");
		return map;
	}
}
