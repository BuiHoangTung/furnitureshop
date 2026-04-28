package com.myproject.furnitureshop.entity;

import com.myproject.furnitureshop.enums.AddressTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "delivery_infors")
public class DeliveryInforEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "ward")
    private String ward;

    @Column(name = "district")
    private String district;

    @Column(name = "city")
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "street_address")
    private AddressTypes streetAddress;

    @Column(name = "address_type")
    private String addressType;

    @Column(name = "is_default")
    private boolean isDefault;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;
}
