package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    private UserExtractor userExtractor;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            insetRole(newKey.intValue(), user.getRoles());
        } else {
            namedParameterJdbcTemplate.update("""
                        UPDATE users SET name=:name, email=:email, password=:password, 
                        registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                    """, parameterSource);
            updateRole(user.id(), user.getRoles());
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_role ur on u.id = ur.user_id WHERE id=?", userExtractor, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_role ur on u.id = ur.user_id WHERE email=?", userExtractor, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_role ur on u.id = ur.user_id ORDER BY name, email", userExtractor);
    }

    @Component
    public class UserExtractor implements ResultSetExtractor<List<User>> {
        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<User> usersList = new ArrayList<>();
            Map<Integer, User> map = new LinkedHashMap<>();
            while (rs.next()) {
                User user = new User();
                int id = rs.getInt(1);
                if (!map.containsKey(id)) {
                    user.setId(id);
                    user.setName(rs.getString(2));
                    user.setEmail(rs.getString(3));
                    user.setPassword(rs.getString(4));
                    user.setRegistered(rs.getDate(5));
                    user.setEnabled(rs.getBoolean(6));
                    user.setCaloriesPerDay(rs.getInt(7));
                    String roles = rs.getString(9);
                    if (roles != null) {
                        user.setRoles(List.of((Role.valueOf(roles))));
                    } else {
                        user.setRoles(new HashSet<>());
                    }
                } else {
                    user = map.get(id);
                    Set<Role> roleSet = user.getRoles();
                    roleSet.add(Role.valueOf(rs.getString(9)));
                    user.setRoles(roleSet);
                }
                map.put(id, user);
            }
            for (User u : map.values()) {
                usersList.add(u);
            }
            return usersList;
        }
    }

    protected void insetRole(int id, Set<Role> role) {
        for (Role r : role) {
            jdbcTemplate.update("INSERT INTO user_role  (user_id, role) VALUES (?,?)", id, r.toString());
        }
    }

    protected void updateRole(int id, Set<Role> role) {
        for (Role r : role) {
            jdbcTemplate.update("UPDATE user_role SET role=? WHERE user_id = ?", r.toString(), id);
        }
    }
}
