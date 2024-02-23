package net.ismailtosun.discordbotultimate.Listeners;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Entity.Playlist;
import net.ismailtosun.discordbotultimate.Repository.PlaylistRepository;

import java.util.ArrayList;
import java.util.List;

public class UtilsCommandsManager extends ListenerAdapter {
    private  PlaylistRepository playlistRepository;
    private final String[] gifs ;

    public UtilsCommandsManager(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
        gifs= new String[]{
                "https://media.giphy.com/media/26gscNQHswYio5RBu/giphy-downsized-large.gif",
                "https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExbzVqb2VqbTFvMGRtc285dnJqaXlkMTl0dWc2dXVzM2pzOWJnNTBwciZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/NV4cSrRYXXwfUcYnua/giphy.gif",
                "https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExdzg4cWN0c2Y0eDJtcTdibDY5dHdlcndxdzYyZXEzaThubWk5bWgwdyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/fveBibo1Kvb1tUHadY/giphy-downsized-large.gif",
                "https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExMXV1d3ZqajB1NnUweHJ6ZG9wNmdwMnh4aGVkYnB5ZXUzc3R6MHkwMSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/RSNVRlrcsrvZm/giphy.gif",
                "https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExcWZ4azkzM2loMGU1d2w3MXpzbmhxZ2Z2NjE4eDcwbGN3ejVrYnE3aiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/26tjZsCAK4UZOVKaA/giphy.gif",
                "https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExMXRobHg1bzBmdHVidGN3ejh3cXQ2OHkzdzluMGQ5dWJtNHh5bWF3ciZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/xsATxBQfeKHCg/giphy-downsized-large.gif",
                "https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExOTZoYjc1ZnQ1ZWxlcmdmOHlwaDk0cWdsMm4zdjZqNWV0ejB2NzNoaCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/3oKIPCSX4UHmuS41TG/giphy.gif"
        };

    }

    @SneakyThrows
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        switch (event.getName()) {
            case "delete":
                handleDeleteCommand(event);
                break;
        }

    }

    private void handleDeleteCommand(SlashCommandInteractionEvent event) {
        int count = 2;
        if(event.getOption("messagecount") != null){
            count = event.getOption("messagecount").getAsInt();
        }
        try {
            event.getChannel().getHistory().retrievePast(count).queue(messages -> {
                event.getChannel().asTextChannel().deleteMessages(messages).queue();

            });

            String gif = gifs[(int) (Math.random() * gifs.length)];

            event.reply(gif).queue();
        }
        catch (Exception e){
            event.reply("I can't delete messages older than 14 days").queue();
        }



    }



}
