package es.redmic.user.common.query;

import es.redmic.exception.database.DBQueryException;

public class Range {
	private long start = 0;
	private long end = 200;
	private long total;

	public long getStart() {
		return this.start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return this.end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Range() {
		this.total = this.end - this.start;
	}

	public Range(String range) {
		this.set(range);
	}

	public String toString() {
		if (this.end > total)
			return "items " + this.start + "-" + this.total + "/" + this.total;
		return "items " + this.start + "-" + this.end + "/" + this.total;
	}

	public boolean validate() {
		if (this.end < this.start)
			throw new DBQueryException();
		return true;
	}

	public void set(String range) {
		String pattern = "^items=(\\d+)-(\\d+)$";
		this.start = Integer.parseInt(range.replaceAll(pattern, "$1"));
		this.end = Integer.parseInt(range.replaceAll(pattern, "$2"));
	}

	public long getLimit() {
		return this.end - this.start + 1;
	}

	public long getOffset() {
		return this.start;
	}

	// Para hacer compatible la paginaci�n del Grid con el QueryDSL se necesitan
	// m�todos para obtener
	// La p�gina y el tama�o a partir del rango.

	/*
	 * Obtener pagina a partir del rango
	 */
	public long getPage() {
		if (this.start == 0)
			return 0;
		else
			return new Double(this.end / getSize()).intValue();
	}

	/*
	 * Obtener tama�o de la pagina a partir del rango
	 */
	public long getSize() {
		if ((this.end - this.start) > 0)
			return this.end - this.start + 1;
		else
			return this.end + 1;
	}
}
