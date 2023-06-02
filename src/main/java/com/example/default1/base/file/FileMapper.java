package com.example.default1.base.file;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    FileInfo findById(Long id);
    List<FileInfo> findAllIn(List<Long> ids);
    List<FileInfo> findListBy(FileInfo fileInfo);
    void insert(FileInfo fileInfo);
    int delete(Long id);
    int deleteAllIn(List<Long> ids);
    int deleteByRef(FileInfo fileInfo);
}

