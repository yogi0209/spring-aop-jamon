package com.yogendra.controller;

import com.yogendra.annotation.MethodMonitor;
import com.yogendra.model.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {

    public final JdbcTemplate jdbcTemplate;

    public AccountController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    @MethodMonitor(name = "account.list", uri = "/account")
    public ResponseEntity<List<Account>> account() {
        List<Account> account = jdbcTemplate.query("select * from account", new BeanPropertyRowMapper(Account.class));
        return ResponseEntity.ok(account);
    }
}
