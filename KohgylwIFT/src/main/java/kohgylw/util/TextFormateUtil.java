package kohgylw.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFormateUtil {
	
	private static TextFormateUtil tfu=new TextFormateUtil();
	
	private TextFormateUtil() {
		
	}
	
	public static TextFormateUtil instance() {
		return tfu;
	}
	
	public boolean matcherFolderName(String folderName) {
		Pattern p=Pattern.compile("^[0-9a-zA-Z_\u4E00-\u9FFF]+$");
		Matcher m=p.matcher(folderName);
		return m.matches();
	}
	
	public boolean matcherFileName(String fileName) {
		Pattern p=Pattern.compile("[|\\/*<> \"]+");
		Matcher m=p.matcher(fileName);
		return !m.find();
	}

}
