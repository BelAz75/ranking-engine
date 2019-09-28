package com.ranking.hachathon.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnifiedAttachmentRepository extends JpaRepository<UnifiedAttachments, String> {
    List<UnifiedAttachments> findAllByPostId(String postId);
}
