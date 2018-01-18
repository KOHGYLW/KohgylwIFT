package kohgylw.enumeration;

/**
 * 账户权限枚举
 * <p>这是对用户权限的定义</p>
 * <ul>
 * <li>CREATE_NEW_FOLDER 创建新文件夹</li>
 * <li>UPLOAD_FILES 上传文件</li>
 * <li>DELETE_FILE_OR_FOLDER 删除文件或文件夹</li>
 * </ul>
 * */
public enum AccountAuth {
	
	CREATE_NEW_FOLDER,//创建新文件夹
	UPLOAD_FILES,//上传文件
	DELETE_FILE_OR_FOLDER,//删除文件或文件夹
	RENAME_FILE_OR_FOLDER,//重命名文件或文件夹
	DOWNLOAD_FILES//下载文件
}
