package com.alisonadamus.astronix.service;

import com.alisonadamus.astronix.dto.TaskResultDto;
import com.alisonadamus.astronix.dto.TaskSubmitDto;
import com.alisonadamus.astronix.model.*;
import com.alisonadamus.astronix.repository.ResultRepository;
import com.alisonadamus.astronix.repository.TaskRepository;
import com.alisonadamus.astronix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskEvaluationService {

    private final TaskRepository taskRepository;
    private final ResultRepository resultRepository;
    private final UserRepository userRepository;

    @Transactional
    public TaskResultDto evaluateTask(Long taskId, TaskSubmitDto dto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Юзера не знайдено"));

        Task task = taskRepository.findById(taskId).orElseThrow();

        Result result = resultRepository.findByUserAndTask(user, task)
                .orElseGet(() -> {
                    Result newResult = new Result();
                    newResult.setUser(user);
                    newResult.setTask(task);
                    newResult.setAttempts(0);
                    newResult.setCompleted(false);
                    return newResult;
                });

        result.setAttempts(result.getAttempts() + 1);
        result.setUpdatedAt(LocalDateTime.now());

        boolean isCorrect = checkAnswer(task, dto);

        if (isCorrect && !result.isCompleted()) {
            result.setCompleted(true);
            result.setCompletedAt(LocalDateTime.now());
            result.setSuccessfulAttempt(result.getAttempts());
        }

        resultRepository.saveAndFlush(result);

        return new TaskResultDto(isCorrect,
                isCorrect ? "Правильно!" : "Спробуй ще",
                isCorrect ? task.getExplanation() : null,
                result.getAttempts());
    }

    private boolean checkAnswer(Task task, TaskSubmitDto dto) {
        switch (task.getType()) {
            case SINGLE_CHOICE:
            case MULTIPLE_CHOICE:
                if (dto.getAnswerIds() == null) return false;

                List<Long> correctIds = task.getAnswers().stream()
                        .filter(a -> a instanceof ChoiceAnswer && ((ChoiceAnswer) a).isCorrect())
                        .map(Answer::getId)
                        .toList();

                return correctIds.size() == dto.getAnswerIds().size() &&
                        new HashSet<>(correctIds).containsAll(dto.getAnswerIds());

            case TEXT:
                if (dto.getTextAnswer() == null || dto.getTextAnswer().trim().isEmpty()) return false;
                TextAnswer textAnswer = (TextAnswer) task.getAnswers().getFirst();
                return textAnswer.getCorrectAnswer().trim().equalsIgnoreCase(dto.getTextAnswer().trim());

            case ORDER:
                List<Long> submittedIds = dto.getAnswerIds();
                List<Answer> dbAnswers = task.getAnswers();

                if (submittedIds == null || submittedIds.size() != dbAnswers.size()) {
                    return false;
                }

                for (int i = 0; i < submittedIds.size(); i++) {
                    Long submittedId = submittedIds.get(i);
                    int expectedOrder = i + 1;

                    Optional<OrderAnswer> foundAnswer = dbAnswers.stream()
                            .filter(a -> a.getId().equals(submittedId))
                            .filter(a -> a instanceof OrderAnswer)
                            .map(a -> (OrderAnswer) a)
                            .findFirst();

                    if (foundAnswer.isEmpty()) return false;

                    if (foundAnswer.get().getCorrectOrder() != expectedOrder) {
                        return false;
                    }
                }
                return true;

            case MATCHING:
                if (dto.getMatchingAnswers() == null) return false;
                for (Answer a : task.getAnswers()) {
                    MatchingAnswer ma = (MatchingAnswer) a;
                    String submittedRightText = dto.getMatchingAnswers().get(ma.getId());
                    if (submittedRightText == null || !submittedRightText.equals(ma.getRightText())) {
                        return false;
                    }
                }
                return true;

            default:
                return false;
        }
    }
}