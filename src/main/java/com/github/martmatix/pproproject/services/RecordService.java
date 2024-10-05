package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.database.entities.RecordEntity;

import java.util.Date;
import java.util.List;

public interface RecordService {

    List<RecordEntity> findRecordsByUsernameAndDate(String username, Date date);

}
