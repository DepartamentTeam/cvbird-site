package ai.cvbird.cvbirdsite.controller;


import ai.cvbird.cvbirdsite.dto.*;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import ai.cvbird.cvbirdsite.service.TelegramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/telegram")
@CrossOrigin(origins = "http://localhost:9000")
public class TelegramController {

    @Autowired
    TelegramService telegramService;

    @Autowired
    CVBirdUserConverter cvBirdUserConverter;

    @Operation(summary = "Get CVBirdUser by Telegram ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been found",content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CVBirdUserResponse.class)) })})
    @GetMapping(value = "/get_cvbird_user/{telegramId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCVBirdUser(@Parameter(description = "Telegram ID") @PathVariable String telegramId){
        CVBirdUser cvBirdUser = telegramService.getCVBirdUser(telegramId);
        if (cvBirdUser != null) {
            CVBirdUserResponse cvBirdUserResponse = cvBirdUserConverter.toCVBirdUserResponse(cvBirdUser);
            if (cvBirdUserResponse != null) {
                return new ResponseEntity<>(cvBirdUserResponse, HttpStatus.OK);
            }
        }
        StringResponse stringResponse = new StringResponse("The there is no such user");
        return new ResponseEntity<>(stringResponse, HttpStatus.OK);
    }

    @Operation(summary = "Save user from Telegram. Create new CVBirdUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CVBirdUserResponse.class)) }),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = @Content) })
    @PostMapping(value = "/unknown_user/save", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUnknownUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Don't fill the ID and registration date") @RequestBody TelegramStatisticDTO telegramStatisticDTO){
        if (telegramService.getCVBirdUser(telegramStatisticDTO.getTelegramId()) == null) {
            CVBirdUser cvBirdUser = telegramService.saveUnknownUser(telegramStatisticDTO);
            if (cvBirdUser != null) {
                CVBirdUserResponse cvBirdUserResponse = cvBirdUserConverter.toCVBirdUserResponse(cvBirdUser);
                return new ResponseEntity<>(cvBirdUserResponse, HttpStatus.CREATED);
            } else {
                StringResponse stringResponse = new StringResponse("User already exists");
                return new ResponseEntity<>(stringResponse, HttpStatus.CONFLICT);
            }
        } else {
            StringResponse stringResponse = new StringResponse("User already exists");
            return new ResponseEntity<>(stringResponse, HttpStatus.CONFLICT);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
