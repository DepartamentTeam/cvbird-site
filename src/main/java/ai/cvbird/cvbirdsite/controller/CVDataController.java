package ai.cvbird.cvbirdsite.controller;

import ai.cvbird.cvbirdsite.dto.CVBirdUserDTO;
import ai.cvbird.cvbirdsite.dto.StringResponse;
import ai.cvbird.cvbirdsite.dto.TelegramRequestFile;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import ai.cvbird.cvbirdsite.model.CVData;
import ai.cvbird.cvbirdsite.service.CVDataService;
import ai.cvbird.cvbirdsite.service.TelegramService;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cv")
@CrossOrigin
public class CVDataController {

    private static final Gson gson = new Gson();

    @Autowired
    CVDataService cvDataService;

    @Autowired
    TelegramService telegramService;

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponse> getCV(@RequestBody CVBirdUserDTO cvBirdUserDTO){
        String file = cvDataService.getCVFile(cvBirdUserDTO);
        if (file != null) {
            return new ResponseEntity<>(new StringResponse(file), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
        }
    }

    @Operation(summary = "Get CV by Telegram ID. Return text in base64")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CV has been saved")})
    @GetMapping(value = "/get_by_telegram_id/{telegramId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponse> getCVByTelegramId(@PathVariable String telegramId){
        String file = cvDataService.getCVFile(telegramId);
        if (file != null) {
            return new ResponseEntity<>(new StringResponse(file), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new StringResponse("CV was not found"), HttpStatus.OK);
        }
    }

    @Operation(summary = "Save user CV by Telegram ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CV has been saved"),
            @ApiResponse(responseCode = "409", description = "CV already exists",
                    content = @Content) })
    @PostMapping(value = "/store_by_telegram_id", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponse> storeCVByTelegramId(@RequestBody TelegramRequestFile telegramRequestFile){
        if (telegramRequestFile.getFile() != null) {
            if (cvDataService.getCVData(telegramRequestFile.getTelegramId()) == null) {
                CVData cvData = cvDataService.setCVFile(telegramRequestFile.getTelegramId(), telegramRequestFile.getFile());
                return new ResponseEntity<>(new StringResponse("CV has been saved"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new StringResponse("User have already had cv"), HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(new StringResponse("File must be not null"), HttpStatus.CONFLICT);
    }

    @Operation(summary = "Delete CV by Telegram ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CV has been deleted")})
    @DeleteMapping(value = "/delete_by_telegram_id/{telegramId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<StringResponse> deleteCVByTelegramId(@PathVariable String telegramId){
        CVBirdUser cvBirdUser = telegramService.getCVBirdUser(telegramId);
        if (cvBirdUser != null) {
            if (cvDataService.deleteCVData(cvBirdUser)) {
                return new ResponseEntity<>(new StringResponse("CV has been deleted"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new StringResponse("CV was not found"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new StringResponse("There is no such user"), HttpStatus.OK);
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
