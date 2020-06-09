package com.beemiel.springrestdocsexample.controller;

import com.beemiel.springrestdocsexample.entity.Member;
import jdk.vm.ci.meta.Value;
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

    @PutMapping("/put")
    public Member update(@RequestBody Member member) {
        return member;
    }

    @DeleteMapping("/{id}")
    public Member delete(@PathVariable(value = "id")Long id) {
        return null;
    }

}

