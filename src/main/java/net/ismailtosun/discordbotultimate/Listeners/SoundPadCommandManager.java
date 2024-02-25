package net.ismailtosun.discordbotultimate.Listeners;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;


public class SoundPadCommandManager extends ListenerAdapter {
    private PlayerManager playerManager;

    public SoundPadCommandManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }
    @SneakyThrows
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

        switch (event.getName()) {
            case "soundpad":
                handleSoundPadCommand(event);
                break;
            default:
                break;
        }

    }

    private void handleSoundPadCommand(SlashCommandInteractionEvent event) {
        //TODO fixed soundpad url for testing
        String soundURL = "https://youtu.be/QODoH34xO8U";


        // get current playing track
        AudioTrack playingTrack = playerManager.getGuildMusicManager(event.getGuild()).audioPlayer.getPlayingTrack();
        // clone the track
        AudioTrack playintrackClone = playingTrack.makeClone();
        // set the position of the clone to the current position of the playing track
        playintrackClone.setPosition(playingTrack.getPosition());

        // add the previous track to top of the queue
        playerManager.getGuildMusicManager(event.getGuild()).scheduler.playNext(playintrackClone);

        // load the sound
        playerManager.loadAndPlay(event.getGuild(), soundURL, true,true);



        event.reply("Playing soundpad").queue();

    }

}
