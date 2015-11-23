package com.innovation.logicalview;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;

public class DataTablesResponse<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3849915961392556115L;

	/**
	 * The draw counter that this object is a response to - from the draw
	 * parameter sent as part of the data request. Note that it is strongly
	 * recommended for security reasons that you cast this parameter to an
	 * integer, rather than simply echoing back to the client what it sent in
	 * the draw parameter, in order to prevent Cross Site Scripting (XSS)
	 * attacks.
	 */
	@JsonView(View.class)
	private Integer draw;

	/**
	 * Total records, before filtering (i.e. the total number of records in the
	 * database)
	 */
	@JsonView(View.class)
	private Long recordsTotal;

	/**
	 * Total records, after filtering (i.e. the total number of records after
	 * filtering has been applied - not just the number of records being
	 * returned for this page of data).
	 */
	@JsonView(View.class)
	private Long recordsFiltered;

	/**
	 * The data to be displayed in the table. This is an array of data source
	 * objects, one for each row, which will be used by DataTables. Note that
	 * this parameter's name can be changed using the ajaxDT option's dataSrc
	 * property.
	 */
	@SuppressWarnings("rawtypes")
	@JsonView(View.class)
	private List data;

	/**
	 * Optional: If an error occurs during the running of the server-side
	 * processing script, you can inform the user of this error by passing back
	 * the error message to be displayed using this parameter. Do not include if
	 * there is no error.
	 */
	@JsonView(View.class)
	private String error;

	public interface View {
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	@SuppressWarnings("rawtypes")
	public List getData() {
		return data;
	}

	@SuppressWarnings("rawtypes")
	public void setData(List data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}