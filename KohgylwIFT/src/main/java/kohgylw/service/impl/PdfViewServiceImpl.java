package kohgylw.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import kohgylw.enumeration.AccountAuth;
import kohgylw.mapper.FileMapper;
import kohgylw.model.File;
import kohgylw.service.PdfViewService;
import kohgylw.util.ConfigureReader;

@Service
public class PdfViewServiceImpl implements PdfViewService {
	
	@Resource
	private FileMapper fm;

	@Override
	public File foundPdf(HttpServletRequest request) {
		// TODO 自动生成的方法存根
		String fileId=request.getParameter("fileId");
		if(fileId!=null&&fileId.length()>0) {
			File f=fm.queryById(fileId);
			if(f!=null) {
				String account=(String) request.getSession().getAttribute("ACCOUNT");
				if(ConfigureReader.instance(request).authorized(account, AccountAuth.DOWNLOAD_FILES)) {
					String fileName=f.getFileName();
					String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if(suffix.equals("pdf")) {
						return f;
					}
				}
			}
		}
		return null;
	}

}
