package com.arihant.expense_tracker.service;

import com.arihant.expense_tracker.dto.ExpenseRequestDto;
import com.arihant.expense_tracker.dto.ExpenseResponseDto;
import com.arihant.expense_tracker.dto.ExpenseUpdateRequestDto;
import com.arihant.expense_tracker.entity.Expense;
import com.arihant.expense_tracker.entity.User;
import com.arihant.expense_tracker.exception.ResourceNotFoundException;
import com.arihant.expense_tracker.repository.ExpenseRepository;
import com.arihant.expense_tracker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepo;
    private final UserRepository userRepo;

    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    public ExpenseService(ExpenseRepository expenseRepo,UserRepository userRepo) {
        this.expenseRepo = expenseRepo;
        this.userRepo = userRepo;
    }

    private User getAuthenticatedUser(){

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String username = authentication.getName();

        return userRepo.findByUsername(username)
                .orElseThrow();

    }

    public ExpenseResponseDto saveNewEntry(ExpenseRequestDto requestDto){

        Expense newExp = new Expense();

        newExp.setTitle(requestDto.getTitle());
        newExp.setCategory(requestDto.getCategory());
        newExp.setType(requestDto.getType());
        newExp.setAmount(requestDto.getAmount());
        newExp.setExpenseDate(requestDto.getExpenseDate());
        newExp.setRemark(requestDto.getRemark());

        newExp.setUser(getAuthenticatedUser());

        Expense expenseRes = expenseRepo.save(newExp);

        logger.info("New expense saved to the database");

        ExpenseResponseDto responseDto = new ExpenseResponseDto();

        responseDto.setId(expenseRes.getExpId());
        responseDto.setTitle(expenseRes.getTitle());
        responseDto.setCategory(expenseRes.getCategory());
        responseDto.setType(expenseRes.getType());
        responseDto.setAmount(expenseRes.getAmount());
        responseDto.setRemark(expenseRes.getRemark());
        responseDto.setEntryDateTime(expenseRes.getEntryDateTime());
        responseDto.setExpenseDate(expenseRes.getExpenseDate());

        return responseDto;
    }

    public List<ExpenseResponseDto> getAll(){

        logger.info("User with username = {} fetched their expenses",getAuthenticatedUser().getUsername());

        List<Expense> expenseResList = expenseRepo.findByUser(getAuthenticatedUser());
        List<ExpenseResponseDto> resDtoList = new ArrayList<>();

        for(Expense expense : expenseResList){

            // WRONG LOGIC :-
            // Convert Entity -> DTO.
            // No need to recreate Expense entity because JPA already returns populated entities.

            // Expense loopExpenseObject = new Expense();

            ExpenseResponseDto loopResDto = new ExpenseResponseDto();

            loopResDto.setId(expense.getExpId());
            loopResDto.setTitle(expense.getTitle());
            loopResDto.setCategory(expense.getCategory());
            loopResDto.setType(expense.getType());
            loopResDto.setAmount(expense.getAmount());
            loopResDto.setExpenseDate(expense.getExpenseDate());
            loopResDto.setEntryDateTime(expense.getEntryDateTime());
            loopResDto.setRemark(expense.getRemark());

            resDtoList.add(loopResDto);
        }

        return resDtoList;

    }

    public String deleteExpense(Long expId){
        User user = getAuthenticatedUser();

        Expense exp = expenseRepo.findByExpIdAndUser(expId,user).orElseThrow(() ->
                new ResourceNotFoundException("Expense not found"));

        expenseRepo.delete(exp);

        logger.warn("An expense has been deleted");

        return "Expense deleted";
    }

    public ExpenseResponseDto updateExpense(Long expId, ExpenseUpdateRequestDto requestDto){

        User user = getAuthenticatedUser();
        Expense expense = expenseRepo.findByExpIdAndUser(expId,user).orElseThrow(() -> {
            logger.warn("ResourceNotFoundException : Expense not found with expId={}",expId);
            return  new ResourceNotFoundException("Expense not found");
        });

        if(requestDto.getTitle() != null){
            expense.setTitle(requestDto.getTitle());
        }

        if(requestDto.getCategory() != null){
            expense.setCategory(requestDto.getCategory());
        }

        if(requestDto.getType() != null){
            expense.setType(requestDto.getType());
        }

        if(requestDto.getAmount() != null){
            expense.setAmount(requestDto.getAmount());
        }

        if(requestDto.getExpenseDate() != null){
            expense.setExpenseDate(requestDto.getExpenseDate());
        }

        if(requestDto.getRemark() != null){
            expense.setRemark(requestDto.getRemark());
        }

        Expense responseExpense= expenseRepo.save(expense);

        ExpenseResponseDto responseDto = new ExpenseResponseDto();

        responseDto.setId(responseExpense.getExpId());
        responseDto.setExpenseDate(responseExpense.getExpenseDate());
        responseDto.setAmount(responseExpense.getAmount());
        responseDto.setRemark(responseExpense.getRemark());
        responseDto.setType(responseExpense.getType());
        responseDto.setCategory(responseExpense.getCategory());
        responseDto.setTitle(responseExpense.getTitle());
        responseDto.setEntryDateTime(responseExpense.getEntryDateTime());

        return responseDto;
    }
}
