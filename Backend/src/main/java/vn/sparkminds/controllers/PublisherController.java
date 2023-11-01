package vn.sparkminds.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.sparkminds.exceptions.PublisherException;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.Publisher;
import vn.sparkminds.model.User;
import vn.sparkminds.services.PublisherService;
import vn.sparkminds.services.UserService;
import vn.sparkminds.services.dto.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/publishers")
public class PublisherController {
    @Autowired
    private PublisherService publisherService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Publisher> createPublisherHandler(
            @RequestHeader("Authorization") String jwt, @RequestBody Publisher req)
            throws UserException {
        User user = userService.findUserByJwt(jwt);

        Publisher createdPublisher = publisherService.createPublisher(req);
        return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
    }

    @GetMapping
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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePublisherHander(
            @RequestHeader("Authorization") String jwt, @PathVariable("id") Long id)
            throws PublisherException, UserException {
        User user = userService.findUserByJwt(jwt);
        publisherService.deletePublisher(id);
        ApiResponse response = new ApiResponse("Delete Publisher succeessfully", true);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

}
