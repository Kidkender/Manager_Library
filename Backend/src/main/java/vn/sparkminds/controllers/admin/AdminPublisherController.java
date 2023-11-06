package vn.sparkminds.controllers.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import vn.sparkminds.exceptions.PublisherException;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.Publisher;
import vn.sparkminds.model.User;
import vn.sparkminds.services.PublisherService;
import vn.sparkminds.services.UserService;
import vn.sparkminds.services.dto.request.UpdatePublisherRequest;
import vn.sparkminds.services.dto.response.ApiResponse;

@RestController
@RequestMapping("/api/admin/publishers")
public class AdminPublisherController {

    @Autowired
    private PublisherService publisherService;
    @Autowired
    private UserService userService;

    private String message = "";
    private boolean status = false;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Publisher> createPublisherHandler(
            @RequestHeader("Authorization") String jwt, @RequestBody @Valid Publisher req)
            throws UserException {
        User user = userService.findUserByJwt(jwt);

        Publisher createdPublisher = publisherService.createPublisher(req);
        return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deletePublisherHander(
            @RequestHeader("Authorization") String jwt, @PathVariable("id") Long id)
            throws PublisherException, UserException {
        User user = userService.findUserByJwt(jwt);
        publisherService.deletePublisher(id);
        ApiResponse response = new ApiResponse("Delete Publisher succeessfully", true);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> updatePublisherHandler(@PathVariable Long id,
            @Valid @RequestBody UpdatePublisherRequest req, BindingResult bindingResult,
            @RequestHeader("Authorization") String authorization) throws PublisherException {
        String message = "";
        if (bindingResult.hasErrors()) {
            message = "Input of publisher invalid";
            return new ResponseEntity<>(new ApiResponse(message, false), HttpStatus.BAD_REQUEST);
        }
        Publisher publisher = publisherService.updatePublisher(id, req);
        return new ResponseEntity<>(
                new ApiResponse(message = "Update successfully: " + publisher.toString(), true),
                HttpStatus.OK);
    }


    @PostMapping("/add/multiple")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> addMultiplePublishersHandler(
            @Valid @RequestBody Publisher[] reqList) {
        if (reqList.length == 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(message = "Length of list data greater than 0", status));
        }
        List<Publisher> publishers = publisherService.createMultiplePublisher(reqList);
        return new ResponseEntity<>(
                new ApiResponse(message = "Add " + publishers.size() + " publishers successfully !",
                        status = true),
                HttpStatus.OK);
    }
}
