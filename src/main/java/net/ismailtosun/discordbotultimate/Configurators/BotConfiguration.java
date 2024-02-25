package net.ismailtosun.discordbotultimate.Configurators;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Listeners.Commands;
import net.ismailtosun.discordbotultimate.Listeners.MediaCommandManager;
import net.ismailtosun.discordbotultimate.Listeners.MessageCreateListener;
import net.ismailtosun.discordbotultimate.Listeners.UtilsCommandsManager;
import net.ismailtosun.discordbotultimate.Repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@Configuration
public class BotConfiguration {

    private PlaylistRepository playlistRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    private PlayerManager playerManager;

    @Autowired
    public BotConfiguration(PlaylistRepository playlistRepository, SimpMessageSendingOperations messagingTemplate) {
        this.playlistRepository = playlistRepository;
        this.messagingTemplate = messagingTemplate;
        playerManager=new PlayerManager(messagingTemplate);
    }

    @Value("${token}")
    private String token;

    public static JDA jda;


    @Bean
    public JDA jda() {

        JDA jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(
                                    new MediaCommandManager(playerManager,messagingTemplate,playlistRepository),
                                   new UtilsCommandsManager(playlistRepository),
                                    new Commands()
                       )
                .build();
        BotConfiguration.jda= jda;
        jda.getPresence().setActivity(net.dv8tion.jda.api.entities.Activity.playing("Leagues of Legends"));
        return jda;
        
    }

    @Bean
    @Lazy
    public PlayerManager playerManager() {
        return playerManager;
    }

    @Bean
    public Guild guild() {
        return jda.getGuildById("775351095748198442");
    }

}