package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;


public class FileReader {

    public Profile getDataFromFile(File file) {
                Map<String, String> personalData = new HashMap<>();
        Profile profile = new Profile();
        StringBuilder data = new StringBuilder();
        try (RandomAccessFile aFile = new RandomAccessFile(file, "r");
             FileChannel inChannel = aFile.getChannel();) {
            long fileSize = inChannel.size();

            //Create buffer of the file size
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            inChannel.read(buffer);
            StringBuilder sb = new StringBuilder();


            while (inChannel.read(buffer) > 0) {
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {
                    data.append((char) buffer.get());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        profile.setAge(Integer.parseInt(personalData.get("Age")));
        profile.setName(personalData.get("Name"));
        profile.setEmail(personalData.get("Email"));
        profile.setPhone(Long.parseLong(personalData.get("Phone")));
        return profile;
    }
}
