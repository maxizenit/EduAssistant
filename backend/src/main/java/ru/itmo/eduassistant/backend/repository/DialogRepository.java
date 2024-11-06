package ru.itmo.eduassistant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.Dialog;

public interface DialogRepository extends JpaRepository<Dialog, Long> {
}
