package bansheeartwork;

import bansheeartwork.cli.CommandLineRun;
import bansheeartwork.config.AppContext;
import java.util.ArrayList;

public class BansheeArtwork {

    public static void main(String[] args) {
        /* If we have 0 arguments, we run the GUI.
         * Otherwise, we run the command line interface.
        */
        if(args.length != 0){
            
            ArrayList<String> arguments = new ArrayList<>();
            
            for(String arg : args){
                if(arg.startsWith("-")){
                    switch(arg.charAt(1)){
                        case 'o': AppContext.getInstance().setAllFilesInAFolderWithSameArtwork(true); break;
                        case 's': AppContext.getInstance().setSkipFilesWithArtwork(true); break;
                    }
                }else{
                    arguments.add(arg);
                }
            }
            CommandLineRun exec = new CommandLineRun(arguments);
            exec.run();
        }else{
            
            // GUI.
            
        }
    }
}
