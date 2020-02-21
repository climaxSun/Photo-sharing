package com.swb.springcloud.comment_vote.repository;

import com.swb.springcloud.comment_vote.pojo.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReportRepository extends JpaRepository<Report,Long> {

    Page<Report> findAllByTypeIsAndHandleOrderByCreateTimeDesc(String type, boolean handle, Pageable pageable);

    @Transactional
    int deleteAllByReportedId(Long reportedId);

    @Transactional
    @Modifying
    @Query(value = "delete from user where id =?1",nativeQuery = true)
    int deleteUser(Long id);

    @Transactional
    @Modifying
    @Query(value = "delete from flower where user_id =?1",nativeQuery = true)
    int deleteFlowerByUserId(Long id);

    @Transactional
    @Modifying
    @Query(value = "delete from flower where id =?1",nativeQuery = true)
    int deleteFlowerByFlowerId(Long id);

    @Transactional
    @Modifying
    @Query(value = "delete from comment where user_id =?1",nativeQuery = true)
    int deleteCommentByUserId(Long id);

    @Modifying
    @Query(value = "update user set shares_number=shares_number-1 where id in\n" +
            "( select user_id from flower f where f.id=?1 )",nativeQuery = true)
    int subUserShareNumberByFlowerId(Long id);

    @Modifying
    @Query(value = "delete from arcticle where id =?1",nativeQuery = true)
    int deleteArticleByReportedId(Long id);
}
