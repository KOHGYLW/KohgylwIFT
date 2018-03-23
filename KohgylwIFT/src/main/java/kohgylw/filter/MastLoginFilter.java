package kohgylw.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kohgylw.util.ConfigureReader;

/**
 * 
 * <h2>登录检查过滤器</h2>
 * <p>
 * 该过滤器用于在用户设定必须登陆后才可访问主页面（文件列表时）进行登录判定。
 * </p>
 * 
 * @author 青阳龙野(kohgylw)
 * @version 1.0
 */
public class MastLoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO 自动生成的方法存根

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO 自动生成的方法存根
		ConfigureReader cr = ConfigureReader.instance((HttpServletRequest) request);
		boolean s = cr.mustLogin();
		HttpServletRequest hsq = (HttpServletRequest) request;
		HttpServletResponse hsr = (HttpServletResponse) response;
		String url = hsq.getServletPath();
		HttpSession session = hsq.getSession();
		if (url.equals("//login.jsp") || url.equals("/login.jsp")) {
			String account = (String) session.getAttribute("ACCOUNT");
			if (cr.foundAccount(account)) {
				request.getRequestDispatcher("/home.jsp").forward(request, response);
			} else {
				chain.doFilter(request, response);
			}
		} else {
			if (s) {
				if (url.endsWith(".jsp") || url.endsWith(".do")) {
					if (session.getAttribute("ACCOUNT") != null) {
						String account = (String) session.getAttribute("ACCOUNT");
						if (cr.foundAccount(account)) {
							chain.doFilter(request, response);
						} else {
							hsq.getRequestDispatcher("/login.jsp").forward(request, response);
						}
					} else {
						hsq.getRequestDispatcher("/login.jsp").forward(request, response);
					}
				} else if (url.endsWith(".ajax")) {
					if (url.equals("/homeController/doLogin.ajax")) {
						chain.doFilter(request, response);
					} else {
						if (session.getAttribute("ACCOUNT") != null) {
							String account = (String) session.getAttribute("ACCOUNT");
							if (cr.foundAccount(account)) {
								chain.doFilter(request, response);
							} else {
								hsr.setCharacterEncoding("UTF-8");
								PrintWriter pw = hsr.getWriter();
								pw.print("mustLogin");
								pw.flush();
							}
						} else {
							hsr.setCharacterEncoding("UTF-8");
							PrintWriter pw = hsr.getWriter();
							pw.print("mustLogin");
							pw.flush();
						}
					}
				} else {
					chain.doFilter(request, response);
				}
			}else {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根

	}

}
