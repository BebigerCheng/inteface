package com.siy.protal.controller;

import com.siy.protal.persistence.CardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class CardController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private CardMapper cardMapper;
    
    
    @RequestMapping(value = "/importnum",method = RequestMethod.POST)
    public void importCardNumber(@RequestParam("number")String number){
        String[] split = number.split(",");
        for (int i = 0; i < split.length; i++) {
            cardMapper.insertNumber(split[i]);
        }
    }
    
    @RequestMapping(value = "/importname",method = RequestMethod.POST)
    public void importCardname (@RequestParam("name")String number){
        String[] split = number.split(",");
        for (int i = 0; i < split.length; i++) {
            cardMapper.insertName(split[i],i+1);
        }
    }
}
