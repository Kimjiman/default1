package com.example.default1.base.file;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.default1.utils.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class FileService {
    private final FileManager fileManager;
    private final FileMapper fileMapper;

    public List<FileInfo> getList(List<Long> ids) {
        return fileMapper.findAllIn(ids);
    }

    public FileInfo getById(Long id) {
        return fileMapper.findById(id);
    }

    @Transactional
    public FileInfo upload(MultipartFile mf, String refPath, Long refId) {
        FileInfo fileInfo = fileManager.upload(mf);
        fileInfo.setRefPath(refPath);
        fileInfo.setRefId(refId);
        fileInfo.setCreateId(SessionUtils.getUserId());
        fileMapper.insert(fileInfo);
        return fileInfo;
    }

    public FileInfo readFile(HttpServletResponse response, Long id) {
        FileInfo fileInfo = fileMapper.findById(id);
        fileManager.readFile(response, fileInfo);
        return fileInfo;
    }

    public FileInfo download(HttpServletRequest request, HttpServletResponse response, Long id) {
        FileInfo fileInfo = fileMapper.findById(id);
        fileManager.download(request, response, fileInfo);
        return fileInfo;
    }

    @Transactional
    public void delete(Long id) {
        FileInfo fileInfo = fileMapper.findById(id);
        int result = fileMapper.deleteById(id);
        if(result > 0) fileManager.delete(fileInfo);
    }

    @Transactional
    public void deleteByRef(FileInfo fileInfo) {
        List<FileInfo> fileInfoList = fileMapper.findBy(fileInfo);
        fileInfoList.forEach(fileMapper::deleteByRef);
    }
}

