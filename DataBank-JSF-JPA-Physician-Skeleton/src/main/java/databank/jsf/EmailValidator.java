/********************************************************************************************************2*4*w*
 * File:  EmailValidator.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.jsf;

import java.util.regex.Pattern;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;



/********************************************************************************************************2*4*w*
 * File:  EmailValidator.java Course Materials CST8277
 *
 * @author Robin Phillis
 * @version 1.0
 * @since 11/10/2024
 */


/**
 * Validator for email input in JSF applications.
 * This class uses a simple regex pattern to validate if the email input follows a basic email format.
 * <p>
 * The validation ensures that the input contains at least one character before and after the '@' symbol.
 * </p>
 * 
 * @author Robin Phillis
 * @version 1.0
 * @since 11/10/2024
 */
@FacesValidator("emailValidator")
public class EmailValidator implements Validator<String> {

    /** A simple regex pattern for validating an email format. */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");

    /**
     * Validates the given email input.
     * 
     * @param context   the {@link FacesContext} for the request being processed.
     * @param component the {@link UIComponent} associated with the email input.
     * @param value     the email value entered by the user.
     * @throws ValidatorException if the email is null or does not match the defined pattern.
     */
    @Override
    public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException 
    {
        if (value == null) 
        {
            FacesMessage msg = new FacesMessage("Email should not be empty", "Invalid email format.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

        // Validate the input against the email pattern.
        if (!EMAIL_PATTERN.matcher(value).matches()) 
        {
            FacesMessage msg = new FacesMessage("Invalid email format", "Please enter a valid email address.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}

