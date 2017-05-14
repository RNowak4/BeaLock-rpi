package com.wpam.utils;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

@Service
public class BluetoothService {
    private Runtime runtime = Runtime.getRuntime();
    private final static String SET_BLUETOOTH_DOWN = "sudo hciconfig hci0 down";
    private final static String SET_BLUETOOTH_UP = "sudo hciconfig hci0 up";
    private final static String SCAN_FOR_BEACONS = "sudo timeout -sHUP 3s hcitool -i hci0 lescan";

    public void checkIfInArea(HashSet<String> registeredBeacons) {
        try {
            final ProcessBuilder pb = new ProcessBuilder("./skrypt.sh");
            final Process proc = pb.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String s;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            String[] command = {"./skrypt.sh"};
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String result = "";
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
                result += s;
            }
            reader.close();
            System.out.println(executeCommand(""));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }
}
