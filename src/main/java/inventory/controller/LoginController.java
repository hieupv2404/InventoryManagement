package inventory.controller;

import inventory.model.*;
import inventory.service.UserService;
import inventory.util.Constant;
import inventory.validate.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private LoginValidator loginValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        if (binder.getTarget().getClass() == Users.class) {
            binder.setValidator(loginValidator);
        }
    }

    @GetMapping("/login")
    //van chuyen du lieu tu backend len tang view. cua spring ui
    public String login(Model model) {
        List<Users> users = userService.findByProperty("status", 1);
        for (Users user : users) {
            user.setStatus(0);
            userService.updateStatus(user);
        }
        model.addAttribute("loginForm", new Users());
        return "login/login";
    }

    @PostMapping("/processLogin")
    public String processLogin(Model model, @ModelAttribute("loginForm") @Validated Users users, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "login/login";
        }
        List<Users> userList = userService.findByProperty("userName", users.getUserName());

        if (userList.size() != 0) {

                Users user = userList.get(0);
                if (users.getPassword().equals(user.getPassword())) {
                    UserRole userRole = (UserRole) user.getUserRoles().iterator().next();
                    List<Menu> menuList = new ArrayList<>();
                    Role role = userRole.getRole();
                    List<Menu> menuChildList = new ArrayList<>();
                    for (Object obj : role.getAuths()) {
                        Auth auth = (Auth) obj;
                        Menu menu = auth.getMenu();
                        if (menu.getParentId() == 0 && menu.getOrderIndex() != -1 && menu.getActiveFlag() == 1 && auth.getPermission() == 1 && auth.getActiveFlag() == 1) {
                            menu.setIdMenu(menu.getUrl().replace("/", "") + "Id");
                            menuList.add(menu);
                        } else if (menu.getParentId() != 0 && menu.getOrderIndex() != -1 && menu.getActiveFlag() == 1 && auth.getPermission() == 1 && auth.getActiveFlag() == 1) {
                            menu.setIdMenu(menu.getUrl().replace("/", "") + "Id");
                            menuChildList.add(menu);
                        }
                    }
                    for (Menu menu : menuList) {
                        List<Menu> childList = new ArrayList<>();
                        for (Menu childMenu : menuChildList) {
                            if (childMenu.getParentId() == menu.getId()) {
                                childList.add(childMenu);
                            }
                        }
                        menu.setChild(childList);
                    }
                    sortMenu(menuList);
                    for (Menu menu : menuList) {
                        sortMenu(menu.getChild());
                    }
                    user.setStatus(1);
                    userService.updateStatus(user);
                    session.setAttribute(Constant.MENU_SESSION, menuList);
                    session.setAttribute(Constant.USER_INFO, user);

                    session.setAttribute(Constant.MSG_SUCCESS, "Login Successful");
                    return "redirect:/index";

                } else {
                    session.setAttribute(Constant.MSG_ERROR, "PassWord is wrong!!!");
                    return "redirect:/login";
                }

        } else {
            session.setAttribute(Constant.MSG_ERROR, "UserName doesn't exist!!!");
            return "redirect:/login";
        }
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.removeAttribute(Constant.MENU_SESSION);
        session.removeAttribute(Constant.USER_INFO);
        return "redirect:/login";
    }

    private void sortMenu(List<Menu> menus) {
        Collections.sort(menus, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                return o1.getOrderIndex() - o2.getOrderIndex();
            }
        });
    }
}
