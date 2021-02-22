package com.demo.phms3.service;

import com.demo.phms3.pojo.ClientImporterDTO;

import java.io.IOException;

public interface ClientImporterService {
    void processClientImport(ClientImporterDTO clientImportInfo) throws IOException;
}
