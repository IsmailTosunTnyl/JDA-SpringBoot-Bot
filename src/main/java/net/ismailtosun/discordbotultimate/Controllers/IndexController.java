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
import org.springframework.web.bind.annotation.GetMapping;


import java.util.ArrayList;
import java.util.List;

@Controller
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
        System.out.println(trackList);
        System.out.println(playlistList.size());


        return "player";
    }

    @GetMapping("/live")
    public String livePage(Model model) throws JsonProcessingException {
        Guild guild = jda.getGuildById(guildId);
        System.out.println(guild.getName());
        Track track1 = new Track();
        long position;


        // get player queue
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


        // insert current track to queue
        if (playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack() != null) {

            track1.setUrl(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getInfo().uri);
            track1.setTitle(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getInfo().title);
            track1.setAuthor(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getInfo().author);
            track1.setDuration(playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getDuration());
            trackList.add(0,track1);
             position = playerManager.getGuildMusicManager(guild).audioPlayer.getPlayingTrack().getPosition();
        }else {
            track1.setUrl("No track in queue");
            track1.setTitle("No track in queue");
            track1.setAuthor("No track in queue");
            trackList.add(track1);
            position = 0;
        }

        List<Playlist> playlistList = new ArrayList<>();
        playlistList.add(new Playlist("TESTURL", "test", trackList.toArray(new Track[0])));


        model.addAttribute("playlists", objectMapper.writeValueAsString(playlistList));
        model.addAttribute("position", position);

        return "player";

    }





}
