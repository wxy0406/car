package com.jiuqi.car.service.impl;

import com.jiuqi.car.domain.VehicleType;
import com.jiuqi.car.repository.VehicleInformationRepository;
import com.jiuqi.car.repository.VehicleTypeRepository;
import com.jiuqi.car.service.VehicleTypeService;
import com.jiuqi.car.service.util.TraversalTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing {@link VehicleType}.
 */
@Service
@Transactional
public class VehicleTypeServiceImpl implements VehicleTypeService {

    private final Logger log = LoggerFactory.getLogger(VehicleTypeServiceImpl.class);

    private final VehicleTypeRepository vehicleTypeRepository;

    private final VehicleInformationRepository vehicleInformationRepository;

    public VehicleTypeServiceImpl(VehicleTypeRepository vehicleTypeRepository, VehicleInformationRepository vehicleInformationRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.vehicleInformationRepository = vehicleInformationRepository;
    }

    /**
     * Save TraversalTree vehicleType.
     *
     * @param vehicleType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VehicleType save(VehicleType vehicleType) {
        log.debug("Request to save VehicleType : {}", vehicleType);
        return vehicleTypeRepository.save(vehicleType);
    }

    /**
     * Get all the vehicleTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleType> findAll() {
        log.debug("Request to get all VehicleTypes");
        return vehicleTypeRepository.findAll();
    }


    /**
     * Get one vehicleType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VehicleType> findOne(Long id) {
        log.debug("Request to get VehicleType : {}", id);
        return vehicleTypeRepository.findById(id);
    }

    /**
     * Delete the vehicleType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VehicleType : {}", id);
        VehicleType vehicleType = vehicleTypeRepository.getOne(id);
        List<Long> vehicleTypeIdList = new ArrayList<>();
        vehicleTypeIdList.add(id);
        TraversalTree.traversalTree(vehicleType.getVehicleTypes(), vehicleTypeIdList);
        if (!Objects.isNull(vehicleType.getVehicleType())) {
            vehicleType.getVehicleType().getVehicleTypes().remove(vehicleType);
        }
        vehicleTypeRepository.deleteById(id);
        vehicleInformationRepository.deleteInBatch(vehicleInformationRepository.findAllByVehicleType_IdIn(vehicleTypeIdList));
    }

    /**
     * 查询车辆类型树根节点
     *
     * @return 车辆类型树根节点
     */
    @Override
    public List<VehicleType> findByVehicleTypeIdIsNull() {
        log.debug("查询车辆类型树根节点");
        return vehicleTypeRepository.findAllByVehicleTypeIdIsNull();
    }

    /**
     * 查询车辆类型树当前节点下的子节点
     *
     * @return 车辆类型树当前节点下的子节点
     */
    @Override
    public List<VehicleType> findByVehicleTypeId(Long VehicleTypeId) {
        log.debug("查询车辆类型树当前节点下的子节点");
        return vehicleTypeRepository.findAllByVehicleTypeId(VehicleTypeId);
    }

    @Override
    public List<Long> findParentsIdById(Long id) {
        VehicleType vehicleType = vehicleTypeRepository.findById(id).orElse(null);
        List<Long> idList = new ArrayList<>();
        if (vehicleType == null) {
            return idList;
        }
        vehicleType = vehicleType.getVehicleType();
        while (null != vehicleType) {
            idList.add(vehicleType.getId());
            vehicleType = vehicleType.getVehicleType();
        }
        Collections.reverse(idList);
        return idList;
    }

    @Override
    public List<VehicleType> getVehicleTypeTree() {
        return null;
    }

    @Override
    public Long findBrotherIdById(Long id) {
        VehicleType vehicleType = vehicleTypeRepository.getOne(id);
        if (null == vehicleType.getVehicleType()) {
            return 0L;
        }
        Set<VehicleType> vehicleTypeSet = vehicleType.getVehicleType().getVehicleTypes();
        if (vehicleTypeSet.size() == 1) {
            return vehicleType.getVehicleType().getId();
        }
        Iterator<VehicleType> it = vehicleTypeSet.iterator();
        VehicleType vehicleType1 = new VehicleType();
        while (it.hasNext()) {
            id = vehicleType1.getId();
            vehicleType1 = it.next();
            if (vehicleType.getId().equals(vehicleType1.getId())) {
                if (it.hasNext()) {
                    return it.next().getId();
                } else {
                    return id;
                }
            }
        }
        return 0L;
    }
}
