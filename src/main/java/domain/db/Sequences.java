/*
 * This file is generated by jOOQ.
 */
package domain.db;


import org.jooq.Sequence;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;


/**
 * Convenience access to all sequences in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.tasks_id_seq</code>
     */
    public static final Sequence<Long> TASKS_ID_SEQ = Internal.createSequence("tasks_id_seq", Public.PUBLIC, SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);

    /**
     * The sequence <code>public.tasks_properties_id_seq</code>
     */
    public static final Sequence<Long> TASKS_PROPERTIES_ID_SEQ = Internal.createSequence("tasks_properties_id_seq", Public.PUBLIC, SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);
}
