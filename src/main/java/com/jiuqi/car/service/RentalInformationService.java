package com.jiuqi.car.service;

import com.jiuqi.car.domain.RentalInformation;
import com.jiuqi.car.service.query.RentalInformationQuery;
import com.jiuqi.car.service.vm.PageVM;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing {@link RentalInformation}.
 */
public interface RentalInformationService {

    /**
     * Save TraversalTree rentalInformation.
     *
     * @param rentalInformation the entity to save.
     * @return the persisted entity.
     */
    RentalInformation save(RentalInformation rentalInformation);

    /**
     * Get all the rentalInformations.
     *
     * @return the list of entities.
     */
    List<RentalInformation> findAll();


    /**
     * Get the "id" rentalInformation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RentalInformation> findOne(Long id);

    /**
     * Delete the "id" rentalInformation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void batchDelete(String ids);

    Map<String, Object> findAllByRentalInformationQueryPageVM(PageVM<RentalInformationQuery> rentalInformationQueryPageVM);

    List<Object> getRentRentalInformationTree();
}
