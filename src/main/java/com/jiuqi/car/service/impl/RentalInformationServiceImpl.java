package com.jiuqi.car.service.impl;

import com.jiuqi.car.domain.RentalInformation;
import com.jiuqi.car.domain.VehicleInformation;
import com.jiuqi.car.domain.enumeration.LeaseState;
import com.jiuqi.car.repository.RentalInformationRepository;
import com.jiuqi.car.repository.VehicleInformationRepository;
import com.jiuqi.car.service.RentalInformationService;
import com.jiuqi.car.service.query.RentalInformationQuery;
import com.jiuqi.car.service.vm.PageVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link RentalInformation}.
 */
@Service
@Transactional
public class RentalInformationServiceImpl implements RentalInformationService {

    private final Logger log = LoggerFactory.getLogger(RentalInformationServiceImpl.class);

    private final RentalInformationRepository rentalInformationRepository;

    private final VehicleInformationRepository vehicleInformationRepository;

    public RentalInformationServiceImpl(RentalInformationRepository rentalInformationRepository, VehicleInformationRepository vehicleInformationRepository) {
        this.rentalInformationRepository = rentalInformationRepository;
        this.vehicleInformationRepository = vehicleInformationRepository;
    }

    /**
     * Save TraversalTree rentalInformation.
     *
     * @param rentalInformation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RentalInformation save(RentalInformation rentalInformation) {
        log.debug("Request to save RentalInformation : {}", rentalInformation);
        vehicleInformationRepository.updateLeaseState(LeaseState.YES, rentalInformation.getVehicleInformation().getId());
        return rentalInformationRepository.save(rentalInformation);
    }

    /**
     * Get all the rentalInformations.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RentalInformation> findAll() {
        log.debug("Request to get all RentalInformations");
        return rentalInformationRepository.findAll();
    }


    /**
     * Get one rentalInformation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RentalInformation> findOne(Long id) {
        log.debug("Request to get RentalInformation : {}", id);
        return rentalInformationRepository.findById(id);
    }

    /**
     * Delete the rentalInformation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RentalInformation : {}", id);
        vehicleInformationRepository.updateLeaseState(LeaseState.NO, rentalInformationRepository.getOne(id).getVehicleInformation().getId());
        rentalInformationRepository.deleteById(id);
    }

    @Override
    public void batchDelete(String ids) {
        List<String> rentalInformationIdList = Arrays.asList(ids.split(","));
        List<Long> rentalInformationId = new ArrayList<>();
        List<RentalInformation> rentalInformationList = new ArrayList<>();
        for (String id : rentalInformationIdList) {
            rentalInformationList.add(new RentalInformation().id(Long.parseLong(id)));
            rentalInformationId.add(Long.parseLong(id));
        }
        vehicleInformationRepository.batchUpdateLeaseState(LeaseState.NO, rentalInformationRepository.findAllByIdIn(rentalInformationId)
            .stream()
            .map(rentalInformation -> rentalInformation.getVehicleInformation().getId())
            .collect(Collectors.toList()));
        rentalInformationRepository.deleteInBatch(rentalInformationList);
    }

    @Override
    public Map<String, Object> findAllByRentalInformationQueryPageVM(PageVM<RentalInformationQuery> rentalInformationQueryPageVM) {
        Map<String, Object> map = new HashMap<>();
        Page page = rentalInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (null != rentalInformationQueryPageVM.getParam()) {
                if (!ObjectUtils.isEmpty(rentalInformationQueryPageVM.getParam().getCode())) {
                    predicateList.add(criteriaBuilder.like(root.get("code").as(String.class), "%" + rentalInformationQueryPageVM.getParam().getCode() + "%"));
                }
                if (!ObjectUtils.isEmpty(rentalInformationQueryPageVM.getParam().getVehicleInformationId())) {
                    predicateList.add(criteriaBuilder.equal(root.get("vehicleInformation").as(VehicleInformation.class), new VehicleInformation().id(rentalInformationQueryPageVM.getParam().getVehicleInformationId())));
                }
            }
            return criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
        }, PageRequest.of(rentalInformationQueryPageVM.getPageIndex(), rentalInformationQueryPageVM.getPageSize()));
        map.put("totalElements", page.getTotalElements());
        map.put("content", page.getContent());
        return map;
    }

    @Override
    public List<Object> getRentRentalInformationTree() {
        return null;
    }
}
