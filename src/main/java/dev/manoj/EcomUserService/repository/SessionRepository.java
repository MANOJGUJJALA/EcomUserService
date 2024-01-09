package dev.manoj.EcomUserService.repository;

import dev.manoj.EcomUserService.modal.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {


    Optional<Session> findByTokenAndUser_Id(String token,Long userId);
    Optional<List<Session>> findByUser_Id(Long userId);
}
