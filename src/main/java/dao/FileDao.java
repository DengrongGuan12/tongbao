package dao;

import model.File;

/**
 * Created by cg on 2016/3/5.
 */
public interface FileDao {
    public boolean uploadFile(File file);
    public boolean removeFile(int id);
    public boolean updateFile(File file);
    public File getFileById(int id);
}
