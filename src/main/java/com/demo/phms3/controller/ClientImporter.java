package com.demo.phms3.controller;

import com.demo.phms3.pojo.Client;
import com.demo.phms3.pojo.ClientImporterDTO;
import com.demo.phms3.service.ClientImporterService;
import com.demo.phms3.service.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
public class ClientImporter {

    @Autowired
    ClientImporterService clientImporterService;
    @Autowired
    ClientRepository clientRepository;

    @GetMapping
    public int getClientListSize (){
         List<Client> clientList = clientRepository.findAll();

         if (!CollectionUtils.isEmpty(clientList)) {
             return clientList.size();
         }

         return 0;
    }

    @PostMapping
    public void importClientList (@RequestBody @Valid ClientImporterDTO clientImportInfo) throws IOException {
        clientImporterService.processClientImport(clientImportInfo);
    }
}

