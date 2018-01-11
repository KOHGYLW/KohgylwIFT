package kohgylw.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface FolderViewService {
	
	/**
	 * 根据文件夹id获取其文件夹视图的JSON数据
	 * <p>由文件夹id得到该文件夹的视图信息，封装为FolderView对象，并将其JSON化</p>
	 * @param fid String 需要获取视图的文件夹id
	 * @return String 该文件夹视图的JSON
	 * */
	String getFolderViewToJson(String fid,HttpSession session,HttpServletRequest request);

}
