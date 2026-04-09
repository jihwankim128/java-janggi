import application.JanggiService;
import application.JanggiServiceImpl;
import application.JanggiTxService;
import javax.sql.DataSource;
import repository.JdbcJanggiRepository;
import repository.db.DataSourceManager;
import repository.db.TransactionTemplate;
import ui.FrontController;

public class Application {

    public static void main(String[] args) {
        DataSource dataSource = DataSourceManager.getDataSource();
        TransactionTemplate transactionTemplate = new TransactionTemplate(dataSource);

        JanggiServiceImpl janggiServiceImpl = new JanggiServiceImpl(new JdbcJanggiRepository(dataSource));
        JanggiService janggiService = new JanggiTxService(transactionTemplate, janggiServiceImpl);

        FrontController frontController = new FrontController(janggiService);
        frontController.run();
    }
}
