package ai.cvbird.cvbirdsite.controller;

import ai.cvbird.cvbirdsite.dto.StringResponse;
import ai.cvbird.cvbirdsite.dto.Vacancy;
import ai.cvbird.cvbirdsite.exception.NotEnoughFundsException;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import ai.cvbird.cvbirdsite.service.CVDataService;
import ai.cvbird.cvbirdsite.service.TelegramService;
import ai.cvbird.cvbirdsite.service.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vacancy")
@CrossOrigin
public class VacancyController {

    @Autowired
    VacancyService vacancyService;

    @Autowired
    TelegramService telegramService;

    @Autowired
    CVDataService cvDataService;

    @Operation(summary = "Get list of recommended vacancies by CVBirdUserId by Telegram ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacancies have been found",content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Vacancy.class))) }),
            @ApiResponse(responseCode = "422", description = "There are not enough funds on your balance")})
    @GetMapping(value = "/get_by_telegram_id/{telegramId}")
    public ResponseEntity<?> getCVByTelegramId(@PathVariable String telegramId){
        CVBirdUser cvBirdUser = telegramService.getCVBirdUser(telegramId);
        if (cvBirdUser != null){
            if (cvDataService.getCVData(telegramId) != null){
                try {
                    List<Vacancy> list = vacancyService.getVacancies(cvBirdUser.getCvBirdUserId());
                    return new ResponseEntity<>(list, HttpStatus.OK);
                } catch (NotEnoughFundsException e) {
                    StringResponse stringResponse = new StringResponse("There are not enough funds on your balance. Balance 0. User : " + telegramId);
                    return new ResponseEntity<>(stringResponse, HttpStatus.UNPROCESSABLE_ENTITY);
                }
            }
            StringResponse stringResponse = new StringResponse("User " + telegramId +  " has not cv");
            return new ResponseEntity<>(stringResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        StringResponse stringResponse = new StringResponse("There is no such user" + telegramId);
        return new ResponseEntity<>(stringResponse, HttpStatus.OK);
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
