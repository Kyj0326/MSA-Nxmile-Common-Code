package com.nxmile.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@Data
public class GroupCodeDto {

    @NotEmpty
    private String grpCode;

    @NotEmpty
    private String commonCode;

    @NotEmpty
    private String arg;

    @NotEmpty
    private String groupCodeName;

    @NotEmpty
    private String commonCodeName;

    @NotNull
    private boolean applyYn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime validFromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime validToDate;

}
