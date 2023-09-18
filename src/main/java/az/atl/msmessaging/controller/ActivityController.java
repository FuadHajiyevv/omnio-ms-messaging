package az.atl.msmessaging.controller;

import az.atl.msmessaging.dto.response.ActivityReportResponse;
import az.atl.msmessaging.service.impl.ActivityReportServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityReportServiceImpl activityReportService;

    public ActivityController(ActivityReportServiceImpl activityReportService) {
        this.activityReportService = activityReportService;
    }

    @GetMapping("/report")
    public ResponseEntity<ActivityReportResponse> getReport() {
        return ResponseEntity.ok(activityReportService.getActivityReport());
    }
}
