package com.persoff68.fatodo.exception.attribute.strategy;

import com.persoff68.fatodo.exception.AbstractException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;

@RequiredArgsConstructor
public final class ExceptionAttributeStrategy extends AbstractAttributeStrategy {

    private final ServerHttpRequest request;
    private final Exception exception;

    @Override
    public HttpStatus getStatus() {
        return exception instanceof AbstractException && ((AbstractException) exception).getStatus() != null
                ? ((AbstractException) exception).getStatus()
                : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getFeedbackCode() {
        return exception instanceof AbstractException && ((AbstractException) exception).getFeedBackCode() != null
                ? ((AbstractException) exception).getFeedBackCode()
                : null;
    }

    @Override
    public void addErrorDetails() {
        String message = exception.getMessage();
        if (message != null) {
            errorAttributes.put("message", message);
        }
    }

    @Override
    public void addPath() {
        String path = request.getURI().getPath();
        if (path != null) {
            errorAttributes.put("path", path);
        }
    }

}
