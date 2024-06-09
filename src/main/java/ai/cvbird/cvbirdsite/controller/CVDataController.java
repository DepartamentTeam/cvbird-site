package ai.cvbird.cvbirdsite.controller;

import ai.cvbird.cvbirdsite.dto.CVBirdUserDTO;
import ai.cvbird.cvbirdsite.dto.TelegramRequestFile;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import ai.cvbird.cvbirdsite.model.CVData;
import ai.cvbird.cvbirdsite.service.CVDataService;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/cv")
@CrossOrigin
public class CVDataController {
    @Autowired
    CVDataService cvDataService;

    @GetMapping(value = "/get")
    public ResponseEntity<String> getCV(@RequestBody CVBirdUserDTO cvBirdUserDTO){
        String file = cvDataService.getCVFile(cvBirdUserDTO);
        if (file != null) {
            return new ResponseEntity<>(file, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
        }
    }

    @Operation(summary = "Get CV by Telegram ID. Return text in base64")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CV has been saved")})
    @GetMapping(value = "/get_by_telegram_id/{telegramId}")
    public ResponseEntity<String> getCVByTelegramId(@PathVariable String telegramId){
        String file = cvDataService.getCVFile(telegramId);
        if (file != null) {
            return new ResponseEntity<>(file, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("CV was not found", HttpStatus.OK);
        }
    }

    @Operation(summary = "Save user CV by Telegram ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CV has been saved"),
            @ApiResponse(responseCode = "409", description = "CV already exists",
                    content = @Content) })
    @PostMapping(value = "/store_by_telegram_id", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> storeCVByTelegramId(@RequestBody TelegramRequestFile telegramRequestFile){
        if (cvDataService.getCVFile(telegramRequestFile.getTelegramId()) == null) {
            CVData cvData = cvDataService.setCVFile(telegramRequestFile.getTelegramId(), telegramRequestFile.getFile());
            return new ResponseEntity<>("CV has been saved", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User have already had cv", HttpStatus.CONFLICT);
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
