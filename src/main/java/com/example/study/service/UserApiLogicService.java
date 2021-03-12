package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.User;
import com.example.study.model.newtwork.Header;
import com.example.study.model.newtwork.Pagination;
import com.example.study.model.newtwork.request.UserApiRequest;
import com.example.study.model.newtwork.response.ItemApiResponse;
import com.example.study.model.newtwork.response.OrderGroupApiResponse;
import com.example.study.model.newtwork.response.UserApiResponse;
import com.example.study.model.newtwork.response.UserOrderInfoResonse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderGroupApiLogicService orderGroupApiLogicService;

    @Autowired
    private ItemApiLogicService itemApiLogicService;

    //1. request data 가져오기
    //2. user 생성
    //3. 생성된 데이터를 기준으로 user api response를 만들어서 return

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {
        //1. request data 가져오기
        UserApiRequest userApiRequest = request.getData();

        //2. user 생성
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(userApiRequest.getStatus())
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();

        User newUser = userRepository.save(user);

        //3. 생성된 데이터를 기준으로 user api response를 만들어서 return
        return Header.OK(response(newUser));
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
//        // 방법 1
//        // id -> repository getOne, getById
//        Optional<User> optional = userRepository.findById(id);
//
//        //user -> userApiResponse return
//        //map은 다른 return 형태로 바꿔줌
//        return optional.map(user-> response(user))
//                .orElseGet(() -> Header.ERROR("데이터 없음"));

        //방법 2

        return userRepository.findById(id)
                .map(user-> response(user))
//                .map(userApiResponse -> Header.OK(userApiResponse))
                .map(Header::OK)
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );


    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        // 1. data 가져오기
        UserApiRequest userApiRequest = request.getData();

        // 2. id -> user 데이터 찾기
        Optional<User> optional = userRepository.findById(userApiRequest.getId());

        return optional.map(user -> {
        // 3. update
            user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setStatus(userApiRequest.getStatus())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setEmail(userApiRequest.getEmail())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());
            return user;
        })
        .map(user -> userRepository.save(user)) // update -> newUser
        .map(updateUser-> response(updateUser))     // userApiResponse가 만들어짐
                 .map(Header::OK)
        .orElseGet(()-> Header.ERROR("데이터 없음."));

        // 4. userApiResponse

    }

    @Override
    public Header delete(Long id) {
        // 1. id -> repository -> user
        Optional<User> optional = userRepository.findById(id);

        // repository -? delete
        return optional.map(user ->{
            userRepository.delete(user);

            return Header.OK();
        })
        .orElseGet(()->Header.ERROR("데이터 없음"));

    }

    private UserApiResponse response(User user){
        // user -> userApiResponse

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        // Header + data return

        return userApiResponse;
    }

    public Header<List<UserApiResponse>> search(Pageable pageable) {

        Page<User> users = userRepository.findAll(pageable);

        List<UserApiResponse> userApiResponseList = users.stream()
                .map(user-> response(user))
                .collect(Collectors.toList());

        // List<UserApiResponse>
        // Header<List<UserApiResponse>>

        Pagination pagination = Pagination.builder()
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .currentPage(users.getNumber())
                .currentElements(users.getNumberOfElements())
                .build();
        return Header.OK(userApiResponseList, pagination);
    }

    public Header<UserOrderInfoResonse> orderInfo(Long id) {
        // 필요한 정보
        // user
        User user = userRepository.getOne(id);
        UserApiResponse userApiResponse = response(user);

        // orderGroup
        List<OrderGroup> orderGroupList = user.getOrderGroupList();
        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroupList.stream()
                .map(orderGroup ->{
                    OrderGroupApiResponse orderGroupApiResponse = orderGroupApiLogicService.response(orderGroup).getData();

                    List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
                            .map(detail -> detail.getItem())
                            .map(item -> itemApiLogicService.response(item).getData())
                            .collect(Collectors.toList());

                    orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);
                    return orderGroupApiResponse;

                })
                .collect(Collectors.toList());

        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);

        UserOrderInfoResonse userOrderInfoResonse = UserOrderInfoResonse.builder()
                .userApiResponse(userApiResponse)
                .build();

        return Header.OK(userOrderInfoResonse);

        // item
//        List<OrderGroup> itemList = user.getOrderGroupList();
    }
}
