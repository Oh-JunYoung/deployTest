package d.Controller;

import d.Domain.Dto.DashboardDto;
import d.Domain.ResponseDto.DashboardResponseDto;
import d.Service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping(value = "/dashboard")
    public ResponseEntity<Flux<ServerSentEvent<DashboardDto>>> sseDashboard() {
        return new ResponseEntity<>(dashboardService.sse(), HttpStatus.OK);
    }

}
