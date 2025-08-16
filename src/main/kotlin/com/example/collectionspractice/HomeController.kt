package com.example.collectionspractice

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/")
    fun index(): String {
        // Swagger UI 기본 경로로 리다이렉트
        return "redirect:/swagger-ui/index.html"
    }
}
