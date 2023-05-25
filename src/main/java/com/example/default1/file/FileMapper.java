package com.example.default1.file;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    FileInfo findById(Long id);
    List<FileInfo> findAllIn(List<Long> ids);
    void insert(FileInfo fileDto);
    int delete(Long id);
    int deleteAllIn(List<Long> ids);
    int deleteByRef(String refPath, Long refId);
}

