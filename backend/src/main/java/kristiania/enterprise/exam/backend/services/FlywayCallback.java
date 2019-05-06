package kristiania.enterprise.exam.backend.services;

import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.springframework.context.annotation.Configuration;

import java.sql.ResultSet;

@Configuration
public class FlywayCallback implements Callback {

    @Override
    public boolean supports(Event event, Context context) {
        return true;
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        return false;
    }

    @Override
    public void handle(Event event, Context context) {
        System.out.println(event.toString());
        if(event.equals(Event.AFTER_MIGRATE)) {

            try {
                ResultSet resultSet = context.getConnection()
                        .prepareStatement("select * from information_schema.tables")
                        .executeQuery();

                while(resultSet.next()) {

                    System.out.println(resultSet.getString("table_name"));
                }
            } catch (Exception e) {
                //blank
            }
        }
    }
}

