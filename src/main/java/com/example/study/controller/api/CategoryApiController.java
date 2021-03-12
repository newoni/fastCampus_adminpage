package com.example.study.controller.api;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.newtwork.Header;
import com.example.study.model.newtwork.request.CategoryApiRequest;
import com.example.study.model.newtwork.response.CategoryApiResponse;
import com.example.study.service.CategoryApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryApiController implements CrudInterface<CategoryApiRequest, CategoryApiResponse> {

    @Autowired
    private CategoryApiLogicService categoryApiLogicService;

    @Override
    @PostMapping("") //api/category
    public Header<CategoryApiResponse> create(@RequestBody Header<CategoryApiRequest> request) {
        System.out.println("hi");
        return categoryApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}") // api/category/ 1 ... 1000
    public Header<CategoryApiResponse> read(@PathVariable Long id) {
        return categoryApiLogicService.read(id);
    }

    @Override
    @PutMapping("") // api/category
    public Header<CategoryApiResponse> update(@RequestBody Header<CategoryApiRequest> request) {
        return null;
    }

    @Override
    @DeleteMapping("{id}") //api/category/1 ... 1000
    public Header delete(Long id) {
        return null;
    }
}
