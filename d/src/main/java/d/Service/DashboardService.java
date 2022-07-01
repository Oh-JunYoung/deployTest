package d.Service;

import d.Domain.Dto.DashboardDto;
import d.Domain.ResponseDto.DashboardResponseDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final RedisService redisService;

    private static final Long refreshTime = 2L;

    public Flux<ServerSentEvent<DashboardDto>> sse() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        // 대시보드를 가져온다.
        DashboardDto dashboardDto = getDashboard();
        if(dashboardDto == null) saveDashboard(initDashboard(now));

        // 대시보드를 갱신한다.
        DashboardDto updatedDashboard = getUpdatedDashboard(now);
        updateDashboard(updatedDashboard);

        System.out.println(getDashboard());
        // 보낸다.
        return Flux.interval(Duration.ofSeconds(refreshTime))
                .map(sequence -> ServerSentEvent.<DashboardDto> builder()
                        .id("/ai/category")
                        .event("periodic-event")
                        .data(getDashboard())
                        .retry(Duration.ofSeconds(refreshTime))
                        .build());
    }

    public boolean saveDashboard(DashboardDto dashboardDto) {
        return redisService.saveDashboard(DashboardDto.toEntity(dashboardDto));
    }

    public DashboardDto getDashboard() {
        try {
            return DashboardDto.of(redisService.getDashboard());
        } catch (NullPointerException e){
            return null;
        }
    }

    public boolean updateDashboard(DashboardDto dashboardDto) {
        return redisService.updateDashboard(DashboardDto.toEntity(dashboardDto));
    }

    public DashboardDto initDashboard(Timestamp now) {
        return DashboardDto.builder()
                .updatedTime(now)
                .build();
    }

    public DashboardDto getUpdatedDashboard(Timestamp now) {
        return DashboardDto.builder()
                .updatedTime(now)
                .build();
    }
}
