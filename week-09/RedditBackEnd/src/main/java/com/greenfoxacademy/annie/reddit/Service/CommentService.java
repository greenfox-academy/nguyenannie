package com.greenfoxacademy.annie.reddit.Service;

import com.greenfoxacademy.annie.reddit.Entity.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    void save(Comment comment);
    void delete(long id);
}
