package com.ranking.hachathon.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnifiedPostRepository extends JpaRepository<String, UnifiedPost> {
}
