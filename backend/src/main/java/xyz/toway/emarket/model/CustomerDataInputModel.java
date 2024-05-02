package xyz.toway.emarket.model;

import lombok.Data;

@Data
public class CustomerDataInputModel {
    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
}
