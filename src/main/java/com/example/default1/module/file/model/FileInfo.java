package com.example.default1.module.file.model;

import com.example.default1.base.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileInfo extends BaseModel {
    private String refPath; // 참조 위치 
    private Long refId; // 참조아이디
    private String oriName; // 원래이름
    private String newName; // 새이름
    private String savePath; // 저장경로
    private String ext; // 확장자
    private String type; // mime타입
    private Long size; // 사이즈
}
