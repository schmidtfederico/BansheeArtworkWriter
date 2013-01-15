package bansheeartwork.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;


/**
 *
 * @author Federico Schmidt
 */
public class AudioFileFilter implements FilenameFilter {
    
    private static AudioFileFilter instance = null;
    
    @Override
    public boolean accept(File dir, String name) {
        name = name.toLowerCase();
        
        if(name.endsWith(".flac")) return true;
        if(name.endsWith(".oog")) return true;
        if(name.endsWith(".wma")) return true;
        if(name.endsWith(".mp3")) return true;
        if(name.endsWith(".aac")) return true;
        
        return false;
    }

    public static AudioFileFilter getInstance(){
        if(instance == null)
            instance = new AudioFileFilter();
        return instance;
    }
    
    public static ArrayList<AudioFile> getAudioFiles(File directory) {
        ArrayList<AudioFile> afiles = new ArrayList<>();
        
        File filteredFiles[] = directory.listFiles(AudioFileFilter.getInstance());
        
        for(File f : filteredFiles){
            try {
                afiles.add(AudioFileIO.read(f));
            } catch (CannotReadException ex) {
                Logger.getLogger(AudioFileFilter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AudioFileFilter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TagException ex) {
                Logger.getLogger(AudioFileFilter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ReadOnlyFileException ex) {
                Logger.getLogger(AudioFileFilter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidAudioFrameException ex) {
                Logger.getLogger(AudioFileFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return afiles;
    }
    
}
