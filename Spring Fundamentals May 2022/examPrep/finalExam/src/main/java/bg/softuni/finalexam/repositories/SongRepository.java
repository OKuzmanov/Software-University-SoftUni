package bg.softuni.finalexam.repositories;

import bg.softuni.finalexam.models.entities.Song;
import bg.softuni.finalexam.models.entities.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findByPerformer(String performer);

    List<Song> findAllByStyle(Style popStyle);

    @Query("FROM Song AS s JOIN s.users AS u WHERE u.id = :id")
    List<Song> findAllByUserId(Long id);
}
