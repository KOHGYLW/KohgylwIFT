package kohgylw.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface AccountService {
	
	/**
	 * 审查登录请求
	 * <p>根据登录请求的参数进行身份判定，并返回识别码，如果验证通过，会在Session中存储用户账户</p>
	 * @param request HttpServletRequest 请求参数
	 * @return String 
	 * <ul>
	 * <li>permitlogin=核对正确，允许登录</li>
	 * <li>accountpwderror=密码错误</li>
	 * <li>accountnotfound=账户不存在</li>
	 * </ul>
	 */
	String checkLoginRequest(HttpServletRequest request,HttpSession session);
	
	/**
	 * 进行用户注销操作
	 * <p>令现有的session失效，从而清除所有用户的信息</p>
	 * @param session HttpSession 需要清除的Session
	 * @return void
	 * */
	void logout(HttpSession session);

}
