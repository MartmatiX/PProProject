package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.database.entities.RecordEntity;
import com.github.martmatix.pproproject.database.entities.embeddable.RecordType;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RecordService {

    List<RecordEntity> findRecordsByUsernameAndDate(String username, Date date);

    Optional<RecordEntity> findById(Long id);

    void deleteRecord(RecordEntity recordEntity);

    void saveRecord(RecordEntity recordEntity);

    List<RecordEntity> findByRecordType(RecordType recordType);

    List<RecordEntity> findRecordsForUser(String username);

}
