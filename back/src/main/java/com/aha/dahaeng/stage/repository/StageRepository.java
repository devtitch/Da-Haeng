package com.aha.dahaeng.stage.repository;

import com.aha.dahaeng.stage.domain.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
* com.aha.dahaeng.stage.repository
* StageRepository.java
* 
* @author 박수빈
* @date 2021-04-30 오후 6:48
* @변경이력
**/

public interface StageRepository extends JpaRepository<Stage, Long> {
}
