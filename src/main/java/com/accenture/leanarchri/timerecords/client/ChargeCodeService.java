package com.accenture.leanarchri.timerecords.client;

import java.util.Collection;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.accenture.leanarchri.timerecords.client.vo.ChargeCode;


@FeignClient(name = "chargecodes")
@SpringBootApplication
public interface ChargeCodeService {
	
	@RequestMapping(value = "/chargecodes/{wbs}",  method = RequestMethod.GET)
	ChargeCode  getChargeCodeDetails(@PathVariable("wbs") String wbs);
	
	@RequestMapping(value = "/chargecodes/{wbs}/employees/{empid}",  method = RequestMethod.GET)
    ChargeCode  getChargeCodeDetailsOfAnEmployee(@PathVariable("wbs") String wbs,@PathVariable("empid") Integer empid);
	
	@RequestMapping(value = "/chargecodes/{chargecodes}", method = RequestMethod.GET)
	public Collection<ChargeCode>  getChargeCodes(@RequestHeader("correlationId") String correlationId,@PathVariable("chargecodes") String[] chargeCodes);

}
