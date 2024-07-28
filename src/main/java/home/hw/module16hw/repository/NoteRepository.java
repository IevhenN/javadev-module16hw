package home.hw.module16hw.repository;

import home.hw.module16hw.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {}
