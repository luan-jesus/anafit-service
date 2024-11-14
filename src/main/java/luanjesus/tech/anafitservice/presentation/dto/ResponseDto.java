package luanjesus.tech.anafitservice.presentation.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseDto<T> {
    private T data;
    private List<String> errors = new ArrayList<>();
}
