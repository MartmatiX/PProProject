package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.database.entities.RecordEntity;
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

    @Autowired
    public void setRecordRepository(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

}
