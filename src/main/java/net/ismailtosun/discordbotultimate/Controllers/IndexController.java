package net.ismailtosun.discordbotultimate.Controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Entity.Playlist;
import net.ismailtosun.discordbotultimate.Entity.Track;
import net.ismailtosun.discordbotultimate.Repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin(maxAge = 3600, origins = "*")
public class IndexController {

    private PlaylistRepository playlistRepository;
    private PlayerManager playerManager;
    private JDA jda;
    ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.data.guild.id}")
    private String guildId;

    @Autowired
    public IndexController(PlaylistRepository playlistRepository, PlayerManager playerManager, JDA jda) {
        this.playlistRepository = playlistRepository;
        this.playerManager = playerManager;
        this.jda = jda;

    }


    @GetMapping("/")
    public String indexPage(Model model) throws JsonProcessingException {

        Guild guild = jda.getGuildById(guildId);

        // get all playlists
        List<Playlist> playlistList = playlistRepository.findAll();

        model.addAttribute("playlists", objectMapper.writeValueAsString(playlistList));

        // get current tracks queue
        List<Track> trackList = new ArrayList<>();
        if (!playerManager.getGuildMusicManager(guild).scheduler.queue.isEmpty()) {
            for (AudioTrack audioTrack : playerManager.getGuildMusicManager(guild).scheduler.queue) {

                trackList.add(new Track(audioTrack.getInfo().uri,
                        audioTrack.getInfo().title,
                        audioTrack.getInfo().author,
                        audioTrack.getDuration(),
                        audioTrack.getPosition()));
            }

        }
        model.addAttribute("tracks", objectMapper.writeValueAsString(trackList));



        return "player";
    }



}
