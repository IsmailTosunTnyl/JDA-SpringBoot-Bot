package net.ismailtosun.discordbotultimate.Repository;

import net.ismailtosun.discordbotultimate.Entity.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface PlaylistRepository extends MongoRepository<Playlist, String> {
}
