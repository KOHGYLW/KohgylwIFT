package kohgylw.service.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kohgylw.enumeration.AccountAuth;
import kohgylw.mapper.FileMapper;
import kohgylw.model.File;
import kohgylw.service.FileService;
import kohgylw.util.ConfigureReader;
import kohgylw.util.FileBlockUtil;
import kohgylw.util.ServerTimeUtil;
import kohgylw.util.TextFormateUtil;

@Service
public class FileServiceImpl implements FileService {

	@Resource
	private FileMapper fm;

	@Resource
	private FileBlockUtil fbu;

	@Override
	public String checkUploadFile(HttpServletRequest request) {
		// TODO 自动生成的方法存根
		String account = (String) request.getSession().getAttribute("ACCOUNT");
		String folderId = request.getParameter("folderId");
		String filename = request.getParameter("filename");
		if (folderId != null && folderId.length() > 0 && filename != null && filename.length() > 0) {
			if (ConfigureReader.instance(request).authorized(account, AccountAuth.UPLOAD_FILES)) {
				List<File> files = fm.queryByParentFolderId(folderId);
				boolean duplication = false;
				for (File f : files) {
					if (f.getFileName().equals(filename)) {
						duplication = true;
					}
				}
				if (!duplication) {
					return "permitUpload";
				} else {
					return "duplicationFileName";
				}
			} else {
				return "noAuthorized";
			}
		} else {
			return "errorParameter";
		}
	}

	@Override
	public String doUploadFile(HttpServletRequest request, MultipartFile file) {
		// TODO 自动生成的方法存根
		String account = (String) request.getSession().getAttribute("ACCOUNT");
		String folderId = request.getParameter("folderId");
		String filename = file.getOriginalFilename();
		if (folderId != null && folderId.length() > 0 && filename != null && filename.length() > 0) {
			if (ConfigureReader.instance(request).authorized(account, AccountAuth.UPLOAD_FILES)) {
				List<File> files = fm.queryByParentFolderId(folderId);
				boolean duplication = false;
				for (File f : files) {
					if (f.getFileName().equals(filename)) {
						duplication = true;
					}
				}
				if (!duplication) {
					String path = fbu.saveToFileBlocks(request, file);
					String fsize = fbu.getFileSize(file);
					if (path.equals("ERROR")) {
						return "uploaderror";
					} else {
						File f = new File();
						f.setFileId(UUID.randomUUID().toString());
						f.setFileCreator(account);
						f.setFileCreationDate(ServerTimeUtil.accurateToDay());
						f.setFileName(filename);
						f.setFileParentFolder(folderId);
						f.setFilePath(path);
						f.setFileSize(fsize);
						if (fm.insert(f) > 0) {
							return "uploadsuccess";
						} else {
							return "uploaderror";
						}
					}
				} else {
					return "uploaderror";
				}
			} else {
				return "uploaderror";
			}
		} else {
			return "uploaderror";
		}
	}

	@Override
	public String deleteFile(HttpServletRequest request) {
		// TODO 自动生成的方法存根
		String fileId = request.getParameter("fileId");
		String account = (String) request.getSession().getAttribute("ACCOUNT");
		if (ConfigureReader.instance(request).authorized(account, AccountAuth.DELETE_FILE_OR_FOLDER)) {
			if (fileId != null && fileId.length() > 0) {
				File file = fm.queryById(fileId);
				if (file != null) {
					String fileblocks = request.getServletContext().getRealPath("/fileblocks");
					if (fbu.deleteFromFileBlocks(fileblocks, file.getFilePath())) {
						if (fm.deleteById(fileId) > 0) {
							return "deleteFileSuccess";
						} else {
							return "cannotDeleteFile";
						}
					} else {
						return "cannotDeleteFile";
					}
				} else {
					return "errorParameter";
				}
			} else {
				return "errorParameter";
			}
		} else {
			return "noAuthorized";
		}
	}

	@Override
	public void doDownloadFile(HttpServletRequest request, HttpServletResponse response) {
		// TODO 自动生成的方法存根
		String account=(String) request.getSession().getAttribute("ACCOUNT");
		if(ConfigureReader.instance(request).authorized(account, AccountAuth.DOWNLOAD_FILES)) {
			String fileId = request.getParameter("fileId");
			if (fileId != null) {
				File f = fm.queryById(fileId);
				if (f != null) {
					String fileBlocks = request.getServletContext().getRealPath("/fileblocks");
					java.io.File fo = fbu.getFileFromBlocks(fileBlocks, f.getFilePath());
					try {
						FileInputStream fis = new FileInputStream(fo);
						response.setContentType("application/force-download");// 以下载方式接受输出流
						response.setHeader("Content-Length", "" + fo.length());// 告知浏览器文件总大小
						// 设置能够使用中文编码的文件名
						response.addHeader("Content-Disposition",
								"attachment;fileName=" + URLEncoder.encode(f.getFileName(), "UTF-8"));
						int buffersize = ConfigureReader.instance(request).getBuffSize();
						byte[] buffer = new byte[buffersize];// 设置缓存
						BufferedInputStream bis = new BufferedInputStream(fis);// 使用带缓存的输入流分节读取文件
						OutputStream os = response.getOutputStream();// 得到HTTP输出流
						int index = 0;
						// 分节进行输出，确保在下载大文件时不会导致内存溢出
						while ((index = bis.read(buffer)) != -1) {
							os.write(buffer, 0, index);
						}
						bis.close();
						fis.close();
					} catch (Exception e) {
						// TODO 自动生成的 catch 块
					}
				}
			}
		}
	}

	@Override
	public String doRenameFile(HttpServletRequest request) {
		// TODO 自动生成的方法存根
		String fileId = request.getParameter("fileId");
		String newFileName = request.getParameter("newFileName");
		String account = (String) request.getSession().getAttribute("ACCOUNT");
		if (ConfigureReader.instance(request).authorized(account, AccountAuth.RENAME_FILE_OR_FOLDER)) {
			if (fileId != null && fileId.length() > 0 && newFileName != null && newFileName.length() > 0) {
				if (TextFormateUtil.instance().matcherFileName(newFileName)) {
					File file = fm.queryById(fileId);
					if (file != null) {
						Map<String, String> map = new HashMap<>();
						map.put("fileId", fileId);
						map.put("newFileName", newFileName);
						if (fm.updateFileNameById(map) > 0) {
							return "renameFileSuccess";
						} else {
							return "cannotRenameFile";
						}
					} else {
						return "errorParameter";
					}
				} else {
					return "errorParameter";
				}
			} else {
				return "errorParameter";
			}
		} else {
			return "noAuthorized";
		}
	}

}
