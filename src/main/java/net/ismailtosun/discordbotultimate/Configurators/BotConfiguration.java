package net.ismailtosun.discordbotultimate.Configurators;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Listeners.CommandManager;
import net.ismailtosun.discordbotultimate.Listeners.MessageCreateListener;
import net.ismailtosun.discordbotultimate.Listeners.PlaylistCommands;
import net.ismailtosun.discordbotultimate.Repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfiguration {

    private PlaylistRepository playlistRepository;

    @Autowired
    public BotConfiguration(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Value("${token}")
    private String token;

    public static JDA jda;
    @Bean
    public JDA jda() {
        PlayerManager playerManager = new PlayerManager();
        JDA jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new MessageCreateListener(),
                                    new CommandManager(playerManager),
                                    new PlaylistCommands(playlistRepository,playerManager))
                .build();
        BotConfiguration.jda= jda;
        return jda;
        
    }


}