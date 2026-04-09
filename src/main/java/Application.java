import application.JanggiQueryService;
import application.JanggiService;
import application.JanggiServiceImpl;
import application.JanggiTxService;
import javax.sql.DataSource;
import repository.JanggiRepository;
import repository.JdbcJanggiRepository;
import repository.db.DataSourceManager;
import repository.db.TransactionTemplate;
import ui.FrontController;

public class Application {

    public static void main(String[] args) {
        DataSource dataSource = DataSourceManager.getDataSource();
        TransactionTemplate transactionTemplate = new TransactionTemplate(dataSource);
        JanggiRepository janggiRepository = new JdbcJanggiRepository(dataSource);

        JanggiServiceImpl janggiServiceImpl = new JanggiServiceImpl(janggiRepository);
        JanggiService janggiService = new JanggiTxService(transactionTemplate, janggiServiceImpl);
        JanggiQueryService janggiQueryService = new JanggiQueryService(janggiRepository);

        new FrontController(janggiService, janggiQueryService).run();
    }
}
