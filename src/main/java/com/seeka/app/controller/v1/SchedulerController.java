package com.seeka.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.jobs.CurrencyConversionRateUtil;

@RestController("schedulerControllerV1")
@RequestMapping("/api/v1/scheduler")
public class SchedulerController {

	@Autowired
	private CurrencyConversionRateUtil currencyConversionRateUtil;

	@GetMapping("/run")
	public void runScheduler() {
		currencyConversionRateUtil.curencySchedulerMethod();
	}
}
