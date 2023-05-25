package com.example.default1.file;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @GetMapping
    @ResponseBody
    public List<FileInfo> get(@RequestParam(value="ids[]") List<Long> ids) {
        return fileService.getList(ids);
    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public List<FileInfo> fileUpload(HttpServletRequest request, HttpServletResponse response, List<MultipartFile> files) {
        List<FileInfo> fileInfoList = new ArrayList<>();
        if(files == null) throw new NullPointerException("파일이 없습니다.");
        files.forEach(file -> fileInfoList.add(fileService.upload(request, response, file)));
        return fileInfoList;
    }

    @GetMapping(value="/download/{id}")
    public void download(@PathVariable("id") String strId, HttpServletRequest request, HttpServletResponse response) {
        try {
            fileService.download(request, response, Long.valueOf(strId));
        } catch (Exception e) {
            logger.error("파일 아이디 오류: {}", strId);
        }
    }

    @GetMapping(value="/{id}")
    public void get(@PathVariable("id") String strId, HttpServletResponse response) {
        try {
            fileService.readFile(response, Long.valueOf(strId));
        } catch (Exception e) {
            logger.error("파일 아이디 오류: {}", strId);
        }
    }

    @DeleteMapping(value="/{id}")
    @ResponseBody
    public void delete(@PathVariable("id") String strId) {
        try {
            fileService.delete(Long.valueOf(strId));
        } catch (Exception e) {
            logger.error("파일 아이디 오류: {}", strId);
        }
    }


}

