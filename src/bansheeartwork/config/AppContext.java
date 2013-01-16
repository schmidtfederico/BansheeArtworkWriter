package bansheeartwork.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Federico Schmidt
 */
public class AppContext {
    static AppContext config = null;
    
    private boolean allFilesInAFolderWithSameArtwork = false;
    private File mediaLibrary = null;
    private File bansheeCache = null;
    private static File logFile = new File("artwork.log");
    
    public AppContext() {
        try {
            Logger rootLogger = Logger.getLogger("");
            
            FileHandler logHandler = new FileHandler(logFile.getAbsolutePath(),
                    2 * 1024 * 1024,
                    1, false);
            logHandler.setFormatter(new SimpleFormatter());
            logHandler.setLevel(Level.INFO);
            rootLogger.removeHandler(rootLogger.getHandlers()[0]);
            rootLogger.setLevel(Level.INFO);
            rootLogger.addHandler(logHandler);
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(AppContext.class.getName()).log(Level.SEVERE, "Couldn't redirect library's log.\nYou may see a lot of warning messages from the library.");
        }
    }
    
    public static AppContext getInstance(){
        if(config == null) config = new AppContext();
        return config;
    }

    /**
     * If true, for each folder in the music library, the artwork is determined
     * only once and with the first item of the folder and then applied to all
     * the music files in that folder.
     * 
     * Useful if the music library is ordered by album.
     */
    public boolean allFilesInAFolderWithSameArtwork() {
        return allFilesInAFolderWithSameArtwork;
    }

    /**
     * Sets whether all files inside a folder should be tagged with the same artwork
     * (the artwork of the first file) or the artwork should be determined for each file.
     * 
     * This is useful (and speed execution up) if the music library is ordered by album.
     * @param b
     */
    public void setAllFilesInAFolderWithSameArtwork(boolean b) {
        this.allFilesInAFolderWithSameArtwork = b;
    }

    public File getMediaLibrary() {
        return mediaLibrary;
    }

    public void setMediaLibrary(File mediaLibrary) {
        this.mediaLibrary = mediaLibrary;
    }

    public File getBansheeCache() {
        return bansheeCache;
    }

    public void setBansheeCache(File bansheeCache) {
        this.bansheeCache = bansheeCache;
    }
}
