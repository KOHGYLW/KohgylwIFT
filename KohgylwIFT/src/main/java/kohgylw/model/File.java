package kohgylw.model;

public class File {
	
	private String fileId;
	private String fileName;
	private String fileSize;
	private String fileParentFolder;
	private String fileCreationDate;
	private String fileCreator;
	private String filePath;
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileParentFolder() {
		return fileParentFolder;
	}
	public void setFileParentFolder(String fileParentFolder) {
		this.fileParentFolder = fileParentFolder;
	}
	public String getFileCreationDate() {
		return fileCreationDate;
	}
	public void setFileCreationDate(String fileCreationDate) {
		this.fileCreationDate = fileCreationDate;
	}
	public String getFileCreator() {
		return fileCreator;
	}
	public void setFileCreator(String fileCreator) {
		this.fileCreator = fileCreator;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
