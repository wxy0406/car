package com.jiuqi.car.service.util;

import com.jiuqi.car.domain.VehicleType;

import java.util.Collection;
import java.util.List;

public class TraversalTree {


    /**
     * 遍历树获取所有树节点的id
     *
     * @param vehicleTypeCollection 树节点
     * @param vehicleTypeIdList     所有树节点的id
     */
    public static void traversalTree(Collection<VehicleType> vehicleTypeCollection, List<Long> vehicleTypeIdList) {
        for (VehicleType vehicleType : vehicleTypeCollection) {
            vehicleTypeIdList.add(vehicleType.getId());
            traversalTree(vehicleType.getVehicleTypes(), vehicleTypeIdList);
        }
//        Iterator<VehicleType> it = vehicleTypeCollection.iterator();
//        while(it.hasNext()) {
//            VehicleType vehicleType = it.next();
//            vehicleTypeIdList.add(vehicleType.getId());
//            traversalTree(vehicleType.getVehicleTypes(), vehicleTypeIdList);
//        }
    }
}
