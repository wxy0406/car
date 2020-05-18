package com.jiuqi.car.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.car.domain.enumeration.LeaseState;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A VehicleInformation.
 */
@Entity
@Table(name = "vehicle_information")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VehicleInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "number", nullable = false)
    private String number;

    @NotNull
    @Column(name = "purchase_date", nullable = false)
    private String purchaseDate;

    @NotNull
    @Min(value = 0)
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "lease_state", nullable = false)
    private LeaseState leaseState;

    @ManyToOne
    @JsonIgnoreProperties("vehicleInformations")
    private VehicleType vehicleType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public VehicleInformation id(Long id) {
        this.id = id;
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public VehicleInformation code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public VehicleInformation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public VehicleInformation number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public VehicleInformation purchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getPrice() {
        return price;
    }

    public VehicleInformation price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LeaseState getLeaseState() {
        return leaseState;
    }

    public VehicleInformation leaseState(LeaseState leaseState) {
        this.leaseState = leaseState;
        return this;
    }

    public void setLeaseState(LeaseState leaseState) {
        this.leaseState = leaseState;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public VehicleInformation vehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleInformation)) {
            return false;
        }
        return id != null && id.equals(((VehicleInformation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VehicleInformation{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", number='" + getNumber() + "'" +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", price=" + getPrice() +
            ", leaseState='" + getLeaseState() + "'" +
            "}";
    }
}
