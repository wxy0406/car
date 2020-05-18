package com.jiuqi.car.service.query;

public class RentalInformationQuery {

    private Long id;

    private String code;

    private String preparedPeople;

    private String rentDate;

    private String rentalUnits;

    private String builder;

    private Integer rental;

    private Integer leaseTime;

    private String remarks;

    private Long vehicleInformationId;

    private String vehicleInformationName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPreparedPeople() {
        return preparedPeople;
    }

    public void setPreparedPeople(String preparedPeople) {
        this.preparedPeople = preparedPeople;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getRentalUnits() {
        return rentalUnits;
    }

    public void setRentalUnits(String rentalUnits) {
        this.rentalUnits = rentalUnits;
    }

    public String getBuilder() {
        return builder;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    public Integer getRental() {
        return rental;
    }

    public void setRental(Integer rental) {
        this.rental = rental;
    }

    public Integer getLeaseTime() {
        return leaseTime;
    }

    public void setLeaseTime(Integer leaseTime) {
        this.leaseTime = leaseTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getVehicleInformationId() {
        return vehicleInformationId;
    }

    public void setVehicleInformationId(Long vehicleInformationId) {
        this.vehicleInformationId = vehicleInformationId;
    }

    public String getVehicleInformationName() {
        return vehicleInformationName;
    }

    public void setVehicleInformationName(String vehicleInformationName) {
        this.vehicleInformationName = vehicleInformationName;
    }

    @Override
    public String toString() {
        return "RentalInformationQuery{" +
            "id=" + id +
            ", code='" + code + '\'' +
            ", preparedPeople='" + preparedPeople + '\'' +
            ", rentDate='" + rentDate + '\'' +
            ", rentalUnits='" + rentalUnits + '\'' +
            ", builder='" + builder + '\'' +
            ", rental=" + rental +
            ", leaseTime=" + leaseTime +
            ", remarks='" + remarks + '\'' +
            ", vehicleInformationId=" + vehicleInformationId +
            ", vehicleInformationName='" + vehicleInformationName + '\'' +
            '}';
    }
}
