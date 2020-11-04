package com.mediscreen.patient.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class in charge of managing ResourceNotFoundException.
 */
@ControllerAdvice(basePackages = {"com.mediscreen.patient"})
public class ExceptionHandlerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    /**
     * Method managing the ResourceNotFoundException.
     *
     * @param e The exception
     * @return A ModelAndView object including information for this exception
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleException(ResourceNotFoundException e) {

        logger.error("Error : patient not found");

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.setViewName("errorResourceNotFound");
        return mav;
    }

    /**
     * Method managing the ResourceAlreadyExistException.
     *
     * @param e The exception
     * @return A ModelAndView object including information for this exception
     */
    @ExceptionHandler(ResourceAlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView handleException(ResourceAlreadyExistException e) {

        logger.error("Error : patient already exists");

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.setViewName("errorResourceAlreadyExist");
        return mav;
    }

}
