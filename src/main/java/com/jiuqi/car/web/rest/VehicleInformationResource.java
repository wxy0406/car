package com.jiuqi.car.web.rest;

import com.jiuqi.car.domain.VehicleInformation;
import com.jiuqi.car.service.VehicleInformationService;
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
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jiuqi.car.domain.VehicleInformation}.
 */
@RestController
@RequestMapping("/api")
public class VehicleInformationResource {

    private final Logger log = LoggerFactory.getLogger(VehicleInformationResource.class);

    private static final String ENTITY_NAME = "vehicleInformation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleInformationService vehicleInformationService;

    public VehicleInformationResource(VehicleInformationService vehicleInformationService) {
        this.vehicleInformationService = vehicleInformationService;
    }

    /**
     * {@code POST  /vehicle-informations} : Create TraversalTree new vehicleInformation.
     *
     * @param vehicleInformation the vehicleInformation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleInformation, or with status {@code 400 (Bad Request)} if the vehicleInformation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-informations")
    public ResponseEntity<VehicleInformation> createVehicleInformation(@Valid @RequestBody VehicleInformation vehicleInformation) throws URISyntaxException {
        log.debug("REST request to save VehicleInformation : {}", vehicleInformation);
        if (vehicleInformation.getId() != null) {
            throw new BadRequestAlertException("A new vehicleInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleInformation result = vehicleInformationService.save(vehicleInformation);
        return ResponseEntity.created(new URI("/api/vehicle-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-informations} : Updates an existing vehicleInformation.
     *
     * @param vehicleInformation the vehicleInformation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleInformation,
     * or with status {@code 400 (Bad Request)} if the vehicleInformation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleInformation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-informations")
    public ResponseEntity<VehicleInformation> updateVehicleInformation(@Valid @RequestBody VehicleInformation vehicleInformation) throws URISyntaxException {
        log.debug("REST request to update VehicleInformation : {}", vehicleInformation);
        if (vehicleInformation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehicleInformation result = vehicleInformationService.save(vehicleInformation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vehicleInformation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vehicle-informations} : get all the vehicleInformations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleInformations in body.
     */
    @GetMapping("/vehicle-informations")
    public List<VehicleInformation> getAllVehicleInformations() {
        log.debug("REST request to get all VehicleInformations");
        return vehicleInformationService.findAll();
    }

    /**
     * {@code GET  /vehicle-informations/:id} : get the "id" vehicleInformation.
     *
     * @param id the id of the vehicleInformation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleInformation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-informations/{id}")
    public ResponseEntity<VehicleInformation> getVehicleInformation(@PathVariable Long id) {
        log.debug("REST request to get VehicleInformation : {}", id);
        Optional<VehicleInformation> vehicleInformation = vehicleInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleInformation);
    }

    /**
     * {@code DELETE  /vehicle-informations/:id} : delete the "id" vehicleInformation.
     *
     * @param id the id of the vehicleInformation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-informations/{id}")
    public ResponseEntity<Void> deleteVehicleInformation(@PathVariable Long id) {
        log.debug("REST request to delete VehicleInformation : {}", id);
        vehicleInformationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @DeleteMapping("/vehicle-informations/batch/{ids}")
    public ResponseEntity<Void> batchDeleteVehicleInformation(@PathVariable String ids) {
        log.debug("REST request to batch delete VehicleInformation : {}", ids);
        vehicleInformationService.batchDelete(ids);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, ids)).build();
    }

    @GetMapping("/vehicle-informations/tree")
    public Map<String, Object> getVehicleInformationByPageVM(PageVM<String> pageVM) {
        log.debug("点击车辆类型树节点查询该类型下的所有车辆详细信息 : {}", pageVM);
        return vehicleInformationService.findAllByVehicleTypeId(pageVM);
    }

    @GetMapping("/vehicle-informations/is-lease/{vehicleTypeId}")
    public List<VehicleInformation> getVehicleInformationByVehicleTypeIdYES(@PathVariable Long vehicleTypeId) {
        log.debug("查询当前车辆类型下已租赁的车辆 : {}", vehicleTypeId);
        return vehicleInformationService.findAllByVehicleTypeIdAndLeaseStateIsYes(vehicleTypeId);
    }

    @GetMapping("/vehicle-informations/not-lease/{vehicleTypeId}")
    public List<VehicleInformation> getVehicleInformationByVehicleTypeIdNO(@PathVariable Long vehicleTypeId) {
        log.debug("查询当前车辆类型下未租赁的车辆 : {}", vehicleTypeId);
        return vehicleInformationService.findAllByVehicleTypeIdAndLeaseStateIsNo(vehicleTypeId);
    }
}
