package es.redmic.user.manager.service;

/*-
 * #%L
 * User
 * %%
 * Copyright (C) 2019 REDMIC Project / Server
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
