package com.example.default1.file;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.default1.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileService {

    @Autowired
    private FileManager fileManager;
    @Autowired
    private FileMapper fileMapper;

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
        fileInfo.setCreateId(SessionUtil.getUserId());
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
        int result = fileMapper.delete(id);
        if(result > 0) fileManager.delete(fileInfo);
    }

    @Transactional
    public void deleteByRef(FileInfo fileInfo) {
        List<FileInfo> fileInfoList = fileMapper.findListBy(fileInfo);
        fileInfoList.forEach(it -> fileMapper.deleteByRef(it));
    }
}

