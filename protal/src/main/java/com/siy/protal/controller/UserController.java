package com.siy.protal.controller;

import com.siy.protal.response.GeneticResp;
import com.siy.protal.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private UserService userService;
    
    @RequestMapping("/list")
    public GeneticResp getUserList(){
        GeneticResp<List> geneticResp = new GeneticResp<>();
            List userList = userService.getUserList();
            int i = 10 / 0;
            Map<String, String> map = new HashMap<>();
            map.put("aini","woyeaini");
            
            return geneticResp.success(userList,map);
    }
}
