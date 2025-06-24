package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query(value = "SELECT * FROM sessions WHERE device_type = ?1 ORDER BY started_at_utc ASC LIMIT 1", nativeQuery = true)
    Session getFirstDesktopSession(DeviceType deviceType);

    @Query("""
    SELECT s
    FROM Session s
    JOIN FETCH s.user u
    WHERE u.deleted = false AND s.endedAtUtc < :endDate
    ORDER BY s.startedAtUtc DESC
""")
    List<Session> getSessionsFromActiveUsersEndedBefore2025(@Param("endDate") LocalDateTime endDate);

}