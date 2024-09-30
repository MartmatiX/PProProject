package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.database.entities.AnnouncementEntity;

import java.util.List;

public interface AnnouncementService {

    List<AnnouncementEntity> findAllAnnouncements(int offset);

    void deleteById(Long id);

    void addAnnouncement(AnnouncementEntity announcementEntity);

}
