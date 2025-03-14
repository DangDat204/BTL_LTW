package com.example.BTL.exception;

import com.example.BTL.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = Exception.class)
//    day la exception tong quat khi exception khong nhay vao cac loai ta define
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception){
        log.error("Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    //    tu dinh nghia ra 1 exception ma ta muon tra ve: AppException
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
//        if (userRepository.existsByUsername(request.getUsername()))
//            throw new AppException(ErrorCode.USER_EXISTED);  tu UserService
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//  truong hop RegisterRequest: dat ten, password
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();
//        tra ve message trong RegisterRequest
//        @Size(min = 5, message = "INVALID_PASSWORD")
//        private String password;

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
//        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
//
        try {
//            set lai errorCode
            errorCode = ErrorCode.valueOf(enumKey);
//           ErrorCode.valueOf("USER_EXISTED") sẽ trả về ErrorCode.USER_EXISTED
//           Tìm trong ErrorCode một giá trị có tên khớp với enumKey.
//          Nếu tìm thấy, gán giá trị đó cho errorCode.
//          Nếu không tìm thấy, nó sẽ ném IllegalArgumentException. Vi vay ta dung try catch
        } catch (IllegalArgumentException e){

        }

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
