package net.ismailtosun.discordbotultimate.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoWriteException;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Configurators.BotConfiguration;
import net.ismailtosun.discordbotultimate.Entity.Playlist;
import net.ismailtosun.discordbotultimate.Entity.Song;
import net.ismailtosun.discordbotultimate.Repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.dao.DuplicateKeyException;

@Controller
public class IndexController {

    private  PlaylistRepository playlistRepository;
    private PlayerManager playerManager;
    private JDA jda;

    @Autowired
    public IndexController(PlaylistRepository playlistRepository, PlayerManager playerManager, JDA jda) {
        this.playlistRepository = playlistRepository;
        this.playerManager = playerManager;
        this.jda = jda;
    }




    @GetMapping("/")
    public String indexPage(Model model) {
        // send data to the template
        model.addAttribute("message", "Ismail");

        try {
            playlistRepository.insert(new Playlist(
                    "https://www.youtube.com/watch?v=5qap5aO4i9A",
                    "test",
                    new String[]{"https://www.youtube.com/watch?v=5qap5aO4i9A"}
            ));
        } catch (DuplicateKeyException e) {
              System.out.println(e.getCause());
                model.addAttribute("message", "DuplicateKeyException");
        }

        Guild guild = jda.getGuildById("775351095748198442");


        if(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack() != null) {
            model.addAttribute("song", playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getInfo().title);
        } else {
            model.addAttribute("song", "No song playing");
        }
        // pass a dummy json object
        Song song = new Song("https://www.youtube.com/watch?v=vudLXNM1uX0", "test", "test");
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert the User object to JSON string
            String songstr = mapper.writeValueAsString(song);

            // Add the JSON string to the model
            model.addAttribute("song", songstr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Add the dummy JSON string to the model



        // return the template name
        return "index";
    }

}
