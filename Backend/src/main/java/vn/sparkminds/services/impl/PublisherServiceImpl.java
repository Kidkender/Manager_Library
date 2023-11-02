package vn.sparkminds.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.sparkminds.exceptions.PublisherException;
import vn.sparkminds.model.Publisher;
import vn.sparkminds.repositories.PublisherRepository;
import vn.sparkminds.services.PublisherService;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public Publisher createPublisher(Publisher req) {
        Publisher publisher = new Publisher();
        publisher.setName(req.getName());
        publisher.setAddress(req.getAddress());
        publisher.setCreateAt(LocalDateTime.now());
        return publisherRepository.save(publisher);
    }

    @Override
    public void deletePublisher(Long publisherId) throws PublisherException {
        Publisher publisher = findPublisherById(publisherId);
        publisherRepository.deleteById(publisher.getId());
    }

    @Override
    public List<Publisher> findAllPublishers() {

        return publisherRepository.findAll();
    }

    @Override
    public Publisher findPublisherById(Long publisherId) throws PublisherException {
        Publisher opt = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new PublisherException("Publisher not found: " + publisherId));
        return opt;
    }

}
