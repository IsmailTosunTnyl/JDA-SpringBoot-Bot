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


import java.io.File;
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

        // get soundpad tracks searching in the directory


        ArrayList<String> soundpadFiles = listFilesInDirectory();


        model.addAttribute("tracks", objectMapper.writeValueAsString(trackList));
        model.addAttribute("soundpadFiles", objectMapper.writeValueAsString(soundpadFiles));


        return "player";
    }


    public ArrayList<String>  listFilesInDirectory() {
        // Create a File object representing the directory
        File folder = new File(System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"audio");
        ArrayList<String> filesList = new ArrayList<>();
        // Check if the directory exists
        if (folder.exists() && folder.isDirectory()) {
            // Get the list of files in the directory
            File[] files = folder.listFiles();

            // Check if files is not null and contains elements
            if (files != null && files.length > 0) {
                // Iterate through the files and print their names
                for (File file : files) {
                    filesList.add(file.getName());
                }
            } else {
                System.out.println("No files found in the directory.");
            }
        } else {
            System.out.println("Specified directory does not exist or is not a directory.");
        }

        return filesList;
    }




}
