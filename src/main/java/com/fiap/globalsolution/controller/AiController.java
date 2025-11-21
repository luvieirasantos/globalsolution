package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@Tag(name = "AI", description = "Recursos de Inteligência Artificial")
public class AiController {

    @Autowired
    private AiService aiService;

    @GetMapping("/recommendation")
    @Operation(summary = "Obter recomendação de estudos")
    public ResponseEntity<String> getRecommendation(@RequestParam String topic) {
        return ResponseEntity.ok(aiService.getRecommendation(topic));
    }

    @GetMapping("/course-description")
    @Operation(summary = "Gerar descrição de curso")
    public ResponseEntity<String> generateCourseDescription(@RequestParam String courseName) {
        return ResponseEntity.ok(aiService.generateCourseDescription(courseName));
    }
}
