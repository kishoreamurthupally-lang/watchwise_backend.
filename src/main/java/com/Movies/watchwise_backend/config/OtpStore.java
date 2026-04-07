package com.Movies.watchwise_backend.config;

import java.util.concurrent.ConcurrentHashMap;

public class OtpStore {
    public static ConcurrentHashMap<String, String> otpMap = new ConcurrentHashMap<>();
}