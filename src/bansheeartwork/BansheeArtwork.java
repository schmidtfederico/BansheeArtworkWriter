package bansheeartwork;

import bansheeartwork.cli.CommandLineRun;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class BansheeArtwork {

    public static void main(String[] args) {
        /* If we have 0 arguments, we run the GUI.
         * Otherwise, we run the command line interface.
        */
        if(args.length != 0){
            
            ArrayList<String> arguments = new ArrayList<>();
            
            for(String arg : args){
                if(arg.startsWith("-")){
                    
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
