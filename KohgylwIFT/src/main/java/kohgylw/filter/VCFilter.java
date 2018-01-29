package kohgylw.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import kohgylw.enumeration.AccountAuth;
import kohgylw.util.ConfigureReader;

/**
 * 请求合法性检查过滤器
 * <p>该过滤器仅允许合法的请求通过，从而避免外部用户直接查看敏感文件例如项目配置和错误日志等。</p>
 * */
public class VCFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO 自动生成的方法存根
		HttpServletRequest hsr=(HttpServletRequest) request;
		String url=hsr.getServletPath();
		//阻止对敏感访问路径的访问
		if(url.startsWith("/etc/")||url.startsWith("//etc/")) {
			hsr.getRequestDispatcher("/errorController/pageNotFound.do").forward(request, response);
		}else if(url.startsWith("/fileblocks/")||url.startsWith("//fileblocks/")) {
			String account=(String) hsr.getSession().getAttribute("ACCOUNT");
			//对于文件读取请求进行权限检查，避免未授权的访问进入
			if(ConfigureReader.instance(hsr).authorized(account, AccountAuth.DOWNLOAD_FILES)) {
				chain.doFilter(request, response);
			}else {
				hsr.getRequestDispatcher("/errorController/pageNotFound.do").forward(request, response);
			}
		}else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}

}
