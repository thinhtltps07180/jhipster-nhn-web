package com.nhn.jhipster.repository;

import com.nhn.jhipster.domain.PokerProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PokerProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PokerProfileRepository extends JpaRepository<PokerProfile, Long> {}
