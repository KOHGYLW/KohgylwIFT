package kohgylw.pojo;

import java.util.List;

import kohgylw.model.File;
import kohgylw.model.Folder;

/**
 * 文件夹的内部视图封装类
 * <p>该类对文件夹的显示数据进行封装并发送至前端使用</p>
 * <p>
 * 内部包括：
 * <ul>
 * <li>本文件夹信息</li>
 * <li>上级目录列表</li>
 * <li>文件夹列表</li>
 * <li>账户名称</li>
 * <li>权限列表</li>
 * <li>更新时间</li>
 * </ul>
 * </p>
 * */
public class FolderView {
	
	private Folder folder;
	private List<Folder> parentList;
	private List<Folder> folderList;
	private List<File> fileList;
	private String account;
	private List<String> authList;
	private String publishTime;
	
	public Folder getFolder() {
		return folder;
	}
	public void setFolder(Folder folder) {
		this.folder = folder;
	}
	public List<Folder> getParentList() {
		return parentList;
	}
	public void setParentList(List<Folder> parentList) {
		this.parentList = parentList;
	}
	public List<Folder> getFolderList() {
		return folderList;
	}
	public void setFolderList(List<Folder> folderList) {
		this.folderList = folderList;
	}
	public List<File> getFileList() {
		return fileList;
	}
	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}
	public List<String> getAuthList() {
		return authList;
	}
	public void setAuthList(List<String> authList) {
		this.authList = authList;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

}
