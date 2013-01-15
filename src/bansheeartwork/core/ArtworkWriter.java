package bansheeartwork.core;

import bansheeartwork.config.AppContext;
import bansheeartwork.filter.AudioFileFilter;
import bansheeartwork.filter.DirectoryFilter;
import java.io.File;
import java.util.ArrayList;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.images.Artwork;

/**
 *
 * @author Federico Schmidt
 */
public class ArtworkWriter extends Thread {
    
    private int writenFiles = 0;
    private ArrayList<String> failedFiles = new ArrayList<>();
    
    private ArtworkWriterObserver caller;
    
    public ArtworkWriter(ArtworkWriterObserver caller){
        this.caller = caller;
    }
    
    @Override
    public void run(){
        File mediaLibrary = AppContext.getInstance().getMediaLibrary();
        
        if(mediaLibrary.isDirectory()){
            this.processDirectory(mediaLibrary);
        } else {
            this.processDirectory(mediaLibrary.getParentFile());
        }
        
        caller.showFailedFiles(failedFiles);
        caller.showStatistics(writenFiles+failedFiles.size(), writenFiles, failedFiles.size());
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
                    writenFiles++;
                } catch (CannotWriteException ex) {
                    failedFiles.add(this.fileRelativePath(audioFile) + " (Reason: CannotWrite)");
                } catch (FieldDataInvalidException ex) {
                    failedFiles.add(this.fileRelativePath(audioFile) + " (Reason: FieldDataInvalid)");
                }
            }else{
                failedFiles.add(this.fileRelativePath(audioFile) + " (Reason: ArtworkNotFound)");
            }
        }
        
        for(File directory : dir.listFiles(DirectoryFilter.getInstance())){
            this.processDirectory(directory);
        }
    }
    
    private String fileRelativePath(AudioFile f){
        return f.getFile().getAbsolutePath().replaceFirst(AppContext.getInstance().getMediaLibrary().getAbsolutePath(), "");
    }
}
