package kohgylw.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import kohgylw.enumeration.AccountAuth;
import kohgylw.mapper.FolderMapper;
import kohgylw.model.Folder;
import kohgylw.service.FolderService;
import kohgylw.util.ConfigureReader;
import kohgylw.util.FolderUtil;
import kohgylw.util.ServerTimeUtil;
import kohgylw.util.TextFormateUtil;

@Service
public class FolderServiceImpl implements FolderService {

	@Resource
	private FolderMapper fm;

	@Resource
	private FolderUtil fu;

	@Override
	public String newFolder(HttpServletRequest request) {
		// TODO 自动生成的方法存根
		String parentId = request.getParameter("parentId");
		String folderName = request.getParameter("folderName");
		String account = (String) request.getSession().getAttribute("ACCOUNT");
		if (account != null) {
			if (ConfigureReader.instance(request).authorized(account, AccountAuth.CREATE_NEW_FOLDER)) {
				if (parentId != null && folderName != null && parentId.length() > 0 && folderName.length() > 0) {
					if (TextFormateUtil.instance().matcherFolderName(folderName)) {
						Folder parentFolder = fm.queryById(parentId);
						if (parentFolder != null) {
							Map<String, String> map = new HashMap<>();
							map.put("parentId", parentId);
							map.put("folderName", folderName);
							Folder f = fm.queryByParentIdAndFolderName(map);
							if (f != null) {
								return "folderAlreadyExist";
							} else {
								f = new Folder();
								f.setFolderId(UUID.randomUUID().toString());
								f.setFolderName(folderName);
								f.setFolderCreationDate(ServerTimeUtil.accurateToDay());
								f.setFolderCreator(account);
								f.setFolderParent(parentId);
								int i = fm.insertNewFolder(f);
								if (i > 0) {
									return "createFolderSuccess";
								} else {
									return "cannotCreateFolder";
								}
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
		} else {
			return "noAccount";
		}
	}

	@Override
	public String deleteFolder(HttpServletRequest request) {
		// TODO 自动生成的方法存根
		String folderId = request.getParameter("folderId");
		String account = (String) request.getSession().getAttribute("ACCOUNT");
		if (account != null) {
			if (ConfigureReader.instance(request).authorized(account, AccountAuth.DELETE_FILE_OR_FOLDER)) {
				if (folderId != null && folderId.length() > 0) {
					Folder folder = fm.queryById(folderId);
					if (folder != null) {
						if (fu.deleteAllChildFolder(request,folderId) > 0) {
							return "deleteFolderSuccess";
						} else {
							return "cannotDeleteFolder";
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
		} else {
			return "noAccount";
		}
	}

	@Override
	public String renameFolder(HttpServletRequest request) {
		// TODO 自动生成的方法存根
		String folderId = request.getParameter("folderId");
		String newName = request.getParameter("newName");
		String account = (String) request.getSession().getAttribute("ACCOUNT");
		if (account != null) {
			if (ConfigureReader.instance(request).authorized(account, AccountAuth.RENAME_FILE_OR_FOLDER)) {
				if (folderId != null && folderId.length() > 0 && newName != null && newName.length() > 0) {
					if (TextFormateUtil.instance().matcherFolderName(newName)) {
						Folder folder = fm.queryById(folderId);
						if (folder != null) {
							Map<String, String> map=new HashMap<>();
							map.put("folderId", folderId);
							map.put("newName", newName);
							if (fm.updateFolderNameById(map) > 0) {
								return "renameFolderSuccess";
							} else {
								return "cannotRenameFolder";
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
		} else {
			return "noAccount";
		}
	}
}
