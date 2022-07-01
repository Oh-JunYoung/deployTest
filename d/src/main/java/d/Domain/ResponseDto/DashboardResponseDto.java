package d.Domain.ResponseDto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDto {
    private String id;
    private Timestamp updatedTime;
}
