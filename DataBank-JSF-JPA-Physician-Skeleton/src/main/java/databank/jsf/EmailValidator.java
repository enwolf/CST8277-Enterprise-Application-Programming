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

@FacesValidator("emailValidator")
public class EmailValidator implements Validator<String> {

	// Really really (!) simple email pattern:  at-least-1-letter, '@', at-least-1-letter
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");

	@Override
	public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {

		if (value == null) {
			FacesMessage msg = new FacesMessage("Email should not be empty", "Invalid email format.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		//TODO 07 - Complete the Matching using the EMAIL_PATTERN above.
		//You can use methods matcher and matches from EMAIL_PATTERN.
		//If match fails, create a new FacesMessage(String, String) object.
		//Use proper error messages.
		//Set the severity of your FacesMessage to FacesMessage.SEVERITY_ERROR.
		//Finally, throw an exception with the FacesMessage in it.
		//To know what exception should be thrown, look at the signature of this method.
	}

}
