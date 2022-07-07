package com.example.letsgongbu.repository;

import com.example.letsgongbu.domain.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long> {
    Optional<StudyRoom> findById(Long roomId);
}
