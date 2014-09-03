// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.db.oom.sqlgen.chunks;

import jodd.db.oom.DbEntityDescriptor;
import jodd.db.oom.DbEntityColumnDescriptor;
import jodd.bean.BeanUtil;
import jodd.db.oom.DbOomUtil;
import jodd.util.StringUtil;

/**
 * Generates the SET part of the UPDATE statement.
 * It may contains only non-<code>null</code> values, or all.
 */
public class UpdateSetChunk extends SqlChunk {

	private static final String SET = "set ";
	
	protected final Object data;
	protected final String tableRef;
	protected final int includeColumns;

	public UpdateSetChunk(String tableRef, Object data, int includeColumns) {
		super(CHUNK_UPDATE);
		this.tableRef = tableRef;
		this.data = data;
		this.includeColumns = includeColumns;
	}

	@Override
	public void process(StringBuilder out) {
		if (isPreviousChunkOfType(CHUNK_TABLE)) {
			appendMissingSpace(out);
		}

		DbEntityDescriptor ded = tableRef != null ?
				lookupTableRef(tableRef) :
				lookupType(resolveClass(data));

		out.append(SET);

		DbEntityColumnDescriptor[] decList = ded.getColumnDescriptors();
		String typeName = StringUtil.uncapitalize(ded.getEntityName());
		//String table = resolveTable(tableRef, ded);

		int size = 0;
		for (DbEntityColumnDescriptor dec : decList) {
			String property = dec.getPropertyName();
			Object value = BeanUtil.getDeclaredProperty(data, property);

			if (includeColumns == COLS_ONLY_EXISTING) {
				if (DbOomUtil.isEmptyColumnValue(dec, value)) {
					continue;
				}
			}

			if (size > 0) {
				out.append(',').append(' ');
			}

			size++;


			// do not add table reference in set
			// as only one table can be updated
			// also, Postgress database does not allow it (see #JODD-21)

			//out.append(table).append('.');

			out.append(dec.getColumnName()).append('=');

			String propertyName = typeName + '.' + property;
			defineParameter(out, propertyName, value, dec);
		}
		if (size > 0) {
			out.append(' ');
		}
	}

}