package vn.sparkminds.services;

import java.util.List;
import vn.sparkminds.exceptions.PublisherException;
import vn.sparkminds.model.Publisher;
import vn.sparkminds.services.dto.request.UpdatePublisherRequest;

public interface PublisherService {
    public Publisher createPublisher(Publisher publisher);

    public Publisher findPublisherById(Long publisherId) throws PublisherException;

    public Publisher updatePublisher(Long publisherId, UpdatePublisherRequest req)
            throws PublisherException;

    public List<Publisher> createMultiplePublisher(Publisher[] reqList);

    public List<Publisher> findAllPublishers();

    public void deletePublisher(Long publisherId) throws PublisherException;
}
