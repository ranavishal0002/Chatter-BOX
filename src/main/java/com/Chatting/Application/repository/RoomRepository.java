package com.Chatting.Application.repository;


import com.Chatting.Application.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
