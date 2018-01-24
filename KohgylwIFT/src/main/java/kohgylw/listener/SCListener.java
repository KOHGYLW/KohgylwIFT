package kohgylw.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import kohgylw.util.FileBlockUtil;

/**
 * 该监听器用于在服务器启动时进行文件节点检查，自动对齐节点信息与文件块并清理无用的块或节点
 * <p>将整个文件系统的基本组成部分——文件块与文件节点信息进行对齐，清理无效的节点信息与文件块，确保文件系统稳定。</p>
 * */
public class SCListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO 自动生成的方法存根
		ApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		FileBlockUtil fbu=context.getBean(FileBlockUtil.class);
		fbu.checkFileBlocks(sce.getServletContext());
		System.out.println("KohgylwIFT:SC complete");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO 自动生成的方法存根
		
	}

}
