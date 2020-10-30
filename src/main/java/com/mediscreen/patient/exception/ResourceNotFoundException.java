package com.mediscreen.patient.exception;

/**
 * Class materializing the ResourceNotFoundException.
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private long id;
    private String lastName;
    private String firstName;

    /**
     * Constructs a new ResourceNotFoundException.
     *
     * @param id the id of the patient that is not found
     */
    public ResourceNotFoundException(long id) {
        this.id = id;
    }

    /**
     * Constructs a new ResourceNotFoundException.
     *
     * @param lastName the last name of the patient that is not found
     */
    public ResourceNotFoundException(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Constructs a new ResourceNotFoundException.
     *
     * @param lastName the last name of the patient that is not found
     * @param firstName the first name of the patient that is not found
     */
    public ResourceNotFoundException(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
