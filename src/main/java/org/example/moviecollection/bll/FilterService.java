package org.example.moviecollection.bll;

import java.util.List;
import java.util.stream.Collectors;

public class FilterService {



       /* public List<Songs> filterSongs(List<Songs> songs, String query) {
            System.out.println("Filter Query: " + query);

            return songs.stream()
                    .filter(song -> query == null || query.isEmpty() ||
                            song.getTitle().toLowerCase().contains(query) || // Matches title
                            song.getArtist().toLowerCase().contains(query))  // Matches artist
                    .peek(song -> System.out.println("Matching Song: " + song))
                    .collect(Collectors.toList());
        }
    }*/
}
