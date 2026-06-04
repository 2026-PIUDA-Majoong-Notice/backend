package com.notice.controller.user;

import com.notice.dto.user.PostUserRequest;
import com.notice.dto.user.PostUserResponse;
import com.notice.service.user.PostUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final PostUserService postUserService;

    @PostMapping
    public ResponseEntity<PostUserResponse> postUser(
            @Valid @RequestBody PostUserRequest request
    ) {
        PostUserResponse response = postUserService.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}