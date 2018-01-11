package kohgylw.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import kohgylw.enumeration.AccountAuth;
import kohgylw.mapper.FileMapper;
import kohgylw.mapper.FolderMapper;
import kohgylw.pojo.FolderView;
import kohgylw.service.FolderViewService;
import kohgylw.util.ConfigureReader;
import kohgylw.util.FolderUtil;
import kohgylw.util.ServerTimeUtil;

@Service
public class FolderViewServiceImpl implements FolderViewService {
	
	@Resource
	private FolderUtil fu;
	
	@Resource
	private FolderMapper fm;
	
	@Resource
	private FileMapper flm;

	@Override
	public String getFolderViewToJson(String fid,HttpSession session,HttpServletRequest request) {
		// TODO 自动生成的方法存根
		Gson g=new Gson();
		ConfigureReader cr=ConfigureReader.instance(request);
		FolderView fv=new FolderView();
		fv.setFolder(fm.queryById(fid));//获取本文件夹信息
		fv.setParentList(fu.getParentList(fid));//获取其父文件夹List
		fv.setFolderList(fm.queryByParentId(fid));//获取其下的文件夹列表
		fv.setFileList(flm.queryByParentFolderId(fid));//获取其下的文件列表
		String account=(String) session.getAttribute("ACCOUNT");
		//设置账户信息
		if(account!=null) {
			fv.setAccount(account);
			List<String> authList=new ArrayList<>();
			if(cr.authorized(account, AccountAuth.UPLOAD_FILES)) {
				authList.add("U");
			}
			if(cr.authorized(account, AccountAuth.CREATE_NEW_FOLDER)) {
				authList.add("C");
			}
			if(cr.authorized(account, AccountAuth.DELETE_FILE_OR_FOLDER)) {
				authList.add("D");
			}
			if(cr.authorized(account, AccountAuth.RENAME_FILE_OR_FOLDER)) {
				authList.add("R");
			}
			fv.setAuthList(authList);
		}else {
			
		}
		fv.setPublishTime(ServerTimeUtil.accurateToMinute());
		return g.toJson(fv);
	}

}
