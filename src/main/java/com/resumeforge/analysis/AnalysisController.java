package com.resumeforge.analysis;

import com.resumeforge.analysis.dto.AnalysisResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "http://localhost:5173")
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(
            AnalysisService analysisService
    ) {
        this.analysisService = analysisService;
    }

    /**
     * Analyze latest uploaded Resume
     * against latest Job Description.
     */
    @PostMapping
    public ResponseEntity<AnalysisResponseDto> analyze() {

        AnalysisResponseDto response =
                analysisService.analyze();

        return ResponseEntity.ok(response);
    }

    /**
     * Latest Analysis
     */
    @GetMapping("/latest")
    public ResponseEntity<AnalysisResponseDto> getLatestAnalysis() {

        AnalysisResponseDto response =
                analysisService.getLatestAnalysis();

        return ResponseEntity.ok(response);
    }

    /**
     * Analysis History
     */
    @GetMapping("/history")
    public ResponseEntity<List<AnalysisResponseDto>> getAnalysisHistory() {

        List<AnalysisResponseDto> response =
                analysisService.getAnalysisHistory();

        return ResponseEntity.ok(response);
    }
}