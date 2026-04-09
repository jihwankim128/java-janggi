import application.JanggiService;
import com.mysql.cj.jdbc.MysqlDataSource;
import repository.JdbcJanggiRepository;
import ui.FrontController;

public class Application {

    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3309/janggi_db?serverTimezone=UTC&useSSL=false");
        dataSource.setUser("janggi");
        dataSource.setPassword("janggi1234");

        JanggiService janggiService = new JanggiService(new JdbcJanggiRepository(dataSource));
        FrontController frontController = new FrontController(janggiService);
        frontController.run();
    }
}
