package com.travelagency.msreservas.model;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String keycloakId;
    private String fullName;
    private String email;
}
