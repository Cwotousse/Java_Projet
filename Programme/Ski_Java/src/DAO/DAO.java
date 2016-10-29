package DAO;
import java.sql.Connection;
import java.util.ArrayList;

import POJO.Utilisateur;

public abstract class DAO<T> {
	protected Connection connect = null;
	public DAO(Connection conn) { this.connect = conn; }

	public abstract int create(T obj);
	public abstract boolean delete(T obj);
	public abstract boolean update(T obj);
	public abstract T find(T obj);

	/*public  int verifPseudoMdp(Utilisateur obj) {
		return -1;
	}*/

	public abstract ArrayList<T> getList();
}
