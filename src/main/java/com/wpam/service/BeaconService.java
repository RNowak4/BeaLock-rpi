package com.wpam.service;

import com.wpam.exception.BeaconAlreadyRegisteredException;
import com.wpam.exception.NoSuchBeaconRegisteredException;
import com.wpam.utils.BluetoothService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class BeaconService {
    private BluetoothService bluetoothService;
    private HashSet<String> registeredBeacons = new HashSet<>();

    @Autowired
    public BeaconService(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    public synchronized void addBeacon(final String beaconName) throws BeaconAlreadyRegisteredException {
        if (registeredBeacons.contains(beaconName)) {
            throw new BeaconAlreadyRegisteredException();
        }

        registeredBeacons.add(beaconName);
    }

    public synchronized void deregisterBeacon(final String beaconName) throws NoSuchBeaconRegisteredException {
        if (!registeredBeacons.contains(beaconName)) {
            throw new NoSuchBeaconRegisteredException();
        }

        registeredBeacons.remove(beaconName);
    }

    @Scheduled(fixedRate = 1500)
    public synchronized void checkIfBeaconsInArea() {
        bluetoothService.checkIfInArea(registeredBeacons);
    }
}