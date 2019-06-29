package Modele;


import Modele.DAOException;
import Modele.DiscountEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class DAO {

	protected final DataSource myDataSource;

	/**
	 *
	 * @param dataSource la source de données à utiliser
	 */
	public DAO(DataSource dataSource) {
		this.myDataSource = dataSource;
	}

        /**
         * Permet d'ajouter un discount danl a BDD
         * @param discountCode, le code de la réduction sur un charactère
         * @param taux, le taux associé
         */
        public void addDiscount(String discountCode, double taux)
        {
            String sql = "INSERT INTO DISCOUNT_CODE (discount_code, rate) values (?,?)";
            
            try 
            (   
                Connection connection = myDataSource.getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql)
            ) 
            {
                // Définir la valeur du paramètre
                stmt.setString(1,discountCode);
                stmt.setDouble(2,taux);
                stmt.executeUpdate();
            }  
            catch (SQLException ex) 
            {
                Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            }
        }
        
        /**
         * 
         * @return
         * @throws DAOException 
         */
        public List<DiscountEntity> getDiscountList() throws DAOException {
		List<DiscountEntity> result = new LinkedList<>(); // Liste vIde

		String sql = "SELECT * FROM DISCOUNT_CODE";
		try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) { // Tant qu'il y a des enregistrements
                                   
					// On récupère les champs nécessaires de l'enregistrement courant
					char discount_code = rs.getString("DISCOUNT_CODE").charAt(0);
					double discount_rate = rs.getDouble("RATE");
					// On crée l'objet entité
					DiscountEntity c = new DiscountEntity(discount_code,discount_rate);
					// On l'ajoute à la liste des résultats
					result.add(c);
				}
			}
		}  catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new DAOException(ex.getMessage());
		}
		return result;
	}
        
        public void deleteDiscount(String discountCode)
        {
            String sql = "DELETE FROM DISCOUNT_CODE WHERE discount_code = ?";
            
            try (   Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)
                ) {
                        // Définir la valeur du paramètre
			stmt.setString(1,discountCode);
			stmt.executeUpdate();

		}  catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
		
		}
        }
        
        public void updateDiscount(String discountCode, double taux)
        {
            String sql = "UPDATE DISCOUNT_CODE set rate = ? WHERE discount_code = ?";
            
            try 
            (   
                Connection connection = myDataSource.getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql)
            ) 
            {
                // Définir la valeur du paramètre
                stmt.setDouble(1,taux);
                stmt.setString(2,discountCode);
                stmt.executeUpdate();
            }  
            catch (SQLException ex) 
            {
                Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            }
        }
        
        
}
