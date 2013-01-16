BansheeArtworkWriter
====================

A command line tool for writing Banshee's cached artworks to the music library files.

Based on <a href="http://www.jthink.net/jaudiotagger/examples.jsp">JAudiotagger</a>, it supports most audio formats.

## Usage

**```java -jar BansheeArtwork.java [-options] mediaLibraryPath [bansheeCachePath]```**

Parameters within brackets are optional.

If not specified, Banshee's Cache Path is set to ```~/.cache/media-art```

Where options can be:
* **o**: stands for organized, you should activate this flag if your music library folders are organized *by album*.
        When activated, for each folder that's processed the artwork lookup is made once and the same artwork is applied
        to all the music files contained in that folder. This speeds up the execution.
* **s**: stands for skip files with artwork.

### Example

The 90% case scenario: you have your music library in ```/home/youruser/Music``` and Banshee's cache in the default place, plus, you've just downloaded this repo in a .zip and you have it in ```/home/youruser/Downloads```.

1. Unzip the file (should be called something like: "BansheeArtworkWriter-master.zip").
2. Open a terminal (Ctrl+Alt+T)
3. Type (or copy) the following command (assuming you have Java installed):
    ```java -jar ~/Downloads/BansheeArtworkWriter-master/bin/BansheeArtwork.jar -s ~/Music```<br><br>
    Or if you have your music library organized by album:
    ```java -jar ~/Downloads/BansheeArtworkWriter-master/bin/BansheeArtwork.jar -os ~/Music```

## Execution

Once the program starts it will print the folder it's working on for you to keep track of progress.

As the program ends, you will see the list of files that the program failed to write the artwork to (if any) and the amount to files processed.

### Important Note
The files listed in the "artwork not written" list may be false negatives, meaning that maybe the library wrote the artwork fine anyway.
The best way to check is run again the program with the "-s" parameter, this will take just a few seconds if artworks were written or fail again if they were not, then you can be sure they don't have an artwork.

Along with console debugging, there's JAudiotagger debugging and it's done to a file named ```artwork.log```, placed in the directory of execution.
Usually you will see a lot of warnings about files headers and things that look like your music is almost dying, but don't worry, they're just warnings and the files are still healthy as far as I've tested.

## License
Can be freely used under the terms of the <a href="http://www.gnu.org/copyleft/lesser.html">GNU LGPL</a>.