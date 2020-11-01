package com.mediscreen.patient.exception;

/**
 * Class materializing the ResourceAlreadyExistException.
 */
public class ResourceAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String lastName;
    private String firstName;

   /**
     * Constructs a new ResourceAlreadyExistException.
     *
     * @param lastName the last name of the patient that already exists
     * @param firstName the first name of the patient that already exists
     */
    public ResourceAlreadyExistException(String lastName, String firstName) {
            this.lastName = lastName;
            this.firstName = firstName;
        }
}
