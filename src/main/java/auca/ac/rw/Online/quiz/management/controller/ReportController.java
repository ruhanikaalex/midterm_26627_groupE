package auca.ac.rw.Online.quiz.management.controller;

import auca.ac.rw.Online.quiz.management.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import auca.ac.rw.Online.quiz.management.model.Report;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<Report> list() { return reportService.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Report> get(@PathVariable Long id) {
        return reportService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Report> create(@RequestBody Report report) {
        Report saved = reportService.save(report);
        return ResponseEntity.created(URI.create("/api/reports/" + saved.getId())).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reportService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/scores/csv")
    public ResponseEntity<byte[]> exportScoresCsv() {
        byte[] data = reportService.exportScoresCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=quiz-scores.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(data);
    }
}


