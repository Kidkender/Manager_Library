package vn.sparkminds.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.sparkminds.exceptions.PublisherException;
import vn.sparkminds.model.Publisher;
import vn.sparkminds.repositories.PublisherRepository;
import vn.sparkminds.services.PublisherService;
import vn.sparkminds.services.dto.request.UpdatePublisherRequest;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    @Transactional
    public Publisher createPublisher(Publisher req) {
        Publisher publisher = new Publisher();
        publisher.setName(req.getName());
        publisher.setAddresses(req.getAddresses());
        publisher.setCountry(req.getCountry());
        publisher.setPhone(req.getPhone());
        publisher.setEmail(req.getEmail());
        publisher.setCreateAt(LocalDateTime.now());
        return publisherRepository.save(publisher);
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public Publisher updatePublisher(Long publisherId, UpdatePublisherRequest req)
            throws PublisherException {
        Publisher publisher = findPublisherById(publisherId);
        if (req.getEmail() != null) {
            publisher.setEmail(req.getEmail());
        }
        if (req.getPhone() != null) {
            publisher.setPhone(req.getPhone());
        }
        publisher.setUpdateAt(LocalDateTime.now());
        return publisherRepository.save(publisher);
    }

    @Override
    public List<Publisher> createMultiplePublisher(Publisher[] reqList) {
        List<Publisher> publishers = new ArrayList<>();
        for (Publisher publisher : reqList) {
            Publisher createdPublisher = createPublisher(publisher);
            publishers.add(createdPublisher);
        }
        return publishers;
    }
}
