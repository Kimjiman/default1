package com.example.default1.config;

import com.example.default1.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {
    private final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    private final MediaType jsonMimeType = MediaType.APPLICATION_JSON;

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<?> InternalServerException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에러가 발생했습니다. 관리자에게 문의해주세요."));
    }

    @ExceptionHandler({CustomException.class})
    @ResponseBody
    public ResponseEntity<?> customException(CustomException ex) {
        return ResponseEntity.status(ex.getStatus()).body(Map.of(ex.getStatus(), ex.getMessage()));
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public ResponseEntity<?> BadRequestException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseBody
    public ResponseEntity<?> DataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getCause().getMessage()));
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String NotFoundException(HttpServletRequest request, HttpServletResponse response, NoHandlerFoundException ex, Model model) throws IOException {
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equals("XMLHttpRequest")) {
            jsonConverter.write(Map.of(HttpStatus.NOT_FOUND.value(), ex.getMessage()), jsonMimeType, new ServletServerHttpResponse(response));
            return null;
        } else {
            model.addAttribute("errorCode", 404);
            model.addAttribute("message", ex.getMessage());
            return "error";
        }
    }
}
