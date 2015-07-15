package tw.edu.chit.model;

import java.util.Date;
import java.util.List;

import tw.edu.chit.model.domain.PeriodInfo;
import tw.edu.chit.model.domain.UploadFileInfo;
import tw.edu.chit.util.IConstants;

public class StudPublicLeaveBase {
	private String askLeaveName;	//假別名稱
	private String deptClassName;	//假別名稱
	private String statusName;	//處理狀態名稱
	private Date processDate;	//最後處理時間
	private boolean isExpired;	//假單已超過請假期限
	private String simpleStartDate;	//只顯示日期,不顯示時間
	private String simpleEndDate;
	private StudPublicDocExam exam;	//存放目前或任一層的審核狀況
	private List<StudPublicDocExam> exams;	//存放所有的審核資料
	private List<UploadFileInfo> uploadFileInfo;	//上傳檔案的資訊
	private List<StudDocApply> studDoc;	//學生假單檔
	private String fileOids;	//上傳檔案的oid集合字串,例如:'123|456|...'
	private List<String> studentNos; //公假學生
	private String applierName;
	private String applierUnit;
	
	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public String getAskLeaveName() {
		return askLeaveName;
	}

	public void setAskLeaveName(String askLeaveName) {
		this.askLeaveName = askLeaveName;
	}

	public List<UploadFileInfo> getUploadFileInfo() {
		return this.uploadFileInfo;
	}

	public void setUploadFileInfo(List<UploadFileInfo> uploadFileInfo) {
		this.uploadFileInfo = uploadFileInfo;
	}
	
	public String getFileOids() {
		return fileOids;
	}

	public void setFileOids(String fileOids) {
		this.fileOids = fileOids;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public void setStatusNameBycode(String code){
		this.statusName = "";
		for (int i = 0; i < IConstants.STUDDocProcess.length; i++) {
			if (code.equals(IConstants.STUDDocProcess[i][0])) {
				this.statusName = IConstants.STUDDocProcess[i][1];
				break;
			}
		}
	}

	public String getSetStatusNameBycode(String code){
		this.statusName = "";
		String name = "";
		for (int i = 0; i < IConstants.STUDDocProcess.length; i++) {
			if (code.equals(IConstants.STUDDocProcess[i][0])) {
				this.statusName = IConstants.STUDDocProcess[i][1];
				break;
			}
		}
		return statusName;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public String getSimpleStartDate() {
		return simpleStartDate;
	}

	public void setSimpleStartDate(String simpleStartDate) {
		this.simpleStartDate = simpleStartDate;
	}

	public String getSimpleEndDate() {
		return simpleEndDate;
	}

	public void setSimpleEndDate(String simpleEndDate) {
		this.simpleEndDate = simpleEndDate;
	}

	public void setExam(StudPublicDocExam exam) {
		this.exam = exam;
	}

	public StudPublicDocExam getExam() {
		return exam;
	}

	public void setExams(List<StudPublicDocExam> exams) {
		this.exams = exams;
	}

	public List<StudPublicDocExam> getExams() {
		return exams;
	}

	public void setStudDoc(List<StudDocApply> studDoc) {
		this.studDoc = studDoc;
	}

	public List<StudDocApply> getStudDoc() {
		return studDoc;
	}

	public void setStudentNos(List<String> studentNos) {
		this.studentNos = studentNos;
	}

	public List<String> getStudentNos() {
		return studentNos;
	}

	public void setDeptClassName(String deptClassName) {
		this.deptClassName = deptClassName;
	}

	public String getDeptClassName() {
		return deptClassName;
	}

	public void setApplierUnit(String applierUnit) {
		this.applierUnit = applierUnit;
	}

	public String getApplierUnit() {
		return applierUnit;
	}

	public void setApplierName(String applierName) {
		this.applierName = applierName;
	}

	public String getApplierName() {
		return applierName;
	}


}
