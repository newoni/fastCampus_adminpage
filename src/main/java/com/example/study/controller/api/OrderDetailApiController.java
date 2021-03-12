package com.example.study.controller.api;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.newtwork.Header;
import com.example.study.model.newtwork.request.OrderDetailApiRequest;
import com.example.study.model.newtwork.response.OrderDetailApiResponse;
import com.example.study.service.OrderDetailApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orderDetail")
public class OrderDetailApiController implements CrudInterface<OrderDetailApiRequest, OrderDetailApiResponse> {

    @Autowired
    private OrderDetailApiLogicService orderDetailApiLogicService;

    @Override
    @PostMapping("") // api/orderDetail
    public Header<OrderDetailApiResponse> create(@RequestBody Header<OrderDetailApiRequest> request) {
        return orderDetailApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}") //api/orderDetail/ 1 ... 1000
    public Header<OrderDetailApiResponse> read(@PathVariable  Long id) {
        return orderDetailApiLogicService.read(id);
    }

    @Override
    @PutMapping("") //api/orderDetail
    public Header<OrderDetailApiResponse> update(Header<OrderDetailApiRequest> request) {
        return null;
    }

    @Override
    @DeleteMapping("{id}") //api/orderDetail/1 ... 1000
    public Header delete(Long id) {
        return null;
    }
}
