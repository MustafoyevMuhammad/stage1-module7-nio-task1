package com.epam.mjc.nio;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;


public class FileReader {

    public Profile getDataFromFile(File file) {
                Map<String,String> personalData = new HashMap<>();
        Profile profile = new Profile();
        String data;
        try (RandomAccessFile aFile = new RandomAccessFile(file, "r");
             FileChannel inChannel = aFile.getChannel();) {

            long fileSize = inChannel.size();

            //Create buffer of the file size
            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);
            buffer.flip();
            String str = "";
            for (int i = 0; i < fileSize; i++) {
                str += (char)buffer.get();
            }
            Scanner sc = new Scanner(str);
            while ((data = sc.nextLine()) != null){
                String[] keyValue = data.split(":");
                personalData.put(keyValue[0].trim(), keyValue[1].trim());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        profile.setAge(Integer.parseInt(personalData.get("Age")));
        profile.setName(personalData.get("Name"));
        profile.setEmail(personalData.get("Email"));
        profile.setPhone(Long.parseLong(personalData.get("Phone")));
        return profile;
    }
}
