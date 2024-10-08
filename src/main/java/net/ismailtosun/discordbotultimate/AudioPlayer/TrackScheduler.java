package net.ismailtosun.discordbotultimate.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Activity;
import net.ismailtosun.discordbotultimate.Configurators.BotConfiguration;
import net.ismailtosun.discordbotultimate.Services.TrackQueueUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class TrackScheduler extends AudioEventAdapter {
    Logger logger = LoggerFactory.getLogger(TrackScheduler.class);
    private final AudioPlayer player;

    public final BlockingQueue<AudioTrack> queue;

    private final SimpMessageSendingOperations messagingTemplate;

    private final TrackQueueUpdateService trackQueueUpdateService;
    public TrackScheduler(AudioPlayer player, SimpMessageSendingOperations messagingTemplate) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.messagingTemplate = messagingTemplate;
        this.trackQueueUpdateService = new TrackQueueUpdateService(messagingTemplate);
    }

    public void queue(AudioTrack track) {

        if (!player.startTrack(track, true)) {
            logger.info("Track added to queue: "+ queue.size()+". " + track.getInfo().title);
            queue.offer(track);
        }
        else {
            logger.info("No Track remain in queue, status set to default");
            BotConfiguration.jda.getPresence().setActivity(Activity.customStatus("Playing: " + track.getInfo().title));
        }
    }

    public void nextTrack() {
        if (queue.isEmpty()) {
            BotConfiguration.jda.getPresence().setActivity(Activity.playing("Leagues of Legends"));
            // TODO send an empty embed message to the channel to clear the player
            return;
        }
        player.startTrack(queue.poll(), false);
        BotConfiguration.jda.getPresence().setActivity(Activity.customStatus("Playing: " + player.getPlayingTrack().getInfo().title));
        trackQueueUpdateService.updateQueue(queue);

        // update embed player
        EmbedPlayer.getInstace().updateEmbedPlayer();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            logger.info("Track ended, starting next track");
            nextTrack();
        }
        else {
           BotConfiguration.jda.getPresence().setActivity(Activity.playing("Leagues of Legends"));
           logger.info("Status set to: Leagues of Legends");
        }
    }


    public void playTrackById(int position) {
        if (position < 1 || position > queue.size()) {
            logger.error("Error: Unable to play track at position " + position);
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
            logger.error("Error: Unable to play track at position " + position);
        }
        trackQueueUpdateService.updateQueue(queue);
    }

    public void shuffleQueue() {
        List<AudioTrack> tracks = new ArrayList<>(queue);
        queue.clear();
        while (!tracks.isEmpty()) {
            int index = (int) (Math.random() * tracks.size());
            queue.offer(tracks.remove(index));
        }
        trackQueueUpdateService.updateQueue(queue);
    }

    public void playNext(AudioTrack track) {

        // place the track at the front of the queue
        logger.info("Added track to front of queue: " + track.getInfo().title);
        List<AudioTrack> tracks = new ArrayList<>(queue);
        queue.clear();
        queue.offer(track);
        queue.addAll(tracks);
        trackQueueUpdateService.updateQueue(queue);
    }

}
