package com.example.default1.base.file;

import com.example.default1.base.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileInfo extends BaseModel {
    private Long id;
    private String refPath;
    private Long refId;
    private String oriName;
    private String newName;
    private String savePath;
    private String ext;
    private String type;
    private Long size;
}
