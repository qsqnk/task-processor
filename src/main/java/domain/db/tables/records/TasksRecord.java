/*
 * This file is generated by jOOQ.
 */
package domain.db.tables.records;


import domain.db.enums.Taskstatusenum;
import domain.db.tables.Tasks;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TasksRecord extends UpdatableRecordImpl<TasksRecord> implements Record6<Long, String, Taskstatusenum, LocalDateTime, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.tasks.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.tasks.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.tasks.runner_name</code>.
     */
    public void setRunnerName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.tasks.runner_name</code>.
     */
    public String getRunnerName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.tasks.status</code>.
     */
    public void setStatus(Taskstatusenum value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.tasks.status</code>.
     */
    public Taskstatusenum getStatus() {
        return (Taskstatusenum) get(2);
    }

    /**
     * Setter for <code>public.tasks.scheduled_ts</code>.
     */
    public void setScheduledTs(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.tasks.scheduled_ts</code>.
     */
    public LocalDateTime getScheduledTs() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>public.tasks.created_ts</code>.
     */
    public void setCreatedTs(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.tasks.created_ts</code>.
     */
    public LocalDateTime getCreatedTs() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>public.tasks.updated_ts</code>.
     */
    public void setUpdatedTs(LocalDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.tasks.updated_ts</code>.
     */
    public LocalDateTime getUpdatedTs() {
        return (LocalDateTime) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, String, Taskstatusenum, LocalDateTime, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Long, String, Taskstatusenum, LocalDateTime, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Tasks.TASKS.ID;
    }

    @Override
    public Field<String> field2() {
        return Tasks.TASKS.RUNNER_NAME;
    }

    @Override
    public Field<Taskstatusenum> field3() {
        return Tasks.TASKS.STATUS;
    }

    @Override
    public Field<LocalDateTime> field4() {
        return Tasks.TASKS.SCHEDULED_TS;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return Tasks.TASKS.CREATED_TS;
    }

    @Override
    public Field<LocalDateTime> field6() {
        return Tasks.TASKS.UPDATED_TS;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getRunnerName();
    }

    @Override
    public Taskstatusenum component3() {
        return getStatus();
    }

    @Override
    public LocalDateTime component4() {
        return getScheduledTs();
    }

    @Override
    public LocalDateTime component5() {
        return getCreatedTs();
    }

    @Override
    public LocalDateTime component6() {
        return getUpdatedTs();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getRunnerName();
    }

    @Override
    public Taskstatusenum value3() {
        return getStatus();
    }

    @Override
    public LocalDateTime value4() {
        return getScheduledTs();
    }

    @Override
    public LocalDateTime value5() {
        return getCreatedTs();
    }

    @Override
    public LocalDateTime value6() {
        return getUpdatedTs();
    }

    @Override
    public TasksRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public TasksRecord value2(String value) {
        setRunnerName(value);
        return this;
    }

    @Override
    public TasksRecord value3(Taskstatusenum value) {
        setStatus(value);
        return this;
    }

    @Override
    public TasksRecord value4(LocalDateTime value) {
        setScheduledTs(value);
        return this;
    }

    @Override
    public TasksRecord value5(LocalDateTime value) {
        setCreatedTs(value);
        return this;
    }

    @Override
    public TasksRecord value6(LocalDateTime value) {
        setUpdatedTs(value);
        return this;
    }

    @Override
    public TasksRecord values(Long value1, String value2, Taskstatusenum value3, LocalDateTime value4, LocalDateTime value5, LocalDateTime value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TasksRecord
     */
    public TasksRecord() {
        super(Tasks.TASKS);
    }

    /**
     * Create a detached, initialised TasksRecord
     */
    public TasksRecord(Long id, String runnerName, Taskstatusenum status, LocalDateTime scheduledTs, LocalDateTime createdTs, LocalDateTime updatedTs) {
        super(Tasks.TASKS);

        setId(id);
        setRunnerName(runnerName);
        setStatus(status);
        setScheduledTs(scheduledTs);
        setCreatedTs(createdTs);
        setUpdatedTs(updatedTs);
    }
}
