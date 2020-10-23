package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class FilesService implements FilesMapper {
    @Resource
    private FilesMapper filesMapper;
    @Autowired
    private HashService hashService;


    @Override
    public List<Files> findAll() {
        return filesMapper.findAll();
    }

    @Override
    public List<Files> findAllByUsername(int userid) {
        return filesMapper.findAllByUsername(userid);
    }

    @Override
    public Files findById(long id) {
        return filesMapper.findById(id);
    }

    @Override
    public Files findByFilename(String filename) {
        return filesMapper.findByFilename(filename);
    }

    @Override
    public int deleteById(long id) {
        return filesMapper.deleteById(id);
    }

    @Override
    public int insert(Files files) throws IOException {

        return filesMapper.insert(files);
    }
}