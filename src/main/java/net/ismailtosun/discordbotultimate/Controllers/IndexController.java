package net.ismailtosun.discordbotultimate.Controllers;


import com.mongodb.MongoWriteException;
import net.ismailtosun.discordbotultimate.Entity.Playlist;
import net.ismailtosun.discordbotultimate.Repository.PlaylistRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.dao.DuplicateKeyException;

@Controller
public class IndexController {

    private  PlaylistRepository playlistRepository;
    public IndexController(PlaylistRepository playlistRepository) {
      this.playlistRepository = playlistRepository;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        // send data to the template
        model.addAttribute("message", "Ismail");

        try {
            playlistRepository.insert(new Playlist(
                    "https://www.youtube.com/watch?v=5qap5aO4i9A",
                    "test",
                    new String[]{"https://www.youtube.com/watch?v=5qap5aO4i9A"}
            ));
        } catch (DuplicateKeyException e) {
              System.out.println(e.getCause());
                model.addAttribute("message", "DuplicateKeyException");
        }

        // return the template name
        return "index";
    }

}
