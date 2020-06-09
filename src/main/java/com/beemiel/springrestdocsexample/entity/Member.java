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

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Member(Long id, int age) {
        this.id = id;
        this.age = age;
    }
}
