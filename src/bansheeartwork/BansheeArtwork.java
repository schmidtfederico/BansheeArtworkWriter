package bansheeartwork;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagOptionSingleton;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.ArtworkFactory;

/**
 * Hello world!
 *
 */
public class BansheeArtwork {

    public static void main(String[] args) {
        try {
            File f = new File("/media/Series/Música/The Beatles/A Hard Day´s Night/2 - I Should Have Known Better.flac");
            AudioFile audioFile;
            Tag audioTag;

            audioFile = AudioFileIO.read(f);
            audioTag = audioFile.getTag();
            
            String md5 = BansheeArtwork.MD5(Normalizer.normalize(audioTag.getFirst(FieldKey.ALBUM_ARTIST)+"\t"+audioTag.getFirst(FieldKey.ALBUM), Normalizer.Form.NFKD));
            
            String path_string = "/home/federico/.cache/media-art/album-" + md5 + ".jpg";
            System.out.println(path_string);
            File cover = new File(path_string);
            
            if(cover.exists() && cover.canRead()){
                System.out.println("Cover found.");
                 Artwork a = ArtworkFactory.createArtworkFromFile(cover);
                 
                 audioTag.setField(a);
                 audioFile.commit();
            }
        } catch (CannotWriteException | CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(BansheeArtwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
