package com.relics.backend.repository;

import com.relics.backend.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query(value = "SELECT COUNT() FROM public.vote\n" +
            "WHERE vote.review_id = :id AND vote.is_positive = true", nativeQuery = true)
    void getPositiveVotesQuantity(Long id);

    @Query(value = "SELECT COUNT() FROM public.vote\n" +
            "WHERE vote.review_id = :id AND vote.is_positive = false", nativeQuery = true)
    void getNegativeVotesQuantity(Long id);
}
