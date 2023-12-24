package net.ismailtosun.discordbotultimate.Controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Entity.Playlist;
import net.ismailtosun.discordbotultimate.Entity.Track;
import net.ismailtosun.discordbotultimate.Repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

@Controller
public class IndexController {

    private  PlaylistRepository playlistRepository;
    private PlayerManager playerManager;
    private JDA jda;
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public IndexController(PlaylistRepository playlistRepository, PlayerManager playerManager, JDA jda) {
        this.playlistRepository = playlistRepository;
        this.playerManager = playerManager;
        this.jda = jda;
    }




    @GetMapping("/")
    public String indexPage(Model model) throws JsonProcessingException {
        // send data to the template
        model.addAttribute("message", "Ismail");


        Guild guild = jda.getGuildById("775351095748198442");

        List<Playlist> playlistList = playlistRepository.findAll();

        System.out.println(playlistList.size());


        model.addAttribute("playlists", objectMapper.writeValueAsString(playlistList));
        // return the template name
        return "index";
    }

}
