package com.Chatting.Application.repository;


import com.Chatting.Application.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomId(String roomId);

    boolean existsByRoomId(String roomId);
}
