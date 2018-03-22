package kohgylw.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import kohgylw.enumeration.LogLevel;
import kohgylw.mapper.FileMapper;
import kohgylw.mapper.FolderMapper;
import kohgylw.model.Folder;

/**
 * 日志记录工具，主要负责将各种日志写入到文件中
 * <p>
 * 日志的默认写入位置为用户主目录的文档（~/Documents/）下的KohgylwIFT/log文件夹，日志文件的名称为YYYY_MM_dd.klog
 * </p>
 * <h3>日志文件格式说明：</h3>
 * <p>
 * 第一段：日志记录时间;第二段：日志类型;第三段：日志内容
 * <p>
 */
@Component
public class LogUtil {

	@Resource
	private FolderUtil fu;
	@Resource
	private FolderMapper fm;
	@Resource
	private FileMapper fim;

	private String sep = "";
	private String log = "";

	public LogUtil() {
		sep = System.getProperty("file.separator");
		log = System.getProperty("user.home") + sep + "Documents" + sep + "KohgylwIFT" + sep + "log";
		File l = new File(log);
		if (!l.exists()) {
			l.mkdir();
		} else {
			if (!l.isDirectory()) {
				l.delete();
				l.mkdir();
			}
		}
	}

	/**
	 * 以格式化记录异常信息
	 * <p>
	 * 创建日志文件并写入异常信息，当同日期的日志文件存在时，则在其后面追加该信息
	 * </p>
	 * 
	 * @param e
	 *            Exception 需要记录的异常对象
	 */
	public void writeException(Exception e, HttpServletRequest request) {
		if (ConfigureReader.instance(request).inspectLogLevel(LogLevel.Runtime_Exception)) {
			writeToLog("Exception", "[" + e + "]:" + e.getMessage());
		}
	}

	/**
	 * 以格式化记录新建文件夹日志
	 * <p>
	 * 写入新建文件夹信息，包括操作者、路劲及新文件夹名称
	 * </p>
	 */
	public void writeCreateFolderEvent(HttpServletRequest request, Folder f) {
		if (ConfigureReader.instance(request).inspectLogLevel(LogLevel.Event)) {
			String account = (String) request.getSession().getAttribute("ACCOUNT");
			if (account == null || account.length() == 0) {
				account = "Anonymous";
			}
			String a = account;// 方便下方使用终态操作
			Thread t = new Thread(() -> {
				List<Folder> l = fu.getParentList(f.getFolderId());
				String pl = new String();
				for (Folder i : l) {
					pl = pl + i.getFolderName() + "/";
				}
				String content = ">ACCOUNT [" + a + "]\r\n>OPERATE [Create new folder]\r\n>PATH [" + pl + "]\r\n>NAME ["
						+ f.getFolderName() + "]";
				writeToLog("Event", content);
			});
			t.start();
		}
	}

	/**
	 * 以格式化记录重命名文件夹日志
	 * <p>
	 * 写入重命名文件夹信息
	 * </p>
	 */
	public void writeRenameFolderEvent(HttpServletRequest request, Folder f, String newName) {
		if (ConfigureReader.instance(request).inspectLogLevel(LogLevel.Event)) {
			String account = (String) request.getSession().getAttribute("ACCOUNT");
			if (account == null || account.length() == 0) {
				account = "Anonymous";
			}
			String a = account;
			Thread t = new Thread(() -> {
				List<Folder> l = fu.getParentList(f.getFolderId());
				String pl = new String();
				for (Folder i : l) {
					pl = pl + i.getFolderName() + "/";
				}
				String content = ">ACCOUNT [" + a + "]\r\n>OPERATE [Rename folder]\r\n>PATH [" + pl + "]\r\n>NAME ["
						+ f.getFolderName() + "]->[" + newName + "]";
				writeToLog("Event", content);
			});
			t.start();
		}
	}

	/**
	 * 以格式化记录删除文件夹日志
	 * <p>
	 * 写入删除文件夹信息
	 * </p>
	 */
	public void writeDeleteFolderEvent(HttpServletRequest request, Folder f, List<Folder> l) {
		if (ConfigureReader.instance(request).inspectLogLevel(LogLevel.Event)) {
			String account = (String) request.getSession().getAttribute("ACCOUNT");
			if (account == null || account.length() == 0) {
				account = "Anonymous";
			}
			String a = account;
			Thread t = new Thread(() -> {
				String pl = new String();
				for (Folder i : l) {
					pl = pl + i.getFolderName() + "/";
				}
				String content = ">ACCOUNT [" + a + "]\r\n>OPERATE [Delete folder]\r\n>PATH [" + pl + "]\r\n>NAME ["
						+ f.getFolderName() + "]";
				writeToLog("Event", content);
			});
			t.start();
		}
	}

	/**
	 * 以格式化记录删除文件日志
	 * <p>
	 * 写入删除文件信息
	 * </p>
	 */
	public void writeDeleteFileEvent(HttpServletRequest request, kohgylw.model.File f) {
		if (ConfigureReader.instance(request).inspectLogLevel(LogLevel.Event)) {
			String account = (String) request.getSession().getAttribute("ACCOUNT");
			if (account == null || account.length() == 0) {
				account = "Anonymous";
			}
			String a = account;
			Thread t = new Thread(() -> {
				Folder folder = fm.queryById(f.getFileParentFolder());
				List<Folder> l = fu.getParentList(folder.getFolderId());
				String pl = new String();
				for (Folder i : l) {
					pl = pl + i.getFolderName() + "/";
				}
				String content = ">ACCOUNT [" + a + "]\r\n>OPERATE [Delete file]\r\n>PATH [" + pl
						+ folder.getFolderName() + "]\r\n>NAME [" + f.getFileName() + "]";
				writeToLog("Event", content);
			});
			t.start();
		}
	}

	/**
	 * 以格式化记录上传文件日志
	 * <p>
	 * 写入上传文件信息
	 * </p>
	 */
	public void writeUploadFileEvent(HttpServletRequest request, kohgylw.model.File f) {
		if (ConfigureReader.instance(request).inspectLogLevel(LogLevel.Event)) {
			String account = (String) request.getSession().getAttribute("ACCOUNT");
			if (account == null || account.length() == 0) {
				account = "Anonymous";
			}
			String a = account;
			Thread t = new Thread(() -> {
				Folder folder = fm.queryById(f.getFileParentFolder());
				List<Folder> l = fu.getParentList(folder.getFolderId());
				String pl = new String();
				for (Folder i : l) {
					pl = pl + i.getFolderName() + "/";
				}
				String content = ">ACCOUNT [" + a + "]\r\n>OPERATE [Upload file]\r\n>PATH [" + pl
						+ folder.getFolderName() + "]\r\n>NAME [" + f.getFileName() + "]";
				writeToLog("Event", content);
			});
			t.start();
		}
	}

	/**
	 * 以格式化记录下载文件日志
	 * <p>
	 * 写入下载文件信息
	 * </p>
	 */
	public void writeDownloadFileEvent(HttpServletRequest request, kohgylw.model.File f) {
		if (ConfigureReader.instance(request).inspectLogLevel(LogLevel.Event)) {
			String account = (String) request.getSession().getAttribute("ACCOUNT");
			if (account == null || account.length() == 0) {
				account = "Anonymous";
			}
			String a = account;
			Thread t = new Thread(() -> {
				Folder folder = fm.queryById(f.getFileParentFolder());
				List<Folder> l = fu.getParentList(folder.getFolderId());
				String pl = new String();
				for (Folder i : l) {
					pl = pl + i.getFolderName() + "/";
				}
				String content = ">ACCOUNT [" + a + "]\r\n>OPERATE [Download file]\r\n>PATH [" + pl
						+ folder.getFolderName() + "]\r\n>NAME [" + f.getFileName() + "]";
				writeToLog("Event", content);
			});
			t.start();
		}
	}

	/**
	 * 以格式化记录重命名文件日志
	 * <p>
	 * 写入重命名文件信息
	 * </p>
	 */
	public void writeRenameFileEvent(HttpServletRequest request, kohgylw.model.File f, String newName) {
		if (ConfigureReader.instance(request).inspectLogLevel(LogLevel.Event)) {
			String account = (String) request.getSession().getAttribute("ACCOUNT");
			if (account == null || account.length() == 0) {
				account = "Anonymous";
			}
			String a = account;
			Thread t = new Thread(() -> {
				Folder folder = fm.queryById(f.getFileParentFolder());
				List<Folder> l = fu.getParentList(folder.getFolderId());
				String pl = new String();
				for (Folder i : l) {
					pl = pl + i.getFolderName() + "/";
				}
				String content = ">ACCOUNT [" + a + "]\r\n>OPERATE [Rename file]\r\n>PATH [" + pl
						+ folder.getFolderName() + "]\r\n>NAME [" + f.getFileName() + "]->[" + newName + "]";
				writeToLog("Event", content);
			});
			t.start();
		}
	}

	private void writeToLog(String type, String content) {
		String t = ServerTimeUtil.accurateToLogName();
		File f = new File(log, t + ".klog");
		FileWriter fw = null;
		if (f.exists()) {
			try {
				fw = new FileWriter(f, true);
				fw.write("\r\n\r\nTIME:\r\n" + ServerTimeUtil.accurateToSecond() + "\r\nTYPE:\r\n" + type
						+ "\r\nCONTENT:\r\n" + content);
				fw.close();
			} catch (Exception e1) {
				System.out.println("KohgylwIFT:[Log]Cannt write to file,message:" + e1.getMessage());
			}
		} else {
			try {
				fw = new FileWriter(f, false);
				fw.write("TIME:\r\n" + ServerTimeUtil.accurateToSecond() + "\r\nTYPE:\r\n" + type + "\r\nCONTENT:\r\n"
						+ content);
				fw.close();
			} catch (IOException e1) {
				System.out.println("KohgylwIFT:[Log]Cannt write to file,message:" + e1.getMessage());
			}
		}
	}

	/**
	 * 以格式化记录下载文件日志
	 * <p>
	 * 写入下载文件信息
	 * </p>
	 */
	public void writeDownloadCheckedFileEvent(HttpServletRequest request, List<String> idList) {
		if (ConfigureReader.instance(request).inspectLogLevel(LogLevel.Event)) {
			String account = (String) request.getSession().getAttribute("ACCOUNT");
			if (account == null || account.length() == 0) {
				account = "Anonymous";
			}
			String a = account;
			Thread t = new Thread(() -> {
				StringBuffer content = new StringBuffer(
						">ACCOUNT [" + a + "]\r\n>OPERATE [Download checked file]\r\n----------------\r\n");
				for (String fid : idList) {
					kohgylw.model.File f = fim.queryById(fid);
					if (f != null) {
						Folder folder = fm.queryById(f.getFileParentFolder());
						List<Folder> l = fu.getParentList(folder.getFolderId());
						String pl = new String();
						for (Folder i : l) {
							pl = pl + i.getFolderName() + "/";
						}
						content.append(
								">PATH [" + pl + folder.getFolderName() + "]\r\n>NAME [" + f.getFileName() + "]\r\n");
					}
				}
				content.append("----------------");
				writeToLog("Event", content.toString());
			});
			t.start();
		}

	}

}
