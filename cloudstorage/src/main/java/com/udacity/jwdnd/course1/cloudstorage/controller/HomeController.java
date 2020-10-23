package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class HomeController {
    @Autowired
    private FilesService filesService;
    @Autowired
    private UserService service;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;

    @Autowired
    private CommonService commonService;

    @GetMapping("/")
    public String showHome(Model model) {
        loadData(model);
        return "home";
    }

    @PostMapping("/uploadfile")
    public String uploadfiles(@Validated Files files, @RequestParam("fileUpload") MultipartFile file, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errMsg", "something wrong");
            loadData(model);
        }
        Files files1 = filesService.findByFilename(file.getOriginalFilename());
        if (files1 != null) {
            model.addAttribute("fileAlreadyExists", "This file already exists!!!");
            loadData(model);
        } else {
            try {
                files.setFileId(filesService.findAll().size() + 1);
                files.setFilename(file.getOriginalFilename());
                Path path = new File(file.getOriginalFilename()).toPath();
                files.setContenttype(java.nio.file.Files.probeContentType(path));
                files.setFiledata(file.getBytes());
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                files.setUserid(service.findByUserName(auth.getName()).getUserid());
                filesService.insert(files);
                return "redirect:/";
            } catch (IOException ioException) {
                System.out.println(ioException);
            }
            model.addAttribute("successMsg", "Success");
            loadData(model);
        }
        return "home";
    }

    @GetMapping("/file/")
    public void showFileImage(@RequestParam("id") int id,
                              HttpServletResponse response) throws IOException {
        Files files = filesService.findById(id);
        response.setContentType(files.getContenttype());

        InputStream is = new ByteArrayInputStream(files.getFiledata());
        IOUtils.copy(is, response.getOutputStream());
    }

    @GetMapping("/deleteimage")
    public String deleteImage(@RequestParam("id") int id) throws IOException {
        filesService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/savenote")
    public String saveNote(@Validated Note note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errMsg", "something wrong");
            loadData(model);
            return "home";
        }
        noteService.insert(note);
        model.addAttribute("successMsg", "Success");
        loadData(model);
        return "redirect:/";
    }

    @PostMapping("/savecredentials")
    public String savecredential(@Validated Credentials credentials, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errMsg", "something wrong");
            loadData(model);
        } else {
            credentialService.insert(credentials);
            model.addAttribute("successMsg", "Success");
            loadData(model);
            return "redirect:/";
        }
        return "home";
    }

    private void loadData(Model model) {
        model.addAttribute("files", new Files());
        model.addAttribute("notes", new Note());
        model.addAttribute("credentials", new Credentials());
        if (commonService.isLoggedIn()) {
            model.addAttribute("filelist", filesService.findAllByUsername(commonService.getUserId()));
            model.addAttribute("notelist", noteService.findAllByUsername(commonService.getUserId()));
            model.addAttribute("credentiallist", credentialService.findAllByUsername(commonService.getUserId()));
        } else {
            model.addAttribute("filelist", new ArrayList<>());
            model.addAttribute("notelist", new ArrayList<>());
            model.addAttribute("credentiallist", new ArrayList<>());
        }
    }
}
