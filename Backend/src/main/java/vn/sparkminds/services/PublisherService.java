package vn.sparkminds.services;

import java.util.List;
import vn.sparkminds.exceptions.PublisherException;
import vn.sparkminds.model.Publisher;

public interface PublisherService {
    public Publisher createPublisher(Publisher publisher);

    public Publisher findPublisherById(Long publisherId) throws PublisherException;

    public List<Publisher> findAllPublishers();

    public void deletePublisher(Long publisherId) throws PublisherException;
}
