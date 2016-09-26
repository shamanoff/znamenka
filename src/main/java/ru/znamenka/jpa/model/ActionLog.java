package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Entity(name = "jf_user_action_log")
@Getter @Setter
public class ActionLog implements BaseModel<Long>, LogEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "currenttime")
    private LocalDateTime currentTime;

    @Column(name = "username")
    private String username;

    @Column(name = "action")
    private String action;

    @Column(name = "success")
    private Boolean success;

    @Column(name = "message")
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
