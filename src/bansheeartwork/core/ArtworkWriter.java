package bansheeartwork.core;

import bansheeartwork.config.AppContext;
import bansheeartwork.filter.AudioFileFilter;
import bansheeartwork.filter.DirectoryFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;

/**
 *
 * @author Federico Schmidt
 */
public class ArtworkWriter extends Thread {
    
    File bansheeCache;
    
    /**
     * Creates an instance of this class specifying the media library location.
     * @param mediaLibrary 
     */
    public ArtworkWriter(){
        
        /*String[] getUserHome = {"/bin/sh", "-c", "echo ~username"};
        try {
            Process p = Runtime.getRuntime().exec(getUserHome);
        } catch (IOException ex) {
            Logger.getLogger(ArtworkWriter.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
    @Override
    public void run(){
        File mediaLibrary = AppContext.getInstance().getMediaLibrary();
        
        if(mediaLibrary.isDirectory()){
            this.processDirectory(mediaLibrary);
        } else {
            this.processDirectory(mediaLibrary.getParentFile());
        }
    }
    
    private void processDirectory(File dir){
        ArrayList<AudioFile> audioFiles = AudioFileFilter.getAudioFiles(dir);
        
        Artwork artwork = null;
        
        for(AudioFile audioFile : audioFiles){
            if(AppContext.getInstance().allFilesInAFolderWithSameArtwork()){
                if(artwork == null){
                    artwork = ArtworkFinder.getArtwork(audioFile);
                }
            } else {
                artwork = ArtworkFinder.getArtwork(audioFile);
            }
            
            if (artwork != null) {
                try {
                    audioFile.getTag().setField(artwork);
                    audioFile.commit();
                } catch (CannotWriteException ex) {
                    Logger.getLogger(ArtworkWriter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FieldDataInvalidException ex) {
                    Logger.getLogger(ArtworkWriter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        for(File directory : dir.listFiles(DirectoryFilter.getInstance())){
            this.processDirectory(directory);
        }
    }
}
