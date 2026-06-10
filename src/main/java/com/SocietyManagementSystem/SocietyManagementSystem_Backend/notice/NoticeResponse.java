package com.SocietyManagementSystem.SocietyManagementSystem_Backend.notice;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NoticeResponse {

    private Long noticeId;
    private String title;
    private String content;
    private String category;
    private LocalDate noticeDate;
    private String postedBy;
    private Long blockId;       // null if society-wide
    private String blockName;   // null if society-wide

    public NoticeResponse(Long noticeId, String title, String content,
                          String category, LocalDate noticeDate, String postedBy,
                          Long blockId, String blockName) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.noticeDate = noticeDate;
        this.postedBy = postedBy;
        this.blockId = blockId;
        this.blockName = blockName;
    }


}