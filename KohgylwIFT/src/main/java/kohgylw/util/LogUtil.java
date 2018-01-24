package kohgylw.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

/**
 * 日志记录工具，主要负责将各种日志写入到文件中
 * <p>
 * 日志的默认写入位置为项目路径下的log文件夹，日志文件的名称为YYYY_MM_dd.klog
 * </p>
 * <h3>日志文件格式说明：</h3>
 * <p>
 * 第一段：日志记录时间;第二段：日志类型;第三段：日志内容
 * <p>
 */
@Component
public class LogUtil {

	public void writeException(Exception e, ServletContext c) {
		String log = c.getRealPath("/log");
		String t = ServerTimeUtil.accurateToLogName();
		File l=new File(log);
		if(!l.exists()) {
			l.mkdir();
		}else {
			if(!l.isDirectory()) {
				l.delete();
				l.mkdir();
			}
		}
		File f = new File(log, t + ".klog");
		FileWriter fw = null;
		if (f.exists()) {
			try {
				fw = new FileWriter(f, true);
				fw.write("\r\n\r\nTIME:\r\n" + ServerTimeUtil.accurateToSecond() + "\r\nSTYLE:\r\nException\r\nCONTENT:\r\n" + e.getMessage());
				fw.close();
			} catch (Exception e1) {

			}
		} else {
			try {
				fw = new FileWriter(f, false);
				fw.write("TIME:\r\n" + ServerTimeUtil.accurateToSecond() + "\r\nSTYLE:\r\nException\r\nCONTENT:\r\n" + e.getMessage());
				fw.close();
			} catch (IOException e1) {
			}
		}
	}

}
