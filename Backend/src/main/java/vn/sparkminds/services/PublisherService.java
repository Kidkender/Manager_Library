package vn.sparkminds.services;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.sparkminds.model.Publisher;

public interface PublisherService extends JpaRepository<Publisher, Long> {
    public Publisher createPublisher(Publisher publisher);

    public Publisher findPublisherById(Long publisherId);

    public Publisher findPublisherByName(String publisherName);

    public Publisher findAllPublishers();

    public String deletePublisher(Long publisherId);
}
