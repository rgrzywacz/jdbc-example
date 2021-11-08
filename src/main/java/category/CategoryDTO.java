package category;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    @Getter
    private int id;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String code;

    public CategoryDTO(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
