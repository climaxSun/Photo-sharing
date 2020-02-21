package com.swb.springcloud.flower.repository;

import com.swb.springcloud.flower.pojo.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long>{

	List<Vote> findVotesByUserIdAndFlowerIdIn(Long userId,List<Long> flowerIds);
}
