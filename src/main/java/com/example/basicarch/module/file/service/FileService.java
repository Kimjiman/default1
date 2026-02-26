package com.example.basicarch.module.file.service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.basicarch.module.file.entity.FileInfo;
import com.example.basicarch.module.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class FileService {
    private final FileStorageService fileStorageService;
    private final FileRepository fileRepository;

    public List<FileInfo> getList(List<Long> ids) {
        return fileRepository.findAllByIdIn(ids);
    }

    public FileInfo getById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    @Transactional
    public FileInfo upload(MultipartFile mf, String refPath, Long refId) {
        FileInfo fileInfo = fileStorageService.upload(mf);
        fileInfo.setRefPath(refPath);
        fileInfo.setRefId(refId);
        return fileRepository.save(fileInfo);
    }

    public FileInfo readFile(HttpServletResponse response, Long id) {
        FileInfo fileInfo = fileRepository.findById(id).orElse(null);
        fileStorageService.readFile(response, fileInfo);
        return fileInfo;
    }

    public FileInfo download(HttpServletRequest request, HttpServletResponse response, Long id) {
        FileInfo fileInfo = fileRepository.findById(id).orElse(null);
        fileStorageService.download(request, response, fileInfo);
        return fileInfo;
    }

    @Transactional
    public void delete(Long id) {
        FileInfo fileInfo = fileRepository.findById(id).orElse(null);
        if (fileInfo != null) {
            fileRepository.deleteById(id);
            fileStorageService.delete(fileInfo);
        }
    }

    @Transactional
    public void deleteByRef(String refPath, Long refId) {
        List<FileInfo> fileInfoList = fileRepository.findByRefPathAndRefId(refPath, refId);
        fileInfoList.forEach(fileInfo -> fileStorageService.delete(fileInfo));
        fileRepository.deleteByRefPathAndRefId(refPath, refId);
    }
}
