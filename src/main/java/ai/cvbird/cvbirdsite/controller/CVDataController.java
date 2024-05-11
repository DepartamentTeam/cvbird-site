package ai.cvbird.cvbirdsite.controller;

import ai.cvbird.cvbirdsite.dto.TelegramRequestFile;
import ai.cvbird.cvbirdsite.dto.TelegramUserDTO;
import ai.cvbird.cvbirdsite.model.CVData;
import ai.cvbird.cvbirdsite.service.CVDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cv")
@CrossOrigin
public class CVDataController {
    @Autowired
    CVDataService cvDataService;

    @GetMapping(value = "/get")
    public ResponseEntity<String> saveTelegramUser(@RequestBody @Valid TelegramUserDTO telegramUserDTO){
        byte[] file = cvDataService.getCVFile(telegramUserDTO);
        String stringFile = new String(file);
        if (file != null) {
            return new ResponseEntity<>(stringFile, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
        }
    }

    @PostMapping(value = "/store", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTelegramUser(@RequestBody TelegramRequestFile telegramRequestFile){
        if (cvDataService.getCVFile(telegramRequestFile.getTelegramId()) == null) {
            byte[] bytes = telegramRequestFile.getFile().getBytes();
            CVData cvData = cvDataService.setCVFile(telegramRequestFile.getTelegramId(), bytes);
            if (cvData != null) {
                return new ResponseEntity<>("success", HttpStatus.OK);
            }
            return new ResponseEntity<>("exception", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User have already had cv", HttpStatus.OK);
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
