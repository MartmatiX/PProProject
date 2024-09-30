package com.github.martmatix.pproproject.database.repositories;

import com.github.martmatix.pproproject.database.entities.AnnouncementEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Long> {

    List<AnnouncementEntity> findAllByOrderByIdDesc(PageRequest request);

    void deleteById(Long id);

}
