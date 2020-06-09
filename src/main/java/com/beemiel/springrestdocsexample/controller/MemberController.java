package com.beemiel.springrestdocsexample.controller;

import com.beemiel.springrestdocsexample.entity.Member;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class MemberController {

    @GetMapping("/list")
    public List<Member> listTest() {
        return null;
    }

    @GetMapping("/list/{id}")
    public Member test(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/post")
    public Member postTest(@RequestBody Member member) {
        return member;
    }

}

