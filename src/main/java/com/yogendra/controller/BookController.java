package com.yogendra.controller;

import com.yogendra.annotation.MethodMonitor;
import com.yogendra.model.BookVolumeList;
import com.yogendra.service.BookService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService, MeterRegistry meterRegistry) {
        this.bookService = bookService;
    }

    @GetMapping
    @MethodMonitor(name = "book.list", uri = "/book")
    public ResponseEntity<BookVolumeList> books() {
        return ResponseEntity.ok(bookService.books());
    }
}
