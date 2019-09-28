package com.ranking.hachathon.posts;

import com.ranking.hachathon.posts.UnifiedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UnifiedPostRepository extends JpaRepository<UnifiedPost, String> {

    @Query("select p from UnifiedPost p where p.userId in :userIds order by p.publicationDate")
    List<UnifiedPost> findByUserIds(@Param("userIds") Set<String> userIds);

}
