package com.example.default1.base.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.default1.exception.CustomException;
import com.example.default1.utils.NetworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class FileManager {
    @Value("${file.store.path}")
    private String storePath;

    /**
     * @param mf 파일
     * @return
     * @throws IOException
     */
    public FileInfo upload(MultipartFile mf) {
        if (mf == null) {
            throw new NullPointerException("파일이 존재하지 않습니다.");
        }

        Calendar time = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String savePath = sdf.format(time.getTime()); // 파일 저장경로
        String storePath = this.storePath + File.separator + savePath; // 파일 실제 저장경로
        String oriName = mf.getOriginalFilename(); // 파일 기존이름
        if (oriName.contains("..")) {
            throw new RuntimeException("파일 이름 에러 '..' 사용 불가");
        }
        String ext = oriName.substring(oriName.lastIndexOf(".") + 1).toLowerCase(); // 파일 확장자
        if ("jsp".equals(ext)) {
            ext = "txt";
        }

        String newName = UUID.randomUUID() + "." + ext; // 파일 변경이름
        String type = mf.getContentType();
        Long size = mf.getSize(); // 파일 크기

        // 1. 지정된 폴더 경로가 없다면, 새로 폴더 생성하기
        File dir = new File(storePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 2. 재조합한 newName으로 파일 객체를 생성하고 파일저장을 한다.
        File file = new File(dir, newName);
        byte[] bytes;
        try {
            bytes = mf.getBytes();
            FileCopyUtils.copy(bytes, file);
        } catch (IOException e) {
            log.error("upload IOException: {}", e.getMessage());
            throw new CustomException(2999, e.getMessage());
        }

        return FileInfo
                .builder()
                .oriName(oriName)
                .newName(newName)
                .ext(ext)
                .savePath(savePath)
                .size(size)
                .type(type)
                .build();
    }

    /**
     * @param mfs 파일리스트
     * @return
     * @throws IOException
     */
    public List<FileInfo> upload(List<MultipartFile> mfs) {
        if (mfs == null || mfs.size() == 0)
            throw new NullPointerException("fileList is null");

        Calendar time = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String savePath = sdf.format(time.getTime()); // 파일 저장경로
        String storePath = this.storePath + File.separator + savePath; // 파일 실제 저장경로

        List<FileInfo> fileInfoList = new ArrayList<>();

        if (null != mfs && mfs.size() > 0) {
            // 1. 지정된 폴더 경로가 없다면, 새로 폴더 생성하기
            File dir = new File(storePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            for (MultipartFile mf : mfs) {
                String oriName = mf.getOriginalFilename(); // 파일 기존이름
                String ext = oriName.substring(oriName.lastIndexOf(".") + 1);  // 파일 확장자
                if ("jsp".equals(ext)) {
                    ext = "txt";
                }

                String newName = UUID.randomUUID().toString() + "." + ext; // 파일 변경이름
                Long size = mf.getSize(); // 파일 크기
                String type = mf.getContentType(); // 파일 타입

                // 2. 재조합한 newName으로 파일 객체를 생성하고 파일저장을 한다.
                File file = new File(dir, newName);
                byte[] bytes;
                try {
                    bytes = mf.getBytes();
                    FileCopyUtils.copy(bytes, file);
                } catch (IOException e) {
                    log.info("upload IOException: {}", e.getMessage());
                    throw new CustomException(2999, e.getMessage());
                }

                FileInfo fileInfo = FileInfo
                        .builder()
                        .oriName(oriName)
                        .newName(newName)
                        .ext(ext)
                        .savePath(savePath)
                        .size(size)
                        .type(type)
                        .build();

                fileInfoList.add(fileInfo);
            }

        }
        return fileInfoList;
    }


    /**
     * @param request
     * @param response
     * @param fileInfo
     */
    public void download(HttpServletRequest request, HttpServletResponse response, FileInfo fileInfo) {
        // fileDown - ContentType 지정
        response.setContentType("application/x-msdownload");

        InputStream is = null; // 주어진 File 객체가 가리키는 파일을 바이트 단위로 읽는 스트림객체를 생성한다.
        OutputStream os = null; // 지정한 파일에 대한 출력스트림을 생성한다.
        PrintWriter out = null; // 자동 Flush기능이 없는 PrintWriter객체 생성

        try {
            if (fileInfo == null) {
                throw new NullPointerException("삭제된 파일 입니다.");
            }

            String storePath = this.storePath + File.separator + fileInfo.getSavePath();
            File file = new File(storePath + File.separator + fileInfo.getNewName());

            // 파일 유무 체크
            boolean isFile = true;
            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                isFile = false;
            }

            // 파일이있다면
            if (isFile) {
                // 다운로드 할 파일의 이름 인코딩(한글처리용), 브라우저에 따른 공백처리
                // 실제 다운로드 시작 부분
                this.setDisposition(fileInfo.getOriName(), NetworkUtils.getBrowser(request), response);

                os = response.getOutputStream();

                // 파일의 크기만큼 바이트 배열 생성
                byte[] b = new byte[(int) file.length()];
                int leng = 0;

                // 파일의 크기 만큼 outputStream에 파일을 작성한다.
                while ((leng = is.read(b)) > 0) {
                    os.write(b, 0, leng);
                }
            } else {
                throw new NullPointerException("삭제된 파일 입니다.");
                //response.setContentType("text/html;charset=UTF-8");
                //out = response.getWriter();
                //out.println("<script language='javascript'>alert('삭제된 파일입니다.');history.back();</script>");
            }
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            if (out != null) {
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            log.error("Exception: {}", e.getMessage());
            throw new CustomException(2999, e.getMessage());
        }
    }


    /**
     * 파일 읽기
     *
     * @param response
     * @param fileInfo
     * @return
     */
    public FileInfo readFile(HttpServletResponse response, FileInfo fileInfo) {
        if (fileInfo == null) return null;

        String storePath = this.storePath +  File.separator + fileInfo.getSavePath();
        File file = new File(storePath + File.separator + fileInfo.getNewName());

        response.setContentType(fileInfo.getType());
        response.setContentLength((int) file.length());

        InputStream is;
        OutputStream os;
        try {
            is = new FileInputStream(file);
            os = response.getOutputStream();
            FileCopyUtils.copy(is, os);

            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
            log.error("read file error: {}", e.getMessage());
            throw new CustomException(2999, e.getMessage());
        }

        return fileInfo;
    }

    /**
     * 파일삭제
     */
    public void delete(FileInfo fileInfo) {
        if (fileInfo == null) {
            throw new NullPointerException("이미 삭제된 파일입니다.");
        }
        String storePath = this.storePath + "/" + fileInfo.getSavePath();
        File file = new File(storePath + "/" + fileInfo.getNewName());
        if (file.exists()) {
            file.delete();
        }
    }

    // 브라우저에 따른 공백처리, 한글 처리용 인코딩
    // TODO: 2023-12-07 업데이트 필요
    public void setDisposition(String fileName, String browser, HttpServletResponse response) {
        String dispositionPrefix = "attachment; filename=";
        String encodedFilename = null;

        try {
            // IE
            if (browser.equalsIgnoreCase("MSIE") // IE 10 이하
                    || browser.equalsIgnoreCase("Trident") // IE 11
                    || browser.equalsIgnoreCase("Edge")) // IE Edge
            {
                encodedFilename = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            }
            // Filefox, Opera, OPR, Chrome, Safari
            else if (browser.equalsIgnoreCase("Firefox") // Firefox
                    || browser.equalsIgnoreCase("Opera") // Opera 구버전
                    || browser.equalsIgnoreCase("OPR") // OPR(Opera 신버전)
                    || browser.equalsIgnoreCase("Chrome") // Chrome
                    || browser.equalsIgnoreCase("Safari")) // Safari
            {
                encodedFilename = new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO_8859_1");
                // Opera
                if (browser.equalsIgnoreCase("Opera") || browser.equalsIgnoreCase("OPR")) {
                    response.setContentType("application/octet-stream;charset=UTF-8");
                }
            } else {
                encodedFilename = new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO_8859_1");
            }
            encodedFilename = encodedFilename.replaceAll(",", "");
            response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);
        } catch (Exception e) {
            log.error("setDisposition Exception: {}", e.getMessage());
            throw new CustomException(2999, e.getMessage());
        }

    }
}

