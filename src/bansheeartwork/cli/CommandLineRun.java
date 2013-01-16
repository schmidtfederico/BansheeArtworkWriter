package bansheeartwork.cli;

import bansheeartwork.config.AppContext;
import bansheeartwork.config.Defaults;
import bansheeartwork.core.ArtworkWriter;
import bansheeartwork.core.ArtworkWriterObserver;
import java.io.File;
import java.util.ArrayList;

/**
 * @author Federico Schmidt
 */
public class CommandLineRun implements ArtworkWriterObserver {
    
    public boolean canExecute = false;
    long startTime;
    
    public CommandLineRun(ArrayList<String> arguments){
        startTime = System.currentTimeMillis();
        
        if(arguments.size() > 0){
            File mediaLibrary = new File(arguments.get(0));
            if(!mediaLibrary.exists()){
                System.err.println("Media library path does not exist.");
                return;
            }
            if(!mediaLibrary.canRead()){
                System.err.println("Can't read media library, not enough privilege.");
                return;
            }
            if(!mediaLibrary.canWrite()){
                System.err.println("Can't write in media library, not enough privilege.");
                return;
            }
            AppContext.getInstance().setMediaLibrary(mediaLibrary);
        }else{
            System.out.println("Usage: java -jar BansheeArtwork.jar [-options] mediaLibraryPath [bansheeCachePath]");
            return;
        }
        
        if(arguments.size() > 1){
            File bansheeCache = new File(arguments.get(1));
            
            if(!bansheeCache.exists()){
                System.err.println("Banshee's cache path does not exist.");
                return;
            }
            if(!bansheeCache.canRead()){
                System.err.println("Can't read Banshee's cache, not enough privilege.");
                return;
            }
            AppContext.getInstance().setBansheeCache(bansheeCache);
        }else{
            File bansheeCache = Defaults.getBansheeCache();
            if(bansheeCache == null){
                System.err.println("Couldn't determine Banshee's cache folder, please specify manually.");
                return;
            }
            AppContext.getInstance().setBansheeCache(bansheeCache);
        }
        
        this.canExecute = true;
    }
    
    public void run(){
        if(this.canExecute){
            System.out.println("Media Library Path: " + AppContext.getInstance().getMediaLibrary().getAbsolutePath());
            System.out.println("Banshee Cache Path: " + AppContext.getInstance().getBansheeCache().getAbsolutePath());
            System.out.println("========================= Start ==========================");
            ArtworkWriter writer = new ArtworkWriter(this);
            writer.start();
        }
    }

    @Override
    public void updateStatus(String s) {
        System.out.println(s);
    }

    @Override
    public void showStatistics(int totalFiles, int artworksWritten, int failedFiles) {
        System.out.println("========================== End ===========================");
        System.out.println("Finished.");
        System.out.println("Artworks written: " + artworksWritten + ". Fails: " + failedFiles + ". (Total: " + totalFiles + ").");
        System.out.println("Total time: " + (System.currentTimeMillis()-this.startTime)/1000 + " seconds.");
    }

    @Override
    public void showFailedFiles(ArrayList<String> failedFiles) {
        if(failedFiles.isEmpty()) return;
        System.out.println("The following files artwork was not written:");
        for(String s : failedFiles){
            System.out.println("  * " + s);
        }
    }
}
