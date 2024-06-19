package com.yogendra.service;

import com.yogendra.annotation.MethodMonitor;
import com.yogendra.model.BookVolumeList;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookService {
    private final RestTemplate restTemplate;

    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public BookVolumeList books() {
        return restTemplate
                .getForObject(
                        "https://www.googleapis.com/books/v1/volumes?q=sex&max_results=2",
                        BookVolumeList.class
                );
    }
}
