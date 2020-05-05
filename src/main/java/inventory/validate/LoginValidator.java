package inventory.validate;

import inventory.model.Users;
import inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class LoginValidator implements Validator {
    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == Users.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Users user = (Users) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"userName","msg.required");
        ValidationUtils.rejectIfEmpty(errors,"password","msg.required");
        if(StringUtils.isEmpty(user.getUserName()) && !StringUtils.isEmpty(user.getPassword())) {
            List<Users> users = userService.findByProperty("userName",user.getUserName());
            if(user!=null && !users.isEmpty()){
                if(!users.get(0).getPassword().equals(user.getPassword())){
                    errors.rejectValue("password","msg.wrong.password");
                } else
                {
                    errors.rejectValue("userName","msg.wrong.username");
                }
            }
        }
    }
}
