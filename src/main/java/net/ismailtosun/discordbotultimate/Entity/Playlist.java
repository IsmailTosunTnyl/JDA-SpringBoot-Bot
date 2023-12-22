package net.ismailtosun.discordbotultimate.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "playlists")
public class Playlist {
    @Id
    private String URL;
    private String name;
    private String[] tracks;

}
