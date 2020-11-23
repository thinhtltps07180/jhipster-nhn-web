package com.nhn.jhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nhn.jhipster.JhipsterApp;
import com.nhn.jhipster.domain.PokerProfile;
import com.nhn.jhipster.repository.PokerProfileRepository;
import com.nhn.jhipster.service.PokerProfileService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PokerProfileResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PokerProfileResourceIT {
    private static final String DEFAULT_ONGAME_ID = "AAAAAAAAAA";
    private static final String UPDATED_ONGAME_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REG_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REG_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_PHOTO_PATH = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_PATH = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_PATH_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_PATH_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    @Autowired
    private PokerProfileRepository pokerProfileRepository;

    @Autowired
    private PokerProfileService pokerProfileService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPokerProfileMockMvc;

    private PokerProfile pokerProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PokerProfile createEntity(EntityManager em) {
        PokerProfile pokerProfile = new PokerProfile()
            .ongameId(DEFAULT_ONGAME_ID)
            .nickName(DEFAULT_NICK_NAME)
            .regDate(DEFAULT_REG_DATE)
            .lastDate(DEFAULT_LAST_DATE)
            .photoPath(DEFAULT_PHOTO_PATH)
            .photoPathContentType(DEFAULT_PHOTO_PATH_CONTENT_TYPE)
            .ip(DEFAULT_IP);
        return pokerProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PokerProfile createUpdatedEntity(EntityManager em) {
        PokerProfile pokerProfile = new PokerProfile()
            .ongameId(UPDATED_ONGAME_ID)
            .nickName(UPDATED_NICK_NAME)
            .regDate(UPDATED_REG_DATE)
            .lastDate(UPDATED_LAST_DATE)
            .photoPath(UPDATED_PHOTO_PATH)
            .photoPathContentType(UPDATED_PHOTO_PATH_CONTENT_TYPE)
            .ip(UPDATED_IP);
        return pokerProfile;
    }

    @BeforeEach
    public void initTest() {
        pokerProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createPokerProfile() throws Exception {
        int databaseSizeBeforeCreate = pokerProfileRepository.findAll().size();
        // Create the PokerProfile
        restPokerProfileMockMvc
            .perform(
                post("/api/poker-profiles").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pokerProfile))
            )
            .andExpect(status().isCreated());

        // Validate the PokerProfile in the database
        List<PokerProfile> pokerProfileList = pokerProfileRepository.findAll();
        assertThat(pokerProfileList).hasSize(databaseSizeBeforeCreate + 1);
        PokerProfile testPokerProfile = pokerProfileList.get(pokerProfileList.size() - 1);
        assertThat(testPokerProfile.getOngameId()).isEqualTo(DEFAULT_ONGAME_ID);
        assertThat(testPokerProfile.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
        assertThat(testPokerProfile.getRegDate()).isEqualTo(DEFAULT_REG_DATE);
        assertThat(testPokerProfile.getLastDate()).isEqualTo(DEFAULT_LAST_DATE);
        assertThat(testPokerProfile.getPhotoPath()).isEqualTo(DEFAULT_PHOTO_PATH);
        assertThat(testPokerProfile.getPhotoPathContentType()).isEqualTo(DEFAULT_PHOTO_PATH_CONTENT_TYPE);
        assertThat(testPokerProfile.getIp()).isEqualTo(DEFAULT_IP);
    }

    @Test
    @Transactional
    public void createPokerProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pokerProfileRepository.findAll().size();

        // Create the PokerProfile with an existing ID
        pokerProfile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPokerProfileMockMvc
            .perform(
                post("/api/poker-profiles").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pokerProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the PokerProfile in the database
        List<PokerProfile> pokerProfileList = pokerProfileRepository.findAll();
        assertThat(pokerProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOngameIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = pokerProfileRepository.findAll().size();
        // set the field null
        pokerProfile.setOngameId(null);

        // Create the PokerProfile, which fails.

        restPokerProfileMockMvc
            .perform(
                post("/api/poker-profiles").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pokerProfile))
            )
            .andExpect(status().isBadRequest());

        List<PokerProfile> pokerProfileList = pokerProfileRepository.findAll();
        assertThat(pokerProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNickNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pokerProfileRepository.findAll().size();
        // set the field null
        pokerProfile.setNickName(null);

        // Create the PokerProfile, which fails.

        restPokerProfileMockMvc
            .perform(
                post("/api/poker-profiles").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pokerProfile))
            )
            .andExpect(status().isBadRequest());

        List<PokerProfile> pokerProfileList = pokerProfileRepository.findAll();
        assertThat(pokerProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pokerProfileRepository.findAll().size();
        // set the field null
        pokerProfile.setRegDate(null);

        // Create the PokerProfile, which fails.

        restPokerProfileMockMvc
            .perform(
                post("/api/poker-profiles").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pokerProfile))
            )
            .andExpect(status().isBadRequest());

        List<PokerProfile> pokerProfileList = pokerProfileRepository.findAll();
        assertThat(pokerProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pokerProfileRepository.findAll().size();
        // set the field null
        pokerProfile.setLastDate(null);

        // Create the PokerProfile, which fails.

        restPokerProfileMockMvc
            .perform(
                post("/api/poker-profiles").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pokerProfile))
            )
            .andExpect(status().isBadRequest());

        List<PokerProfile> pokerProfileList = pokerProfileRepository.findAll();
        assertThat(pokerProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIpIsRequired() throws Exception {
        int databaseSizeBeforeTest = pokerProfileRepository.findAll().size();
        // set the field null
        pokerProfile.setIp(null);

        // Create the PokerProfile, which fails.

        restPokerProfileMockMvc
            .perform(
                post("/api/poker-profiles").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pokerProfile))
            )
            .andExpect(status().isBadRequest());

        List<PokerProfile> pokerProfileList = pokerProfileRepository.findAll();
        assertThat(pokerProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPokerProfiles() throws Exception {
        // Initialize the database
        pokerProfileRepository.saveAndFlush(pokerProfile);

        // Get all the pokerProfileList
        restPokerProfileMockMvc
            .perform(get("/api/poker-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pokerProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].ongameId").value(hasItem(DEFAULT_ONGAME_ID)))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME)))
            .andExpect(jsonPath("$.[*].regDate").value(hasItem(DEFAULT_REG_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastDate").value(hasItem(DEFAULT_LAST_DATE.toString())))
            .andExpect(jsonPath("$.[*].photoPathContentType").value(hasItem(DEFAULT_PHOTO_PATH_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photoPath").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_PATH))))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)));
    }

    @Test
    @Transactional
    public void getPokerProfile() throws Exception {
        // Initialize the database
        pokerProfileRepository.saveAndFlush(pokerProfile);

        // Get the pokerProfile
        restPokerProfileMockMvc
            .perform(get("/api/poker-profiles/{id}", pokerProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pokerProfile.getId().intValue()))
            .andExpect(jsonPath("$.ongameId").value(DEFAULT_ONGAME_ID))
            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME))
            .andExpect(jsonPath("$.regDate").value(DEFAULT_REG_DATE.toString()))
            .andExpect(jsonPath("$.lastDate").value(DEFAULT_LAST_DATE.toString()))
            .andExpect(jsonPath("$.photoPathContentType").value(DEFAULT_PHOTO_PATH_CONTENT_TYPE))
            .andExpect(jsonPath("$.photoPath").value(Base64Utils.encodeToString(DEFAULT_PHOTO_PATH)))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP));
    }

    @Test
    @Transactional
    public void getNonExistingPokerProfile() throws Exception {
        // Get the pokerProfile
        restPokerProfileMockMvc.perform(get("/api/poker-profiles/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePokerProfile() throws Exception {
        // Initialize the database
        pokerProfileService.save(pokerProfile);

        int databaseSizeBeforeUpdate = pokerProfileRepository.findAll().size();

        // Update the pokerProfile
        PokerProfile updatedPokerProfile = pokerProfileRepository.findById(pokerProfile.getId()).get();
        // Disconnect from session so that the updates on updatedPokerProfile are not directly saved in db
        em.detach(updatedPokerProfile);
        updatedPokerProfile
            .ongameId(UPDATED_ONGAME_ID)
            .nickName(UPDATED_NICK_NAME)
            .regDate(UPDATED_REG_DATE)
            .lastDate(UPDATED_LAST_DATE)
            .photoPath(UPDATED_PHOTO_PATH)
            .photoPathContentType(UPDATED_PHOTO_PATH_CONTENT_TYPE)
            .ip(UPDATED_IP);

        restPokerProfileMockMvc
            .perform(
                put("/api/poker-profiles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPokerProfile))
            )
            .andExpect(status().isOk());

        // Validate the PokerProfile in the database
        List<PokerProfile> pokerProfileList = pokerProfileRepository.findAll();
        assertThat(pokerProfileList).hasSize(databaseSizeBeforeUpdate);
        PokerProfile testPokerProfile = pokerProfileList.get(pokerProfileList.size() - 1);
        assertThat(testPokerProfile.getOngameId()).isEqualTo(UPDATED_ONGAME_ID);
        assertThat(testPokerProfile.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testPokerProfile.getRegDate()).isEqualTo(UPDATED_REG_DATE);
        assertThat(testPokerProfile.getLastDate()).isEqualTo(UPDATED_LAST_DATE);
        assertThat(testPokerProfile.getPhotoPath()).isEqualTo(UPDATED_PHOTO_PATH);
        assertThat(testPokerProfile.getPhotoPathContentType()).isEqualTo(UPDATED_PHOTO_PATH_CONTENT_TYPE);
        assertThat(testPokerProfile.getIp()).isEqualTo(UPDATED_IP);
    }

    @Test
    @Transactional
    public void updateNonExistingPokerProfile() throws Exception {
        int databaseSizeBeforeUpdate = pokerProfileRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPokerProfileMockMvc
            .perform(
                put("/api/poker-profiles").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pokerProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the PokerProfile in the database
        List<PokerProfile> pokerProfileList = pokerProfileRepository.findAll();
        assertThat(pokerProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePokerProfile() throws Exception {
        // Initialize the database
        pokerProfileService.save(pokerProfile);

        int databaseSizeBeforeDelete = pokerProfileRepository.findAll().size();

        // Delete the pokerProfile
        restPokerProfileMockMvc
            .perform(delete("/api/poker-profiles/{id}", pokerProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PokerProfile> pokerProfileList = pokerProfileRepository.findAll();
        assertThat(pokerProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
