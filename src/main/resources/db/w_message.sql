DROP TABLE w_message;
CREATE TABLE `w_message`
(
    `id`        varchar(30)  NOT NULL,
    `wxid`      varchar(30)  NOT NULL,
    `content`   varchar(500) NOT NULL,
    `room_id`   varchar(30)           DEFAULT NULL,
    `type`      int          NOT NULL,
    `nick`      varchar(50)  NOT NULL,
    `id1`       varchar(30)  NOT NULL,
    `id2`       varchar(30)  NOT NULL,
    `srv_id`    varchar(30)  NOT NULL,
    `time`      varchar(30)  NOT NULL,
    `is_at`     tinyint(1) NOT NULL,
    `update_st` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;;