package com.alisonadamus.astronix.service;

import com.alisonadamus.astronix.model.*;
import com.alisonadamus.astronix.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAllByOrderByOrderIndexAsc();
    }

    public List<Task> getTasksByLocation(Long locationId) {
        return taskRepository.findByLocationIdOrderByOrderIndexAsc(locationId);
    }
    public List<Integer> getOccupiedIndices(Long locationId) {
        if (locationId == null) return Collections.emptyList();
        return taskRepository.findOccupiedIndicesByLocationId(locationId);
    }

    public List<Task> search(Long locationId, TaskType type, Difficulty diff) {
        if (type == null && diff == null) {
            return getTasksByLocation(locationId);
        }
        return taskRepository.findFiltered(locationId, type, diff);
    }

    public Task getById(Long id) {
        return taskRepository.findById(id).orElse(new Task());
    }

    @Transactional
    public void saveWithAnswers(Task task, HttpServletRequest request) {
        if (task.getId() != null) {
            Task existing = taskRepository.findById(task.getId()).orElseThrow();
            existing.getAnswers().clear();
            task.setAnswers(existing.getAnswers());
        }

        switch (task.getType()) {
            case SINGLE_CHOICE:
            case MULTIPLE_CHOICE:
                String[] choiceTexts = request.getParameterValues("choiceText");
                String[] choiceCorrects = request.getParameterValues("choiceCorrectHidden");
                if (choiceTexts != null) {
                    for (int i = 0; i < choiceTexts.length; i++) {
                        ChoiceAnswer answer = new ChoiceAnswer(choiceTexts[i], Boolean.parseBoolean(choiceCorrects[i]));
                        answer.setTask(task);
                        task.getAnswers().add(answer);
                    }
                }
                break;
            case MATCHING:
                String[] leftTexts = request.getParameterValues("leftText");
                String[] rightTexts = request.getParameterValues("rightText");
                if (leftTexts != null) {
                    for (int i = 0; i < leftTexts.length; i++) {
                        MatchingAnswer answer = new MatchingAnswer(leftTexts[i], rightTexts[i]);
                        answer.setTask(task);
                        task.getAnswers().add(answer);
                    }
                }
                break;
            case ORDER:
                String[] orderTexts = request.getParameterValues("orderText");
                String[] correctOrders = request.getParameterValues("correctOrder");
                if (orderTexts != null) {
                    for (int i = 0; i < orderTexts.length; i++) {
                        OrderAnswer answer = new OrderAnswer(orderTexts[i], Integer.parseInt(correctOrders[i]));
                        answer.setTask(task);
                        task.getAnswers().add(answer);
                    }
                }
                break;
            case TEXT:
                String textAnswerValue = request.getParameter("textAnswerValue");
                if (textAnswerValue != null && !textAnswerValue.isBlank()) {
                    TextAnswer answer = new TextAnswer(textAnswerValue);
                    answer.setTask(task);
                    task.getAnswers().add(answer);
                }
                break;
        }

        taskRepository.save(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}