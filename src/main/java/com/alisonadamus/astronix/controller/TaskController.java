package com.alisonadamus.astronix.controller;

import com.alisonadamus.astronix.dto.TaskResultDto;
import com.alisonadamus.astronix.dto.TaskSubmitDto;
import com.alisonadamus.astronix.model.*;
import com.alisonadamus.astronix.repository.UserRepository;
import com.alisonadamus.astronix.service.ResultService;
import com.alisonadamus.astronix.service.TaskEvaluationService;
import com.alisonadamus.astronix.service.TaskService;
import com.alisonadamus.astronix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskEvaluationService evaluationService;
    private final ResultService resultService;
    private final UserService userService;

    @GetMapping("/{id}")
    public String showTask(@PathVariable Long id, Model model, Principal principal) {
        Task task = taskService.getById(id);

        User user = userService.findByEmail(principal.getName());
        Result result = resultService.getResult(user, task).orElse(null);

        List<?> displayAnswers = task.getAnswers();
        if (task.getType() == TaskType.ORDER) {
            Collections.shuffle(displayAnswers);
        } else if (task.getType() == TaskType.MATCHING) {
            List<String> rightOptions = task.getAnswers().stream()
                    .map(a -> ((MatchingAnswer) a).getRightText())
                    .collect(Collectors.toList());
            Collections.shuffle(rightOptions);
            model.addAttribute("rightOptions", rightOptions);
        }

        model.addAttribute("task", task);
        model.addAttribute("displayAnswers", displayAnswers);
        model.addAttribute("result", result);

        return "task-execution";
    }

    @PostMapping("/{id}/submit")
    @ResponseBody
    public ResponseEntity<TaskResultDto> submitTask(
            @PathVariable Long id,
            @RequestBody TaskSubmitDto submitDto,
            Principal principal) {

        TaskResultDto result = evaluationService.evaluateTask(id, submitDto, principal.getName());
        System.out.println(principal.getName());
        return ResponseEntity.ok(result);
    }
}