package net.ismailtosun.discordbotultimate.Repository;

import com.austinv11.servicer.Service;
import net.ismailtosun.discordbotultimate.Entity.SoundPadItem;
import org.springframework.data.mongodb.repository.MongoRepository;

@Service
public interface SoundpadRepository extends MongoRepository<SoundPadItem, String> {
}
