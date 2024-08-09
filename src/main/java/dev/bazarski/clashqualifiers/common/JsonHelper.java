package dev.bazarski.clashqualifiers.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.bazarski.clashqualifiers.records.Account;

import java.io.*;
import java.util.List;

public class JsonHelper {

    public static List<Account> readAccounts() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = JsonHelper.class.getResourceAsStream("/json/accounts.json")) {
            return mapper.readValue(inputStream, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeAccounts(List<Account> accounts) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (OutputStream outputStream = new FileOutputStream("/json/accounts.json")) {
            mapper.writeValue(outputStream, accounts);
        }
    }
}
