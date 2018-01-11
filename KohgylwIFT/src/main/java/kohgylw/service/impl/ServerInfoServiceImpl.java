package kohgylw.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import kohgylw.service.ServerInfoService;

@Service
public class ServerInfoServiceImpl implements ServerInfoService{
	
	@Override
	public String getOSName() {
		// TODO 自动生成的方法存根
		return System.getProperty("os.name");
	}
	
	@Override
	public String getServerTime() {
		// TODO 自动生成的方法存根
		Date d=new Date();
		DateFormat df=new SimpleDateFormat("YYYY年MM月dd日 hh:mm");
		return df.format(d);
	}

}
