package org.kn.hw.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Historical {
    private JsonNode bpi;
    private String disclaimer;
    private Time time;

    @Data
    @Getter
    @Setter
    private static class Time {
        private String updated;
        private String updatedISO;
    }
}
