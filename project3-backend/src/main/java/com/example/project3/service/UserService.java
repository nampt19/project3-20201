package com.example.project3.service;

import com.example.project3.domain.request.UserRequest;
import com.example.project3.domain.response.BaseResponse;
import com.example.project3.domain.response.LoginResponse;
import com.example.project3.domain.response.UserListResponse;
import com.example.project3.domain.response.UserResponse;
import com.example.project3.helper.JwtToken;
import com.example.project3.helper.MD5;
import com.example.project3.helper.Regex;
import com.example.project3.model.*;
import com.example.project3.repository.ActionRepository;
import com.example.project3.repository.PageRepository;
import com.example.project3.repository.RolePageActionRepository;
import com.example.project3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UserService extends CommonService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RolePageActionRepository rolePageActionRepository;

    @Autowired
    PageRepository pageRepository;

    @Autowired
    ActionRepository actionRepository;


    public LoginResponse checkLogin(Login login) {
        LoginResponse loginResponse = new LoginResponse();
        String email = login.getEmail();
        String password = login.getPassword();
        JwtToken jwtToken = new JwtToken();
        if (email.matches(Regex.EMAIL_REGEX)) {
            User user = userRepository.findByEmail(email);
            if (user != null) {
                if (new MD5().decrypt(user.getPassword(), email).equals(password)) {
                    String token = jwtToken.generateToken(user);
                    String passwordMD5 = new MD5().encrypt(password, email);
                    user.setToken(token);
                    user.setPassword(passwordMD5);
                    userRepository.save(user);
                    loginResponse.setCode("100");
                    loginResponse.setMessage("Success");
                    loginResponse.setIdUser(user.getId());
                    loginResponse.setPageActions(getRoleOfUser(user.getRoleId()));
                    loginResponse.setToken(token);
                } else {
                    loginResponse.setCode("103");
                    loginResponse.setMessage("wrong password");
                }
            } else {
                loginResponse.setCode("102");
                loginResponse.setMessage("wrong email");
            }
        } else {
            loginResponse.setCode("101");
            loginResponse.setMessage("wrong format email");
        }
        return loginResponse;
    }

    private List<PageAction> getRoleOfUser(int roleId) {
        List<PageAction> pageActions = new ArrayList<>();
        List<RolePageAction> rolePageActions = rolePageActionRepository.findAllByIdRole(roleId);
        for (RolePageAction rolePageAction : rolePageActions) {
            if (rolePageAction.getIdRole() != roleId) {
                rolePageActions.remove(rolePageAction);
            }
        }

        List<Integer> pageNumber = new ArrayList<>();
        for (RolePageAction rolePageAction : rolePageActions) {
            pageNumber.add(rolePageAction.getIdPage());
        }
        Collections.sort(pageNumber);
        for (int i = 1; i < pageNumber.size(); i++) {
            if (pageNumber.get(i) == pageNumber.get(i - 1)) {
                pageNumber.remove(pageNumber.get(i - 1));
                i--;
            }
        }

        for (int i = 0; i < pageNumber.size(); i++) {
            List<Action> actions = new ArrayList<>();
            Page page = new Page();
            for (int j = 0; j < rolePageActions.size(); j++) {
                page = pageRepository.getOne(pageNumber.get(i));
                if (pageNumber.get(i) == rolePageActions.get(j).getIdPage()) {
                    Action action = actionRepository.getOne(rolePageActions.get(j).getIdAction());
                    actions.add(action);
                }
            }
            PageAction pageAction = new PageAction();
            pageAction.setPage(page);
            pageAction.setActions(actions);
            pageActions.add(pageAction);
        }
        return pageActions;
    }

    public BaseResponse logOut(String token) {
        BaseResponse baseResponse = new BaseResponse();
        User user = userRepository.findByToken(token);
        if (user == null) {
            baseResponse.setCode("101");
            baseResponse.setMessage("Your account was remove or not active !");
        } else {
            user.setToken("");
            userRepository.save(user);
            baseResponse.setCode("100");
            baseResponse.setMessage("Success");
        }

        return baseResponse;
    }

    public UserResponse createUser(String token, UserRequest user) {
        UserResponse userResponse = new UserResponse();
        if (checkToken(token)) {
            User userNew = new User(user.getName(),
                    user.getEmail(),
                    new MD5().encrypt(user.getPassword(), user.getEmail()),
                    user.getPhone(),
                    user.getAddress(),
                    userResponse.getUrl_image());
            if (userNew.getEmail().matches(Regex.EMAIL_REGEX) &&
                    userNew.getPhone().matches(Regex.PHONE_REGREX)) {
                if (userRepository.findByEmail(userNew.getEmail()) == null
                        && userRepository.findByPhone(userNew.getPhone()) == null) {
                    userNew.setCreateByIdUser(1);
                    userNew.setCreateTime(new Date());
                    userNew.setRoleId(user.getIdRole());
                    userNew.setUrlImage(user.getUrl_image());
                    User userCreated = userRepository.save(userNew);
                    userResponse.setCode("100");
                    userResponse.setMessage("create success");
                    userResponse.setIdUser(userCreated.getId());
                    userResponse.setUrl_image("user" + userCreated.getId() + ".jpg");
                    userNew.setUrlImage(userResponse.getUrl_image());
                    userRepository.save(userNew);

                } else {
                    userResponse.setCode("102");
                    userResponse.setMessage("email/phone exist");
                }

            } else {
                userResponse.setCode("101");
                userResponse.setMessage("wrong format email/phone");
            }
        } else {
            userResponse.setCode("111");
            userResponse.setMessage("not token");
        }
        return userResponse;
    }

    public UserResponse updateUser(String token, UserRequest userRequest) {
        UserResponse userResponse = new UserResponse();

        if (checkToken(token)) {
            if (userRequest.getPhone().matches(Regex.PHONE_REGREX)) {
                if (userRepository.findByPhone(userRequest.getPhone()) == null
                        || userRepository.findByPhone(userRequest.getPhone()).getId() == userRequest.getIdUser()) {
                    User user = userRepository.findById(userRequest.getIdUser()).get();

                    user.setName(userRequest.getName());
                    user.setPhone(userRequest.getPhone());
                    user.setAddress(userRequest.getAddress());
                    user.setUrlImage(userRequest.getUrl_image());
                    user.setRoleId(userRequest.getIdRole());

                    User userEdited = userRepository.save(user);
                    userResponse.setCode("100");
                    userResponse.setMessage("edit success");
                    userResponse.setIdUser(userEdited.getId());
                    userResponse.setUrl_image(userEdited.getUrlImage());
                } else {
                    userResponse.setCode("102");
                    userResponse.setMessage("email/phone exist");
                }

            } else {
                userResponse.setCode("101");
                userResponse.setMessage("wrong format email/phone");
            }
        } else {
            userResponse.setCode("111");
            userResponse.setMessage("not token");
        }


        return userResponse;
    }

    public UserListResponse getAllUser(String token) {
        UserListResponse userListResponse = new UserListResponse();
        if (checkToken(token)) {
            List<User> userList = userRepository.findAll();
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getRoleId() == 1) {
                    userList.remove(userList.get(i));
                }
            }
            userListResponse.setUserList(userList);
            userListResponse.setCode("100");
            userListResponse.setMessage("success");
        } else {
            userListResponse.setCode("111");
            userListResponse.setMessage("not token");
        }

        return userListResponse;
    }

    public BaseResponse deleteUser(String token, int id) {
        BaseResponse baseResponse = new BaseResponse();
        if(checkToken(token)){
            User user = userRepository.findById(id).get();
            if (user != null){
                userRepository.delete(user);
                baseResponse.setCode("100");
                baseResponse.setMessage("delete success");
            }else {
                baseResponse.setCode("101");
                baseResponse.setMessage("uses not exist");
            }
        }else {
            baseResponse.setCode("111");
            baseResponse.setMessage("not token");
        }
        return  baseResponse;
    }
}
