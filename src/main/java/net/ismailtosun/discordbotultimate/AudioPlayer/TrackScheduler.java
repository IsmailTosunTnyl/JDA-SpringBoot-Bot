package net.ismailtosun.discordbotultimate.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Activity;
import net.ismailtosun.discordbotultimate.Configurators.BotConfiguration;
import net.ismailtosun.discordbotultimate.Entity.Track;
import net.ismailtosun.discordbotultimate.Services.TrackQeueUpdateService;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer player;

    public final BlockingQueue<AudioTrack> queue;

    private final SimpMessageSendingOperations messagingTemplate;

    private final TrackQeueUpdateService trackQeueUpdateService;
    public TrackScheduler(AudioPlayer player, SimpMessageSendingOperations messagingTemplate) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.messagingTemplate = messagingTemplate;
        this.trackQeueUpdateService = new TrackQeueUpdateService(messagingTemplate);
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

        trackQeueUpdateService.updateQueue(queue);
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


    public void playTrackById(int position) {
        if (position < 1 || position > queue.size()) {
            System.out.println("Invalid position: " + position);
            return;
        }

        // Dequeue and requeue tracks until the desired position
        for (int i = 1; i < position; i++) {
            AudioTrack removedTrack = queue.poll();
            if (removedTrack != null) {
                queue.offer(removedTrack);
            }
        }

        // Start the track at the specified position
        AudioTrack requestedTrack = queue.poll();
        if (requestedTrack != null) {
            player.startTrack(requestedTrack, false);
            BotConfiguration.jda.getPresence().setActivity(
                    Activity.customStatus("Playing: " + requestedTrack.getInfo().title)
            );
        } else {
            System.out.println("Error: Unable to play track at position " + position);
        }
        trackQeueUpdateService.updateQueue(queue);
    }

}
