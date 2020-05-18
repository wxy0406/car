package com.jiuqi.car.repository;

import com.jiuqi.car.domain.RentalInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the RentalInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RentalInformationRepository extends JpaRepository<RentalInformation, Long>, JpaSpecificationExecutor<RentalInformation> {

    List<RentalInformation> findAllByIdIn(List<Long> idList);
}
