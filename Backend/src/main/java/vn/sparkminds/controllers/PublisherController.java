package vn.sparkminds.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.sparkminds.exceptions.PublisherException;
import vn.sparkminds.model.Publisher;
import vn.sparkminds.services.PublisherService;
import vn.sparkminds.services.UserService;


@RestController
@RequestMapping("/api/v1/publishers")
public class PublisherController {
    @Autowired
    private PublisherService publisherService;

    @Autowired
    private UserService userService;

    private String message = "";
    private boolean status = false;


    @GetMapping("/")
    public ResponseEntity<List<Publisher>> getAllPublishersHandler() {
        List<Publisher> publishers = publisherService.findAllPublishers();
        return new ResponseEntity<>(publishers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherByIdHandler(@PathVariable("id") Long id)
            throws PublisherException {

        Publisher publisher = publisherService.findPublisherById(id);
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

}
