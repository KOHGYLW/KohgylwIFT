package kohgylw.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kohgylw.model.File;
import kohgylw.service.AccountService;
import kohgylw.service.FileService;
import kohgylw.service.FolderService;
import kohgylw.service.FolderViewService;
import kohgylw.service.PdfViewService;
import kohgylw.service.PlayVideoService;
import kohgylw.service.ServerInfoService;
import kohgylw.service.ShowPictureService;

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
	
	@Resource
	private PlayVideoService pvs;
	
	@Resource
	private PdfViewService pdvs;
	
	@Resource
	private ShowPictureService sps;
	
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
	
	//播放视频
	@RequestMapping("/playVideo.do")
	public String playVideo(HttpServletRequest request) {
		File f=pvs.foundVideo(request);
		if(f!=null) {
			request.setAttribute("video", f);
			return "WEB-INF/video";
		}else {
			return "WEB-INF/error";
		}
	}
	
	//预览PDF
	@RequestMapping("/pdfView.do")
	public String pdfView(HttpServletRequest request) {
		File f=pdvs.foundPdf(request);
		if(f!=null) {
			return "redirect:/pdfview/web/viewer.jsp?file="+request.getContextPath()+"/fileblocks/"+f.getFilePath();
		}else {
			return "WEB-INF/error";
		}
	}
	
	//查看图片
	@RequestMapping("/showPicture.do")
	public String showPicture(HttpServletRequest request) {
		File f=sps.foundPicture(request);
		if(f!=null) {
			request.setAttribute("picture", f);
			return "WEB-INF/picture";
		}else {
			return "WEB-INF/error";
		}
	}
	
	//删除所有被选中的文件
	@RequestMapping("/deleteCheckedFiles.ajax")
	public @ResponseBody String deleteCheckedFiles(HttpServletRequest request) {
		return fis.deleteCheckedFiles(request);
	}
	
	@RequestMapping("/downloadCheckedFiles.do")
	public void downloadCheckedFiles(HttpServletRequest request,HttpServletResponse response) {
		fis.downloadCheckedFiles(request, response);
	}
}
