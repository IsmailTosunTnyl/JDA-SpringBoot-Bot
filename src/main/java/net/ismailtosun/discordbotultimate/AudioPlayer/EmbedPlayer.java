package net.ismailtosun.discordbotultimate.AudioPlayer;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.object.Embed;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageEditBuilder;
import net.ismailtosun.discordbotultimate.Constants.ButtonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmbedPlayer {
    Logger logger = LoggerFactory.getLogger(EmbedPlayer.class);

    public static EmbedPlayer getInstace() {
        if (embedPlayer == null) {
            embedPlayer = new EmbedPlayer();
        }
        return embedPlayer;
    }

    // stop shuffle and next buttons with emojis
    private static final List<Button> buttons = List.of(
            Button.primary(ButtonConstants.ACTION + ButtonConstants.SEPERATOR + ButtonConstants.STOP, "Stop")
                    .withEmoji(Emoji.fromUnicode("⏹️")),
            Button.primary(ButtonConstants.ACTION + ButtonConstants.SEPERATOR + ButtonConstants.SHUFFLE, "Shuffle")
                    .withEmoji(Emoji.fromUnicode("🔀")),
            Button.primary(ButtonConstants.ACTION + ButtonConstants.SEPERATOR + ButtonConstants.NEXT, "Next").withEmoji(Emoji.fromUnicode("⏭️"))
    );

    private static EmbedPlayer embedPlayer;

    @Getter
    private MessageEmbed embed;

    @Setter
    private TextChannel textChannel;

    @Setter
    private Long lastMessageId;

    @Setter
    private PlayerManager playerManager;

    private EmbedPlayer() {
        startProgressUpdate();
    }


    private String createProgressBar(int currentTime, int totalTime) {
        int progress = (int) Math.round((currentTime / (double) totalTime) * 20);
        int emptyProgress = 20 - progress;
        String progressText = "▬".repeat(progress);
        String emptyProgressText = "▬".repeat(emptyProgress);
        return "[" + progressText + "🔘" + emptyProgressText + "]" + " " + totalTime / 60000 + ":" + (totalTime % 60000) / 1000;
    }

    public MessageEmbed createEmbed(AudioTrack audioTrack) {
        if (audioTrack == null) {
            // return empty embed
            return new EmbedBuilder().setTitle("Nothing playing").build();
        }
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(audioTrack.getInfo().title);
        embedBuilder.setThumbnail(extractVideoCoverFromUrl(audioTrack.getInfo().uri));
        embedBuilder.setDescription("Author: " + audioTrack.getInfo().author);
        String progress = createProgressBar((int) audioTrack.getPosition(), (int) audioTrack.getDuration());
        embedBuilder.addField("Progress", progress, false);


        return embedBuilder.build();

    }

    public void sendEmbed(TextChannel textChannel) {
        if (textChannel == null) {
            logger.warn("Text channel is null ***********");
            return;
        }
        if (embed == null) {
            logger.warn("Embed is null");
            embed = new EmbedBuilder().setTitle("Getting Ready").build();
        }

        setTextChannel(textChannel);
        textChannel.sendMessageEmbeds(embed).addActionRow(buttons).queue(message -> {
            setLastMessageId(message.getIdLong());
            logger.info("Embed id is set to " + message.getIdLong());
        });
    }


    // update periodically with threads
    // update embed


    private void startProgressUpdate() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    if (textChannel == null) {
                        Thread.sleep(2000);
                        logger.warn("Text channel is null");
                        continue;
                    }
                    GuildMusicManager musicManager = playerManager.musicManagers.get(textChannel.getGuild().getIdLong());
                    if (musicManager != null && musicManager.audioPlayer.getPlayingTrack() != null) {
                        AudioTrack audioTrack = playerManager.musicManagers.get(textChannel.getGuild().getIdLong()).audioPlayer.getPlayingTrack();
                        if (audioTrack != null) {
                            this.embed = createEmbed(audioTrack);
                            if (lastMessageId != null) {
                                textChannel.editMessageEmbedsById(lastMessageId, embed).queue();
                            } else {
                                Thread.sleep(2000);
                                logger.warn("Message id is null");
                            }
                        } else {
                            Thread.sleep(5000);
                            logger.warn("Audio track is null");
                        }
                    }

                } catch (InterruptedException e) {
                    logger.error(e.toString());
                }
            }
        });
        thread.start();
    }


    public static String extractVideoCoverFromUrl(String url) {
        String videoId = null;
        try {
            String youtubeRegex = "^(?:https?:\\/\\/)?(?:www\\.)?(?:youtube\\.com\\/(?:[^\\/]+\\/.+\\/|(?:v|e(?:mbed)?)\\/|.*[?&]v=)|youtu\\.be\\/)([^\"&?\\/\\s]{11})";
            String youtubeMusicRegex = "^(?:https?:\\/\\/)?music\\.youtube\\.com\\/(?:watch\\?v=|embed\\/|.*[?&]v=)([^\"&?\\/\\s]{11})";

            Pattern youtubePattern = Pattern.compile(youtubeRegex, Pattern.CASE_INSENSITIVE);
            Pattern youtubeMusicPattern = Pattern.compile(youtubeMusicRegex, Pattern.CASE_INSENSITIVE);

            Matcher youtubeMatcher = youtubePattern.matcher(url);
            Matcher youtubeMusicMatcher = youtubeMusicPattern.matcher(url);

            if (youtubeMatcher.find()) {
                videoId = youtubeMatcher.group(1);
            } else if (youtubeMusicMatcher.find()) {
                videoId = youtubeMusicMatcher.group(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return videoId != null ? "http://img.youtube.com/vi/" + videoId + "/hqdefault.jpg" : null;
    }


}
