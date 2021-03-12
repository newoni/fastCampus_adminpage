package com.example.study.controller;

import com.example.study.model.SearchParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")  //class에 대해서 mapping이 겹치는 건 상관 없음.
public class PostController {
    //HTML from 태그, ajax 검색.
    //http post body -> data
    // json, xml, multiport-form / text-plain

    //    @RequestMapping(method= RequestMethod.POST, path = "/postMethod")
    @PostMapping(value="/postMethod")
    public SearchParam postMethod(@RequestBody SearchParam searchParam){
        return searchParam;
    }

    @PutMapping("/putMethod")
    public void put(){}

    @PatchMapping("/patchMapping")
    public void patch(){}
}
