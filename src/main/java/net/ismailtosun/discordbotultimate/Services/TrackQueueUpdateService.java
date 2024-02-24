package net.ismailtosun.discordbotultimate.Services;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.ismailtosun.discordbotultimate.Entity.Track;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class TrackQueueUpdateService {
    private final SimpMessageSendingOperations messagingTemplate;


    public TrackQueueUpdateService(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void updateQueue(BlockingQueue<AudioTrack> queue) {
        List<Track> trackList = new ArrayList<>();
        for (AudioTrack audioTrack : queue) {
            trackList.add(new Track(audioTrack.getInfo().uri,
                    audioTrack.getInfo().title,
                    audioTrack.getInfo().author,
                    audioTrack.getDuration(),
                    audioTrack.getPosition()));
        }
        messagingTemplate.convertAndSend("/bot/queue", trackList);
    }


}
