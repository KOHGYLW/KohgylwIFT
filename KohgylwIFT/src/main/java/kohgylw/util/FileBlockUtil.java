package kohgylw.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import kohgylw.mapper.FileMapper;

@Component
public class FileBlockUtil {

	@Resource
	private FileMapper fm;
	
	/**
	 * 将传入文件存入文件块
	 * <p>
	 * 将传入文件存入文件块，并使用指定方法命名，同时将其名称path返回
	 * </p>
	 * 
	 * @param request
	 *            HttpServletRequest 请求对象
	 * @param f
	 *            MultipartFile 上传文件对象
	 * @return String path 存储的块名
	 */
	public String saveToFileBlocks(HttpServletRequest request, MultipartFile f) {
		String fileBlocks = request.getServletContext().getRealPath("/fileblocks");
		String id = UUID.randomUUID().toString().replace("-", "");
		String path = "file_" + id + ".block";
		File file = new File(fileBlocks, path);
		try {
			f.transferTo(file);
			return path;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return "ERROR";
		}
	}

	/**
	 * 获取某一文件对象的体积并转化为MB单位，如果不足1MB则返回0
	 * 
	 * @param f
	 *            MultipartFile 需要获取大小的文件对象
	 * @return String 以MB为单位的大小
	 */
	public String getFileSize(MultipartFile f) {
		long size = f.getSize();
		int mb = (int) (size / 1024 / 1024);
		return "" + mb;
	}

	/**
	 * 从文件块中删除文件
	 * <p>
	 * 从文件块中彻底删除一个文件，并返回结果
	 * </p>
	 * 
	 * @param request
	 *            HttpServletRequest 请求对象
	 * @param path
	 *            String 文件块名称
	 * @return boolean 删除结果
	 */
	public boolean deleteFromFileBlocks(String fileblocks, String path) {
		File file = new File(fileblocks, path);
		if (file.exists() && file.isFile()) {
			return file.delete();
		} else {
			return false;
		}
	}

	/**
	 * 根据path从fileBlocks中取得文件并返回
	 * 
	 * @param fileBlocks
	 *            String 文件块位置
	 * @param path
	 *            String node中存入的path信息
	 * @return File
	 */
	public File getFileFromBlocks(String fileBlocks, String path) {
		File file = new File(fileBlocks, path);
		if (file.exists() && file.isFile()) {
			return file;
		} else {
			return null;
		}
	}

	/**
	 * 检查文件block与node的信息是否匹配，并对冗余信息进行清理
	 * <p>
	 * 自动对齐文件系统的node列表及block堆，如果出现引用失效的node或无法被引用的block，则将其清除
	 * </p>
	 * 
	 * @param c
	 *            ServletContext 上下文对象
	 * @return 无
	 */
	public void checkFileBlocks(ServletContext c) {
		String fileblocks = c.getRealPath("/fileblocks");
		Thread checkThread = new Thread(() -> {
			List<kohgylw.model.File> nodes = fm.queryAll();
			for (kohgylw.model.File node : nodes) {
				File block = new File(fileblocks, node.getFilePath());
				if (!block.exists()) {
					fm.deleteById(node.getFileId());
				}
			}
			File blocks = new File(fileblocks);
			String[] bn = blocks.list();
			for (String n : bn) {
				kohgylw.model.File node = fm.queryByPath(n);
				if (node == null) {
					File f = new File(fileblocks, n);
					f.delete();
				}
			}
		});
		checkThread.start();
	}
	
	/**
	 * 根据传入的id生成ZIP文件
	 * <p>
	 * 按照id列表代表的文件生成一个ZIP并放入临时文件夹temporaryfiles内。
	 * </p>
	 * 
	 * @param idList
	 *            List<String> 需要加入压缩文件中的文件id列表
	 * @param tfPath String 临时文件夹路径
	 * @param fileBlocks String 文件块路径
	 * @return String 生成压缩文件的临时文件名，如果生成失败则返回null
	 */
	public String createZip(List<String> idList,String tfPath,String fileBlocks) {
		String zipname="tf_"+UUID.randomUUID().toString()+".zip";
		File f=new File(tfPath,zipname);
		try {
			FileOutputStream fos=new FileOutputStream(f);
			ZipOutputStream zos=new ZipOutputStream(fos);
			for(String fid:idList) {
				kohgylw.model.File node=fm.queryById(fid);
				if(node!=null) {
					File block=new File(fileBlocks,node.getFilePath());
					FileInputStream fis=new FileInputStream(block);
					zos.putNextEntry(new ZipEntry(node.getFileName()));
					int count=0;
					byte[] buffer=new byte[4096];
					while((count=fis.read(buffer))!=-1) {
						zos.write(buffer, 0, count);
					}
					zos.closeEntry();
					fis.close();
				}
			}
			zos.close();
			return zipname;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}
	
}
