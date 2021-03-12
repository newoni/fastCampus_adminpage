package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.Category;
import com.example.study.model.newtwork.Header;
import com.example.study.model.newtwork.request.CategoryApiRequest;
import com.example.study.model.newtwork.response.CategoryApiResponse;
import com.example.study.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.study.model.newtwork.Header.OK;

@Service
public class CategoryApiLogicService implements CrudInterface<CategoryApiRequest, CategoryApiResponse> {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> request) {
        CategoryApiRequest body = request.getData();
        System.out.println("hi");
        Category category = Category.builder()
                .type(body.getType())
                .title(body.getTitle())
                .build();

        Category newCategory = categoryRepository.save(category);

        return response(newCategory);
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {
        return categoryRepository.findById(id)
                .map(category -> response(category))
                .orElseGet(()-> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        return null;
    }

    public Header<CategoryApiResponse> response(Category category){
        CategoryApiResponse body = CategoryApiResponse.builder()
                .id(category.getId())
                .type(category.getType())
                .title(category.getTitle())
                .build();

        return Header.OK(body);
    }
}
