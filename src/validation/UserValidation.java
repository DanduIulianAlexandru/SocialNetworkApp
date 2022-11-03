package validation;

import domain.User;
import exceptions.ValidationException;

public class UserValidation {
    public void validate(User entity) throws ValidationException{
        String errors = "";
        if (entity.getId() <= 0L) {
            errors = errors + "Id-ul user-ului invalid\n";
        }

        if (entity.getFirstName().equals("")) {
            errors = errors + "Numele user-ului este invalid\n";
        }

        if (entity.getLastName().equals("")) {
            errors = errors + "Prenumele user-ului este invalid\n";
        }
        if (!errors.equals("")) {
            throw new ValidationException(errors);
        }
    }
}
