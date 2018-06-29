package es.redmic.user.common.dto;

import java.util.List;

import es.redmic.models.es.common.dto.SuperDTO;

public class BodyListDTO<TDto> extends SuperDTO {

	private List<TDto> body;

	private Long total;

	public BodyListDTO(List<TDto> list) {
		super(true);
		this.body = list;
	}

	public List<TDto> getBody() {
		return body;
	}

	public void setBody(List<TDto> list) {
		this.body = list;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
}
