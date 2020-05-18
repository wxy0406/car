package com.jiuqi.car.repository;

import com.jiuqi.car.domain.VehicleInformation;
import com.jiuqi.car.domain.enumeration.LeaseState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the VehicleInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleInformationRepository extends JpaRepository<VehicleInformation, Long> {

    Page<VehicleInformation> findAllByVehicleType_IdIn(List<Long> vehicleTypeIds, Pageable pageable);

    List<VehicleInformation> findAllByVehicleType_IdIn(List<Long> vehicleTypeIds);

    List<VehicleInformation> findAllByVehicleType_IdAndLeaseState(Long vehicleTypeId, LeaseState leaseState);

    @Modifying
    @Query("update VehicleInformation v set v.leaseState = ?1 where v.id = ?2")
    int updateLeaseState(LeaseState leaseState, Long id);

    @Modifying
    @Query("update VehicleInformation v set v.leaseState = ?1 where v.id in ( ?2 )")
    int batchUpdateLeaseState(LeaseState leaseState, List<Long> idList);
}
