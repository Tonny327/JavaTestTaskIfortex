package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
    SELECT u.*
    FROM users u
    WHERE u.id IN (
        SELECT s.user_id
        FROM sessions s
        WHERE s.device_type = 1
    )
    ORDER BY (
        SELECT MAX(s2.started_at_utc)
        FROM sessions s2
        WHERE s2.user_id = u.id
    ) DESC
""", nativeQuery = true)
    List<User> getUsersWithAtLeastOneMobileSession();



    @Query(value = """
    SELECT u.*
    FROM "users" u
    JOIN sessions s ON u.id = s.user_id
    GROUP BY u.id
    ORDER BY COUNT(s.id) DESC
    LIMIT 1
""", nativeQuery = true)
    User getUserWithMostSessions();

}
