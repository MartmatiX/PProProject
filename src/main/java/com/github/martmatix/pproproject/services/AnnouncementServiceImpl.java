package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.database.entities.AnnouncementEntity;
import com.github.martmatix.pproproject.database.repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private AnnouncementRepository announcementRepository;

    @Override
    public List<AnnouncementEntity> findAllAnnouncements(int offset) {
        return announcementRepository.findAllByOrderByIdDesc(PageRequest.ofSize(offset));
    }

    @Override
    public void deleteById(Long id) {
        announcementRepository.deleteById(id);
    }

    @Override
    public void addAnnouncement(AnnouncementEntity announcementEntity) {
        announcementRepository.save(announcementEntity);
    }

    @Autowired
    public void setAnnouncementRepository(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }
}
