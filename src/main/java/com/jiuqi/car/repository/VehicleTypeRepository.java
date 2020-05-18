package com.jiuqi.car.repository;

import com.jiuqi.car.domain.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the VehicleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {

    List<VehicleType> findAllByVehicleTypeIdIsNull();

    List<VehicleType> findAllByVehicleTypeId(Long vehicleTypeId);
}
