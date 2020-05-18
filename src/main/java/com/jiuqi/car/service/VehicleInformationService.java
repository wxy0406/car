package com.jiuqi.car.service;

import com.jiuqi.car.domain.VehicleInformation;
import com.jiuqi.car.service.vm.PageVM;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing {@link VehicleInformation}.
 */
public interface VehicleInformationService {

    /**
     * Save TraversalTree vehicleInformation.
     *
     * @param vehicleInformation the entity to save.
     * @return the persisted entity.
     */
    VehicleInformation save(VehicleInformation vehicleInformation);

    /**
     * Get all the vehicleInformations.
     *
     * @return the list of entities.
     */
    List<VehicleInformation> findAll();


    /**
     * Get the "id" vehicleInformation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VehicleInformation> findOne(Long id);

    /**
     * Delete the "id" vehicleInformation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * 点击车辆类型树上的节点查询对应的车辆详细信息
     *
     * @param pageVM 点击的车辆类型树上节点的Id
     * @return 该车辆类型的所有车辆
     */
    Map<String, Object> findAllByVehicleTypeId(PageVM<String> pageVM);

    List<VehicleInformation> findAllByVehicleTypeIdAndLeaseStateIsYes(Long vehicleTypeId);

    List<VehicleInformation> findAllByVehicleTypeIdAndLeaseStateIsNo(Long vehicleTypeId);

    void batchDelete(String ids);
}
