package kohgylw.model;

public class Folder {
	
	private String folderId;
	private String folderName;
	private String folderCreationDate;
	private String folderCreator;
	private String folderParent;
	
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getFolderCreationDate() {
		return folderCreationDate;
	}
	public void setFolderCreationDate(String folderCreationDate) {
		this.folderCreationDate = folderCreationDate;
	}
	public String getFolderCreator() {
		return folderCreator;
	}
	public void setFolderCreator(String folderCreator) {
		this.folderCreator = folderCreator;
	}
	public String getFolderParent() {
		return folderParent;
	}
	public void setFolderParent(String folderParent) {
		this.folderParent = folderParent;
	}
}
