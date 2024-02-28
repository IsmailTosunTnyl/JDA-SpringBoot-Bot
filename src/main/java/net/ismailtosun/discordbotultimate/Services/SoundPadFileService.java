package net.ismailtosun.discordbotultimate.Services;

import net.ismailtosun.discordbotultimate.Entity.SoundPadItem;
import net.ismailtosun.discordbotultimate.Repository.SoundpadRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class SoundPadFileService {
    private final SoundpadRepository soundpadRepository;
    private final String audioDirectoryPath;

    public SoundPadFileService(SoundpadRepository soundpadRepository) {
        this.soundpadRepository = soundpadRepository;
        audioDirectoryPath = "src/main/resources/audio/";
    }



    public int uploadAudioFolderToDB() {

        File audioDirectory = new File(audioDirectoryPath);
        if (!audioDirectory.exists()) {
            audioDirectory.mkdirs();
        }
        int fileCount = 0;
        File[] audioFiles = audioDirectory.listFiles();
        if (audioFiles != null) {
            for (File audioFile : audioFiles) {
                try {
                    byte[] sound = Files.readAllBytes(audioFile.toPath());
                    SoundPadItem soundPadItem = new SoundPadItem(audioFile.getName(), sound);
                    soundpadRepository.save(soundPadItem);
                    fileCount++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.printf("Uploaded %d files to the database%n", fileCount);
        return fileCount;
    }
    public int downloadAudioFolderFromDB() {
        File audioDirectory = new File(audioDirectoryPath);
        if (!audioDirectory.exists()) {
            audioDirectory.mkdirs();
        }
        int fileCount = 0;
        Iterable<SoundPadItem> soundPadItems = soundpadRepository.findAll();
        for (SoundPadItem soundPadItem : soundPadItems) {
            String audioFileName = soundPadItem.getName();
            File audioFile = new File(audioDirectory, audioFileName);
            try {
                Files.write(audioFile.toPath(), soundPadItem.getSound());
                fileCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Downloaded %d files from the database%n", fileCount);
        return fileCount;
    }

}
