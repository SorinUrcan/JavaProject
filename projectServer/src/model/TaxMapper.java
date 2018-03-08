package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaxMapper {
	public List<Tax> getAllTaxes() {
		List<Tax> taxes = new ArrayList<>();
		ResultSet res = DB.getDBInstance().executeQuery("select * from taxes;");
		try {
			while (res.next()) {
					Tax tax = new Tax();
					tax.setId(res.getString("id"));
					tax.setClient(res.getString("client"));
					tax.setEmitter(res.getString("emitter"));
					tax.setAmount(res.getString("amount"));
					tax.setType(res.getString("type"));
					tax.setStatus(res.getString("status"));
					tax.setDescription(res.getString("description"));
					taxes.add(tax);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return taxes;
	}
	
	public void createTax(Tax user) {
		StringBuilder query = new StringBuilder();
		query.append("insert into taxes (client,emitter,amount,type,status,description) values ('");
		query.append(user.getClient());
		query.append("','");
		query.append(user.getEmitter());
		query.append("','");
		query.append(user.getAmount());
		query.append("','");
		query.append(user.getType());
		query.append("','");
		query.append(user.getStatus());
		query.append("','");
		query.append(user.getDescription());
		query.append("');");
		DB.getDBInstance().executeUpdate(query.toString());
	}
	
	public void updateTax(Tax tax) {
		String id = tax.getId(), client = tax.getClient(), emitter = tax.getEmitter(), amount = tax.getAmount(), type = tax.getType(), status = tax.getStatus(), description = tax.getDescription();
		StringBuilder query = new StringBuilder();
		query.append("update taxes set client='");
		query.append(client);
		query.append("',emitter='");
		query.append(emitter);
		query.append("',amount='");
		query.append(amount);
		query.append("',type='");
		query.append(type);
		query.append("',status='");
		query.append(status);
		query.append("',description='");
		query.append(description);	
		query.append("' where id='");
		query.append(id);
		query.append("';");
		DB.getDBInstance().executeUpdate(query.toString());
	}
	
	public void deleteTax(Tax user) {
		StringBuilder query = new StringBuilder();
		query.append("delete from taxes where id='");
		query.append(user.getId());
		query.append("';");
		DB.getDBInstance().executeUpdate(query.toString());
	}

	public void updateTaxStatus(String id, String status) {
		StringBuilder query = new StringBuilder();
		query.append("update taxes set status='");
		query.append(status);	
		query.append("' where id='");
		query.append(id);
		query.append("';");
		DB.getDBInstance().executeUpdate(query.toString());
	}
}
