package kohgylw.mapper;

import java.util.List;
import java.util.Map;

import kohgylw.model.Folder;

public interface FolderMapper {
	
	Folder queryById(String fid);
	
	List<Folder> queryByParentId(String pid);
	
	Folder queryByParentIdAndFolderName(Map<String, String> map);
	
	int insertNewFolder(Folder f);
	
	int deleteById(String folderId);
	
	int updateFolderNameById(Map<String, String> map);

}
