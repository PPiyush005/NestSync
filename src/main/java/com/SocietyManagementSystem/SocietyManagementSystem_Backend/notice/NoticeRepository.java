package com.SocietyManagementSystem.SocietyManagementSystem_Backend.notice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByBlock_BlockId(Long blockId);

    List<Notice> findByBlockIsNull(); // society-wide notices
}