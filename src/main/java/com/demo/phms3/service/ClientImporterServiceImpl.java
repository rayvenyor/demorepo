package com.demo.phms3.service;

import com.demo.phms3.pojo.Client;
import com.demo.phms3.pojo.ClientImporterDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service
@Primary
@Transactional
@Slf4j
public class ClientImporterServiceImpl implements ClientImporterService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void processClientImport(ClientImporterDTO clientImportInfo) throws IOException {

        String filePath = clientImportInfo.getFilePath();
        String fileName = clientImportInfo.getFileName();

        try (BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
             FileWriter csvWriter = new FileWriter(fileName + "-bad.csv");
             FileWriter logFileWriter = new FileWriter(fileName+".log")){

            csvWriter.append(csvReader.readLine()+"\n"); //skips first line and adds it to bad csv

            int successCtr = 0, failCtr = 0;

            clientRepository.deleteAll();

            String row;
            while ((row = csvReader.readLine()) != null) {
                row = row.replace("base64,", "base64|");
                String[] rawData = row.split(",");

                if (rawData.length > 10) {
                    csvWriter.append(row+"\n");
                    failCtr++;
                } else if (rawData.length == 10) {
                    Client client = convertCsvToClientModel(row, rawData, csvWriter);
                    if (client != null) {
                        clientRepository.save(client);
                        successCtr++;
                    } else {
                        failCtr++;
                    }
                } else {
                    csvWriter.append(row+"\n");
                    failCtr++;
                    log.error(row); //Check possible scenario if column count is greater than intended
                }
            }


            int totalCount = successCtr + failCtr;
            logFileWriter.write(totalCount+" rows read.\n" + successCtr + " rows inserted successfully.\n"+ failCtr+" rows failed to insert.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public Client convertCsvToClientModel(String row, String[] rawData, FileWriter csvWriter) throws IOException {
        Client client = new Client();
        Double monthlyFee = null;

        try {
            if (rawData[6].length() > 1) {
                monthlyFee = Double.parseDouble(rawData[6].substring(1));
            }
        } catch (NumberFormatException e) {
            log.error("Invalid currency format. Logging bad data.");
            csvWriter.append(row+"\n");

            return null;
        }

        client.setFirstName(rawData[0]);
        client.setLastName(rawData[1]);
        client.setEmail(rawData[2]);
        client.setGender(rawData[3]);
        client.setImg(rawData[4]);
        client.setCardType(rawData[5]);
        client.setMonthlyFee(monthlyFee);
        client.setPaid(Boolean.parseBoolean(rawData[7]));
        client.setInactive(Boolean.parseBoolean(rawData[8]));
        client.setCity(rawData[9]);

        return client;
    }
}

