package com.springwithaarafa.accounts.controllers;



import com.springwithaarafa.accounts.constants.AccountsConstants;
import com.springwithaarafa.accounts.dto.AccountsContactInfoDto;
import com.springwithaarafa.accounts.dto.CustomerDto;
import com.springwithaarafa.accounts.dto.ErrorResponseDto;
import com.springwithaarafa.accounts.dto.ResponseDto;
import com.springwithaarafa.accounts.services.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Accounts microservice",
        description = "CRUD REST APIs  to CREATE, UPDATE, FETCH AND DELETE account details"
)
@RestController
@RequestMapping(path="/api", produces= {MediaType.APPLICATION_JSON_VALUE})
//@AllArgsConstructor
@Validated
public class AccountsContorller {

   private IAccountService iAccountService;

    public AccountsContorller( IAccountService iAccountService) {
        this.iAccountService = iAccountService;
    }

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

   @Value("${build.version}")
   private String buildVersion;

   @Operation(
           summary = "Create Account REST API",
           description = "REST API to create new Customer &  Account"
   )
   @ApiResponses({
           @ApiResponse(
                   responseCode = "201",
                   description = "HTTP Status CREATED"
           ),
           @ApiResponse(
                   responseCode = "500",
                   description = "HTTP Status Internal Server Error",
                   content = @Content(
                           schema = @Schema(implementation = ErrorResponseDto.class)
                   )
           )
   }
   )
   @PostMapping("/create")
   public ResponseEntity<ResponseDto> create(@Valid @RequestBody CustomerDto customerDto) {

      iAccountService.createAccount(customerDto);
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
   }

   @Operation(
           summary = "Fetch Account Details REST API",
           description = "REST API to fetch Customer &  Account details based on a mobile number"
   )
   @ApiResponses({
           @ApiResponse(
                   responseCode = "200",
                   description = "HTTP Status OK"
           ),
           @ApiResponse(
                   responseCode = "500",
                   description = "HTTP Status Internal Server Error",
                   content = @Content(
                           schema = @Schema(implementation = ErrorResponseDto.class)
                   )
           )
   })
   @GetMapping("/fetch")
   public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                             @Pattern(regexp = "[0-9]{10}", message = "Mobile number must be 10 digits")
                                                             String mobileNumber) {
      CustomerDto customerDto = iAccountService.fetchAccountDetails(mobileNumber);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(customerDto);
   }

    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer &  Account details based on a account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
   @PutMapping("/update")
   public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
      boolean isUpdated = iAccountService.updateAccount(customerDto);
      if (isUpdated) {
         return ResponseEntity
                 .status(HttpStatus.OK)
                 .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
      } else {
         return ResponseEntity
                 .status(HttpStatus.EXPECTATION_FAILED)
                 .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
      }
   }

    @Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to delete Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
   @DeleteMapping("/delete")
   public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                           @Pattern(regexp = "[0-9]{10}", message = "Mobile number must be 10 digits")
                                                           String mobileNumber) {
      boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
      if(isDeleted) {
         return ResponseEntity
                 .status(HttpStatus.OK)
                 .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
      } else{
         return ResponseEntity
                 .status(HttpStatus.EXPECTATION_FAILED)
                 .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
      }
   }

    @Operation(
            summary = "Get Build information",
            description = "Get Build information that is deployed into accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
   @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInf() {
       return ResponseEntity
               .status(HttpStatus.OK)
                  .body(buildVersion);
   }


    @GetMapping("/java-version")
   public ResponseEntity<String> fetchJavaVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));

    }



    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }


}
