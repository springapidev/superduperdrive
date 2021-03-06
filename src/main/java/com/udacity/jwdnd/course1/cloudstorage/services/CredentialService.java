package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
/**
 * @Author Mohammad Rajaul Islam
 * @Since v.1.0
 */
@Service
public class CredentialService implements CredentialMapper {
    @Autowired
    private CredentialMapper credentialMapper;
    @Autowired
    private AesSecurityService aesSecurityService;

    @Autowired
    private CommonService commonService;

    @Override
    public List<Credentials> findAll() {
        return credentialMapper.findAll();
    }

    @Override
    public List<Credentials> findAllByUsername(int userid) {
             return credentialMapper.findAllByUsername(userid);
    }

    @Override
    public Credentials findById(long id) {
       // Credentials credentials=credentialMapper.findById(id);
      //  credentials.setPassword(aesSecurityService.decrypt(credentials.getPassword(), credentials.getKey()));
        return credentialMapper.findById(id);
    }

    @Override
    public Credentials findByUrl(String url) {
        return credentialMapper.findByUrl(url);
    }

    @Override
    public int deleteById(long credentialid) {
        return credentialMapper.deleteById(credentialid);
    }

    @Override
    public int insert(Credentials credentials) {
        credentials.setCredentialid(credentialMapper.findAll().size()+1);
        credentials.setPassword(aesSecurityService.encrypt(credentials.getPassword(), credentials.getKey()));
        credentials.setUserid(commonService.getUserId());
        return credentialMapper.insert(credentials);
    }

    @Override
    public int update(Credentials credentials) {
        credentials.setUserid(commonService.getUserId());
        credentials.setPassword(aesSecurityService.encrypt(credentials.getPassword(), credentials.getKey()));
        return credentialMapper.update(credentials);
    }


    public Credentials findDecryptedCredentialById(long id) {
         Credentials credentials=credentialMapper.findById(id);
          credentials.setPassword(aesSecurityService.decrypt(credentials.getPassword(), credentials.getKey()));
        return credentials;
    }
}
