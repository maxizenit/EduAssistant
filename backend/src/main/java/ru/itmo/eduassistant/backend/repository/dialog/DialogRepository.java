package ru.itmo.eduassistant.backend.repository.dialog;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.dialog.Dialog;

public interface DialogRepository extends JpaRepository<Dialog, Long> {
}
