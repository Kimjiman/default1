package com.example.default1.module.file.mapper;

import com.example.default1.module.file.model.FileInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    FileInfo findById(Long id);
    List<FileInfo> findAllIn(List<Long> ids);
    List<FileInfo> findBy(FileInfo fileInfo);
    void insert(FileInfo fileInfo);
    int deleteById(Long id);
    int deleteAllIn(List<Long> ids);
    int deleteByRef(FileInfo fileInfo);
}

