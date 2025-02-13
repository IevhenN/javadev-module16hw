package home.hw.module16hw.sevice;

import home.hw.module16hw.model.Note;
import home.hw.module16hw.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public void deleteById(long id) {
        noteRepository.deleteById(id);
    }

    public void save(Note note) {
        noteRepository.save(note);
    }

    public Optional<Note> getById(Long id) {
        return noteRepository.findById(id);
    }
}
