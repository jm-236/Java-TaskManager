package edu.taskmanager.taskmanager.specification;

import edu.taskmanager.taskmanager.domain.task.Task;
import edu.taskmanager.taskmanager.domain.user.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecifications {
    public static Specification<Task> filterTasks(User user, String title, String status, String categoria, String description) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro obrigatório: sempre deve ser do usuário logado
            predicates.add(cb.equal(root.get("user"), user));

            // Filtros opcionais
            if (title != null && !title.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            if (status != null && !status.isEmpty()) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (categoria != null && !categoria.isEmpty()) {
                predicates.add(cb.equal(root.get("category"), categoria));
            }

            if (description != null && !description.isEmpty()) {
                predicates.add(cb.equal(root.get("description"), description));
            }

            // or para cumprir pelo menos um dos predicados, and para cumprir todos
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}