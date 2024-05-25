package ai.cvbird.cvbirdsite.controller;


import ai.cvbird.cvbirdsite.dto.TelegramStatisticConverter;
import ai.cvbird.cvbirdsite.dto.TelegramStatisticDTO;
import ai.cvbird.cvbirdsite.dto.TelegramUserConverter;
import ai.cvbird.cvbirdsite.dto.TelegramUserDTO;
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
    public ResponseEntity<TelegramStatistic> getTelegramUserStatistic(@RequestBody TelegramStatistic request){
        TelegramStatistic telegramStatistic = telegramService.getUserStatistic(request.getTelegramId());
        if (telegramStatistic != null) {
            return new ResponseEntity<>(telegramStatistic, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(value = "/statistic/save", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveStatistic(@RequestBody @Valid TelegramStatisticDTO telegramStatisticDTO){
        TelegramStatistic telegramStatistic = telegramService.saveTelegramStatistic(telegramStatisticDTO);
        if (telegramStatistic != null) {
            return new ResponseEntity<>("The statistic has been successfully saved", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("The statistic does include telegramId", HttpStatus.ALREADY_REPORTED);
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
