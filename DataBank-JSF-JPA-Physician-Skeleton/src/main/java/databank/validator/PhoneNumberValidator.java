package databank.validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.ValidatorException;
import java.util.regex.Pattern;

@FacesValidator("phoneNumberValidator")
public class PhoneNumberValidator implements Validator<String> {

    // Regular expression to validate phone numbers
    private static final String PHONE_REGEX = "^\\+?[0-9. ()-]{7,25}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    @Override
    public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
        if (value == null || value.isEmpty()) {
            return; // Allow empty values to be validated elsewhere if needed
        }

        if (!PHONE_PATTERN.matcher(value).matches()) {
            FacesMessage msg = new FacesMessage("Invalid phone number format.",
                    "Please enter a valid phone number.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
