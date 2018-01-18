package kohgylw.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import kohgylw.service.AccountService;
import kohgylw.util.ConfigureReader;

@Service
public class AccountServiceImpl implements AccountService {

	@Override
	public String checkLoginRequest(HttpServletRequest request,HttpSession session) {
		// TODO 自动生成的方法存根
		String account = request.getParameter("accountid");
		String pwd = request.getParameter("accountpwd");
		ConfigureReader cr=ConfigureReader.instance(request);
		if(cr.foundAccount(account)) {
			if(cr.checkAccountPwd(account, pwd)) {
				session.setAttribute("ACCOUNT", account);
				return "permitlogin";
			}else {
				return "accountpwderror";
			}
		}else {
			return "accountnotfound";
		}
	}

	@Override
	public void logout(HttpSession session) {
		// TODO 自动生成的方法存根
		session.invalidate();
	}

}
