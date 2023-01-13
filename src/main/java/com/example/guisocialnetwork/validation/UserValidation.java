package com.example.guisocialnetwork.validation;

import com.example.guisocialnetwork.domain.User;
import com.example.guisocialnetwork.exceptions.ValidationException;

public class UserValidation {
    public void validate(User entity) throws ValidationException{
        String errors = "";
        if(entity.getUsername().equals(""))
            errors += "Username invalid!\n";
        if (entity.getId() <= 0L) {
            errors = errors + "Id-ul user-ului invalid\n";
        }

        if (entity.getFirstName().equals("") || !entity.getFirstName().matches("[a-zA-Z]+")) {
            errors += "Numele user-ului este invalid\n";
        }

        if (entity.getLastName().equals("") || !entity.getLastName().matches("[a-zA-Z]+")) {
            errors += "Prenumele user-ului este invalid\n";
        }

        if(entity.getEmail().equals("") || !entity.getEmail().matches("[a-z]+@[a-z]+.com")){
            errors += "Mail-ul userului este invalid!\n";
        }
        if(entity.getPassword().equals("")){
            errors += "Parola user-ului nu poate fi goala!\n";
        }
        if (!errors.equals("")) {
            throw new ValidationException(errors);
        }
    }
}
