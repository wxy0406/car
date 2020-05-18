package com.jiuqi.car.web.rest;

import com.jiuqi.car.CarApp;
import com.jiuqi.car.domain.RentalInformation;
import com.jiuqi.car.repository.RentalInformationRepository;
import com.jiuqi.car.service.RentalInformationService;
import com.jiuqi.car.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.jiuqi.car.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RentalInformationResource} REST controller.
 */
@SpringBootTest(classes = CarApp.class)
public class RentalInformationResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PREPARED_PEOPLE = "AAAAAAAAAA";
    private static final String UPDATED_PREPARED_PEOPLE = "BBBBBBBBBB";

    private static final String DEFAULT_RENT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_RENT_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_RENTAL_UNITS = "AAAAAAAAAA";
    private static final String UPDATED_RENTAL_UNITS = "BBBBBBBBBB";

    private static final String DEFAULT_BUILDER = "AAAAAAAAAA";
    private static final String UPDATED_BUILDER = "BBBBBBBBBB";

    private static final Integer DEFAULT_RENTAL = 0;
    private static final Integer UPDATED_RENTAL = 1;

    private static final Integer DEFAULT_LEASE_TIME = 0;
    private static final Integer UPDATED_LEASE_TIME = 1;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private RentalInformationRepository rentalInformationRepository;

    @Autowired
    private RentalInformationService rentalInformationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRentalInformationMockMvc;

    private RentalInformation rentalInformation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RentalInformationResource rentalInformationResource = new RentalInformationResource(rentalInformationService);
        this.restRentalInformationMockMvc = MockMvcBuilders.standaloneSetup(rentalInformationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is TraversalTree static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RentalInformation createEntity(EntityManager em) {
        RentalInformation rentalInformation = new RentalInformation()
            .code(DEFAULT_CODE)
            .preparedPeople(DEFAULT_PREPARED_PEOPLE)
            .rentDate(DEFAULT_RENT_DATE)
            .rentalUnits(DEFAULT_RENTAL_UNITS)
            .builder(DEFAULT_BUILDER)
            .rental(DEFAULT_RENTAL)
            .leaseTime(DEFAULT_LEASE_TIME)
            .remarks(DEFAULT_REMARKS);
        return rentalInformation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is TraversalTree static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RentalInformation createUpdatedEntity(EntityManager em) {
        RentalInformation rentalInformation = new RentalInformation()
            .code(UPDATED_CODE)
            .preparedPeople(UPDATED_PREPARED_PEOPLE)
            .rentDate(UPDATED_RENT_DATE)
            .rentalUnits(UPDATED_RENTAL_UNITS)
            .builder(UPDATED_BUILDER)
            .rental(UPDATED_RENTAL)
            .leaseTime(UPDATED_LEASE_TIME)
            .remarks(UPDATED_REMARKS);
        return rentalInformation;
    }

    @BeforeEach
    public void initTest() {
        rentalInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createRentalInformation() throws Exception {
        int databaseSizeBeforeCreate = rentalInformationRepository.findAll().size();

        // Create the RentalInformation
        restRentalInformationMockMvc.perform(post("/api/rental-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalInformation)))
            .andExpect(status().isCreated());

        // Validate the RentalInformation in the database
        List<RentalInformation> rentalInformationList = rentalInformationRepository.findAll();
        assertThat(rentalInformationList).hasSize(databaseSizeBeforeCreate + 1);
        RentalInformation testRentalInformation = rentalInformationList.get(rentalInformationList.size() - 1);
        assertThat(testRentalInformation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRentalInformation.getPreparedPeople()).isEqualTo(DEFAULT_PREPARED_PEOPLE);
        assertThat(testRentalInformation.getRentDate()).isEqualTo(DEFAULT_RENT_DATE);
        assertThat(testRentalInformation.getRentalUnits()).isEqualTo(DEFAULT_RENTAL_UNITS);
        assertThat(testRentalInformation.getBuilder()).isEqualTo(DEFAULT_BUILDER);
        assertThat(testRentalInformation.getRental()).isEqualTo(DEFAULT_RENTAL);
        assertThat(testRentalInformation.getLeaseTime()).isEqualTo(DEFAULT_LEASE_TIME);
        assertThat(testRentalInformation.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createRentalInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rentalInformationRepository.findAll().size();

        // Create the RentalInformation with an existing ID
        rentalInformation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRentalInformationMockMvc.perform(post("/api/rental-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalInformation)))
            .andExpect(status().isBadRequest());

        // Validate the RentalInformation in the database
        List<RentalInformation> rentalInformationList = rentalInformationRepository.findAll();
        assertThat(rentalInformationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentalInformationRepository.findAll().size();
        // set the field null
        rentalInformation.setCode(null);

        // Create the RentalInformation, which fails.

        restRentalInformationMockMvc.perform(post("/api/rental-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalInformation)))
            .andExpect(status().isBadRequest());

        List<RentalInformation> rentalInformationList = rentalInformationRepository.findAll();
        assertThat(rentalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPreparedPeopleIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentalInformationRepository.findAll().size();
        // set the field null
        rentalInformation.setPreparedPeople(null);

        // Create the RentalInformation, which fails.

        restRentalInformationMockMvc.perform(post("/api/rental-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalInformation)))
            .andExpect(status().isBadRequest());

        List<RentalInformation> rentalInformationList = rentalInformationRepository.findAll();
        assertThat(rentalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentalInformationRepository.findAll().size();
        // set the field null
        rentalInformation.setRentDate(null);

        // Create the RentalInformation, which fails.

        restRentalInformationMockMvc.perform(post("/api/rental-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalInformation)))
            .andExpect(status().isBadRequest());

        List<RentalInformation> rentalInformationList = rentalInformationRepository.findAll();
        assertThat(rentalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRentalUnitsIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentalInformationRepository.findAll().size();
        // set the field null
        rentalInformation.setRentalUnits(null);

        // Create the RentalInformation, which fails.

        restRentalInformationMockMvc.perform(post("/api/rental-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalInformation)))
            .andExpect(status().isBadRequest());

        List<RentalInformation> rentalInformationList = rentalInformationRepository.findAll();
        assertThat(rentalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBuilderIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentalInformationRepository.findAll().size();
        // set the field null
        rentalInformation.setBuilder(null);

        // Create the RentalInformation, which fails.

        restRentalInformationMockMvc.perform(post("/api/rental-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalInformation)))
            .andExpect(status().isBadRequest());

        List<RentalInformation> rentalInformationList = rentalInformationRepository.findAll();
        assertThat(rentalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRentalIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentalInformationRepository.findAll().size();
        // set the field null
        rentalInformation.setRental(null);

        // Create the RentalInformation, which fails.

        restRentalInformationMockMvc.perform(post("/api/rental-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalInformation)))
            .andExpect(status().isBadRequest());

        List<RentalInformation> rentalInformationList = rentalInformationRepository.findAll();
        assertThat(rentalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLeaseTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentalInformationRepository.findAll().size();
        // set the field null
        rentalInformation.setLeaseTime(null);

        // Create the RentalInformation, which fails.

        restRentalInformationMockMvc.perform(post("/api/rental-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalInformation)))
            .andExpect(status().isBadRequest());

        List<RentalInformation> rentalInformationList = rentalInformationRepository.findAll();
        assertThat(rentalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRentalInformations() throws Exception {
        // Initialize the database
        rentalInformationRepository.saveAndFlush(rentalInformation);

        // Get all the rentalInformationList
        restRentalInformationMockMvc.perform(get("/api/rental-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rentalInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].preparedPeople").value(hasItem(DEFAULT_PREPARED_PEOPLE.toString())))
            .andExpect(jsonPath("$.[*].rentDate").value(hasItem(DEFAULT_RENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].rentalUnits").value(hasItem(DEFAULT_RENTAL_UNITS.toString())))
            .andExpect(jsonPath("$.[*].builder").value(hasItem(DEFAULT_BUILDER.toString())))
            .andExpect(jsonPath("$.[*].rental").value(hasItem(DEFAULT_RENTAL)))
            .andExpect(jsonPath("$.[*].leaseTime").value(hasItem(DEFAULT_LEASE_TIME)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }
    
    @Test
    @Transactional
    public void getRentalInformation() throws Exception {
        // Initialize the database
        rentalInformationRepository.saveAndFlush(rentalInformation);

        // Get the rentalInformation
        restRentalInformationMockMvc.perform(get("/api/rental-informations/{id}", rentalInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rentalInformation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.preparedPeople").value(DEFAULT_PREPARED_PEOPLE.toString()))
            .andExpect(jsonPath("$.rentDate").value(DEFAULT_RENT_DATE.toString()))
            .andExpect(jsonPath("$.rentalUnits").value(DEFAULT_RENTAL_UNITS.toString()))
            .andExpect(jsonPath("$.builder").value(DEFAULT_BUILDER.toString()))
            .andExpect(jsonPath("$.rental").value(DEFAULT_RENTAL))
            .andExpect(jsonPath("$.leaseTime").value(DEFAULT_LEASE_TIME))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRentalInformation() throws Exception {
        // Get the rentalInformation
        restRentalInformationMockMvc.perform(get("/api/rental-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRentalInformation() throws Exception {
        // Initialize the database
        rentalInformationService.save(rentalInformation);

        int databaseSizeBeforeUpdate = rentalInformationRepository.findAll().size();

        // Update the rentalInformation
        RentalInformation updatedRentalInformation = rentalInformationRepository.findById(rentalInformation.getId()).get();
        // Disconnect from session so that the updates on updatedRentalInformation are not directly saved in db
        em.detach(updatedRentalInformation);
        updatedRentalInformation
            .code(UPDATED_CODE)
            .preparedPeople(UPDATED_PREPARED_PEOPLE)
            .rentDate(UPDATED_RENT_DATE)
            .rentalUnits(UPDATED_RENTAL_UNITS)
            .builder(UPDATED_BUILDER)
            .rental(UPDATED_RENTAL)
            .leaseTime(UPDATED_LEASE_TIME)
            .remarks(UPDATED_REMARKS);

        restRentalInformationMockMvc.perform(put("/api/rental-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRentalInformation)))
            .andExpect(status().isOk());

        // Validate the RentalInformation in the database
        List<RentalInformation> rentalInformationList = rentalInformationRepository.findAll();
        assertThat(rentalInformationList).hasSize(databaseSizeBeforeUpdate);
        RentalInformation testRentalInformation = rentalInformationList.get(rentalInformationList.size() - 1);
        assertThat(testRentalInformation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRentalInformation.getPreparedPeople()).isEqualTo(UPDATED_PREPARED_PEOPLE);
        assertThat(testRentalInformation.getRentDate()).isEqualTo(UPDATED_RENT_DATE);
        assertThat(testRentalInformation.getRentalUnits()).isEqualTo(UPDATED_RENTAL_UNITS);
        assertThat(testRentalInformation.getBuilder()).isEqualTo(UPDATED_BUILDER);
        assertThat(testRentalInformation.getRental()).isEqualTo(UPDATED_RENTAL);
        assertThat(testRentalInformation.getLeaseTime()).isEqualTo(UPDATED_LEASE_TIME);
        assertThat(testRentalInformation.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingRentalInformation() throws Exception {
        int databaseSizeBeforeUpdate = rentalInformationRepository.findAll().size();

        // Create the RentalInformation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentalInformationMockMvc.perform(put("/api/rental-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalInformation)))
            .andExpect(status().isBadRequest());

        // Validate the RentalInformation in the database
        List<RentalInformation> rentalInformationList = rentalInformationRepository.findAll();
        assertThat(rentalInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRentalInformation() throws Exception {
        // Initialize the database
        rentalInformationService.save(rentalInformation);

        int databaseSizeBeforeDelete = rentalInformationRepository.findAll().size();

        // Delete the rentalInformation
        restRentalInformationMockMvc.perform(delete("/api/rental-informations/{id}", rentalInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RentalInformation> rentalInformationList = rentalInformationRepository.findAll();
        assertThat(rentalInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RentalInformation.class);
        RentalInformation rentalInformation1 = new RentalInformation();
        rentalInformation1.setId(1L);
        RentalInformation rentalInformation2 = new RentalInformation();
        rentalInformation2.setId(rentalInformation1.getId());
        assertThat(rentalInformation1).isEqualTo(rentalInformation2);
        rentalInformation2.setId(2L);
        assertThat(rentalInformation1).isNotEqualTo(rentalInformation2);
        rentalInformation1.setId(null);
        assertThat(rentalInformation1).isNotEqualTo(rentalInformation2);
    }
}
