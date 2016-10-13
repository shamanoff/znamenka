package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.now;

@Entity(name = "user_action_log")
@Getter @Setter
public class ActionLog implements BaseModel<Long>, LogEntity {

    @Id
    @SequenceGenerator(name="user_action_log_id_seq",
            sequenceName="user_action_log_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="user_action_log_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "currenttime")
    private Timestamp currentTime;

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
        actionLog.setCurrentTime(valueOf(now()));

        return actionLog;
    }
}
