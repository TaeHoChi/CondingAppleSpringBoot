package com.apple.shop.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString // 값을 출력하기 위해 사용한다(toString대신 사용).
@Getter
@Setter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "member_username", referencedColumnName = "username")
    private String username;

    private String title;

    private Integer price;


}
