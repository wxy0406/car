package com.jiuqi.car.web.rest;

import com.jiuqi.car.domain.RentalInformation;
import com.jiuqi.car.service.RentalInformationService;
import com.jiuqi.car.service.query.RentalInformationQuery;
import com.jiuqi.car.service.vm.PageVM;
import com.jiuqi.car.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jiuqi.car.domain.RentalInformation}.
 */
@RestController
@RequestMapping("/api")
public class RentalInformationResource {

    private final Logger log = LoggerFactory.getLogger(RentalInformationResource.class);

    private static final String ENTITY_NAME = "rentalInformation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RentalInformationService rentalInformationService;

    public RentalInformationResource(RentalInformationService rentalInformationService) {
        this.rentalInformationService = rentalInformationService;
    }

    /**
     * {@code POST  /rental-informations} : Create TraversalTree new rentalInformation.
     *
     * @param rentalInformation the rentalInformation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rentalInformation, or with status {@code 400 (Bad Request)} if the rentalInformation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rental-informations")
    public ResponseEntity<RentalInformation> createRentalInformation(@Valid @RequestBody RentalInformation rentalInformation) throws URISyntaxException {
        log.debug("REST request to save RentalInformation : {}", rentalInformation);
        if (rentalInformation.getId() != null) {
            throw new BadRequestAlertException("A new rentalInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RentalInformation result = rentalInformationService.save(rentalInformation);
        return ResponseEntity.created(new URI("/api/rental-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rental-informations} : Updates an existing rentalInformation.
     *
     * @param rentalInformation the rentalInformation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rentalInformation,
     * or with status {@code 400 (Bad Request)} if the rentalInformation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rentalInformation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rental-informations")
    public ResponseEntity<RentalInformation> updateRentalInformation(@Valid @RequestBody RentalInformation rentalInformation) throws URISyntaxException {
        log.debug("REST request to update RentalInformation : {}", rentalInformation);
        if (rentalInformation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RentalInformation result = rentalInformationService.save(rentalInformation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rentalInformation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rental-informations} : get all the rentalInformations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rentalInformations in body.
     */
    @GetMapping("/rental-informations")
    public List<RentalInformation> getAllRentalInformations() {
        log.debug("REST request to get all RentalInformations");
        return rentalInformationService.findAll();
    }

    /**
     * {@code GET  /rental-informations/:id} : get the "id" rentalInformation.
     *
     * @param id the id of the rentalInformation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rentalInformation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rental-informations/{id}")
    public ResponseEntity<RentalInformation> getRentalInformation(@PathVariable Long id) {
        log.debug("REST request to get RentalInformation : {}", id);
        Optional<RentalInformation> rentalInformation = rentalInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rentalInformation);
    }

    /**
     * {@code DELETE  /rental-informations/:id} : delete the "id" rentalInformation.
     *
     * @param id the id of the rentalInformation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rental-informations/{id}")
    public ResponseEntity<Void> deleteRentalInformation(@PathVariable Long id) {
        log.debug("REST request to delete RentalInformation : {}", id);
        rentalInformationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @DeleteMapping("/rental-informations/batch/{ids}")
    public ResponseEntity<Void> batchDeleteRentalInformation(@PathVariable String ids) {
        log.debug("REST request to batch delete RentalInformation : {}", ids);
        rentalInformationService.batchDelete(ids);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, ids)).build();
    }

    @GetMapping("/rental-informations/table")
    public Object getRentalInformationsByRentalInformationQueryPageVM(PageVM<RentalInformationQuery> rentalInformationQueryPageVM, RentalInformationQuery rentalInformationQuery) {
        rentalInformationQueryPageVM.setParam(rentalInformationQuery);
        log.debug("查询车辆租赁信息 : {}", rentalInformationQueryPageVM);
        return rentalInformationService.findAllByRentalInformationQueryPageVM(rentalInformationQueryPageVM);
    }

    @GetMapping("/rental-informations/tree/query")
    public Object getRentRentalInformationTree() {
        log.debug("查询已租赁车辆的树");
        return rentalInformationService.getRentRentalInformationTree();
    }
}
