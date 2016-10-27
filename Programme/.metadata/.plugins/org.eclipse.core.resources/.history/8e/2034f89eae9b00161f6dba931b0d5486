package DAO;
import java.sql.Connection;

import POJO.Utilisateur;

public abstract class DAO<T> {
	protected Connection connect = null;
	public DAO(Connection conn) { this.connect = conn; }

	public abstract boolean create(T obj);
	public abstract boolean delete(T obj);
	public abstract boolean update(T obj);
	public abstract T find(int id);

	public  int verifPseudoMdp(Utilisateur obj) {
		return -1;
	}
}
