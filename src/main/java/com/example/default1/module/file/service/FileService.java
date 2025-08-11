package com.example.default1.module.file.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.default1.module.file.model.FileInfo;
import com.example.default1.module.file.util.FileUtils;
import com.example.default1.module.file.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class FileService {
    private final FileUtils fileUtils;
    private final FileMapper fileMapper;

    public List<FileInfo> getList(List<Long> ids) {
        return fileMapper.findAllIn(ids);
    }

    public FileInfo getById(Long id) {
        return fileMapper.findById(id);
    }

    @Transactional
    public FileInfo upload(MultipartFile mf, String refPath, Long refId) {
        FileInfo fileInfo = fileUtils.upload(mf);
        fileInfo.setRefPath(refPath);
        fileInfo.setRefId(refId);
        fileInfo.setCurrentUserCreateId();
        fileMapper.insert(fileInfo);
        return fileInfo;
    }

    public FileInfo readFile(HttpServletResponse response, Long id) {
        FileInfo fileInfo = fileMapper.findById(id);
        fileUtils.readFile(response, fileInfo);
        return fileInfo;
    }

    public FileInfo download(HttpServletRequest request, HttpServletResponse response, Long id) {
        FileInfo fileInfo = fileMapper.findById(id);
        fileUtils.download(request, response, fileInfo);
        return fileInfo;
    }

    @Transactional
    public void delete(Long id) {
        FileInfo fileInfo = fileMapper.findById(id);
        int result = fileMapper.deleteById(id);
        if(result > 0) fileUtils.delete(fileInfo);
    }

    @Transactional
    public void deleteByRef(FileInfo fileInfo) {
        List<FileInfo> fileInfoList = fileMapper.findBy(fileInfo);
        fileInfoList.forEach(fileMapper::deleteByRef);
    }
}

