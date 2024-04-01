package dbService.dao;

import dbService.datasets.UsersDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UsersDao {

    private Session session;

    public UsersDao(Session session) {
        this.session = session;
    }

    public UsersDataSet get(long id) throws HibernateException {
        return (UsersDataSet) session.get(UsersDataSet.class, id);
    }

    public long insertUser(String login, String password) throws HibernateException {
        return (Long) session.save(new UsersDataSet(login, password));
    }

    public UsersDataSet getUserByLoginAndPassword(String login, String password) throws HibernateException {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        return (UsersDataSet) criteria
                .add(Restrictions.eq("login", login))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }
}