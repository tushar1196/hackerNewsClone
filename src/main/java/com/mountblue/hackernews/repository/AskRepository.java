package com.mountblue.hackernews.repository;

import com.mountblue.hackernews.model.Ask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AskRepository extends JpaRepository<Ask, Integer> {

}
