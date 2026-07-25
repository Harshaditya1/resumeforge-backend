package com.resumeforge.analysis;

import com.resumeforge.analysis.dto.AnalysisRequestDto;
import com.resumeforge.analysis.dto.AnalysisResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "http://localhost:5173")
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping
    public ResponseEntity<AnalysisResponseDto> analyze(
            @Valid @RequestBody AnalysisRequestDto request) {

        AnalysisResponseDto response = analysisService.analyze(request);

        return ResponseEntity.ok(response);
    }
}