import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cinema?&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "admin";
    private static final Interactor interactor = new Interactor();

    public static void main(String[] args) {
        inicio();
    }

    private static void inicio() {
        int num = interactor.inicio();
        if (num == 1) {
            estrena();
        } else if (num == 2) {
            directors();
        } else if (num == 3) {
            insert();
        } else {
            System.out.println("NO");
        }
    }

    private static void estrena() {
        String data = interactor.data1();
        String data2 = interactor.data2();
        String query = "SELECT * FROM Films WHERE ReleaseDate BETWEEN '" + data +"' AND '"  + data2 +"'";
        executeQuery(query);
    }
    private static void directors() {
        String director = interactor.director();

        String query = "SELECT Films.Title, Films.ReleaseDate " +
                "FROM Films " +
                "JOIN Film_Peli ON Films.idFilm = Film_Peli.idFilm " +
                "JOIN Director ON Film_Peli.idDirector = Director.idDirector " +
                "WHERE Director.Nom LIKE '" + director + "';";
        executeQuery(query);
    }
    private static void insert() {
        interactor.film();
        String query = "INSERT INTO films(Title, ReleaseDate, Country) values " +
                "('" + interactor.getTitulo() + "', '" + interactor.getDataEstrena() + "', '" + interactor.getPais() + "');";
        executeInsert(query);
    }
    private static void executeQuery(String query) {
        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                int colNum = getColumnNames(resultSet);
                while (resultSet.next()) {
                    for (int i = 1; i <= colNum; i++) {
                        System.out.print(resultSet.getString(i));
                        if (i < colNum) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static void executeInsert(String query) {
        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static int getColumnNames(ResultSet rs) throws SQLException {
        int numberOfColumns = 0;
        if (rs != null) {   //create an object based on the Metadata of the result set
            ResultSetMetaData rsMetaData = rs.getMetaData();
            numberOfColumns = rsMetaData.getColumnCount();   //Use the getColumn method to get the number of columns returned
            for (int i = 1; i < numberOfColumns + 1; i++) {  //get and print the column names, column indexes start from 1
                String columnName = rsMetaData.getColumnName(i);
                System.out.print(columnName + ", ");
            }
        }
        System.out.println();
        return numberOfColumns;
    }
}


