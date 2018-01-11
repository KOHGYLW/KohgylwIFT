package kohgylw.service;

import javax.servlet.http.HttpServletRequest;

public interface FolderService {

	/**
	 * 创建新的文件夹
	 * <p>创建一个新的文件夹，并返回相应的结果代码，结果代码请见下方</p>
	 * @return String 结果代码：
	 * <ul>
	 * <li>noAccount 无法获取用户信息，创建失败</li>
	 * <li>noAuthorized 用户无权限进行此操作，创建失败</li>
	 * <li>errorParameter 传入参数格式错误或缺失，创建失败</li>
	 * <li>folderAlreadyExist 该文件夹已存在，创建失败</li>
	 * <li>cannotCreateFolder 无法写入文件夹信息，创建失败</li>
	 * <li>createFolderSuccess 创建文件夹成功</li>
	 * </ul>
	 * */
	String newFolder(HttpServletRequest request);
	
	/**
	 * 删除文件夹及其全部内容
	 * <p>删除指定文件夹，并返回相应的结果代码，结果代码请见下方</p>
	 * @return String 结果代码：
	 * <ul>
	 * <li>noAccount 无法获取用户信息，删除失败</li>
	 * <li>noAuthorized 用户无权限进行此操作，删除失败</li>
	 * <li>errorParameter 传入参数格式错误或缺失，删除失败</li>
	 * <li>deleteFolderSuccess 文件夹删除成功</li>
	 * <li>cannotDeleteFolder 无法删除文件夹信息，删除失败</li>
	 * </ul>
	 * */
	String deleteFolder(HttpServletRequest request);
	
	/**
	 * 修改文件夹名称
	 * <p>重命名指定文件夹，并返回相应的结果代码，结果代码请见下方</p>
	 * @return String 结果代码：
	 * <ul>
	 * <li>noAccount 无法获取用户信息，重命名失败</li>
	 * <li>noAuthorized 用户无权限进行此操作，重命名失败</li>
	 * <li>errorParameter 传入参数格式错误或缺失，重命名失败</li>
	 * <li>renameFolderSuccess 文件夹重命名成功</li>
	 * <li>cannotRenameFolder 无法重命名文件夹，重命名失败</li>
	 * </ul>
	 * */
	String renameFolder(HttpServletRequest request);
}
