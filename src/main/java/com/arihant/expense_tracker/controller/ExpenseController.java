package com.arihant.expense_tracker.controller;

import com.arihant.expense_tracker.dto.ExpenseRequestDto;
import com.arihant.expense_tracker.dto.ExpenseResponseDto;
import com.arihant.expense_tracker.dto.ExpenseUpdateRequestDto;
import com.arihant.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/create-exp")
    public ResponseEntity<ExpenseResponseDto> saveNewEntry(@RequestBody @Valid ExpenseRequestDto expenseRequestDto){
        ExpenseResponseDto exp = expenseService.saveNewEntry(expenseRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(exp);
    }

    @GetMapping("/get-exp")
    public ResponseEntity<List<ExpenseResponseDto>>  getAll(){
        return new ResponseEntity<>(expenseService.getAll(),HttpStatus.OK);
    }

    @DeleteMapping("/delete-exp/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long expId){
        return new ResponseEntity<>(expenseService.deleteExpense(expId),HttpStatus.OK);
    }

    @PatchMapping("/update-exp/{id}")
    public ResponseEntity<ExpenseResponseDto> updateExpense(@PathVariable Long expId, @RequestBody ExpenseUpdateRequestDto requestDto){
        ExpenseResponseDto res = expenseService.updateExpense(expId,requestDto);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
