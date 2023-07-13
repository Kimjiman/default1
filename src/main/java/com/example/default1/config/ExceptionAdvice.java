package com.example.default1.config;

import com.example.default1.base.model.Response;
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

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {
    private final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    private final MediaType jsonMimeType = MediaType.APPLICATION_JSON;

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response<?> InternalServerException(Exception ex) {
        return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler({CustomException.class})
    @ResponseBody
    public Response<?> customException(CustomException ex) {
        return Response.fail(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public Response<?> BadRequestException(RuntimeException ex) {
        return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseBody
    public Response<?> DataIntegrityViolationException(DataIntegrityViolationException ex) {
        return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getCause().getMessage());
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String NotFoundException(HttpServletRequest request, HttpServletResponse response, NoHandlerFoundException ex, Model model) throws IOException {
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equals("XMLHttpRequest")) {
            jsonConverter.write(Response.fail(HttpStatus.NOT_FOUND.value(), ex.getCause().getMessage()), jsonMimeType, new ServletServerHttpResponse(response));
            return null;
        } else {
            model.addAttribute("errorCode", 404);
            model.addAttribute("message", ex.getMessage());
            return "error";
        }
    }
}
