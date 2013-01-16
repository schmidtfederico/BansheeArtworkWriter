package bansheeartwork;

import bansheeartwork.cli.CommandLineRun;
import bansheeartwork.config.AppContext;
import java.util.ArrayList;

/**
 * A command line tool for writing Banshee's cached artworks to the music library files.
 * 
 * @author Federico Schmidt
 * @mail schmidt.fdr@gmail.com
 */
public class BansheeArtwork {

    public static void main(String[] args) {
        if(args.length > 0){
            ArrayList<String> arguments = new ArrayList<>();
            
            for(String arg : args){
                if(arg.startsWith("-")){
                    for(int i = 1; i < arg.length(); i++) {
                        switch (arg.charAt(i)) {
                            case 'o':
                                AppContext.getInstance().setAllFilesInAFolderWithSameArtwork(true);
                                break;
                            case 's':
                                AppContext.getInstance().setSkipFilesWithArtwork(true);
                                break;
                        }
                    }
                }else{
                    arguments.add(arg);
                }
            }
            CommandLineRun exec = new CommandLineRun(arguments);
            exec.run();
        }else{
            System.out.println("Usage: java -jar BansheeArtwork.jar [-options] mediaLibraryPath [bansheeCachePath]");
        }
    }
}
