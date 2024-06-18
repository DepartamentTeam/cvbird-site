package ai.cvbird.cvbirdsite.controller;

import ai.cvbird.cvbirdsite.dto.StringResponse;
import ai.cvbird.cvbirdsite.exception.NotEnoughFundsException;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import ai.cvbird.cvbirdsite.service.BalanceService;
import ai.cvbird.cvbirdsite.service.TelegramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/balance")
@CrossOrigin
public class BalanceController {

    @Autowired
    TelegramService telegramService;

    @Autowired
    BalanceService balanceService;

    @Operation(summary = "Get balance by Telegram id")
    @GetMapping(value = "/get_by_telegram_id/{telegramId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponse> get(@PathVariable String telegramId){
        BigDecimal balance = balanceService.getByTelegramId(telegramId);
        CVBirdUser cvBirdUser = telegramService.getCVBirdUser(telegramId);
        if (cvBirdUser != null) {
            return new ResponseEntity<>(new StringResponse(balance.toString()), HttpStatus.OK);
        }
        StringResponse stringResponse = new StringResponse("There is no such user " + telegramId);
        return new ResponseEntity<>(stringResponse, HttpStatus.OK);
    }


    @Operation(summary = "Top Up user balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful")})
    @GetMapping(value = "/top_up/{telegramId}/{amount}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("@securityService.isAllowedAccessByCurrentUser(#resource?.userId)")
    public ResponseEntity<StringResponse> topUp(@PathVariable String telegramId, @PathVariable String amount){
        BigDecimal bigDecimalAmount = new BigDecimal(amount);
        CVBirdUser cvBirdUser = telegramService.getCVBirdUser(telegramId);
        if (cvBirdUser != null) {
            BigDecimal balance = balanceService.balanceTopUp(telegramId, bigDecimalAmount);
            return new ResponseEntity<>(new StringResponse(balance.toString()), HttpStatus.OK);
        }
        StringResponse stringResponse = new StringResponse("There is no such user " + telegramId);
        return new ResponseEntity<>(stringResponse, HttpStatus.OK);
    }

    @Operation(summary = "Debiting funds from user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful")})
    @GetMapping(value = "/charging/{telegramId}/{amount}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponse> charging(@PathVariable String telegramId, @PathVariable String amount){
        BigDecimal bigDecimalAmount = new BigDecimal(amount);
        try {
            CVBirdUser cvBirdUser = telegramService.getCVBirdUser(telegramId);
            if (cvBirdUser != null) {
                BigDecimal balance = balanceService.balanceCharging(telegramId, bigDecimalAmount);
                return new ResponseEntity<>(new StringResponse(balance.toString()), HttpStatus.OK);
            }
            StringResponse stringResponse = new StringResponse("There is no such user " + telegramId);
            return new ResponseEntity<>(stringResponse, HttpStatus.OK);
        } catch (NotEnoughFundsException e) {
            return new ResponseEntity<>(new StringResponse(e.getMessage()), HttpStatus.OK);
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
