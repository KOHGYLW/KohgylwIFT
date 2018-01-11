package kohgylw.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kohgylw.util.FileBlockUtil;

@Controller
@RequestMapping("/errorController")
public class ErrorController {
	
	@Resource
	private FileBlockUtil fbu;
	
	@RequestMapping("/handleError.do")
	public String handleError(HttpServletRequest request) {
		fbu.checkFileBlocks(request);
		return "WEB-INF/error";
	}

}
