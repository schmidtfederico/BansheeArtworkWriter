package bansheeartwork.config;

import java.io.File;

/**
 *
 * @author Federico Schmidt
 */
public class Defaults {

    public static File getBansheeCache(){
        File cache = new File(Defaults.getHomeFolder().getAbsolutePath() + "/.cache/media-art");
        
        if(cache.exists() && cache.canRead()){
            return cache;
        }
        return null;
    }
    
    public static File getHomeFolder(){
        File f = new File(System.getProperty("user.home"));
        if(f.exists() && f.canRead()){
            return f;
        }
        return null;
    }
}
