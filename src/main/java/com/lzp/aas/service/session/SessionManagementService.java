package com.lzp.aas.service.session;

import com.lzp.aas.exception.AppException;
import com.lzp.aas.exception.HttpAppError;
import com.lzp.aas.model.Session;
import com.lzp.aas.model.User;
import com.lzp.aas.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionManagementService {

    private final SessionRepository sessionRepository;

    public Session createSessionForUser(User user) {
        Base64.Encoder encoder = Base64.getEncoder().withoutPadding();
        SecureRandom random;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        }
        catch (NoSuchAlgorithmException exception) {
            log.error(exception.getMessage(), exception);
            throw new AppException(HttpAppError.EXTERNAL_API_ERROR, exception.getMessage());
        }
        String token = encoder.encodeToString(random.generateSeed(16));
        token = token.replace("+", "Q");
        token = token.replace("/", "r");
        token = token.replace("=", "t");

        Session session = new Session();
        session.setUser(user);
        session.setUserRole(user.getUserRole());
        session.setToken(token);
        session.setCreatedAt(LocalDateTime.now());
        return sessionRepository.save(session);
    }

    public void updateUsersSession(User user) {
        Optional<Session> usersSession = sessionRepository.findByUser(user)
                .stream().max(Comparator.comparing(Session::getCreatedAt));
        if (usersSession.isPresent()) {
            Session session = usersSession.get();
            session.setUser(user);
            session.setUserRole(user.getUserRole());
            sessionRepository.save(session);
        }
    }
}
