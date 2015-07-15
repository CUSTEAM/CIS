package tw.edu.chit.model.domain;

public class UploadFileInfo {
	int fileOid;
	public int getFileOid() {
		return fileOid;
	}

	public void setFileOid(int fileOid) {
		this.fileOid = fileOid;
	}

	String fileName;
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	String fileType;
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public UploadFileInfo(){};
		
	public UploadFileInfo(int fileOid, String fileName){
		this.fileOid = fileOid;
		this.fileName = fileName;
	}

	public UploadFileInfo(int fileOid, String fileName, String fileType){
		this.fileOid = fileOid;
		this.fileName = fileName;
		this.fileType = fileType;
	}

}
