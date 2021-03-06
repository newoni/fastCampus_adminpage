package com.example.study.repository;
import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import com.example.study.model.enumClass.UserStatus;
import jdk.vm.ci.meta.Local;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;


public class UserRepositoryTest extends StudyApplicationTests{

    // Dependency Injection (DI)
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        String account = "Test03";
        String password = "Test03";
        UserStatus status =UserStatus.REGISTERED;
        String email =" Test01@gmail.com";
        String phoneNumber = "010-1111-3333";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);
//        user.setCreatedAt(createdAt);
//        user.setCreatedBy(createdBy);

        User u = User.builder()
                .account(account)
                .password(password)
                .status(status)
                .email(email)
                .build();

        User newUser = userRepository.save(user);

        Assertions.assertNotNull(newUser);

    }

    @Test
    @Transactional
    public void read(){
        String phoneNumber = "010-1111-2222";
        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc(phoneNumber);

        //chain pattern
//        user.setEmail("")
//                .setPhoneNumber("")
//                .setStatus("");

        if(user != null) {
            user.getOrderGroupList().stream().forEach(orderGroup -> {
                System.out.println("------------?????? ??????-----------");
                System.out.println("?????????: " + orderGroup.getRevName());
                System.out.println("?????????: " + orderGroup.getRevAddress());
                System.out.println("?????????: " + orderGroup.getTotalPrice());
                System.out.println("?????????: " + orderGroup.getTotalQuantity());

                System.out.println("------------?????? ??????-----------");
                orderGroup.getOrderDetailList().forEach(orderDetail -> {
                    System.out.println("???????????? ?????? :"+orderDetail.getItem().getPartner().getName());
                    System.out.println("???????????? ???????????? :" + orderDetail.getItem().getPartner().getCategory().getTitle());

                    System.out.println("?????? ??????:" +orderDetail.getItem().getName());
                    System.out.println("???????????? ??????: " +orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("????????? ?????? : " +orderDetail.getStatus());
                    System.out.println("??????????????????: " +orderDetail.getArrivalDate());
                });
            });
        }
        Assertions.assertNotNull(user);
    }

    @Test
    public void update(){
        Optional<User> user = userRepository.findById(2L);
        user.ifPresent(selectUser->{
            selectUser.setAccount("pppp");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("update method()");

            userRepository.save(selectUser);
        });
    }

    @Test
    @Transactional
    public void delete(){
        Optional<User> user = userRepository.findById(3L);

//        ????????? ????????????????????? ??? (Junit package??? AssertTrue ??????)
//        Assert.assertTrue(user.isPresent());

        user.ifPresent(selectUser->{
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(3L);

//        if(deleteUser.isPresent()){
//            System.out.println("????????? ?????? : " +deleteUser.get());
//        }else{
//            System.out.println("????????? ?????? ????????? ??????");
//        }

        //        ????????? ???????????? ?????? ??? (Junit package??? AssertFalse ??????)
//        Assert.assertFalse(user.isPresent()); // false
    }

}
