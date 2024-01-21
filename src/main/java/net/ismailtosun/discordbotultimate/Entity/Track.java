package net.ismailtosun.discordbotultimate.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Track {
    private String url;
    private String title;
    private String author;
    private long duration;
    private long position;
}
