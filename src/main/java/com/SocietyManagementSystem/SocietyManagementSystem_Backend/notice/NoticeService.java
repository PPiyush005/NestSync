package com.SocietyManagementSystem.SocietyManagementSystem_Backend.notice;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.block.Blocks;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.block.BlockRepository;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.security.AuthHelper;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final BlockRepository blockRepository;
    private final AuthHelper authHelper;

    public NoticeService(NoticeRepository noticeRepository,
                         BlockRepository blockRepository,
                         AuthHelper authHelper) {
        this.noticeRepository = noticeRepository;
        this.blockRepository = blockRepository;
        this.authHelper = authHelper;
    }

    // addNotice — clean, no nested method
    public NoticeResponse addNotice(NoticeRequest request) {
        Notice notice = new Notice();
        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setCategory(request.getCategory());
        notice.setNoticeDate(request.getNoticeDate());
        notice.setPostedBy(request.getPostedBy());

        if (request.getBlockId() != null) {
            validateSecretaryBlockAccess(request.getBlockId()); //  just a method call
            Blocks block = blockRepository.findById(request.getBlockId())
                    .orElseThrow(() -> new RuntimeException("Block not found with id: " + request.getBlockId()));
            notice.setBlock(block);
        }

        Notice saved = noticeRepository.save(notice);
        return toResponse(saved);
    }

    // validateSecretaryBlockAccess — separate private method at class level
    private void validateSecretaryBlockAccess(Long blockId) {
        User currentUser = authHelper.getCurrentUser();
        if (currentUser.getRole().name().equals("SECRETARY")) {
            if (!currentUser.getBlockId().equals(blockId)) {
                throw new RuntimeException("Access denied: You can only manage your own block");
            }
        }
    }

    // GetById
    public NoticeResponse getNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found with id: " + id));
        return toResponse(notice);
    }

    // Get All
    public List<NoticeResponse> getAllNotices() {
        return noticeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Block
    public List<NoticeResponse> getNoticesByBlock(Long blockId) {
        blockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Block not found with id: " + blockId));
        return noticeRepository.findByBlock_BlockId(blockId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get Society-Wide Notices
    public List<NoticeResponse> getSocietyWideNotices() {
        return noticeRepository.findByBlockIsNull()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public NoticeResponse updateNotice(Long id, NoticeRequest request) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found with id: " + id));

        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setCategory(request.getCategory());
        notice.setNoticeDate(request.getNoticeDate());
        notice.setPostedBy(request.getPostedBy());

        if (request.getBlockId() != null) {
            validateSecretaryBlockAccess(request.getBlockId());
            Blocks block = blockRepository.findById(request.getBlockId())
                    .orElseThrow(() -> new RuntimeException("Block not found with id: " + request.getBlockId()));
            notice.setBlock(block);
        } else {
            notice.setBlock(null);
        }

        return toResponse(noticeRepository.save(notice));
    }

    // Delete Notice
    public String deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found with id: " + id));
        noticeRepository.delete(notice);
        return "Notice deleted successfully with id: " + id;
    }

    // Helper: Entity -> Response
    private NoticeResponse toResponse(Notice notice) {
        Long blockId = null;
        String blockName = null;
        if (notice.getBlock() != null) {
            blockId = notice.getBlock().getBlockId();
            blockName = notice.getBlock().getBlockName();
        }
        return new NoticeResponse(
                notice.getNoticeId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getCategory(),
                notice.getNoticeDate(),
                notice.getPostedBy(),
                blockId,
                blockName
        );
    }
}