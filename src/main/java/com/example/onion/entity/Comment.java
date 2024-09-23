package com.example.onion.entity;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @SequenceGenerator(name = "comment_seq", sequenceName = "seq_comment", allocationSize = 1)
    private int commentId;

    @Column(nullable = false)
    private int communityId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 1000)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date logtime = new Date(); // 작성 시간
}