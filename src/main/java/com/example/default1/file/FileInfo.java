package com.example.default1.file;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileInfo {
    private Long id;
    private String refPath;
    private Long refId;
    private String oriName;
    private String newName;
    private String savePath;
    private String ext;
    private String type;
    private Long size;
    private LocalDateTime createTime;
    private Long createId;
}
