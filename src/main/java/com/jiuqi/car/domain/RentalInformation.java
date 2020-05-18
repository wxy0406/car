package com.jiuqi.car.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A RentalInformation.
 */
@Entity
@Table(name = "rental_information")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RentalInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "prepared_people", nullable = false)
    private String preparedPeople;

    @NotNull
    @Column(name = "rent_date", nullable = false)
    private String rentDate;

    @NotNull
    @Column(name = "rental_units", nullable = false)
    private String rentalUnits;

    @NotNull
    @Column(name = "builder", nullable = false)
    private String builder;

    @NotNull
    @Min(value = 0)
    @Column(name = "rental", nullable = false)
    private Integer rental;

    @NotNull
    @Min(value = 0)
    @Column(name = "lease_time", nullable = false)
    private Integer leaseTime;

    @Column(name = "remarks")
    private String remarks;

    @OneToOne
    @JoinColumn(unique = true)
    private VehicleInformation vehicleInformation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public RentalInformation id(Long id) {
        this.id = id;
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public RentalInformation code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPreparedPeople() {
        return preparedPeople;
    }

    public RentalInformation preparedPeople(String preparedPeople) {
        this.preparedPeople = preparedPeople;
        return this;
    }

    public void setPreparedPeople(String preparedPeople) {
        this.preparedPeople = preparedPeople;
    }

    public String getRentDate() {
        return rentDate;
    }

    public RentalInformation rentDate(String rentDate) {
        this.rentDate = rentDate;
        return this;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getRentalUnits() {
        return rentalUnits;
    }

    public RentalInformation rentalUnits(String rentalUnits) {
        this.rentalUnits = rentalUnits;
        return this;
    }

    public void setRentalUnits(String rentalUnits) {
        this.rentalUnits = rentalUnits;
    }

    public String getBuilder() {
        return builder;
    }

    public RentalInformation builder(String builder) {
        this.builder = builder;
        return this;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    public Integer getRental() {
        return rental;
    }

    public RentalInformation rental(Integer rental) {
        this.rental = rental;
        return this;
    }

    public void setRental(Integer rental) {
        this.rental = rental;
    }

    public Integer getLeaseTime() {
        return leaseTime;
    }

    public RentalInformation leaseTime(Integer leaseTime) {
        this.leaseTime = leaseTime;
        return this;
    }

    public void setLeaseTime(Integer leaseTime) {
        this.leaseTime = leaseTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public RentalInformation remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public VehicleInformation getVehicleInformation() {
        return vehicleInformation;
    }

    public RentalInformation vehicleInformation(VehicleInformation vehicleInformation) {
        this.vehicleInformation = vehicleInformation;
        return this;
    }

    public void setVehicleInformation(VehicleInformation vehicleInformation) {
        this.vehicleInformation = vehicleInformation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RentalInformation)) {
            return false;
        }
        return id != null && id.equals(((RentalInformation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RentalInformation{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", preparedPeople='" + getPreparedPeople() + "'" +
            ", rentDate='" + getRentDate() + "'" +
            ", rentalUnits='" + getRentalUnits() + "'" +
            ", builder='" + getBuilder() + "'" +
            ", rental=" + getRental() +
            ", leaseTime=" + getLeaseTime() +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
