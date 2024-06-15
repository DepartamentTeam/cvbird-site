package ai.cvbird.cvbirdsite.controller;

import ai.cvbird.cvbirdsite.dto.StringResponse;
import ai.cvbird.cvbirdsite.exception.NotEnoughFundsException;
import ai.cvbird.cvbirdsite.service.BalanceService;
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
    BalanceService balanceService;

    @Operation(summary = "Get balance by Telegram id")
    @GetMapping(value = "/get_by_telegram_id/{telegramId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponse> get(@PathVariable String telegramId){
        BigDecimal balance = balanceService.getByTelegramId(telegramId);
        return new ResponseEntity<>(new StringResponse(balance.toString()), HttpStatus.ALREADY_REPORTED);
    }


    @Operation(summary = "Top Up user balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful")})
    @GetMapping(value = "/top_up/{telegramId}/{amount}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponse> topUp(@PathVariable String telegramId, @PathVariable String amount){
        BigDecimal bigDecimalAmount = new BigDecimal(amount);
        BigDecimal balance = balanceService.balanceTopUp(telegramId, bigDecimalAmount);
        return new ResponseEntity<>(new StringResponse(balance.toString()), HttpStatus.OK);
    }

    @Operation(summary = "Debiting funds from user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful")})
    @GetMapping(value = "/charging/{telegramId}/{amount}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponse> charging(@PathVariable String telegramId, @PathVariable String amount){
        BigDecimal bigDecimalAmount = new BigDecimal(amount);
        try {
            BigDecimal balance = balanceService.balanceCharging(telegramId, bigDecimalAmount);
            return new ResponseEntity<>(new StringResponse(balance.toString()), HttpStatus.OK);
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