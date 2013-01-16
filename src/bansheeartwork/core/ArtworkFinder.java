package bansheeartwork.core;

import bansheeartwork.config.AppContext;
import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.ArtworkFactory;

/**
 * @author Federico Schmidt
 */
public abstract class ArtworkFinder {
    
    /**
     * Finds the artwork for the specified file.
     * 
     * @param f The file.
     * @return The Artwork in Banshee's cache or null if not found.
     */
    public static Artwork getArtwork(AudioFile f) {
        Tag audioTag;
        audioTag = f.getTag();

        String md5 = ArtworkFinder.MD5(Normalizer.normalize(audioTag.getFirst(FieldKey.ALBUM_ARTIST) + "\t" + audioTag.getFirst(FieldKey.ALBUM), Normalizer.Form.NFKD));

        String path_string = AppContext.getInstance().getBansheeCache().getAbsolutePath() + "/album-" + md5 + ".jpg";
        
        File cover = new File(path_string);

        if (cover.exists() && cover.canRead()) {
            try {
                return ArtworkFactory.createArtworkFromFile(cover);
            } catch (IOException ex) {
                Logger.getLogger(ArtworkFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    /**
     * Calculates the md5 string of a string.
     * @param md5 The String to hash.
     * @return The md5 hash string.
     * @source http://m2tec.be/blog/2010/02/03/java-md5-hex-0093
     */
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
    
}
