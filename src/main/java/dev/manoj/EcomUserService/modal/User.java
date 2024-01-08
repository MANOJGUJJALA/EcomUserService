package dev.manoj.EcomUserService.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name="ECOM_USER")
@Getter
@Setter
public class User  extends BaseClass{

private String email;
private String password;

@ManyToMany
private Set<Role> roles=new HashSet<>();

}
