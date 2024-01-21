package dev.manoj.EcomUserService.security.repository;

import java.util.Optional;

//import sample.jpa.entity.client.Client;

import dev.manoj.EcomUserService.security.modal.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}
