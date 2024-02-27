package net.ismailtosun.discordbotultimate.Controllers;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/soundpad/")
@CrossOrigin(maxAge = 3600, origins = "*")
public class SoundPadFileUploadController {

    private final ServletContext servletContext;

    public SoundPadFileUploadController(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    @GetMapping("/index")
    public String indexPage() {
        return "soundpad";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        // Logic to handle the uploaded file
        if (!file.isEmpty()) {
            System.out.println("Received file: " + file.getOriginalFilename());


            // working directory
            String uploadDirectory = System.getProperty("user.dir");

            // upload directory
            uploadDirectory = uploadDirectory +File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"audio";

            // Ensure the directory exists; create it if not
            File uploadDir = new File(uploadDirectory);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Save the file to the specified directory
            String filePath = uploadDirectory + File.separator + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            System.out.println("File saved to: " + filePath);
            System.out.println(System.getProperty("user.dir"));

        } else {
            // Handle case when no file is uploaded
            System.out.println("No file uploaded");
        }


        // Redirect back to the sound pad page
        return "redirect:/";
    }

}
