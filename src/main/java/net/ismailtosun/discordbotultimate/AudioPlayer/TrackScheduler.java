package net.ismailtosun.discordbotultimate.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Activity;
import net.ismailtosun.discordbotultimate.Configurators.BotConfiguration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer player;

    public final BlockingQueue<AudioTrack> queue;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track) {

        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
        else {
            BotConfiguration.jda.getPresence().setActivity(Activity.customStatus("Playing: " + track.getInfo().title));
        }
    }

    public void nextTrack() {
        if (queue.isEmpty()) {
            return;
        }
        player.startTrack(queue.poll(), false);
        BotConfiguration.jda.getPresence().setActivity(Activity.customStatus("Playing: " + player.getPlayingTrack().getInfo().title));
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            System.out.println("Track ended, starting next track");
            nextTrack();
        }
        else {
           BotConfiguration.jda.getPresence().setActivity(Activity.playing("Leagues of Legends"));
        }
    }


}
