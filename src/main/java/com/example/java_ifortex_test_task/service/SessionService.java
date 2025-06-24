package com.example.java_ifortex_test_task.service;

import com.example.java_ifortex_test_task.dto.SessionResponseDTO;
import com.example.java_ifortex_test_task.entity.User;
import com.example.java_ifortex_test_task.mapper.SessionMapper;
import com.example.java_ifortex_test_task.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.Session;
@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;


    public SessionResponseDTO getFirstDesktopSession() {
        Session session = sessionRepository.getFirstDesktopSession(DeviceType.DESKTOP);
        if (session == null) {
            return new SessionResponseDTO(); // ← безопасно вернуть пустой DTO
        }
        return sessionMapper.toDto(session);
    }

    public List<SessionResponseDTO> getSessionsFromActiveUsersEndedBefore2025() {
        LocalDateTime cutoffDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        List<Session> sessions = sessionRepository.getSessionsFromActiveUsersEndedBefore2025(cutoffDate);

        return sessions.stream()
                .map(sessionMapper::toDto)
                .toList();
    }







}