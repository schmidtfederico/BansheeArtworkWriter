package bansheeartwork.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Federico Schmidt
 */
public class DirectoryFilter implements FileFilter {

    private static DirectoryFilter instance = null;
    
    @Override
    public boolean accept(File f) {
        return f.isDirectory();
    }
    
    public static DirectoryFilter getInstance(){
        if(instance == null) instance = new DirectoryFilter();
        return instance;
    }
}
