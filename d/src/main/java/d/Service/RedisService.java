package d.Service;

import d.Domain.Entity.Dashboard;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@NoArgsConstructor
public class RedisService {
    @Autowired
    private RedisTemplate<String, Dashboard> dashboardRedisRepository;

    public boolean saveDashboard(Dashboard dashboard) {
        ValueOperations<String, Dashboard> valueOperations = dashboardRedisRepository.opsForValue();
        valueOperations.set("dashboard", dashboard);
        return true;
    }

    public Dashboard getDashboard() {
        ValueOperations<String, Dashboard> valueOperations = dashboardRedisRepository.opsForValue();
        try {
            return valueOperations.get("dashboard");
        } catch (NullPointerException e) {
            return null;
        }
    }

    public boolean updateDashboard(Dashboard dashboard) {
        Dashboard foundDashboard = getDashboard();

        foundDashboard.setId("dashboard");
        if(dashboard.getUpdatedTime() != null && foundDashboard.getUpdatedTime().toLocalDateTime().isAfter(dashboard.getUpdatedTime().toLocalDateTime())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "업데이트 시간이 누락되었습니다.");
        if(dashboard.getUpdatedTime() != null) foundDashboard.setUpdatedTime(dashboard.getUpdatedTime());
        saveDashboard(foundDashboard);

        return true;
    }

    public boolean existDashboard() {
        ValueOperations<String, Dashboard> valueOperations = dashboardRedisRepository.opsForValue();
        return valueOperations.get("dashboard") != null;
    }
}
