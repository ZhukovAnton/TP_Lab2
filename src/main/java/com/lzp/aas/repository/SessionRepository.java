package com.lzp.aas.repository;

import com.lzp.aas.model.Session;
import com.lzp.aas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByToken(String token);

    Set<Session> findByUser(User user);

}
