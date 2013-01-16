/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bansheeartwork.core;

import java.util.ArrayList;

/**
 * @author federico
 */
public interface ArtworkWriterObserver {
    public void updateStatus(String s);
    public void showStatistics(int totalFiles, int artworksWritten, int failedFiles);
    public void showFailedFiles(ArrayList<String> failedFiles);
}
