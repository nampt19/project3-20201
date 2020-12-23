package com.example.project3.service;

import com.example.project3.domain.request.CreateRoleRequest;
import com.example.project3.domain.response.*;
import com.example.project3.model.*;
import com.example.project3.repository.RolePageActionRepository;
import com.example.project3.repository.RoleRepository;
import com.example.project3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService extends  CommonService{
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolePageActionRepository rolePageActionRepository;

    @Autowired
    UserRepository userRepository;

    public CreateRoleResponse createRole(String token, CreateRoleRequest createRoleRequest) {
        CreateRoleResponse baseResponse = new CreateRoleResponse();
        if (checkToken(token)) {
            String nameRole = createRoleRequest.getNameRole();
            List<PageAction> pageActions = createRoleRequest.getPageActions();

            Role role = roleRepository.findByName(nameRole);
            if (role == null) {
                Role roleNew = roleRepository.save(new Role(nameRole));
                List<RolePageAction> rolePageActions = new ArrayList<>();
                for (PageAction pageAction : pageActions) {
                    for (Action action : pageAction.getActions()) {
                        rolePageActions.add(new RolePageAction(roleNew.getId(), pageAction.getPage().getId(), action.getId()));
                    }
                }
                rolePageActionRepository.saveAll(rolePageActions);
                baseResponse.setCode("100");
                baseResponse.setMessage("Success");
                baseResponse.setIdRole(roleNew.getId());
            } else {
                baseResponse.setCode("101");
                baseResponse.setMessage("Tên quyền đã tồn tại !");
            }
        } else {
            baseResponse.setCode("111");
            baseResponse.setMessage("no role");
        }

        return baseResponse;
    }

//    private boolean checkToken(String token) {
//        User user = userRepository.findByToken(token);
//        return user != null;
//    }

    public EditRoleResponse editRole(String token, CreateRoleRequest createRoleRequest) {
        EditRoleResponse baseResponse = new EditRoleResponse();
        if (checkToken(token)) {
            int idRole = createRoleRequest.getIdRole();
            String nameRoleNew = createRoleRequest.getNameRole();
            List<PageAction> pageActions = createRoleRequest.getPageActions();
            Role roleEdit = roleRepository.findById(idRole);
            if (roleEdit != null) {
                if (roleRepository.findByName(nameRoleNew) == null ||
                        roleRepository.findByName(nameRoleNew).getId() == idRole) {
                    // xóa toàn bộ role page action cũ
                    List<RolePageAction> rolePageActions = rolePageActionRepository.findAllByIdRole(roleEdit.getId());
                    for (RolePageAction rolePageAction : rolePageActions) {
                        rolePageActionRepository.delete(rolePageAction);
                    }
                    // thêm role page action mới
                    List<RolePageAction> rolePageActionsNew = new ArrayList<>();
                    for (PageAction pageAction : pageActions) {
                        for (Action action : pageAction.getActions()) {
                            rolePageActionsNew.add(new RolePageAction(roleEdit.getId(), pageAction.getPage().getId(), action.getId()));
                        }
                    }
                    rolePageActionRepository.saveAll(rolePageActionsNew);
                    roleEdit.setName(nameRoleNew);
                    roleRepository.save(roleEdit);

                    baseResponse.setCode("100");
                    baseResponse.setMessage("Success");
                    baseResponse.setIdRole(roleEdit.getId());
                } else {
                    baseResponse.setCode("102");
                    baseResponse.setMessage("Tên quyền đã tồn tại");
                }

            } else {
                baseResponse.setCode("101");
                baseResponse.setMessage("not exist role");
            }
        } else {
            baseResponse.setCode("111");
            baseResponse.setMessage("no role");
        }


        return baseResponse;
    }

    public BaseResponse deleteRole(String token, int idRole) {
        BaseResponse baseResponse = new BaseResponse();
        Role role = roleRepository.findById(idRole);
        if (role != null) {
            List<RolePageAction> rolePageActions = rolePageActionRepository.findAllByIdRole(idRole);

            for (RolePageAction rolePageAction : rolePageActions) {
                rolePageActionRepository.delete(rolePageAction);
            }

            baseResponse.setCode("100");
            baseResponse.setMessage("Success");
            roleRepository.delete(role);
        } else {
            baseResponse.setCode("111");
            baseResponse.setMessage("not role");
        }
        return baseResponse;
    }

    public RoleListResponse getAllRole(String token) {
        RoleListResponse roleListResponse = new RoleListResponse();
        if (checkToken(token)) {
            List<Role> roleList = roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
            if (roleList != null) {
                roleListResponse.setCode("200");
                roleListResponse.setMessage("get All success !");
                roleListResponse.setRoleList(roleList);
            } else {
                roleListResponse.setCode("201");
                roleListResponse.setMessage("get All error!");
            }
        } else {
            roleListResponse.setCode("111");
            roleListResponse.setMessage("not token");
        }
        return roleListResponse;
    }


    public RolePageListActionResponse getRolePageAction(String token, int idRole) {
        // System.out.println(idRole);
        RolePageListActionResponse rolePageListActionResponse = new RolePageListActionResponse();
        if (checkToken(token)) {

            List<RolePageAction> rolePageActionList = rolePageActionRepository.findAllByIdRole(idRole);

            if (roleRepository.findById(idRole) == null) {
                rolePageListActionResponse.setCode("102");
                rolePageListActionResponse.setMessage("Role not exist !");
            } else if (rolePageActionList.isEmpty()) {
                rolePageListActionResponse.setCode("101");
                rolePageListActionResponse.setMessage("PageAction null");
            } else {
                rolePageListActionResponse.setCode("100");
                rolePageListActionResponse.setMessage("success");
                rolePageListActionResponse.setRolePageActionList(rolePageActionList);
            }
        }
        return rolePageListActionResponse;
    }
}
