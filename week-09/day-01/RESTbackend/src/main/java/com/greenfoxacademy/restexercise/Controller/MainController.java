package com.greenfoxacademy.restexercise.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.restexercise.Model.*;
import com.greenfoxacademy.restexercise.Model.ArraysEndpoint.ArraySumandMultiple;
import com.greenfoxacademy.restexercise.Model.ArraysEndpoint.ArraysDouble;
import com.greenfoxacademy.restexercise.Model.ArraysEndpoint.ArraysRequestBody;
import com.greenfoxacademy.restexercise.Model.DoUntilEndPoint.DoUntilGet;
import com.greenfoxacademy.restexercise.Model.DoUntilEndPoint.DoUntilMultiple;
import com.greenfoxacademy.restexercise.Model.DoUntilEndPoint.DoUntilSum;
import com.greenfoxacademy.restexercise.Model.Error;
import com.greenfoxacademy.restexercise.Model.LogEndpoint.Log;
import com.greenfoxacademy.restexercise.Model.LogEndpoint.LogPages;
import com.greenfoxacademy.restexercise.Model.LogEndpoint.LogResponse;
import com.greenfoxacademy.restexercise.Model.SithEndPoint.Sith;
import com.greenfoxacademy.restexercise.Model.SithEndPoint.SithRequestBody;
import com.greenfoxacademy.restexercise.Model.TranslateEndPoint.TranslateRequestBody;
import com.greenfoxacademy.restexercise.Model.TranslateEndPoint.TranslateResponse;
import com.greenfoxacademy.restexercise.Service.LogServiceDbImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {
    private final LogServiceDbImpl logServiceDb;

    @Autowired
    @JsonIgnore
    public MainController(LogServiceDbImpl logServiceDb) {
        this.logServiceDb = logServiceDb;
    }

    @RequestMapping("/doubling")
    public ResponseEntity<RestResponse> doubling(@RequestParam(value = "input", required = false) Integer input) {
        String endpoint = "/doubling";
        String data;
        Log log = new Log();
        log.setEndPoint(endpoint);
        if(input != null) {
            data = "input=" + input;
            log.setData(data);
            logServiceDb.save(log);
            return new ResponseEntity<>(new Doubling(input), HttpStatus.OK);
        } else {
            data = "";
            log.setData(data);
            logServiceDb.save(log);
            return new ResponseEntity<>(new Error("Please provide an input!"), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/greeter")
    public ResponseEntity<RestResponse> greeting(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "title", required = false) String title) {
        String endpoint = "/greeter";
        String data;
        Log log = new Log();
        log.setEndPoint(endpoint);
        if(name == null && title == null) {
            data = "";
            log.setData("");
            logServiceDb.save(log);
            return new ResponseEntity<>(new Error("Please provide a name or a title!"), HttpStatus.BAD_REQUEST);
        } else {
            data = "name=" + name + "&title=" + title;
            log.setData(data);
            logServiceDb.save(log);
            return new ResponseEntity<>(new Greeter(name, title), HttpStatus.OK);
        }
    }

    @RequestMapping("/appends/{appendable}")
    public ResponseEntity<RestResponse> append(@PathVariable(value = "appendable") String appendable) {
        Log log = new Log("/appends/{appendable}", "appendable=" + appendable);
        logServiceDb.save(log);
        return new ResponseEntity<>(new Append(appendable), HttpStatus.OK);
    }

    @PostMapping("/dountil/{what}")
    public ResponseEntity<RestResponse> doUntil(@PathVariable(value = "what") String what, @RequestBody DoUntilGet doUntilGet) {
        int input = doUntilGet.getUntil();
        Object result;

        String endpoint = "/dountil/";

        switch (what) {
            case "sum":
                endpoint += "sum";
                saveLog(endpoint, doUntilGet);
                return new ResponseEntity<>(new DoUntilSum(input), HttpStatus.OK);
            case "multiple":
                endpoint += "multiple";
                saveLog(endpoint, doUntilGet);
                return new ResponseEntity<>(new DoUntilMultiple(input), HttpStatus.OK);
            default:
                endpoint += "";
                saveLog(endpoint, doUntilGet);
                return new ResponseEntity<>(new Error("Please provide an input!"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/arrays")
    public ResponseEntity<RestResponse> arrays(@RequestBody ArraysRequestBody arraysRequestBody) {
        String what = arraysRequestBody.getWhat();
        int[] numbers = arraysRequestBody.getNumbers();

        saveLog("/arrays", arraysRequestBody);

        switch (what) {
            case "double":
                return new ResponseEntity<>(new ArraysDouble(numbers), HttpStatus.OK);
            case "sum":
                return new ResponseEntity<>(new ArraySumandMultiple(what, numbers), HttpStatus.OK);
            case "multiple":
                return new ResponseEntity<>(new ArraySumandMultiple(what, numbers), HttpStatus.OK);
            default:
                return new ResponseEntity<>(new Error("Please provide what to do with the numbers!"), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/log")
    public ResponseEntity<RestResponse> getLog(@RequestParam(value = "count", required = false) Integer count,
                         @RequestParam(value = "page", required = false) Integer page,
                         @RequestParam(value = "q", required = false) String q) {
        if(count == null || page == null) {
            return new ResponseEntity<>(new LogResponse(logServiceDb), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new LogPages(logServiceDb, count, page), HttpStatus.OK);
        }
    }

    @PostMapping("/sith")
    public ResponseEntity<RestResponse> sithEndpoint(@RequestBody SithRequestBody sithRequestBody) {

        saveLog("/sith", sithRequestBody);

        if(sithRequestBody != null) {
            return new ResponseEntity<>(new Sith(sithRequestBody.getText()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Error("Feed me some text you have to, " +
                    "padawan young you are. Hmmm."), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/translate")
    public ResponseEntity<RestResponse> translate(@RequestBody TranslateRequestBody translateRequestBody) {

        saveLog("/translate", translateRequestBody);

        if(translateRequestBody.getText() == null || translateRequestBody.getLang() == null) {
            return new ResponseEntity<>(new Error("I can't translate that!"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new TranslateResponse(translateRequestBody.getText()), HttpStatus.OK);
        }
    }

    private void saveLog(String endpoint, Object requestBody) {
        ObjectMapper mapper = new ObjectMapper();
        String requestBodyInString;
        try {
            requestBodyInString = mapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            requestBodyInString = null;
            e.printStackTrace();
        }

        Log log = new Log(endpoint, requestBodyInString);
        logServiceDb.save(log);
    }

}
