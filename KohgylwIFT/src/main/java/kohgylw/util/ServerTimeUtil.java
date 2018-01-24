package kohgylw.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 服务器时间获取工具
 * <p>用以获取服务器的实时时间，该工具类不提供实例，所有方法均为静态方法</p>
 * */
public class ServerTimeUtil {
	
	private ServerTimeUtil() {
		//none
	}
	
	/**
	 * 获取精确到秒的时间
	 * <p>时间格式为{YYYY年MM月dd日 hh:mm:ss}</p>
	 * @return String 时间信息
	 * */
	public static String accurateToSecond() {
		Date d=new Date();
		DateFormat df=new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
		return df.format(d);
	}
	
	/**
	 * 获取精确到分的时间
	 * <p>时间格式为{YYYY年MM月dd日 hh:mm}</p>
	 * @return String 时间信息
	 * */
	public static String accurateToMinute() {
		Date d=new Date();
		DateFormat df=new SimpleDateFormat("YYYY年MM月dd日 HH:mm");
		return df.format(d);
	}
	
	/**
	 * 获取精确到日的时间
	 * <p>时间格式为{YYYY年MM月dd日}</p>
	 * @return String 时间信息
	 * */
	public static String accurateToDay() {
		Date d=new Date();
		DateFormat df=new SimpleDateFormat("YYYY年MM月dd日");
		return df.format(d);
	}
	
	/**
	 * 生成日志名称专用日期格式
	 * <p>时间格式为{YYYY_MM_dd}</p>
	 * */
	public static String accurateToLogName() {
		Date d=new Date();
		DateFormat df=new SimpleDateFormat("YYYY_MM_dd");
		return df.format(d);
	}
}
