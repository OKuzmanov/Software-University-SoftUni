package bg.softuni.examprepmusicdb.repositories;

import bg.softuni.examprepmusicdb.models.entities.Artist;
import bg.softuni.examprepmusicdb.models.enums.ArtistEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findByName(ArtistEnum name);
}
