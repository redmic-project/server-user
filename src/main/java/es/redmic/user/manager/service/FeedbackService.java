package es.redmic.user.manager.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.redmic.user.config.EntityManagerWrapper;
import es.redmic.user.manager.dto.FeedbackDTO;

@Service
public class FeedbackService {

	@Autowired
	EntityManagerWrapper emw;

	public FeedbackService() {
	}

	public void send(FeedbackDTO dto, String emailContact) {

		String queryString = "SELECT * from app.send_email(?, ?, ?, ?)";

		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(0, emailContact);
		parameters.add(1, dto.getSubject());
		String message = "Email: " + dto.getEmail() + ".<br />Nombre: " + dto.getName() + ".<br />Mensaje: "
				+ dto.getMessage();

		if (dto.getCodeError() != null)
			message += ".<br />Código de error: " + dto.getCodeError();

		parameters.add(2, message);
		parameters.add(3, dto.getEmail());

		emw.setQueryString(queryString);
		emw.setParameters(parameters);
		emw.execute();
	}
}