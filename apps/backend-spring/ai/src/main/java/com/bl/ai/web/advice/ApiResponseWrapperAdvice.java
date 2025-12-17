package com.bl.ai.web.advice;

import com.bl.ai.web.annotation.SkipApiWrapper;
import com.bl.ai.web.dto.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

/**
 * Automatically wraps controller return values into ApiResponse if not already wrapped.
 */
@ControllerAdvice(basePackages = "com.bl.ai.web.controller")
public class ApiResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // skip if controller or method annotated with @SkipApiWrapper
        if (hasSkipAnnotation(returnType.getContainingClass().getAnnotations())) return false;
        if (hasSkipAnnotation(returnType.getMethodAnnotations())) return false;
        return true;
    }

    private boolean hasSkipAnnotation(Annotation[] annotations) {
        for (Annotation a : annotations) {
            if (a.annotationType().equals(SkipApiWrapper.class)) return true;
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return ApiResponse.ok(null);
        }
        if (body instanceof ApiResponse) {
            return body;
        }
        return ApiResponse.ok(body);
    }
}
