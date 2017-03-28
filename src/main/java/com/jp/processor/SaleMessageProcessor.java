package com.jp.processor;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.message.SaleMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class SaleMessageProcessor implements MessageProcessor {

    public void process(String fileName) {
        File salesNotificationsFile = openFile(fileName);
        Scanner scanner = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            scanner = new Scanner(salesNotificationsFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                SaleMessage saleMessage = mapper.readValue(line, SaleMessage.class);
                System.out.println(saleMessage);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Opens a file specified by fileName
     * @param fileName File Name
     * @return File
     */
    private File openFile(String fileName) {

        if(fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Incorrect input file name " + fileName);
        }

        URL url = getClass().getClassLoader().getResource(fileName);

        if(url == null) {
            throw new IllegalArgumentException("Can't open file " + fileName);
        }

        return new File(url.getFile());
    }
}
