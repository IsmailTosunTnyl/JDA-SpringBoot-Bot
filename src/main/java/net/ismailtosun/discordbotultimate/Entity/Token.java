package net.ismailtosun.discordbotultimate.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Token {
    private String token;
    private long expirationDate;

}
