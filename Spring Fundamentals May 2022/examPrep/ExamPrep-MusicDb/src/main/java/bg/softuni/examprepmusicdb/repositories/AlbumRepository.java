package bg.softuni.examprepmusicdb.repositories;

import bg.softuni.examprepmusicdb.models.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
}
