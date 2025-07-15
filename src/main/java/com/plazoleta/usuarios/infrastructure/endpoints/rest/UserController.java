package com.plazoleta.usuarios.infrastructure.endpoints.rest;

import com.plazoleta.usuarios.application.dto.request.SaveUserRequest;
import com.plazoleta.usuarios.application.dto.response.SaveUserResponse;
import com.plazoleta.usuarios.application.dto.response.UserResponse;
import com.plazoleta.usuarios.application.service.UserService;
import com.plazoleta.usuarios.domain.models.UserModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name= "Users", description = "Controller for User")
public class UserController {
    private final UserService userService;
    @PostMapping("/owner")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create owner user",
            description = "This method saves a user with owner role",
            tags = {"Users"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Creates a new User with owner role",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveUserRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaveUserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request"
                    )
            }
    )
    public ResponseEntity<SaveUserResponse> saveOwner(@RequestBody SaveUserRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.saveOwner(request));
    }

    @PostMapping("/employee")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<SaveUserResponse> saveEmployee(
            @RequestBody @Valid SaveUserRequest request) {
        SaveUserResponse response = userService.saveEmployee(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @PostMapping("/client")
    public ResponseEntity<SaveUserResponse> saveClient(@RequestBody SaveUserRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.saveClient(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        UserModel user = userService.getUserById(id);
        UserResponse resp = new UserResponse(user.getId(), user.getEmail());
        return ResponseEntity.ok(resp);
    }
}
