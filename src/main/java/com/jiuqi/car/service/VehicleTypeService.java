package com.jiuqi.car.service;

import com.jiuqi.car.domain.VehicleType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link VehicleType}.
 */
public interface VehicleTypeService {

    /**
     * Save TraversalTree vehicleType.
     *
     * @param vehicleType the entity to save.
     * @return the persisted entity.
     */
    VehicleType save(VehicleType vehicleType);

    /**
     * Get all the vehicleTypes.
     *
     * @return the list of entities.
     */
    List<VehicleType> findAll();


    /**
     * Get the "id" vehicleType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VehicleType> findOne(Long id);

    /**
     * Delete the "id" vehicleType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * 查询车辆类型树
     *
     * @return 车辆类型树
     */
    List<VehicleType> findByVehicleTypeIdIsNull();

    List<VehicleType> findByVehicleTypeId(Long VehicleTypeId);

    List<Long> findParentsIdById(Long id);

    List<VehicleType> getVehicleTypeTree();

    Long findBrotherIdById(Long id);
}
