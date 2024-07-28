package home.hw.module16hw.controller;

import home.hw.module16hw.model.Note;
import home.hw.module16hw.sevice.NoteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/list")
    public void getNotList(HttpServletResponse response) {
        List<Note> notes = noteService.findAll();

        response.setContentType("text/html");
        try {

            response.getWriter().write("<html lang='uk'><head><meta charset='UTF-8'><title>List notes</title>" +
                    "<link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css' rel='stylesheet'>" +
                    "</head><body><div class='container'><h1>Notes list</h1><ul class='list-group'>");
            for (Note note : notes) {
                response.getWriter().write("<li class='list-group-item d-flex justify-content-between align-items-center'>" +
                        "<div><strong>" + note.getTitle() + "</strong><br>" + note.getContent() + "</div>" +
                        "<div>" +
                        "<form action='/note/delete' method='post' class='d-inline'>" +
                        "<input type='hidden' name='id' value='" + note.getId() + "'>" +
                        "<button type='submit' class='btn btn-danger btn-sm'>Delete</button></form>" +
                        "<form action='/note/edit' method='get' class='d-inline ml-2'>" +
                        "<input type='hidden' name='id' value='" + note.getId() + "'>" +
                        "<button type='submit' class='btn btn-primary btn-sm'>Edit</button></form></div></li>");
            }
            response.getWriter().write("</ul><a href='/note/edit' class='btn btn-success mt-3'>Add note</a></div>" +
                    "<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>" +
                    "<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js'></script>" +
                    "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js'></script></body></html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/delete")
    public void noteDelete(@RequestParam Long id, HttpServletResponse response) throws IOException {
        noteService.deleteById(id);
        response.sendRedirect("/note/list");
    }

    @GetMapping("/edit")
    public void noteEdit(@RequestParam(required = false, defaultValue = "0") Long id
            , HttpServletResponse response) {

        Note note = new Note();
        if (id != null) {
            Optional<Note> optionalNote = noteService.getById(id);
            if (optionalNote.isPresent()) {
                note = optionalNote.get();
            }
        }


        response.setContentType("text/html");
        try {
            response.getWriter().write("<html lang='uk'><head><meta charset='UTF-8'><title>Edit note</title>" +
                    "<link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css' rel='stylesheet'>" +
                    "</head><body><div class='container'><h1>Edit note</h1><form action='/note/edit' method='post'>" +
                    "<input type='hidden' name='id' value='" + (note.getId() != null ? note.getId() : "") + "'>" +
                    "<div class='form-group'><label for='title'>Title</label>" +
                    "<input type='text' id='title' name='title' class='form-control' value='" +(note.getTitle() != null ? note.getTitle() : "") + "'></div>" +
                    "<div class='form-group'><label for='content'>Content:</label>" +
                    "<textarea id='content' name='content' class='form-control'>" + (note.getContent() != null ? note.getContent() : "") + "</textarea></div>" +
                    "<button type='submit' class='btn btn-primary'>Save</button></form>" +
                    "<br><a href='/note/list' class='btn btn-secondary mt-3'>Return to list</a></div>" +
                    "<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>" +
                    "<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js'></script>" +
                    "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js'></script></body></html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/edit")
    public void noteSave(@RequestParam(required = false, defaultValue = "0") long id
            , @RequestParam String title
            , @RequestParam String content
            , HttpServletResponse response) throws IOException {

        Note note = new Note();
        note.setId(id);
        note.setTitle(title);
        note.setContent(content);

        noteService.save(note);

        response.sendRedirect("/note/list");
    }
}
