package kohgylw.enumeration;

/**
 * 日志记录等级
 * <p>
 * 设定日志的记录等级
 * </p>
 * <ul>
 * <li>None 不进行日志记录</li>
 * <li>Runtime_Exception 只记录运行时的异常信息</li>
 * <li>Event 记录重要事件，包括异常信息、删除操作、下载操作、重命名操作和上传（创建）操作</li>
 * </ul>
 */
public enum LogLevel {
	None, Runtime_Exception, Event;
}
