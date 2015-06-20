package com.fork.persistance.rdf;

import com.hp.hpl.jena.query.DatasetAccessorFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import com.hp.hpl.jena.update.UpdateRequest;

public class RdfQuery {
	private String stmt;
	private String prefix;

	public RdfQuery() {
	}

	public RdfQuery(String stmt) {
		this.stmt = stmt;
	}

	public String getStmt() {
		return stmt;
	}

	public void setStmt(String stmt) {
		this.stmt = stmt;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public ResultSet execute(QueryType type) {
		if (type == QueryType.INSERT) {
			update();
			return null;
		} else if (type == QueryType.UPDATE) {
			update();
			return null;
		} else if (type == QueryType.SELECT) {
			return select();
		} else if (type == QueryType.DELETE) {
			update();
			return null;
		}
		return null;
	}

	private ResultSet select() {
		final String SELECT_URL = RdfUtilty.BASE_URL + "/data";
		String selectQuery = RdfUtilty.PREFIXES + this.prefix + this.stmt;
		Model model = DatasetAccessorFactory.createHTTP(SELECT_URL).getModel();
		Query query = QueryFactory.create(selectQuery);
		QueryExecution queryExe = QueryExecutionFactory.create(query, model);
		return queryExe.execSelect();
	}

	private void update() {
		final String UPDATE_URL = RdfUtilty.BASE_URL + "/update";
		String updateQuery = RdfUtilty.PREFIXES + prefix + stmt;
		UpdateRequest updateRqst = UpdateFactory.create(updateQuery);
		UpdateProcessor processor = UpdateExecutionFactory.createRemote(
				updateRqst, UPDATE_URL);
		processor.execute();
	}
}
