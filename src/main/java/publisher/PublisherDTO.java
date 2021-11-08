package publisher;

import lombok.*;

@Builder
@ToString
public class PublisherDTO {
    @Getter
    private int id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String dialingCode;
    @Getter
    @Setter
    private String phoneNumber;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String description;
}
