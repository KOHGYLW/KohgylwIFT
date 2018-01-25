package kohgylw.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kohgylw.util.FileBlockUtil;
import kohgylw.util.LogUtil;

@ControllerAdvice
@RequestMapping("/errorController")
public class ErrorController {

	@Resource
	private FileBlockUtil fbu;
	@Resource
	private LogUtil lu;

	@RequestMapping("/pageNotFound.do")
	public String handleError(HttpServletRequest request) {
		return "WEB-INF/error";
	}

	// 全局异常处理
	@ExceptionHandler(Exception.class)
	public @ResponseBody String handleException(HttpServletRequest request, Exception e) {
		lu.writeException(e);
		fbu.checkFileBlocks(request.getServletContext());
		return "handleException";
	}

}
