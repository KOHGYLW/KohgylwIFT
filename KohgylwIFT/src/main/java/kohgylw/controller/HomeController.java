package kohgylw.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kohgylw.mapper.FolderMapper;
import kohgylw.service.AccountService;
import kohgylw.service.FileService;
import kohgylw.service.FolderService;
import kohgylw.service.FolderViewService;
import kohgylw.service.ServerInfoService;

@Controller
@RequestMapping("/homeController")
public class HomeController {
	
	@Resource
	private ServerInfoService si;
	
	@Resource
	private AccountService as;
	
	@Resource
	private FolderViewService fvs;
	
	@Resource
	private FolderService fs;
	
	@Resource
	private FileService fis;
	
	//获取服务器操作系统
	@RequestMapping("/getServerOS.ajax")
	public @ResponseBody String getServerOS(){
		return si.getOSName();
	}
	
	//执行登录操作判定
	@RequestMapping("/doLogin.ajax")
	public @ResponseBody String doLogin(HttpServletRequest request,HttpSession session) {
		return as.checkLoginRequest(request,session);
	}
	
	//获取指定文件夹视图的JSON数据
	@RequestMapping(value="/getFolderView.ajax",produces = "text/html; charset=utf-8")
	public @ResponseBody String getFolderView(String fid,HttpSession session,HttpServletRequest request) {
		String re=fvs.getFolderViewToJson(fid,session,request);
		return re;
	}
	
	//执行注销操作
	@RequestMapping("/doLogout.do")
	public String doLogout(HttpSession session) {
		as.logout(session);
		return "redirect:/home.jsp";
	}
	
	//新建文件夹
	@RequestMapping("/newFolder.ajax")
	public @ResponseBody String newFolder(HttpServletRequest request) {
		return fs.newFolder(request);
	}
	
	//删除文件夹
	@RequestMapping("/deleteFolder.ajax")
	public @ResponseBody String deleteFolder(HttpServletRequest request) {
		return fs.deleteFolder(request);
	}
	
	//重命名文件夹
	@RequestMapping("/renameFolder.ajax")
	public @ResponseBody String renameFolder(HttpServletRequest request) {
		return fs.renameFolder(request);
	}
	
	//上传文件
	@RequestMapping(value="/douploadFile.ajax",produces = "text/html; charset=utf-8")
	public @ResponseBody String douploadFile(HttpServletRequest request,MultipartFile file) {
		return fis.doUploadFile(request, file);
	}
	
	//检查是否能够上传文件
	@RequestMapping(value="/checkUploadFile.ajax",produces = "text/html; charset=utf-8")
	public @ResponseBody String checkUploadFile(HttpServletRequest request) {
		return fis.checkUploadFile(request);
	}
	
	//删除文件
	@RequestMapping("/deleteFile.ajax")
	public @ResponseBody String deleteFile(HttpServletRequest request) {
		return fis.deleteFile(request);
	}
	
	//下载文件
	@RequestMapping("/downloadFile.do")
	public void downloadFile(HttpServletRequest request,HttpServletResponse response){
		fis.doDownloadFile(request,response);
	}
	
	//修改文件名
	@RequestMapping("/renameFile.ajax")
	public @ResponseBody String renameFile(HttpServletRequest request) {
		return fis.doRenameFile(request);
	}
}
