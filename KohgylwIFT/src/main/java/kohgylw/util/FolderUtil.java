package kohgylw.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import kohgylw.mapper.FileMapper;
import kohgylw.mapper.FolderMapper;
import kohgylw.model.File;
import kohgylw.model.Folder;

/**
 * 系统文件夹结构解析工具
 * <p>
 * 本工具提供基于本系统文件结构的一些解析操作，例如获取某个目录的父级目录列表等
 * </p>
 */
@Component
public class FolderUtil {

	@Resource
	private FolderMapper fm;
	
	@Resource
	private FileMapper fim;
	
	@Resource
	private FileBlockUtil fbu;

	/**
	 * 获取文件夹的父级目录列表
	 * <p>
	 * 根据文件夹id获取其父级目录，当无父级文件夹或找不到文件夹时，返回空List
	 * </p>
	 * @param fid String 文件夹id
	 * @return 上级文件夹列表，一直到ROOT节点
	 */
	public List<Folder> getParentList(String fid) {
		Folder f = fm.queryById(fid);//获取当前文件夹
		List<Folder> folderList = new ArrayList<>();
		if (f != null) {
			while (!f.getFolderParent().equals("null")){
				f = fm.queryById(f.getFolderParent());//从本级文件夹向上，依次查询其父级文件夹
				folderList.add(f);
			}
		} else {

		}
		Collections.reverse(folderList);//进行倒序使之从根节点向下排列
		return folderList;
	}
	
	/**
	 * 删除某一文件夹下所有子文件夹及其内容
	 * <p>该方法使用迭代的方式，将传入ID文件夹下的所有子文件夹及文件全部移除。为了不阻塞主线程，该操作将开启新线程执行</p>
	 * @param folderId String 需要删除所有子文件件及其内容的文件夹ID
	 * @return 无
	 * */
	public int deleteAllChildFolder(HttpServletRequest request,String folderId) {
		String fileblocks=request.getServletContext().getRealPath("/fileblocks");
		Thread deleteChildFolderThread=new Thread(()->{
			iterationDeleteFolder(fileblocks,folderId);
		});
		deleteChildFolderThread.start();
		return fm.deleteById(folderId);
	}
	
	private void iterationDeleteFolder(String fileblocks,String folderId) {
		List<Folder> cf=fm.queryByParentId(folderId);
		if(cf.size()>0) {
			for(Folder f:cf) {
				iterationDeleteFolder(fileblocks,f.getFolderId());
			}
		}
		List<File> files=fim.queryByParentFolderId(folderId);
		if(files.size()>0) {
			fim.deleteByParentFolderId(folderId);
			for(File f:files) {
				fbu.deleteFromFileBlocks(fileblocks, f.getFilePath());
			}
		}
		fm.deleteById(folderId);
	}

}
