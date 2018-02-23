package kohgylw.service;

import javax.servlet.http.HttpServletRequest;

import kohgylw.model.File;

public interface ShowPictureService {
	
	/**
	 * 根据请求资源ID获取到相应的图片节点对象
	 * <p>该方法对查看图片请求进行处理，验证权限后查找图片信息，并返回。如果未被授权或请求出错，返回null。</p>
	 * @param request 请求对象
	 * @return File 图片资源的节点对象，如果请求失败，则返回null
	 * */
	File foundPicture(HttpServletRequest request);
}
