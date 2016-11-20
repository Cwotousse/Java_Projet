package be.mousty.utilitaire;

import java.sql.Date;

//CLASSE UTILISEE POUR RECUPERER LID DE LA PERSONNE
public class ComboItem
{
	private String 	key;
	private int 	value;
	private Date 	valueDate;
	private String 	valueString;

	public ComboItem(String key, int value)
	{
		this.key 	= key;
		this.value 	= value;
	}
	
	public ComboItem(String key, Date valueDate)
	{
		this.key 	= key;
		this.valueDate 	= valueDate;
	}
	
	public ComboItem(String key, String valueString)
	{
		this.key 	= key;
		this.valueString 	= valueString;
	}

	@Override
	public String 	toString	() { return key; 		} 
	public String 	getKey		() { return key; 		}
	public int 		getValue	() { return value; 		}
	public Date 	getValueDate() { return valueDate; 	}
	public String 	getValueString() { return valueString; 	}
}