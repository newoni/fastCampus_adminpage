package com.example.study.controller.api;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.newtwork.Header;
import com.example.study.model.newtwork.request.UserApiRequest;
import com.example.study.model.newtwork.response.UserApiResponse;
import com.example.study.model.newtwork.response.UserOrderInfoResonse;
import com.example.study.service.UserApiLogicService;
import jdk.incubator.jpackage.internal.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserApiController implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserApiLogicService userApiLogicService;

    @GetMapping("/{id}/orderInfo")
    public Header<UserOrderInfoResonse> orderInfo(@PathVariable Long id){
        return userApiLogicService.orderInfo(id);
    }

    @GetMapping("")
    public Header<List<UserApiResponse>> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){
        log.info("{}",pageable);
        return userApiLogicService.search(pageable);
    }

    @Override
    @PostMapping("") //api/user 로
    public Header<UserApiResponse> create(@RequestBody Header<UserApiRequest> request) {
        log.info("{}", request);
        return userApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}") //api/user/{id}
    public Header read(@PathVariable(name = "id") Long id) {
        log.info("read: {}", id);
        return userApiLogicService.read(id);
    }

    @Override
    @PutMapping("") //api/user/
    public Header<UserApiResponse> update(@RequestBody Header<UserApiRequest> request) {
        return userApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}") //api/user/{id}
    public Header delete(@PathVariable Long id) {
        log.info("delete: {}", id);
        return userApiLogicService.delete(id);
    }
}