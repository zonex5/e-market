package xyz.toway.emarket.model;

import lombok.Data;

@Data
public class OrderInputModel {
    private Integer customerId;
    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer country;
    private String address;
    private String zip;
    private String city;
    private Boolean save;
}
