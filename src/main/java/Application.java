import application.JanggiQueryService;
import application.JanggiService;
import javax.sql.DataSource;
import repository.JanggiRepository;
import repository.JdbcJanggiRepository;
import repository.db.DataSourceManager;
import repository.db.JdbcDao;
import repository.db.TransactionTemplate;
import ui.FrontController;

public class Application {

    public static void main(String[] args) {
        DataSource dataSource = DataSourceManager.getDataSource();
        TransactionTemplate transactionTemplate = new TransactionTemplate(dataSource);
        JanggiRepository janggiRepository = new JdbcJanggiRepository(new JdbcDao(dataSource));

        JanggiService janggiService = new JanggiService(transactionTemplate, janggiRepository);
        JanggiQueryService janggiQueryService = new JanggiQueryService(janggiRepository);

        new FrontController(janggiService, janggiQueryService).run();
    }
}
