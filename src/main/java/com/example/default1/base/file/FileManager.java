package com.example.default1.base.file;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.default1.exception.CustomException;
import com.example.default1.utils.CollectionUtils;
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
        if (mf == null || mf.isEmpty()) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }

        String savePath = generateDynamicPath();
        File dir = new File(storePath + File.separator + savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String oriName = mf.getOriginalFilename();
        String extension = extractSafeExtension(Objects.requireNonNull(oriName));
        String newName = UUID.randomUUID() + "." + extension;

        FileInfo fileInfo = new FileInfo();
        try {
            FileCopyUtils.copy(mf.getInputStream(), new FileOutputStream(new File(dir, newName)));
            fileInfo.setOriName(oriName);
            fileInfo.setNewName(newName);
            fileInfo.setSavePath(savePath);
            fileInfo.setSize(mf.getSize());
            fileInfo.setType(mf.getContentType());
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패하였습니다.");
        }

        return fileInfo;
    }

    /**
     * @param mfs 파일리스트
     * @return
     * @throws IOException
     */
    public List<FileInfo> uploadList(List<MultipartFile> mfs) throws IOException {
        if (CollectionUtils.isEmpty(mfs)) {
            throw new IllegalArgumentException("파일 리스트가 비어있습니다.");
        }
        return mfs.stream()
                .map(this::upload)
                .collect(Collectors.toList());
    }


    /**
     * @param request
     * @param response
     * @param fileInfo
     */
    public void download(HttpServletRequest request, HttpServletResponse response, FileInfo fileInfo) {
        if (fileInfo == null) {
            throw new IllegalArgumentException("다운로드할 파일 정보가 없습니다.");
        }

        String filePath = storePath + File.separator + fileInfo.getSavePath();
        File file = new File(filePath, fileInfo.getNewName());

        if (!file.exists()) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }

        try (InputStream is = new FileInputStream(file); OutputStream os = response.getOutputStream()) {

            response.setContentType(fileInfo.getType());
            response.setContentLength((int) file.length());

            this.setDisposition(fileInfo.getOriName(), NetworkUtils.getBrowser(request), response);

            FileCopyUtils.copy(is, os);

        } catch (IOException e) {
            log.error("파일 다운로드 중 오류가 발생했습니다.", e);
            throw new CustomException(2999, "파일 다운로드 중 오류가 발생했습니다.");
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
        if (fileInfo == null) {
            throw new IllegalArgumentException("읽을 파일 정보가 없습니다.");
        }

        String filePath = storePath + File.separator + fileInfo.getSavePath();
        File file = new File(filePath, fileInfo.getNewName());

        if (!file.exists()) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }

        try (InputStream is = new FileInputStream(file); OutputStream os = response.getOutputStream()) {

            response.setContentType(fileInfo.getType());
            response.setContentLength((int) file.length());

            FileCopyUtils.copy(is, os);

        } catch (IOException e) {
            log.error("파일 읽기 중 오류가 발생했습니다.", e);
            throw new CustomException(2999, "파일 읽기 중 오류가 발생했습니다.");
        }

        return fileInfo;
    }

    /**
     * 파일삭제
     */
    public void delete(FileInfo fileInfo) {
        if (fileInfo == null) {
            throw new IllegalArgumentException("삭제할 파일 정보가 없습니다.");
        }

        String filePath = storePath + File.separator + fileInfo.getSavePath();
        File file = new File(filePath, fileInfo.getNewName());

        if (file.exists() && file.delete()) {
            log.info("파일을 삭제했습니다: {}", file.getAbsolutePath());
        } else {
            log.warn("파일을 삭제할 수 없습니다: {}", file.getAbsolutePath());
        }
    }

    private void setDisposition(String fileName, String browser, HttpServletResponse response) {
        String dispositionPrefix = "attachment; filename=";
        String encodedFilename;

        try {
            if (browser != null && (browser.equalsIgnoreCase("MSIE")
                    || browser.equalsIgnoreCase("Trident")
                    || browser.equalsIgnoreCase("Edge"))) {
                encodedFilename = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            } else {
                encodedFilename = new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO_8859_1");
            }

            response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);
        } catch (UnsupportedEncodingException e) {
            log.error("파일명 인코딩에 실패했습니다: {}", fileName, e);
            throw new CustomException(2999, "파일명 인코딩에 실패했습니다.");
        }
    }


    // 동적 파일 경로 생성
    private String generateDynamicPath() {
        Calendar time = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(time.getTime());
    }

    // 파일 이름에서 안전한 확장자 추출
    private String extractSafeExtension(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        return "jsp".equals(extension) ? "txt" : extension;
    }
}

