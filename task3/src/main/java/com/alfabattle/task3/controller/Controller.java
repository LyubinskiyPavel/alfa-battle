package com.alfabattle.task3.controller;

import com.alfabattle.task3.domain.Branch;
import com.alfabattle.task3.domain.BranchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class Controller {

    private final BranchRepository branchRepository;

    public Controller(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @GetMapping(path = "/branches/{id}")
    public ResponseEntity<?> getBranch(@PathVariable("id") Integer id) {
        final Optional<Branch> branch = branchRepository.findById(id);
        if (branch.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FailedStatus("branch not found"));
        }

        return ResponseEntity.ok(branch.get());
    }
}
