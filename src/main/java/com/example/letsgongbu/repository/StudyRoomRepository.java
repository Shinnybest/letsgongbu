package com.example.letsgongbu.repository;

import com.example.letsgongbu.domain.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long> {
    Optional<StudyRoom> findById(Long roomId);

    @Query("select distinct s from StudyRoom s join fetch s.memberStudyRooms m where m.member.id = :memberId")
    List<StudyRoom> findAllByMemberId(Long memberId);
}
