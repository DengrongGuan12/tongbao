package dao.impl;

import dao.FileDao;
import model.File;
import org.springframework.stereotype.Repository;

/**
 * Created by cg on 2016/3/5.
 */
@Repository
public class FileDaoImpl extends BaseDaoImpl implements FileDao {
    public boolean uploadFile(File file) {
        try {
            super.save(file);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFile(int id) {
        try {
            super.delete(File.class,id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFile(File file) {
        try {
            super.update(file);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public File getFileById(int id) {
        try {
            File file = (File)super.load(File.class, id);
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
