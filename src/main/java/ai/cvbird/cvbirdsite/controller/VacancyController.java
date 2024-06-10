package ai.cvbird.cvbirdsite.controller;

import ai.cvbird.cvbirdsite.dto.Vacancy;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import ai.cvbird.cvbirdsite.service.TelegramService;
import ai.cvbird.cvbirdsite.service.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Operation(summary = "Get list of recommended vacancies by CVBirdUserId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacancies have been found")})
    @GetMapping(value = "/get/{cvBirdUserId}", params = { "page", "size" })
    public ResponseEntity<List<Vacancy>> getVacancy(@RequestParam("page") int page,
                                                       @RequestParam("size") int size,@PathVariable Long cvBirdUserId) {
        Page<Vacancy> pages = vacancyService.getVacancies(cvBirdUserId, page , size);
        return new ResponseEntity<>(pages.getContent(), HttpStatus.OK);
    }

    @Operation(summary = "Get list of recommended vacancies by CVBirdUserId by Telegram ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacancies have been found")})
    @GetMapping(value = "/get_by_telegram_id/{telegramId}", params = { "page", "size" })
    public ResponseEntity<List<Vacancy>> getCVByTelegramId(@RequestParam("page") int page,
                                                    @RequestParam("size") int size,@RequestBody String telegramId){
        CVBirdUser cvBirdUser = telegramService.getCVBirdUser(telegramId);
        if (cvBirdUser != null){
            Page<Vacancy> pages = vacancyService.getVacancies(cvBirdUser.getCvBirdUserId(), page , size);
            return new ResponseEntity<>(pages.getContent(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
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
