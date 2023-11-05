package vn.sparkminds.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.sparkminds.exceptions.BorrowedBookException;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.BorrowedBook;
import vn.sparkminds.model.User;
import vn.sparkminds.services.BorrowBookService;
import vn.sparkminds.services.UserService;

@RestController
@RequestMapping("/api/v1/borrows")
public class BorrowBookController {

    @Autowired
    private BorrowBookService borrowBookService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<BorrowedBook>> getAllBorrowBookHandler(
            @RequestHeader("Authorization") String authorization) throws UserException {
        User user = userService.findUserByJwt(authorization);
        List<BorrowedBook> listResponse = borrowBookService.getAllBorrowedBook();
        return new ResponseEntity<List<BorrowedBook>>(listResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowedBook> getBorrowedBookByIdHandler(
            @RequestHeader("Authorization") String authorization, @PathVariable("id") Long id)
            throws UserException, BorrowedBookException {
        User user = userService.findUserByJwt(authorization);
        BorrowedBook res = borrowBookService.getBorrowedBookById(id);
        return new ResponseEntity<BorrowedBook>(res, HttpStatus.OK);

    }
}
