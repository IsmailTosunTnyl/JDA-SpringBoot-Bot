package net.ismailtosun.discordbotultimate.Listeners;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.ismailtosun.discordbotultimate.AudioPlayer.PlayerManager;
import net.ismailtosun.discordbotultimate.Entity.SoundPadItem;
import net.ismailtosun.discordbotultimate.Repository.SoundpadRepository;
import net.ismailtosun.discordbotultimate.Services.SoundPadFileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class SoundPadCommandManager extends ListenerAdapter {
    private PlayerManager playerManager;
    private SoundPadFileService soundPadFileService;

    private String audioDirectoryPath ; // Adjust this path accordingly

    public SoundPadCommandManager(PlayerManager playerManager,
                                  SoundPadFileService soundPadFileService) {
        this.playerManager = playerManager;
        this.soundPadFileService = soundPadFileService;
        audioDirectoryPath = "src/main/resources/audio/";
    }

    @SneakyThrows
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

        switch (event.getName()) {
            case "soundpadupload":
                handleSoundPadUploadCommand(event);
                break;
            case "soundpaddownload":
                handleDownloadCommand(event);
                break;
            default:
                break;
        }

    }

    private void handleDownloadCommand(SlashCommandInteractionEvent event) {
        int fileCount = soundPadFileService.downloadAudioFolderFromDB();


        event.reply(fileCount+" file downloaded from database").queue();
    }

    private void handleSoundPadUploadCommand(SlashCommandInteractionEvent event) throws IOException {

        int fileCount = soundPadFileService.uploadAudioFolderToDB();


        event.reply(fileCount+" File wrote to DB ").queue();

    }

}
