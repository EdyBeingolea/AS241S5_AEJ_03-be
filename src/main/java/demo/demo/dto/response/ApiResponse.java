package demo.demo.dto.response;

public record ApiResponse<T>(Integer codigo, String mensaje, T data, boolean estado) {

    public static <T> ApiResponse<T> succes(String mensaje, T data) {
        return new ApiResponse<>(200, mensaje, data, Boolean.TRUE);
    }

    public static <T> ApiResponse<T> error(String mensaje, T data) {
        return new ApiResponse<>(400, mensaje, data, Boolean.FALSE);
    }
}
