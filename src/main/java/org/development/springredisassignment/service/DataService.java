package org.development.springredisassignment.service;

import lombok.RequiredArgsConstructor;
import org.development.springredisassignment.model.DataRecord;
import org.development.springredisassignment.repository.DataRecordRepository;
import org.development.springredisassignment.util.CSVUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataService {

    private final DataRecordRepository dataRecordRepository;
    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    public void processCSVFile(MultipartFile file) {
        try {
            logger.info("processing CSV named : " + file.getOriginalFilename());
            List<DataRecord> records = CSVUtil.parseCSVFile(file);
            dataRecordRepository.saveAll(records);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void addData(DataRecord record) {
        logger.info("saving data" + record);

        try {
            dataRecordRepository.save(record);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public List<DataRecord> fetchAllData() {

        List<DataRecord> records = new ArrayList<>();
        logger.info("fetching all data");
        try {
            dataRecordRepository.findAll().forEach(records::add);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return records;
    }

    public byte[] downloadAllDataAsCSV() {
        List<DataRecord> records = fetchAllData();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            CSVUtil.writeCSVFile(records, outputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return outputStream.toByteArray();
    }

    public void removeAllData() {
        logger.info("removing all data");
        try {
            dataRecordRepository.deleteAll();

        } catch (Exception e) {

            logger.error(e.getMessage());
        }
    }

    public DataRecord getDataByCode(String code) {
        logger.info("fetching data by code : " + code);
        try {
            return dataRecordRepository.findById(code).orElse(null);

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new DataRecord();
        }

    }
}
