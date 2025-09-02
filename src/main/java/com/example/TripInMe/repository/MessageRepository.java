package com.example.TripInMe.repository;

import com.example.TripInMe.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("""
        select m from Message m
            where (m.sender.id = :a and m.recipient.id = :b)
            or (m.sender.id = :b and m.recipient.id = :a)
        order by m.id desc
    """
    )
    @EntityGraph(attributePaths = {"sender","recipient"})
    Page<Message> findConversation(@Param("a") Long a, @Param("b") Long b, Pageable pageable);

    @Query("select count (m) from Message m where m.recipient.id = :uid and m.readAt is null")
    long countUnread(@Param("uid") Long userId);

    @Query(value = """
    select
      case when sender_id = :uid then recipient_id else sender_id end as partner_id,
      max(id) as last_id,
      sum(case when recipient_id = :uid and read_at is null then 1 else 0 end) as unread
    from messages
    where sender_id = :uid or recipient_id = :uid
    group by partner_id
    order by last_id desc
    """, nativeQuery = true)
    java.util.List<Object[]> listThreads(@Param("uid") Long userId);

}
