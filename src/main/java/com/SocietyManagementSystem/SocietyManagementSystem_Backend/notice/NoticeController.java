package com.SocietyManagementSystem.SocietyManagementSystem_Backend.notice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/add")
    public ResponseEntity<NoticeResponse> addNotice(@RequestBody NoticeRequest request) {
        return ResponseEntity.ok(noticeService.addNotice(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponse> getNoticeById(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.getNoticeById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<NoticeResponse>> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    @GetMapping("/block/{blockId}")
    public ResponseEntity<List<NoticeResponse>> getNoticesByBlock(@PathVariable Long blockId) {
        return ResponseEntity.ok(noticeService.getNoticesByBlock(blockId));
    }

    @GetMapping("/society-wide")
    public ResponseEntity<List<NoticeResponse>> getSocietyWideNotices() {
        return ResponseEntity.ok(noticeService.getSocietyWideNotices());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<NoticeResponse> updateNotice(@PathVariable Long id,
                                                       @RequestBody NoticeRequest request) {
        return ResponseEntity.ok(noticeService.updateNotice(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotice(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.deleteNotice(id));
    }
}