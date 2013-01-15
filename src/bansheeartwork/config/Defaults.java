package bansheeartwork.config;

import bansheeartwork.core.ArtworkWriter;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
