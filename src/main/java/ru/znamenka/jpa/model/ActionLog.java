package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Entity(name = "jf_user_action_log")
@Getter @Setter
public class ActionLog implements BaseModel<Long>, LogEntity {

    private Long id;

    private LocalDateTime currentTime;

    private String username;

    private String action;

    private Boolean success;

    private String message;

    public static ActionLog of(User user, String action, boolean success, String message) {
        ActionLog actionLog = new ActionLog();
        actionLog.setAction(action);
        actionLog.setUsername(user == null ? "anonymous" : user.getUsername());
        actionLog.setSuccess(success);
        actionLog.setMessage(message);
        actionLog.setCurrentTime(now());

        return actionLog;
    }
}
