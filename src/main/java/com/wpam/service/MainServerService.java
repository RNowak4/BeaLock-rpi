package com.wpam.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MainServerService {

    @Value("${main.server.port}")
    private String mainServerPost;
}
