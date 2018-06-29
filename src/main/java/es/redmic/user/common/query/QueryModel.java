package es.redmic.user.common.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Operator;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

import es.redmic.exception.database.DBQueryException;

public class QueryModel {

	private String sort;
	private String where;
	private String text;
	private Range range;
	private String qTable;

	public QueryModel() {
		this.range = new Range();
	}

	/*
	 * Devuelve un pageRequest con la paginaci�n y la ordenaci�n si la hubiera
	 */
	public PageRequest getPageRequest() {
		Sort sortParam = null;
		ArrayList<Sort.Order> orderlist = this.getSortList();
		if (orderlist != null && orderlist.size() > 0) {
			sortParam = new Sort(orderlist.get(0).getDirection(), orderlist.get(0).getProperty());
		}
		else
			sortParam = new Sort(Direction.ASC, "id");
		return PageRequest.of((int) range.getPage(), (int) range.getSize(), sortParam);
	}

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}

	/*
	 * Devuelve una lista de order, que es el tipo de datos que se le pasa a
	 * Sort para establecer la ordenaci�n (1 order por campo a ordenar)
	 */

	public ArrayList<Sort.Order> getSortList() {

		ArrayList<Sort.Order> orderlist = new ArrayList<Sort.Order>();

		if (sort != null) {

			String sortpattern = "\\(([-|+](\\w+),)*([+|-](\\w+))\\)";

			if (Pattern.matches(sortpattern, sort)) {

				sort = sort.substring(1, sort.length() - 1);

				Pattern pattern = Pattern.compile(",");
				String[] words = pattern.split(sort);

				for (String s : words) {
					Order order;
					if (s.substring(0, 1).toString().equals("-"))
						order = new Order(Direction.DESC, s.substring(1).toLowerCase());
					else
						order = new Order(Direction.ASC, s.substring(1).toLowerCase());
					orderlist.add(order);
				}
			} else
				return null;
		} else
			return null;

		return orderlist;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	// Si se ha obtenido una query con where devolvemos el criterion a aplicar,
	// si no devolvemos null
	public Predicate getWhere() {
		// Pasarle desde el controller el modelo.
		if (where != null) {
			Class<?> entityClass = null;
			try {
				entityClass = Class.forName(getqTable());
			} catch (Exception ex) {
				throw new DBQueryException(ex);
			}

			Path<?> entity = Expressions.path(entityClass, entityClass.getFields()[0].getName());

			String w = "[a-zA-Z�������������]";

			ArrayList<String> singlePattern = new ArrayList<String>();
			// Cada una de las subexpresiones con las que tiene que coincidir.
			// (una por cada operaci�n)
			// Expresiones para Strings
			singlePattern.add("(LIKE)\\s\\'(%?((" + w + "+\\s?)+)%?)\\'|");
			singlePattern.add("(NOT_LIKE)\\s\\'(%?((" + w + "+\\s?)+)%?)\\'|");
			singlePattern.add("IS_NULL|");
			singlePattern.add("IS_NOT_NULL|");
			// Expresiones para Numbers
			singlePattern.add("(\\=\\=)\\s(\\d+)|");
			singlePattern.add("(\\>)\\s(\\d+)|");
			singlePattern.add("(\\<)\\s(\\d+)|");
			singlePattern.add("(\\>\\=)\\s(\\d+)|");
			singlePattern.add("(\\<\\=)\\s(\\d+)|");
			singlePattern.add("(\\!\\=)\\s(\\d+)"); // el �ltimo no tiene |

			// Inicio de la expresi�n regular
			String pString = "((?<op1>" + w + "+\\.?" + w + "+)\\s(";
			// Por cada una de las subexpresiones, la concatenamos a la
			// expresi�n base
			for (String object : singlePattern) {
				pString += object;
			}
			// Final de la expresi�n
			pString += ")(\\s(?<conjuntion>and|or))?)+";

			//
			Pattern pattern = Pattern.compile(pString);
			Matcher matcher = pattern.matcher(where);

			BooleanBuilder builder = new BooleanBuilder();
			String lastConjuntion = null;
			Boolean match = false;
			while (matcher.find()) {
				match = true;
				Predicate pred = null;
				String op1 = matcher.group("op1").toString().toLowerCase();
				String type = validate(entityClass, op1);
				if (type == null)
					throw new DBQueryException();

				String operator = matcher.group(3).split(" ")[0];
				String op2 = null;
				// Si es un string se hace un split por comillas y si es un
				// n�mero split por espacio
				// TODO: Arreglar expresi�n para no tener que hacer split
				if (matcher.group(3).split(" ").length > 1) {
					if (matcher.group(3).split("\\'").length > 1) {
						op2 = matcher.group(3).split("\\'")[1];
					} else {
						op2 = matcher.group(3).split(" ")[1];
					}
				}

				// Path dependiente del tipo
				Path<?> attr = null;
				if (type.contains("String"))
					attr = Expressions.path(String.class, entity, op1);
				else if (type.contains("Long"))
					attr = Expressions.path(Long.class, entity, op1);
				else if (type.contains("Integer"))
					attr = Expressions.path(Integer.class, entity, op1);
				else if (type.contains("Double"))
					attr = Expressions.path(Double.class, entity, op1);
				else if (type.contains("Float"))
					attr = Expressions.path(Float.class, entity, op1);
				else if (type.contains("DateTime"))
					attr = Expressions.path(DateTime.class, entity, op1);

				switch (operator) {
				case "LIKE":
					pred = Expressions.predicate(Ops.LIKE, attr, Expressions.constant(op2));
					break;
				case "NOT_LIKE":
					pred = Expressions.predicate(Ops.LIKE, attr, Expressions.constant(op2)).not();
					break;
				case "IS_NULL":
					pred = Expressions.predicate(Ops.IS_NULL, attr);
					break;
				case "IS_NOT_NULL":
					pred = Expressions.predicate(Ops.IS_NOT_NULL, attr);
					break;
				case "==":
					pred = getNumericExpression(Ops.EQ, attr, type, op2);
					break;
				case ">":
					pred = getNumericExpression(Ops.GT, attr, type, op2);
					break;
				case "<":
					pred = getNumericExpression(Ops.LT, attr, type, op2);
					break;
				case ">=":
					pred = getNumericExpression(Ops.GOE, attr, type, op2);
					break;
				case "<=":
					pred = getNumericExpression(Ops.LOE, attr, type, op2);
					break;
				case "!=":
					pred = getNumericExpression(Ops.NE, attr, type, op2);
					break;
				default:
					break;
				}
				if ((lastConjuntion != null && lastConjuntion.contains("or")) || (builder.getValue() == null)) {
					builder.or(pred);
				} else if (lastConjuntion != null && lastConjuntion.contains("and")) {
					builder.and(pred);
				}
				lastConjuntion = matcher.group("conjuntion");
			}

			if (!match) // si la query no caza con nada
				throw new DBQueryException();
			return builder;// Restrictions.sqlRestriction(where);
		} else
			return null;
	}

	public String exitsWhere() {
		if (where != null) {
			return where;
		} else
			return null;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	/*
	 * Devuelve la expresi�n dependiendo el tipo de dato por el que se est�
	 * buscando
	 */
	private BooleanExpression getNumericExpression(Operator ops, Path<?> attr, String type, String op2) {
		if (type.contains("Long"))
			return Expressions.predicate(ops, attr, Expressions.constant(Long.parseLong(op2)));
		else if (type.contains("Integer"))
			return Expressions.predicate(ops, attr, Expressions.constant(Integer.parseInt(op2)));
		else if (type.contains("Double"))
			return Expressions.predicate(ops, attr, Expressions.constant(Double.parseDouble(op2)));
		else if (type.contains("Float"))
			return Expressions.predicate(ops, attr, Expressions.constant(Float.parseFloat(op2)));
		else if (type.contains("DateTime"))
			return Expressions.predicate(ops, attr, Expressions.constant(DateTime.parse(op2)));
		else
			return null;

	}

	/*
	 * Devuelve un predicado con la busqueda de la cadena text en todos los
	 * campos de tipo string
	 */
	public Predicate getText() {
		Boolean match = false;
		if (text != null) {
			Class<?> entityClass = null;
			try {
				entityClass = Class.forName(getqTable());
			} catch (ClassNotFoundException e) {
				throw new DBQueryException(e);
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			PathBuilder<?> entity = new PathBuilder(entityClass, entityClass.getFields()[0].getName());
			Field[] fields = entityClass.getDeclaredFields();
			BooleanBuilder builder = new BooleanBuilder();
			for (Field field : fields) {
				if (field.getType().toString().contains("StringPath")) {
					match = true;
					StringPath path = Expressions.stringPath(entity, field.getName());
					builder.or(path.contains(text));
				}
			}
			if (!match) // si la query no caza con nada
				throw new DBQueryException();
			return builder;
		} else
			return null;
	}

	public String exitsText() {
		if (text != null) {
			return text;
		} else
			return null;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getqTable() {
		return qTable;
	}

	public void setqTable(String qTable) {
		this.qTable = qTable;
	}

	private String validate(Class<?> entityClass, String op1) {
		Field[] fields = entityClass.getDeclaredFields();
		String[] op = op1.split("\\.");
		op1 = op[0];
		String type = null;
		for (Field field : fields) {
			if (field.getName().equals(op1)) {

				type = field.getGenericType().toString();

				// TODO: que devuelva el tipo del operando despues del punto, no
				// suponer q es id
				if (op.length > 1)
					;
				type = "Long";
			}
		}
		return type;
	}
}
