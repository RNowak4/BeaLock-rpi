package com.wpam.controller;

import com.wpam.exception.BeaconAlreadyRegisteredException;
import com.wpam.exception.NoSuchBeaconRegisteredException;
import com.wpam.service.BeaconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/lock")
public class LockBeaconController {
    private BeaconService beaconService;

    @Autowired
    public LockBeaconController(BeaconService beaconService) {
        this.beaconService = beaconService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/lock/{beaconId}")
    public void addBeaconLock(@PathVariable("beaconId") final String beaconId)
            throws BeaconAlreadyRegisteredException {
        beaconService.addBeacon(beaconId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/lock/{beaconId}")
    public void removeBeaconLock(@PathVariable("beaconId") final String beaconId)
            throws BeaconAlreadyRegisteredException, NoSuchBeaconRegisteredException {
        beaconService.deregisterBeacon(beaconId);
    }
}