package bansheeartwork.config;

import java.io.File;

/**
 *
 * @author Federico Schmidt
 */
public class AppContext {
    static AppContext config = null;
    
    private boolean allFilesInAFolderWithSameArtwork = false;
    private File mediaLibrary = null;
    private File bansheeCache = null;
    
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
