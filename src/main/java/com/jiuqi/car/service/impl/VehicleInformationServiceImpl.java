package com.jiuqi.car.service.impl;

import com.jiuqi.car.domain.VehicleInformation;
import com.jiuqi.car.domain.VehicleType;
import com.jiuqi.car.domain.enumeration.LeaseState;
import com.jiuqi.car.repository.VehicleInformationRepository;
import com.jiuqi.car.repository.VehicleTypeRepository;
import com.jiuqi.car.service.VehicleInformationService;
import com.jiuqi.car.service.util.TraversalTree;
import com.jiuqi.car.service.vm.PageVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing {@link VehicleInformation}.
 */
@Service
@Transactional
public class VehicleInformationServiceImpl implements VehicleInformationService {

    private final Logger log = LoggerFactory.getLogger(VehicleInformationServiceImpl.class);

    private final VehicleInformationRepository vehicleInformationRepository;

    private final VehicleTypeRepository vehicleTypeRepository;

    public VehicleInformationServiceImpl(VehicleInformationRepository vehicleInformationRepository, VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleInformationRepository = vehicleInformationRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    /**
     * Save TraversalTree vehicleInformation.
     *
     * @param vehicleInformation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VehicleInformation save(VehicleInformation vehicleInformation) {
        log.debug("Request to save VehicleInformation : {}", vehicleInformation);
        return vehicleInformationRepository.save(vehicleInformation);
    }

    /**
     * Get all the vehicleInformations.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleInformation> findAll() {
        log.debug("Request to get all VehicleInformations");
        return vehicleInformationRepository.findAll();
    }


    /**
     * Get one vehicleInformation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VehicleInformation> findOne(Long id) {
        log.debug("Request to get VehicleInformation : {}", id);
        return vehicleInformationRepository.findById(id);
    }

    /**
     * Delete the vehicleInformation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VehicleInformation : {}", id);
        vehicleInformationRepository.deleteById(id);
    }

    /**
     * 点击车辆类型树上的节点查询对应的车辆详细信息
     *
     * @param pageVM 点击的车辆类型树上节点的Id
     * @return 该车辆类型的所有车辆
     */
    @Override
    public Map<String, Object> findAllByVehicleTypeId(PageVM<String> pageVM) {
        Map<String, Object> map = new HashMap<>();
        Page page;
        if (pageVM.getParam() == null || pageVM.getParam().equals("0")) {
            page = vehicleInformationRepository.findAll(PageRequest.of(pageVM.getPageIndex(), pageVM.getPageSize()));
        } else {
            Long vehicleTypeId = Long.parseLong(pageVM.getParam());
            VehicleType vehicleType = vehicleTypeRepository.getOne(vehicleTypeId);
            List<Long> vehicleTypeIdList = new ArrayList<>();
            vehicleTypeIdList.add(vehicleTypeId);
            TraversalTree.traversalTree(vehicleType.getVehicleTypes(), vehicleTypeIdList);
            page = vehicleInformationRepository.findAllByVehicleType_IdIn(vehicleTypeIdList, PageRequest.of(pageVM.getPageIndex(), pageVM.getPageSize()));
        }
        map.put("totalElements", page.getTotalElements());
        map.put("content", page.getContent());
        return map;
    }

    @Override
    public List<VehicleInformation> findAllByVehicleTypeIdAndLeaseStateIsYes(Long vehicleTypeId) {
        return vehicleInformationRepository.findAllByVehicleType_IdAndLeaseState(vehicleTypeId, LeaseState.YES);
    }

    @Override
    public List<VehicleInformation> findAllByVehicleTypeIdAndLeaseStateIsNo(Long vehicleTypeId) {
        return vehicleInformationRepository.findAllByVehicleType_IdAndLeaseState(vehicleTypeId, LeaseState.NO);
    }

    @Override
    public void batchDelete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        List<VehicleInformation> vehicleInformationList = new ArrayList<>();
        for (String id : idList) {
            vehicleInformationList.add(new VehicleInformation().id(Long.parseLong(id)));
        }
        vehicleInformationRepository.deleteInBatch(vehicleInformationList);
    }

}
