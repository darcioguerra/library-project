/**
 * Classe onde serão guardadas informações de revisores que se negarem a aceitar o pedido de adesao a uma revisao por parte do gestor
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class DeclinedReviewers {
	
	/**
	 * Metodo para adicionar um revisor, que negou um pedido de adesao a uma revisao, na base de dados
	 * @author Dárcio Guerra e Rodrigo Pereira
	 */
	public boolean addDeclinedReviewer(String aLogin, Long aSerialNumber) {
		Inserts i = new Inserts();

		if (i.insertDeclinedReviewers(aLogin, aSerialNumber))
			return true;
			
		return false;
	}
}
