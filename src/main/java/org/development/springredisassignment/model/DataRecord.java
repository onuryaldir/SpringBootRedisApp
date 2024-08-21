package org.development.springredisassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@RedisHash("DataRecord")
public class DataRecord implements Serializable {

    @Id
    private String code;
    private String codeListCode;
    private String codeDisplayValue;
    private String longDescription;
    private String fromDate;
    private String toDate;
    private String sortingPriority;
}
