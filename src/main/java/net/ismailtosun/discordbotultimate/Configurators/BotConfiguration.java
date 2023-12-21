package net.ismailtosun.discordbotultimate.Configurators;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Listeners.CommandManager;
import net.ismailtosun.discordbotultimate.Listeners.MessageCreateListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfiguration {

    @Value("${token}")
    private String token;

    public static JDA jda;
    @Bean
    public JDA jda() {

        JDA jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new MessageCreateListener(),
                                    new CommandManager(new PlayerManager()))
                .build();
        BotConfiguration.jda= jda;
        return jda;
        
    }


}