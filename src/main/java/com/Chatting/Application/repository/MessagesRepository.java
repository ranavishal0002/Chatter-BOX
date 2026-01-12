package com.Chatting.Application.repository;

import com.Chatting.Application.entities.Messages;
import com.Chatting.Application.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {

    Page<Messages> findByRoomOrderByTimeStampDesc(Room room, Pageable pageable);
}
