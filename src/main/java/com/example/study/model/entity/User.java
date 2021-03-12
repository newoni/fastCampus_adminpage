//DB table 의 이름과 같음.
package com.example.study.model.entity;


import com.example.study.model.enumClass.UserStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"orderGroupList"})
@Builder
@Accessors(chain = true)
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String account;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String email;

    private String phoneNumber;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    // user 1 : N OrderGroup
    @OneToMany(fetch = FetchType.LAZY, mappedBy="user")
    private List<OrderGroup> orderGroupList;


    //Fetch type은 Lazy(지연로딩) Eager(즉시로딩)
    // select * from item where id =?
    // LAZY = from item item0_ where item0_.id=?

    // EAGER = from item item0_ left outer join order_detail orderdetai1_ on item0_.id=orderdetai1_.item_id left outer join user user2_ on orderdetai1_.user_id=user2_.id where item0_.id=?
    // item_id = order_detail.item_id
    // user_id = order_dtail.user_id
    //where item.id =?
    //EAGER는 연관된 것을 모두 물어봄 -> 1:1 에 대해서만 eager를 활용함 1:many 같은 건 다 lazy

}
