package com.mediscreen.patient.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * Class managing Feign custom errors.
 */
public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    /**
     * Manage Feign custom exceptions.
     *
     * @param invoker the invoker for the exception
     * @param response the error response
     * @return The default error decoder
     */
    @Override
    public Exception decode(String invoker, Response response) {

        if(response.status()==404) {
            return new ResourceNotFoundException("Error : Resource Not Found");
        }

        return defaultErrorDecoder.decode(invoker, response);
    }
}
