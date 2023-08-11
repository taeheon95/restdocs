package com.taeheon.graal.member.controller;

import com.taeheon.graal.member.model.dto.MemberCreateDTO;
import com.taeheon.graal.member.model.vo.MemberVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/{id}")
    public MemberVO getMember(@PathVariable Long id) {
        MemberVO memberVO = new MemberVO(id, 29, "test");
        return memberVO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MemberVO> postMember(@RequestBody MemberCreateDTO createDTO) {
        MemberVO memberVO = new MemberVO(1L, createDTO.getAge(), createDTO.getName());
        return ResponseEntity
                .created(URI.create("/member/1"))
                .body(memberVO);
    }
}
