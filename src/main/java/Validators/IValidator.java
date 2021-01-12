package Validators;

import model.User;

import java.util.List;

public interface IValidator<InputType> {
    boolean isValid(InputType obj, List<String> feedback);
}
