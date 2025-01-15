package org.example.moviecollection.dal;

import org.example.moviecollection.be.Movie;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    /*//Method to get movies from a folder
    public List<Movie> getMoviesfromFolder(String folderPath){
        List<Movie> movieList = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    String category;
                    if (file.isFile() && file.getName().endsWith(".mp4") || file.isFile() && file.getName().endsWith(".mpeg4")) //filters mp4 and mpeg4 files
                        movieList.add(new Movie (file.getName(), file.getAbsolutePath(), category));
                }
            }
        }
        return movieList;
    }*/
}
