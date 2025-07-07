package com.plazoleta.usuarios.infrastructure.endpoints.rest;

import com.plazoleta.usuarios.application.dto.request.SaveUserRequest;
import com.plazoleta.usuarios.application.dto.response.SaveUserResponse;
import com.plazoleta.usuarios.application.service.UserService;
import com.plazoleta.usuarios.domain.util.constants.DomainConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        SaveUserRequest requestWithRole = new SaveUserRequest(
                request.id(),
                request.name(),
                request.lastname(),
                request.document(),
                request.phoneNumber(),
                request.dateOfBirth(),
                request.email(),
                request.password(),
                DomainConstants.OWNER_ID
        );

        SaveUserResponse response = userService.save(requestWithRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/employee")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<SaveUserResponse> saveEmployee(@RequestBody SaveUserRequest request) {
        SaveUserRequest withEmployeeRole = new SaveUserRequest(
                request.id(),
                request.name(),
                request.lastname(),
                request.document(),
                request.phoneNumber(),
                request.dateOfBirth(),
                request.email(),
                request.password(),
                DomainConstants.EMPLOYEE_ID
        );
        SaveUserResponse response = userService.save(withEmployeeRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/client")
    public ResponseEntity<SaveUserResponse> saveClient(@RequestBody SaveUserRequest request) {
        SaveUserRequest withClientRole = new SaveUserRequest(
                request.id(),
                request.name(),
                request.lastname(),
                request.document(),
                request.phoneNumber(),
                request.dateOfBirth(),
                request.email(),
                request.password(),
                DomainConstants.CLIENT_ID
        );
        SaveUserResponse response = userService.save(withClientRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /*@PostMapping("/admin")
    public ResponseEntity<SaveUserResponse> saveAdmin(@RequestBody SaveUserRequest request) {
        SaveUserRequest withAdminRole = new SaveUserRequest(
                request.id(),
                request.name(),
                request.lastname(),
                request.document(),
                request.phoneNumber(),
                request.dateOfBirth(),
                request.email(),
                request.password(),
                DomainConstants.ADMIN_ID
        );
        SaveUserResponse response = userService.save(withAdminRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }*/
}
