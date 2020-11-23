package com.nhn.jhipster.service;

import com.nhn.jhipster.domain.PokerProfile;
import com.nhn.jhipster.repository.PokerProfileRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PokerProfile}.
 */
@Service
@Transactional
public class PokerProfileService {
    private final Logger log = LoggerFactory.getLogger(PokerProfileService.class);

    private final PokerProfileRepository pokerProfileRepository;

    public PokerProfileService(PokerProfileRepository pokerProfileRepository) {
        this.pokerProfileRepository = pokerProfileRepository;
    }

    /**
     * Save a pokerProfile.
     *
     * @param pokerProfile the entity to save.
     * @return the persisted entity.
     */
    public PokerProfile save(PokerProfile pokerProfile) {
        log.debug("Request to save PokerProfile : {}", pokerProfile);
        return pokerProfileRepository.save(pokerProfile);
    }

    /**
     * Get all the pokerProfiles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PokerProfile> findAll() {
        log.debug("Request to get all PokerProfiles");
        return pokerProfileRepository.findAll();
    }

    /**
     * Get one pokerProfile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PokerProfile> findOne(Long id) {
        log.debug("Request to get PokerProfile : {}", id);
        return pokerProfileRepository.findById(id);
    }

    /**
     * Delete the pokerProfile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PokerProfile : {}", id);
        pokerProfileRepository.deleteById(id);
    }
}
