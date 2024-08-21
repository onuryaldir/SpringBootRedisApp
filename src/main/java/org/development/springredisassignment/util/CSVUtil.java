package org.development.springredisassignment.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.development.springredisassignment.model.DataRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.development.springredisassignment.constant.CSVFields.*;

public class CSVUtil {

    private static final Logger logger = LoggerFactory.getLogger(CSVUtil.class);

    public static List<DataRecord> parseCSVFile(MultipartFile file) throws IOException {

        List<DataRecord> records;
        logger.info("parsing csv file");
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream());
             CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(reader)) {

            records = csvParser.getRecords().stream()
                    .map(csvRecord -> new DataRecord(
                            csvRecord.get(CODE),
                            csvRecord.get(CODE_LIST_CODE),
                            csvRecord.get(DISPLAY_VALUE),
                            csvRecord.get(LONG_DESCRIPTION),
                            csvRecord.get(FROM_DATE),
                            csvRecord.get(TO_DATE),
                            csvRecord.get(SORTING_PRIORITY)
                    ))
                    .collect(Collectors.toList());
            return records;
        } catch (Exception e) {

            logger.error(e.getMessage());
        }

        return new ArrayList<>();
    }

    public static void writeCSVFile(List<DataRecord> records, OutputStream outputStream) throws IOException {

        logger.info("writing to csv file");

        try (CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(outputStream),
                CSVFormat.DEFAULT.withHeader(CODE, CODE_LIST_CODE, DISPLAY_VALUE,
                        LONG_DESCRIPTION, FROM_DATE, TO_DATE, SORTING_PRIORITY))) {

            records.forEach(record -> {
                try {
                    printRecord(csvPrinter, record);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            });
        }
    }

    private static void printRecord(CSVPrinter csvPrinter, DataRecord record) throws IOException {
        csvPrinter.printRecord(
                record.getCode(),
                record.getCodeListCode(),
                record.getCodeDisplayValue(),
                record.getLongDescription(),
                record.getFromDate(),
                record.getToDate(),
                record.getSortingPriority()
        );
    }
}
