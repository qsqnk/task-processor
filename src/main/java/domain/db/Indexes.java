/*
 * This file is generated by jOOQ.
 */
package domain.db;


import domain.db.tables.FlywaySchemaHistory;
import domain.db.tables.Tasks;
import domain.db.tables.TasksProperties;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index FLYWAY_SCHEMA_HISTORY_S_IDX = Internal.createIndex(DSL.name("flyway_schema_history_s_idx"), FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, new OrderField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.SUCCESS }, false);
    public static final Index IDX__TASKS__STATUS__SCHEDULED_TS = Internal.createIndex(DSL.name("idx__tasks__status__scheduled_ts"), Tasks.TASKS, new OrderField[] { Tasks.TASKS.STATUS, Tasks.TASKS.SCHEDULED_TS }, false);
    public static final Index IDX__TASKS_PROPERTIES__TASK_ID__FIELD_NAME = Internal.createIndex(DSL.name("idx__tasks_properties__task_id__field_name"), TasksProperties.TASKS_PROPERTIES, new OrderField[] { TasksProperties.TASKS_PROPERTIES.TASK_ID, TasksProperties.TASKS_PROPERTIES.FIELD_NAME }, false);
}
