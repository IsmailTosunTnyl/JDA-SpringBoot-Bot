package net.ismailtosun.discordbotultimate.Entity;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "soundpads")
public class SoundPadItem {

    @Id
    private String name;
    private byte[] sound;

}
