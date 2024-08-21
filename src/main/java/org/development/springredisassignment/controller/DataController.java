package org.development.springredisassignment.controller;

import lombok.RequiredArgsConstructor;
import org.development.springredisassignment.model.DataRecord;
import org.development.springredisassignment.service.DataService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.development.springredisassignment.constant.EndpointConstants.*;

@RestController
@RequestMapping(API_PATH)
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @GetMapping(GET_DATA)
    public ResponseEntity<List<DataRecord>> fetchAllData() {
        return new ResponseEntity<>(dataService.fetchAllData(), HttpStatus.OK);
    }

    @PostMapping(ADD_DATA)
    public ResponseEntity<Object> addData(@RequestBody DataRecord record) {
        dataService.addData(record);
        return ResponseEntity.ok().build();
    }

    @GetMapping(GET_DATA_BY_CODE)
    public ResponseEntity<DataRecord> getDataByCode(@PathVariable String code) {
        return ResponseEntity.ok(dataService.getDataByCode(code));
    }

    @DeleteMapping(DELETE_DATA)
    public ResponseEntity<Object> removeAllData() {
        dataService.removeAllData();
        return ResponseEntity.ok().build();
    }

    @PostMapping(UPLOAD_DATA)
    public ResponseEntity<Object> uploadCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            dataService.processCSVFile(file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {

            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping(DOWNLOAD_DATA)
    public ResponseEntity<byte[]> downloadCSV() {
        byte[] data = dataService.downloadAllDataAsCSV();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.csv");
        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }

}
