package com.nucleon.maru.DI;

import com.nucleon.maru.Service.ApiService;
import com.nucleon.maru.Service.MeetingRepository;

public class DI {

    private static final ApiService service = new MeetingRepository();

    public static ApiService getApiService() {
        return service;
    }

    public static ApiService getNewInstanceApiService() {
        return new MeetingRepository();
    }
}
