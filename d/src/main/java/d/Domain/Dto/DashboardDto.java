package d.Domain.Dto;

import d.Domain.Entity.Dashboard;
import d.Domain.ResponseDto.DashboardResponseDto;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDto {
    private String id;
    private Timestamp updatedTime;

    public static DashboardDto of(Dashboard dashboard) {
        return DashboardDto.builder()
                .id(dashboard.getId())
                .updatedTime(dashboard.getUpdatedTime())
                .build();
    }

    public static Dashboard toEntity(DashboardDto dashboardDto) {
        return Dashboard.builder()
                .id(dashboardDto.getId())
                .updatedTime(dashboardDto.getUpdatedTime())
                .build();
    }

    public DashboardResponseDto toResponse() {
        return DashboardResponseDto.builder()
                .updatedTime(updatedTime)
                .build();
    }
}
