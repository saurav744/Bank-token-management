package com.saurav.bankingapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="This token id is not valid")
public class TokenNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	@Override
    public String toString() {
        return "Token not valid";
    }

}
