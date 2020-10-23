package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NoteService implements NoteMapper {
    @Resource
    private NoteMapper noteMapper;
    @Resource
    private UserService userService;
    @Autowired
    private HashService hashService;


    @Override
    public List<Note> findAll() {
        return noteMapper.findAll();
    }

    @Override
    public List<Note> findAllByUsername(int userid) {
        return noteMapper.findAllByUsername(userid);
    }

    @Override
    public Note findById(long id) {
        return noteMapper.findById(id);
    }

    @Override
    public Note findByNotetitle(String notetitle) {
        return noteMapper.findByNotetitle(notetitle);
    }

    @Override
    public int deleteById(long id) {
        return noteMapper.deleteById(id);
    }

    @Override
    public int insert(Note notes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        notes.setUserid(user.getUserid());
        notes.setNoteid(noteMapper.findAll().size()+1);
        return noteMapper.insert(notes);
    }

    @Override
    public int update(Note notes) {
        return 0;
    }
}
