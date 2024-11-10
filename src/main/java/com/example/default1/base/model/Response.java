package com.example.default1.base.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class Response<T> {
	private int status = 0;
	private String message;
	private T response;

	public static <T> Response<T> fail(int status, String message) {
		Response<T> ret = new Response<T>();
		ret.setStatus(status);
		ret.setMessage(message);
		return ret;
	}
	public static <T> Response<T> success(T response) {
		Response<T> ret = new Response<T>();
		ret.setStatus(0);
		ret.setResponse(response);
		return ret;
	}
}
