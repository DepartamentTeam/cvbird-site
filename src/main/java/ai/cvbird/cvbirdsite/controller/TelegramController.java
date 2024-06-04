package ai.cvbird.cvbirdsite.controller;


import ai.cvbird.cvbirdsite.dto.*;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import ai.cvbird.cvbirdsite.model.TelegramStatistic;
import ai.cvbird.cvbirdsite.model.TelegramUser;
import ai.cvbird.cvbirdsite.service.TelegramService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/telegram")
@CrossOrigin(origins = "http://localhost:9000")
public class TelegramController {

    @Autowired
    TelegramService telegramService;

    @Autowired
    TelegramUserConverter telegramUserConverter;

    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveTelegramUser(@RequestBody @Valid TelegramUserDTO telegramUserDTO){
        TelegramUser telegramUser = telegramService.registerTelegramUser(telegramUserDTO);
        if (telegramUser != null) {
            return new ResponseEntity<>("The user has been successfully registered", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("The email already exists", HttpStatus.ALREADY_REPORTED);
        }
    }

    @GetMapping(value = "/get_user", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TelegramUserDTO> getTelegramUser(@RequestBody TelegramUserDTO telegramUserDTO){
        TelegramUser telegramUser = telegramService.getTelegramUserById(telegramUserDTO.getTelegramId());
        if (telegramUser != null) {
            telegramUser.getCvbirdUser().setPassword(null);
            return new ResponseEntity<>(telegramUserConverter.toDTO(telegramUser), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(value = "/get_user_statistic", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CVBirdUser> getTelegramUserStatistic(@RequestBody CVBirdUser cvBirdUser){
        CVBirdUser response = telegramService.getCVBirdUser(cvBirdUser.getTelegramId());
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(value = "/unknown_user/save", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveStatistic(@RequestBody CVBirdUserDTO cvBirdUserDTO){
        CVBirdUser cvBirdUser = telegramService.saveUnknownUser(cvBirdUserDTO);
        if (cvBirdUser != null) {
            return new ResponseEntity<>("The user has been successfully saved", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("The user does not include telegramId", HttpStatus.ALREADY_REPORTED);
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
