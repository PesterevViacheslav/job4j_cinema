package ru.job4j.cinema.store;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
/**
 * Class PsqlStore - Хранилище в БД postgres.
 * Решение задач уровня Middle. Части 012. Servlet JSP.
 * Сервис - Кинотеатр[#283003]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 02.05.2021
 * @version 1
 */
public class PsqlStore implements Store {
    private static final Logger LOG = LogManager.getLogger(PsqlStore.class.getName());
    private final BasicDataSource pool = new BasicDataSource();
    /**
     * Method PsqlStore. Конструктор
     */
    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }
    /**
     * Class Lazy. Экземпляр хранилища
     */
    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }
    /**
     * Method instOf. Получение экземпляра
     * @return Экземпляр
     */
    public static Store instOf() {
        return Lazy.INST;
    }
    /**
     * Method findAllSeats. Отображение мест
     * @return Коллекция мест
     */
    @Override
    public Collection<Seat> findAllSeats() {
        List<Seat> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT s.*, u.name as user_name FROM seat s LEFT JOIN users u ON u.id = s.user_id ORDER BY hall_id, row, num")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Seat(it.getInt("id"),
                                       it.getInt("hall_id"),
                                       it.getInt("row"),
                                       it.getInt("num"),
                                       it.getInt("price"),
                                       it.getInt("user_id"),
                                       it.getString("user_name")
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error("findAllPosts", e);
        }
        return posts;
    }
    /**
     * Method create. Создание пользователя
     * @param user Пользователь
     * @return Пользователь
     */
    @Override
    public User create(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO users(name, phone, password) "
                             + "VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("create", e);
        }
        return user;
    }
    /**
     * Method reserve. Резерв места
     * @param row Место
     * @param num Место
     * @param user Место
     */
    @Override
    public boolean reserve(int user, int row, int num) {
        boolean res = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("UPDATE seat SET user_id = ? WHERE row = ? AND num = ? AND user_id IS NULL",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, user);
            ps.setInt(2, row);
            ps.setInt(3, num);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    if (id.getInt(1) > 0 ) {
                        res = true;
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("reserve", e);
        }
        return res;
    }
    /**
     * Method findSeatById. Поиск по ID.
     * @param id ID места
     * @return Место
     */
    @Override
    public Seat findSeatById(int id) {
        Seat res = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT s.*, u.name as user_name FROM seat s LEFT JOIN users u ON u.id = s.user_id WHERE s.id = ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    res = new Seat(rs.getInt("id"),
                                   rs.getInt("hall_id"),
                                   rs.getInt("row"),
                                   rs.getInt("num"),
                                   rs.getInt("price"),
                                   rs.getInt("user_id"),
                                   rs.getString("user_name")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("findPostById", e);
        }
        return res;
    }
    /**
     * Method findUserByPhonePassword. Поиск пользователя.
     * @param phone
     * @return Пользователь
     */
    @Override
    public User findUserByPhonePassword(String phone, String password) {
        User res = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users WHERE phone = ? and password = ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, phone);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    res = new User(rs.getInt("id"),
                                   rs.getString("name"),
                                   rs.getString("phone"),
                                   rs.getString("password")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("findUserByEmailPassword", e);
        }
        return res;
    }
}