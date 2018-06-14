package com.siy.protal.controller;

import com.siy.protal.response.GeneticResp;
import com.siy.protal.service.UserService;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping("/list")
    public GeneticResp getUserList(){
        GeneticResp<List> geneticResp = new GeneticResp<>();
        try {
            List userList = userService.getUserList();
            //int i = 10 / 0;
            HashMap<String, String> map = new HashMap<>();
            map.put("aini","woyeaini");
            
            return geneticResp.success(userList,map);
        }catch (Exception e){
            return geneticResp.error();
        }
    }
}
