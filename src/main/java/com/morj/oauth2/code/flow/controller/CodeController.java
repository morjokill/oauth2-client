package com.morj.oauth2.code.flow.controller;

import com.morj.oauth2.code.flow.OAuth2CodeFlowApplication;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller(value = "/")
public class CodeController {
    @SneakyThrows
    @GetMapping(value = "/")
    public ResponseEntity<Object> auth() {
        ResponseEntity.BodyBuilder status = ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY);
        String login = OAuth2CodeFlowApplication.login();
        status.header("Location", login);
        return status.build();
    }

    @GetMapping(value = "/code")
    public String code(@RequestParam("code") String code, Model model) {
        System.out.println("CODE CAME: " + code);
        String accessToken = OAuth2CodeFlowApplication.getAccessToken(code);
        String payload = "";
        if (accessToken != null) {
            payload = new String(Base64.decodeBase64(accessToken.split("\\.")[1]));
        }
        model.addAttribute("token", accessToken);
        model.addAttribute("payload", payload);
        return "index";
    }
}
