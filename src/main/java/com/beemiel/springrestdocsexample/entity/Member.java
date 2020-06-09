package com.beemiel.springrestdocsexample.entity;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private Long id;
    private String name;
    private int age;

}
