package com.SocietyManagementSystem.SocietyManagementSystem_Backend.notice;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NoticeRequest {

    private String title;
    private String content;
    private String category;
    private LocalDate noticeDate;
    private String postedBy;
    private Long blockId; // optional, null = society-wide
}