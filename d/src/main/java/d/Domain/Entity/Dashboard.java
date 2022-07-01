package d.Domain.Entity;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@RedisHash(value = "bashboard", timeToLive = 60L * 3)
public class Dashboard implements Serializable {
    @Id
    private String id;

    private Timestamp updatedTime;
}
