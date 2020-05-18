package com.jiuqi.car.web.rest;

import com.jiuqi.car.CarApp;
import com.jiuqi.car.domain.VehicleInformation;
import com.jiuqi.car.repository.VehicleInformationRepository;
import com.jiuqi.car.service.VehicleInformationService;
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
import java.sql.Timestamp;
import java.util.List;

import static com.jiuqi.car.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jiuqi.car.domain.enumeration.LeaseState;
/**
 * Integration tests for the {@Link VehicleInformationResource} REST controller.
 */
@SpringBootTest(classes = CarApp.class)
public class VehicleInformationResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_DATE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 0;
    private static final Integer UPDATED_PRICE = 1;

    private static final LeaseState DEFAULT_LEASE_STATE = LeaseState.YES;
    private static final LeaseState UPDATED_LEASE_STATE = LeaseState.NO;

    @Autowired
    private VehicleInformationRepository vehicleInformationRepository;

    @Autowired
    private VehicleInformationService vehicleInformationService;

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

    private MockMvc restVehicleInformationMockMvc;

    private VehicleInformation vehicleInformation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleInformationResource vehicleInformationResource = new VehicleInformationResource(vehicleInformationService);
        this.restVehicleInformationMockMvc = MockMvcBuilders.standaloneSetup(vehicleInformationResource)
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
    public static VehicleInformation createEntity(EntityManager em) {
        VehicleInformation vehicleInformation = new VehicleInformation()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .number(DEFAULT_NUMBER)
            .purchaseDate(DEFAULT_PURCHASE_DATE)
            .price(DEFAULT_PRICE)
            .leaseState(DEFAULT_LEASE_STATE);
        return vehicleInformation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is TraversalTree static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleInformation createUpdatedEntity(EntityManager em) {
        VehicleInformation vehicleInformation = new VehicleInformation()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .price(UPDATED_PRICE)
            .leaseState(UPDATED_LEASE_STATE);
        return vehicleInformation;
    }

    @BeforeEach
    public void initTest() {
        vehicleInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleInformation() throws Exception {
        int databaseSizeBeforeCreate = vehicleInformationRepository.findAll().size();

        // Create the VehicleInformation
        restVehicleInformationMockMvc.perform(post("/api/vehicle-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleInformation)))
            .andExpect(status().isCreated());

        // Validate the VehicleInformation in the database
        List<VehicleInformation> vehicleInformationList = vehicleInformationRepository.findAll();
        assertThat(vehicleInformationList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleInformation testVehicleInformation = vehicleInformationList.get(vehicleInformationList.size() - 1);
        assertThat(testVehicleInformation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVehicleInformation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVehicleInformation.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testVehicleInformation.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testVehicleInformation.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testVehicleInformation.getLeaseState()).isEqualTo(DEFAULT_LEASE_STATE);
    }

    @Test
    @Transactional
    public void createVehicleInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleInformationRepository.findAll().size();

        // Create the VehicleInformation with an existing ID
        vehicleInformation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleInformationMockMvc.perform(post("/api/vehicle-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleInformation)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleInformation in the database
        List<VehicleInformation> vehicleInformationList = vehicleInformationRepository.findAll();
        assertThat(vehicleInformationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInformationRepository.findAll().size();
        // set the field null
        vehicleInformation.setCode(null);

        // Create the VehicleInformation, which fails.

        restVehicleInformationMockMvc.perform(post("/api/vehicle-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleInformation)))
            .andExpect(status().isBadRequest());

        List<VehicleInformation> vehicleInformationList = vehicleInformationRepository.findAll();
        assertThat(vehicleInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInformationRepository.findAll().size();
        // set the field null
        vehicleInformation.setName(null);

        // Create the VehicleInformation, which fails.

        restVehicleInformationMockMvc.perform(post("/api/vehicle-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleInformation)))
            .andExpect(status().isBadRequest());

        List<VehicleInformation> vehicleInformationList = vehicleInformationRepository.findAll();
        assertThat(vehicleInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInformationRepository.findAll().size();
        // set the field null
        vehicleInformation.setNumber(null);

        // Create the VehicleInformation, which fails.

        restVehicleInformationMockMvc.perform(post("/api/vehicle-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleInformation)))
            .andExpect(status().isBadRequest());

        List<VehicleInformation> vehicleInformationList = vehicleInformationRepository.findAll();
        assertThat(vehicleInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPurchaseDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInformationRepository.findAll().size();
        // set the field null
        vehicleInformation.setPurchaseDate(null);

        // Create the VehicleInformation, which fails.

        restVehicleInformationMockMvc.perform(post("/api/vehicle-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleInformation)))
            .andExpect(status().isBadRequest());

        List<VehicleInformation> vehicleInformationList = vehicleInformationRepository.findAll();
        assertThat(vehicleInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInformationRepository.findAll().size();
        // set the field null
        vehicleInformation.setPrice(null);

        // Create the VehicleInformation, which fails.

        restVehicleInformationMockMvc.perform(post("/api/vehicle-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleInformation)))
            .andExpect(status().isBadRequest());

        List<VehicleInformation> vehicleInformationList = vehicleInformationRepository.findAll();
        assertThat(vehicleInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLeaseStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInformationRepository.findAll().size();
        // set the field null
        vehicleInformation.setLeaseState(null);

        // Create the VehicleInformation, which fails.

        restVehicleInformationMockMvc.perform(post("/api/vehicle-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleInformation)))
            .andExpect(status().isBadRequest());

        List<VehicleInformation> vehicleInformationList = vehicleInformationRepository.findAll();
        assertThat(vehicleInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVehicleInformations() throws Exception {
        // Initialize the database
        vehicleInformationRepository.saveAndFlush(vehicleInformation);

        // Get all the vehicleInformationList
        restVehicleInformationMockMvc.perform(get("/api/vehicle-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].leaseState").value(hasItem(DEFAULT_LEASE_STATE.toString())));
    }
    
    @Test
    @Transactional
    public void getVehicleInformation() throws Exception {
        // Initialize the database
        vehicleInformationRepository.saveAndFlush(vehicleInformation);

        // Get the vehicleInformation
        restVehicleInformationMockMvc.perform(get("/api/vehicle-informations/{id}", vehicleInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleInformation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.leaseState").value(DEFAULT_LEASE_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicleInformation() throws Exception {
        // Get the vehicleInformation
        restVehicleInformationMockMvc.perform(get("/api/vehicle-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleInformation() throws Exception {
        // Initialize the database
        vehicleInformationService.save(vehicleInformation);

        int databaseSizeBeforeUpdate = vehicleInformationRepository.findAll().size();

        // Update the vehicleInformation
        VehicleInformation updatedVehicleInformation = vehicleInformationRepository.findById(vehicleInformation.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleInformation are not directly saved in db
        em.detach(updatedVehicleInformation);
        updatedVehicleInformation
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .price(UPDATED_PRICE)
            .leaseState(UPDATED_LEASE_STATE);

        restVehicleInformationMockMvc.perform(put("/api/vehicle-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVehicleInformation)))
            .andExpect(status().isOk());

        // Validate the VehicleInformation in the database
        List<VehicleInformation> vehicleInformationList = vehicleInformationRepository.findAll();
        assertThat(vehicleInformationList).hasSize(databaseSizeBeforeUpdate);
        VehicleInformation testVehicleInformation = vehicleInformationList.get(vehicleInformationList.size() - 1);
        assertThat(testVehicleInformation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVehicleInformation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVehicleInformation.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testVehicleInformation.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testVehicleInformation.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testVehicleInformation.getLeaseState()).isEqualTo(UPDATED_LEASE_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleInformation() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInformationRepository.findAll().size();

        // Create the VehicleInformation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleInformationMockMvc.perform(put("/api/vehicle-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleInformation)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleInformation in the database
        List<VehicleInformation> vehicleInformationList = vehicleInformationRepository.findAll();
        assertThat(vehicleInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicleInformation() throws Exception {
        // Initialize the database
        vehicleInformationService.save(vehicleInformation);

        int databaseSizeBeforeDelete = vehicleInformationRepository.findAll().size();

        // Delete the vehicleInformation
        restVehicleInformationMockMvc.perform(delete("/api/vehicle-informations/{id}", vehicleInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleInformation> vehicleInformationList = vehicleInformationRepository.findAll();
        assertThat(vehicleInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleInformation.class);
        VehicleInformation vehicleInformation1 = new VehicleInformation();
        vehicleInformation1.setId(1L);
        VehicleInformation vehicleInformation2 = new VehicleInformation();
        vehicleInformation2.setId(vehicleInformation1.getId());
        assertThat(vehicleInformation1).isEqualTo(vehicleInformation2);
        vehicleInformation2.setId(2L);
        assertThat(vehicleInformation1).isNotEqualTo(vehicleInformation2);
        vehicleInformation1.setId(null);
        assertThat(vehicleInformation1).isNotEqualTo(vehicleInformation2);
    }
}
