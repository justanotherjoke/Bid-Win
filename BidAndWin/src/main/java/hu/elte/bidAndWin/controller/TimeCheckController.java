package hu.elte.bidAndWin.controller;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/systime")
public class TimeCheckController {
	
    @GetMapping(value = "/get")
    public Date getTime() {
    	Date date = new Date();
    	Timestamp currentTime = new Timestamp(date.getTime());
		return currentTime;
    }
}
