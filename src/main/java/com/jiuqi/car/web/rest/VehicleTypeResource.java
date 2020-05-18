package com.jiuqi.car.web.rest;

import com.jiuqi.car.domain.VehicleType;
import com.jiuqi.car.service.VehicleTypeService;
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
 * REST controller for managing {@link com.jiuqi.car.domain.VehicleType}.
 */
@RestController
@RequestMapping("/api")
public class VehicleTypeResource {

    private final Logger log = LoggerFactory.getLogger(VehicleTypeResource.class);

    private static final String ENTITY_NAME = "vehicleType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleTypeService vehicleTypeService;

    public VehicleTypeResource(VehicleTypeService vehicleTypeService) {
        this.vehicleTypeService = vehicleTypeService;
    }

    /**
     * {@code POST  /vehicle-types} : Create TraversalTree new vehicleType.
     *
     * @param vehicleType the vehicleType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleType, or with status {@code 400 (Bad Request)} if the vehicleType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-types")
    public ResponseEntity<VehicleType> createVehicleType(@Valid @RequestBody VehicleType vehicleType) throws URISyntaxException {
        log.debug("REST request to save VehicleType : {}", vehicleType);
        if (vehicleType.getId() != null) {
            throw new BadRequestAlertException("A new vehicleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleType result = vehicleTypeService.save(vehicleType);
        return ResponseEntity.created(new URI("/api/vehicle-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-types} : Updates an existing vehicleType.
     *
     * @param vehicleType the vehicleType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleType,
     * or with status {@code 400 (Bad Request)} if the vehicleType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-types")
    public ResponseEntity<VehicleType> updateVehicleType(@Valid @RequestBody VehicleType vehicleType) throws URISyntaxException {
        log.debug("REST request to update VehicleType : {}", vehicleType);
        if (vehicleType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehicleType result = vehicleTypeService.save(vehicleType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vehicleType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vehicle-types} : get all the vehicleTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleTypes in body.
     */
    @GetMapping("/vehicle-types")
    public List<VehicleType> getAllVehicleTypes() {
        log.debug("REST request to get all VehicleTypes");
        return vehicleTypeService.findAll();
    }

    /**
     * {@code GET  /vehicle-types/:id} : get the "id" vehicleType.
     *
     * @param id the id of the vehicleType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-types/{id}")
    public ResponseEntity<VehicleType> getVehicleType(@PathVariable Long id) {
        log.debug("REST request to get VehicleType : {}", id);
        Optional<VehicleType> vehicleType = vehicleTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleType);
    }

    /**
     * {@code DELETE  /vehicle-types/:id} : delete the "id" vehicleType.
     *
     * @param id the id of the vehicleType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-types/{id}")
    public ResponseEntity<Void> deleteVehicleType(@PathVariable Long id) {
        log.debug("REST request to delete VehicleType : {}", id);
        vehicleTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @SuppressWarnings("unused")
    @GetMapping("/vehicle-types/tree")
    public List<VehicleType> getVehicleTypeTree() {
        log.debug("查询车辆类型树");
        return vehicleTypeService.getVehicleTypeTree();
    }

    @GetMapping("/vehicle-types/node")
    public List<VehicleType> getVehicleTypeNodes() {
        log.debug("查询车辆类型树根节点");
        return vehicleTypeService.findByVehicleTypeIdIsNull();
    }

    @GetMapping("/vehicle-types/leaf/{vehicleTypeId}")
    public List<VehicleType> getVehicleTypeLefts(@PathVariable Long vehicleTypeId) {
        log.debug("查询车辆类型树当前节点的子节点 : {}", vehicleTypeId);
        return vehicleTypeService.findByVehicleTypeId(vehicleTypeId);
    }

    @GetMapping("/vehicle-types/parent/{id}")
    public List<Long> getVehicleTypeParents(@PathVariable Long id) {
        log.debug("查询车辆类型树当前节点的所有父节点 : {}", id);
        return vehicleTypeService.findParentsIdById(id);
    }

    @GetMapping("/vehicle-types/brother/{id}")
    public Long getVehicleTypeBrother(@PathVariable Long id) {
        log.debug("查询车辆类型树当前节点的兄弟节点 : {}", id);
        return vehicleTypeService.findBrotherIdById(id);
    }
}
