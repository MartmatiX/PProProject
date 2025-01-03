package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.database.entities.RecordEntity;
import com.github.martmatix.pproproject.database.entities.embeddable.RecordType;
import com.github.martmatix.pproproject.database.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    private RecordRepository recordRepository;

    @Override
    public List<RecordEntity> findRecordsByUsernameAndDate(String username, Date date) {
        return recordRepository.findAllByUserAndDate(username, date);
    }

    @Override
    public Optional<RecordEntity> findById(Long id) {
        return recordRepository.findById(id);
    }

    @Override
    public void deleteRecord(RecordEntity recordEntity) {
        recordRepository.delete(recordEntity);
    }

    @Override
    public void saveRecord(RecordEntity recordEntity) {
        recordRepository.save(recordEntity);
    }

    @Override
    public List<RecordEntity> findByRecordType(RecordType recordType) {
        return recordRepository.findAllByRecordType(recordType);
    }

    @Override
    public List<RecordEntity> findRecordsForUser(String username) {
        return recordRepository.findAllByUserOrderByDate(username);
    }

    @Autowired
    public void setRecordRepository(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

}
