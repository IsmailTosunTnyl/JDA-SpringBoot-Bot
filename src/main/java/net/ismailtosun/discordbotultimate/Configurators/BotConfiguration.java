package net.ismailtosun.discordbotultimate.Configurators;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;

import net.ismailtosun.discordbotultimate.AudioPlayer.EmbedPlayer;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Listeners.*;
import net.ismailtosun.discordbotultimate.Repository.PlaylistRepository;
import net.ismailtosun.discordbotultimate.Services.SoundPadFileService;
import net.ismailtosun.discordbotultimate.Services.TokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessageSendingOperations;



@Configuration
public class BotConfiguration {
    private Logger logger = LoggerFactory.getLogger(BotConfiguration.class);
    private PlaylistRepository playlistRepository;
    private SoundPadFileService soundPadFileService;
    private final SimpMessageSendingOperations messagingTemplate;

    private PlayerManager playerManager;

    public static JDA jda;

    private final TokenService tokenService;

    @Autowired
    public BotConfiguration(PlaylistRepository playlistRepository,
                            SimpMessageSendingOperations messagingTemplate,
                            TokenService tokenService,
                            SoundPadFileService soundPadFileService) {
        this.playlistRepository = playlistRepository;
        this.messagingTemplate = messagingTemplate;
        playerManager = new PlayerManager(messagingTemplate);
        this.tokenService = tokenService;
        this.soundPadFileService = soundPadFileService;

        EmbedPlayer embedPlayer = EmbedPlayer.getInstace();
        embedPlayer.setPlayerManager(playerManager);

    }

    @Value("${token}")
    private String token;

    @Value("${GUILD_ID}")
    private String guildId;

    @Bean
    public JDA jda() {
        logger.info("JDABuilder is creating");
        JDA jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(
                        new MediaCommandManager(playerManager
                                , messagingTemplate
                                , playlistRepository
                                , tokenService),
                        new ButtonCommandListener(playerManager, playlistRepository),
                        new UtilsCommandsManager(playlistRepository),
                        new SlashCommands(),
                        new SoundPadCommandManager(playerManager, soundPadFileService)
                )
                .build();

        logger.info("JDABuilder is created");

        BotConfiguration.jda = jda;
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
        return jda.getGuildById(guildId);
    }

}