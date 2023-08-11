package com.taeheon.graal.member.model.vo;

import lombok.Getter;

@Getter
public class MemberVO {
    private Long memberId;
    private Integer age;
    private String name;

    public MemberVO(Long memberId, Integer age, String name) {
        this.memberId = memberId;
        this.age = age;
        this.name = name;
    }
}
