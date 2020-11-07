package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialsDto;
import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;

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

    @RequestMapping("/")
    public String showHome(Model model) {
        loadData(model);
        return "home";
    }

    @PostMapping("/uploadfile")
    public String uploadfiles(@Validated Files files, @RequestParam("fileUpload") MultipartFile file, BindingResult result, Model model) {
        if (result.hasFieldErrors("filename")) {
            model.addAttribute("errMsg", "something wrong");
            loadData(model);
        }
            try {
                Files files1 = filesService.findByFilename(file.getOriginalFilename());
                if(files1 != null){
                    model.addAttribute("errMsg", "This file is already exist!");
                    loadData(model);
                    return "redirect:/";
                }else {
                    files.setFileId(filesService.findAll().size() + 1);
                    files.setFilename(file.getOriginalFilename());
                    Path path = new File(file.getOriginalFilename()).toPath();
                    files.setContenttype(java.nio.file.Files.probeContentType(path));
                    files.setFiledata(file.getBytes());
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    files.setUserid(service.findByUserName(auth.getName()).getUserid());
                    filesService.insert(files);
                    model.addAttribute("successMsg", "Success");
                    return "redirect:/";
                }
            } catch (IOException ioException) {
                System.out.println(ioException);
            }
            loadData(model);

        return "home";
    }

    @GetMapping("/file/{id}")
    public void showFileImage(@PathVariable("id") int id,
                              HttpServletResponse response, Model model) throws IOException {
        Files files = filesService.findById(id);
//        response.setContentType(files.getContenttype());
//
//        InputStream is = new ByteArrayInputStream(files.getFiledata());
//        IOUtils.copy(is, response.getOutputStream());

        model.addAttribute("pic", Base64.getEncoder().encodeToString(files.getFiledata()));

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


    @GetMapping("/editnote/{noteid}")
    @ResponseBody
    public ResponseEntity<Note> vieweditNote(@PathVariable long noteid) {
        System.out.println("HERE I");
        try {
            return new ResponseEntity<Note>(this.noteService.findById(noteid), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/editnote/{id}")
    public String editNote(@ModelAttribute Note note, BindingResult result, Model model, RedirectAttributes redirectAttributes, @PathVariable int id) {
        System.out.println("HERE II");
        System.out.println("NOTE:" + note.getNoteid() + note.getNotetitle() + note.getNotedescription());
        if (result.hasErrors()) {
            model.addAttribute("errMsg", "something wrong");
            loadData(model);
            return "home";
        }
        noteService.update(note);
        return "redirect:/";
    }

    @GetMapping("/note-delete/{noteid}")
    public String deleteNote(@PathVariable("noteid") int noteid) throws IOException {
        noteService.deleteById(noteid);
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
    @GetMapping("/editcredentials/{id}")
    @ResponseBody
    public ResponseEntity<Credentials> vieweditCredincials(@PathVariable long id) {
        System.out.println("editcredentials  HERE II");
        try {
            return new ResponseEntity<Credentials>(this.credentialService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/editcredentials/{id}")
    public String editCredintials(@Validated Credentials credentials, BindingResult result, Model model, RedirectAttributes redirectAttributes, @PathVariable int id) {
        System.out.println("HERE III");
        System.out.println("Credincials:" + credentials.getCredentialid() + credentials.getUsername() + credentials.getPassword());
        if (result.hasErrors()) {
            model.addAttribute("errMsg", "something wrong");
            loadData(model);
            return "home";
        }
        credentialService.update(credentials);
        return "redirect:/";
    }

    @GetMapping("/decrypt-password/{credentialid}")
    @ResponseBody
    public ResponseEntity<CredentialsDto> viewPassword(@PathVariable long credentialid) {
        System.out.println("HERE Credential");
        Credentials credentials = credentialService.findById(credentialid);
        String decryptedPassword = AesSecurityService.decrypt(credentials.getPassword(),credentials.getKey());
        CredentialsDto credentialsDTO = new CredentialsDto();
        credentialsDTO.setSecretkey(credentials.getSecretkey());
        credentialsDTO.setDecryptedpassword(decryptedPassword);
        try {
            return new ResponseEntity<CredentialsDto>(credentialsDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/credential-delete/{credentialid}")
    public String deleteCredentials(@PathVariable("credentialid") int credentialid) throws IOException {
        credentialService.deleteById(credentialid);
        return "redirect:/";
    }

    private void loadData(Model model) {
        model.addAttribute("files", new Files());
        model.addAttribute("notes", new Note());
        model.addAttribute("credentials", new Credentials());
        if (commonService.isLoggedIn()) {
            model.addAttribute("filelist", filesService.findAllFiles());
            model.addAttribute("notelist", noteService.findAllByUsername(commonService.getUserId()));
            model.addAttribute("credentiallist", credentialService.findAllByUsername(commonService.getUserId()));
        } else {
            model.addAttribute("filelist", new ArrayList<>());
            model.addAttribute("notelist", new ArrayList<>());
            model.addAttribute("credentiallist", new ArrayList<>());
        }
    }
}
