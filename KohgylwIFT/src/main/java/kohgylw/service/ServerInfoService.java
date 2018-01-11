package kohgylw.service;

public interface ServerInfoService {
	
	/**
	 * 获取服务器操作系统名
	 * <p>用于获取服务器操作系统的类型名称，它来源于System的配置信息</p>
	 * @param null
	 * @return String 操作系统名
	 * 
	 * */
	String getOSName();
	
	/**
	 * 获取服务器时间
	 * <p>用于获取服务器的当前时间，它由Date类自动获取并封装为 YYYY年MM月dd hh:mm 的格式</p>
	 * @param 无
	 * @return String 服务器当前时间
	 * */
	String getServerTime();

}
